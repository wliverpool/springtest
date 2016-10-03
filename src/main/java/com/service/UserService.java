package com.service;

import java.util.Set;

import com.pojo.User;

/**
 * 用户服务接口
 * @author 吴福明
 *
 */
public interface UserService {
	
	/**
	 * 创建账户
	 * @param user  用户
	 * @return   用户
	 */
	public User createUser(User user);

	/**
	 * 修改密码
	 * @param userId   用户id
	 * @param newPassword   新密码
	 */
	public void changePassword(Long userId, String newPassword);

	/**
	 * 添加用户-角色关系
	 * @param userId   用户id
	 * @param roleIds   角色id数组
	 */
	public void correlationRoles(Long userId, Long... roleIds); 

	/**
	 * 移除用户-角色关系
	 * @param userId    用户id
	 * @param roleIds   角色id数组
	 */
	public void uncorrelationRoles(Long userId, Long... roleIds);

	/**
	 * 根据用户名查找用户
	 * @param username   用户名
	 * @return   用户
	 */
	public User findByUsername(String username);

	/**
	 * 根据用户名查找其角色集合
	 * @param username    用户名
	 * @return    用户角色集合
	 */
	public Set<String> findRoles(String username);

	/**
	 * 根据用户名查找其权限集合
	 * @param username   用户名
	 * @return     用户权限集合
	 */
	public Set<String> findPermissions(String username); 
}