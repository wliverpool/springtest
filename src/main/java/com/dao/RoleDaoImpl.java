package com.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.pojo.Role;

/**
 * 角色dao
 * 
 * @author 吴福明
 *
 */
public class RoleDaoImpl implements RoleDao {

	@Cacheable(value={"cache1"},condition="#id==\"22\"")
	public Role find(String id) {
		try {
			Thread.sleep(10000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Role("id1", "name1", true);
	}

	public List<Role> findAll() {
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Role> list = new ArrayList<Role>();
		list.add(new Role("id1","name1", true));
		list.add(new Role("id2","name2", false));
		list.add(new Role("id3","name3", true));
		return list;
	}

	@Cacheable({"cache1","cache2"})
	public Role findById(String id) {
		try {
			Thread.sleep(10000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Role("id1", "name1", false);
	}

}