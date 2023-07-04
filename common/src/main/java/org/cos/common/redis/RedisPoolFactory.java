package org.cos.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.StreamEntryID;
import redis.clients.jedis.exceptions.JedisDataException;


@Service
public class RedisPoolFactory {

	@Autowired
	RedisConfig redisConfig;

	@Value("${spring.redis.stream.key}")
	private String key;
	@Value("${spring.redis.stream.group}")
	private String group;
	@Value("${spring.redis.stream.maxMount}")
	private int maxMount;
	
	@Bean
	public JedisPool jedisPoolFactory() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxIdle(redisConfig.getPoolMaxIdle());
		poolConfig.setMaxTotal(redisConfig.getPoolMaxTotal());
		poolConfig.setMinIdle(redisConfig.getPoolMinIdle());
//		poolConfig.setMaxWaitMillis(redisConfig.getPoolMaxWait() * 1000);
		JedisPool jp = new JedisPool(poolConfig, redisConfig.getHost(), redisConfig.getPort(),
				redisConfig.getTimeout()*1000, redisConfig.getPassword(), 0);
		// 创建消息队列
		Jedis jedis = jp.getResource();
		try {
			jedis.xgroupCreate(key, group, StreamEntryID.LAST_ENTRY, true);
		} catch (JedisDataException e) {
			if (e.getMessage().contains("BUSYGROUP Consumer Group name already exists")) {
				// 忽略，因为组已经存在
			} else {
				throw e; // 重新抛出，因为这是一个我们不知道如何处理的异常
			}
		}
		jedis.xtrim(key, maxMount, false);

		if(jedis != null) {
			jedis.close();
		}
		return jp;
	}
	
}
