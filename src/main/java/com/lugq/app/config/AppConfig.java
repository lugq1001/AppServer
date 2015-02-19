package com.lugq.app.config;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "AppConfig")
public class AppConfig {

	private static Logger logger = LogManager.getLogger(AppConfig.class);

	private ServerConfig serverConfig;	
	
	private NetworkConfig networkConfig;	
	
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
			logger.info("===========================");
			ServerConfig serverConfig = config.getServerConfig();
			logger.info("serverName:" + serverConfig.getName());
			logger.info("serverVersionName:" + serverConfig.getVerName());
			logger.info("serverVersionCode:" + serverConfig.getVerCode());
			logger.info("===========================");
			NetworkConfig networkConfig = config.getNetworkConfig();
			logger.info("magicKey:" + networkConfig.getMagicKey());
			logger.info("===========================");
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

	
}
