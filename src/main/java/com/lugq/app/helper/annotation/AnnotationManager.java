package com.lugq.app.helper.annotation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lugq.app.handler.AppLogicHandler;
import com.lugq.app.util.PackageUtil;

public class AnnotationManager {

	private static Logger logger = LogManager.getLogger(AnnotationManager.class);

	private static Map<Integer, Class<?>> logicHandlers = new HashMap<Integer, Class<?>>();
	
	public static void initAnnotation() {
		String packageName = AppLogicHandler.class.getPackage().getName();
		logger.info("扫描包：" + packageName);
		logger.info("加载@LogicHandler");
		List<Class<?>> classes = PackageUtil.getClasssFromPackage(packageName);
		for (Class<?> clazz : classes) {
			LogicHandler handler = clazz.getAnnotation(LogicHandler.class);
			if (handler != null) {
				logger.info(clazz.getName() + ":" + handler.desc() + "(" + handler.id() + ")");
				logicHandlers.put(handler.id(), clazz);
			}
		}
		logger.info("@LogicHandler加载完成");
	}
	
	public static AppLogicHandler createLogicHandlerInstance(int messageID) {
		Class<?> clazz = logicHandlers.get(messageID);
		try {
			AppLogicHandler handler = (AppLogicHandler) clazz.newInstance();
			return handler;
		} catch (InstantiationException e) {
			
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			
			e.printStackTrace();
		}
		return null;
		
	}
	
}
