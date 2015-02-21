package com.lugq.app.logic.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lugq.app.network.SBMessage;
import com.lugq.app.util.LangUtil;

/**
 * 业务逻辑处理入口
 * @author Luguangqing
 *
 */
public abstract class AppServerHandler {

	private static Logger logger = LogManager.getLogger(AppServerHandler.class);
	
	protected ObjectMapper objMapper = new ObjectMapper();
	
	public abstract void logicProcess(SBMessage message);
	
	protected void fileProcess(SBMessage message){}
	
	public void process(SBMessage message) {
		long time = System.currentTimeMillis();
		if (!LangUtil.isEmpty(message.getFiles())) {
			fileProcess(message);
			long interval = System.currentTimeMillis() - time;
			logger.info("reqid(" + message.getReq_id() + ") completed with fileProcess in " + interval + " ms.");
		} else {
			logicProcess(message);
			long interval = System.currentTimeMillis() - time;
			logger.info("reqid(" + message.getReq_id() + ") completed with logicProcess in " + interval + " ms.");
		}
	}
	
	
	
}
