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
		SAXReader sr = new SAXReader();
		try {
			Document doc = sr.read(xml);
			Element node = doc.getRootElement();
			{// magicKey
				Element element = node.element("magicKey");  
				this.magicKey = element.getText();
			}
		} catch (DocumentException e) {
			e.printStackTrace();
			logger.error("解析AppConfig.xml失败:" + e.getLocalizedMessage());
		}
		return;
	}

	public String getMagicKey() {
		return magicKey;
	}

	public void setMagicKey(String magicKey) {
		this.magicKey = magicKey;
	}
}
