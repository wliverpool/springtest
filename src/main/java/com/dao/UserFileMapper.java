package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.pojo.UserFile;

public class UserFileMapper implements RowMapper<UserFile> {

	@Override
	public UserFile mapRow(ResultSet rs, int row) throws SQLException {
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
		return userFile;
	}

}
