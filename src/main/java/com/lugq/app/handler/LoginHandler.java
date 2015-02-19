package com.lugq.app.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lugq.app.helper.annotation.LogicHandler;

@LogicHandler(id = MessageID.LOGIN, desc = "用户登录接口")
public class LoginHandler extends AppLogicHandler {

	private static Logger logger = LogManager.getLogger(LoginHandler.class);
	
	@Override
	public void process() {
		logger.info("LoginHandler");
		
	}

}
