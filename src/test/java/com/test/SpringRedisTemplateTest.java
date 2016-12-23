package com.test;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.pojo.User;

public class SpringRedisTemplateTest {

	private ClassPathXmlApplicationContext context;
	private RedisTemplate<Serializable, Serializable> template;
	private StringRedisTemplate stringTemplate;

	@Before
	public void before() throws Exception {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		template = (RedisTemplate<Serializable, Serializable>) context.getBean("redisTemplate");
		stringTemplate = (StringRedisTemplate) context.getBean("redisStringTemplate");
		for (int i = 0; i < 10; i++) {
			String uid = "u" + i;
			stringTemplate.opsForList().rightPush("queue", uid);
		}
	}

	@After
	public void after() {
		long length = stringTemplate.opsForList().size("queue");
		for (long i = 0; i < length; i++) {
			stringTemplate.opsForList().leftPop("queue");
		}
		context.close();
	}

	@Test
	public void testStringSet() {
		stringTemplate.opsForValue().set("tempkey", "tempvalue");
	}

	@Test
	public void testSetList() {
		stringTemplate.opsForList().set("queue", 4, "ux4");
		List<String> list = stringTemplate.opsForList().range("queue", 3, 5);
		boolean result4 = list.contains("ux4");
		assertEquals(true, result4);
	}

	/**
	 * 在连接池环境中，需要借助sessionCallback来绑定connection
	 */
	@Test
	public void testSpringRedisTransactionInPool() {
		SessionCallback<Object> sessionCallback = new SessionCallback<Object>() {
			@Override
			public Object execute(RedisOperations operations) throws DataAccessException {
				operations.multi();
				operations.delete("test");
				operations.opsForValue().set("test", "2");
				Object val = operations.exec();
				return val;
			}
		};
		stringTemplate.execute(sessionCallback);
	}
	
	/**
	 * 非连接池环境下
	 */
	/*public void testSpringRedisTransactionNotPool(){
		User suser = new User("zhangsan","affdasfa");
		template.watch("user:" + suser.getUsername());
		template.multi();
		ValueOperations<Serializable, Serializable> tvo = template.opsForValue();
		tvo.set("user:" + suser.getUsername(), suser);
		template.exec();
	}*/

	@Test
	public void remove() {
		stringTemplate.opsForList().remove("queue", 4, "u4");
		String value = stringTemplate.opsForList().index("queue", 9);
		assertEquals(null, value);
	}

	@Test
	public void testListIndex() {
		String value = stringTemplate.opsForList().index("queue", 3);
		assertEquals("u3", value);
	}

	@Test
	public void testRangeList() {
		List<String> list = stringTemplate.opsForList().range("queue", 3, 5);
		boolean result1 = list.contains("u3");
		assertEquals(true, result1);

		boolean result2 = list.contains("u1");
		assertEquals(false, result2);
	}

	@Test
	public void testTrimList() {
		List<String> list = stringTemplate.opsForList().range("queue", 3, 5);
		stringTemplate.opsForList().trim("queue", 3, 5);
		boolean result3 = list.contains("u1");
		assertEquals(false, result3);
	}

	@Test
	public void testListPushPop() {
		String key = "stack";
		int len = 5;
		for (int i = 0; i < len; i++) {
			String uid = "u" + System.currentTimeMillis();
			System.out.println(uid);
			stringTemplate.opsForList().leftPush(key, uid);
		}

		long length = stringTemplate.opsForList().size(key);
		assertEquals(len, length);

		// ------------POP---------------
		System.out.println("------------POP---------------");
		for (long i = 0; i < length; i++) {
			String uid = stringTemplate.opsForList().leftPop(key);
			System.out.println(uid);
		}
	}

	@Test
	public void testStringSetUseCallback() {
		final User user = new User("123", "testUser1");
		template.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection conn) throws DataAccessException {
				conn.set(template.getStringSerializer().serialize("user.uid." + user.getUsername()),
						template.getStringSerializer().serialize(user.getPassword()));
				return null;
			}
		});
	}

	@Test
	public void testHashSet() {
		final User user = new User("user123", "testUser1");
		template.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection conn) throws DataAccessException {
				byte[] key = template.getStringSerializer().serialize("user." + user.getUsername());
				Map<byte[], byte[]> paramMap = new HashMap<>();
				try {
					paramMap.put("username".getBytes(), user.getUsername().getBytes("UTF-8"));
					paramMap.put("password".getBytes(), user.getPassword().getBytes("UTF-8"));
				} catch (UnsupportedEncodingException e) {
					// TODO: handle exception
				}
				conn.hMSet(key, paramMap);
				return null;
			}

		});
	}

	@Test
	public void testHashMGet() {
		final String uid = "user123";
		User u = template.execute(new RedisCallback<User>() {
			@Override
			public User doInRedis(RedisConnection conn) throws DataAccessException {
				byte[] key = template.getStringSerializer().serialize("user." + uid);
				if (conn.exists(key)) {
					List<byte[]> value = null;
					try {
						value = conn.hMGet(key, "username".getBytes("UTF-8"),"password".getBytes("UTF-8"));
					} catch (UnsupportedEncodingException e) {
						// TODO: handle exception
					}		
					if(null!=value&&value.size()>2){
						User user = new User();
						String username = template.getStringSerializer().deserialize(value.get(0));
						user.setUsername(username);
						String password = template.getStringSerializer().deserialize(value.get(1));
						user.setPassword(password);
						return user;
					}
				}
				return null;
			}
		});
		assertEquals("testUser1", u.getPassword());
		assertEquals("user123", u.getUsername());
	}

	@Test
	public void testHashDel() {
		final String uid = "user123";
		template.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection conn) throws DataAccessException {
				conn.del(template.getStringSerializer().serialize("user." + uid));
				return null;
			}
		});
	}

	@Test
	public void testStringGet() {
		final String uid = "123";
		User u = template.execute(new RedisCallback<User>() {
			@Override
			public User doInRedis(RedisConnection conn) throws DataAccessException {
				byte[] keys = template.getStringSerializer().serialize("user.uid." + uid);
				if (conn.exists(keys)) {
					byte[] value = conn.get(keys);
					String name = template.getStringSerializer().deserialize(value);
					User user = new User(uid, name);
					return user;
				}
				return null;
			}
		});
		assertEquals("testUser1", u.getPassword());
	}

	@Test
	public void testStringDel() {
		final int uid = 123;
		template.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection conn) throws DataAccessException {
				conn.del(template.getStringSerializer().serialize("user.uid." + uid));
				return null;
			}
		});
	}
	
	/**
	 * 多次重复操作使用pipeline,重复操作用与不用pipeline效率差很多
	 */
	@Test
	public void testPipeline(){
		final byte[] rawKey = template.getStringSerializer().serialize("user_total");
		//pipeline
		RedisCallback<List<Object>> pipelineCallback = new RedisCallback<List<Object>>() {
			@Override
			public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
				connection.openPipeline();
				for(int i =0; i<10000; i++){
					connection.incr(rawKey);

	            }
				return connection.closePipeline();
			}
			
		};
		long start = System.currentTimeMillis();
		List<Object> results = (List<Object>)template.execute(pipelineCallback);
		for(Object item : results){
			System.out.println(item.toString());
		}
		long end = System.currentTimeMillis();
		System.out.println("total time:" + (end-start));
		//total time:624
	}
	
	@Test
	public void testWithOutPipeline(){
		final byte[] rawKey = template.getStringSerializer().serialize("user_total_2");
		//pipeline
		RedisCallback<List<Object>> callback = new RedisCallback<List<Object>>() {
			@Override
			public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
				for(int i =0; i<10000; i++){
					connection.incr(rawKey);

	            }
				return null;
			}
		};
		long start = System.currentTimeMillis();
		template.execute(callback);
		long end = System.currentTimeMillis();
		System.out.println("total time:" + (end-start));
		//total time:2526
	}

}
