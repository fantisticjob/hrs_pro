package com.hausontech.hrs.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hausontech.hrs.api.userManager.service.IUserService;
import com.hausontech.hrs.bean.Json;
import com.hausontech.hrs.bean.userManager.pojo.SessionInfo;
import com.hausontech.hrs.bean.userManager.pojo.User;
import com.hausontech.hrs.utils.Comp;
import com.hausontech.hrs.utils.Loap;
import com.hausontech.hrs.utils.ResourceUtil;
import com.hausontech.hrs.utils.Tan;
import com.hausontech.hrs.utils.ThirdLevelType;

@Controller
@RequestMapping({ "/userController" })
public class AuthenticationController {

	@Autowired
	private IUserService userService;

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	@ResponseBody
	public Json userLogin(HttpSession session, HttpServletRequest request) {
		Json j = new Json();
		j.setMsg("登录成功!");
		return j;
	}
	
	
	
//	@RequestMapping(params = "login")
//	@ResponseBody
//	public Json userLogin(User user, HttpSession session, HttpServletRequest request) {
//		Json j = new Json();
//		User u = userService.login(user);
//		
//		if (u != null) {
//			j.setSuccess(true);
//			j.setMsg("登录成功!");
//
//			SessionInfo sessionInfo = new SessionInfo();
//			sessionInfo.setUser(u);
//			session.setAttribute(ResourceUtil.getSessionInfoName(), sessionInfo);
//			j.setObj(u);
//			
//		} else {
//			j.setMsg("用户名或密码错误!");
//		}
//		return j;
//	}

	@RequestMapping({"/performLogout" })
	public String performLogout(HttpSession session,HttpServletRequest request,HttpServletResponse response) {
//		Map result = new HashMap();
//		session.removeAttribute(ResourceUtil.getSessionInfoName());
//		result.put("success", Boolean.valueOf(true));
//		result.put("message", "logout successfully");
		return "/";
	}
	
	@RequestMapping({"/timedout" })
	public String sessionTimeOut(HttpSession session,HttpServletRequest request,HttpServletResponse response) {
		
		return "sessionTimeOut";
	}
}