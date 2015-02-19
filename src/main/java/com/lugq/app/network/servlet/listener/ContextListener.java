package com.lugq.app.network.servlet.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lugq.app.config.AppConfig;
import com.lugq.app.helper.annotation.AnnotationManager;

@WebListener
public class ContextListener implements ServletContextListener {

	private static Logger logger = LogManager.getLogger(ContextListener.class);
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		logger.info("Servlet start init");
		logger.info("加载配置文件");
		AppConfig.getInstance().loadConfig();
		
		logger.info("加载自定义标签");
		AnnotationManager.initAnnotation();
		
		logger.info("Servlet init completed");
	}
	
	
	


}
