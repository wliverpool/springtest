package com.pojo;

import java.io.Serializable;

/**
 * 微信用户分享中奖基础数据类
 * 
 */
public class WchatLotteryDomain implements Serializable {
	
	private static final long serialVersionUID = -1595371216905016135L;

	//奖项id
	private Integer id;
	
	//开奖奖项在360开奖转盘中占据的最小角度
	private int minAngle;
	
	//开奖奖项在360开奖转盘中占据的最大角度
	private int maxAngle;

	// 中奖金额
	private String prize;

	// 中奖率
	private Integer v;

	public WchatLotteryDomain(Integer id, String prize, Integer v, int minAngle, int maxAngle) {
		this.id = id;
		this.prize = prize;
		this.v = v;
		this.minAngle = minAngle;
		this.maxAngle = maxAngle;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPrize() {
		return prize;
	}

	public void setPrize(String prize) {
		this.prize = prize;
	}

	public Integer getV() {
		return v;
	}

	public void setV(Integer v) {
		this.v = v;
	}
	
	public int getMinAngle() {
		return minAngle;
	}

	public void setMinAngle(int minAngle) {
		this.minAngle = minAngle;
	}

	public int getMaxAngle() {
		return maxAngle;
	}

	public void setMaxAngle(int maxAngle) {
		this.maxAngle = maxAngle;
	}

}