package com.lugq.app.logic.handler;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.CharSequenceInputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lugq.app.config.AppConfig;
import com.lugq.app.config.AppMessage;
import com.lugq.app.helper.annotation.LogicHandler;
import com.lugq.app.logic.LogicHelper;
import com.lugq.app.logic.MessageID;
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
			String uploadPath = AppConfig.getInstance().getServerConfig().getFileServer().getUploadPath();
			List<SBMessageFile> files = message.getFiles();
			int size = files.size();
			if (size > MAX_FILE_COUNT) {
				logger.debug("-测试失败-文件数量超过限制");
				sendFailureResp(message, TestResult.FailureFileMax);
				return;
			}
			TestRequest req = objMapper.readValue(message.getReq_data(), TestRequest.class);
			for (int i = 0; i < size; i++) {
				SBMessageFile f = files.get(i);
				String fileName = LogicHelper.generateFileName(req.userSid, MessageID.TEST, i, f.getFileName());
				String filePath = uploadPath + File.separator +  fileName;
				File file = new File(filePath);
				FileUtils.writeByteArrayToFile(file, f.getBytes());
				logger.debug("-文件保存路径-" + filePath);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
			sendFailureResp(message, TestResult.Failure);
		}
	}
	
	@Override
	public void logicProcess(SBMessage message) {
		logger.info("测试接口：" + message.getReq_data());
		
		TestResponse resp = new TestResponse(TestResult.Success.ordinal(), AppMessage.get(TestResult.Success.i18nCode));
		message.send(resp);
	}
	
	private static class TestRequest extends BaseRequest {
		
		public String userSid;
		public String content;
		public String title;
	}
	
	private enum TestResult {
		Success(MessageID.TEST + ".succ"), 
		Failure(MessageID.TEST + ".failure"),
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

		public TestResponse(int retCode, String retMsg) {
			super(retCode, retMsg);
		}
	}
}
