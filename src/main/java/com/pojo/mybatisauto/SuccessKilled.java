package com.pojo.mybatisauto;

import java.util.Date;

public class SuccessKilled extends SuccessKilledKey {
    private Byte state;

    private Date createTime;
    
    //多对一关联的秒杀商品记录
    private Seckill seckill;

    public Seckill getSeckill() {
		return seckill;
	}

	public void setSeckill(Seckill seckill) {
		this.seckill = seckill;
	}

	public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}