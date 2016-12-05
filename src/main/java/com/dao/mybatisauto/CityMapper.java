package com.dao.mybatisauto;

import com.pojo.mybatisauto.City;
import com.pojo.mybatisauto.CityExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CityMapper {
    int countByExample(CityExample example);

    int deleteByExample(CityExample example);

    int deleteByPrimaryKey(Integer ID);

    int insert(City record);

    int insertSelective(City record);

    List<City> selectByExample(CityExample example);

    City selectByPrimaryKey(Integer ID);

    int updateByExampleSelective(@Param("record") City record, @Param("example") CityExample example);

    int updateByExample(@Param("record") City record, @Param("example") CityExample example);

    int updateByPrimaryKeySelective(City record);
    
    int updateByPrimaryKey(City record);
    
    List<City> selectByCustomCondition(Map<String, Object> condition);
    
    int insertBatch(List<City> cities);
}