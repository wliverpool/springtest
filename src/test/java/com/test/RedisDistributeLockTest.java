package com.test;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dao.redis.RedisDao;

public class RedisDistributeLockTest {
	
	private ClassPathXmlApplicationContext context;
	private RedisDao redisDao;
	
	@Before
	public void before(){
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		redisDao = (RedisDao)context.getBean("redisDao");
	}
	
	@Test
	public void testDistributeLock()throws Exception{
		CountDownLatch   start=new CountDownLatch(1);// 初始化计数器为 一  
        for(int i=0;i<3;i++){
            MyTestThread tt = new MyTestThread(start,redisDao);  
            Thread t = new Thread(tt);  
            t.start();  
        }  
        start.countDown();//计数器減一  所有线程释放 同时跑  
        Thread.sleep(100000);
	}
	
	@After
	public void after(){
		context.close();
	}
	
	class MyTestThread implements Runnable {  
	    private final CountDownLatch startSignal;  
	    private RedisDao redisDao;
	      
	    public MyTestThread(CountDownLatch startSignal,RedisDao redisDao) {  
	            super();  
	            this.startSignal = startSignal;  
	            this.redisDao = redisDao;
	        }  
	  
	    @Override  
	    public void run() {  
	        try {  
	            startSignal.await(); //一直阻塞当前线程，直到计时器的值为0  
	        } catch (InterruptedException e) {  
	            e.printStackTrace();  
	        }  
	        doWork();  
	          
	    }  
	  
	    private void doWork() {  
	    	System.out.println("===================start thread=======================:" + Thread.currentThread().getName() + ":" + System.currentTimeMillis());
	    	String requestId = UUID.randomUUID().toString();
        	while(true){
        		if(redisDao.getDistributedLock("testKey", requestId, 20000)){
        			System.out.println("===================get redis lock=======================:" + Thread.currentThread().getName() + ":" + System.currentTimeMillis());
        			try {
    					Thread.sleep(15000);
    				} catch (InterruptedException e) {
    					e.printStackTrace();
    				}
            		redisDao.releaseDistributedLock("testKey", requestId);
            		System.out.println("===================release redis lock=======================:" + Thread.currentThread().getName() + ":" + System.currentTimeMillis());
            		break;
        		}
        	}
        	System.out.println("===================end thread=======================:" + Thread.currentThread().getName() + ":" + System.currentTimeMillis());
	    }  
	  
	}  

}
