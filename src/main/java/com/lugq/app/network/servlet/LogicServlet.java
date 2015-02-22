package com.lugq.app.network.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lugq.app.config.AppMessage;
import com.lugq.app.helper.annotation.AnnotationManager;
import com.lugq.app.logic.handler.AppServerHandler;
import com.lugq.app.network.BaseResponse;
import com.lugq.app.network.BaseResult;
import com.lugq.app.network.SBMessage;
import com.lugq.app.util.LangUtil;


//@WebServlet(name="LogicServlet", urlPatterns="/logic")  
public class LogicServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Logger logger = LogManager.getLogger(LogicServlet.class);

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		handler(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		handler(req, resp);
	}
	
	private void handler(HttpServletRequest req, HttpServletResponse resp) {
		SBMessage message = new SBMessage(resp);
		try {
			int reqid = Integer.parseInt(req.getParameter("reqid"));
			String data = req.getParameter("data");
			if (LangUtil.isEmpty(data)) {
				logger.debug("请求失败:参数错误");
				BaseResponse response = new BaseResponse(BaseResult.Failure.ordinal(), AppMessage.get(BaseResult.Failure.i18nCode));
				message.send(response);
				return;
			}
			
			message.setReq_data(data);
			message.setReq_id(reqid);
			AppServerHandler handler = AnnotationManager.createLogicHandlerInstance(reqid);
			if (handler != null) {
				handler.process(message);
			} else {
				// 无效请求
				logger.debug("请求失败:参数错误");
				BaseResponse response = new BaseResponse(BaseResult.Failure.ordinal(), AppMessage.get(BaseResult.Failure.i18nCode));
				message.send(response);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("请求失败:" + e.getMessage());
			BaseResponse response = new BaseResponse(BaseResult.Failure.ordinal(), AppMessage.get(BaseResult.Failure.i18nCode));
			message.send(response);
		}
	}
}
















