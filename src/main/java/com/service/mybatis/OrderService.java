package com.service.mybatis;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.dao.mybatisauto.OrderApplyMapper;
import com.dao.mybatisauto.OrderInfoMapper;
import com.pojo.mybatisauto.OrderApply;
import com.pojo.mybatisauto.OrderApplyExample;
import com.pojo.mybatisauto.OrderInfo;
import com.pojo.mybatisauto.OrderInfoExample;

/**
 * 测试模拟下单并访问银行系统出帐
 * @author 吴福明
 *
 */
public class OrderService {
	
	private OrderInfoMapper orderInfoMapper;
	private OrderApplyMapper orderApplyMapper;
	private PlatformTransactionManager ptm;
	
	//不使用方法级事务
	public OrderInfoMapper getOrderInfoMapper() {
		return orderInfoMapper;
	}

	public void setOrderInfoMapper(OrderInfoMapper orderInfoMapper) {
		this.orderInfoMapper = orderInfoMapper;
	}
	
	public void saveOrderInfo(OrderInfo info){
		orderInfoMapper.insertSelective(info);
	}

	public PlatformTransactionManager getPtm() {
		return ptm;
	}

	public void setPtm(PlatformTransactionManager ptm) {
		this.ptm = ptm;
	}
	
	public OrderApplyMapper getOrderApplyMapper() {
		return orderApplyMapper;
	}

	public void setOrderApplyMapper(OrderApplyMapper orderApplyMapper) {
		this.orderApplyMapper = orderApplyMapper;
	}

	//模拟单张orderInfo发起出帐
	//不加方法级别事务锁防止出现因为访问第三方系统问题导致本身系统出现长时间数据库锁等待
	public void tradeOut1(OrderInfo info){
		
		//使用乐观锁方法先对订单表更新状态,起到加锁作用,防止因为多次请求导致多次发起出帐的问题		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = ptm.getTransaction(def);
		boolean lockStatus = false;
		try{
			//设置更新条件
			OrderInfoExample condition = new OrderInfoExample();
			condition.createCriteria().andOrderIdEqualTo(info.getOrderId()).andOrderStatusEqualTo("1");
			//设置需要更新成什麽状态
			OrderInfo updateValue = new OrderInfo();
			updateValue.setOrderStatus("4");
			int AffectedRows = orderInfoMapper.updateByExampleSelective(updateValue, condition);
			//根据受影响行数是否等于1判断乐观锁是否加上,只有上锁成功才发起出帐申请
			lockStatus = (AffectedRows == 1);
			ptm.commit(status);
		} catch (Exception e) {
			ptm.rollback(status);
			e.printStackTrace();
		}
		//如果加上锁
		if(lockStatus){
			//在此处可以访问外部第三方系统
			System.out.println("====================使用"+info.getOrderId()+"代表的订单信息,访问第三方出帐系统==================");
			boolean externalSystemSuccess = true;
			//访问完第三方系统之后再根据第三方系统的响应对加锁订单进行更新设置是否出帐成功
			def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
			status = ptm.getTransaction(def);
			try{
				//设置更新条件
				OrderInfoExample condition = new OrderInfoExample();
				condition.createCriteria().andOrderIdEqualTo(info.getOrderId()).andOrderStatusEqualTo("4");
				//设置需要更新成什麽状态
				OrderInfo updateValue = new OrderInfo();
				if(externalSystemSuccess){
					//出帐成功
					updateValue.setOrderStatus("5");
				}else{
					//出帐失败
					updateValue.setOrderStatus("6");
				}
				
				orderInfoMapper.updateByExampleSelective(updateValue, condition);
				ptm.commit(status);
			} catch (Exception e) {
				ptm.rollback(status);
				e.printStackTrace();
			}
		}
		
		
	}
	
	//模拟两张表orderInfo和applyOrder发起出帐,此处用来处理第三方系统要求每次发起的出帐id必须唯一的要求
	//不加方法级别事务锁防止出现因为访问第三方系统问题导致本身系统出现长时间数据库锁等待
	public void tradeOut2(OrderInfo info){
		//使用乐观锁方法先对订单表更新状态,起到加锁作用,防止因为多次请求导致多次发起出帐的问题		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = ptm.getTransaction(def);
		long applyId = 0;
		try{
			//先对orderInfo表上锁
			//设置更新条件
			OrderInfoExample condition = new OrderInfoExample();
			condition.createCriteria().andOrderIdEqualTo(info.getOrderId()).andOrderStatusEqualTo("1");
			//设置需要更新成什麽状态
			OrderInfo updateValue = new OrderInfo();
			updateValue.setOrderStatus("4");
			int AffectedRows = orderInfoMapper.updateByExampleSelective(updateValue, condition);
			//如果对orderInfo表上锁成功,再在apply表中新增申请记录发起出帐申请
			if(AffectedRows == 1){
				OrderApply apply = new OrderApply();
				apply.setOrderId(info.getOrderId());
				apply.setStatus("1");
				orderApplyMapper.insertSelective(apply);
				applyId = apply.getOrderId();
			}
			ptm.commit(status);
		} catch (Exception e) {
			ptm.rollback(status);
			e.printStackTrace();
		}
		//如果加上锁成功哦你
		if(applyId>0){
			
			//对apply表上锁
			def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
			status = ptm.getTransaction(def);
			boolean lockStatus = false;
			try{
				//设置更新条件
				OrderApplyExample condition = new OrderApplyExample();
				condition.createCriteria().andApplyIdEqualTo(applyId).andStatusEqualTo("1");
				//设置需要更新成什麽状态
				OrderApply updateValue = new OrderApply();
				updateValue.setStatus("4");
				int AffectedRows = orderApplyMapper.updateByExampleSelective(updateValue, condition);
				lockStatus = (AffectedRows == 1);
				ptm.commit(status);
			} catch (Exception e) {
				ptm.rollback(status);
				e.printStackTrace();
			}
			//如果对apply表上锁成功
			if (lockStatus) {
				//在此处可以访问外部第三方系统
				System.out.println("====================使用"+applyId+"代表的订单信息,访问第三方出帐系统==================");
				boolean externalSystemSuccess = true;
				//访问完第三方系统之后再根据第三方系统的响应对加锁订单进行更新设置是否出帐成功
				def = new DefaultTransactionDefinition();
				def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
				status = ptm.getTransaction(def);
				try{
					//更新OrderInfo的信息
					OrderInfoExample condition = new OrderInfoExample();
					condition.createCriteria().andOrderIdEqualTo(info.getOrderId()).andOrderStatusEqualTo("4");
					OrderInfo updateValue = new OrderInfo();
					//设置更新apply表的信息
					OrderApplyExample condition2 = new OrderApplyExample();
					condition2.createCriteria().andApplyIdEqualTo(applyId).andStatusEqualTo("4");
					OrderApply applyUpdate = new OrderApply();
					if(externalSystemSuccess){
						//出帐成功
						updateValue.setOrderStatus("5");
						applyUpdate.setStatus("5");
					}else{
						//出帐失败
						updateValue.setOrderStatus("6");
						applyUpdate.setStatus("6");
					}
					orderApplyMapper.updateByExampleSelective(applyUpdate, condition2);
					orderInfoMapper.updateByExampleSelective(updateValue, condition);
					ptm.commit(status);
				} catch (Exception e) {
					ptm.rollback(status);
					e.printStackTrace();
				}
			}
		}
	}
	
	

}
