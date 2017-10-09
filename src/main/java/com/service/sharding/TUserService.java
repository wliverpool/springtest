package com.service.sharding;

import java.util.List;
import java.util.Map;

import com.pojo.sharding.TUser;

public interface TUserService {
	
	public boolean insert(TUser user);
	
	public List<Map<String,Object>> findAll();

}
