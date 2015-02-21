package com.lugq.app.logic;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

import com.lugq.app.config.AppConfig;
import com.lugq.app.util.LangUtil;

public class LogicHelper {

	public static String generateFileName(String userSid, int messageID, int order, String customName) {
		long time = System.currentTimeMillis() / 1000;
		String timeHex = Long.toHexString(time);
		
		String sid = StringUtils.leftPad(userSid, 10, "0");
		String sidHex = Integer.toHexString(sid.hashCode() << 16);

		String sep = File.separator;
		if (LangUtil.isEmpty(userSid))
			userSid = "trash";
		String fileName = userSid + sep + messageID + sep + timeHex + sidHex + order + "-" + customName;
		return fileName;
	}
	
	public static String downloadPathPrefix() {
		String path = AppConfig.getInstance().getServerConfig().getFileServer().getDownloadPath();
		return path;
	}
}
