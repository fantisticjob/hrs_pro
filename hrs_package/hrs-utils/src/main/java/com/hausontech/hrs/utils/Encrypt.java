package com.hausontech.hrs.utils;

import it.sauronsoftware.base64.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密工具
 * 
 * md5加密出来的长度是32
 * 
 * sha加密出来的长度是40
 * 
 * base64加密可以指定字符集，可以解密
 * 
 * @author  
 * 
 */
public class Encrypt {

	/**
	 * 测试
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// md5加密测试
		String md5_1 = md5("123456");
		String md5_2 = md5("AA");
		System.out.println(md5_1 + "\n" + md5_2);
		// sha加密测试
		String sha_1 = sha("123456");
		String sha_2 = sha("AA");
		System.out.println(sha_1 + "\n" + sha_2);
		

	}

	/**
	 * 加密
	 * 
	 * @param inputText
	 * @return
	 */
	public static String e(String inputText) {
		return md5(inputText);
	}

	/**
	 * 解密
	 * 
	 * @param inputText
	 * @return
	 */
	public static String d(String inputText) {
		return base64Decode(inputText, "UTF-8");
	}

	/**
	 * 使用Base64加密
	 * 
	 * @param inputText
	 * @param charset
	 *            字符
	 * @return
	 */
	public static String base64Encode(String inputText, String... charset) {
		if (charset.length == 1) {
			return Base64.encode(inputText, charset[0]);
		} else {
			return Base64.encode(inputText);
		}
	}

	/**
	 * Base64解密
	 * 
	 * @param inputText
	 * @param charset
	 *            字符
	 * @return
	 */
	public static String base64Decode(String inputText, String... charset) {
		if (charset.length == 1) {
			return Base64.decode(inputText, charset[0]);
		} else {
			return Base64.decode(inputText);
		}
	}

	/**
	 * 二次加密，应该破解不了了吧？
	 * 
	 * @param inputText
	 * @return
	 */
	public static String md5AndSha(String inputText) {
		return sha(md5(inputText));
	}

	/**
	 * md5加密
	 * 
	 * @param inputText
	 * @return
	 */
	public static String md5(String inputText) {
		return encrypt(inputText, "md5");
	}

	/**
	 * sha加密
	 * 
	 * @param inputText
	 * @return
	 */
	public static String sha(String inputText) {
		return encrypt(inputText, "sha-1");
	}

	/**
	 * md5或sha-1加密
	 * 
	 * @param inputText
	 *            要加密的内容
	 * @param algorithmName
	 *            加密算法名称：md5或sha-1，不区分大小
	 * @return
	 */
	private static String encrypt(String inputText, String algorithmName) {
		if (inputText == null || "".equals(inputText.trim())) {
			throw new IllegalArgumentException("请输入要加密的内");
		}
		if (algorithmName == null || "".equals(algorithmName.trim())) {
			algorithmName = "md5";
		}
		String encryptText = null;
		try {
			MessageDigest m = MessageDigest.getInstance(algorithmName);
			m.update(inputText.getBytes("UTF8"));
			byte s[] = m.digest();
			// m.digest(inputText.getBytes("UTF8"));
			return hex(s);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encryptText;
	}

	/**
	 * 返回十六进制字符
	 * 
	 * @param arr
	 * @return
	 */
	private static String hex(byte[] arr) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; ++i) {
			sb.append(Integer.toHexString((arr[i] & 0xFF) | 0x100).substring(1, 3));
		}
		return sb.toString();
	}

}
