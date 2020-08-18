package com.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.junit.Test;

/**
 * 负载均衡算法测试
 * @author wufuming
 *
 */

public class BalanceTest {

	public static List<String> list = Arrays.asList("10.180.11.126:8888","10.180.11.128:8888","10.180.11.130:8888");
	public static Map<String,Integer> map = new HashMap<>();
	
	static {
		//ip,权重
        map.put("10.180.11.126:8888",2);
        map.put("10.180.11.128:8888",7);
        map.put("10.180.11.130:8888",1);
    }
 
	
	/**
	 * 随机
	 */
	@Test
	public void testRandomServer(){
		for(int i = 0; i < 15; i++) {
			Random random = new Random();
			int number = random.nextInt(list.size());
			System.out.println("随机获取到ip:" + list.get(number));
		}
	}
	
	/**
	 * 加权随机
	 */
	@Test
	public void testWeightRandom(){
		for(int i = 0; i < 15; i++) {
			Random random = new Random();
			//计算总权值
	        int weight = map.values().stream().mapToInt(p -> p).sum();
	        //随机一个随机数
	        int index = random.nextInt(weight);
	        boolean isGetIp = false;
	        //遍历  服务  map
	        for(Map.Entry<String,Integer> entry : map.entrySet()) {
	            //如果权重大于  索引
	            if(entry.getValue() >= index) {
	                // 返回这个服务
	            	System.out.println("加权随机获取到ip:" + entry.getKey());
	            	isGetIp = true;
	            }
	            //否则，索引 = 当前索引 - 当前服务的权重
	            index = index - entry.getValue();
	        }
	        if(!isGetIp){
	        	System.out.println("加权随机未能获取到ip");
	        }
		}
	}
	
	/**
	 * 轮询
	 */
	@Test
	public void testPoll(){
		int index = 0;
		for(int i = 0; i < 15; i++) {
			if(index == list.size()) {
	            index = 0;
	        }
			System.out.println("轮询获取到ip:" + list.get(index++));
		}
	}
	
	/**
	 * 加权轮询
	 */
	@Test
	public void testWeightPoll(){
		int index = 0;
		for(int i = 0; i < 15; i++) {
			int weight = map.values().stream().mapToInt( p -> p).sum();
			int number = (index++) % weight;
			boolean isGetIp = false;
			for(Map.Entry<String,Integer> entry : map.entrySet()) {
	            if(entry.getValue() >= number) {
	                System.out.println("加权轮询获取到ip:" + entry.getKey());
	                isGetIp = true;
	            }
	            number = number - entry.getValue();
	        }
			if(!isGetIp){
				System.out.println("加权轮询未能获取到ip");
			}
		}
	}
	
	/**
	 * 根据访问用户hash负载均衡
	 * 实际常用根据用户ip
	 */
	@Test
	public void testHash(){
        //獲取ip列表list
        List<String> keyList = new ArrayList<String>();
        keyList.addAll(list);
        int serverListSize = keyList.size();
		//根据用户获取不同的服务器
		for(int i = 0; i < 100; i++) {
			int hashCode = ("用户" + i + "访问").hashCode();
	        int serverPos = Math.abs(hashCode % serverListSize);
	        System.out.println("根据访问用户:" + i + ",hash获取到ip:" + keyList.get(serverPos));
		}
		System.out.println("==================================================");
		for(int i = 99; i >=0 ; i--) {
			int hashCode = ("用户" + i + "访问").hashCode();
	        int serverPos = Math.abs(hashCode % serverListSize);
	        System.out.println("根据访问用户:" + i + ",hash获取到ip:" + keyList.get(serverPos));
		}
	}
	
}
