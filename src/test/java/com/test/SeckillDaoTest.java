package com.test;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dao.mybatisauto.SeckillMapper;
import com.dao.mybatisauto.SuccessKilledMapper;
import com.pojo.mybatisauto.Seckill;
import com.pojo.mybatisauto.SeckillExample;
import com.pojo.mybatisauto.SuccessKilled;
import com.pojo.mybatisauto.SuccessKilledKey;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class SeckillDaoTest {
	
	@Autowired
	private SeckillMapper seckillMapper;
	@Autowired
	private SuccessKilledMapper successKilledMapper;
	
	@Test
	public void testReduceNumber(){
		SeckillExample conditions = new SeckillExample();
		Date now = new Date();
		conditions.createCriteria().andSeckillIdEqualTo(1000L).andStartTimeLessThanOrEqualTo(now)
					.andEndTimeGreaterThanOrEqualTo(now).andNumberGreaterThan(0);
		int count = seckillMapper.reduceNumber(conditions);
		assertEquals(count, 1);
	}
	
	@Test
	public void testQueryById(){
		Seckill seckill = seckillMapper.selectByPrimaryKey(1000L);
		System.out.println(seckill.getName());
		System.out.println(seckill.getNumber());
	}
	
	@Test
	public void testQueryAll(){
		SeckillExample conditions = new SeckillExample();
		conditions.setLimitStart(0);
		conditions.setPageSize(100);
		List<Seckill> seckills = seckillMapper.selectByExample(conditions);
		for(Seckill seckill : seckills){
			System.out.println(seckill.getName());
			System.out.println(seckill.getNumber());
		}
	}
	
	@Test
	public void testInsertSuccessKilled(){
		SuccessKilled successKilled = new SuccessKilled();
		successKilled.setSeckillId(1000L);
		successKilled.setUserPhone("13916445646");
		successKilled.setState(new Byte("1"));
		int count = successKilledMapper.insertSelective(successKilled);
		assertEquals(count, 1);
	}
	
	@Test
	public void testSelectBySeckillIdWithSeckill(){
		SuccessKilledKey key = new SuccessKilledKey();
		key.setSeckillId(1000L);
		key.setUserPhone("13916445646");
		SuccessKilled successKilled = successKilledMapper.selectBySeckillIdWithSeckill(key);
		System.out.println(successKilled.getCreateTime());
		System.out.println(successKilled.getState());
		System.out.println(successKilled.getUserPhone());
		System.out.println(successKilled.getSeckillId());
		if(null!=successKilled.getSeckill()){
			Seckill seckill = successKilled.getSeckill();
			System.out.println(seckill.getName());
			System.out.println(seckill.getNumber());
		}
	}

}
