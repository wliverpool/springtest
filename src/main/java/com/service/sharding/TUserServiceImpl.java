package com.service.sharding;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.sharding.TUserMapper;
import com.pojo.sharding.TUser;

@Service
public class TUserServiceImpl implements TUserService {
	
	@Autowired
	private TUserMapper userMapper;

	@Override
	public boolean insert(TUser user) {
		return userMapper.insert(user) > 0 ? true:false;
	}

	@Override
	public List<Map<String, Object>> findAll() {
		return userMapper.findAll();
	}

}
