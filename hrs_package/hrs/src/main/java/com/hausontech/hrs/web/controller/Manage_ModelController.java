package com.hausontech.hrs.web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hausontech.hrs.api.constants.Constants;
import com.hausontech.hrs.api.dimensionManager.service.IRptGeneratorConfigService;
import com.hausontech.hrs.bean.userManager.pojo.User;
import com.hausontech.hrs.utils.Comp;
import com.hausontech.hrs.utils.Loap;
import com.hausontech.hrs.utils.StringUtil;
import com.hausontech.hrs.utils.Tan;
import com.hausontech.hrs.utils.ThirdLevelType;

@Controller
@RequestMapping(value = "/mainFrame")
public class Manage_ModelController extends BaseController {


	@Resource
	private IRptGeneratorConfigService rptGenConfigService;
	
	public IRptGeneratorConfigService getRptGenConfigService() {
		return rptGenConfigService;
	}

	public void setRptGenConfigService(IRptGeneratorConfigService rptGenConfigService) {
		this.rptGenConfigService = rptGenConfigService;
	}

	@RequestMapping(value = "/portal")
	public String showPortal(Model model) {
		return "MainForm";
	}
		
	@RequestMapping(value = "/findLoginFirstMenu")
	@ResponseBody
	public Object loadFirstLevMenu(Model model, HttpSession session,HttpServletResponse response,HttpServletRequest request) {
		//get user in the session
		String userId = (String) session.getAttribute(Constants.USER_KEY);
		//get role in the request
		User user = this.getLoginUser(request);
		String roleText = "User";
		if (user != null) {
			roleText="Admin";
					//user.getRoleText();
			}
		//要返回的列表
		List<Map<String, Object>> list = null;
		//初始化菜单数据
		list = new ArrayList<Map<String, Object>>();
//		if(!(Tan.lp().equals(Comp.dp()))){
//			if(Loap.la(request).equals(ThirdLevelType.QP)){
		//set 报表设置菜单
		Map<String, Object> innerMap = new HashMap<String, Object>();
		innerMap.put("id", "100101");
		innerMap.put("text", "报表设置");
		innerMap.put("attributes", new HashMap<String, Object>().put("href", "/hrs/reportSetting"));
		//if("Admin".equals(roleText)){
		list.add(innerMap);
		//}
		//设置维度管理菜单
		innerMap = new HashMap<String, Object>();
		innerMap.put("id", "100102");
		innerMap.put("text", "维度管理");
		innerMap.put("attributes", new HashMap<String, Object>().put("href", "/hrs/demionsionManage"));
		//if("Admin".equals(roleText)){
		list.add(innerMap);
		//}
		//设置数据处理
		innerMap = new HashMap<String, Object>();
		innerMap.put("id", "100103");
		innerMap.put("text", "数据处理");
		innerMap.put("attributes", new HashMap<String, Object>().put("href", "/hrs/dataProcessing"));
		//if("Admin".equals(roleText)){
		list.add(innerMap);
		//}
		
		//ELT运行
		innerMap = new HashMap<String, Object>();
		innerMap.put("id", "100104");
		innerMap.put("text", "ETL任务");
		innerMap.put("attributes", new HashMap<String, Object>().put("href", "/hrs/etlProcessing"));
		//if("Admin".equals(roleText)){
		list.add(innerMap);
		//}
		
//	
//			}else{
//				try {
//					request.getRequestDispatcher("/error/webErr.jsp").forward(request, response);
//				} catch (ServletException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
		return list;
		
	}
	

	/**
	 * 获取登录操作员的二三级菜单
	 */
	@RequestMapping(value = "/findLgoinSecondMenu")
	@ResponseBody
	public Object findLgoinSecondMenu(HttpServletRequest request,HttpServletResponse response) {
		
		String maindatauuid = request.getParameter("maindatauuid");

		//要返回的列表
		List<Map<String, Object>> list = null;
		//一级菜单list
		list = new ArrayList<Map<String,Object>>();
		
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
			
		if (null != maindatauuid && maindatauuid.length() > 0) {
			try {
				maindatauuid = java.net.URLDecoder.decode(maindatauuid, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		if (maindatauuid.equals("100101")) {
			//一级map
			Map<String,Object> map1 = new HashMap<String,Object>();
			map1.put("id", "100101");
			map1.put("text", "报表设置");
			list.add(map1);
			
			//第一个一级菜单下面的二级菜单
			List<Map<String, Object>> list11 = new ArrayList<Map<String,Object>>();
			//第一个2级菜单
			Map<String,Object> map11 = new HashMap<String,Object>();
			map11.put("id", "10010101");
			map11.put("text", "值集定义");	
			Map<String,Object> map111 = new HashMap<String,Object>();
			map111.put("href", "../views/configuration/valueSetConfiguration.jsp");	
			map11.put("attributes", map111);
			list11.add(map11);
			map1.put("children", list11);
			//第二个二级菜单
			Map<String,Object> map12 = new HashMap<String,Object>();
			map12.put("id", "10010102");
			map12.put("text", "外部代码定义");	
			Map<String,Object> map121 = new HashMap<String,Object>();
			map121.put("href", "../views/configuration/externalCodeConfiguration.jsp");	
			map12.put("attributes", map121);
			list11.add(map12);
			//第三个二级菜单			
			Map<String,Object> map13 = new HashMap<String,Object>();
			map13.put("id", "10010103");
			map13.put("text", "表项循环组定义");	
			Map<String,Object> map131 = new HashMap<String,Object>();
			map131.put("href", "../views/configuration/loopGroupConfiguration.jsp");	
			map13.put("attributes", map131);
			list11.add(map13);
			//第四个二级菜单
			Map<String,Object> map14 = new HashMap<String,Object>();
			map14.put("id", "10010104");
			map14.put("text", "表项规则定义");	
			Map<String,Object> map141 = new HashMap<String,Object>();
			map141.put("href", "../views/configuration/itemRuleConfiguration.jsp");	
			map14.put("attributes", map141);
			list11.add(map14);
			//第五个二级菜单
			Map<String,Object> map15 = new HashMap<String,Object>();
			map15.put("id", "10010105");
			map15.put("text", "报表行集定义");	
			Map<String,Object> map151 = new HashMap<String,Object>();
			map151.put("href", "../views/configuration/reportRowSetConfiguration.jsp");	
			map15.put("attributes", map151);
			list11.add(map15);
			map1.put("children", list11);
		} else if (maindatauuid.equals("100102")) {
			Map<String,Object> map2 = new HashMap<String,Object>();
			map2.put("id", "100102");
			map2.put("text", "维度管理");
			list.add(map2);
			
			List<Map<String, Object>> list21 = new ArrayList<Map<String,Object>>();
			Map<String,Object> map21 = new HashMap<String,Object>();
			map21.put("id", "10010201");
			map21.put("text", "维度设置");
			Map<String,Object> map211 = new HashMap<String,Object>();
			map211.put("href", "../views/configuration/dimensionConfiguration.jsp");	
			map21.put("attributes", map211);
			list21.add(map21);
			map2.put("children", list21);
			
		} else if (maindatauuid.equals("100103")) {
			Map<String,Object> map3 = new HashMap<String,Object>();
			map3.put("id", "100103");
			map3.put("text", "数据处理");
			list.add(map3);
			
			List<Map<String, Object>> list31 = new ArrayList<Map<String,Object>>();
			Map<String,Object> map31 = new HashMap<String,Object>();
			map31.put("id", "131");
			map31.put("text", "报表指标计算");
			Map<String,Object> map311 = new HashMap<String,Object>();
			map311.put("href", "../dataProcessing/showDataProcessView.do?");	
			map31.put("attributes", map311);
			list31.add(map31);
			map3.put("children", list31);
		} else if (maindatauuid.equals("100104")) {
			Map<String,Object> map4 = new HashMap<String,Object>();
			map4.put("id", "100104");
			map4.put("text", "ETL任务");
			list.add(map4);
			
			List<Map<String, Object>> list41 = new ArrayList<Map<String,Object>>();
			Map<String,Object> map41 = new HashMap<String,Object>();
			map41.put("id", "141");
			map41.put("text", "ELT任务调度");
			Map<String,Object> map411 = new HashMap<String,Object>();
			map411.put("href", "../views/configuration/etlJobConfiguration.jsp");	
			map41.put("attributes", map411);
			list41.add(map41);
			
			Map<String,Object> map42 = new HashMap<String,Object>();
			map42.put("id", "142");
			map42.put("text", "ELT任务调度日期设置");
			Map<String,Object> map412 = new HashMap<String,Object>();
			map412.put("href", "../views/configuration/etlScheduleConfiguration.jsp");	
			map42.put("attributes", map412);
			list41.add(map42);
			
			map4.put("children", list41);
		} 

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
		return list;
	} 	
}
