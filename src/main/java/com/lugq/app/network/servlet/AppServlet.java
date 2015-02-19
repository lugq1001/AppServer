package com.lugq.app.network.servlet;

import java.io.IOException;
import java.util.Base64;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.lugq.app.config.AppMessage;
import com.lugq.app.helper.annotation.AnnotationManager;
import com.lugq.app.logic.handler.AppLogicHandler;
import com.lugq.app.network.BaseResponse;
import com.lugq.app.network.SBMessage;
import com.lugq.app.util.LangUtil;

@WebServlet(name="AppServlet", urlPatterns="/app")  
public class AppServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Logger logger = LogManager.getLogger(AppServlet.class);

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
				logger.error("请求失败:参数错误");
				BaseResponse response = new BaseResponse(BaseResult.Failure.ordinal(), AppMessage.get(BaseResult.Failure.i18nCode));
				message.send(response);
				return;
			}
			
			message.setReq_data(new String(Base64.getDecoder().decode(data)));
			message.setReq_id(reqid);
			AppLogicHandler handler = AnnotationManager.createLogicHandlerInstance(reqid);
			if (handler != null) {
				handler.logicProcess(message);
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
	
	private enum BaseResult {
		Success("base.succ"), 
		Failure("base.failure");
		
		public String i18nCode;
		
		private BaseResult(String i18nCode) {
			this.i18nCode = i18nCode;
		}

	}

	/*Collection<Part> parts = null;
	List<SBMessageFile> files = new ArrayList<SBMessageFile>();
	if (req.getMethod().equals("POST")) {
		parts = req.getParts();
		if (!LangUtil.isEmpty(parts)) {
			for (Part p : parts) {
				String type = p.getContentType();
				long size = p.getSize();
				String name = p.getSubmittedFileName();
				String fileData = IOUtils.toString(p.getInputStream());
				SBMessageFile file = new SBMessageFile();
				file.setFileData(fileData);
				file.setFileName(name);
				file.setFileSize(size);
				file.setFileType(type);
				files.add(file);
			}
		}
	} */
}
















