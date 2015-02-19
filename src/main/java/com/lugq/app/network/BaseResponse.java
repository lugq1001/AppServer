package com.lugq.app.network;

/**
 * 接口响应类
 * @author Luguangqing
 *
 */
public class BaseResponse {

	public int reqid = -1;
	
	public int retCode = -1;
	
	public String retMsg = "";
	
	public String custom_1 = "";
	
	public String custom_2 = "";
	
	public BaseResponse(int retCode, String retMsg) {
		this.retCode = retCode;
		this.retMsg = retMsg;
	}
	
}
