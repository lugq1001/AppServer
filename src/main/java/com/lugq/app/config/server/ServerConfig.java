package com.lugq.app.config.server;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * 服务器配置
 * @author Luguangqing
 *
 */
@JacksonXmlRootElement(localName = "serverConfig")
public class ServerConfig {

	@JacksonXmlProperty(localName = "name")
	private String name = "AppServer";
	
	@JacksonXmlProperty(localName = "versionName")
	private String verName = "1.0";
	
	@JacksonXmlProperty(localName = "versionCode")
	private int verCode = 1;
	
	private LogicServer logicServer;
	
	private FileServer fileServer;
	
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

	public LogicServer getLogicServer() {
		return logicServer;
	}

	public void setLogicServer(LogicServer logicServer) {
		this.logicServer = logicServer;
	}

	public FileServer getFileServer() {
		return fileServer;
	}

	public void setFileServer(FileServer fileServer) {
		this.fileServer = fileServer;
	}

}
