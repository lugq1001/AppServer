package com.lugq.app.logic.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lugq.app.config.AppConfig;
import com.lugq.app.config.AppMessage;
import com.lugq.app.helper.annotation.LogicHandler;
import com.lugq.app.model.User;
import com.lugq.app.network.BaseRequest;
import com.lugq.app.network.BaseResponse;
import com.lugq.app.network.SBMessage;
import com.lugq.app.util.LangUtil;
import com.lugq.app.util.MD5Util;
import com.lugq.app.logic.MessageID;

@LogicHandler(desc = "用户登录接口", id = MessageID.USR_LOGIN)
public class LoginHandler extends AppLogicHandler {

	private static Logger logger = LogManager.getLogger(LoginHandler.class);

	@Override
	public void process(SBMessage message) {
		try {
			String reqData = message.getReq_data();
			logger.debug(message.getReq_data());
			LoginRequest req = objMapper.readValue(reqData, LoginRequest.class);
			
			String username = req.username;
			String password = req.password;
			String s = req.getS();
			String c1 = LangUtil.StringRidOfNull(req.getCustom_1());
			String c2 = LangUtil.StringRidOfNull(req.getCustom_2());
			
			// 验证MD5
			String magicKey = AppConfig.getInstance().getNetworkConfig().getMagicKey();
			if (!MD5Util.md5(username + magicKey + password).equals(s)) {
				logger.error("验证失败");
				sendFailureResp(message, LoginResult.FailureVerify, c1, c2);
				return;
			}
			
			// 用户名为空
			if (LangUtil.isEmpty(username)) {
				logger.error("用户名为空");
				sendFailureResp(message, LoginResult.FailureUserNone, c1, c2);
				return;
			}
			
			// 查找用户
			User u = User.findByUsername(username);
			
			// 用户不存在
			if (u == null) {
				logger.error("用户:" + username + " 不存在");
				sendFailureResp(message, LoginResult.FailureUserNone, c1, c2);
				return;
			}
			
			// 密码错误
			if (!u.getPassword().equals(password)) {
				logger.error("用户:" + username + " 密码错误");
				sendFailureResp(message, LoginResult.FailurePasswordError, c1, c2);
				return;
			}
			
			// 更新用户最后登录时间
			u.setLateseLogin(System.currentTimeMillis());
			u.save();
			
			// 响应
			LoginResult result = LoginResult.Success;
			LoginResponse resp = new LoginResponse(result.ordinal(), AppMessage.get(result.i18nCode), c1, c2);
			message.send(resp);
			logger.info("用户:" + req.username + "密码:" + req.password + " 登录成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
			sendFailureResp(message, LoginResult.Failure, "", "");
		}
	}

	private static class LoginRequest extends BaseRequest {
		
		public String username;
		public String password;
	
	}
	
	public class LoginResponse extends BaseResponse {

		public LoginResponse(int retCode, String retMsg, String custom_1, String custom_2) {
			super(retCode, retMsg);
			this.reqid = MessageID.USR_LOGIN;
			this.custom_1 = custom_1;
			this.custom_2 = custom_2;
		}
	}
	
	private void sendFailureResp(SBMessage message, LoginResult result, String custom_1, String custom_2) {
		LoginResponse resp = new LoginResponse(result.ordinal(), AppMessage.get(result.i18nCode), custom_1, custom_2);
		message.send(resp);
	}

	private enum LoginResult {
		Success(MessageID.USR_LOGIN + ".succ"), 
		Failure(MessageID.USR_LOGIN + ".failure"),
		FailureVerify(MessageID.USR_LOGIN + ".failure.verify"),
		FailureUserNone(MessageID.USR_LOGIN + ".failure.user.none"),
		FailurePasswordError(MessageID.USR_LOGIN + ".failure.password.error");

		public String i18nCode;

		private LoginResult(String i18nCode) {
			this.i18nCode = i18nCode;
		}
	}

	

}
