package com.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pojo.MonitorInfoBean;
import com.service.IMonitorService;
import com.service.MonitorServiceImpl;

public class MonitorTest {
	
	public static void main(String[] args)throws Exception {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		IMonitorService service = context.getBean(MonitorServiceImpl.class);
		MonitorInfoBean info = service.getMonitorInfoBean();
		System.out.println("CpuRatio:"+info.getCpuRatio());
		System.out.println("FreeMemory:"+info.getFreeMemory());
		System.out.println("FreePhysicalMemorySize:"+info.getFreePhysicalMemorySize());
		System.out.println("MaxMemory:"+info.getMaxMemory());
		System.out.println("OsName:"+info.getOsName());
		System.out.println("TotalMemory:"+info.getTotalMemory());
		System.out.println("TotalMemorySize:"+info.getTotalMemorySize());
		System.out.println("TotalThread:"+info.getTotalThread());
		System.out.println("UsedMemory:"+info.getUsedMemory());
		
	}

}
