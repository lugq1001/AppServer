package com.lugq.app.config;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class AppConfig {

	private static Logger logger = LogManager.getLogger(AppConfig.class);

	private String serverName = "AppServer";
	
	private String serverVersionName = "1.0";
	
	private int serverVersionCode = 1;
	
	private String magicKey = "";
	
	private static AppConfig instance = null;

	private AppConfig() {
	}

	public static AppConfig getInstance() {
		if (instance == null) {
			instance = new AppConfig();
		}
		return instance;
	}

	public void loadConfig() {
		File xml = new File(AppConfig.class.getResource("/AppConfig.xml").getFile());
		if (!xml.exists()) {
			logger.error("解析AppConfig.xml失败: AppConfig.xml文件不存在。");
			return;
		} 
		SAXReader sr = new SAXReader();
		try {
			logger.info("读取AppConfig.xml:");
			Document doc = sr.read(xml);
			Element node = doc.getRootElement();
			{// server
				logger.info("===========================");
				Element serverNode = node.element("server");  
				Element serverName = serverNode.element("name");
				Element serverVersionName = serverNode.element("versionName");
				Element serverVersionCode = serverNode.element("versionCode");
				this.serverName = serverName.getText();
				this.serverVersionName = serverVersionName.getText();
				this.serverVersionCode = Integer.parseInt(serverVersionCode.getText());
				logger.info("serverName:" + this.serverName);
				logger.info("serverVersionName:" + this.serverVersionName);
				logger.info("serverVersionCode:" + this.serverVersionCode);
				logger.info("===========================");
			}
			
			{// magicKey
				Element magicKey = node.element("magicKey");  
				this.magicKey = magicKey.getText();
				logger.info("magicKey:" + this.magicKey);
				logger.info("===========================");
			}
			logger.info("AppConfig.xml 读取成功");
		} catch (DocumentException e) {
			e.printStackTrace();
			logger.error("读取AppConfig.xml失败:" + e.getLocalizedMessage());
		}
		return;
	}

	public String getMagicKey() {
		return magicKey;
	}

	public void setMagicKey(String magicKey) {
		this.magicKey = magicKey;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}



	public int getServerVersionCode() {
		return serverVersionCode;
	}

	public void setServerVersionCode(int serverVersionCode) {
		this.serverVersionCode = serverVersionCode;
	}

	public String getServerVersionName() {
		return serverVersionName;
	}

	public void setServerVersionName(String serverVersionName) {
		this.serverVersionName = serverVersionName;
	}
}
