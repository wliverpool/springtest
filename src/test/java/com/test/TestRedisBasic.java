package com.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.util.JedisUtil;

import redis.clients.jedis.Jedis;

/**
 * * 在不同的线程中使用相同的Jedis实例会发生奇怪的错误。但是创建太多的实现也不好因为这意味着会建立很多sokcet连接， *
 * 也会导致奇怪的错误发生。单一Jedis实例不是线程安全的。为了避免这些问题，可以使用JedisPool, *
 * JedisPool是一个线程安全的网络连接池。可以用JedisPool创建一些可靠Jedis实例，可以从池中拿到Jedis的实例。 *
 * 这种方式可以解决那些问题并且会实现高效的性能
 * 
 * redis中基本数据类型
 */
public class TestRedisBasic {

	@Test
	public void testKey() {
		JedisUtil jedisTool = JedisUtil.getInstance();
		Jedis jedis = jedisTool.getJedis("192.168.122.129", 6379, "liverpool");
		try {

			System.out.println("===========清空当前数据库数据================");
			String info = jedis.flushDB();
			System.out.println("===========" + info + "================");

			System.out.println("===========输出内容 ================");
			info = jedis.echo("foo");
			System.out.println("===========" + info + "================");

			System.out.println("===========判断key是否存在================");
			boolean exist = jedis.exists("foo");
			System.out.println("===========" + exist + "================");
			jedis.set("foo", "values");
			exist = jedis.exists("foo");
			System.out.println("===========" + exist + "================");

			System.out.println("===========通过通配符,获取key的数量================");
			Set<String> num = jedis.keys("*");
			System.out.println("===========" + num + "================");

			System.out.println("===========获取key有效时间================");
			jedis.setex("existtime", 10, "10");
			long time = jedis.ttl("existtime");
			System.out.println("===========" + time + "================");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO: handle exception
			}
			time = jedis.ttl("existtime");
			System.out.println("===========" + time + "================");

			System.out.println("===========去除key有效时间================");
			jedis.persist("existtime");
			time = jedis.ttl("existtime");
			System.out.println("===========" + time + "================");

			System.out.println("===========重命名key================");
			jedis.rename("existtime", "newname");
			String value = jedis.get("existtime");
			System.out.println("===========" + value + "================");
			value = jedis.get("newname");
			System.out.println("===========" + value + "================");

			System.out.println("===========排序================");
			jedis.rpush("sort", "45.6");
			jedis.rpush("sort", "65.7");
			jedis.rpush("sort", "32.5");
			jedis.rpush("sort", "0.5");
			jedis.sort("sort");
			List<String> values = jedis.lrange("sort", 0, -1);
			for (String str : values) {
				System.out.println("===========" + str + "================");
			}

			System.out.println("===========获取key的类型================");
			String type = jedis.type("sort");
			System.out.println("===========" + type + "================");

			System.out.println("===========移动key去其他数据库================");
			jedis.move("sort", 1);
			jedis.select(1);
			values = jedis.lrange("sort", 0, -1);
			for (String str : values) {
				System.out.println("===========" + str + "================");
			}

			System.out.println("===========选择数据库,最大index为15================");
			jedis.select(0);
			values = jedis.lrange("sort", 0, -1);
			for (String str : values) {
				System.out.println("===========" + str + "================");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedisTool.closeJedis(jedis);
		}
	}

	@Test
	public void testString() {
		JedisUtil jedisTool = JedisUtil.getInstance();
		Jedis jedis = jedisTool.getJedis("192.168.122.129", 6379, "liverpool");
		try {

			System.out.println("===========清空当前数据库数据================");
			String info = jedis.flushDB();
			System.out.println("===========" + info + "================");

			System.out.println("===========向key-->stringName中放入value-->minxr================");
			jedis.set("stringName", "minxr");
			String ss = jedis.get("stringName");
			System.out.println("===========" + ss + "================");

			System.out.println("===========将jintao追加到hellName中================");
			jedis.append("stringName", "jintao");
			ss = jedis.get("stringName");
			System.out.println("===========" + ss + "================");

			System.out.println("===========截取value的值================");
			ss = jedis.getrange("stringName", 1, 4);
			System.out.println("===========" + ss + "================");

			System.out.println("===========获取并且修改值================");
			ss = jedis.getSet("stringName", "update");
			System.out.println("===========" + ss + "================");
			ss = jedis.get("stringName");
			System.out.println("===========" + ss + "================");

			System.out.println("===========只有key不存在时存储================");
			long result = jedis.setnx("foo", "exits");
			ss = jedis.get("foo");
			System.out.println("===========" + ss + "================");
			System.out.println("===========" + result + "================");
			result = jedis.setnx("foo", "exits");
			System.out.println("===========" + result + "================");

			System.out.println("===========覆盖原有数据================");
			jedis.set("stringName", "jintao");
			ss = jedis.get("stringName");
			System.out.println("===========" + ss + "================");

			System.out.println("===========设置key的有效期，并存储数据================");
			jedis.setex("effective", 20, "time");
			System.out.println(jedis.get("effective"));
			try {
				Thread.sleep(21000);
			} catch (InterruptedException e) {

			}
			System.out.println(jedis.get("effective"));

			System.out.println("===========删除key================");
			jedis.del("stringName");
			ss = jedis.get("stringName");
			System.out.println("===========" + ss + "================");

			System.out.println("===========设置多个值================");
			jedis.msetnx("string1", "value1", "string2", "value2", "string3", "value3");
			List<String> stringList = jedis.mget("string1", "string2", "string3");
			for (String str : stringList) {
				System.out.println("===========" + str + "================");
			}

			System.out.println("===========设置值自增================");
			jedis.incr("test");
			ss = jedis.get("test");
			System.out.println("===========" + ss + "================");

			System.out.println("===========设置值增加多少================");
			jedis.incrBy("test", 4);
			ss = jedis.get("test");
			System.out.println("===========" + ss + "================");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedisTool.closeJedis(jedis);
		}
	}

	@Test
	public void testList() {
		JedisUtil jedisTool = JedisUtil.getInstance();
		Jedis jedis = jedisTool.getJedis("192.168.122.129", 6379, "liverpool");
		try {

			System.out.println("===========清空当前数据库数据================");
			String info = jedis.flushDB();
			System.out.println("===========" + info + "================");

			System.out.println("===========从list尾部添加数据================");
			jedis.rpush("messages", "Hello how ware you?");
			jedis.rpush("messages", "Fine thanks.I'm having fun with redis.");
			jedis.rpush("messages", "I should look into this NOSQL thing ASAP");

			System.out.println("===========从list头部取出数据================");
			List<String> values = jedis.lrange("messages", 1, 2);
			for (String str : values) {
				System.out.println("===========" + str + "================");
			}

			System.out.println("===========从list头部添加数据================");
			jedis.lpush("lists", "vector");
			jedis.lpush("lists", "ArrayList");
			jedis.lpush("lists", "LinkedList");
			values = jedis.lrange("lists", 0, -1);
			for (String str : values) {
				System.out.println("===========" + str + "================");
			}

			System.out.println("===========list长度================");
			long length = jedis.llen("lists");
			System.out.println("===========" + length + "================");

			System.out.println("===========从list头部开始算起,修改指定下标的值================");
			jedis.lset("lists", 0, "NodeList");
			values = jedis.lrange("lists", 0, -1);
			for (String str : values) {
				System.out.println("===========" + str + "================");
			}

			System.out.println("===========从list头部开始算起,获取指定下标的值================");
			String value = jedis.lindex("lists", 1);
			System.out.println("===========" + value + "================");

			System.out.println("===========从list头部开始算起,删除list中指定个数的值为XXXX的数据================");
			jedis.lrem("messages", 1, "I should look into this NOSQL thing ASAP");
			values = jedis.lrange("messages", 0, -1);
			for (String str : values) {
				System.out.println("===========" + str + "================");
			}

			System.out.println("===========从list头部开始算起,删除list中下标范围以外的值================");
			jedis.ltrim("lists", 1, 2);
			values = jedis.lrange("lists", 0, -1);
			for (String str : values) {
				System.out.println("===========" + str + "================");
			}

			System.out.println("===========从list头部出栈一个数据================");
			String message = jedis.lpop("messages");
			System.out.println("===========" + message + "================");
			values = jedis.lrange("messages", 0, -1);
			for (String str : values) {
				System.out.println("===========" + str + "================");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedisTool.closeJedis(jedis);
		}
	}

	@Test
	public void testSet() {
		JedisUtil jedisTool = JedisUtil.getInstance();
		Jedis jedis = jedisTool.getJedis("192.168.122.129", 6379, "liverpool");
		try {

			System.out.println("===========清空当前数据库数据================");
			String info = jedis.flushDB();
			System.out.println("===========" + info + "================");

			System.out.println("===========集合中添加值================");
			jedis.sadd("myset", "1");
			jedis.sadd("myset", "2");
			jedis.sadd("myset", "3");
			jedis.sadd("myset", "4");

			jedis.sadd("myset2", "6");
			jedis.sadd("myset2", "5");
			jedis.sadd("myset2", "4");
			jedis.sadd("myset2", "3");
			jedis.sadd("myset2", "2");

			System.out.println("===========获取集合中的所有值================");
			Set<String> setValues = jedis.smembers("myset");
			for (String str : setValues) {
				System.out.println("===========" + str + "================");
			}

			System.out.println("===========从集合中删除指定值================");
			jedis.srem("myset", "4");
			setValues = jedis.smembers("myset");
			for (String str : setValues) {
				System.out.println("===========" + str + "================");
			}

			System.out.println("===========获取集合中元素个数================");
			long number = jedis.scard("myset");
			System.out.println("===========" + number + "================");

			System.out.println("===========判断集合中是否包含某个元素================");
			boolean flag = jedis.sismember("myset", "4");
			System.out.println("===========" + flag + "================");
			flag = jedis.sismember("myset", "3");
			System.out.println("===========" + flag + "================");

			System.out.println("===========从集合中随机出栈一个元素================");
			String ss = jedis.spop("myset2");
			System.out.println("===========" + ss + "================");
			setValues = jedis.smembers("myset2");
			for (String str : setValues) {
				System.out.println("===========" + str + "================");
			}

			System.out.println("===========获取集合的交集================");
			Set<String> inter = jedis.sinter("myset", "myset2");
			for (String str : inter) {
				System.out.println("===========" + str + "================");
			}

			System.out.println("===========获取集合的交集,并存储到指定集合中================");
			jedis.sinterstore("myset3", "myset", "myset2");
			setValues = jedis.smembers("myset3");
			for (String str : setValues) {
				System.out.println("===========" + str + "================");
			}

			System.out.println("===========获取集合的并集================");
			Set<String> union = jedis.sunion("myset", "myset2");
			for (String str : union) {
				System.out.println("===========" + str + "================");
			}

			System.out.println("===========获取集合的并集,并存储到指定集合中================");
			jedis.sunionstore("myset4", "myset", "myset2");
			setValues = jedis.smembers("myset4");
			for (String str : setValues) {
				System.out.println("===========" + str + "================");
			}

			System.out.println("===========获取集合的差集================");
			Set<String> diff = jedis.sdiff("myset", "myset2");
			for (String str : diff) {
				System.out.println("===========" + str + "================");
			}

			System.out.println("===========获取集合的差集,并存储到指定集合中================");
			jedis.sdiffstore("myset5", "myset", "myset2");
			setValues = jedis.smembers("myset5");
			for (String str : setValues) {
				System.out.println("===========" + str + "================");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedisTool.closeJedis(jedis);
		}
	}

	@Test
	public void testSortedSet() {
		JedisUtil jedisTool = JedisUtil.getInstance();
		Jedis jedis = jedisTool.getJedis("192.168.122.129", 6379, "liverpool");
		try {

			System.out.println("===========清空当前数据库数据================");
			String info = jedis.flushDB();
			System.out.println("===========" + info + "================");

			System.out.println("===========添加有序集合================");
			jedis.zadd("hackers", 1940, "Alan Kay");
			jedis.zadd("hackers", 1953, "Richard Stallman");
			jedis.zadd("hackers", 1965, "Yukihiro Matsumoto");
			jedis.zadd("hackers", 1916, "Claude Shannon");
			jedis.zadd("hackers", 1969, "Linus Torvalds");
			jedis.zadd("hackers", 1912, "Alan Turing");

			System.out.println("===========正序按照下标,显示有序集合中元素================");
			Set<String> hackers = jedis.zrange("hackers", 0, -1);
			for (String str : hackers) {
				System.out.println("===========" + str + "================");
			}

			System.out.println("===========倒序按照下标,显示有序集合中元素================");
			hackers = jedis.zrevrange("hackers", 0, -1);
			for (String str : hackers) {
				System.out.println("===========" + str + "================");
			}

			System.out.println("===========显示元素个数================");
			long number = jedis.zcard("hackers");
			System.out.println("===========" + number + "================");

			System.out.println("===========获取给定元素的score================");
			double score = jedis.zscore("hackers", "Claude Shannon");
			System.out.println("===========" + score + "================");

			System.out.println("===========获取有序集合中score在区间中的元素数量================");
			long count = jedis.zcount("hackers", 1953, 1969);
			System.out.println("===========" + count + "================");

			System.out.println("===========获取有序集合中score在区间中的元素================");
			hackers = jedis.zrangeByScore("hackers", 0, -1);
			for (String str : hackers) {
				System.out.println("===========" + str + "================");
			}

			System.out.println("===========删除有序集合中score在区间中的元素================");
			long delete = jedis.zremrangeByScore("hackers", 1935, 1955);
			System.out.println("===========" + delete + "================");
			hackers = jedis.zrange("hackers", 0, -1);
			for (String str : hackers) {
				System.out.println("===========" + str + "================");
			}

			System.out.println("===========删除有序集合中指定值的元素================");
			delete = jedis.zrem("hackers", "Claude Shannon");
			System.out.println("===========" + delete + "================");
			hackers = jedis.zrange("hackers", 0, -1);
			for (String str : hackers) {
				System.out.println("===========" + str + "================");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedisTool.closeJedis(jedis);
		}
	}

	@Test
	public void testHash() {
		JedisUtil jedisTool = JedisUtil.getInstance();
		Jedis jedis = jedisTool.getJedis("192.168.122.129", 6379, "liverpool");
		try {

			System.out.println("===========清空当前数据库数据================");
			String info = jedis.flushDB();
			System.out.println("===========" + info + "================");

			System.out.println("===========设置hash================");
			Map<String, String> pairs = new HashMap<String, String>();
			pairs.put("name", "Akshi");
			pairs.put("age", "2");
			pairs.put("sex", "Female");
			pairs.put("number", "6");
			pairs.put("height", "1.68");
			jedis.hmset("kid", pairs);

			System.out.println("===========获取hash中key的值================");
			String ss = jedis.hget("kid", "number");
			System.out.println("===========" + ss + "================");

			System.out.println("===========设置hash中key对应的值增加多少================");
			jedis.hincrBy("kid", "age", 4);
			ss = jedis.hget("kid", "age");
			System.out.println("===========" + ss + "================");

			System.out.println("===========获取hash中多个key的值================");
			List<String> values = jedis.hmget("kid", "name", "age");
			for (String str : values) {
				System.out.println("===========" + str + "================");
			}

			System.out.println("===========hash长度================");
			long length = jedis.hlen("kid");
			System.out.println("===========" + length + "================");

			System.out.println("===========hash是否存在================");
			boolean exists = jedis.exists("kid");
			System.out.println("===========" + exists + "================");

			System.out.println("===========hash中包含的多少key================");
			Set<String> keys = jedis.hkeys("kids");
			for (String str : keys) {
				System.out.println("===========" + str + "================");
			}

			System.out.println("===========hash中包含的多少value================");
			values = jedis.hvals("kid");
			for (String str : values) {
				System.out.println("===========" + str + "================");
			}

			System.out.println("===========删除hash中的key的值================");
			jedis.hdel("kid", "heigth");
			keys = jedis.hkeys("kids");
			for (String str : keys) {
				System.out.println("===========" + str + ":" + jedis.hget("kid", str) + "================");
			}

			System.out.println("===========获取hash全部key和值================");
			pairs = jedis.hgetAll("kid");
			for (String key : pairs.keySet()) {
				System.out.println("===========" + key + ":" + pairs.get(key) + "================");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedisTool.closeJedis(jedis);
		}
	}

}
