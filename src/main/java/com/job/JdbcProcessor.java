package com.job;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.pojo.Seckill;
import com.pojo.TargetSeckill;

/**
 * 业务处理类。
 * 
 * @author 吴福明
 */
@Component("jdbcProcessor")
public class JdbcProcessor implements ItemProcessor<Seckill, TargetSeckill> {
	
	/**
	 * 对取到的数据进行简单的处理。
	 * 
	 * @param seckill
	 *            处理前的数据。
	 * @return 处理后的数据。
	 * @exception Exception
	 *                处理是发生的任何异常。
	 */
	public TargetSeckill process(Seckill seckill) throws Exception {
		TargetSeckill targetSeckill = new TargetSeckill();
		targetSeckill.setId(seckill.getSeckill_id());
		targetSeckill.setCreated(seckill.getCreate_time());
		targetSeckill.setEnded(seckill.getEnd_time());
		targetSeckill.setName(seckill.getName());
		targetSeckill.setNumber(seckill.getNumber());
		targetSeckill.setStarted(seckill.getStart_time());
		
		return targetSeckill;
	}

}
