package com.lugq.app.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lugq.app.helper.annotation.AnnotationManager;
import com.lugq.app.helper.annotation.LogicHandler;

public abstract class AppLogicHandler {

	private static Logger logger = LogManager.getLogger(AppLogicHandler.class);
	
	public abstract void process();
	
	public void logicProcess() {
		logger.debug("logicProcess");
		process();
	}
}
