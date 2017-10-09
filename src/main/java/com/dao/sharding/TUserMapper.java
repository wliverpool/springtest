package com.dao.sharding;

import java.util.List;
import java.util.Map;

import com.pojo.sharding.TUser;

public interface TUserMapper {
	
	Integer insert(TUser s);  
    
    List<Map<String, Object>> findAll();  

}
