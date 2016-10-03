package com.service.atomikos;

import com.dao.atomikos.LiveWarmDao;
import com.dao.atomikos.TmpUserDao;
import com.pojo.LiveWarm;
import com.pojo.TmpUser;

public class AtomikosService {
	
	private TmpUserDao tmpUserDao;
	private LiveWarmDao liveWarmDao;
	
	public TmpUserDao getTmpUserDao() {
		return tmpUserDao;
	}
	
	public void setTmpUserDao(TmpUserDao tmpUserDao) {
		this.tmpUserDao = tmpUserDao;
	}
	
	public LiveWarmDao getLiveWarmDao() {
		return liveWarmDao;
	}
	
	public void setLiveWarmDao(LiveWarmDao liveWarmDao) {
		this.liveWarmDao = liveWarmDao;
	}
	
	public void save(LiveWarm liveWarm,TmpUser user){
		tmpUserDao.save(user);
		liveWarmDao.save(liveWarm);
	}
	
	public void readAndWriteInfo(LiveWarm liveWarm,TmpUser user){
		tmpUserDao.save(user);
		tmpUserDao.getById(123456L);
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		liveWarmDao.getById(59);
		liveWarmDao.save(liveWarm);
	}

}
