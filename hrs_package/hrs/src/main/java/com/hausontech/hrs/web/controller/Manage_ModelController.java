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

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

	@RequestMapping(value = "/portal",method = RequestMethod.GET)
	public String showPortal(Model model) {
		return "MainForm";
	}
		
	@RequestMapping(value = "/findLoginFirstMenu")
	@ResponseBody
	public Object loadFirstLevMenu(Model model, HttpSession session,HttpServletResponse response,HttpServletRequest request) {
		//get user in the session
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
			    .getAuthentication()
			    .getPrincipal();

		String userId = userDetails.getUsername();
		//String userId = (String) session.getAttribute(Constants.USER_KEY);
		//get role in the request
		//User user = this.getLoginUser(request);
		UserDetails user = this.getLoginUser(request);
		String roleText = "User";
		if (user != null) {
			roleText="Admin";
					//user.getRoleText();
			}
		//要返回的列表
		List<Map<String, Object>> list = null;
		//初始化菜单数据
		list = new ArrayList<Map<String, Object>>();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
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
		
		//事务处理
		innerMap = new HashMap<String, Object>();
		innerMap.put("id", "100105");
		innerMap.put("text", "事务处理");
		innerMap.put("attributes", new HashMap<String, Object>().put("href", "/hrs/transProcessing"));
		//if("Admin".equals(roleText)||"TransactionI".equals(roleText)||"TransactionM".equals(roleText)){
		list.add(innerMap);
		//}
		
		//分摊管理
		innerMap = new HashMap<String, Object>();
		innerMap.put("id", "100106");
		innerMap.put("text", "分摊管理");
		innerMap.put("attributes", new HashMap<String, Object>().put("href", "/hrs/dimFilterManage"));
		//if("Admin".equals(roleText)){
		list.add(innerMap);
		//}
		
		//用户管理
		innerMap = new HashMap<String, Object>();
		innerMap.put("id", "100107");
		innerMap.put("text", "用户管理");
		innerMap.put("attributes", new HashMap<String, Object>().put("href", "/hrs/userManage"));
		if("Admin".equals(roleText)){
		list.add(innerMap);
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
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
			
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
			map311.put("href", "/hrs/dataProcessing/showDataProcessView.do?");	
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
		} else if (maindatauuid.equals("100105")) {
			Map<String,Object> map5 = new HashMap<String,Object>();
			map5.put("id", "100105");
			map5.put("text", "事务处理");
			list.add(map5);
			
			List<Map<String, Object>> list51 = new ArrayList<Map<String,Object>>();
			
			Map<String,Object> map51 = new HashMap<String,Object>();
			map51.put("id", "151");
			map51.put("text", "事务信息录入");
			Map<String,Object> map511 = new HashMap<String,Object>();
			map511.put("href", "/hrs/txnProcess.do?showTxnProcHeaderView");	
			//map511.put("href", "../views/configuration/transactionProcessHeader.jsp");	
			map51.put("attributes", map511);
			list51.add(map51);
			
			Map<String,Object> map52 = new HashMap<String,Object>();
			map52.put("id", "152");
			map52.put("text", "Excel导入");
			Map<String,Object> map512 = new HashMap<String,Object>();
			//map511.put("href", "/hrs/txnProcess.do?showTxnProcHeaderView");	
			map512.put("href", "../views/configuration/mrdmExcel.jsp");	
			map52.put("attributes", map512);
			list51.add(map52);
			
//			User user = this.getLoginUser(request);
//			if (user != null) {
//				String roleText = user.getRoleText();
//				if (!StringUtil.isEmptyTrim(roleText) && ("Admin".equals(roleText)||"TransactionM".equals(roleText))) {
					Map<String,Object> map53 = new HashMap<String,Object>();
					map53.put("id", "153");
					map53.put("text", "事务审批");
					Map<String,Object> map513 = new HashMap<String,Object>();
					//map511.put("href", "/hrs/txnProcess.do?showTxnProcHeaderView");	
					map513.put("href", "../views/configuration/transactionAuidt.jsp");	
					map53.put("attributes", map513);
					list51.add(map53);
//				}
//			}		
//			if (user != null) {
//				String roleText = user.getRoleText();
//				if (!StringUtil.isEmptyTrim(roleText) && ("Admin".equals(roleText))) {
//					Map<String,Object> map54 = new HashMap<String,Object>();
//					map54.put("id", "154");
//					map54.put("text", "权限管理");
//					Map<String,Object> map514 = new HashMap<String,Object>();
//					//map511.put("href", "/hrs/txnProcess.do?showTxnProcHeaderView");	
//					map514.put("href", "../views/configuration/AllocationRulesSetting/roleConfiguration.jsp");	
//					map54.put("attributes", map514);
//					list51.add(map54);
//				}
//			}	
			map5.put("children", list51);
			
		} else if (maindatauuid.equals("100106")) {
			Map<String,Object> map6 = new HashMap<String,Object>();
			map6.put("id", "100106");
			map6.put("text", "分摊管理");
			list.add(map6);
			
			List<Map<String, Object>> list61 = new ArrayList<Map<String,Object>>();
			
			Map<String,Object> map61 = new HashMap<String,Object>();
			map61.put("id", "161");
			map61.put("text", "维度筛选条件设置");
			Map<String,Object> map611 = new HashMap<String,Object>();
			//map511.put("href", "/hrs/txnProcess.do?showTxnProcHeaderView");	
			map611.put("href", "../views/configuration/AllocationRulesSetting/dimensionFilterHeaderConfiguration.jsp"
					+ "");	
			map61.put("attributes", map611);
			list61.add(map61);
			
			Map<String,Object> map62 = new HashMap<String,Object>();
			map62.put("id", "162");
			map62.put("text", "静态分摊因子设置");
			Map<String,Object> map612 = new HashMap<String,Object>();
			map612.put("href", "../views/configuration/AllocationRulesSetting/driverStaticHeaderConfiguration.jsp");	
			map62.put("attributes", map612);
			list61.add(map62);
			
			Map<String,Object> map63 = new HashMap<String,Object>();
			map63.put("id", "163");
			map63.put("text", "分摊规则组设置");
			Map<String,Object> map613 = new HashMap<String,Object>();
			map613.put("href", "../views/configuration/AllocationRulesSetting/allocRulesGroupHaeaderConfiguration.jsp");	
			map63.put("attributes", map613);
			list61.add(map63);
			
			Map<String,Object> map64 = new HashMap<String,Object>();
			map64.put("id", "164");
			map64.put("text", "分摊规则设置");
			Map<String,Object> map614 = new HashMap<String,Object>();
			map614.put("href", "../views/configuration/AllocationRulesSetting/allocRuleConfiguration.jsp");	
			map64.put("attributes", map614);
			list61.add(map64);
			
			Map<String,Object> map65 = new HashMap<String,Object>();
			map65.put("id", "165");
			map65.put("text", "分摊规则运行");
			Map<String,Object> map615 = new HashMap<String,Object>();
			map615.put("href", "/hrs/dataProcessing/showAllocDataProcessView.do?");	
			//map615.put("href", "../views/configuration/AllocationRulesSetting/allocJobConfiguration.jsp");	
			map65.put("attributes", map615);
			list61.add(map65);
			
			Map<String,Object> map66 = new HashMap<String,Object>();
			map66.put("id", "166");
			map66.put("text", "分摊规则调度运行日期设置");
			Map<String,Object> map616 = new HashMap<String,Object>();
			map616.put("href", "/hrs/dataProcessing/showAllocDataScheduleProcessView.do?");	
			//map615.put("href", "../views/configuration/AllocationRulesSetting/allocJobConfiguration.jsp");	
			map66.put("attributes", map616);
			list61.add(map66);
			
			Map<String,Object> map67 = new HashMap<String,Object>();
			map67.put("id", "167");
			map67.put("text", "分摊运行历史记录");
			Map<String,Object> map617 = new HashMap<String,Object>();
			map617.put("href", "/hrs/dataProcessing/showAllocInstanceHistoryView.do?");	
			map67.put("attributes", map617);
			list61.add(map67);
			map6.put("children", list61);
		}else if(maindatauuid.equals("100107")){
			Map<String,Object> map7 = new HashMap<String,Object>();
			map7.put("id", "100107");
			map7.put("text", "用户管理");
			list.add(map7);
			
			List<Map<String, Object>> list71 = new ArrayList<Map<String,Object>>();
			Map<String,Object> map71 = new HashMap<String,Object>();
			map71.put("id", "171");
			map71.put("text", "用户管理");
			Map<String,Object> map711 = new HashMap<String,Object>();
			map711.put("href", "../views/configuration/userManagement.jsp");	
			map71.put("attributes", map711);
			
			list71.add(map71);
			map7.put("children", list71);
		}else if(maindatauuid.equals("100108")){
			Map<String,Object> map8 = new HashMap<String,Object>();
			map8.put("id", "100108");
			map8.put("text", "银企直联操作平台");
			list.add(map8);
			
			List<Map<String, Object>> list81 = new ArrayList<Map<String,Object>>();
			
			Map<String,Object> map81 = new HashMap<String,Object>();
			map81.put("id", "181");
			map81.put("text", "支付记录维护");
			Map<String,Object> map811 = new HashMap<String,Object>();
			map811.put("href", "../views/BankEnterpriseDirectOperatingPlatform/payInformitionUpdate.jsp");	
			map81.put("attributes", map811);
			list81.add(map81);
			

			
			Map<String,Object> map82 = new HashMap<String,Object>();
			map82.put("id", "182");
			map82.put("text", "支付记录审核");
			Map<String,Object> map812 = new HashMap<String,Object>();
			map812.put("href", "../views/configuration/userManagement.jsp");	
			map82.put("attributes", map812);
			list81.add(map82);
			

			Map<String,Object> map83 = new HashMap<String,Object>();
			map83.put("id", "183");
			map83.put("text", "支付记录复核");
			Map<String,Object> map813 = new HashMap<String,Object>();
			map813.put("href", "../views/configuration/userManagement.jsp");	
			map83.put("attributes", map813);
			list81.add(map83);
			

			Map<String,Object> map84 = new HashMap<String,Object>();
			map84.put("id", "184");
			map84.put("text", "支付记录查询");
			Map<String,Object> map814 = new HashMap<String,Object>();
			map814.put("href", "../views/configuration/userManagement.jsp");	
			map84.put("attributes", map814);
			list81.add(map84);

			Map<String,Object> map85 = new HashMap<String,Object>();
			map85.put("id", "185");
			map85.put("text", "支付记录指令同步");
			Map<String,Object> map815 = new HashMap<String,Object>();
			map815.put("href", "../views/configuration/userManagement.jsp");	
			map85.put("attributes", map815);
			list81.add(map85);

			Map<String,Object> map86 = new HashMap<String,Object>();
			map86.put("id", "186");
			map86.put("text", "支付指令处理查询");
			Map<String,Object> map816 = new HashMap<String,Object>();
			map816.put("href", "../views/configuration/userManagement.jsp");	
			map86.put("attributes", map816);
			list81.add(map86);
			

			Map<String,Object> map87 = new HashMap<String,Object>();
			map87.put("id", "187");
			map87.put("text", "银行账户余额查询");
			Map<String,Object> map817 = new HashMap<String,Object>();
			map817.put("href", "../views/configuration/userManagement.jsp");	
			map87.put("attributes", map817);
			list81.add(map87);
			
			Map<String,Object> map88 = new HashMap<String,Object>();
			map88.put("id", "188");
			map88.put("text", "银行账户交易查询");
			Map<String,Object> map818 = new HashMap<String,Object>();
			map818.put("href", "../views/configuration/userManagement.jsp");	
			map88.put("attributes", map818);
			list81.add(map88);
			
			map8.put("children", list81);
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
