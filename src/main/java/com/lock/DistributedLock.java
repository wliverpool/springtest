package com.lock;

import java.util.concurrent.TimeUnit;  

/**
 * 分布式锁的接口
 * @author mittermeyer
 *
 */
public interface DistributedLock {  
      
    /**
     * 获取锁，如果没有得到就等待 
     * @throws Exception
     */
    public void acquire() throws Exception;  
  
    /**
     * 获取锁，直到超时 
     * @param time
     * @param unit
     * @return
     * @throws Exception
     */
    public boolean acquire(long time, TimeUnit unit) throws Exception;  
  
    /**
     * 释放锁 
     * @throws Exception
     */
    public void release() throws Exception;  
  
  
}  