package com.util;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;


/**
 * memcached连接工具类
 * @author 吴福明
 *
 */
public class MemcachedUtils {
	
	private static MemCachedClient client;
	
	static{
		//配置memcached
		SockIOPool sockIOPool = SockIOPool.getInstance();
		//memcached服务器地址
		sockIOPool.setServers(new String[]{"192.168.3.12:11211"});
		//memcached服务器权重
		sockIOPool.setWeights(new Integer[]{3});
		//当一个memcached服务器失效时是否链接服务器地址中其他memcached服务器
		sockIOPool.setFailover(false);
		//初始连接数
		sockIOPool.setInitConn(10);
		//最小连接数
		sockIOPool.setMinConn(10);
		//最大连接数
		sockIOPool.setMaxConn(100);
		//自查现成周期工作时,每次休眠时间
		sockIOPool.setMaintSleep(30);
		//为true时将不会等待memcached服务器返回上一次发送数据的确认信息,直接再次发送写入数据,为false时则会等待
		sockIOPool.setNagle(false);
		//socket阻塞读取数据的超时时间
		sockIOPool.setSocketTO(3000);
		//否是检查服务器是否失效
		sockIOPool.setAliveCheck(true);
		//最大处理时间
		sockIOPool.setMaxIdle(1000*30*30);
		//建立连接时的超时时间
		sockIOPool.setSocketConnectTO(0);
		sockIOPool.initialize();
		
		if(client==null){
			client=new MemCachedClient();
			//基本类型的数据全部转换成string存储
			client.setPrimitiveAsString(true);
		}
	}
	
	private MemcachedUtils(){
		
	}
	
	/**
	 * 向memcached服务器添加键值对,如果键已存在则替换原先的值
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean set(String key,Object value){
		try {
			return client.set(key,value);
		} catch (Exception e) {
			MemcachedLogUtils.writeLog("Memcached set 方法报错, key值:" + key +"\r\n" + exceptionWrite(e));
			return false;
		}
	}
	
	/**
	 * 向memcached服务器添加键值对,设置键值对的过期时间,如果键已存在则替换原先的值
	 * @param key
	 * @param value
	 * @param expire 单位毫秒
	 * @return
	 */
	public static boolean set(String key,Object value,Date expire){
		try {
			return client.set(key,value,expire);
		} catch (Exception e) {
			MemcachedLogUtils.writeLog("Memcached set 方法报错, key值:" + key +"\r\n" + exceptionWrite(e));
			return false;
		}
	}
	
	/**
	 * 向memcached服务器添加键值对,只有当服务器中不存在键时才能添加成功
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean add(String key,Object value){
		try {
			if(get(key)!=null){
				MemcachedLogUtils.writeLog("Memcached add 方法报错, key值:" + key +"\r\n" + exceptionWrite(new Exception("memcached服务器中已经存在该键值对")));
				return false;
			}else{
				return client.add(key,value);
			}
		} catch (Exception e) {
			MemcachedLogUtils.writeLog("Memcached add 方法报错, key值:" + key +"\r\n" + exceptionWrite(e));
			return false;
		}
	}
	
	/**
	 * 向memcached服务器添加键值对,设置键值对的过期时间,只有当服务器中不存在键时才能添加成功
	 * @param key
	 * @param value
	 * @param expire  单位毫秒
	 * @return
	 */
	public static boolean add(String key,Object value,Date expire){
		try {
			if(get(key)!=null){
				MemcachedLogUtils.writeLog("Memcached add 方法报错, key值:" + key +"\r\n" + exceptionWrite(new Exception("memcached服务器中已经存在该键值对")));
				return false;
			}else{
				return client.add(key,value,expire);
			}
		} catch (Exception e) {
			MemcachedLogUtils.writeLog("Memcached add 方法报错, key值:" + key +"\r\n" + exceptionWrite(e));
			return false;
		}
	}
	
	/**
	 * 向memcached服务器更新键值对,只有当服务器中存在键时才能更新成功
	 * @param key
	 * @param newValue
	 * @return
	 */
	public static boolean replace(String key,Object newValue){
		try {
			return client.replace(key,newValue);
		} catch (Exception e) {
			MemcachedLogUtils.writeLog("Memcached replace 方法报错, key值:" + key +"\r\n" + exceptionWrite(e));
			return false;
		}
	}
	
	/**
	 * 向memcached服务器更新键值对,设置键值对的过期时间,只有当服务器中存在键时才能更新成功
	 * @param key
	 * @param newValue
	 * @return
	 */
	public static boolean replace(String key,Object newValue,Date expire){
		try {
			return client.replace(key,newValue,expire);
		} catch (Exception e) {
			MemcachedLogUtils.writeLog("Memcached replace 方法报错, key值:" + key +"\r\n" + exceptionWrite(e));
			return false;
		}
	}
	
	/**
	 * memcached服务器中删除键值对
	 * @param key
	 * @return
	 */
	public static boolean delete(String key){
		try {
			return client.delete(key);
		} catch (Exception e) {
			MemcachedLogUtils.writeLog("Memcached delete 方法报错, key值:" + key +"\r\n" + exceptionWrite(e));
			return false;
		}
	}
	
	/**
	 * 清除memcached服务器中所有缓存键值对
	 * @return
	 */
	public static boolean flushAll(){
		try {
			return client.flushAll();
		} catch (Exception e) {
			MemcachedLogUtils.writeLog("Memcached flushAll 方法报错" + "\r\n" + exceptionWrite(e));
			return false;
		}
	}
	
	/**
	 * 从memcached服务器中根据key获取对应值
	 * @param key
	 * @return
	 */
	public static Object get(String key){
		try {
			return client.get(key);
		} catch (Exception e) {
			MemcachedLogUtils.writeLog("Memcached get 方法报错, key值:" + key +"\r\n" + exceptionWrite(e));
		}
		return null;
	}
	
	/**
	 * 根据异常生成String类型的写入日志的异常信息
	 * @param e
	 * @return
	 */
	private static String exceptionWrite(Exception e){
		StringWriter writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		e.printStackTrace(printWriter);
		printWriter.flush();
		return writer.toString();
	}
	
	//内部静态类记录日志
	private static class MemcachedLogUtils{
		
		private static FileWriter fileWriter;
		private static BufferedWriter logWriter;
		//通过jvm的进程获取PID信息
		private final static String PID = ManagementFactory.getRuntimeMXBean().getName();
		
		//初始化日志写入位置
		static{
			try {
				String osName = System.getProperty("os.name");
				if(osName.contains("Windows")){
					fileWriter = new FileWriter("D:\\memcached.log",true);
				}else{
					fileWriter = new FileWriter("/usr/local/logs/memcached.log",true);
				}
				logWriter = new BufferedWriter(fileWriter);
			} catch (IOException e) {
				e.printStackTrace();
				try {
					if(fileWriter!=null){
						fileWriter.close();
					}
					if(logWriter!=null){
						logWriter.close();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		
		/**
		 * 写入日志方法
		 * @param logContent
		 */
		public static void writeLog(String logContent){
			try {
				logWriter.write("[" + PID + "]" + "- [" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "]\r\n" + logContent);
				logWriter.newLine();
				logWriter.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

}