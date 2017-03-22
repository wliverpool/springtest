package com.test;

import com.test.business.BusinessDemo;

//子线程运行30次暂停，主线程再运行40次暂停，这样交易50次
public class ThreadTest {
	
	public static void main(String[] args){
		
		final BusinessDemo demo = new BusinessDemo();
		new Thread(new Runnable() {
			@Override
			public void run() {
				for(int i = 1;i<=50;i++){
					demo.sonBusiness(i);
				}
			}
		}).start();
		
		for(int i=1;i<50;i++){
			demo.mainBusiness(i);
		}
		
	}

}
