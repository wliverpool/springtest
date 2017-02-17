package com.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.dao.UserFileDao;
import com.dao.util.DynamicDataSourceHolder;
import com.pojo.Page;
import com.pojo.UserFile;
import com.util.LogUtil;

/**
 * 
 * 功能描述： 用户上传下载文件操作DAO<br>
 * @author 吴福明
 */
@Service("userFileService")
public class UserFileService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserFileDao userFileDao;
	
	/**
	 * 保存上传文件
	 * 
	 * @param userFile
	 */
	@CachePut(value={"userFile"},key="#userFile.id")
	public void save(UserFile userFile){
		DynamicDataSourceHolder.setDbType(DynamicDataSourceHolder.DB_TYPE_RW);
		userFileDao.save(userFile);
		logger.info(LogUtil.getLogStr("saveUserFile","200",userFile,"",""));
	}

	/**
	 * 查询用户上传文件列表
	 * 
	 * @param map
	 * @return List<UserFile>
	 */
	public UserFile findFileById(Long id){
		DynamicDataSourceHolder.setDbType(DynamicDataSourceHolder.DB_TYPE_RW);
		return userFileDao.findFileById(id);
	}
	
	/**
	 * 根据查询条件查询上传文件列表
	 * @param map
	 * @return
	 */
	public List<UserFile> findList(Map<String,Object> map){
		DynamicDataSourceHolder.setDbType(DynamicDataSourceHolder.DB_TYPE_RW);
		return userFileDao.findList(map);
	}
	
	/**
	 * 根据查询条件分页查询用户上传文件列表
	 * 
	 * @param map
	 * @return List<UserFile>
	 */
	public Page findPage(Map<String, Object> map,int pageNo){
		DynamicDataSourceHolder.setDbType(DynamicDataSourceHolder.DB_TYPE_RW);
		logger.info(LogUtil.getLogStr("findUserFileList","200",map,"",""));
		Page pageInfo = userFileDao.findPage(map,pageNo,10);
		return pageInfo;
	}
}