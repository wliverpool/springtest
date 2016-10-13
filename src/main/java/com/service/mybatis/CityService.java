package com.service.mybatis;

import com.dao.mybatisauto.CityMapper;
import com.pojo.mybatisauto.City;

public class CityService {
	
	private CityMapper cityMapper;

	public CityMapper getCityMapper() {
		return cityMapper;
	}

	public void setCityMapper(CityMapper cityMapper) {
		this.cityMapper = cityMapper;
	}
	
	public void saveCity(City city){
		cityMapper.insert(city);
		city.setName(city.getName()+"_update_update");
		cityMapper.updateByPrimaryKey(city);
	}

}
