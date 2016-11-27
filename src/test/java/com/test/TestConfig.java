package com.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dao.util.Config;

public class TestConfig {
	
	private ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	
	@Test
	public void testConfig(){
		Config config = context.getBean(Config.class);
		System.out.println(config.gnssServerUrl);
		System.out.println(config.jdbcUlr);
		System.out.println(config.serverId);
		System.out.println(config.getProperty("server.id"));
		System.out.println(config.getProperty("memcache.failover"));
	}

}
