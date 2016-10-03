package com.test;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.util.JedisUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.SortingParams;

public class TestRedisEfficient {

	@Test
	public void testUnUsePipeLine() {

		long start = new Date().getTime();
		JedisUtil jedisTool = JedisUtil.getInstance();
		Jedis jedis = jedisTool.getJedis("192.168.122.129", 6379, "liverpool");
		try {

			System.out.println("===========清空当前数据库数据================");
			String info = jedis.flushDB();
			System.out.println("===========" + info + "================");

			for (int i = 0; i < 10000; i++) {
				jedis.set("age" + i, i + "");
				// System.out.println(jedis.get("age"+i));
			}

			long end = new Date().getTime();

			System.out.println("unuse pipeline cost:" + (end - start) + "ms");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedisTool.closeJedis(jedis);
		}

	}

	@Test
	public void testUsePipeLine() {

		long start = new Date().getTime();
		JedisUtil jedisTool = JedisUtil.getInstance();
		Jedis jedis = jedisTool.getJedis("192.168.122.129", 6379, "liverpool");
		try {

			System.out.println("===========清空当前数据库数据================");
			String info = jedis.flushDB();
			System.out.println("===========" + info + "================");

			Pipeline p = jedis.pipelined();
			for (int i = 0; i < 10000; i++) {
				p.set("age" + i, i + "");
				// System.out.println(p.get("age"+i));
			}
			p.sync();

			long end = new Date().getTime();

			System.out.println("use pipeline cost:" + (end - start) + "ms");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedisTool.closeJedis(jedis);
		}

	}

	/**
	 * hash数据类型和list数据类型联合使用的例子
	 */
	@Test
	public void testListHashUsed() {

		JedisUtil jedisTool = JedisUtil.getInstance();
		Jedis jedis = jedisTool.getJedis("192.168.122.129", 6379, "liverpool");
		try {

			System.out.println("===========清空当前数据库数据================");
			String info = jedis.flushDB();
			System.out.println("===========" + info + "================");

			// 利用incr获取一个自增数据,类似关系型数据库中auto_increment或者identity
			long infoId = jedis.incr("info.next.id");
			jedis.hset("info:" + infoId, "title", "太阳能是绿色能源1");
			jedis.hset("info:" + infoId, "url", "http://javacreazyer.iteye.com/1");
			jedis.lpush("infoList", String.valueOf(infoId));

			infoId = jedis.incr("info.next.id");
			jedis.hset("info:" + infoId, "title", "太阳能是绿色能源2");
			jedis.hset("info:" + infoId, "url", "http://javacreazyer.iteye.com/2");
			jedis.lpush("infoList", String.valueOf(infoId));

			List<String> ids = jedis.lrange("infoList", 0, -1);
			for (String str : ids) {
				System.out.println(str);
				Map<String, String> infoMap = jedis.hgetAll("info:" + str);
				for (String key : infoMap.keySet()) {
					System.out.println("---------" + key + ":" + infoMap.get(key) + "------------");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedisTool.closeJedis(jedis);
		}

	}

	/**
	 * 使用lrange对数据进行分页
	 */
	@Test
	public void testQueryPage() {

		JedisUtil jedisTool = JedisUtil.getInstance();
		Jedis jedis = jedisTool.getJedis("192.168.122.129", 6379, "liverpool");
		try {

			System.out.println("===========清空当前数据库数据================");
			String info = jedis.flushDB();
			System.out.println("===========" + info + "================");

			System.out.println("===========分页获取数据================");
			int pageNo = 2;
			int pageSize = 6;
			for (int i = 1; i <= 30; i++) {
				jedis.rpush("a", i + "");
			}
			// 因为redis中list元素位置基数是0
			int start = pageSize * (pageNo - 1);
			int end = start + pageSize - 1;
			// 从start算起，start算一个元素，到结束那个元素
			List<String> results = jedis.lrange("a", start, end);
			for (String str : results) {
				System.out.println(str);
			}

			System.out.println("===========mget获取数据================");
			jedis.rpush("ids", "aa");
			jedis.rpush("ids", "bb");
			jedis.rpush("ids", "cc");
			List<String> ids = jedis.lrange("ids", 0, -1);
			jedis.set("aa", "{'name':'zhoujie','age':20}");
			jedis.set("bb", "{'name':'yilin','age':28}");
			jedis.set("cc", "{'name':'lucy','age':21}");
			List<String> list = jedis.mget(ids.toArray(new String[ids.size()]));
			for (String str : list) {
				System.out.println(str);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedisTool.closeJedis(jedis);
		}

	}

	/**
	 * 时间复杂度： O(N+M*log(M))， N 为要排序的列表或集合内的元素数量， M 为要返回的元素数量。 如果只是使用 SORT 命令的
	 * GET 选项获取数据而没有进行排序，时间复杂度 O(N)。
	 */
	@Test
	public void testListSort() {

		JedisUtil jedisTool = JedisUtil.getInstance();
		Jedis jedis = jedisTool.getJedis("192.168.122.129", 6379, "liverpool");
		try {

			System.out.println("===========清空当前数据库数据================");
			String info = jedis.flushDB();
			System.out.println("===========" + info + "================");

			jedis.lpush("mylist", "1");
			jedis.lpush("mylist", "4");
			jedis.lpush("mylist", "6");
			jedis.lpush("mylist", "3");
			jedis.lpush("mylist", "0");

			SortingParams sortingParameters = new SortingParams();
			sortingParameters.desc();
			// 当数据集中保存的是字符串值时，你可以用 ALPHA
			// sortingParameters.alpha();进行排序。
			// 可用于分页查询,设置排序返回的元素个数
			sortingParameters.limit(0, 2);
			List<String> list = jedis.sort("mylist", sortingParameters);
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedisTool.closeJedis(jedis);
		}

	}

	/**
	 * String类型结合Set类型进行排序
	 */
	@Test
	public void testStringSetSort() {

		JedisUtil jedisTool = JedisUtil.getInstance();
		Jedis jedis = jedisTool.getJedis("192.168.122.129", 6379, "liverpool");
		try {

			System.out.println("===========清空当前数据库数据================");
			String info = jedis.flushDB();
			System.out.println("===========" + info + "================");
			// tom的好友列表
			jedis.sadd("tom:friend:list", "123");
			jedis.sadd("tom:friend:list", "456");
			jedis.sadd("tom:friend:list", "789");
			jedis.sadd("tom:friend:list", "101");
			// 好友对应的成绩
			jedis.set("score:uid:123", "1000");
			jedis.set("score:uid:456", "6000");
			jedis.set("score:uid:789", "100");
			jedis.set("score:uid:101", "5999");
			// 好友的详细信息
			jedis.set("uid:123", "{'uid':123,'name':'lucy'}");
			jedis.set("uid:456", "{'uid':456,'name':'jack'}");
			jedis.set("uid:789", "{'uid':789,'name':'jay'}");
			jedis.set("uid:101", "{'uid':101,'name':'jolin'}");
			// 设置排序参数
			SortingParams sortingParameters = new SortingParams();
			// 降序
			sortingParameters.desc();
			// 排序的结果列表里面显示的set数据前缀
			sortingParameters.get("uid:*");
			// sortingParameters.get("score:uid:*");
			// 根据好友成绩排序
			sortingParameters.by("score:uid:*");

			List<String> result = jedis.sort("tom:friend:list", sortingParameters);
			for (String item : result) {
				System.out.println("item..." + item);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedisTool.closeJedis(jedis);
		}

	}

}
