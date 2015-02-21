package com.lugq.app.logic.handler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lugq.app.config.AppConfig;
import com.lugq.app.config.AppMessage;
import com.lugq.app.helper.annotation.LogicHandler;
import com.lugq.app.logic.LogicHelper;
import com.lugq.app.logic.MessageID;
import com.lugq.app.model.entity.User;
import com.lugq.app.network.BaseRequest;
import com.lugq.app.network.BaseResponse;
import com.lugq.app.network.SBMessage;
import com.lugq.app.network.SBMessageFile;
import com.lugq.app.util.LangUtil;

@LogicHandler(id = MessageID.TEST, desc = "测试")
public class TestHandler extends AppServerHandler {

	private static Logger logger = LogManager.getLogger(TestHandler.class);
	private final int MAX_FILE_COUNT = 3;
	
	@Override
	public void fileProcess(SBMessage message) {
		try {
			logger.debug("-测试(file)-");
			String uploadPath = AppConfig.getInstance().getServerConfig().getFileServer().getUploadPath();
			List<SBMessageFile> files = message.getFiles();
			int size = files.size();
			if (size > MAX_FILE_COUNT) {
				logger.debug("文件数量超过限制");
				sendFailureResp(message, TestResult.FailureFileMax);
				return;
			}
			TestRequest req = objMapper.readValue(message.getReq_data(), TestRequest.class);
			logger.debug("开始处理文件");
			for (int i = 0; i < size; i++) {
				SBMessageFile f = files.get(i);
				String filePath = LogicHelper.generateFileName(req.userSid, MessageID.TEST, i, f.getFileName());
				String storePath = uploadPath +  filePath;
				FileUtils.writeByteArrayToFile(new File(storePath), f.getBytes());
				req.filePaths.add(filePath);
				logger.debug("-----文件" + (i + 1) + "-----");
				logger.debug("文件保存路径:" + storePath);
				logger.debug("文件类型:" + f.getFileType());
				logger.debug("文件名:" + f.getFileName());
				logger.debug("文件大小:" + f.getFileSize());
				logger.debug("写入成功");
			}
			logger.debug("-测试(file)- 完成");
			logger.debug("-测试(file)- 转发至logic server");
			String result = message.transmitToLogic(MessageID.TEST, req);
			logger.debug("-测试(file)- logic响应:" + result);
			message.send(result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("-测试(file)- 失败:" + e.getLocalizedMessage());
			sendFailureResp(message, TestResult.Failure);
		}
	}
	
	@Override
	public void logicProcess(SBMessage message) {
		try {
			logger.debug("-测试(logic)-");
			logger.debug("-测试(logic)-请求数据：" + message.getReq_data());
			TestRequest req = objMapper.readValue(message.getReq_data(), TestRequest.class);
			String userSid = req.userSid;
			List<String> filePaths = req.filePaths;
			if (LangUtil.isEmpty(filePaths)) {
				logger.debug("-测试(logic)-文件为空");
				sendFailureResp(message, TestResult.FailureFileMax);
				return;
			}
			
			User u = User.findBySid(userSid);
			if (u == null) {
				logger.debug("-测试(logic)-用户不存在");
				sendFailureResp(message, TestResult.Failure);
				return;
			}
			
			u.setAvatar(filePaths.get(0));
			u.save();
			logger.debug("-测试(logic)-更新用户头像成功");
			TestResponse resp = new TestResponse(TestResult.Success.ordinal(), AppMessage.get(TestResult.Success.i18nCode));
			resp.userSid = userSid;
			for (String path : filePaths) {
				path = LogicHelper.downloadPathPrefix() + path;
				resp.filePaths.add(path);
				logger.debug("-头像下载路径-" + path);
			}
			message.send(resp);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("-测试(logic)-(文件上传) 失败:" + e.getLocalizedMessage());
			sendFailureResp(message, TestResult.Failure);
		}
		
		
		
	}
	
	private static class TestRequest extends BaseRequest {
		
		public String userSid;
		public List<String> filePaths = new ArrayList<String>();
	}
	
	private enum TestResult {
		Success(MessageID.TEST + ".succ"), 
		Failure(MessageID.TEST + ".failure"),
		FailureFileNone(MessageID.TEST + ".failure.file.none"),
		FailureFileMax(MessageID.TEST + ".failure.file.max");
		
		public String i18nCode;
		
		private TestResult(String i18nCode) {
			this.i18nCode = i18nCode;
		}

	}
	
	private void sendFailureResp(SBMessage message, TestResult result) {
		TestResponse resp = new TestResponse(result.ordinal(), AppMessage.get(result.i18nCode));
		message.send(resp);
	}

	public class TestResponse extends BaseResponse {
		
		public String userSid;
		public List<String> filePaths = new ArrayList<String>();
		
		public TestResponse(int retCode, String retMsg) {
			super(retCode, retMsg);
		}
	}
}
