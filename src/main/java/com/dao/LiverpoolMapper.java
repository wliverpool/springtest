package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.pojo.Team;

public class LiverpoolMapper implements RowMapper<Team> {

	@Override
	public Team mapRow(ResultSet rs, int row) throws SQLException {
		Team lfc = new Team();
		lfc.setId(rs.getLong("id"));
		lfc.setName(rs.getString("name"));
		lfc.setNo(rs.getInt("no"));
		lfc.setPosition(rs.getString("position"));
		return lfc;
	}

}
