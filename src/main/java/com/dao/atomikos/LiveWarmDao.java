package com.dao.atomikos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

import com.pojo.LiveWarm;

public class LiveWarmDao {
	
	private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
    
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public void save(final LiveWarm liveWarm) {
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement p = con.prepareStatement("insert into live_warm (webcast_id,vod_id) values (?,?)");
				p.setString(1, liveWarm.getWebcastId());
				p.setString(2, liveWarm.getVodId());
				return p;
			}
		});
	}
	
	public LiveWarm getById(int id){
		return jdbcTemplate.queryForObject("SELECT id,webcast_id,vod_id FROM live_warm where id=?", new Object[]{id},new LiveWarmMapper());
	}

}
