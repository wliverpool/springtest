package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.mysql.jdbc.Statement;
import com.pojo.Page;
import com.pojo.UserFile;

/**
 * 
 * 功能描述： 用户上传下载文件操作DAO<br>
 * @author 吴福明
 */
public class UserFileDao extends BaseDao{

	/**
	 * 保存上传文件
	 * 
	 * @param userFile
	 */
	public void save(final UserFile userFile){
		KeyHolder key=new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement p = con.prepareStatement("INSERT INTO T_USER_FILE(USER_ID,OPERATE_TIME,IP_ADDRESS,FILE_NAME,FILE_SIZE,FILE_PATH,UPLOAD_TYPE,REMARK) VALUES (?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
				p.setLong(1, userFile.getUserId());
				p.setObject(2, userFile.getOperateTime());
				p.setString(3, userFile.getIpAddress());
				p.setString(4, userFile.getFileName());
				p.setString(5, userFile.getFileSize());
				p.setString(6, userFile.getFilePath());
				p.setInt(7, userFile.getUploadType());
				p.setString(8, userFile.getRemark());
				return p;
			}
		},key);
	}

	/**
	 * 查询用户上传文件列表
	 * 
	 * @param map
	 * @return List
	 */
	public UserFile findFileById(Long Id){
		return jdbcTemplate.queryForObject("SELECT ID,USER_ID,OPERATE_TIME,IP_ADDRESS,FILE_NAME,FILE_SIZE,FILE_PATH,UPLOAD_TYPE,REMARK FROM t_user_file where ID = ?", new Object[]{Id},new UserFileMapper());
	}
	
	public List<UserFile> findList(Map<String, Object> map){
		String originalSql = "SELECT ID,USER_ID,b.USER_NAME,OPERATE_TIME,IP_ADDRESS,FILE_NAME,FILE_SIZE,FILE_PATH,UPLOAD_TYPE,REMARK FROM t_user_file a,t_user b where a.user_id=b.uid ";
		final String sql = generateFindSql(originalSql,map);
		final Object[] conditions = transferMap(map);
		List<UserFile> list = jdbcTemplate.execute(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection conn)throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql);
				if(null!=conditions&&conditions.length>0){
					for(int i=0;i<conditions.length;i++){
						ps.setObject(i+1, conditions[i]);
					}
				}
				return ps;  
			}
			
		}, new PreparedStatementCallback<List<UserFile>>() {

			@Override
			public List<UserFile> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.execute();  
				ResultSet rs = ps.getResultSet();  
				List<UserFile> userFileList = new ArrayList<UserFile>();
				while(rs.next()){
					UserFile userFile = convert(rs);
					userFileList.add(userFile);
				}
				return userFileList; 
			}
			
		});
		return list;
	}

	/**
	 * 查询用户上传文件列表
	 * 
	 * @param map
	 * @return List<UserFileDTO>
	 */
	public Page findPage(Map<String, Object> map,int pageNo,int pageSize){
		String originalSql = "SELECT ID,USER_ID,b.USER_NAME,OPERATE_TIME,IP_ADDRESS,FILE_NAME,FILE_SIZE,FILE_PATH,UPLOAD_TYPE,REMARK FROM t_user_file a,t_user b where a.user_id=b.uid ";
		String sql = generateFindSql(originalSql,map);
		final Object[] conditions = transferMap(map);
		//计算分页信息
		String countString = getCountString(sql);
		int totalCount = getTotalCount(countString,conditions);
		if (totalCount <= 0) {
			return new Page(0,pageSize,0,0,null);
		}
		long totalPageCount = Page.getTotalPageCount(totalCount, pageSize);
		if (pageNo > totalPageCount) {
			pageNo = Long.valueOf(totalPageCount).intValue();
		}
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		//获取分页查询sql
		final String exeSql = getPageSQL(sql, startIndex, pageSize);
		
		List<UserFile> list = jdbcTemplate.execute(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection conn)throws SQLException {
				PreparedStatement ps = conn.prepareStatement(exeSql);
				if(null!=conditions&&conditions.length>0){
					for(int i=0;i<conditions.length;i++){
						ps.setObject(i+1, conditions[i]);
					}
				}
				return ps;  
			}
			
		}, new PreparedStatementCallback<List<UserFile>>() {

			@Override
			public List<UserFile> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.execute();  
				ResultSet rs = ps.getResultSet();  
				List<UserFile> userFileList = new ArrayList<UserFile>();
				while(rs.next()){
					UserFile userFile = convert(rs);
					userFileList.add(userFile);
				}
				return userFileList; 
			}
			
		});
		
		Page pageInfo = new Page(pageNo, pageSize, (int)totalPageCount, totalCount, list);
		
		return pageInfo;
	}
	
	private String generateFindSql(String originalSql,Map<String, Object> map){
		StringBuffer sb = new StringBuffer(originalSql);
		if(null!=map&&map.keySet().size()>0){
			int wherePosition = originalSql.indexOf("where");
			if(wherePosition<0){
				sb.append(" where ");
			}
			Iterator<String> it = map.keySet().iterator();
			int i = 0;
			while(it.hasNext()){
				String key = it.next();
				if(wherePosition>0||(wherePosition<0&&i>0)){
					sb.append(" and ");
				}
				sb.append(key+"=? ");
				i++;
			}
		}
		return sb.toString();
	}
	
	private Object[] transferMap(Map<String, Object> map){
		if(null!=map&&map.keySet().size()>0){
			Object[] conditions = new Object[map.keySet().size()];
			Iterator<String> it = map.keySet().iterator();
			int i = 0;
			while(it.hasNext()&&i<conditions.length){
				String key = it.next();
				conditions[i] = map.get(key);
				i++;
			}
			return conditions;
		}else{
			return new Object[0];
		}
		
	}
	
	private UserFile convert(ResultSet rs)throws SQLException{
		UserFile userFile = new UserFile();
		userFile.setId(rs.getLong("ID"));
		userFile.setFileName(rs.getString("FILE_NAME"));
		userFile.setFilePath(rs.getString("FILE_PATH"));
		userFile.setFileSize(rs.getString("FILE_SIZE"));
		userFile.setIpAddress(rs.getString("IP_ADDRESS"));
		userFile.setOperateTime(rs.getTimestamp("OPERATE_TIME"));
		userFile.setRemark(rs.getString("REMARK"));
		userFile.setUploadType(rs.getInt("UPLOAD_TYPE"));
		userFile.setUserId(rs.getLong("USER_ID"));
		userFile.setUserName(rs.getString("USER_NAME"));
		return userFile;
	}
}