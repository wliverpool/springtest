package com.lock;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;

/**
 * 简单分布式锁的实现,利用zookeeper中创建的EphemeralSequential节点会有序列的特性
 * 判断当前客户端创建的节点是否是锁列表中值最小的那个如果是就获取锁，如果不是就等待其他客户端执行完成。
 * 当获取锁之后执行操作，在完成操作之后删除本客户端创建的锁节点，作为释放锁的操作。
 * @author mittermeyer
 *
 */
public class SimpleDistributedLock implements DistributedLock {
	
	private BaseDistributedLock baseDistributedLock;
	private String selfLockPath;

	public BaseDistributedLock getBaseDistributedLock() {
		return baseDistributedLock;
	}

	public void setBaseDistributedLock(BaseDistributedLock baseDistributedLock) {
		this.baseDistributedLock = baseDistributedLock;
	}

	@Override
	public void acquire() throws Exception {
		selfLockPath = baseDistributedLock.attemptLock(0, TimeUnit.MILLISECONDS);
	}

	@Override
	public boolean acquire(long time, TimeUnit unit) throws Exception {
		selfLockPath = baseDistributedLock.attemptLock(time, unit);
		if(StringUtils.isNotBlank(selfLockPath)){
			return true;
		}
		return false;
	}

	@Override
	public void release() throws Exception {
		if(StringUtils.isNotBlank(selfLockPath)){
			baseDistributedLock.releaseLock(selfLockPath);
		}
	}

}
