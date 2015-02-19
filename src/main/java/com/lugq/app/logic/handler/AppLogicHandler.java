package com.lugq.app.logic.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lugq.app.network.SBMessage;

/**
 * 业务逻辑处理接口
 * @author Luguangqing
 *
 */
public abstract class AppLogicHandler {

	//private static Logger logger = LogManager.getLogger(AppLogicHandler.class);
	
	protected ObjectMapper objMapper = new ObjectMapper();
	
	public abstract void process(SBMessage message);
	
	public void logicProcess(SBMessage message) {
		process(message);
	}
	
}
