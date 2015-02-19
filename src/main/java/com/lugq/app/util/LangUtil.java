package com.lugq.app.util;

import java.util.Collection;

public class LangUtil {

	public static boolean isEmpty(Collection<?> coolection) {
		return coolection == null || coolection.isEmpty();
	}
	
	public static boolean isEmpty(String s) {
		return s == null || s.isEmpty();
	}
	
	public static String StringRidOfNull(String s) {
		return s == null ? "" : s;
	}
	
}
