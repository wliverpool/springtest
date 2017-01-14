package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.pojo.QaReader;
import com.util.SqlUtil;

public class QaReaderDao {
	
	private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
    
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public int countQaReader(){
		String sql = "select a.read_id readId,a.uid uId,a.create_time createTime,b.name,c.title,c.bigType from world.qa_read a,world.city b left join world.clobtest c on c.id = b.id where a.read_id = b.id";
		sql = SqlUtil.generateCountSql(sql);
		int totalCount = jdbcTemplate.query(sql, new ResultSetExtractor<Integer>(){
			 @Override  
	         public Integer extractData(ResultSet rs) throws SQLException,DataAccessException {  
				if(rs.next()){
					return rs.getInt(1);
				}
				return 0;
			 }
		});
		return totalCount;
	}
	
	public List<QaReader> queryQaReaderByPage(int begin,int length) {
		String sql = "select a.read_id readId,a.uid uId,a.create_time createTime,b.name,c.title,c.bigType from world.qa_read a,world.city b left join world.clobtest c on c.id = b.id where a.read_id = b.id";
    	sql = SqlUtil.createPageSql("mysql", sql, begin, begin+length);
		List<QaReader> qaReaders = jdbcTemplate.query(sql, new RowMapper<QaReader>() {
            public QaReader mapRow(ResultSet rs, int indexRow) throws SQLException {
            	QaReader t = new QaReader();
                t.setReadId(rs.getLong(1));
                t.setuId(rs.getLong(2));
                t.setCreateTime(rs.getTimestamp(3));
                t.setName(rs.getString(4));
                t.setTitle(StringUtils.isNotBlank(rs.getString(5))?rs.getString(5):"");
                t.setBigType(StringUtils.isNotBlank(rs.getString(6))?rs.getString(6):"");
                return t;
            }
        });
		return qaReaders;
    }

}
