package com.dao.sharding;

import java.util.List;

import com.pojo.sharding.TOrderItem;

public interface TOrderItemMapper {
	
	Integer insert(TOrderItem u);  
    
    List<TOrderItem> findAll();  
      
    List<TOrderItem> findByUserIds(List<Integer> userIds);  

}
