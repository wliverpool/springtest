package com.dao;

import java.util.Set;

import com.pojo.User;

/**
 * 用户dao接口
 * @author 吴福明
 *
 */
public interface UserDao {

	/**
	 * 创建用户
	 * @param user   用户
	 * @return   用户
	 */
	public User createUser(User user);

	/**
	 * 更新用户
	 * @param user  用户
	 */
	public void updateUser(User user);

	/**
	 * 删除用户
	 * @param userId   用户id
	 */
	public void deleteUser(Long userId);

	/**
	 * 添加用户角色关系
	 * @param userId  用户id
	 * @param roleIds   角色id数组
	 */
	public void correlationRoles(Long userId, Long... roleIds);

	/**
	 * 删除用户角色关系
	 * @param userId   用户id
	 * @param roleIds   角色id数组
	 */
	public void uncorrelationRoles(Long userId, Long... roleIds);

	/**
	 * 根据用户id查询用户
	 * @param userId  用户id
	 * @return   用户
	 */
	User findOne(Long userId);

	/**
	 * 根据用户名查询用户
	 * @param username  用户名
	 * @return   用户
	 */
	User findByUsername(String username);

	/**
	 * 根据用户名查询该用户所具有角色集合
	 * @param username   用户名
	 * @return    用户角色集合
	 */
	Set<String> findRoles(String username);

	/**
	 * 根据用户名查询该用户所具有权限集合
	 * @param username   用户名
	 * @return   用户权限集合
	 */
	Set<String> findPermissions(String username);
}