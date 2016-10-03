package com.util;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * 自定义密码验证服务,限制短时间内重复验证次数
 * @author Administrator
 *
 */
public class RetryLimitHashedCredentialsMatcher extends SimpleCredentialsMatcher {

	//在ehcache的缓存种获取密码重试的缓存
	private Cache<String, AtomicInteger> passwordRetryCache;

    public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String username = (String)token.getPrincipal();

        //在缓存中查找到当前用户的密码重试信息
        AtomicInteger retryCount = passwordRetryCache.get(username);
        if(retryCount == null) {
            retryCount = new AtomicInteger(0);
            passwordRetryCache.put(username, retryCount);
        }
        
        if(retryCount.incrementAndGet() > 5) {
            //重试次数>5
            throw new ExcessiveAttemptsException();
        }
        //验证成功
        boolean matches = super.doCredentialsMatch(token, info);
        if(matches) {
            //在缓存中清除存储的当前用户重试验证的信息
            passwordRetryCache.remove(username);
        }
        return matches;
    }
}