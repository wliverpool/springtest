package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;

public class BaseDao {
	
	protected JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
    
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public int getTotalCount(final String countSql,final Object[] conditions){
		return jdbcTemplate.execute(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection conn)throws SQLException {
				PreparedStatement ps = conn.prepareStatement(countSql);
				if(null!=conditions&&conditions.length>0){
					for(int i=0;i<conditions.length;i++){
						ps.setObject(i+1, conditions[i]);
					}
				}
				return ps;  
			}
			
		}, new PreparedStatementCallback<Integer>() {

			@Override
			public Integer doInPreparedStatement(PreparedStatement pstmt)throws SQLException, DataAccessException {	
				pstmt.execute();  
				ResultSet rs = pstmt.getResultSet();  
				if(rs.next()){
					return rs.getInt(1); 
				}else{
					return 0;
				}
				
				
			}
			
		});
	}
	
	/**
	 * 根据查询sql生成统计总数的sql
	 * @param queryString  查询sql
	 * @return
	 */
	public String getCountString(String queryString) {
		String countQueryString = "select count(*) " + removeSelect(removeOrders(queryString));

		countQueryString = hasGroupBy(countQueryString);

		return countQueryString;
	}
	
	/**
	 * 如果统计sql中有group by条件,生成有group by条件也能运行的sql
	 * @param countQueryString  统计sql
	 * @return
	 */
	public String hasGroupBy(String countQueryString) {
		Pattern p = Pattern.compile("group\\s*by[\\w|\\W|\\s|\\S]*", 2);
		Matcher m = p.matcher(countQueryString);
		while (m.find()) {
			countQueryString = "select count(*) from (" + countQueryString+ ")";
		}

		return countQueryString;
	}
	
	/**
	 * 除去查询sql中的排序条件
	 * @param sql   查询sql
	 * @return
	 */
	public String removeOrders(String sql) {
		System.out.println("sql:" + sql);
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", 2);
		Matcher m = p.matcher(sql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}
	
	/**
	 * 除去查询sql中的select字段部分
	 * @param sql   查询sql
	 * @return
	 */
	public String removeSelect(String sql) {
		//System.out.println("sql:" + sql);
		int beginPos = sql.toLowerCase().indexOf("from");
		//Assert.isTrue(beginPos != -1, " hql : " + sql+ " must has a keyword 'from'");
		return sql.substring(beginPos);
	}
	
	public String getPageSQL(String sql, int startIndex, int pageSize) {
		//加上分页条件
		String sql2 = sql + " limit " + startIndex +"," + pageSize;
		return sql2;
	}

}
