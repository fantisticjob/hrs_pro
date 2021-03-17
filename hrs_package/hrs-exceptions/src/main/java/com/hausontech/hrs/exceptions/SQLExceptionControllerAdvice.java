package com.hausontech.hrs.exceptions;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@ControllerAdvice
public class SQLExceptionControllerAdvice{

	@ExceptionHandler({ BsException.class })
	@ResponseBody
	public JSONObject handleException(
			HttpServletRequest request, HttpServletResponse response,
			BsException exception) throws IOException {
		JSONObject	json = new JSONObject();
		json.put("errCode",0);
		json.put("errMsg", exception.getMsg());
		return json;
	}
}
