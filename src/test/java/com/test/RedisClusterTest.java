package com.test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.JedisCluster;

public class RedisClusterTest {

	private ApplicationContext context;
	private JedisCluster jedisCluster;

	@Before
	public void before() throws Exception {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		jedisCluster = (JedisCluster) context.getBean("jedisCluster");
		/*
		 * Map<String, JedisPool> nodes = jedisCluster.getClusterNodes();
		 * System.out.println(nodes.size()); for(String key : nodes.keySet()){
		 * JedisPool pool = nodes.get(key); System.out.println(pool); }
		 */
	}

	/*
	 * @Test public void testStringSet(){ final User user = new User(123,
	 * "testUser1"); jedisCluster.set("user"+user.getUid(), user.getName()); }
	 * 
	 * @Test public void testStringGet(){ final User user = new User(123,
	 * "testUser1"); String name = jedisCluster.get("user"+user.getUid());
	 * assertEquals("testUser1", name); }
	 */

	@Test
	public void testString() {

		System.out.println("===========向key-->stringName中放入value-->minxr================");
		jedisCluster.set("stringName", "minxr");
		String ss = jedisCluster.get("stringName");
		assertEquals("minxr", ss);

		System.out.println("===========将jintao追加到hellName中================");
		jedisCluster.append("stringName", "jintao");
		ss = jedisCluster.get("stringName");
		assertEquals("minxrjintao", ss);

		System.out.println("===========截取value的值================");
		ss = jedisCluster.getrange("stringName", 1, 4);
		System.out.println("===========" + ss + "================");

		System.out.println("===========获取并且修改值================");
		ss = jedisCluster.getSet("stringName", "update");
		System.out.println("===========" + ss + "================");
		ss = jedisCluster.get("stringName");
		System.out.println("===========" + ss + "================");

		System.out.println("===========只有key不存在时存储================");
		long result = jedisCluster.setnx("foo", "exits");
		ss = jedisCluster.get("foo");
		System.out.println("===========" + ss + "================");
		System.out.println("===========" + result + "================");
		result = jedisCluster.setnx("foo", "exits");
		System.out.println("===========" + result + "================");

		System.out.println("===========覆盖原有数据================");
		jedisCluster.set("stringName", "jintao");
		ss = jedisCluster.get("stringName");
		System.out.println("===========" + ss + "================");

		System.out.println("===========设置key的有效期，并存储数据================");
		jedisCluster.setex("effective", 20, "time");
		System.out.println(jedisCluster.get("effective"));
		try {
			Thread.sleep(21000);
		} catch (InterruptedException e) {

		}
		System.out.println(jedisCluster.get("effective"));

		//System.out.println("===========删除key================");
		//jedisCluster.del("stringName");
		//ss = jedisCluster.get("stringName");
		//System.out.println("===========" + ss + "================");

		System.out.println("===========设置值自增================");
		jedisCluster.incr("test");
		ss = jedisCluster.get("test");
		System.out.println("===========" + ss + "================");

		System.out.println("===========设置值增加多少================");
		jedisCluster.incrBy("test", 4);
		ss = jedisCluster.get("test");
		System.out.println("===========" + ss + "================");
	}

}
