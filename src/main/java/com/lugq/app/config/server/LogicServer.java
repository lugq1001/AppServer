package com.lugq.app.config.server;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * 逻辑服务器配置
 * @author Luguangqing
 *
 */
@JacksonXmlRootElement(localName = "logicServer")
public class LogicServer {

	@JacksonXmlProperty(localName = "name")
	private String name = "";
	
	@JacksonXmlProperty(localName = "enable")
	private boolean enable = true; 
	
	@JacksonXmlProperty(localName = "magicKey")
	private String magicKey = "";

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getMagicKey() {
		return magicKey;
	}

	public void setMagicKey(String magicKey) {
		this.magicKey = magicKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
