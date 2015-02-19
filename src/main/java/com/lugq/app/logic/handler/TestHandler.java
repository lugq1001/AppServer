package com.lugq.app.logic.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.lugq.app.config.AppMessage;
import com.lugq.app.helper.annotation.LogicHandler;
import com.lugq.app.logic.MessageID;
import com.lugq.app.network.BaseResponse;
import com.lugq.app.network.SBMessage;

@LogicHandler(id = MessageID.TEST, desc = "测试")
public class TestHandler extends AppLogicHandler {

	private static Logger logger = LogManager.getLogger(TestHandler.class);
	
	@Override
	public void process(SBMessage message) {
		logger.info("测试接口：" + message.getReq_data());
		
		TestResponse resp = new TestResponse(TestResult.Success.ordinal(), AppMessage.get(TestResult.Success.i18nCode));
		message.send(resp);
	}
	
	private enum TestResult {
		Success(MessageID.TEST + ".succ"), 
		Failure(MessageID.TEST + ".failure");
		
		public String i18nCode;
		
		private TestResult(String i18nCode) {
			this.i18nCode = i18nCode;
		}

	}

	public class TestResponse extends BaseResponse {

		public TestResponse(int retCode, String retMsg) {
			super(retCode, retMsg);
		}
	}
}
