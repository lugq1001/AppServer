package com.lugq.app.network;

/**
 * 接口响应类
 * @author Luguangqing
 *
 */
public class BaseResponse {

	private int retCode = -1;
	
	private String retMsg = "";
	
	public BaseResponse(int retCode, String retMsg) {
		this.retCode = retCode;
		this.retMsg = retMsg;
	}

	public int getRetCode() {
		return retCode;
	}

	public void setRetCode(int retCode) {
		this.retCode = retCode;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}
	
}
