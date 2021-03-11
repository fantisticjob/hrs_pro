package com.hausontech.hrs.web.filter;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hausontech.hrs.bean.userManager.pojo.SessionInfo;
import com.hausontech.hrs.utils.ResourceUtil;


//登录session问题
public class SessionFilter implements Filter {


    public SessionFilter() {}


    public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String url = req.getServletPath();
		SessionInfo sessionInfo = (SessionInfo) req.getSession().getAttribute(ResourceUtil.getSessionInfoName());
		if (sessionInfo==null) {
			request.setAttribute("msg", "1");
			res.sendRedirect("/hrs");
		} else {
			// 如果存在进行操作
			chain.doFilter(request, response);
		}
    }
	
	
    public void init(FilterConfig fConfig) throws ServletException {
    }


    public void destroy() {
    	
    }
}