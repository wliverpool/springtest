package com.service.sharding;

import java.util.List;

import com.pojo.sharding.TOrder;

public interface TOrderService {
	
	public boolean insert(TOrder order);  
    
    public List<TOrder> findAll();  
      
    public List<TOrder> findByOrderIds(List<Integer> orderIds);  
      
    public void transactionTestSucess();  
      
    public void transactionTestFailure() throws IllegalAccessException;  

}
