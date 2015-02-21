package com.lugq.app.config.server;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * 文件服务器配置
 * @author Luguangqing
 *
 */
@JacksonXmlRootElement(localName = "fileServer")
public class FileServer {

	@JacksonXmlProperty(localName = "name")
	private String name = "";
	
	@JacksonXmlProperty(localName = "enable")
	private boolean enable = true;
	
	@JacksonXmlProperty(localName = "uploadPath")
	private String uploadPath = "";
	
	@JacksonXmlProperty(localName = "downloadPath")
	private String downloadPath = "";
	
	@JacksonXmlProperty(localName = "maxFileSize")
	private int maxFileSize = 30000000;
	
	@JacksonXmlProperty(localName = "host")
	private String host = "";
	
	@JacksonXmlProperty(localName = "port")
	private int port = 8080;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	public int getMaxFileSize() {
		return maxFileSize;
	}

	public void setMaxFileSize(int maxFileSize) {
		this.maxFileSize = maxFileSize;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getDownloadPath() {
		return downloadPath;
	}

	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}
}
