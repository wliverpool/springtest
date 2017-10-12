package com.job;

import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;

import com.pojo.mybatisauto.MybatisClob;

@Component("itemReadListener")
public class MyItemReadListener implements ItemReadListener<MybatisClob>{

	@Override
	public void beforeRead() {
		System.out.println("================beforeRead====================");
	}

	@Override
	public void afterRead(MybatisClob item) {
		System.out.println("================afterRead:" + item.getTitle() + "====================");
	}

	@Override
	public void onReadError(Exception ex) {
		System.out.println("================onReadError====================");
	}

}
