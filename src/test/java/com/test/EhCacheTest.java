package com.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dao.RoleDao;

public class EhCacheTest {

	ApplicationContext context = null;

	@Before
	public void setUp() {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
	}

	@Test
	public void testEncache() {

		RoleDao pDao = (RoleDao) context.getBean("roleDao");
		long start = System.currentTimeMillis();
		pDao.find("22");
		long end = System.currentTimeMillis();
		System.out.println("=================未有缓存id=22,查询用时:" + (end - start) + "==========================");
		pDao.find("33");
		long end2 = System.currentTimeMillis();
		System.out.println("-----------------未有缓存id=33,查询用时:" + (end2 - end) + "--------------------------");
		pDao.find("22");
		long end3 = System.currentTimeMillis();
		System.out.println("=================之前已经查询过已有缓存id=22,查询用时:" + (end3 - end2) + "==========================");
		pDao.find("33");
		long end4 = System.currentTimeMillis();
		System.out.println("=================虽然之前已经查询过id=33,但由于有缓存的条件condition=\"id==22\",所以之前查询结果未放入缓存之中,查询用时:" + (end4 - end3) + "==========================");
		pDao.findAll();
		long end5 = System.currentTimeMillis();
		System.out.println("=================未查询过finAll的查询,查询用时:" + (end5 - end4) + "==========================");
		pDao.findAll();
		long end6 = System.currentTimeMillis();
		System.out.println("=================虽然之前查询过findAll的查询但方法未加@Cacheable注解所以未缓存,查询用时:" + (end6 - end5) + "==========================");
	}

}
