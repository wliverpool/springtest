package com.dao.atomikos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

import com.pojo.LiveWarm;
import com.pojo.TmpUser;

public class TmpUserDao {
	
	private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
    
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public void save(final TmpUser user) {
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement p = con.prepareStatement("insert into tmp_user (user_id,org_id,orgid) values (?,?,?)");
				p.setLong(1, user.getUserId());
				p.setString(2, user.getOrg());
				p.setLong(3, user.getOrgId());
				return p;
			}
		});
	}
	
	public TmpUser getById(Long userId){
		return jdbcTemplate.queryForObject("SELECT user_id,org_id,orgid FROM live_warm where user_id=?", new Object[]{userId},new TmpUserMapper());
	}

}
