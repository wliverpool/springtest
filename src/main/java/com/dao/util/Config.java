
package com.dao.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;



@Configuration
// 多个用,号分隔
@PropertySource(value = { "classpath:app.properties" })
public class Config {
	@Value("${gnss.server.url}")
	public String gnssServerUrl;
	@Value("${jdbc.url}")
	public String jdbcUlr;
	@Value("#{'${server.id}'.split(',')}")
	public List<Integer> serverId;

	@Autowired
	private Environment environment;

	public Environment getEnvironment() {
		return environment;
	}
	
	public String getProperty(String key){
		return environment.getProperty(key);
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

}
