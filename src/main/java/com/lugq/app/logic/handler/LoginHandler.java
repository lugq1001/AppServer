package com.lugq.app.logic.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lugq.app.config.AppConfig;
import com.lugq.app.config.AppMessage;
import com.lugq.app.helper.annotation.LogicHandler;
import com.lugq.app.model.entity.User;
import com.lugq.app.network.BaseRequest;
import com.lugq.app.network.BaseResponse;
import com.lugq.app.network.SBMessage;
import com.lugq.app.util.LangUtil;
import com.lugq.app.util.MD5Util;
import com.lugq.app.logic.MessageID;

@LogicHandler(desc = "用户登录接口", id = MessageID.USR_LOGIN)
public class LoginHandler extends AppServerHandler {

	private static Logger logger = LogManager.getLogger(LoginHandler.class);
	
	@Override
	public void logicProcess(SBMessage message) {
		try {
			logger.debug("-用户登录-");
			String reqData = message.getReq_data();
			logger.debug("-请求数据-" + reqData);
			LoginRequest req = objMapper.readValue(reqData, LoginRequest.class);

			String username = req.username;
			String password = req.password;
			String s = req.getS();

			// 验证MD5
			String magicKey = AppConfig.getInstance().getServerConfig().getLogicServer().getMagicKey();
			String verify = MD5Util.md5(username + magicKey + password);
			if (!verify.equals(s)) {
				logger.debug("-登录失败-验证错误");
				sendFailureResp(message, LoginResult.FailureVerify);
				return;
			}

			// 用户名为空
			if (LangUtil.isEmpty(username)) {
				logger.debug("-登录失败-用户名为空");
				sendFailureResp(message, LoginResult.FailureUserNone);
				return;
			}

			// 查找用户
			User u = User.findByUsername(username);

			// 用户不存在
			if (u == null) {
				logger.debug("-登录失败-" + username + " 不存在");
				sendFailureResp(message, LoginResult.FailureUserNone);
				return;
			}

			// 密码错误
			if (!u.getPassword().equals(password)) {
				logger.debug("-登录失败-" + username + " 密码错误");
				sendFailureResp(message, LoginResult.FailurePasswordError);
				return;
			}
			long time = System.currentTimeMillis();
			// 更新用户最后登录时间
			u.setLateseLogin(time);
			u.save();
			logger.debug("-更新用户最后登录时间-" + time);
			// 响应
			LoginResult result = LoginResult.Success;
			LoginResponse resp = new LoginResponse(result.ordinal(), AppMessage.get(result.i18nCode));
			resp.user = u;
			message.send(resp);
			logger.debug("-登录成功-" + u.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("-登录失败-" + e.getLocalizedMessage());
			sendFailureResp(message, LoginResult.Failure);
		}
	}

	private static class LoginRequest extends BaseRequest {

		public String username;
		public String password;

	}

	public class LoginResponse extends BaseResponse {

		public LoginResponse(int retCode, String retMsg) {
			super(retCode, retMsg);
			reqid = MessageID.USR_LOGIN;
		}

		public User user;
	}

	private void sendFailureResp(SBMessage message, LoginResult result) {
		LoginResponse resp = new LoginResponse(result.ordinal(), AppMessage.get(result.i18nCode));
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
