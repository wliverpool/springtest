package com.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.service.LiverpoolService;

public class LiverpoolServiceTest {
	
	private LiverpoolService service = null;
	
	@Before
	public void setUp(){
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		service =(LiverpoolService) context.getBean("LiverpoolService");
	}
	
	@Test
	public void testUpdate(){
		service.updateLfcName(1L, "Gerrard");
	}
	
	@Test
	public void testGetById(){
		service.getById(1L);
	}

}
