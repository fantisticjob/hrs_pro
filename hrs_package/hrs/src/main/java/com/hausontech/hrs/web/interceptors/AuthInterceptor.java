package com.hausontech.hrs.web.interceptors;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hausontech.hrs.api.userManager.service.IAuthService;
import com.hausontech.hrs.bean.userManager.MrdmResources;
import com.hausontech.hrs.bean.userManager.pojo.SessionInfo;
import com.hausontech.hrs.bean.userManager.pojo.User;
import com.hausontech.hrs.utils.RequestUtil;
import com.hausontech.hrs.utils.ResourceUtil;

/**
 * 权限拦截器
 * 
 * 
 */
public class AuthInterceptor implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

	private IAuthService authService;

	public IAuthService getAuthService() {
		return authService;
	}

	@Autowired
	public void setAuthService(IAuthService authService) {
		this.authService = authService;
	}

	/**
	 * 完成页面的render后调用
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception) throws Exception {
	}

	/**
	 * 在调用controller具体方法后拦截
	 */
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView) throws Exception {

	}

	/**
	 * 在调用controller具体方法前拦截
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {

		String requestPath = RequestUtil.getRequestPath(request);// 用户访问的资源地址

//		boolean b = false;
//		List<Syresources> offResourcesList = authService.offResourcesList(); // 不需要权限验证的资源集合
//		for (Syresources syresources : offResourcesList) {
//			if (syresources.getSrc() != null && syresources.getSrc().equals(requestPath)) {
//				b = true;
//				break;
//			}
//		}
//		if (b) {
//			return true;// 当前访问资源地址是不需要验证的资源
//		}
//
		MrdmResources mrdmResources = authService.getSyresourcesByRequestPath(requestPath);
/*		if (mrdmResources == null) {// 当前访问资源地址没有在数据库中存在
			forward("访问资源地址没有在数据库中存在", request, response);
			return false;
		}*/
		

		if("/userController.do?login".equals(requestPath)) {
			return true;
		}
		
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
//		if (sessionInfo.getUser() == null) {// 没有登录系统，或登录超时
//			forward("您没有登录或登录超时，请重新登录！", request, response);
//			return false;
//		}
		

		User user = sessionInfo.getUser();
		if (user.getName().equals("luis") || 
				user.getName().equals("CSC01106") || 
				user.getName().equals("CSC01277") || 
				user.getName().equals("CSC00090") || 
				user.getName().equals("CFS10073") ||
				user.getName().equals("CSC00299") ||
				user.getName().equals("qhl")||
				user.getName().equals("admin")||
				user.getName()!=null) {// 超级管理员不需要验证权限
			return true;
		}

		if (authService.checkAuth(user.getName(), requestPath)) {// 验证当前用户是否有权限访问此资源
			return true;
		} else {
			forward("您没有【" + mrdmResources.getText() + "】权限，请联系管理员给您赋予相应权限！", request, response);
			return false;
		}

	}

	private void forward(String msg, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("msg", msg);
		request.getRequestDispatcher("/views/error/authMsg.jsp").forward(request, response);
	}

}
