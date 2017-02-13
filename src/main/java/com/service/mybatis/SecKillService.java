package com.service.mybatis;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

import com.alibaba.fastjson.TypeReference;
import com.dao.mybatisauto.SeckillMapper;
import com.dao.mybatisauto.SuccessKilledMapper;
import com.dao.redis.RedisDao;
import com.excption.SecKillException;
import com.excption.SeckillCloseException;
import com.excption.SeckillRepeatException;
import com.pojo.SecKillResult;
import com.pojo.SeckillVo;
import com.pojo.mybatisauto.Seckill;
import com.pojo.mybatisauto.SeckillExample;
import com.pojo.mybatisauto.SuccessKilled;
import com.service.LoadCallBack;

import net.sf.json.JSONObject;

public class SecKillService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private SeckillMapper seckillMapper;
	private SuccessKilledMapper successKilledMapper;
	private final String salt = "wfmhbbwt";
	private RedisDao redisDao;

	public RedisDao getRedisDao() {
		return redisDao;
	}

	public void setRedisDao(RedisDao redisDao) {
		this.redisDao = redisDao;
	}

	public SeckillMapper getSeckillMapper() {
		return seckillMapper;
	}
	
	public void setSeckillMapper(SeckillMapper seckillMapper) {
		this.seckillMapper = seckillMapper;
	}
	
	public SuccessKilledMapper getSuccessKilledMapper() {
		return successKilledMapper;
	}
	
	public void setSuccessKilledMapper(SuccessKilledMapper successKilledMapper) {
		this.successKilledMapper = successKilledMapper;
	}
	
	public List<Seckill> getSecKillList(int pageNo,int pageSize){
		SeckillExample conditions = new SeckillExample();
		int start = (pageNo-1)*pageSize;
		conditions.setLimitStart(start);
		conditions.setPageSize(pageSize);
		List<Seckill> seckills = seckillMapper.selectByExample(conditions);
		return seckills;
	}
	
	public Seckill getSecKillById(long secKillId){
		String key = "miaosha"+secKillId;
		Seckill seckill = redisDao.getObject(key, 2, new TypeReference<Seckill>(){}, new LoadCallBack<Seckill>() {
			@Override
			public Seckill load() {
				return seckillMapper.selectByPrimaryKey(secKillId);
			}
		});
		String leftNumber = redisDao.getObject("miaosha_"+secKillId+"_num", -1, new TypeReference<String>(){}, new LoadCallBack<String>() {
			@Override
			public String load(){
				return String.valueOf(seckill.getNumber());
			}
		});
		//Seckill seckill = seckillMapper.selectByPrimaryKey(secKillId);
		return seckill;
	}
	
	/**
	 * 当秒杀商品开始时获取秒杀的接口地址,否则为秒杀商品开始时间
	 * @param secKillId
	 */
	public SeckillVo getSeckillUrl(long secKillId){
		Seckill seckill = getSecKillById(secKillId);
		SeckillVo seckillVo = new SeckillVo();
		seckillVo.setSecKillId(secKillId);
		if(null==seckill){
			seckillVo.setBegin(false);
			return seckillVo;
		}
		Date startTime = seckill.getStartTime();
		Date endTime =seckill.getEndTime();
		seckillVo.setStartTime(startTime);
		seckillVo.setEndTime(endTime);
		Date now = new Date();
		if(now.getTime()<startTime.getTime()||now.getTime()>endTime.getTime()){
			seckillVo.setBegin(false);
			return seckillVo;
		}
		seckillVo.setNow(now);
		seckillVo.setBegin(true);
		String md5 = getMD5(seckill.getSeckillId());
		seckillVo.setMd5Url(md5);
		return seckillVo;
	}
	
	public boolean saveSeckillByRedis(long secKillId,String userPhone,String md5)throws SeckillCloseException,SeckillRepeatException,SecKillException{
		if(StringUtils.isBlank(md5)||(!md5.equals(getMD5(secKillId)))){
			throw new SecKillException("秒杀数据错误");
		}
		try{
			String result = redisDao.executeScript("miaosha_" + secKillId + "_num",userPhone,"miaosha" + secKillId);
			System.out.println(result);
			JSONObject json = JSONObject.fromObject(result);
			SecKillResult secKillResult = (SecKillResult)JSONObject.toBean(json, SecKillResult.class);
			if("1".equals(secKillResult.getResultCode())){
				return true;
			}else if("-1".equals(secKillResult.getResultCode())){
				throw new SeckillRepeatException("重复秒杀");
			}else if("0".equals(secKillResult.getResultCode())){
				throw new SeckillCloseException("秒杀已结束");
			}else if("-2".equals(secKillResult.getResultCode())){
				throw new SecKillException("秒杀错误");
			}
		} catch (SeckillRepeatException e) {
			logger.error(e.getMessage(),e);
			throw new SeckillRepeatException(e.getMessage());
		} catch (SeckillCloseException e) {
			logger.error(e.getMessage(),e);
			throw new SeckillCloseException(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SecKillException(e.getMessage());
		}
		return false;
	}
	
	/**
	 * 通过mysql存储过程执行秒杀
	 * @param secKillId
	 * @param userPhone
	 * @param md5
	 * @return
	 * @throws SeckillCloseException
	 * @throws SeckillRepeatException
	 * @throws SecKillException
	 */
	public boolean saveSeckillByProcedure(long secKillId,String userPhone,String md5)throws SeckillCloseException,SeckillRepeatException,SecKillException{
		if(StringUtils.isBlank(md5)||(!md5.equals(getMD5(secKillId)))){
			throw new SecKillException("秒杀数据错误");
		}
		Map<String, Object> params = new HashMap<>();
		params.put("seckillId", secKillId);
		params.put("userPhone", userPhone);
		params.put("createTime", new Date());
		params.put("result", null);
		try {
			successKilledMapper.seckillByProcedure(params);
			int result = MapUtils.getInteger(params, "result" ,-2);
			if(result==1){
				return true;
			}else if(result == -1){
				throw new SeckillRepeatException("重复秒杀");
			}else if(result == 0){
				throw new SeckillCloseException("秒杀已结束");
			}else if(result ==-2){
				throw new SecKillException("秒杀错误");
			}
		} catch (SeckillRepeatException e) {
			logger.error(e.getMessage(),e);
			throw new SeckillRepeatException(e.getMessage());
		} catch (SeckillCloseException e) {
			logger.error(e.getMessage(),e);
			throw new SeckillCloseException(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SecKillException(e.getMessage());
		}
		return false;
	}
	
	/**
	 * 执行秒杀
	 * @param secKillId
	 * @param userPhone
	 * @param md5
	 * @return  秒杀是否成功
	 */
	public boolean saveSeckill(long secKillId,String userPhone,String md5)throws SeckillCloseException,SeckillRepeatException,SecKillException{
		if(StringUtils.isBlank(md5)||(!md5.equals(getMD5(secKillId)))){
			throw new SecKillException("秒杀数据错误");
		}
		SeckillExample conditions = new SeckillExample();
		Date now = new Date();
		conditions.createCriteria().andSeckillIdEqualTo(secKillId).andStartTimeLessThanOrEqualTo(now)
					.andEndTimeGreaterThanOrEqualTo(now).andNumberGreaterThan(0);
		try {
			int updateCount = seckillMapper.reduceNumber(conditions);
			if(updateCount<=0){
				throw new SeckillCloseException("秒杀已结束");
			}else{
				SuccessKilled successKilled = new SuccessKilled();
				successKilled.setSeckillId(secKillId);
				successKilled.setUserPhone(userPhone);
				successKilled.setState(new Byte("0"));
				try {
					successKilledMapper.insertSelective(successKilled);
				} catch (Exception e) {
					throw new SeckillRepeatException("重复秒杀");
				}
			}
		} catch (SeckillRepeatException e) {
			logger.error(e.getMessage(),e);
			throw new SeckillRepeatException(e.getMessage());
		} catch (SeckillCloseException e) {
			logger.error(e.getMessage(),e);
			throw new SeckillCloseException(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SecKillException(e.getMessage());
		}
		return true;
	}
	
	private String getMD5(long secKillId){
		String md5 = secKillId + "/" + salt;
		return DigestUtils.md5DigestAsHex(md5.getBytes());
	}

}
