package com.test;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dao.redis.RedisDao;
import com.util.RedisBloomFilter;

public class RedisBloomFilterTests {
	
	private static final int NUM_APPROX_ELEMENTS = 3000;
    private static final double FPP = 0.03;
    private static final int DAY_SEC = 60 * 60 * 24;
    private static RedisDao redisDao;
    private static RedisBloomFilter redisBloomFilter;
    
    @BeforeClass
    public static void beforeClass() throws Exception{
    	ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    	redisDao = context.getBean(RedisDao.class);
    	redisBloomFilter = new RedisBloomFilter(NUM_APPROX_ELEMENTS, FPP, redisDao);
    }
    
    @AfterClass
    public static void afterClass() throws Exception {
    	
    }
    
    @Test
    public void testInsert() throws Exception {
        redisBloomFilter.insert("topic_read:8839540:20190609", "76930242", DAY_SEC);
        redisBloomFilter.insert("topic_read:8839540:20190609", "76930243", DAY_SEC);
        redisBloomFilter.insert("topic_read:8839540:20190609", "76930244", DAY_SEC);
        redisBloomFilter.insert("topic_read:8839540:20190609", "76930245", DAY_SEC);
        redisBloomFilter.insert("topic_read:8839540:20190609", "76930246", DAY_SEC);
    }
    
    @Test
    public void testMayExist() throws Exception {
        System.out.println(redisBloomFilter.mayExist("topic_read:8839540:20190609", "76930242"));
        System.out.println(redisBloomFilter.mayExist("topic_read:8839540:20190609", "76930244"));
        System.out.println(redisBloomFilter.mayExist("topic_read:8839540:20190609", "76930246"));
        System.out.println(redisBloomFilter.mayExist("topic_read:8839540:20190609", "76930248"));
    }

}
