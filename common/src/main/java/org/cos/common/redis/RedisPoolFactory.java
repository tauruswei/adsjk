package org.cos.common.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.StreamEntryID;
import redis.clients.jedis.exceptions.JedisDataException;

import java.util.HashSet;
import java.util.Set;

@Service
public class RedisPoolFactory {

	@Value("${spring.redis.cluster.nodes}")
	private String clusterNodes;
	@Value("${spring.redis.password}")
	private String password;

	@Value("${spring.redis.stream.key}")
	private String key;
	@Value("${spring.redis.stream.group}")
	private String group;
	@Value("${spring.redis.stream.maxMount}")
	private int maxMount;



	@Bean
	public JedisCluster getJedisCluster() {
		Set<HostAndPort> jedisClusterNodes = new HashSet<>();
		for (String node : clusterNodes.split(",")) {
			String[] parts = node.split(":");
			jedisClusterNodes.add(new HostAndPort(parts[0], Integer.parseInt(parts[1])));
		}

		// 配置连接池
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(100);
		poolConfig.setMaxIdle(20);


		// 其他连接池参数设置...

		// 创建 JedisCluster 实例
		JedisCluster jedisCluster = new JedisCluster(jedisClusterNodes,2000, 2000, 5, password,  poolConfig);
		// 创建 stream、创建 group
		try {
			jedisCluster.xgroupCreate(key, group, StreamEntryID.LAST_ENTRY, true);

		} catch (JedisDataException e) {
			if (e.getMessage().contains("BUSYGROUP Consumer Group name already exists")) {
				// 忽略，因为组已经存在
			} else {
				throw e; // 重新抛出，因为这是一个我们不知道如何处理的异常
			}
		}
		jedisCluster.xtrim(key, maxMount, false);
		return jedisCluster;

	}
	
}
