package com.test;

import java.io.Serializable;
import java.util.List;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.pojo.User;

public class SpringRedisTemplateTest {

	private ApplicationContext context;
	private RedisTemplate<Serializable, Serializable> template;
	private StringRedisTemplate stringTemplate;

	@Before
	public void before()throws Exception {
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
	}
/*
	@Test
	public void testStringSet() {
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

	@Test
	public void testHMSet() {
		final User user = new User("123", "testUser1");
		template.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection conn) throws DataAccessException {
				byte[] key = template.getStringSerializer().serialize("user" + user.getUsername());
				BoundHashOperations<Serializable, byte[], byte[]> opertions = template.boundHashOps(key);
				opertions.put(template.getStringSerializer().serialize("uid"),
						template.getStringSerializer().serialize(String.valueOf(user.getUsername())));
				opertions.put(template.getStringSerializer().serialize("name"),
						template.getStringSerializer().serialize(user.getPassword()));
				conn.hMSet(key, opertions.entries());
				return null;
			}

		});

	}

	@Test
	public void testHMGet() {
		final String uid = "123";
		User u = template.execute(new RedisCallback<User>() {
			@Override
			public User doInRedis(RedisConnection conn) throws DataAccessException {
				byte[] key = template.getStringSerializer().serialize("user" + uid);
				if (conn.exists(key)) {
					List<byte[]> value = conn.hMGet(key, template.getStringSerializer().serialize("uid"),
							template.getStringSerializer().serialize("name"));
					User user = new User();
					String uid = template.getStringSerializer().deserialize(value.get(0));
					user.setUsername(uid);
					String name = template.getStringSerializer().deserialize(value.get(1));
					user.setPassword(name);
					return user;
				}
				return null;
			}
		});
		assertEquals("testUser1", u.getPassword());
		assertEquals("123", u.getUsername());
	}

	@Test
	public void testHDel() {
		final int uid = 123;
		template.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection conn) throws DataAccessException {
				conn.del(template.getStringSerializer().serialize("user" + uid));
				return null;
			}
		});
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
	public void testListIndex() {
		String value = stringTemplate.opsForList().index("queue", 3);
		assertEquals("u3", value);
	}

	@Test
	public void range() {
		List<String> list = stringTemplate.opsForList().range("queue", 3, 5);
		boolean result1 = list.contains("u3");
		assertEquals(true, result1);

		boolean result2 = list.contains("u1");
		assertEquals(false, result2);
	}

	@Test
	public void trim() {
		List<String> list = stringTemplate.opsForList().range("queue", 3, 5);
		stringTemplate.opsForList().trim("queue", 3, 5);
		boolean result3 = list.contains("u1");		
		assertEquals(false, result3);
	}*/

	@Test
	public void set() {
		stringTemplate.opsForList().set("queue", 4, "ux4");
		List<String> list = stringTemplate.opsForList().range("queue", 3, 5);
		boolean result4 = list.contains("ux4");
		assertEquals(true, result4);
	}

	@Test
	public void remove() {
		stringTemplate.opsForList().remove("queue", 4, "u4");
		String value = stringTemplate.opsForList().index("queue", 9);
		assertEquals(null, value);

	}

}
