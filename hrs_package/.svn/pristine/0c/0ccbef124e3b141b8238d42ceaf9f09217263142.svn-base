package com.hausontech.hrs.web.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hausontech.hrs.api.userManager.service.IUserService;
import com.hausontech.hrs.bean.userManager.pojo.User;
import com.hausontech.hrs.utils.Encrypt;
import com.hausontech.hrs.utils.JsonUtil;


@Controller
@RequestMapping(value = "/userManage")
public class UserManageController extends BaseController {
	
	/**
	 * 组件日志
	 */
	private static Logger logger = LoggerFactory.getLogger(UserManageController.class);

	@Autowired
	private IUserService userService;



	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		UserManageController.logger = logger;
	}
	
	@RequestMapping(value = "/findRoleInfo")
	@ResponseBody
	public JsonUtil findRoleInfo(HttpServletRequest request,HttpServletResponse response){
		
		
		return null;
	}

	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findUserInfo")
	@ResponseBody
	public Object findUserInfo(HttpServletRequest request,HttpServletResponse response){
		List<User> list = null;
		/*
		 * userDetails参数暂时没用，用作以后扩展
		 */
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		list = userService.combobox(userDetails.getUsername());
		dataMap.clear();
		dataMap.put("total", userService.selectCount());
		dataMap.put("rows", list);
		
		return JsonUtil.fromObject(this.dataMap);
	}
	
	@RequestMapping(value = "/addUserInfo")
	@ResponseBody
	public Object addUserInfo(HttpServletRequest request,HttpServletResponse response){
		User u = null;
//		boolean isError = false;
//		String errorMsg = "保存信息失败：请检查输入数据，重新提交！";
		
		if(request!=null){
			u = new User();
			try {
				//set username
				u.setId(this.getDecodedRequestParam(request, "id"));
				//set fullname
				u.setName(this.getDecodedRequestParam(request, "name"));
				//set password
				u.setPassword(Encrypt.md5(this.getDecodedRequestParam(request, "password")));
				//set createDateTime
				Date date = new Date();
				u.setCreatedatetime(date);
				//set roleId
				u.setRoleId(this.getDecodedRequestParam(request, "roleId"));
			} catch (UnsupportedEncodingException e) {
//				isError = true;
				e.printStackTrace();
			}
		}
		
		
		
		return userService.add(u);
		
	}
	
	@RequestMapping(value = "/delUserInfo")
	@ResponseBody
	public Object delUserInfo(HttpServletRequest request,HttpServletResponse response){
		String id = "";
		if(request!=null){
			try {
				id = this.getDecodedRequestParam(request, "id");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		userService.del(id);
		return id;
		
	}
	
	@RequestMapping(value = "/updateUserInfo")
	@ResponseBody
	public Object updateUserInfo(HttpServletRequest request,HttpServletResponse response){
		User user = new User();
		if(request!=null){
			try {
				user.setId(this.getDecodedRequestParam(request, "id"));
				user.setName(this.getDecodedRequestParam(request, "name"));
				user.setPassword(this.getDecodedRequestParam(request, "password"));
				user.setRoleId(this.getDecodedRequestParam(request, "roleId"));
				user.setModifydatetime(new Date());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return userService.edit(user);
		
	}
}
