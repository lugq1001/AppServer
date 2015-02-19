package com.lugq.app.config;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "networkConfig")
public class NetworkConfig {

	@JacksonXmlProperty(localName = "magicKey")
	private String magicKey = "";

	public String getMagicKey() {
		return magicKey;
	}

	public void setMagicKey(String magicKey) {
		this.magicKey = magicKey;
	}
	
}
