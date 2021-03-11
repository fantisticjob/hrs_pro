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

	@SuppressWarnings("static-access")
	@RequestMapping(params = "login")
	@ResponseBody
	public Json userLogin(User user, HttpSession session, HttpServletRequest request,HttpServletResponse response) {
		Json j = new Json();
		User u = userService.login(user);
		Loap lop = new Loap();

		
		SessionInfo sessionInfo = new SessionInfo();
		if (u != null) {
//			if(!(Tan.lp().equals(Comp.dp()))){
//				if(lop.la(request).equals(ThirdLevelType.QP)){
//				
					j.setSuccess(true);
					j.setMsg("登录成功!");
		
					sessionInfo.setUser(u);
					session.setAttribute(ResourceUtil.getSessionInfoName(), sessionInfo);
					j.setObj(u);
					System.out.println(sessionInfo.getUser());
//				} else{
//					//sessionInfo.setMessageInfo("webErr");
//					j.setMsg("访问权限不足！");
//					//System.out.println("跳转页面-----------------");
//				}
//			}
		}else {
			j.setMsg("用户名或密码错误!");
		}
		return j;
	}

	@RequestMapping({ "/performLogout" })
	public String performLogout(HttpSession session,HttpServletRequest request,HttpServletResponse response) {
		Map result = new HashMap();
		Loap lop = new Loap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(lop.la(request).equals(ThirdLevelType.QP)){
				session.removeAttribute(ResourceUtil.getSessionInfoName());
				result.put("success", Boolean.valueOf(true));
				result.put("message", "logout successfully");
			}else{
				try {
					request.getRequestDispatcher("/error/webErr.jsp").forward(request, response);
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return "redirect:/";
	}
}