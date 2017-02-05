package com.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pojo.SeckillVo;
import com.pojo.mybatisauto.Seckill;
import com.service.mybatis.SecKillService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class SecKillServiceTest {
	
	@Autowired
	private SecKillService secKillService;
	
	@Test
	public void testGetSeckillList(){
		List<Seckill> seckills = secKillService.getSecKillList(1, 10);
		assertEquals(5, seckills.size());
	}
	
	@Test
	public void testGetSecKillById(){
		Seckill seckill = secKillService.getSecKillById(1000L);
		assertEquals("100元秒杀iphone6", seckill.getName());
	}
	
	@Test
	public void testGetSeckillUrl(){
		SeckillVo seckillVo = secKillService.getSeckillUrl(1000L);
		assertTrue(seckillVo.isBegin());
		System.out.println(seckillVo.getMd5Url());
	}
	
	@Test
	public void testSaveSeckill(){
		boolean flag = secKillService.saveSeckill(1000L, "13916445646", "9a7576b2c48c05491ccc6048772b22c8");
		assertTrue(flag);
	}

}
