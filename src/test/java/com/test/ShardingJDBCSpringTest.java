package com.test;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pojo.sharding.TOrder;
import com.pojo.sharding.TUser;
import com.service.sharding.TOrderService;
import com.service.sharding.TUserService;

public class ShardingJDBCSpringTest {
	
	private ClassPathXmlApplicationContext context;
	private TOrderService tOrderService;
	private TUserService tUserService;
	
	@Before
	public void before(){
		context = new ClassPathXmlApplicationContext("sharding-jdbc.xml");
		tOrderService = (TOrderService)context.getBean(TOrderService.class);
		tUserService = (TUserService)context.getBean(TUserService.class);
	}
	
	@After
	public void after(){
		context.close();
	}
	
	@Test  
    public void testOrderInsert() {  
        TOrder u = new TOrder();  
        u.setUserId(11);  
        u.setOrderId(990);  
        Assert.assertEquals(tOrderService.insert(u), true);  
    } 
	
	@Test  
    public void testFindAll(){  
        List<TOrder> orders = tOrderService.findAll();  
        if(null != orders && !orders.isEmpty()){  
            for(TOrder u :orders){  
                System.out.println(u.getOrderId()+":"+u.getUserId());  
            }  
        }  
    }  
	
	@Test  
    public void testTransactionTestSucess(){  
		tOrderService.transactionTestSucess();  
    }  
      
    @Test(expected = IllegalAccessException.class)  
    public void testTransactionTestFailure() throws IllegalAccessException{  
    	tOrderService.transactionTestFailure();  
    }  
    
    @Test  
    public void testUserInsert() {  
        TUser u = new TUser();  
        u.setUserId(10);  
        u.setName("测试2");  
        Assert.assertEquals(tUserService.insert(u), true);  
    } 
    
    @Test  
    public void testUserFindAll() {  
    	List<Map<String, Object>> users = tUserService.findAll();
    	Assert.assertTrue(users.size()>0);
    	for(Map<String,Object> user : users){
    		for(String key : user.keySet()){
    			System.out.println(key + ":" + user.get(key));
    		}
    		System.out.println("===============================");
    	}
    }

}
