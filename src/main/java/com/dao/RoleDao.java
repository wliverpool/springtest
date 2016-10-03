package com.dao;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.pojo.Role;

/**
 * 测试spring集成encache缓存
 * @author 吴福明
 *
 */
@Cacheable("cache1")
public interface RoleDao {

public Role find(String id);
	
	public List<Role> findAll();
	
	public Role findById(String id);

}
