package com.dao.redis;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.service.LoadCallBack;

import redis.clients.jedis.Jedis;

public class RedisDao {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private static final String LOCK_SUCCESS = "OK";
	private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final Long RELEASE_SUCCESS = 1L;
	
	private StringRedisTemplate stringTemplate;
	private RedisScript<String> updateAvailableSavingsCard;
	private RedisTemplate<String, String> template;
	
	public RedisTemplate<String, String> getTemplate() {
		return template;
	}

	public void setTemplate(RedisTemplate<String, String> template) {
		this.template = template;
	}

	public RedisScript<String> getUpdateAvailableSavingsCard() {
		return updateAvailableSavingsCard;
	}

	public void setUpdateAvailableSavingsCard(RedisScript<String> updateAvailableSavingsCard) {
		this.updateAvailableSavingsCard = updateAvailableSavingsCard;
	}

	public StringRedisTemplate getStringTemplate() {
		return stringTemplate;
	}

	public void setStringTemplate(StringRedisTemplate stringTemplate) {
		this.stringTemplate = stringTemplate;
	}
	
	/**
	 * 获取单机redis分布式锁
	 * @param lockKey 分布式锁的key
	 * @param requestId 由客户端生成的一个随机字符串，它要保证在足够长的一段时间内在所有客户端的所有获取锁的请求中都是唯一的
	 * @param expireTime  锁key的过期时间,expire time units: EX = seconds; PX = milliseconds
	 * @return
	 */
	public boolean getDistributedLock(final String lockKey, final String requestId, final int expireTime){
		String result = getTemplate().execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				Jedis jedis = (Jedis) connection.getNativeConnection();
				return jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
			}		
		});
		
		if (LOCK_SUCCESS.equals(result)) {
            return true;
        }
        return false;
	}
	
	/**
     * 释放redis分布式锁
     * @param lockKey 锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public boolean releaseDistributedLock(String lockKey, String requestId) {
    	final String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
    	Object result = getTemplate().execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				Jedis jedis = (Jedis) connection.getNativeConnection();
				return jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
			}
		});
    	
        if (RELEASE_SUCCESS.equals(result)) {
            return true;
        }
        return false;

    }
	
	public String executeScript(String numKey,String userPhone,String infoKey){
		String SecKillHsetName = "miaosha_" + numKey + "_user";
		return stringTemplate.execute(updateAvailableSavingsCard, Arrays.asList(numKey,SecKillHsetName,infoKey),userPhone,String.valueOf(System.currentTimeMillis()));
	}
	
	public void setObject(String key,long expire,String value){
		if(expire>0){
			stringTemplate.opsForValue().set(key, value,expire ,TimeUnit.HOURS);
		}else{
			stringTemplate.opsForValue().set(key, value);
		}
	}
	
	public <T> T getObject(String key,long expire,TypeReference<T> clazz,LoadCallBack<T> load){
		String value = stringTemplate.opsForValue().get(key)+"";
		if(logger.isDebugEnabled()){
			logger.debug("======get from redis key:" + key + ",value:" + value + "==========");
		}
		if(StringUtils.isBlank(value)||value.equalsIgnoreCase("null")){
			synchronized (this) {
				value = stringTemplate.opsForValue().get(key)+"";
				if(StringUtils.isBlank(value)||value.equalsIgnoreCase("null")){
					T t = load.load();
					String jsonString = JSON.toJSONString(t);
					if(logger.isDebugEnabled()){
						logger.debug("======get from db key:" + key + ",value:" + jsonString + "==========");
					}
					setObject(key,expire,jsonString);
					return t;
				}
				if(logger.isDebugEnabled()){
					logger.debug("======get from redis key:" + key + ",value:" + value + "==========");
				}
				return JSON.parseObject(value,clazz);
			}
		}else {
			return JSON.parseObject(value, clazz);
		}
	}
	
	/**
	 * 获取名为key的bitmap中index个元素的值
	 * @param key
	 * @param index
	 * @return
	 */
	public Boolean getBitMap(String key, Long index){
		return template.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.getBit(key.getBytes(Charset.forName("UTF-8")), index);
			}
		});
	}
	
	/**
	 * 设置名为key的bitmap中index个元素的值
	 * @param key
	 * @param index
	 * @param tag
	 * @return
	 */
	public Boolean setBitMap(String key, Long index, Boolean tag) {
		return template.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.setBit(key.getBytes(Charset.forName("UTF-8")), index, tag);
			}
		});
	}
	
	/**
	 * 统计bitmap中value为1的个数
	 * @param key
	 * @return
	 */
	public Long countBitMap(String key){
		return template.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.bitCount(key.getBytes(Charset.forName("UTF-8")));
			}
		});
	}
	
	/**
	 * 按照范围统计bitmap中value为1的个数
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Long countBitMap(String key, long start, long end){
		return template.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.bitCount(key.getBytes(Charset.forName("UTF-8")), start,end);
			}
		});
	}
	
	/**
	 * 对任意个key求AND 、 OR 、 NOT 、 XOR操作,结果返回到saveKey中
	 * @param op
	 * @param saveKey
	 * @param desKey
	 * @return
	 */
	public Long bitOp(RedisStringCommands.BitOperation op, String saveKey, String... desKey) {
	    byte[][] bytes = new byte[desKey.length][];
	    for (int i = 0; i < desKey.length; i++) {
	        bytes[i] = desKey[i].getBytes(Charset.forName("UTF-8"));
	    }
	    return template.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.bitOp(op, saveKey.getBytes(Charset.forName("UTF-8")), bytes);
			}
	    });
	}

}
