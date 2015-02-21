package com.lugq.app.dao.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.lugq.app.config.AppConfig;

public class RedisManager {
	
	private static JedisPool pool;
	
	static {
		RedisConfig config = AppConfig.getInstance().getRedisConfig();
		JedisPoolConfig c = new JedisPoolConfig();
		c.setMaxIdle(config.getMaxIdle());
		c.setMaxWaitMillis(config.getMaxWaitMillis());
		c.setTestWhileIdle(config.isTestWhileIdle());
		c.setTestOnBorrow(config.isTestOnBorrow());
		c.setTestOnReturn(config.isTestOnReturn());
		pool = new JedisPool(c, config.getIp(), config.getPort());
	}

	public static Jedis getResource() {
		return pool.getResource();
	}
	
	public static void returnResource(Jedis j) {
		pool.returnResource(j);
	}
}
