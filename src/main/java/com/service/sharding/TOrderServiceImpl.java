package com.service.sharding;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dao.sharding.TOrderItemMapper;
import com.dao.sharding.TOrderMapper;
import com.pojo.sharding.TOrder;
import com.pojo.sharding.TOrderItem;

@Service
@Transactional
public class TOrderServiceImpl implements TOrderService{
	
	@Autowired
	private TOrderMapper tOrderMapper;
	
	@Autowired
	private TOrderItemMapper tOrderItemMapper;

	@Override
	public boolean insert(TOrder order) {
		return tOrderMapper.insert(order) > 0 ? true:false ;
	}

	@Override
	public List<TOrder> findAll() {
		return tOrderMapper.findAll();
	}

	@Override
	public List<TOrder> findByOrderIds(List<Integer> orderIds) {
		return tOrderMapper.findByOrderIds(orderIds);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)  
	public void transactionTestSucess() {
		TOrder order = new TOrder();
		order.setOrderId(1002);
		order.setUserId(11);
		tOrderMapper.insert(order);
		TOrderItem item = new TOrderItem();
		item.setItemId(11);
		item.setUserId(11);
		item.setOrderId(1002);
		tOrderItemMapper.insert(item);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)  
	public void transactionTestFailure() throws IllegalAccessException {
		TOrder order = new TOrder();
		order.setOrderId(1003);
		order.setUserId(12);
		tOrderMapper.insert(order);
		TOrderItem item = new TOrderItem();
		item.setItemId(12);
		item.setUserId(12);
		item.setOrderId(1003);
		tOrderItemMapper.insert(item);
		TOrderItem item2 = new TOrderItem();
		item2.setItemId(4);
		item2.setUserId(2);
		item2.setOrderId(1001);
		tOrderItemMapper.insert(item2);
	}

	
	
}
