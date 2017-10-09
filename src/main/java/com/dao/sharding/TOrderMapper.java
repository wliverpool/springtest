package com.dao.sharding;

import java.util.List;

import com.pojo.sharding.TOrder;

public interface TOrderMapper {
	
	Integer insert(TOrder s);  
    
    List<TOrder> findAll();  
      
    List<TOrder> findByOrderIds(List<Integer> orderIds); 

}
