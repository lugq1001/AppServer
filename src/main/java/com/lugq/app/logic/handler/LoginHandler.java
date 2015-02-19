package com.lugq.app.logic.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lugq.app.helper.annotation.LogicHandler;
import com.lugq.app.network.BaseResponse;
import com.lugq.app.network.SBMessage;

@LogicHandler(id = MessageID.USR_LOGIN, desc = "用户登录接口")
public class LoginHandler extends AppLogicHandler {

	private static Logger logger = LogManager.getLogger(LoginHandler.class);
	
	@Override
	public void process(SBMessage message) {
		logger.info("LoginHandler");
		
	}

	public class LoginResponse extends BaseResponse {

		public LoginResponse(int retCode, String retMsg) {
			super(retCode, retMsg);
			
		}

	}
}
