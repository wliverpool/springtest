package com.service.sharding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.sharding.TOrderItemMapper;
import com.pojo.sharding.TOrderItem;

@Service
public class TOrderItemServiceImpl implements TOrderItemService{
	
	@Autowired
	private TOrderItemMapper tOrderItemMapper;

	@Override
	public boolean insert(TOrderItem item) {
		return tOrderItemMapper.insert(item)>0 ? true:false ;
	}

}
