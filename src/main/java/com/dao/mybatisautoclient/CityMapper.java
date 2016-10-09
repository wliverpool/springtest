package com.dao.mybatisautoclient;

import com.pojo.mybatisauto.City;

public interface CityMapper {
    int deleteByPrimaryKey(Integer ID);

    int insert(City record);

    int insertSelective(City record);

    City selectByPrimaryKey(Integer ID);

    int updateByPrimaryKeySelective(City record);

    int updateByPrimaryKey(City record);
}