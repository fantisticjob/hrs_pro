package com.hausontech.hrs.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Exception工具�?
 * 
 * 
 */
public class ExceptionUtil {

	/**
	 * 返回错误信息字符�?
	 * 
	 * @param ex
	 *            Exception
	 * @return 错误信息字符�?
	 */
	public static String getExceptionMessage(Exception ex) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		return sw.toString();
	}

}
