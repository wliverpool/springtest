package com.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dao.QaReaderDao;
import com.dao.util.DynamicDataSourceHolder;
import com.pojo.Page;
import com.pojo.QaReader;

@Service
public class QaReaderService {
	
	private QaReaderDao qaReaderDao;

	public QaReaderDao getQaReaderDao() {
		return qaReaderDao;
	}

	public void setQaReaderDao(QaReaderDao qaReaderDao) {
		this.qaReaderDao = qaReaderDao;
	}
	
	public void queryQaReaderByPage(Page pageInfo){
		DynamicDataSourceHolder.setDbType(DynamicDataSourceHolder.DB_TYPE_R);
		int totalCount = qaReaderDao.countQaReader();
		if(totalCount>0){
			List<QaReader> qaReaders = qaReaderDao.queryQaReaderByPage(Page.getStartOfPage(pageInfo.getPage(), pageInfo.getRows()), pageInfo.getRows());
			pageInfo.setDataRows(qaReaders);
		}
		pageInfo.setRecords(totalCount);
		pageInfo.setTotal((int)Page.getTotalPageCount(totalCount, pageInfo.getRows()));
	}

}
