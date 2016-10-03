package com.dao.atomikos;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.pojo.TmpUser;

public class TmpUserMapper implements RowMapper<TmpUser>{
	
	@Override
	public TmpUser mapRow(ResultSet rs, int row) throws SQLException {
		TmpUser t = new TmpUser();
		t.setOrg(rs.getString("org_id"));
		t.setOrgId(rs.getLong("orgid"));
		t.setUserId(rs.getLong("user_id"));
		return t;
	}

}
