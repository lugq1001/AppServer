package com.lugq.app.logic.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lugq.app.network.SBMessage;
import com.lugq.app.util.LangUtil;
import com.lugq.app.util.StringBufferLine;

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
		StringBufferLine logBuffer = new StringBufferLine();
		logBuffer.append("\n*************************** AppServerHandler process start *********************************************************");
		long time = System.currentTimeMillis();
		if (!LangUtil.isEmpty(message.getFiles())) {
			logBuffer.append("== reqid(" + message.getReq_id() + ") file process start ==");
			fileProcess(message);
			long interval = System.currentTimeMillis() - time;
			logBuffer.append("reqid(" + message.getReq_id() + ") completed with fileProcess in " + interval + " ms.");
		} else {
			logBuffer.append("== reqid(" + message.getReq_id() + ") logic process start ==");
			logicProcess(message);
			long interval = System.currentTimeMillis() - time;
			logBuffer.append("== reqid(" + message.getReq_id() + ") completed with logicProcess in " + interval + " ms. ==");
		}
		logBuffer.append("***************************** AppServerHandler process end *******************************************************");
		logger.info(logBuffer.toString());
	}
	
	
	
}
