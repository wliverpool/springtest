package com.test.business;

public class BusinessDemo {
	
	private boolean isShowSonThread = true;
	
	public synchronized void sonBusiness(int i){
		//判断子线程是否可以运行
		while(!isShowSonThread){
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		for(int j=1;j<=30;j++){
			System.out.println("========子线程运行第：" + i + "轮，第" + j + "次========");
		}
		//设置禁止子线程运行
		isShowSonThread = false;
		//通知主线程
		this.notify();
	}
	
	public synchronized void mainBusiness(int i){
		//判断主线程是否能运行
		while(isShowSonThread){
			try {
				//不能运行就等待
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		for(int j=1;j<=30;j++){
			System.out.println("========主线程运行第：" + i + "轮，第" + j + "次========");
		}
		//允许子线程运行
		isShowSonThread = true;
		//通知子线程
		this.notify();
	}

}
