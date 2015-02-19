package com.lugq.app.network;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

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
	 * 上传的文件
	 */
	private List<SBMessageFile> req_files = new ArrayList<SBMessageFile>();
	
	private SBMessageType type = SBMessageType.Http;
	
	private HttpServletResponse resp;
	
	public SBMessage(HttpServletResponse resp) {
		this.resp = resp;
		this.type = SBMessageType.Http;
	}
	
	public void send(BaseResponse response) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();  
			String json = objectMapper.writeValueAsString(response);  
			switch (type) {
			case Http:
				resp.getWriter().write(json);
				break;
			case WebSocket:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
		}
	}

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

	public List<SBMessageFile> getReq_files() {
		return req_files;
	}

	public void setReq_files(List<SBMessageFile> req_files) {
		this.req_files = req_files;
	}
	
}
