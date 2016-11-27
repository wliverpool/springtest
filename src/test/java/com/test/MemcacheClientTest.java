package com.test;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

import com.util.MemcachedUtils;

public class MemcacheClientTest {
	
	@Test
	public void testSet(){
		MemcachedUtils.set("set1", "调用MemcachedUtils的set方法不设置过期时间");
		assertEquals("调用MemcachedUtils的set方法不设置过期时间", MemcachedUtils.get("set1"));
	}
	
	@Test
	public void testSetExpire()throws Exception{
		Calendar time = Calendar.getInstance();
		time.add(Calendar.SECOND, 10);
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
		time.add(Calendar.SECOND, 10);
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
