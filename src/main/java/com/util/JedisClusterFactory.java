package com.util;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

/**
 * 实现spring集成redis集群的Factory
 * 
 * @author 吴福明
 *
 */
public class JedisClusterFactory implements FactoryBean<JedisCluster>, InitializingBean {

	private Resource addressConfig;
	private String addressKeyPrefix;
	private JedisCluster jedisCluster;
	private Integer timeout;
	private Integer maxRedirections;
	private GenericObjectPoolConfig genericObjectPoolConfig;
	private Pattern p = Pattern.compile("^.+[:]\\d{1,5}\\s*$");

	@Override
	public void afterPropertiesSet() throws Exception {
		Set<HostAndPort> haps = this.parseHostAndPort();
		jedisCluster = new JedisCluster(haps, timeout, maxRedirections, genericObjectPoolConfig);
	}

	@Override
	public JedisCluster getObject() throws Exception {
		System.out.println(jedisCluster == null);
		return jedisCluster;
	}

	@Override
	public Class<? extends JedisCluster> getObjectType() {
		return (this.jedisCluster != null ? this.jedisCluster.getClass() : JedisCluster.class);
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	/**
	 * 从配置文件中获取redis集群信息
	 * 
	 * @return redis集群地址set
	 * @throws Exception
	 */
	private Set<HostAndPort> parseHostAndPort() throws Exception {
		try {
			// 读取redis集群地址配置文件
			Properties prop = new Properties();
			prop.load(this.addressConfig.getInputStream());
			Set<HostAndPort> haps = new HashSet<HostAndPort>();
			for (Object key : prop.keySet()) {
				// 配置文件ip端口格式验证
				if (!((String) key).startsWith(addressKeyPrefix)) {
					continue;
				}
				String val = (String) prop.get(key);
				boolean isIpPort = p.matcher(val).matches();
				if (!isIpPort) {
					throw new IllegalArgumentException("ip或端口不正确!!!");
				}
				// 生成redis集群地址set
				String[] ipAndPort = val.split(":");
				HostAndPort hap = new HostAndPort(ipAndPort[0], Integer.parseInt(ipAndPort[1]));
				haps.add(hap);
			}
			return haps;
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (Exception e) {
			throw new Exception("解析jedis集群配置文件失败", e);
		}
	}

	public Resource getAddressConfig() {
		return addressConfig;
	}

	public void setAddressConfig(Resource addressConfig) {
		this.addressConfig = addressConfig;
	}

	public String getAddressKeyPrefix() {
		return addressKeyPrefix;
	}

	public void setAddressKeyPrefix(String addressKeyPrefix) {
		this.addressKeyPrefix = addressKeyPrefix;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public Integer getMaxRedirections() {
		return maxRedirections;
	}

	public void setMaxRedirections(Integer maxRedirections) {
		this.maxRedirections = maxRedirections;
	}

	public GenericObjectPoolConfig getGenericObjectPoolConfig() {
		return genericObjectPoolConfig;
	}

	public void setGenericObjectPoolConfig(GenericObjectPoolConfig genericObjectPoolConfig) {
		this.genericObjectPoolConfig = genericObjectPoolConfig;
	}

}
