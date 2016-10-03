package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.LiverpoolDao;
import com.dao.util.DynamicDataSourceHolder;
import com.pojo.Team;

@Transactional
@Service
public class LiverpoolService {
	
	private LiverpoolDao lfcDao;
	
	public LiverpoolDao getLfcDao() {
		return lfcDao;
	}

	@Autowired
	public void setLfcDao(LiverpoolDao lfcDao) {
		this.lfcDao = lfcDao;
	}

	public void updateLfcName(Long id,String name){
		DynamicDataSourceHolder.setDbType(DynamicDataSourceHolder.DB_TYPE_RW);
		Team lfc = lfcDao.getById(id);
		lfc.setName(name);
		lfcDao.updateNameById(id,name);
		lfcDao.updateNameById(id,name+"三大发生发生地方纳斯达克理发师考虑；地方打算地方吗上的；离开附近；阿喀琉斯觉得法拉开始的解放军卡数据库发牢骚；快递费数码方面打开拉风士大夫健康垃圾上的法律空间地方；考虑撒大家弗兰克撒旦风口浪尖撒旦考虑警方；斯科拉附近啊顺口溜解决考虑；");
	}
	
	@Transactional(readOnly=true)
	public Team getById(Long id){
		DynamicDataSourceHolder.setDbType(DynamicDataSourceHolder.DB_TYPE_R);
		return lfcDao.getById(id);
	}

}
