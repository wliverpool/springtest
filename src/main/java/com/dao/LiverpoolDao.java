package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

import com.pojo.Team;

public class LiverpoolDao {
	
	private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
    
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public void save(final Team lfc) {
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement p = con.prepareStatement("insert into Team (id,name,no,position) values (?,?,?,?)");
				p.setLong(1, lfc.getId());
				p.setString(2, lfc.getName());
				p.setInt(3, lfc.getNo());
				p.setString(4, lfc.getPosition());
				return p;
			}
		});
	}
	
	public void updateNameById(final Long id,final String name){
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement p = con.prepareStatement("update Team set name = ? where id = ?");
				p.setString(1, name);
				p.setLong(2, id);
				return p;
			}
		});
	}
	
	public Team getById(Long id) {//jdbcTemplate.getDataSource()
    	return jdbcTemplate.queryForObject("SELECT id,name,no,position FROM Team where id=?", new Object[]{id},new LiverpoolMapper());
    }

}
