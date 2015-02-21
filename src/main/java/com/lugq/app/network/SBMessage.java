package com.lugq.app.network;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lugq.app.config.AppConfig;

/**
 * 网络请求消息包装类
 * @author Luguangqing
 *
 */
public class SBMessage {

	private static Logger logger = LogManager.getLogger(SBMessage.class);
	
	/**
	 * MessageID
	 */
	private int req_id = 0;
	
	/**
	 * json格式参数
	 */
	private String req_data = "";
	
	/**
	 * 上传文件
	 */
	private List<SBMessageFile> files = new ArrayList<SBMessageFile>();
	
	private SBMessageType type = SBMessageType.Http;
	
	private HttpServletResponse resp;
	
	public SBMessage(HttpServletResponse resp) {
		this.resp = resp;
		this.type = SBMessageType.Http;
	}
	
	/*===================== Method =====================*/
	
	public void send(BaseResponse response) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();  
			String json = objectMapper.writeValueAsString(response);
			send(json);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
		}
	}
	
	public void send(String result) {
		try {
			switch (type) {
			case Http:
				resp.getWriter().write(result);
				break;
			case WebSocket:
				break;
			}
			logger.debug("resp json:" + result);
			//logger.debug("resp base64:" + b64Json);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
		}
	}
	
	public String transmitToLogic(int reqid, BaseRequest req) {
		String result = "";
		try {
			ObjectMapper objectMapper = new ObjectMapper();  
			String json = objectMapper.writeValueAsString(req);
			String host = AppConfig.getInstance().getServerConfig().getLogicServer().getHost();
			int port = AppConfig.getInstance().getServerConfig().getLogicServer().getPort();
			switch (type) {
			case Http:
				String path = AppConfig.getInstance().getServerConfig().getLogicServer().getHttpPath();
				URIBuilder builder = new URIBuilder();
				builder.setParameter("reqid", reqid + "");
				builder.setParameter("data", json);
				builder.setScheme("http");
				builder.setHost(host);
				builder.setPort(port);
				builder.setPath(path + "/logic");
				URI uri = builder.build();
				HttpPost post = new HttpPost(uri);
				CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse resp = httpClient.execute(post);
				result = EntityUtils.toString(resp.getEntity());  
				resp.close();
				break;
			case WebSocket:
				break;
			}
			logger.debug("result from logic:" + result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
		}
		return result;
	}

	/*===================== GetterSetter =====================*/
	
	public int getReq_id() {
		return req_id;
	}

	public void setReq_id(int req_id) {
		this.req_id = req_id;
	}

	public String getReq_data() {
		return req_data;
	}

	public void setReq_data(String req_data) {
		this.req_data = req_data;
	}

	public List<SBMessageFile> getFiles() {
		return files;
	}

	public void setFiles(List<SBMessageFile> files) {
		this.files = files;
	}

}
