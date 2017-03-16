package com.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.util.ConfigDataChangeListener;
import com.util.DynamicPropertiesHelper;
import com.util.DynamicPropertiesHelperFactory;
import com.lock.SimpleDistributedLock;
import com.util.ConfigChangeSubscriber;
import com.util.ConfigChildrenChangeListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ZookeeperConfigTest {
	
	@Autowired
	private ZkClient zkClient;
	@Autowired
	private ConfigChangeSubscriber zkConfig;
	@Autowired
	private DynamicPropertiesHelperFactory helperFactory;
	@Autowired
	private SimpleDistributedLock simpleDistributedLock;
	
	@Test
	public void testCreateNode(){
		String path = zkClient.create("/llyg/conf/cn", "测试中文", CreateMode.PERSISTENT); 
		//zkClient.addAuthInfo("auth", DigestAuthenticationProvider.generateDigest("root:wfmhbbwt").getBytes());
		assertNotNull(path);
		//输出创建节点的路径  
        System.out.println("created path:"+path);  
	}
	
	@Test
	public void testGetNodeValue(){
		Stat stat = new Stat();  
		String node = (String)zkClient.readData("/llyg/conf/testUserNode",stat);  
		assertNotNull(node);
        System.out.println(node);  
        System.out.println(stat);  
	}
	
	@Test
	public void testNodeExist(){
		//返回 true表示节点存在 ，false表示不存在  
		boolean e = zkClient.exists("/llyg/conf/testUserNode"); 
		assertTrue(e);
	}
	
	@Test
	public void updateNode(){
		zkClient.writeData("/llyg/conf/testUserNode", "updateValue");  
	}
	
	@Test
	public void deleteNode(){
		//删除单独一个节点，返回true表示成功  
        boolean e1 = zkClient.delete("/llyg/conf/testUserNode");  
        //删除含有子节点的节点  
        //boolean e2 = zkClient.deleteRecursive("/test");  
        assertTrue(e1);
	}
	
	@Test
	public void testGetChildren() throws InterruptedException {
		List<String> list = zkClient.getChildren("/llyg/conf");
		assertTrue(list.size()>0);
		for (String s : list) {
			System.out.println("children:" + s);
		}
	}
	
	@Test
	public void testSubscribeDataChange() throws IOException, InterruptedException {
		final CountDownLatch latch = new CountDownLatch(1);
		this.zkConfig.subscribeDataChanges("testUserNode", new ConfigDataChangeListener() {
			public void configChanged(String key, String value) {
				System.out.println("接收到数据变更通知: key=" + key + ", value=" + value);
				latch.countDown();
			}
		});
		zkClient.writeData("/llyg/conf/testUserNode", "newTest");
		boolean notified = latch.await(90L, TimeUnit.SECONDS);
		if (!notified){
			fail("客户端没有收到变更通知");
		}
	}
	
	@Test
	public void testSubscribeChildrenChange() throws IOException, InterruptedException {
		final CountDownLatch latch = new CountDownLatch(1);
		this.zkConfig.subscribeChildrenChanges(new ConfigChildrenChangeListener() {
			public void configChanged(String key, List<String> currentChilds) {
				System.out.println("接收到数据变更通知: key=" + key);
				System.out.println("新子节点列表:");
				for (String s : currentChilds) {
					System.out.println("children:" + s);
				}
				latch.countDown();
			}
		});
		zkClient.create("/llyg/conf/newNode222", "新建测试", CreateMode.PERSISTENT); 
		boolean notified = latch.await(90L, TimeUnit.SECONDS);
		if (!notified){
			fail("客户端没有收到变更通知");
		}
	}
	
	@Test
	public void testDelSubscribe() throws IOException, InterruptedException {
		final CountDownLatch latch = new CountDownLatch(1);
		this.zkConfig.subscribeChildrenChanges(new ConfigChildrenChangeListener() {
			public void configChanged(String key, List<String> currentChilds) {
				System.out.println("接收到数据变更通知: key=" + key);
				System.out.println("新子节点列表:");
				for (String s : currentChilds) {
					System.out.println("children:" + s);
				}
				latch.countDown();
			}
		});
		this.zkConfig.subscribeDataChanges("newNode", new ConfigDataChangeListener() {
			public void configChanged(String key, String value) {
				System.out.println("接收到数据变更通知: key=" + key + ", value=" + value);
				latch.countDown();
			}
		});
		zkClient.delete("/llyg/conf/newNode"); 
		boolean notified = latch.await(90L, TimeUnit.SECONDS);
		if (!notified){
			fail("客户端没有收到变更通知");
		}
	}
	
	@Test
	public void testRegisterListener() throws InterruptedException {
		DynamicPropertiesHelper helper = helperFactory.getHelper("test.properties");
		System.out.println("liverpool:" + helper.getProperty("liverpool"));
		System.out.println("test:" + helper.getProperty("test"));
		assertEquals(helper.getProperty("liverpool"), "555555");
		this.zkClient.writeData("/llyg/conf/test.properties", "test=6666666\r\nliverpool=8888888");
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertEquals(helper.getProperty("liverpool"), "8888888");
		assertEquals(helper.getProperty("test"), "6666666");
	}
	
	@Test
	public void testDistributeLock(){
		try {  
			simpleDistributedLock.acquire();  
            System.out.println("Client1 locked");  
            Thread.sleep(60000);  
            simpleDistributedLock.release();             
            System.out.println("Client1 released lock");                              
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
	}

}
