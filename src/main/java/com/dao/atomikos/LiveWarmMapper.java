package com.dao.atomikos;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.pojo.LiveWarm;

public class LiveWarmMapper implements RowMapper<LiveWarm>{
	
	@Override
	public LiveWarm mapRow(ResultSet rs, int row) throws SQLException {
		LiveWarm l = new LiveWarm();
		l.setId(rs.getInt("id"));
		l.setVodId(rs.getString("vod_id"));
		l.setWebcastId(rs.getString("webcast_id"));
		return l;
	}

}
