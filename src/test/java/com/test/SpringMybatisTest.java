package com.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dao.mybatisauto.BookBrowserMapper;
import com.dao.mybatisauto.CityMapper;
import com.dao.mybatisauto.MybatisClobMapper;
import com.dao.mybatisauto.PersonMapper;
import com.pojo.mybatisauto.Book;
import com.pojo.mybatisauto.Browser;
import com.pojo.mybatisauto.City;
import com.pojo.mybatisauto.CityExample;
import com.pojo.mybatisauto.CityExample.Criteria;
import com.pojo.mybatisauto.MybatisClob;
import com.pojo.mybatisauto.OrderInfo;
import com.pojo.mybatisauto.Person;
import com.service.mybatis.CityService;
import com.service.mybatis.OrderService;

public class SpringMybatisTest {
	
	private ClassPathXmlApplicationContext context;
	private CityMapper cityMapper;
	private CityService cityService;
	private OrderService orderService;
	private MybatisClobMapper clobMapper;
	private PersonMapper personMapper;
	private BookBrowserMapper bookBrowserMapper;
	
	@Before
	public void before(){
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		cityMapper = (CityMapper) context.getBean("cityDao");
		cityService = (CityService) context.getBean("cityService");
		orderService = (OrderService) context.getBean("orderService");
		clobMapper = (MybatisClobMapper) context.getBean("clobMapper");
		//测试一对一,一对多关系
		personMapper = (PersonMapper) context.getBean("personMapper");
		//测试多对多
		bookBrowserMapper = (BookBrowserMapper) context.getBean("bookBrowserMapper");
	}
	
	@After
	public void after(){
		context.close();
	}
	
	@Test
	public void testManyToMany(){
		Browser browser = bookBrowserMapper.selectBrowserBook(1);
		assertNotNull(browser.getName());
		System.out.println(browser.getName());
		assertTrue(browser.getBooks().size()>0);
		for(Book b : browser.getBooks()){
			System.out.println(b.getBname());
			System.out.println(b.getBprice());
			//System.out.println(b.getBrowsers().size());
		}
	}
	
	@Test
	public void testSelectOneToManyAndOneToOne(){
		List<Person> persons = personMapper.queryAllPerson();
		assertTrue(persons.size()>0);
		for(Person p : persons){
			System.out.println(p.getName());
			System.out.println(p.getCard().getInfo());
			System.out.println(p.getAddressList().size());
		}
	}
	
	@Test
	public void insertClobTest(){
		MybatisClob test=new MybatisClob();
		test.setBigType("测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试");
		test.setTitle("测试title");
		clobMapper.insertClobTest(test);
		assertTrue(test.getId()>0);
		System.out.println("id:"+test.getId());
	}
	
	@Test
	public void getClobTestById(){
		MybatisClob test= clobMapper.getClobTestById(1);
		assertNotNull(test.getBigType());
		System.out.println(test.getBigType());
	}
	
	@Test
	public void testMapCondition(){
		Map<String, Object> condition = new HashMap<>();
		List<Integer> ids = new ArrayList<>();
		ids.add(1);
		ids.add(4);
		ids.add(6);
		condition.put("ids", ids);
		List<City> cities = cityMapper.selectByCustomCondition(condition);
		assertTrue(cities.size()>0);
		for(City city : cities){
			System.out.println(city.getName());
		}
	}
	
	@Test
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
	public void testInsertBatch(){
		List<City> cities = new ArrayList<>();
		City city = new City();
		city.setCountryCode("AIA");
		city.setDistrict("111122");
		city.setName("11114444");
		city.setPopulation(2);
		cities.add(city);
		city = new City();
		city.setCountryCode("AIA");
		city.setDistrict("33333");
		city.setName("33333");
		city.setPopulation(3);
		cities.add(city);
		city = new City();
		city.setCountryCode("AIA");
		city.setDistrict("4444");
		city.setName("44444");
		city.setPopulation(4);
		cities.add(city);
		cityMapper.insertBatch(cities);
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
	}
	
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
	
	@Test
	public void testInsertOrder(){
		OrderInfo info = new OrderInfo();
		info.setOrderName("测试订单2");
		info.setOrderStatus("1");
		orderService.saveOrderInfo(info);
	}
	
	@Test
	public void testTradeOut1(){
		OrderInfo info = new OrderInfo();
		info.setOrderId(1L);
		orderService.tradeOut1(info);
	}
	
	@Test
	public void testTradeOut2(){
		OrderInfo info = new OrderInfo();
		info.setOrderId(3L);
		orderService.tradeOut2(info);
	}
	
	@Test
	public void testTradeOut1WithTemplate(){
		OrderInfo info = new OrderInfo();
		info.setOrderId(4L);
		orderService.tradeOut1WithTemplate(info);
	}
	

}
