package com.dao.mybatisauto;

import java.util.List;

import com.pojo.mybatisauto.MybatisClob;

public interface MybatisClobMapper {

	public int insertClobTest(MybatisClob test);

	public List<MybatisClob> getAllClobTest();

	public MybatisClob getClobTestById(int id);

}
