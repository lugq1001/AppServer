package com.lugq.app.config;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.lugq.app.dao.mongo.MongoConfig;
import com.lugq.app.dao.redis.RedisConfig;

@JacksonXmlRootElement(localName = "AppConfig")
public class AppConfig {

	private static Logger logger = LogManager.getLogger(AppConfig.class);

	private ServerConfig serverConfig;	
	
	private NetworkConfig networkConfig;
	
	private MongoConfig mongoConfig;
	
	private RedisConfig redisConfig;
	
	private static AppConfig instance = null;

	private AppConfig() {
	}

	public static AppConfig getInstance() {
		if (instance == null) {
			instance = loadConfig();
		}
		return instance;
	}

	private static AppConfig loadConfig() {
		AppConfig config = new AppConfig();
		File xml = new File(AppConfig.class.getResource("/AppConfig.xml").getFile());
		if (!xml.exists()) {
			logger.error("读取AppConfig.xml失败: AppConfig.xml文件不存在。");
			return config;
		} 
		try {
			logger.info("读取AppConfig.xml:");
			XmlMapper mapper = new XmlMapper();
			config = mapper.readValue(xml, AppConfig.class);
			logger.info("===========ServerConfig================");
			ServerConfig serverConfig = config.getServerConfig();
			logger.info("serverName:" + serverConfig.getName());
			logger.info("serverVersionName:" + serverConfig.getVerName());
			logger.info("serverVersionCode:" + serverConfig.getVerCode());
			logger.info("===========NetworkConfig===============");
			NetworkConfig networkConfig = config.getNetworkConfig();
			logger.info("magicKey:" + networkConfig.getMagicKey());
			logger.info("===========MongoConfig=================");
			MongoConfig mongoConfig = config.getMongoConfig();
			logger.info("ip:" + mongoConfig.getIp());
			logger.info("port:" + mongoConfig.getPort());
			logger.info("dbName:" + mongoConfig.getDbName());
			logger.info("poolSize:" + mongoConfig.getPoolSize());
			logger.info("threadsAllowedToBlockForConnectionMultiplier:" + mongoConfig.getThreadsAllowedToBlockForConnectionMultiplier());
			logger.info("maxWaitTime:" + mongoConfig.getMaxWaitTime());
			logger.info("connectTimeout:" + mongoConfig.getConnectTimeout());
			logger.info("socketTimeout:" + mongoConfig.getSocketTimeout());
			logger.info("socketKeepAlive:" + mongoConfig.isSocketKeepAlive());	
			logger.info("===========RedisConfig=================");
			RedisConfig redisConfig = config.getRedisConfig();
			logger.info("ip:" + redisConfig.getIp());
			logger.info("port:" + redisConfig.getPort());
			logger.info("maxIdle:" + redisConfig.getMaxIdle());
			logger.info("maxWaitMillis:" + redisConfig.getMaxWaitMillis());
			logger.info("testOnBorrow:" + redisConfig.isTestOnBorrow());
			logger.info("testOnReturn():" + redisConfig.isTestOnReturn());
			logger.info("testWhileIdle:" + redisConfig.isTestWhileIdle());	
			logger.info("=======================================");
			logger.info("AppConfig.xml 读取成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("读取AppConfig.xml失败:" + e.getLocalizedMessage());
		}
		return config;
	}

	public ServerConfig getServerConfig() {
		return serverConfig;
	}

	public void setServerConfig(ServerConfig serverConfig) {
		this.serverConfig = serverConfig;
	}

	public NetworkConfig getNetworkConfig() {
		return networkConfig;
	}

	public void setNetworkConfig(NetworkConfig networkConfig) {
		this.networkConfig = networkConfig;
	}

	public MongoConfig getMongoConfig() {
		return mongoConfig;
	}

	public void setMongoConfig(MongoConfig mongoConfig) {
		this.mongoConfig = mongoConfig;
	}

	public RedisConfig getRedisConfig() {
		return redisConfig;
	}

	public void setRedisConfig(RedisConfig redisConfig) {
		this.redisConfig = redisConfig;
	}

	
}
