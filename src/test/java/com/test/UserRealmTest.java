package com.test;

import static org.junit.Assert.*;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Test;

public class UserRealmTest {

	@Test
	public void testLoginSuccess() {
		login("classpath:shiro.ini", "zhang", "1234");
		assertTrue(subject().isAuthenticated());
	}
	
	@Test(expected = UnknownAccountException.class)
	public void testLoginFailWithUnknownUsername() {
		login("classpath:shiro.ini", "zhang" + "1", "1234");
	}
	
	@Test(expected = IncorrectCredentialsException.class)
	public void testLoginFailWithErrorPassowrd() {
		login("classpath:shiro.ini", "zhang", "1234" + "1");
	}
	
	@Test(expected = LockedAccountException.class)
	public void testLoginFailWithLocked() {
		login("classpath:shiro.ini", "liu", "123");
	}
	
	@Test(expected = ExcessiveAttemptsException.class)
	public void testLoginFailWithLimitRetryCount() {
		for (int i = 1; i <= 5; i++) {
			try {
				login("classpath:shiro.ini", "zhang", "1234" + "1");
			} catch (Exception e) {
				
			}
		}
		login("classpath:shiro.ini", "zhang", "1234");

		// 需要清空缓存，否则后续的执行就会遇到问题(或者使用一个全新账户测试)
	}

	
	@Test
	public void testHasRole() {
		login("classpath:shiro.ini", "cheng", "123");
		assertTrue(subject().hasRole("admin"));
	}

	
	@Test
	public void testNoRole() {
		login("classpath:shiro.ini", "niwg", "12345");
		assertFalse(subject().hasRole("admin"));
	}

	
	@Test
	public void testHasPermission() {
		login("classpath:shiro.ini", "cheng", "123");
		assertTrue(subject().isPermittedAll("user:create", "menu:create"));
	}

	@Test
	public void testNoPermission() {
		login("classpath:shiro.ini", "niwg", "12345");
		assertFalse(subject().isPermitted("user:create"));
	}
	
	@After
    public void tearDown() throws Exception {
		//退出时请解除绑定Subject到线程 否则对下次测试造成影响
        ThreadContext.unbindSubject();
    }

	protected void login(String configFile, String username, String password) {
		// 1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory(configFile);

		// 2、得到SecurityManager实例 并绑定给SecurityUtils
		org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);

		// 3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username,password);

		subject.login(token);
	}

	public Subject subject() {
		return SecurityUtils.getSubject();
	}

}