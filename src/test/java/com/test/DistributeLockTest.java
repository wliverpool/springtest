package com.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lock.SimpleDistributedLock;

/**
 * 基于zookeeper的分布式锁
 * @author mittermeyer
 *
 */
public class DistributeLockTest {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		SimpleDistributedLock simpleDistributedLock = (SimpleDistributedLock)ac.getBean("simpleDistributedLock");
		try {
			simpleDistributedLock.acquire();
			System.out.println("Client2 locked");
			simpleDistributedLock.release();
			System.out.println("Client2 released lock");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ac.close();
		}
	}

}
