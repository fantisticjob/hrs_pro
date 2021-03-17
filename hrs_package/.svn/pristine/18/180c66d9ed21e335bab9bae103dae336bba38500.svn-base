
package com.hausontech.hrs.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;


public class StringUtil {

	/**
	 * 判断字对象是否为NULL 或�?? “�??
	 * 
	 * @param object
	 * @return 空：true 非空：false
	 *  
	 * @date 2012-6-7
	 */
	public static boolean isEmpty(Object object) {
		if(object==null) return true;
		if("".equals(object.toString())){return true;}
		return false;
	}
	
	public static boolean isEmptyTrim(String str) {
		if(StringUtils.isEmpty(str)){
			return true;
		}
		if(StringUtil.isEmpty(str.trim())){
			return true;
		}
		return false;
	}

	/**
	 * @param args
	 *  
	 * @date 2012-6-7
	 * @see
	 */
	public static void main(String[] args) {
		System.out.println(makeEOName("USER_NAME"));
	}

	/**
	 * 将java命名转换成数据库命名，例如：userNameDs--->USER_NAME_DS
	 * @param key
	 * @return
	 * @author Administrator
	 * @date 2012-8-8
	 * @see
	 */
	public static String makeDBName(String key) {
		if(isEmpty(key)){
			return null;
		}
		char[] chars = key.toCharArray();
		String result = "";
		for (int i=0;i<chars.length;i++) {
			char c = chars[i];
			if(c>='A' && c<='Z'){
				result += "_"+c;
			}else{
				result += c;
			}
		}
		return result.toUpperCase();
	}
	/**
	 * 将java命名转换成数据库命名，例如：USER_NAME--->userName
	 * @param key
	 * @return
	 * @author zheng shanwei
	 * @date 2012-8-9
	 * @see
	 */
	public static String makeEOName(String key) {

		char[] chars = key.toLowerCase().toCharArray();
		String result = "";
		int i=0;
		while(i<chars.length){
			char c = chars[i];
			if(c=='_'){
				result += (chars[i+1]+"").toUpperCase();
				i++;
			}else{
				result += c;
			}
			i++;
		}
		return result;
	}
	
	/**
	 * 从链接地�?得到参数集合
	 * @param url
	 * 			bds/project/prestoreproject.xhtml?projectType=01&isDetail=01
	 * @return
	 * 			{projectType=01,isDetail=01}
	 * @author xyg
	 * @date 2013-2-20
	 * @see
	 */
	public static List<String> getParamFromUrl(String url){
		if(StringUtil.isEmpty(url) || url.indexOf("?")<0){
			return null;
		}
		String[] str = url.split("\\?");
		if(str.length<2){
			return null;
		}
		String paramStr = str[1];
		//得到参数字符�?
		List<String> params = new ArrayList<String>();
		String[] paramArray = null;
		if(!StringUtil.isEmpty(paramStr) && paramStr.indexOf("&")>0){
			paramArray = paramStr.split("&");
			for (String param : paramArray) {
				if(param.indexOf("Id")<0){
					//排除动�?�的参数
					params.add(param);
				}
			}
		}else{
			if(paramStr.indexOf("Id")<0){
				params.add(paramStr);
			}
		}
		return params;
	}
	
	/**
	 * 从链接地�?获得资源路径
	 * @param url
	 * 			bds/project/prestoreproject.xhtml?projectType=01
	 * @return
	 * 			bds/project/prestoreproject.xhtml
	 * @author xyg
	 * @date 2013-2-20
	 * @see
	 */
	public static String getResouceAddrFromUrl(String url){
		if(StringUtil.isEmpty(url) || url.indexOf("?")<0){
			return url;
		}
		return url.substring(0, url.indexOf("?"));
	}
	
}
