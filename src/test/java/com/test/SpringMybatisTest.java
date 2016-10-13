package com.test;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dao.mybatisauto.CityMapper;
import com.pojo.mybatisauto.City;
import com.pojo.mybatisauto.CityExample;
import com.pojo.mybatisauto.CityExample.Criteria;
import com.service.mybatis.CityService;

public class SpringMybatisTest {
	
	private ClassPathXmlApplicationContext context;
	private CityMapper cityMapper;
	private CityService cityService;
	
	@Before
	public void before(){
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		cityMapper = (CityMapper) context.getBean("cityDao");
		cityService = (CityService) context.getBean("cityService");
	}
	
	@After
	public void after(){
		context.close();
	}
	
	/*@Test
	public void testInsert(){
		City city = new City();
		city.setCountryCode("AIA");
		city.setDistrict("1111");
		city.setName("1111");
		city.setPopulation(1);
		cityMapper.insert(city);
		System.out.println(city.getID());
	}
	
	@Test
	public void testDelete(){
		cityMapper.deleteByPrimaryKey(4086);
	}
	
	@Test 
	public void testMybatisTX(){
		City city = new City();
		city.setCountryCode("AIA");
		city.setDistrict("1111");
		city.setName("1111");
		city.setPopulation(1);
		cityService.saveCity(city);
	}*/
	
	@Test
	public void testSelectByExample(){
		CityExample example = new CityExample();
		Criteria criteria = example.createCriteria();
		example.setOrderByClause("Name desc");
		criteria.andNameLike("%1111%");
		criteria.andCountryCodeEqualTo("AIA");
		
		Criteria criteria2 = example.createCriteria();
		criteria2.andCountryCodeEqualTo("CAN");
		
		example.or(criteria2);
		
		example.setLimitStart(0);
		example.setPageSize(10);
		
		
		
		List<City> list = cityMapper.selectByExample(example);
		System.out.println(list.size());
		if(null!=list){
			for(City city : list){
				System.out.println(city.getCountryCode());
				System.out.println(city.getDistrict());
				System.out.println(city.getName());
				System.out.println(city.getID());
				System.out.println(city.getPopulation());
			}
		}
	}
	

}
