package com.lugq.app.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

	public static String md5(String inStr) {
		String s  = null;
	    char hexDigist[] = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
	    MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	    md.update(inStr.getBytes());
	    byte[] datas = md.digest(); //16个字节的长整数
	    char[] str = new char[2*16];
	    int k = 0;
	    for(int i=0;i<16;i++){
	      byte b   = datas[i];
	      str[k++] = hexDigist[b>>>4 & 0xf];//高4位
	      str[k++] = hexDigist[b & 0xf];//低4位
	    }
	    s = new String(str);
	    return s;

	}
	
	public static void main(String args[]) {
		String a = "阿斯蒂芬奋斗43234";
		String md5 = MD5Util.md5(a);
		System.out.println(md5);
	}
}
