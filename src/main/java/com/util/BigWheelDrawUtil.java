package com.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.pojo.WchatLotteryDomain;

import net.sf.json.JSONObject;

/**
 *
 * wchat大转盘抽奖活动
 */
public class BigWheelDrawUtil {

	/**
	 * 给转盘的每个角度赋初始值
	 * 
	 * @return
	 */
	private final static List<WchatLotteryDomain> initDrawList = new ArrayList<WchatLotteryDomain>() {
		{
			add(new WchatLotteryDomain(1, "一等奖", 1,1,14));
			add(new WchatLotteryDomain(2, "一等奖", 1,346,364));
			add(new WchatLotteryDomain(3, "不要灰心", 10,16,44));
			add(new WchatLotteryDomain(4, "神马也没有", 10,46,74));
			add(new WchatLotteryDomain(5, "祝您好运", 10,76,104));
			add(new WchatLotteryDomain(6, "二等奖", 2,106,134));
			add(new WchatLotteryDomain(7, "再接再厉", 10,136,164));
			add(new WchatLotteryDomain(8, "神马也没有", 10,166,194));
			add(new WchatLotteryDomain(9, "运气先攒着", 10,196,224));
			add(new WchatLotteryDomain(10, "三等奖", 5,226,254));
			add(new WchatLotteryDomain(11, "要加油哦", 10,256,284));
			add(new WchatLotteryDomain(12, "神马也没有", 10,286,314));
			add(new WchatLotteryDomain(13, "谢谢参与", 10,316,344));
		}
	};
	
	//根据概率获取奖项
	public static WchatLotteryDomain getRand(List<WchatLotteryDomain> domains){
		WchatLotteryDomain result = null;
		try {
			//获取所有奖项的总概率值
			int  sum = 0;
			for(int i=0;i<domains.size();i++){
				sum+=domains.get(i).getV();
			}
			//循环概率数组
			for(int i=0;i<domains.size();i++){ 
				//随机生成1到总概率值的整数
				int randomNum = new Random().nextInt(sum);
				//判断是否中奖
				if(randomNum<domains.get(i).getV()){
					result = domains.get(i);
					break;
				}else{
					sum -=domains.get(i).getV();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 生成转盘上指针最终的指向位置和所获奖项
	 * 
	 * @return
	 */
	public static Map<String, String> generateAward() {
		//获得初始装盘
		List<WchatLotteryDomain> initData = initDrawList;
		//根据概率获取奖项
		WchatLotteryDomain prizedDomain = getRand(initData);
		//根据获取奖项生转盘上的指针最终停留的位置,根据奖项的角度范围中最大值-最小值
		int angle = new Random().nextInt(prizedDomain.getMaxAngle()-prizedDomain.getMinAngle())+prizedDomain.getMinAngle();
		//奖项提示信息
		String msg = prizedDomain.getPrize();
		int id = prizedDomain.getId();
		Map<String, String> returnObj = new HashMap<>();
		returnObj.put("angle", String.valueOf(angle));
		returnObj.put("msg", msg);
		returnObj.put("prizedId", String.valueOf(id));
		return returnObj;
	}

	public static void main(String[] args) {
		System.out.println(JSONObject.fromObject(generateAward()).toString());
	}

}