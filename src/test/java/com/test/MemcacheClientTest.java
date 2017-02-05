package com.test;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.service.QueryService;
import com.util.MemcachedUtils;

public class MemcacheClientTest {
	
	private ClassPathXmlApplicationContext context;
	private QueryService queryService;
	//java并发中的计时器,它允许一个或多个线程一直等待，直到其他线程的操作执行完后再执行
	private CountDownLatch latch = new CountDownLatch(1000);
	//用于执行计数和调用queryService查询的线程
	private class ExecuteThread implements Runnable{
		public void run(){
			try {
				//等待计时器的值为0
				latch.await();
			} catch (Exception e) {
				e.printStackTrace();
			}
			//当计时到0之后所有线程都会执行
			Calendar time = Calendar.getInstance();
			time.add(Calendar.MINUTE, 3);
			Map<String, Object> result = queryService.query("testQuery", time.getTime()); 
			if(result==null){
				System.out.println("=========查询失败 ===========");
			}else{
				System.out.println(result.get("name"));
				System.out.println(result.get("age"));
			}
		}
	}
	
	@Before
	public void before(){
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		queryService = (QueryService) context.getBean("queryService");
	}
	
	@After
	public void after(){
		context.close();
	}
	
	@Test
	public void testQueryService()throws Exception{
		//使用并发测试查询缓存
		for(int i=0;i<1000;i++){
			Thread t = new Thread(new ExecuteThread());
			t.start();
			latch.countDown();
		}
		Thread.sleep(10000);
		//只会有一次打印===============no cache,load data===========
	}
	
	@Test
	public void testSet(){
		MemcachedUtils.set("set1", "调用MemcachedUtils的set方法不设置过期时间");
		assertEquals("调用MemcachedUtils的set方法不设置过期时间", MemcachedUtils.get("set1"));
	}
	
	@Test
	public void testSetExpire()throws Exception{
		//MemcachedUtils.set(key,value,new Date(System.currentTimeMillis()+5000)) 
		//设置为50秒过期它,会以服务端的时间为准，也就是说如果本地客户端的时间跟服务端的时间有差值，这个值就会出现问题。
		Calendar time = Calendar.getInstance();
		time.add(Calendar.SECOND, 60);
		MemcachedUtils.set("set2", "调用MemcachedUtils的set方法设置过期时间",time.getTime());
		assertEquals("调用MemcachedUtils的set方法设置过期时间", MemcachedUtils.get("set2"));
		Thread.sleep(60*1000);
		assertNull(MemcachedUtils.get("set2"));
	}
	
	@Test
	public void testAdd(){
		MemcachedUtils.add("add1", "调用MemcachedUtils的add方法不设置过期时间");
		assertEquals("调用MemcachedUtils的add方法不设置过期时间", MemcachedUtils.get("add1"));
	}
	
	@Test
	public void testAddExpire()throws Exception{
		Calendar time = Calendar.getInstance();
		time.add(Calendar.SECOND, 60);
		MemcachedUtils.add("add2", "调用MemcachedUtils的add方法设置过期时间",time.getTime());
		assertEquals("调用MemcachedUtils的add方法设置过期时间", MemcachedUtils.get("add2"));
		Thread.sleep(60*1000);
		assertNull(MemcachedUtils.get("add2"));
	}
	
	@Test
	public void testReplace(){
		assertEquals("调用MemcachedUtils的set方法不设置过期时间", MemcachedUtils.get("set1"));
		MemcachedUtils.replace("set1", "调用MemcachedUtils的set方法不设置过期时间,update");
		assertEquals("调用MemcachedUtils的set方法不设置过期时间,update", MemcachedUtils.get("set1"));
	}
	
	@Test
	public void testDelete(){
		assertEquals("调用MemcachedUtils的set方法不设置过期时间,update", MemcachedUtils.get("set1"));
		MemcachedUtils.delete("set1");
		assertNull(MemcachedUtils.get("set1"));
	}

}
