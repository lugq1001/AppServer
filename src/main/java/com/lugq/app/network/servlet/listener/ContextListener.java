package com.lugq.app.network.servlet.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lugq.app.config.AppConfig;
import com.lugq.app.config.AppMessage;
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
		AppConfig.getInstance();
		logger.info("加载国际化资源");
		AppMessage.loadMessage();
		logger.info("加载自定义标签");
		AnnotationManager.initAnnotation();
		logger.info("===============================================");
		logger.info("==   *************************************   ==");
		logger.info("==   *****                           *****   ==");
		logger.info("==   *****     Server Started        *****   ==");
		logger.info("==   *****                           *****   ==");
		logger.info("==   *************************************   ==");
		logger.info("===============================================");
		
		// 测试数据库连接
		
	}
	
	
	


}
