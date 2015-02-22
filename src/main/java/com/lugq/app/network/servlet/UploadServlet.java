package com.lugq.app.network.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lugq.app.config.AppConfig;
import com.lugq.app.config.AppMessage;
import com.lugq.app.helper.annotation.AnnotationManager;
import com.lugq.app.logic.handler.AppServerHandler;
import com.lugq.app.network.BaseResponse;
import com.lugq.app.network.BaseResult;
import com.lugq.app.network.SBMessage;
import com.lugq.app.network.SBMessageFile;
import com.lugq.app.util.LangUtil;

//@WebServlet(name="FileUploadServlet", urlPatterns="/file/upload")  
//@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 30) 
public class UploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Logger logger = LogManager.getLogger(UploadServlet.class);

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		handler(req, resp);
	}
	
	private void handler(HttpServletRequest req, HttpServletResponse resp) {
		SBMessage message = new SBMessage(resp);
		try {
			String reqidStr = null;
			String data = null;
			// 请求处理
			Collection<Part> parts = req.getParts();
			List<SBMessageFile> files = new ArrayList<SBMessageFile>();
			for (Part p : parts) {
				String name = p.getName();
				if (name.equals("reqid")) {
					reqidStr = IOUtils.toString(p.getInputStream());
				} else if (name.equals("data")) {
					data = IOUtils.toString(p.getInputStream());
				} else {
					long maxSize = AppConfig.getInstance().getServerConfig().getFileServer().getMaxFileSize();
					if (p.getSize() > maxSize) {
						// 无效请求
						logger.debug("请求失败:文件过大");
						BaseResponse response = new BaseResponse(BaseResult.Failure.ordinal(), AppMessage.get(BaseResult.Failure.i18nCode));
						message.send(response);
						return;
					}
					String type = p.getContentType();
					long size = p.getSize();
					String fileName = p.getSubmittedFileName();
					//String fileData = IOUtils.toString(p.getInputStream());
					SBMessageFile file = new SBMessageFile();
					file.setBytes(IOUtils.toByteArray(p.getInputStream()));
					file.setFileName(name);
					file.setFileSize(size);
					file.setFileType(type);
					files.add(file);
					logger.debug("接收文件：" + fileName + " size:" + size + " type:" + type);
				}
			}
			
			if (LangUtil.isEmpty(reqidStr) || LangUtil.isEmpty(data) || LangUtil.isEmpty(files)) {
				// 无效请求
				logger.debug("请求失败:参数错误");
				BaseResponse response = new BaseResponse(BaseResult.Failure.ordinal(), AppMessage.get(BaseResult.Failure.i18nCode));
				message.send(response);
				return;
			}
			
			int reqid = Integer.parseInt(reqidStr);
			message.setReq_id(reqid);
			message.setReq_data(data);
			message.setFiles(files);

			AppServerHandler handler = AnnotationManager.createLogicHandlerInstance(reqid);
			if (handler != null) {
				handler.process(message);
			} else {
				// 无效请求
				logger.error("请求失败:参数错误");
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
















