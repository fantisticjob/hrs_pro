package com.hausontech.hrs.web.controller;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.hausontech.hrs.api.constants.Constants;
import com.hausontech.hrs.bean.userManager.pojo.SessionInfo;
import com.hausontech.hrs.bean.userManager.pojo.User;
import com.hausontech.hrs.utils.CustomDateEditor;
import com.hausontech.hrs.utils.ResourceUtil;

import net.sf.json.JSONArray;

public class BaseController {
	// Spring配置或者Action传入传出时使用，需要实现get，set
	protected int page;
	protected int rows;
	protected int queryRowStartIndex;
	protected Map dataMap = new HashMap();
	protected String condition;
	protected String maindatauuid;
	protected String maindata;
	protected JSONArray jsonarr;
	protected String exportflag;
	protected final String ERROR_MESSAGE = "保存信息失败：请检查输入数据，重新提交！";
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public Map getDataMap() {
		return dataMap;
	}
	public void setDataMap(Map dataMap) {
		this.dataMap = dataMap;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getMaindatauuid() {
		return maindatauuid;
	}
	public void setMaindatauuid(String maindatauuid) {
		this.maindatauuid = maindatauuid;
	}
	public String getMaindata() {
		return maindata;
	}
	public void setMaindata(String maindata) {
		this.maindata = maindata;
	}
	public JSONArray getJsonarr() {
		return jsonarr;
	}
	public void setJsonarr(JSONArray jsonarr) {
		this.jsonarr = jsonarr;
	}
	public String getExportflag() {
		return exportflag;
	}
	public void setExportflag(String exportflag) {
		this.exportflag = exportflag;
	}
	
	public int getQueryRowStartIndex() {
		return queryRowStartIndex;
	}
	public void setQueryRowStartIndex(int queryRowStartIndex) {
		this.queryRowStartIndex = queryRowStartIndex;
	}
	protected String getDecodedRequestParam(HttpServletRequest request, String paraName) throws UnsupportedEncodingException {
		String paraValue = null;
		if (request != null && !StringUtils.isBlank(request.getParameter(paraName))) {
			paraValue = request.getParameter(paraName);
			if (paraValue.contains(Constants.PERCENT_CHARACTER)) {
				paraValue.replaceAll("%(?![0-9a-fA-F]{2})", Constants.PERCENT_TRANS_CHARACTER);
			}
			paraValue = java.net.URLEncoder.encode(request.getParameter(paraName),"UTF-8");
			paraValue = java.net.URLDecoder.decode(paraValue,"UTF-8");
		}
		return paraValue;	
	}
	
	protected void initilizePagingInfo(HttpServletRequest request) throws UnsupportedEncodingException {
		this.page = 1;
		this.rows = 10;
		this.queryRowStartIndex = 1;
		if (request != null) {
			String currPage = this.getDecodedRequestParam(request, "page");
			if (!StringUtils.isBlank(currPage) && !"0".equals(currPage)) {
				this.page = Integer.parseInt(currPage);
			}
			String strPageCount =  this.getDecodedRequestParam(request, "rows");
			if (!StringUtils.isBlank(strPageCount) && !"0".equals(strPageCount)) {
				this.rows = Integer.parseInt(strPageCount);
			}
			this.queryRowStartIndex = (this.page - 1) * this.rows + 1;
		}
	}
	
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		// binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
	}
	
	
	public User getLoginUser(HttpServletRequest request) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
		if (sessionInfo == null) {// 没有登录系统，或登录超时
			return null;
		}
		return sessionInfo.getUser();
	}
	
	public String getLoginUserName(HttpServletRequest request) {
		String userName = null;
		User user = this.getLoginUser(request);
		if (user != null) {
			userName = user.getName();
		}
		return userName;
	}
}
