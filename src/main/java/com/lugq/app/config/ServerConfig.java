package com.lugq.app.config;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "serverConfig")
public class ServerConfig {

	@JacksonXmlProperty(localName = "name")
	private String name = "AppServer";
	
	@JacksonXmlProperty(localName = "versionName")
	private String verName = "1.0";
	
	@JacksonXmlProperty(localName = "versionCode")
	private int verCode = 1;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVerName() {
		return verName;
	}

	public void setVerName(String verName) {
		this.verName = verName;
	}

	public int getVerCode() {
		return verCode;
	}

	public void setVerCode(int verCode) {
		this.verCode = verCode;
	}
	
}
