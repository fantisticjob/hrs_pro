package com.hausontech.hrs.web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hausontech.hrs.api.constants.Constants;
import com.hausontech.hrs.api.dimensionManager.service.IRptGeneratorConfigService;
import com.hausontech.hrs.api.reportSetting.service.IRptSettingService;
import com.hausontech.hrs.bean.AuditBean;
import com.hausontech.hrs.bean.dimensionManager.DimensionBean;
import com.hausontech.hrs.bean.dimensionManager.DimensionValueBean;
import com.hausontech.hrs.bean.reportSetting.ContentLowHighBean;
import com.hausontech.hrs.bean.reportSetting.DimValueNamePair;
import com.hausontech.hrs.bean.reportSetting.DimensionValueSetBean;
import com.hausontech.hrs.bean.reportSetting.ItemCalBean;
import com.hausontech.hrs.bean.reportSetting.ItemCodeExtHeaderBean;
import com.hausontech.hrs.bean.reportSetting.ItemCodeExtLineBean;
import com.hausontech.hrs.bean.reportSetting.ItemContentBean;
import com.hausontech.hrs.bean.reportSetting.ItemGroupRuleHeaderBean;
import com.hausontech.hrs.bean.reportSetting.ItemGroupRuleLineBean;
import com.hausontech.hrs.bean.reportSetting.ItemHeaderBean;
import com.hausontech.hrs.bean.reportSetting.ItemLookUpHeaderBean;
import com.hausontech.hrs.bean.reportSetting.ItemLookUpLineBean;
import com.hausontech.hrs.bean.reportSetting.ItemRowCalculationBean;
import com.hausontech.hrs.bean.reportSetting.ItemRowSetHeaderBean;
import com.hausontech.hrs.bean.reportSetting.ItemRowSetLineBean;
import com.hausontech.hrs.exceptions.DataReferenceException;
import com.hausontech.hrs.utils.Comp;
import com.hausontech.hrs.utils.JsonUtil;
import com.hausontech.hrs.utils.Loap;
import com.hausontech.hrs.utils.Tan;
import com.hausontech.hrs.utils.ThirdLevelType;

import net.sf.json.JSONArray;

@Controller
@RequestMapping(value = "/rptSettings")
public class ReportSettingController extends BaseController {
	/** 组件日志 */ 
	private static Logger logger = LoggerFactory.getLogger(ReportSettingController.class);  
	
	@Resource
	private IRptSettingService rptSettingService;
	
	private final String SESSION_KEY_RULE_HEADER = "rule_header_key";
	private final String SESSION_KEY_ITEM_HEADER = "item_header_key";
	private final String SESSION_KEY_ITEM_CODE = "item_code_key";
	
	private final String SESSION_KEY_CODE_HEADER ="code_header_key";
	
	private final String SESSION_KEY_LOOKUP_HEADER="lookup_header_key";
	private final String SESSION_KEY_ROWSET_HEADER = "rowset_header_key";
	
	private final String SESSION_KEY_ROWCAL_HEADER="rowsetcal_header_key";
	
	public IRptSettingService getRptSettingService() {
		return rptSettingService;
	}

	public void setRptSettingService(IRptSettingService rptSettingService) {
		this.rptSettingService = rptSettingService;
	}
	
	@Resource
	private IRptGeneratorConfigService rptGenConfigService;
	
	public IRptGeneratorConfigService getRptGenConfigService() {
		return rptGenConfigService;
	}

	public void setRptGenConfigService(IRptGeneratorConfigService rptGenConfigService) {
		this.rptGenConfigService = rptGenConfigService;
	}
	
	/*Loop group Header definition related methods ---------Start*/
	@RequestMapping(value = "/getItemLoopRuleList")
	@ResponseBody
	public Object getItemLoopRuleList(HttpServletRequest request,HttpServletResponse response) {
		// 参数
		ItemGroupRuleHeaderBean ruleHeaderBean = new ItemGroupRuleHeaderBean();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			String target = this.getDecodedRequestParam(request, "target");
			if (StringUtils.isBlank(target) || !"rowSetConf".equals(target)) {
	            //handle the pagination case, initialize page related information
				this.initilizePagingInfo(request);
				//set query start index
				ruleHeaderBean.setRowStartIndex(this.queryRowStartIndex);
				//set row count
				ruleHeaderBean.setRowCount(this.rows);
			}
			ruleHeaderBean.setRuleCode(this.getDecodedRequestParam(request, "ruleCode"));
			ruleHeaderBean.setDescription(this.getDecodedRequestParam(request, "ruleName"));		
		} catch (UnsupportedEncodingException e1) {
			logger.error("getItemLoopRuleList():" + e1.toString());
		}
		List<Map<String, Object>> list = null;
		try {
			list = rptSettingService.getRuleHeaderList(ruleHeaderBean);
		} catch (Exception e) {
			logger.error("getItemLoopRuleList():" + e.toString());
		}
		JSONArray jsonlist = JsonUtil.fromObject(list);
		//put rows into map
		dataMap.put("rows", jsonlist);
		int count = 0;
		try {
			count = rptSettingService.getCountByItemGroupRuleHeaderBean(ruleHeaderBean);
		} catch (Exception e) {
			logger.error("getItemLoopRuleList():" + e.toString());
		}
		//put total row number in the map
		dataMap.put("total", count);	
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
		return JsonUtil.fromObject(dataMap);
	}
	
	@RequestMapping(value = "/saveLoopRuleHeader")
	@ResponseBody
	public Object saveLoopRuleHeader(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		//get dimension bean from request
		ItemGroupRuleHeaderBean reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			reqBean = this.constructRuleHeaderBean(request);
			if (!this.isRuleInputValid(reqBean, true)) {
				isError = true;
				errorMsg = "输入信息有误，请检查数据格式，重新保存！";
			} else {
				String userId = (String) session.getAttribute(Constants.USER_KEY);
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());
				//save dimension into database
				int newKey = 0;
				try {
					newKey = rptSettingService.createNewRuleHeader(reqBean);
				} catch (DuplicateKeyException de) {
					logger.error("saveLoopRuleHeader():" + de);
					errorMsg = "不能存在重复的循环组编码，请重新输入再次提交";
				} catch (Exception e) {
					logger.error("saveLoopRuleHeader():" + e);
				}
				if (newKey == 0) {
					isError = true;
				}
				reqBean.setRuleHeaderId(newKey);
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		}
		
		if (isError) {
			dataMap.put("isError", true);
			dataMap.put("msg", errorMsg);
		} else {
			constructReturnMap(reqBean, true);	
		}}else{
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
		return JsonUtil.fromObject(dataMap);
	}
		
	@RequestMapping(value = "/updateLoopRuleHeader")
	@ResponseBody
	public Object updateLoopRuleHeader(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		//get dimension bean from request
		ItemGroupRuleHeaderBean reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			reqBean = this.constructRuleHeaderBean(request);
			if (!this.isRuleInputValid(reqBean, false)) {
				isError = true;
				errorMsg = "输入信息有误，请检查数据格式，重新保存！";
			} else {
				String userId = (String) session.getAttribute(Constants.USER_KEY);
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());
				//save dimension into database
				int updateCount = 0;
				try {
					updateCount = rptSettingService.updateRuleHeader(reqBean);
				}  catch (DuplicateKeyException de) {
					logger.error("updateLoopRuleHeader():" + de);
					errorMsg = "不能存在重复的循环组编码，请重新输入再次提交";
				} catch (Exception e) {
					logger.error("updateLoopRuleHeader():" + e);
				}
				if (updateCount == 0) {
					isError = true;
				}
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		}
		
		if (isError) {
			dataMap.put("isError", true);
			dataMap.put("msg", errorMsg);
		} else {
			constructReturnMap(reqBean, false);	
		}}else{
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
		return JsonUtil.fromObject(dataMap);
	}
	
	@RequestMapping(value = "/deleteLoopRuleHeader")
	@ResponseBody
	public Object deleteLoopRuleHeader(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		//get dimension bean from request
		ItemGroupRuleHeaderBean reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			String ruleHeaderId = this.getDecodedRequestParam(request, "id");
			if (StringUtils.isBlank(ruleHeaderId)) {
				isError = true;
				errorMsg = "没有选中需要删除的记录，请选择后再删除！";
			} else {
				reqBean = new ItemGroupRuleHeaderBean();
				reqBean.setRuleHeaderId(Integer.parseInt(ruleHeaderId));
				String userId = (String) session.getAttribute(Constants.USER_KEY);
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());
				//save dimension into database
				int delCount = 0;
				try {
					delCount = rptSettingService.deleteRuleHeader(reqBean);
				} catch (DataReferenceException de) {
					// TODO Auto-generated catch block
					System.out.println("Reference record found while delete rule header: " + de);
					errorMsg = "报表表项引用循环组，请先删除或更新使用该循环组的表项，再删除循环组！";
					isError = true;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("Errors while delete rule header: " + e);
				}
				if (delCount == 0) {
					isError = true;
				}
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		} catch (Exception e) {
			isError = true;
		}
		
		if (isError) {
			dataMap.put("isError", true);
			dataMap.put("msg", errorMsg);
		} else {
			constructReturnMap(reqBean, false);	
		}}else{
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
		return JsonUtil.fromObject(dataMap);
	}
	/*Loop group definition related methods ---------End*/
	
	/*Rule Line related actions -----------Start*/
	@RequestMapping(value = "/showLoopDimensionView")
	public String showLoopDimensionView(HttpServletRequest request,HttpServletResponse response, Model model) {
		//get demensionValueId
		String ruleHeaderId = null;
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			ruleHeaderId = this.getDecodedRequestParam(request, "loopRuleId");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (!StringUtils.isBlank(ruleHeaderId)) {
			ItemGroupRuleHeaderBean parentBean = null;
			try {
				parentBean = rptSettingService.getRuleHeaderByByPrimaryKey(Integer.parseInt(ruleHeaderId));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (parentBean != null) {
				model.addAttribute("ruleHeaderBean", parentBean);
			}			
		}}else{
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
		return "configuration/loopDimensionConfig";
	}
	
	@RequestMapping(value = "/getRuleDeminsionList")
	@ResponseBody
	public Object getRuleDemionList(HttpServletRequest request,HttpServletResponse response) {
		DimensionBean dimBean = new DimensionBean();
		List<Map<String, Object>> list = null;
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			list = rptGenConfigService.getByCondition(dimBean);
		} catch (Exception e) {
			System.out.println("Error while get dimension:" + e);
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
		return JsonUtil.fromObject(list);
	}
	
	@RequestMapping(value = "/getRuleLineList")
	@ResponseBody
	public Object getRuleLineList(HttpServletRequest request,HttpServletResponse response, HttpSession httpSession) {
		// parameter
		ItemGroupRuleLineBean ruleLineBean = new ItemGroupRuleLineBean(); 
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			   //handle the pagination case, initialize page related information
						this.initilizePagingInfo(request);
						//set query start index
						ruleLineBean.setRowStartIndex(this.queryRowStartIndex);
						//set row count
						ruleLineBean.setRowCount(this.rows);
						
			
			
			ruleLineBean.setDimSegment(this.getDecodedRequestParam(request, "dimSegment"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		String ruleHeaderId = null;
		try {
			ruleHeaderId = this.getDecodedRequestParam(request, "ruleHeaderId");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (!StringUtils.isBlank(ruleHeaderId)) {
			ruleLineBean.setRuleHeaderId(Integer.parseInt(ruleHeaderId));
			httpSession.removeAttribute(SESSION_KEY_RULE_HEADER);
			httpSession.setAttribute(SESSION_KEY_RULE_HEADER, ruleHeaderId);
		}
		List<Map<String, Object>> list = null;
		try {
			list = rptSettingService.getRuleLineList(ruleLineBean);
		} catch (Exception e) {
			System.out.println("Error while get dimension:" + e);
		}
		int count = 0;
		try {
			count = rptSettingService.getCountByItemGroupRuleLineBean(ruleLineBean);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error while get dimension count:" + e);
		}
		
		JSONArray jsonlist = JsonUtil.fromObject(list);
		//set rows in the map
		dataMap.put("rows", jsonlist);
		//set total in the map
		dataMap.put("total", count);
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
		return JsonUtil.fromObject(dataMap);
	}
	
	@RequestMapping(value = "/saveRuleLine")
	@ResponseBody
	public Object saveRuleLineRecord(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		//get dimension bean from request
		ItemGroupRuleLineBean reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			reqBean = this.constructRuleLineBean(request, true);
			//try to get header it from session
			String sessionHeaderId = null;
			if (session.getAttribute(SESSION_KEY_RULE_HEADER) != null) {
				sessionHeaderId = (String)session.getAttribute(SESSION_KEY_RULE_HEADER);
			}
			if (!StringUtils.isBlank(sessionHeaderId)) {
				reqBean.setRuleHeaderId(Integer.parseInt(sessionHeaderId));
			}
			if (!this.isInputValid(reqBean, true)) {
				isError = true;
				errorMsg = "输入信息有误，请检查数据格式，重新保存！";
			} else {
				String userId = (String) session.getAttribute(Constants.USER_KEY);
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());
				//save dimension into database
				int newKey = 0;
				try {
					newKey = rptSettingService.createNewRuleLine(reqBean);
				} catch (DuplicateKeyException de) {
					logger.error("saveRuleLineRecord():" + de);
					errorMsg = "不能存在重复的维度，请重新输入再次提交";
				} catch (Exception e) {
					logger.error("saveRuleLineRecord():" + e);
				}
				if (newKey == 0) {
					isError = true;
				}
				reqBean.setRuleHeaderId(newKey);
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		}
		
		if (isError) {
			dataMap.put("isError", true);
			dataMap.put("msg", errorMsg);
		} else {
			//
			if (!StringUtils.isBlank(reqBean.getDimSegment())) {
				DimensionBean dimBean = null;
				try {
					dimBean = this.rptGenConfigService.getDimensionByDimSegment(reqBean.getDimSegment());
				} catch (Exception e) {
					logger.error("Erro while getting dimension name: " + e);
				}
				if (dimBean != null) {
					reqBean.setDimSegDescription(dimBean.getDimensionName());
				}				
			}
			constructReturnMap(reqBean);	
		}}else{
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
		return JsonUtil.fromObject(dataMap);
	}
		
	@RequestMapping(value = "/updateRuleLine")
	@ResponseBody
	public Object updateRuleLineRecord(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		//get dimension bean from request
		ItemGroupRuleLineBean reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			reqBean = this.constructRuleLineBean(request, false);
			if (!this.isInputValid(reqBean, false)) {
				isError = true;
				errorMsg = "输入信息有误，请检查数据格式，重新保存！";
			} else {
				String userId = (String) session.getAttribute(Constants.USER_KEY);
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());
				//save dimension into database
				int updateCount = 0;
				try {
					updateCount = rptSettingService.updateRuleLine(reqBean);
				} catch (DuplicateKeyException de) {
					logger.error("updateRuleLineRecord():" + de);
					errorMsg = "不能存在重复的维度，请重新输入再次提交";
				} catch (Exception e) {
					logger.error("updateRuleLineRecord():" + e);
				}
				if (updateCount == 0) {
					isError = true;
				}
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		}
		
		if (isError) {
			dataMap.put("isError", true);
			dataMap.put("msg", errorMsg);
		} else {
			if (!StringUtils.isBlank(reqBean.getDimSegment())) {
				DimensionBean dimBean = null;
				try {
					dimBean = this.rptGenConfigService.getDimensionByDimSegment(reqBean.getDimSegment());
				} catch (Exception e) {
					logger.error("Erro while getting dimension name: " + e);
				}
				if (dimBean != null) {
					reqBean.setDimSegDescription(dimBean.getDimensionName());
				}				
			}
			constructReturnMap(reqBean);	
		}}else{
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
		return JsonUtil.fromObject(dataMap);
	}
	
	@RequestMapping(value = "/deleteRuleLine")
	@ResponseBody
	public Object deleteRuleLineRecord(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		//get dimension bean from request
		ItemGroupRuleLineBean reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			String ruleLineId = this.getDecodedRequestParam(request, "id");
			if (StringUtils.isBlank(ruleLineId)) {
				isError = true;
				errorMsg = "没有选中需要删除的记录，请选择后再删除！";
			} else {
				reqBean = new ItemGroupRuleLineBean();
				reqBean.setRuleHeaderId(Integer.parseInt(ruleLineId));
				String userId = (String) session.getAttribute(Constants.USER_KEY);
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());
				//save dimension into database
				int delCount = 0;
				try {
					delCount = rptSettingService.deleteRuleLine(reqBean);
				} catch (DataReferenceException de) {
					// TODO Auto-generated catch block
					System.out.println("Reference record found while delete rule header: " + de);
					errorMsg = "报表表项引用循环组，请先删除或更新使用该循环组的表项，再删除循环组！";
					isError = true;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("Errors while delete rule header: " + e);
				}
				if (delCount == 0) {
					isError = true;
				}
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		} catch (Exception e) {
			isError = true;
		}
		
		if (isError) {
			dataMap.put("isError", true);
			dataMap.put("msg", errorMsg);
		} else {
			constructReturnMap(reqBean);	
		}}else{
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
		return JsonUtil.fromObject(dataMap);
	}
	/*Rule Line related actions -----------End*/
	
	
	/*Item Rule definition -----------Start  */
	@RequestMapping(value = "/getLoopGroupList")
	@ResponseBody
	public Object getLoopGroupList(HttpServletRequest request,HttpServletResponse response) {
		ItemGroupRuleHeaderBean ruleHeader = new ItemGroupRuleHeaderBean();
		List<Map<String, Object>> list = null;
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			list = rptSettingService.getRuleHeaderList(ruleHeader);
		} catch (Exception e) {
			System.out.println("Error while get dimension:" + e);
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
		return JsonUtil.fromObject(list);
	}
	
	@RequestMapping(value = "/getItemRuleList")
	@ResponseBody
	public Object getItemRuleList(HttpServletRequest request,HttpServletResponse response) {
		// 参数
		ItemHeaderBean itemRuleBean = new ItemHeaderBean();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
            //handle the pagination case, initialize page related information
			this.initilizePagingInfo(request);
			//set query start index
			itemRuleBean.setRowStartIndex(this.queryRowStartIndex);
			//set row count
			itemRuleBean.setRowCount(this.rows);
			//itemRuleBean.setRuleCode(this.getDecodedRequestParam(request, "ruleCode"));
			//get rule header record by description
			String ruleDescription=  (this.getDecodedRequestParam(request, "ruleCode"));
			String ruleCode = null;
			if (!StringUtils.isBlank(ruleDescription)) {
				try {
					ruleCode = rptSettingService.getLoopUpByRuleCode(ruleDescription);
				} catch (Exception e) {
					logger.error("getItemRuleList():" + e);
				}
			}
			itemRuleBean.setRuleCode(ruleCode);
			itemRuleBean.setItemCode(this.getDecodedRequestParam(request, "itemCode"));
			itemRuleBean.setItemDescription(this.getDecodedRequestParam(request, "itemName"));
		} catch (UnsupportedEncodingException ue) {
			logger.error("getItemRuleList():" + ue);
		}
		
		List<Map<String, Object>> list = null;
		try {
			list = rptSettingService.getItmeHeaderList(itemRuleBean);
		} catch (Exception e) {
			logger.error("getItemRuleList():" + e);
		}
		JSONArray jsonlist = JsonUtil.fromObject(list);
		//put rows into map
		dataMap.put("rows", jsonlist);
		int count = 0;
		try {
			count = rptSettingService.getCountByItemHeaderBean(itemRuleBean);
		} catch (Exception e) {
			logger.error("getItemRuleList():" + e);
		}
		//put total row number in the map
		dataMap.put("total", count);
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
		return JsonUtil.fromObject(dataMap);
	}
	
	@RequestMapping(value = "/saveItemHeader")
	@ResponseBody
	public Object saveItemHeader(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		//get dimension bean from request
		ItemHeaderBean reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			reqBean = this.constructItemHeaderBean(request);
			if (!this.isInputValid(reqBean, true)) {
				isError = true;
				errorMsg = "输入信息有误，请检查数据格式，重新保存！";
			} else {
				String userId = (String) session.getAttribute(Constants.USER_KEY);
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				//set start date
				reqBean.setStartDate(new Date());
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());
				//save dimension into database
				long newKey = 0;
				try {
					newKey = rptSettingService.createNewItemHeader(reqBean);
				} catch (DuplicateKeyException de) {
					logger.error("saveItemHeader():" + de);
					errorMsg = "不能存在重复的表项代码，请重新输入再次提交";
				} catch (Exception e) {
					logger.error("saveItemHeader():" + e);
				}
				if (newKey == 0) {
					isError = true;
				}
				reqBean.setItemHeaderId(newKey);
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		}
		if (isError) {
			dataMap.put("isError", true);
			dataMap.put("msg", errorMsg);
		} else {
			constructReturnMap(reqBean, true);	
		}}else{
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
		return JsonUtil.fromObject(dataMap);
	}
		
	@RequestMapping(value = "/updateItemHeader")
	@ResponseBody
	public Object updateItemHeader(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		//get dimension bean from request
		ItemHeaderBean reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			reqBean = this.constructItemHeaderBean(request);
			if (!this.isInputValid(reqBean, false)) {
				isError = true;
				errorMsg = "输入信息有误，请检查数据格式，重新保存！";
			} else {
				String userId = (String) session.getAttribute(Constants.USER_KEY);
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());
				//save dimension into database
				int updateCount = 0;
				try {
					updateCount = rptSettingService.updateItemHeader(reqBean);
				} catch (DuplicateKeyException de) {
					logger.error("updateItemHeader():" + de);
					errorMsg = "不能存在重复的表项代码，请重新输入再次提交";
				} catch (Exception e) {
					logger.error("updateItemHeader():" + e);
				}
				if (updateCount == 0) {
					isError = true;
				}
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		}
		
		if (isError) {
			dataMap.put("isError", true);
			dataMap.put("msg", errorMsg);
		} else {
			constructReturnMap(reqBean, false);	
		}}else{
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
		return JsonUtil.fromObject(dataMap);
	}
	
	@RequestMapping(value = "/deleteItmeHeader")
	@ResponseBody
	public Object deleteItmeHeader(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		//get dimension bean from request
		ItemHeaderBean reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			String itemHeaderId = this.getDecodedRequestParam(request, "id");
			if (StringUtils.isBlank(itemHeaderId)) {
				isError = true;
				errorMsg = "没有选中需要删除的记录，请选择后再删除！";
			} else {
				reqBean = new ItemHeaderBean();
				reqBean.setItemHeaderId(Integer.parseInt(itemHeaderId));
				String userId = (String) session.getAttribute(Constants.USER_KEY);
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());
				//save dimension into database
				int delCount = 0;
				try {
					delCount = rptSettingService.deleteItemHeader(reqBean);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("Errors while delete rule header: " + e);
				}
				if (delCount == 0) {
					isError = true;
				}
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		} catch (Exception e) {
			isError = true;
		}
		
		if (isError) {
			dataMap.put("isError", true);
			dataMap.put("msg", errorMsg);
		} else {
			constructReturnMap(reqBean, false);	
		}}else{
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
		return JsonUtil.fromObject(dataMap);
	}
	
	/*Item Rule definition ---------------------END*/
	
	/*Item Content Header -------------------Start*/
	
	@RequestMapping(value = "/showItemContentView")
	public String showItemContentView(HttpServletRequest request,HttpServletResponse response
, Model model) {
		//get demensionValueId
		String itemHeaderId = null;
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			itemHeaderId = this.getDecodedRequestParam(request, "itemHeaderId");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (!StringUtils.isBlank(itemHeaderId)) {
			ItemHeaderBean parentBean = null;
			try {
				parentBean = rptSettingService.getItemHeaderByByPrimaryKey(Long.parseLong(itemHeaderId));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (parentBean != null) {
				model.addAttribute("itemHeaderBean", parentBean);
			}			
		}}else{
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
		return "configuration/itemContentConfiguration";
	}
	
	@RequestMapping(value = "/getItemContentConfigList")
	@ResponseBody
	public Object getItemContentConfigList(HttpServletRequest request,HttpServletResponse response
, HttpSession httpSession) {
		// parameter
		ItemContentBean contentBean = new ItemContentBean(); 
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			
            //handle the pagination case, initialize page related information
			this.initilizePagingInfo(request);
			//set query start index
			contentBean.setRowStartIndex(this.queryRowStartIndex);
			//set row count
			contentBean.setRowCount(this.rows);
			
			
			String itemHeaderId = this.getDecodedRequestParam(request, "itemHeaderId");
			if (!StringUtils.isBlank(itemHeaderId)) {
				contentBean.setItemHeaderId(Long.parseLong(itemHeaderId));
				httpSession.removeAttribute(SESSION_KEY_ITEM_HEADER);
				httpSession.setAttribute(SESSION_KEY_ITEM_HEADER, itemHeaderId);
			}
			String itemCode = this.getDecodedRequestParam(request, "itemCode");
			if (!StringUtils.isBlank(itemCode)) {
				contentBean.setItemCode(itemCode);
				httpSession.removeAttribute(SESSION_KEY_ITEM_CODE);
				httpSession.setAttribute(SESSION_KEY_ITEM_CODE, itemCode);
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		List<Map<String, Object>> list = null;
		try {
			list = rptSettingService.getItemContentList(contentBean);
		} catch (Exception e) {
			System.out.println("Error while get dimension:" + e);
		}
		int count = 0;
		try {
			count = rptSettingService.getCountByItemContentBean(contentBean);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error while get dimension count:" + e);
		}
		
		JSONArray jsonlist = JsonUtil.fromObject(list);
		//set rows in the map
		dataMap.put("rows", jsonlist);
		//set total in the map
		dataMap.put("total", count);
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
		return JsonUtil.fromObject(dataMap);
	}
	
	@RequestMapping(value = "/saveContentHeader")
	@ResponseBody
	public Object saveContentHeader(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		//get dimension bean from request
		ItemContentBean reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();

		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			reqBean = this.constructItemContentBean(request, true);
			
			//get item header id from session
			String sessionItemHeaderId = null;
			String sessionItemCode = null;
			
			//try to get item header id from session
			if (session.getAttribute(SESSION_KEY_ITEM_HEADER) != null) {
				sessionItemHeaderId = (String)session.getAttribute(SESSION_KEY_ITEM_HEADER);
			}
			if (!StringUtils.isBlank(sessionItemHeaderId)) {
				reqBean.setItemHeaderId(Long.parseLong(sessionItemHeaderId));
			}
			
			//try to get item code from session
			if (session.getAttribute(SESSION_KEY_ITEM_CODE) != null) {
				sessionItemCode = (String)session.getAttribute(SESSION_KEY_ITEM_CODE);
			}
			reqBean.setItemCode(sessionItemCode);
			
			if (!this.isInputValid(reqBean, true)) {
				isError = true;
				errorMsg = "输入信息有误，请检查数据格式，重新保存！";
			} else {
				String userId = (String) session.getAttribute(Constants.USER_KEY);
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());
				//save dimension into database
				int newKey = 0;
				try {
					newKey = rptSettingService.createItemContentHeader(reqBean);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("Errors while save dimension: " + e);
				}
				if (newKey == 0) {
					isError = true;
				}
				reqBean.setItemContentId(newKey);
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		}
		
		if (isError) {
			dataMap.put("isError", true);
			dataMap.put("msg", errorMsg);
		} else {
			constructReturnMap(reqBean, true);	
		}}else{
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
		return JsonUtil.fromObject(dataMap);
	}
	
	@RequestMapping(value = "/updateContentHeader")
	@ResponseBody
	public Object updateContentHeader(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		//get dimension bean from request
		ItemContentBean reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			reqBean = this.constructItemContentBean(request, false);
			if (!this.isInputValid(reqBean, false)) {
				isError = true;
				errorMsg = "输入信息有误，请检查数据格式，重新保存！";
			} else {
				String userId = (String) session.getAttribute(Constants.USER_KEY);
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());
				//save dimension into database
				int updateCount = 0;
				try {
					updateCount = rptSettingService.updateItemContenHeader(reqBean);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("Errors while update content header: " + e);
				}
				if (updateCount == 0) {
					isError = true;
				}
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		}
		
		if (isError) {
			dataMap.put("isError", true);
			dataMap.put("msg", errorMsg);
		} else {
			constructReturnMap(reqBean, false);	
		}}else{
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
		return JsonUtil.fromObject(dataMap);
	}
	
	@RequestMapping(value = "/deleteContentHeader")
	@ResponseBody
	public Object deleteContentHeader(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		//get dimension bean from request
		ItemContentBean reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			String ruleLineId = this.getDecodedRequestParam(request, "id");
			if (StringUtils.isBlank(ruleLineId)) {
				isError = true;
				errorMsg = "没有选中需要删除的记录，请选择后再删除！";
			} else {
				reqBean = new ItemContentBean();
				reqBean.setItemContentId(Integer.parseInt(ruleLineId));
				String userId = (String) session.getAttribute(Constants.USER_KEY);
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());
				//save dimension into database
				int delCount = 0;
				try {
					delCount = rptSettingService.deleteItemContentHeader(reqBean);
				} catch (DataReferenceException de) {
					// TODO Auto-generated catch block
					System.out.println("Reference record found while delete rule header: " + de);
					errorMsg = "报表表项引用循环组，请先删除或更新使用该循环组的表项，再删除循环组！";
					isError = true;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("Errors while delete rule header: " + e);
				}
				if (delCount == 0) {
					isError = true;
				}
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		} catch (Exception e) {
			isError = true;
		}
		
		if (isError) {
			dataMap.put("isError", true);
			dataMap.put("msg", errorMsg);
		} else {
			constructReturnMap(reqBean, false);	
		}}else{
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
		return JsonUtil.fromObject(dataMap);
	}
	
	@RequestMapping(value = "/showContentLowHighView")
	public String showContentLowHighView(HttpServletRequest request,HttpServletResponse response, Model model) {
		//get itemContentId
		ContentLowHighBean contentLowHighBean = new ContentLowHighBean();
		String itemContentId = null;
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			itemContentId = this.getDecodedRequestParam(request, "itemContentId");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (!StringUtils.isBlank(itemContentId)) {
			contentLowHighBean.setItemContentId(Integer.parseInt(itemContentId));
			//try to get content for itemContentId
			//List<DimensionValueSetBean> dimValueSetList = null;
			List<DimensionBean>  configurableDimList = null;
			ItemContentBean contentBean = null;
			try {
				contentBean = rptSettingService.getItemContentByByPrimaryKey(Integer.parseInt(itemContentId));
				if (contentBean != null) {
					//set already defined low high value
					if (!StringUtils.isBlank(contentBean.getSegment1Low())) {
						contentLowHighBean.setSegment1LowValue(contentBean.getSegment1Low());
					} 					
					if (!StringUtils.isBlank(contentBean.getSegment1Hign())) {
						contentLowHighBean.setSegment1HighValue(contentBean.getSegment1Hign());						
					}
					if (!StringUtils.isBlank(contentBean.getSegment2Low())) {
						contentLowHighBean.setSegment2LowValue(contentBean.getSegment2Low());
					}
                    if (!StringUtils.isBlank(contentBean.getSegment2Hign())) {
						contentLowHighBean.setSegment2HighValue(contentBean.getSegment2Hign());
					} 
                    if (!StringUtils.isBlank(contentBean.getSegment3Low())) {
						contentLowHighBean.setSegment3LowValue(contentBean.getSegment3Low());
					} 
                    if (!StringUtils.isBlank(contentBean.getSegment3Hign())) {
						contentLowHighBean.setSegment3HighValue(contentBean.getSegment3Hign());
					}
                    if (!StringUtils.isBlank(contentBean.getSegment4Low())) {
						contentLowHighBean.setSegment4LowValue(contentBean.getSegment4Low());
					} 
                    if (!StringUtils.isBlank(contentBean.getSegment4Hign())) {
						contentLowHighBean.setSegment4HighValue(contentBean.getSegment4Hign());
					} 
                    if (!StringUtils.isBlank(contentBean.getSegment5Low())) {
						contentLowHighBean.setSegment5LowValue(contentBean.getSegment5Low());
					} 
                    if (!StringUtils.isBlank(contentBean.getSegment5Hign())) {
						contentLowHighBean.setSegment5HighValue(contentBean.getSegment5Hign());
					} 
                    if (!StringUtils.isBlank(contentBean.getSegment6Low())) {
						contentLowHighBean.setSegment6LowValue(contentBean.getSegment6Low());
					} 
					if (!StringUtils.isBlank(contentBean.getSegment6Hign())) {
						contentLowHighBean.setSegment6HighValue(contentBean.getSegment6Hign());
					} 
					if (!StringUtils.isBlank(contentBean.getSegment7Low())) {
						contentLowHighBean.setSegment7LowValue(contentBean.getSegment7Low());
					} 
					if (!StringUtils.isBlank(contentBean.getSegment7Hign())) {
						contentLowHighBean.setSegment7HighValue(contentBean.getSegment7Hign());
					} 
					if (!StringUtils.isBlank(contentBean.getSegment8Low())) {
						contentLowHighBean.setSegment8LowValue(contentBean.getSegment8Low());
					} 
					if (!StringUtils.isBlank(contentBean.getSegment8Hign())) {
						contentLowHighBean.setSegment8HighValue(contentBean.getSegment8Hign());
					} 
					if (!StringUtils.isBlank(contentBean.getSegment9Low())) {
						contentLowHighBean.setSegment9LowValue(contentBean.getSegment9Low());
					} 
					if (!StringUtils.isBlank(contentBean.getSegment9Hign())) {
						contentLowHighBean.setSegment9HighValue(contentBean.getSegment9Hign());
					} 
					if (!StringUtils.isBlank(contentBean.getSegment10Low())) {
						contentLowHighBean.setSegment10LowValue(contentBean.getSegment10Low());
					} 
					if (!StringUtils.isBlank(contentBean.getSegment10Hign())) {
						contentLowHighBean.setSegment10HighValue(contentBean.getSegment10Hign());
					} 
					if (!StringUtils.isBlank(contentBean.getSegment11Low())) {
						contentLowHighBean.setSegment11LowValue(contentBean.getSegment11Low());
					} 
					if (!StringUtils.isBlank(contentBean.getSegment11Hign())) {
						contentLowHighBean.setSegment11HighValue(contentBean.getSegment11Hign());
					} 
					if (!StringUtils.isBlank(contentBean.getSegment12Low())) {
						contentLowHighBean.setSegment12LowValue(contentBean.getSegment12Low());
					} 
					if (!StringUtils.isBlank(contentBean.getSegment12Hign())) {
						contentLowHighBean.setSegment12HighValue(contentBean.getSegment12Hign());
					} 
					if (!StringUtils.isBlank(contentBean.getSegment13Low())) {
						contentLowHighBean.setSegment13LowValue(contentBean.getSegment13Low());
					} 
					if (!StringUtils.isBlank(contentBean.getSegment13Hign())) {
						contentLowHighBean.setSegment13HighValue(contentBean.getSegment13Hign());
					} 
					if (!StringUtils.isBlank(contentBean.getSegment14Low())) {
						contentLowHighBean.setSegment14LowValue(contentBean.getSegment14Low());
					} 
					if (!StringUtils.isBlank(contentBean.getSegment14Hign())) {
						contentLowHighBean.setSegment14HighValue(contentBean.getSegment14Hign());
					} 
					if (!StringUtils.isBlank(contentBean.getSegment15Low())) {
						contentLowHighBean.setSegment15LowValue(contentBean.getSegment15Low());
					} 
					if (!StringUtils.isBlank(contentBean.getSegment15Hign())) {
						contentLowHighBean.setSegment15HighValue(contentBean.getSegment15Hign());
					} 
					if (!StringUtils.isBlank(contentBean.getSegment15Low())) {
						contentLowHighBean.setSegment15LowValue(contentBean.getSegment15Low());
					} 
					if (!StringUtils.isBlank(contentBean.getSegment16Low())) {
						contentLowHighBean.setSegment16LowValue(contentBean.getSegment16Low());
					} 
					if (!StringUtils.isBlank(contentBean.getSegment16Hign())) {
						contentLowHighBean.setSegment16HighValue(contentBean.getSegment16Hign());
					} 
					if (!StringUtils.isBlank(contentBean.getSegment17Low())) {
						contentLowHighBean.setSegment17LowValue(contentBean.getSegment17Low());
					} 
					if (!StringUtils.isBlank(contentBean.getSegment17Hign())) {
						contentLowHighBean.setSegment17HighValue(contentBean.getSegment17Hign());
					} 
					if (!StringUtils.isBlank(contentBean.getSegment18Low())) {
						contentLowHighBean.setSegment18LowValue(contentBean.getSegment18Low());
					} 
					if (!StringUtils.isBlank(contentBean.getSegment18Hign())) {
						contentLowHighBean.setSegment18HighValue(contentBean.getSegment18Hign());
					} 
					if (!StringUtils.isBlank(contentBean.getSegment19Low())) {
						contentLowHighBean.setSegment19LowValue(contentBean.getSegment19Low());
					} 
					if (!StringUtils.isBlank(contentBean.getSegment19Hign())) {
						contentLowHighBean.setSegment19HighValue(contentBean.getSegment19Hign());
					} 
					if (!StringUtils.isBlank(contentBean.getSegment20Low())) {
						contentLowHighBean.setSegment20LowValue(contentBean.getSegment20Low());
					} 
					if (!StringUtils.isBlank(contentBean.getSegment20Hign())) {
						contentLowHighBean.setSegment20HighValue(contentBean.getSegment20Hign());
					}
				}   
			    //get dimension list
				//dimValueSetList = rptSettingService.getDimensionValueSetList();
				configurableDimList = rptSettingService.getConfigurableLowHighDimList();
			} catch (Exception e) {
				logger.error("showContentLowHighView()" + e.toString());
			}
			//set segment name pair
			if (configurableDimList != null && 0 < configurableDimList.size()) {
				for (DimensionBean tmpDimBean : configurableDimList) {
					if (!StringUtils.isBlank(tmpDimBean.getDimSegment())) {
						if ("SEGMENT1".equals(tmpDimBean.getDimSegment())) {
							contentLowHighBean.setSegment1Pair(new DimValueNamePair(tmpDimBean.getDimensionId(),tmpDimBean.getDimensionCode(), tmpDimBean.getDimensionName()));
						} else if ("SEGMENT2".equals(tmpDimBean.getDimSegment())) {
							contentLowHighBean.setSegment2Pair(new DimValueNamePair(tmpDimBean.getDimensionId(),tmpDimBean.getDimensionCode(), tmpDimBean.getDimensionName()));
						} else if ("SEGMENT3".equals(tmpDimBean.getDimSegment())) {
							contentLowHighBean.setSegment3Pair(new DimValueNamePair(tmpDimBean.getDimensionId(),tmpDimBean.getDimensionCode(), tmpDimBean.getDimensionName()));
						} else if ("SEGMENT4".equals(tmpDimBean.getDimSegment())) {
							contentLowHighBean.setSegment4Pair(new DimValueNamePair(tmpDimBean.getDimensionId(),tmpDimBean.getDimensionCode(), tmpDimBean.getDimensionName()));
						} else if ("SEGMENT5".equals(tmpDimBean.getDimSegment())) {
							contentLowHighBean.setSegment5Pair(new DimValueNamePair(tmpDimBean.getDimensionId(),tmpDimBean.getDimensionCode(), tmpDimBean.getDimensionName()));
						} else if ("SEGMENT6".equals(tmpDimBean.getDimSegment())) {
							contentLowHighBean.setSegment6Pair(new DimValueNamePair(tmpDimBean.getDimensionId(),tmpDimBean.getDimensionCode(), tmpDimBean.getDimensionName()));
						} else if ("SEGMENT7".equals(tmpDimBean.getDimSegment())) {
							contentLowHighBean.setSegment7Pair(new DimValueNamePair(tmpDimBean.getDimensionId(),tmpDimBean.getDimensionCode(), tmpDimBean.getDimensionName()));
						} else if ("SEGMENT8".equals(tmpDimBean.getDimSegment())) {
							contentLowHighBean.setSegment8Pair(new DimValueNamePair(tmpDimBean.getDimensionId(),tmpDimBean.getDimensionCode(), tmpDimBean.getDimensionName()));
						} else if ("SEGMENT9".equals(tmpDimBean.getDimSegment())) {
							contentLowHighBean.setSegment9Pair(new DimValueNamePair(tmpDimBean.getDimensionId(),tmpDimBean.getDimensionCode(), tmpDimBean.getDimensionName()));
						} else if ("SEGMENT10".equals(tmpDimBean.getDimSegment())) {
							contentLowHighBean.setSegment10Pair(new DimValueNamePair(tmpDimBean.getDimensionId(),tmpDimBean.getDimensionCode(), tmpDimBean.getDimensionName()));
						} else if ("SEGMENT11".equals(tmpDimBean.getDimSegment())) {
							contentLowHighBean.setSegment11Pair(new DimValueNamePair(tmpDimBean.getDimensionId(),tmpDimBean.getDimensionCode(), tmpDimBean.getDimensionName()));
						} else if ("SEGMENT12".equals(tmpDimBean.getDimSegment())) {
							contentLowHighBean.setSegment12Pair(new DimValueNamePair(tmpDimBean.getDimensionId(),tmpDimBean.getDimensionCode(), tmpDimBean.getDimensionName()));
						} else if ("SEGMENT13".equals(tmpDimBean.getDimSegment())) {
							contentLowHighBean.setSegment13Pair(new DimValueNamePair(tmpDimBean.getDimensionId(),tmpDimBean.getDimensionCode(), tmpDimBean.getDimensionName()));
						} else if ("SEGMENT14".equals(tmpDimBean.getDimSegment())) {
							contentLowHighBean.setSegment14Pair(new DimValueNamePair(tmpDimBean.getDimensionId(),tmpDimBean.getDimensionCode(), tmpDimBean.getDimensionName()));
						} else if ("SEGMENT15".equals(tmpDimBean.getDimSegment())) {
							contentLowHighBean.setSegment15Pair(new DimValueNamePair(tmpDimBean.getDimensionId(),tmpDimBean.getDimensionCode(), tmpDimBean.getDimensionName()));
						} else if ("SEGMENT16".equals(tmpDimBean.getDimSegment())) {
							contentLowHighBean.setSegment16Pair(new DimValueNamePair(tmpDimBean.getDimensionId(),tmpDimBean.getDimensionCode(), tmpDimBean.getDimensionName()));
						} else if ("SEGMENT17".equals(tmpDimBean.getDimSegment())) {
							contentLowHighBean.setSegment17Pair(new DimValueNamePair(tmpDimBean.getDimensionId(),tmpDimBean.getDimensionCode(), tmpDimBean.getDimensionName()));
						} else if ("SEGMENT18".equals(tmpDimBean.getDimSegment())) {
							contentLowHighBean.setSegment18Pair(new DimValueNamePair(tmpDimBean.getDimensionId(),tmpDimBean.getDimensionCode(), tmpDimBean.getDimensionName()));
						} else if ("SEGMENT19".equals(tmpDimBean.getDimSegment())) {
							contentLowHighBean.setSegment19Pair(new DimValueNamePair(tmpDimBean.getDimensionId(),tmpDimBean.getDimensionCode(), tmpDimBean.getDimensionName()));
						} else if ("SEGMENT20".equals(tmpDimBean.getDimSegment())) {
							contentLowHighBean.setSegment20Pair(new DimValueNamePair(tmpDimBean.getDimensionId(),tmpDimBean.getDimensionCode(), tmpDimBean.getDimensionName()));
						} 
					}
				}		
			}
				
/*			List<DimensionValueSetBean> nameValPairs = null;
			if (dimValueSetList != null &&  0 < dimValueSetList.size()) {
				DimensionValueSetBean oriDimValeBean = null;			
				Iterator<DimensionValueSetBean> iter = dimValueSetList.iterator();
				while(iter.hasNext()) {
					DimensionValueSetBean tmpBean = iter.next();
					if (nameValPairs == null) {
						nameValPairs = new ArrayList<DimensionValueSetBean>();
					}
					tmpBean.setDispValueName(tmpBean.getDimValue() + "-" + tmpBean.getDimValDescription());
					if (oriDimValeBean == null) {
						nameValPairs.add(tmpBean);					
					} else {
						if (tmpBean.getDimensionId() == oriDimValeBean.getDimensionId()) {
							nameValPairs.add(tmpBean);
						} else {
							addDimensionValueList(contentLowHighBean, nameValPairs, oriDimValeBean);
							nameValPairs = new ArrayList<DimensionValueSetBean>();
							nameValPairs.add(tmpBean);
						}
					}
					oriDimValeBean = tmpBean;
				}
				
				if(nameValPairs != null && 0 < nameValPairs.size()) {
					addDimensionValueList(contentLowHighBean, nameValPairs, nameValPairs.get(0));
				}
			}*/
		}
		model.addAttribute("contentLowHighBean", contentLowHighBean);
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
		return "configuration/contentLowHigh";
	}
	
	@RequestMapping(value = "/getConfigurableDimValueList")
	@ResponseBody
	public Object getConfigurableDimValueList(HttpServletRequest request,HttpServletResponse response, HttpSession httpSession) {
		// 参数
		DimensionValueBean dimValueBean = new DimensionValueBean();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			
			//get search operation
			String operation = this.getDecodedRequestParam(request, "performOperation");
			if (!StringUtils.isBlank(operation) && operation.equals("comboSearch")) {
				String dimValCode = this.getDecodedRequestParam(request, "comboxDimValCode");
				if (!StringUtils.isBlank(dimValCode) && dimValCode.contains(Constants.PERCENT_TRANS_CHARACTER)) {
					dimValCode.replace(Constants.PERCENT_TRANS_CHARACTER, Constants.PERCENT_CHARACTER);
				}	
				dimValueBean.setDimensionValue(dimValCode);
			} else {
				String inputQuery = this.getDecodedRequestParam(request, "q");
				if (!StringUtils.isBlank(inputQuery)) {
					dimValueBean.setDimensionValue(Constants.PERCENT_CHARACTER + inputQuery.trim() + Constants.PERCENT_CHARACTER);
				}
			}
		    //handle the pagination case, initialize page related information
			this.initilizePagingInfo(request);
			//set query start index
			dimValueBean.setRowStartIndex(this.queryRowStartIndex);
			//set row count
			dimValueBean.setRowCount(this.rows);
			//get other parameter from request
			String dimensionId = this.getDecodedRequestParam(request, "dimensionId");
			if (!StringUtils.isBlank(dimensionId)) {
				dimValueBean.setDimensionId(Long.parseLong(dimensionId));
			}
		} catch (UnsupportedEncodingException e1) {
			logger.error("Errors while get dimension value list:" + e1);
		}
			
		List<Map<String, Object>> list = null;
		try {
			list = rptGenConfigService.getDimensionValueListByCondition(dimValueBean);
		} catch (Exception e) {
			logger.error("Errors while save dimension value list:" + e);
		}
		int count = 0;
		try {
			count = rptGenConfigService.getCountByCondition(dimValueBean);
		} catch (Exception e) {
			logger.error("Errors while save dimension value list count:" + e);
		}
		JSONArray jsonlist = JsonUtil.fromObject(list);
		
		
		dataMap.put("rows", jsonlist);
		dataMap.put("total", count);
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
        return JsonUtil.fromObject(dataMap);
	}
	
	@RequestMapping(value = "/submitContentLowHighValue")
	@ResponseBody
	public Object submitContentLowHighValue(HttpServletRequest request,HttpServletResponse response, @ModelAttribute ContentLowHighBean contentLowHighBean) {
		int result = 0;
		boolean isError = false;
		this.dataMap = new HashMap<String, Object>();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		if (contentLowHighBean != null) {
			ItemContentBean contentBean = new ItemContentBean();
			contentBean.setItemContentId(contentLowHighBean.getItemContentId());
			
			//get dimension list
			List<DimensionValueSetBean> dimValueSetList = null;
			try {
				dimValueSetList = rptSettingService.getDimensionValueSetList();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (!isLowHighValEmpty(contentLowHighBean.getSegment1LowValue()) ||
					!isLowHighValEmpty(contentLowHighBean.getSegment1HighValue())) {
				contentBean.setSegment1Low(preProcessLowHigh(contentLowHighBean.getSegment1LowValue()));
				contentBean.setSegment1Hign(preProcessLowHigh(contentLowHighBean.getSegment1HighValue()));
				contentBean.setSegment1Type(
						getSegmentType(contentBean.getSegment1Hign(), 
								       contentBean.getSegment1Low(), 
								       dimValueSetList));
			}

			if (!isLowHighValEmpty(contentLowHighBean.getSegment2LowValue()) ||
					!isLowHighValEmpty(contentLowHighBean.getSegment2HighValue())) {
				contentBean.setSegment2Low(preProcessLowHigh(contentLowHighBean.getSegment2LowValue()));
				contentBean.setSegment2Hign(preProcessLowHigh(contentLowHighBean.getSegment2HighValue()));
				contentBean.setSegment2Type(
						getSegmentType(contentBean.getSegment2Hign(), 
								contentBean.getSegment2Low(), 
								       dimValueSetList));
			}
			
			if (!isLowHighValEmpty(contentLowHighBean.getSegment3LowValue()) ||
					!isLowHighValEmpty(contentLowHighBean.getSegment3HighValue())) {
				contentBean.setSegment3Low(preProcessLowHigh(contentLowHighBean.getSegment3LowValue()));
				contentBean.setSegment3Hign(preProcessLowHigh(contentLowHighBean.getSegment3HighValue()));
				contentBean.setSegment3Type(
						getSegmentType(contentBean.getSegment3Hign(), 
								contentBean.getSegment3Low(), 
								       dimValueSetList));
			}
			if (!isLowHighValEmpty(contentLowHighBean.getSegment4LowValue()) ||
					!isLowHighValEmpty(contentLowHighBean.getSegment4HighValue())) {
				contentBean.setSegment4Low(preProcessLowHigh(contentLowHighBean.getSegment4LowValue()));
				contentBean.setSegment4Hign(preProcessLowHigh(contentLowHighBean.getSegment4HighValue()));
				contentBean.setSegment4Type(
						getSegmentType(contentBean.getSegment4Hign(), 
								contentBean.getSegment4Low(), 
								       dimValueSetList));
			}
			
			if (!isLowHighValEmpty(contentLowHighBean.getSegment5LowValue()) ||
					!isLowHighValEmpty(contentLowHighBean.getSegment5HighValue())) {
				contentBean.setSegment5Low(preProcessLowHigh(contentLowHighBean.getSegment5LowValue()));
				contentBean.setSegment5Hign(preProcessLowHigh(contentLowHighBean.getSegment5HighValue()));
				contentBean.setSegment5Type(
						getSegmentType(contentBean.getSegment5Hign(), 
								contentBean.getSegment5Low(), 
								       dimValueSetList));
			}
			if (!isLowHighValEmpty(contentLowHighBean.getSegment6LowValue()) ||
					!isLowHighValEmpty(contentLowHighBean.getSegment6HighValue())) {
				contentBean.setSegment6Low(preProcessLowHigh(contentLowHighBean.getSegment6LowValue()));
				contentBean.setSegment6Hign(preProcessLowHigh(contentLowHighBean.getSegment6HighValue()));
				contentBean.setSegment6Type(
						getSegmentType(contentBean.getSegment6Hign(), 
								contentBean.getSegment6Low(), 
								       dimValueSetList));
			}
			if (!isLowHighValEmpty(contentLowHighBean.getSegment7LowValue()) ||
					!isLowHighValEmpty(contentLowHighBean.getSegment7HighValue())) {
				contentBean.setSegment7Low(preProcessLowHigh(contentLowHighBean.getSegment7LowValue()));
				contentBean.setSegment7Hign(preProcessLowHigh(contentLowHighBean.getSegment7HighValue()));
				contentBean.setSegment7Type(
						getSegmentType(contentBean.getSegment7Hign(), 
								contentBean.getSegment7Low(), 
								       dimValueSetList));
			}
			if (!isLowHighValEmpty(contentLowHighBean.getSegment8LowValue()) ||
					!isLowHighValEmpty(contentLowHighBean.getSegment8HighValue())) {
				contentBean.setSegment8Low(preProcessLowHigh(contentLowHighBean.getSegment8LowValue()));
				contentBean.setSegment8Hign(preProcessLowHigh(contentLowHighBean.getSegment8HighValue()));
				contentBean.setSegment8Type(
						getSegmentType(contentBean.getSegment8Hign(), 
								contentBean.getSegment8Low(), 
								       dimValueSetList));
			}
			if (!isLowHighValEmpty(contentLowHighBean.getSegment9LowValue()) ||
					!isLowHighValEmpty(contentLowHighBean.getSegment9HighValue())) {
				contentBean.setSegment9Low(preProcessLowHigh(contentLowHighBean.getSegment9LowValue()));
				contentBean.setSegment9Hign(preProcessLowHigh(contentLowHighBean.getSegment9HighValue()));
				contentBean.setSegment9Type(
						getSegmentType(contentBean.getSegment9Hign(), 
								contentBean.getSegment9Low(), 
								       dimValueSetList));
			}
			if (!isLowHighValEmpty(contentLowHighBean.getSegment10LowValue()) ||
					!isLowHighValEmpty(contentLowHighBean.getSegment10HighValue())) {
				contentBean.setSegment10Low(preProcessLowHigh(contentLowHighBean.getSegment10LowValue()));
				contentBean.setSegment10Hign(preProcessLowHigh(contentLowHighBean.getSegment10HighValue()));
				contentBean.setSegment10Type(
						getSegmentType(contentBean.getSegment10Hign(), 
								contentBean.getSegment10Low(), 
								       dimValueSetList));
			}
			if (!isLowHighValEmpty(contentLowHighBean.getSegment11LowValue()) ||
					!isLowHighValEmpty(contentLowHighBean.getSegment11HighValue())) {
				contentBean.setSegment11Low(preProcessLowHigh(contentLowHighBean.getSegment11LowValue()));
				contentBean.setSegment11Hign(preProcessLowHigh(contentLowHighBean.getSegment11HighValue()));
				contentBean.setSegment11Type(
						getSegmentType(contentBean.getSegment11Hign(), 
								contentBean.getSegment11Low(), 
								       dimValueSetList));
			}
			if (!isLowHighValEmpty(contentLowHighBean.getSegment12LowValue()) ||
					!isLowHighValEmpty(contentLowHighBean.getSegment12HighValue())) {
				contentBean.setSegment12Low(preProcessLowHigh(contentLowHighBean.getSegment12LowValue()));
				contentBean.setSegment12Hign(preProcessLowHigh(contentLowHighBean.getSegment12HighValue()));
				contentBean.setSegment12Type(
						getSegmentType(contentBean.getSegment12Hign(), 
								contentBean.getSegment12Low(), 
								       dimValueSetList));
			}
			if (!isLowHighValEmpty(contentLowHighBean.getSegment13LowValue()) ||
					!isLowHighValEmpty(contentLowHighBean.getSegment13HighValue())) {
				contentBean.setSegment13Low(preProcessLowHigh(contentLowHighBean.getSegment13LowValue()));
				contentBean.setSegment13Hign(preProcessLowHigh(contentLowHighBean.getSegment13HighValue()));
				contentBean.setSegment13Type(
						getSegmentType(contentBean.getSegment13Hign(), 
								contentBean.getSegment13Low(), 
								       dimValueSetList));
			}
			if (!isLowHighValEmpty(contentLowHighBean.getSegment14LowValue()) ||
					!isLowHighValEmpty(contentLowHighBean.getSegment14HighValue())) {
				contentBean.setSegment14Low(preProcessLowHigh(contentLowHighBean.getSegment14LowValue()));
				contentBean.setSegment14Hign(preProcessLowHigh(contentLowHighBean.getSegment14HighValue()));
				contentBean.setSegment14Type(
						getSegmentType(contentBean.getSegment14Hign(), 
								contentBean.getSegment14Low(), 
								       dimValueSetList));
			}
			if (!isLowHighValEmpty(contentLowHighBean.getSegment15LowValue()) ||
					!isLowHighValEmpty(contentLowHighBean.getSegment15HighValue())) {
				contentBean.setSegment15Low(preProcessLowHigh(contentLowHighBean.getSegment15LowValue()));
				contentBean.setSegment15Hign(preProcessLowHigh(contentLowHighBean.getSegment15HighValue()));
				contentBean.setSegment15Type(
						getSegmentType(contentBean.getSegment15Hign(), 
								contentBean.getSegment15Low(), 
								       dimValueSetList));
			}
			if (!isLowHighValEmpty(contentLowHighBean.getSegment16LowValue()) ||
					!isLowHighValEmpty(contentLowHighBean.getSegment16HighValue())) {
				contentBean.setSegment16Low(preProcessLowHigh(contentLowHighBean.getSegment16LowValue()));
				contentBean.setSegment16Hign(preProcessLowHigh(contentLowHighBean.getSegment16HighValue()));
				contentBean.setSegment16Type(
						getSegmentType(contentBean.getSegment16Hign(), 
								contentBean.getSegment16Low(), 
								       dimValueSetList));
			}
			if (!isLowHighValEmpty(contentLowHighBean.getSegment17LowValue()) ||
					!isLowHighValEmpty(contentLowHighBean.getSegment17HighValue())) {
				contentBean.setSegment17Low(preProcessLowHigh(contentLowHighBean.getSegment17LowValue()));
				contentBean.setSegment17Hign(preProcessLowHigh(contentLowHighBean.getSegment17HighValue()));
				contentBean.setSegment17Type(
						getSegmentType(contentBean.getSegment17Hign(), 
								contentBean.getSegment17Low(), 
								       dimValueSetList));
			}
			if (!isLowHighValEmpty(contentLowHighBean.getSegment18LowValue()) ||
					!isLowHighValEmpty(contentLowHighBean.getSegment18HighValue())) {
				contentBean.setSegment18Low(preProcessLowHigh(contentLowHighBean.getSegment18LowValue()));
				contentBean.setSegment18Hign(preProcessLowHigh(contentLowHighBean.getSegment18HighValue()));
				contentBean.setSegment18Type(
						getSegmentType(contentBean.getSegment18Hign(), 
								contentBean.getSegment18Low(), 
								       dimValueSetList));
			}
			if (!isLowHighValEmpty(contentLowHighBean.getSegment19LowValue()) ||
					!isLowHighValEmpty(contentLowHighBean.getSegment19HighValue())) {
				contentBean.setSegment19Low(preProcessLowHigh(contentLowHighBean.getSegment19LowValue()));
				contentBean.setSegment19Hign(preProcessLowHigh(contentLowHighBean.getSegment19HighValue()));
				contentBean.setSegment19Type(
						getSegmentType(contentBean.getSegment19Hign(), 
								contentBean.getSegment19Low(), 
								       dimValueSetList));
			}
			if (!isLowHighValEmpty(contentLowHighBean.getSegment20LowValue()) ||
					!isLowHighValEmpty(contentLowHighBean.getSegment20HighValue())) {
				contentBean.setSegment20Low(preProcessLowHigh(contentLowHighBean.getSegment20LowValue()));
				contentBean.setSegment20Hign(preProcessLowHigh(contentLowHighBean.getSegment20HighValue()));
				contentBean.setSegment20Type(
						getSegmentType(contentBean.getSegment20Hign(), 
								contentBean.getSegment20Low(), 
								       dimValueSetList));
			}
			
			contentBean.setLastUpdatedBy("web");
			contentBean.setLastUpdateDate(new Date());
			
			try {
				result = rptSettingService.updateItemContenLowHigh(contentBean);
			} catch (Exception e) {
				isError = true;
			}
		}
		
		if (result == 0 || isError) {
			dataMap.put("success", false);
			dataMap.put("message", "更新账户失败");
		} else {
			dataMap.put("success", true);
			dataMap.put("message", "保存账户成功");
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
		return JsonUtil.fromObject(dataMap);
	}
	
	/*Item Content Header -------------------END*/
	
	
	/*Item Calculation Header -------------------Start*/
	
	@RequestMapping(value = "/showItemCalculationView")
	public String showItemCalculationView(HttpServletRequest request,HttpServletResponse response, Model model) {
		//get item header Id
		String itemHeaderId = null;
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			itemHeaderId = this.getDecodedRequestParam(request, "itemHeaderId");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (!StringUtils.isBlank(itemHeaderId)) {
			ItemHeaderBean parentBean = null;
			try {
				parentBean = rptSettingService.getItemHeaderByByPrimaryKey(Long.parseLong(itemHeaderId));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (parentBean != null) {
				model.addAttribute("itemHeaderBean", parentBean);
			}			
		}}else{
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
		return "configuration/itemCalConfiguration";
	}
	
	@RequestMapping(value = "/getItemCalConfigList")
	@ResponseBody
	public Object getItemCalConfigList(HttpServletRequest request,HttpServletResponse response, HttpSession httpSession) {
		// parameter
		ItemCalBean calBean = new ItemCalBean(); 
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
            //handle the pagination case, initialize page related information
			this.initilizePagingInfo(request);
			//set query start index
			calBean.setRowStartIndex(this.queryRowStartIndex);
			//set row count
			calBean.setRowCount(this.rows);
			//get item header id from reqesut
			String itemHeaderId = this.getDecodedRequestParam(request, "itemHeaderId");
			if (!StringUtils.isBlank(itemHeaderId)) {
				calBean.setItemHeaderId(Long.parseLong(itemHeaderId));
				httpSession.removeAttribute(SESSION_KEY_ITEM_HEADER);
				httpSession.setAttribute(SESSION_KEY_ITEM_HEADER, itemHeaderId);
			}
			//get item header code from request
			String itemCode = this.getDecodedRequestParam(request, "itemCode");
			if (!StringUtils.isBlank(itemCode)) {
				calBean.setItemCode(itemCode);
				httpSession.removeAttribute(SESSION_KEY_ITEM_CODE);
				httpSession.setAttribute(SESSION_KEY_ITEM_CODE, itemCode);
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		List<Map<String, Object>> list = null;
		try {
			list = rptSettingService.getItemCalculationList(calBean);
		} catch (Exception e) {
			System.out.println("Error while get dimension:" + e);
		}
		int count = 0;
		try {
			count = rptSettingService.getItemCalculationCount(calBean);
		} catch (Exception e) {
			System.out.println("Error while get dimension count:" + e);
		}
		
		JSONArray jsonlist = JsonUtil.fromObject(list);
		//set rows in the map
		dataMap.put("rows", jsonlist);
		//set total in the map
		dataMap.put("total", count);
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
		return JsonUtil.fromObject(dataMap);
	}
	
	@RequestMapping(value = "/saveItemCalculation")
	@ResponseBody
	public Object saveItemCalculation(HttpServletRequest request,HttpServletResponse response
, HttpSession session) {
		//get itme calculation bean
		ItemCalBean reqBean = new ItemCalBean();
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			//construct bean with request
			reqBean.constructBeanWithRequest(request, true);
			//get item header id from session
			String sessionItemHeaderId = null;
			String sessionItemCode = null;
			
			//try to get item header id from session
			if (session.getAttribute(SESSION_KEY_ITEM_HEADER) != null) {
				sessionItemHeaderId = (String)session.getAttribute(SESSION_KEY_ITEM_HEADER);
			}
			if (!StringUtils.isBlank(sessionItemHeaderId)) {
				reqBean.setItemHeaderId(Long.parseLong(sessionItemHeaderId));
			}			
			//try to get item code from session
			if (session.getAttribute(SESSION_KEY_ITEM_CODE) != null) {
				sessionItemCode = (String)session.getAttribute(SESSION_KEY_ITEM_CODE);
			}
			reqBean.setItemCode(sessionItemCode);
			//check input whether valide
			int validationCode = reqBean.inputValidate(true);
			if (validationCode != 0) {
				isError = true;
				if (validationCode == -1) {
					errorMsg = "计算配置所关联表项的唯一标识符没有找到";
				} else if (validationCode == -2) {
					errorMsg = "没有找到计算配置所关联的表项";
				} else if (validationCode == -3) {
					errorMsg = "缺少必填数据，请检查输入后重新提交";
				} else if (validationCode == -4) {
					errorMsg = "计算用表项与常数不能同时存在，计算用表项只支持加减操作，常数只支持乘除操作";
				}
			} else {
				String userId = (String) session.getAttribute(Constants.USER_KEY);
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());
				//save dimension into database
				int newKey = 0;
				try {
					newKey = rptSettingService.createItemCalculation(reqBean);
				} catch (Exception e) {
					logger.error("Errors while save item calculation", e);
				}
				if (newKey == 0) {
					isError = true;
				}
				reqBean.setItemCalId(newKey);
			}
			if (!isError) {
				dataMap = reqBean.constructReturnMap(true);	
			}		
		} catch (UnsupportedEncodingException ue) {
			isError = true;
			logger.error("Errors while save item calculation", ue);
		} catch (Exception e) {
			isError = true;
			logger.error("Errors while save item calculation", e);
		}
		if (isError) {
			dataMap.put("isError", true);
			dataMap.put("msg", errorMsg);
		}}else{
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
		return JsonUtil.fromObject(dataMap);
	}
	
	
	@RequestMapping(value = "/updateItemCalculation")
	@ResponseBody
	public Object updateItemCalculation(HttpServletRequest request,HttpServletResponse response
, HttpSession session) {
		//get itme calculation bean
		ItemCalBean reqBean = new ItemCalBean();
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			//construct bean with request
			reqBean.constructBeanWithRequest(request, false);
			//check input whether valide
			int validationCode = reqBean.inputValidate(false);
			if (validationCode != 0) {
				isError = true;
				if (validationCode == -1) {
					errorMsg = "计算配置所关联表项的唯一标识符没有找到";
				} else if (validationCode == -2) {
					errorMsg = "没有找到计算配置所关联的表项";
				} else if (validationCode == -3) {
					errorMsg = "缺少必填数据，请检查输入后重新提交";
				} else if (validationCode == -4) {
					errorMsg = "计算用表项与常数不能同时存在，计算用表项只支持加减操作，常数只支持乘除操作";
				}
			} else {
				String userId = (String) session.getAttribute(Constants.USER_KEY);
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());
				//save dimension into database
				int result = 0;
				try {
					result = rptSettingService.updateItemCalculation(reqBean);
				} catch (Exception e) {
					logger.error("Errors while update item calculation", e);
				}
				if (result == 0) {
					isError = true;
				}
			}
			//
			if (!isError) {
				dataMap = reqBean.constructReturnMap(false);
			}		
		} catch (UnsupportedEncodingException ue) {
			isError = true;
			logger.error("Errors while update item calculation", ue);
		} catch (Exception e) {
			isError = true;
			logger.error("Errors while update item calculation", e);
		}
		
		if (isError) {
			dataMap.put("isError", true);
			dataMap.put("msg", errorMsg);
		} }else{
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
		return JsonUtil.fromObject(dataMap);
	}	
	
	
	@RequestMapping(value = "/deleteItemCalculation")
	@ResponseBody
	public Object deleteItemCalculation(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		//get dimension bean from request
		ItemCalBean reqBean = new ItemCalBean();
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			String itemCalId = this.getDecodedRequestParam(request, "id");
			if (StringUtils.isBlank(itemCalId)) {
				isError = true;
				errorMsg = "没有选中需要删除的记录，请选择后再删除！";
			} else {
				reqBean.setItemCalId(Integer.parseInt(itemCalId));
				String userId = (String) session.getAttribute(Constants.USER_KEY);
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());
				//save dimension into database
				int delCount = 0;
				try {
					delCount = rptSettingService.deleteItemCalculation(reqBean);
				} catch (Exception e) {
					logger.error("Errors while delete item calculation", e);
				}
				if (delCount == 0) {
					isError = true;
				}
			}
		} catch (UnsupportedEncodingException ue) {
			isError = true;
			logger.error("Errors while delete item calculation", ue);
		} catch (Exception e) {
			isError = true;
			logger.error("Errors while delete item calculation", e);
		}
		
		if (isError) {
			dataMap.put("isError", true);
			dataMap.put("msg", errorMsg);
		} else {
			dataMap.put("msg", "计算项删除成功");
		}}else{
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
		return JsonUtil.fromObject(dataMap);
	}
	
	@RequestMapping(value = "/getCalItemList")
	@ResponseBody
	public Object getCalItemList(HttpServletRequest request,HttpServletResponse response, HttpSession httpSession) {
		// parameter
		ItemHeaderBean itemHeaderBean = new ItemHeaderBean(); 
		List<Map<String, Object>> list = null;
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			//get item header id from reqesut
			itemHeaderBean.setRuleCode(this.getDecodedRequestParam(request, "itemRuleCode"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		try {
			list = rptSettingService.getCalItemList(itemHeaderBean);
		} catch (Exception e) {
			logger.error("getCalItemList():" + e.toString());
		}}else{
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
		return JsonUtil.fromObject(list);
	}
	
	private String getSegmentType(String highValue, String lowValue, List<DimensionValueSetBean> valueList) {
		String summaryFlag = "N";
		if (!StringUtils.isBlank(highValue) && !StringUtils.isBlank(lowValue) && highValue.equals(lowValue)) {
			if (valueList != null && 0 < valueList.size()) {
				DimensionValueSetBean foundRecord = null;
				for (DimensionValueSetBean dValue : valueList) {
					if (dValue.getDimValue().equals(highValue.trim())) {
						foundRecord = dValue;
						break;
					}
				}
				if (foundRecord != null) {
					summaryFlag = foundRecord.getSummaryFlag();
				}
			}
		}
		return summaryFlag;	
	}
	
	private void addDimensionValueList(ContentLowHighBean contentLowHighBea, List<DimensionValueSetBean> target,  DimensionValueSetBean dimBean) {
		if (dimBean != null && !StringUtils.isBlank(dimBean.getDimSegment())) {
			if ("SEGMENT1".equals(dimBean.getDimSegment())) {
				contentLowHighBea.setSegment1List(target);
				contentLowHighBea.setSegment1Pair(new DimValueNamePair(dimBean.getDimensionCode(), dimBean.getDimensionDescription()));
			} else if ("SEGMENT2".equals(dimBean.getDimSegment())) {
				contentLowHighBea.setSegment2List(target);
				contentLowHighBea.setSegment2Pair(new DimValueNamePair(dimBean.getDimensionCode(), dimBean.getDimensionDescription()));
			} else if ("SEGMENT3".equals(dimBean.getDimSegment())) {
				contentLowHighBea.setSegment3List(target);
				contentLowHighBea.setSegment3Pair(new DimValueNamePair(dimBean.getDimensionCode(), dimBean.getDimensionDescription()));
			} else if ("SEGMENT4".equals(dimBean.getDimSegment())) {
				contentLowHighBea.setSegment4List(target);
				contentLowHighBea.setSegment4Pair(new DimValueNamePair(dimBean.getDimensionCode(), dimBean.getDimensionDescription()));
			} else if ("SEGMENT5".equals(dimBean.getDimSegment())) {
				contentLowHighBea.setSegment5List(target);
				contentLowHighBea.setSegment5Pair(new DimValueNamePair(dimBean.getDimensionCode(), dimBean.getDimensionDescription()));
			} else if ("SEGMENT6".equals(dimBean.getDimSegment())) {
				contentLowHighBea.setSegment6List(target);
				contentLowHighBea.setSegment6Pair(new DimValueNamePair(dimBean.getDimensionCode(), dimBean.getDimensionDescription()));
			} else if ("SEGMENT7".equals(dimBean.getDimSegment())) {
				contentLowHighBea.setSegment7List(target);
				contentLowHighBea.setSegment7Pair(new DimValueNamePair(dimBean.getDimensionCode(), dimBean.getDimensionDescription()));
			} else if ("SEGMENT8".equals(dimBean.getDimSegment())) {
				contentLowHighBea.setSegment8List(target);
				contentLowHighBea.setSegment8Pair(new DimValueNamePair(dimBean.getDimensionCode(), dimBean.getDimensionDescription()));
			} else if ("SEGMENT9".equals(dimBean.getDimSegment())) {
				contentLowHighBea.setSegment9List(target);
				contentLowHighBea.setSegment9Pair(new DimValueNamePair(dimBean.getDimensionCode(), dimBean.getDimensionDescription()));
			} else if ("SEGMENT10".equals(dimBean.getDimSegment())) {
				contentLowHighBea.setSegment10List(target);
				contentLowHighBea.setSegment10Pair(new DimValueNamePair(dimBean.getDimensionCode(), dimBean.getDimensionDescription()));
			} else if ("SEGMENT11".equals(dimBean.getDimSegment())) {
				contentLowHighBea.setSegment11List(target);
				contentLowHighBea.setSegment11Pair(new DimValueNamePair(dimBean.getDimensionCode(), dimBean.getDimensionDescription()));
			} else if ("SEGMENT12".equals(dimBean.getDimSegment())) {
				contentLowHighBea.setSegment12List(target);
				contentLowHighBea.setSegment12Pair(new DimValueNamePair(dimBean.getDimensionCode(), dimBean.getDimensionDescription()));
			} else if ("SEGMENT13".equals(dimBean.getDimSegment())) {
				contentLowHighBea.setSegment13List(target);
				contentLowHighBea.setSegment13Pair(new DimValueNamePair(dimBean.getDimensionCode(), dimBean.getDimensionDescription()));
			} else if ("SEGMENT14".equals(dimBean.getDimSegment())) {
				contentLowHighBea.setSegment14List(target);
				contentLowHighBea.setSegment14Pair(new DimValueNamePair(dimBean.getDimensionCode(), dimBean.getDimensionDescription()));
			} else if ("SEGMENT15".equals(dimBean.getDimSegment())) {
				contentLowHighBea.setSegment15List(target);
				contentLowHighBea.setSegment15Pair(new DimValueNamePair(dimBean.getDimensionCode(), dimBean.getDimensionDescription()));
			} else if ("SEGMENT16".equals(dimBean.getDimSegment())) {
				contentLowHighBea.setSegment16List(target);
				contentLowHighBea.setSegment16Pair(new DimValueNamePair(dimBean.getDimensionCode(), dimBean.getDimensionDescription()));
			} else if ("SEGMENT17".equals(dimBean.getDimSegment())) {
				contentLowHighBea.setSegment17List(target);
				contentLowHighBea.setSegment17Pair(new DimValueNamePair(dimBean.getDimensionCode(), dimBean.getDimensionDescription()));
			} else if ("SEGMENT18".equals(dimBean.getDimSegment())) {
				contentLowHighBea.setSegment18List(target);
				contentLowHighBea.setSegment18Pair(new DimValueNamePair(dimBean.getDimensionCode(), dimBean.getDimensionDescription()));
			} else if ("SEGMENT19".equals(dimBean.getDimSegment())) {
				contentLowHighBea.setSegment19List(target);
				contentLowHighBea.setSegment19Pair(new DimValueNamePair(dimBean.getDimensionCode(), dimBean.getDimensionDescription()));
			} else if ("SEGMENT20".equals(dimBean.getDimSegment())) {
				contentLowHighBea.setSegment20List(target);
				contentLowHighBea.setSegment20Pair(new DimValueNamePair(dimBean.getDimensionCode(), dimBean.getDimensionDescription()));
			} 
		}
	}
	
	
	/*Item Rule definition -----------End  */
	
	
	/*Rule Header related private methods*/
	private ItemGroupRuleHeaderBean constructRuleHeaderBean(HttpServletRequest request) throws UnsupportedEncodingException {		
		ItemGroupRuleHeaderBean requestRuleBean = null;
		if (request != null) {
			requestRuleBean = new ItemGroupRuleHeaderBean();
			//set rule header id
			String ruleHeaderId = this.getDecodedRequestParam(request, "RULE_HEADER_ID");
			if (!StringUtils.isBlank(ruleHeaderId)) {
				requestRuleBean.setRuleHeaderId(Integer.parseInt(ruleHeaderId));
			}
			//set rule code
			requestRuleBean.setRuleCode(this.getDecodedRequestParam(request, "RULE_CODE"));
			//set rule name
			requestRuleBean.setDescription(this.getDecodedRequestParam(request, "DESCRIPTION"));		
		}
		return requestRuleBean;
	}
	
	private boolean isRuleInputValid(ItemGroupRuleHeaderBean requestRuleBean, boolean isCreate) {
		boolean isValid = true;
		if (requestRuleBean == null) {
			return false;
		}
		
		if (!isCreate) {
			if (requestRuleBean.getRuleHeaderId() == 0) {
				isValid = false;
			}
		} else {
			if (StringUtils.isBlank(requestRuleBean.getRuleCode()) || 
					StringUtils.isBlank(requestRuleBean.getDescription())) {
				isValid = false;
			}
		}
		return isValid;
	}
	
	private void constructReturnMap(AuditBean auditBean, boolean isCreate) {
		dataMap = new HashMap();
		if (auditBean != null) {
			if (auditBean instanceof ItemGroupRuleHeaderBean) {
				ItemGroupRuleHeaderBean requestRuleBean = (ItemGroupRuleHeaderBean)auditBean;
				dataMap.put("RULE_HEADER_ID", requestRuleBean.getRuleHeaderId());
				dataMap.put("RULE_CODE", requestRuleBean.getRuleCode());
				dataMap.put("DESCRIPTION", requestRuleBean.getDescription());
				dataMap.put("CREATION_DATE", requestRuleBean.getCreationDate());
				dataMap.put("CREATED_BY", requestRuleBean.getCreatedBy());
				dataMap.put("LAST_UPDATE_DATE", requestRuleBean.getLastUpdateDate());
				dataMap.put("LAST_UPDATED_BY", requestRuleBean.getLastUpdatedBy());
			} else if (auditBean instanceof ItemContentBean) {
				ItemContentBean contentBean = (ItemContentBean)auditBean;
				dataMap.put("ITEM_CONTENT_ID", contentBean.getItemContentId());
				dataMap.put("ITEM_HEADER_ID", contentBean.getItemHeaderId());
				dataMap.put("ITEM_CODE", contentBean.getItemCode());
				dataMap.put("LINE_NUM", contentBean.getLineNum());
				dataMap.put("CREATION_DATE", contentBean.getCreationDate());
				dataMap.put("CREATED_BY", contentBean.getCreatedBy());
				dataMap.put("LAST_UPDATE_DATE", contentBean.getLastUpdateDate());
				dataMap.put("LAST_UPDATED_BY", contentBean.getLastUpdatedBy());
			}else if (auditBean instanceof ItemRowSetHeaderBean) {
				ItemRowSetHeaderBean itemBean = (ItemRowSetHeaderBean) auditBean;
				dataMap.put("ROW_SET_ID", itemBean.getRowSetId());
				dataMap.put("ROW_SET_NAME", itemBean.getRowSetName());
				dataMap.put("RULE_CODE", itemBean.getRuleCode());
				dataMap.put("EXT_ITEM_TYPE", itemBean.getExtItemType());
				dataMap.put("DESCRIPTION", itemBean.getDescription());
				dataMap.put("CREATION_DATE", itemBean.getCreationDate());
				dataMap.put("CREATED_BY", itemBean.getCreatedBy());
				dataMap.put("LAST_UPDATE_DATE", itemBean.getLastUpdateDate());
				dataMap.put("LAST_UPDATED_BY", itemBean.getLastUpdatedBy());
			}
			 else if (auditBean instanceof ItemRowSetLineBean) {
			 ItemRowSetLineBean itemBean = (ItemRowSetLineBean) auditBean;
			 dataMap.put("ROW_ID", itemBean.getRowId());
			 dataMap.put("ROW_SET_ID", itemBean.getRowSetId());
			 dataMap.put("LINE_NUM", itemBean.getLineNum());
			 dataMap.put("ROW_NAME", itemBean.getRowName());
			 dataMap.put("ROW_NUM", itemBean.getRowNum());
			 dataMap.put("CHANGE_SIGN", itemBean.getChangeSign());
			 dataMap.put("DISPLAY_FLAG", itemBean.getDisplayFlag());
			 dataMap.put("EXTERNAL_CODE", itemBean.getExternalCode());
			 dataMap.put("ATTRIBUTE1", itemBean.getAttribute1());
			 dataMap.put("ATTRIBUTE2", itemBean.getAttribute2());
			 dataMap.put("ATTRIBUTE3", itemBean.getAttribute3());
			 dataMap.put("ATTRIBUTE4", itemBean.getAttribute4());
			 dataMap.put("ATTRIBUTE5", itemBean.getAttribute5());
			 
			 
			 dataMap.put("CREATION_DATE", itemBean.getCreationDate());
			 dataMap.put("CREATED_BY", itemBean.getCreatedBy());
			 dataMap.put("LAST_UPDATE_DATE", itemBean.getLastUpdateDate());
			 dataMap.put("LAST_UPDATED_BY", itemBean.getLastUpdatedBy());
			 }
			
			 else if (auditBean instanceof ItemRowCalculationBean) {
				 ItemRowCalculationBean itemBean = (ItemRowCalculationBean) auditBean;
				 dataMap.put("ROW_CAL_ID", itemBean.getRowCalId());
				 dataMap.put("ROW_ID", itemBean.getRowId());
				 dataMap.put("ROW_CAL_NUM", itemBean.getRowCalNum());

				 dataMap.put("SIGN", itemBean.getSign());
				 dataMap.put("CAL_ITEM_CODE", itemBean.getCalItemCode());

				 
				 
				 dataMap.put("CREATION_DATE", itemBean.getCreationDate());
				 dataMap.put("CREATED_BY", itemBean.getCreatedBy());
				 dataMap.put("LAST_UPDATE_DATE", itemBean.getLastUpdateDate());
				 dataMap.put("LAST_UPDATED_BY", itemBean.getLastUpdatedBy());
				 }
			
			
			else if (auditBean instanceof ItemHeaderBean) {
				ItemHeaderBean itemBean = (ItemHeaderBean)auditBean;
				dataMap.put("ITEM_HEADER_ID", itemBean.getItemHeaderId());
				dataMap.put("ITEM_CODE", itemBean.getItemCode());
				dataMap.put("DESCRIPTION", itemBean.getItemDescription());
				dataMap.put("RULE_CODE", itemBean.getRuleCode());
				dataMap.put("CREATION_DATE", itemBean.getCreationDate());
				dataMap.put("CREATED_BY", itemBean.getCreatedBy());
				dataMap.put("LAST_UPDATE_DATE", itemBean.getLastUpdateDate());
				dataMap.put("LAST_UPDATED_BY", itemBean.getLastUpdatedBy());
				if (isCreate) {
					dataMap.put("IS_CONTENT", 'N');
					dataMap.put("IS_CALCULATION", 'N');
				}
			}
		}
	}
	
	/*Rule line related private methods*/
	private ItemGroupRuleLineBean constructRuleLineBean(HttpServletRequest request, boolean isCreate) throws UnsupportedEncodingException {		
		ItemGroupRuleLineBean requestRuleBean = null;
		if (request != null) {
			requestRuleBean = new ItemGroupRuleLineBean();
			//set rule line id
			String ruleLineId = this.getDecodedRequestParam(request, "RULE_LINE_ID");
			if (!StringUtils.isBlank(ruleLineId)) {
				requestRuleBean.setRuleLineId(Long.parseLong(ruleLineId));
			}
			String ruleHeaderId = null;
			if (!isCreate) {
				//set rule header id
				ruleHeaderId = this.getDecodedRequestParam(request, "RULE_HEADER_ID");
			}
			if (!StringUtils.isBlank(ruleHeaderId)) {
				requestRuleBean.setRuleHeaderId(Integer.parseInt(ruleHeaderId));
			}
			//set line number
			String lineSeq = this.getDecodedRequestParam(request, "RULE_LINE_SEQ");
			if (!StringUtils.isBlank(lineSeq)) {
				requestRuleBean.setRuleLineSeq(Integer.parseInt(lineSeq));
			}
			//set line segment 
			requestRuleBean.setDimSegment(this.getDecodedRequestParam(request, "DIM_SEGMENT"));
		}
		return requestRuleBean;
	}
	
	private ItemHeaderBean constructItemHeaderBean(HttpServletRequest request) throws UnsupportedEncodingException {		
		ItemHeaderBean requestItemBean = null;
		if (request != null) {
			requestItemBean = new ItemHeaderBean();
			//set rule line id
			String itemHeaderId = this.getDecodedRequestParam(request, "ITEM_HEADER_ID");
			if (!StringUtils.isBlank(itemHeaderId)) {
				requestItemBean.setItemHeaderId(Long.parseLong(itemHeaderId));
			}
			//set item code
			requestItemBean.setItemCode(this.getDecodedRequestParam(request, "ITEM_CODE"));
			//set item desc
			requestItemBean.setItemDescription(this.getDecodedRequestParam(request, "DESCRIPTION"));
			//set rule code
			requestItemBean.setRuleCode(this.getDecodedRequestParam(request, "RULE_CODE"));
		}
		return requestItemBean;
	}
	
	private boolean isInputValid(AuditBean auditBean, boolean isCreate) {
		boolean isValid = true;
		if (auditBean instanceof ItemGroupRuleLineBean) {
			ItemGroupRuleLineBean reqRuleLineBean = (ItemGroupRuleLineBean)auditBean;
			if (reqRuleLineBean == null) {
				return false;
			}
			if (!isCreate) {
				if (reqRuleLineBean.getRuleLineId() == 0) {
					isValid = false;
				}
			} else {
				if (reqRuleLineBean.getRuleHeaderId() == 0 || reqRuleLineBean.getRuleLineSeq() == 0 ||
						StringUtils.isBlank(reqRuleLineBean.getDimSegment())) {
					isValid = false;
				}
			}
		} 
		else if (auditBean instanceof ItemLookUpLineBean) {
			ItemLookUpLineBean reqLookUpLineBean = (ItemLookUpLineBean)auditBean;
			if (reqLookUpLineBean == null) {
				return false;
			}
			if (!isCreate) {
				if (reqLookUpLineBean.getLookUpValueId() == 0) {
					isValid = false;
				}
			} else {
				if (reqLookUpLineBean.getLookUpTypeId() == 0 || StringUtils.isBlank(reqLookUpLineBean.getLookUpValue())||
						StringUtils.isBlank(reqLookUpLineBean.getDescription())) {
					isValid = false;
				}
			}
		}
		else if (auditBean instanceof ItemCodeExtLineBean) {
			ItemCodeExtLineBean reqCodeExtLineBean = (ItemCodeExtLineBean)auditBean;
			if (reqCodeExtLineBean == null) {
				return false;
			}
			if (!isCreate) {
				if (reqCodeExtLineBean.getExtItemValueId() == 0) {
					isValid = false;
				}
			} else {
				if (reqCodeExtLineBean.getExtItemTypeId() == 0 || StringUtils.isBlank(reqCodeExtLineBean.getExtItemValue())||
						StringUtils.isBlank(reqCodeExtLineBean.getDescription())) {
					isValid = false;
				}
			}
		}
		
		else if (auditBean instanceof ItemContentBean) {
			ItemContentBean contentBean = (ItemContentBean)auditBean;
			if (contentBean == null) {
				return false;
			}
			if (!isCreate) {
				if (contentBean.getItemContentId() == 0) {
					isValid = false;
				}
			} else {
				if (contentBean.getLineNum() == 0 || contentBean.getItemHeaderId() == 0 ||
						StringUtils.isBlank(contentBean.getItemCode()) || StringUtils.isBlank(contentBean.getSign())) {
					isValid = false;
				}
			}
		} else if (auditBean instanceof ItemHeaderBean) {
			ItemHeaderBean itemBean = (ItemHeaderBean)auditBean;
			if (itemBean == null) {
				return false;
			}
			if (!isCreate) {
				if (itemBean.getItemHeaderId() == 0) {
					isValid = false;
				}
			} else {
				if (StringUtils.isBlank(itemBean.getItemCode()) || 
						StringUtils.isBlank(itemBean.getItemDescription()) ||
						StringUtils.isBlank(itemBean.getRuleCode())) {
					isValid = false;
				}
			}
		}
		else if (auditBean instanceof ItemRowSetLineBean) {
			ItemRowSetLineBean reqRowSetLineBean = (ItemRowSetLineBean) auditBean;
			if (reqRowSetLineBean == null) {
				return false;
			}
			if (!isCreate) {
				if (reqRowSetLineBean.getRowId() == 0) {
					isValid = false;
				}
			} else {
				if (reqRowSetLineBean.getRowSetId() == 0 || reqRowSetLineBean.getLineNum() == 0

						|| StringUtils.isBlank(reqRowSetLineBean.getRowName())
						|| StringUtils.isBlank(reqRowSetLineBean.getChangeSign())
						|| StringUtils.isBlank(reqRowSetLineBean.getDisplayFlag())) {
					isValid = false;
				}
			}
		} else if (auditBean instanceof ItemRowSetHeaderBean) {
			ItemRowSetHeaderBean reqRowSetHeaderBean = (ItemRowSetHeaderBean) auditBean;
			if (reqRowSetHeaderBean == null) {
				return false;
			}

			if (!isCreate) {
				if (reqRowSetHeaderBean.getRowSetId() == 0) {
					isValid = false;
				}
			}
			if (isValid) {
				if (StringUtils.isBlank(reqRowSetHeaderBean.getRowSetName())
						|| StringUtils.isBlank(reqRowSetHeaderBean.getRuleCode())) {
					isValid = false;
				}
			}
		}else if (auditBean instanceof ItemRowCalculationBean) {
			ItemRowCalculationBean reqRowCalculationBean = (ItemRowCalculationBean) auditBean;
			if (reqRowCalculationBean == null) {
				return false;
			}
			if (!isCreate) {
				if (reqRowCalculationBean.getRowCalId() == 0) {
					isValid = false;
				}
			} else {
				if (reqRowCalculationBean.getRowId() == 0 || reqRowCalculationBean.getRowCalNum() == 0

	
						|| StringUtils.isBlank(reqRowCalculationBean.getSign())
						|| StringUtils.isBlank(reqRowCalculationBean.getCalItemCode())) {
					isValid = false;
				}
			}
		}
		return isValid;
	}
	
	private void constructReturnMap(ItemGroupRuleLineBean reqRuleLineBean) {
		dataMap = new HashMap();
		if (reqRuleLineBean != null) {
			dataMap.put("RULE_LINE_ID", reqRuleLineBean.getRuleLineId());
			dataMap.put("RULE_LINE_SEQ", reqRuleLineBean.getRuleLineSeq());
			dataMap.put("RULE_HEADER_ID", reqRuleLineBean.getRuleHeaderId());
			dataMap.put("DIM_SEGMENT", reqRuleLineBean.getDimSegment());
			dataMap.put("DESCRIPTION", reqRuleLineBean.getDimSegDescription());
			dataMap.put("CREATION_DATE", reqRuleLineBean.getCreationDate());
			dataMap.put("CREATED_BY", reqRuleLineBean.getCreatedBy());
			dataMap.put("LAST_UPDATE_DATE", reqRuleLineBean.getLastUpdateDate());
			dataMap.put("LAST_UPDATED_BY", reqRuleLineBean.getLastUpdatedBy());
		}
	}
	
	/*Rule line related private methods*/
	private ItemContentBean constructItemContentBean(HttpServletRequest request, boolean isCreate) throws UnsupportedEncodingException {		
		ItemContentBean reqContentBean = null;
		if (request != null) {
			reqContentBean = new ItemContentBean();
			//set item content id
			String contentId = this.getDecodedRequestParam(request, "ITEM_CONTENT_ID");
			if (!StringUtils.isBlank(contentId)) {
				reqContentBean.setItemContentId(Integer.parseInt(contentId));
			}
			String itemHeaderId = null;
			if (!isCreate) {
				//set rule header id
				itemHeaderId = this.getDecodedRequestParam(request, "ITEM_HEADER_ID");
				if (!StringUtils.isBlank(itemHeaderId)) {
					reqContentBean.setItemHeaderId(Integer.parseInt(itemHeaderId));
				}
				reqContentBean.setItemCode(this.getDecodedRequestParam(request, "ITEM_CODE"));
			} 
		
			//set line number
			String lineSeq = this.getDecodedRequestParam(request, "LINE_NUM");
			if (!StringUtils.isBlank(lineSeq)) {
				reqContentBean.setLineNum(Integer.parseInt(lineSeq));
			}
			//set line segment 
			reqContentBean.setSign(request.getParameter("SIGN"));
		}
		return reqContentBean;
	}
	
	
	

	/* This is the report CodeExt in the Settings .  ----Mr. Wang.2016.10.25  */
	
	@RequestMapping(value = "/getItemCodeExtList")
	@ResponseBody
	public Object getItemCodeExtList (HttpServletRequest request,HttpServletResponse response
) {
		ItemCodeExtHeaderBean codeExtHeaderBean =new ItemCodeExtHeaderBean();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			String target = this.getDecodedRequestParam(request, "target");
			if (StringUtils.isBlank(target) || !"rowSetConf".equals(target)) {
	            //handle the pagination case, initialize page related information
				this.initilizePagingInfo(request);
				//set query start index
				codeExtHeaderBean.setRowStartIndex(this.queryRowStartIndex);
				//set row count
				codeExtHeaderBean.setRowCount(this.rows);
			}
			codeExtHeaderBean.setExtItemType(this.getDecodedRequestParam(request, "codeExtCode"));
			codeExtHeaderBean.setDescription(this.getDecodedRequestParam(request, "codeExtName"));		
		} catch (UnsupportedEncodingException e1) {
			logger.error("getItemCodeExtList():" + e1.toString());
		}
		
		List<Map<String, Object>> list = null;
		try {
			
		list = rptSettingService.getExtHeaderList(codeExtHeaderBean);
		
		} catch (Exception e) {
			logger.error("getItemCodeExtList():" + e.toString());
		}
	
		JSONArray jsonlist = JsonUtil.fromObject(list);
		//put rows into map
		dataMap.put("rows", jsonlist);
		int count = 0;
		try {
			count = rptSettingService.getCountByCodeExtHeaderBean(codeExtHeaderBean);		
		} catch (Exception e) {
			logger.error("getItemCodeExtList():" + e.toString());
		}
		//put total row number in the map
		dataMap.put("total", count);
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
		return JsonUtil.fromObject(dataMap);
	}

	@RequestMapping(value = "/saveCodeExtHeader")
	@ResponseBody
	public Object saveCodeExtHeader(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		//get dimension bean from request
		ItemCodeExtHeaderBean reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			reqBean = this.constructCodeExtHeaderBean(request);
			if (!this.isCodeExtInputValid(reqBean, true)) {
				isError = true;
				errorMsg = "输入信息有误，请检查数据格式，重新保存！";
			} else {
				String userId = (String) session.getAttribute(Constants.USER_KEY);
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());
				//save dimension into database
				int newKey = 0;
				try {
					newKey = rptSettingService.createNewCodeExtHeader(reqBean);
				} catch (DuplicateKeyException de) {
					logger.error("saveCodeExtHeader():" + de);
					errorMsg = "不能存在重复的外部代码类型，请重新输入再次提交";
				} catch (Exception e) {
					logger.error("saveCodeExtHeader():" + e);
				}
				if (newKey == 0) {
					isError = true;
				}
				reqBean.setExtItemTypeId(newKey);
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		}
		
		if (isError) {
			dataMap.put("isError", true);
			dataMap.put("msg", errorMsg);
		} else {
			constructReturnMap(reqBean);	
		}}else{
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
		return JsonUtil.fromObject(dataMap);
	}
		
	@RequestMapping(value = "/updateCodeExtHeader")
	@ResponseBody
	public Object updateCodeExtHeader(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		//get dimension bean from request
		ItemCodeExtHeaderBean reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			reqBean = this.constructCodeExtHeaderBean(request);
			if (!this.isCodeExtInputValid(reqBean, true)) {
				isError = true;
				errorMsg = "输入信息有误，请检查数据格式，重新保存！";
			} else {
				String userId = (String) session.getAttribute(Constants.USER_KEY);
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());
				//save dimension into database
				int updateCount = 0;
				try {
					updateCount = rptSettingService.updateCodeExtHeader(reqBean);
				} catch (DuplicateKeyException de) {
					logger.error("updateCodeExtHeader():" + de);
					errorMsg = "不能存在重复的外部代码类型，请重新输入再次提交";
				} catch (Exception e) {
					logger.error("updateCodeExtHeader():" + e);
				}
				if (updateCount == 0) {
					isError = true;
				}
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		}
		
		if (isError) {
			dataMap.put("isError", true);
			dataMap.put("msg", errorMsg);
		} else {
			constructReturnMap(reqBean);	
		}}else{
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
		return JsonUtil.fromObject(dataMap);
	}
	
	@RequestMapping(value = "/deleteCodeExtHeader")
	@ResponseBody
	public Object deleteCodeExtHeader(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		//get dimension bean from request
		ItemCodeExtHeaderBean reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();

		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			String codeExtHeaderId = this.getDecodedRequestParam(request, "id");		
			if (StringUtils.isBlank(codeExtHeaderId)) {
				isError = true;
				errorMsg = "外部代码行类型为空";
			} else {
				reqBean = new ItemCodeExtHeaderBean();
				reqBean.setExtItemTypeId(Integer.parseInt(codeExtHeaderId));
			
				String userId = (String) session.getAttribute(Constants.USER_KEY);
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());
				//save dimension into database
				int delCount = 0;
				try {
					delCount = rptSettingService.deleteCodeExtHeader(reqBean);
				} catch (DataReferenceException de) {
					// TODO Auto-generated catch block
					System.out.println("Reference record found while delete codeExt header: " + de);
					errorMsg = "没有选中需要删除的记录，请选择后再删除！！";
					isError = true;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("Errors while delete codeExt header: " + e);
				}
				if (delCount == 0) {
					isError = true;
				}
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		} catch (Exception e) {
			isError = true;
		}
		
		if (isError) {
			dataMap.put("isError", true);
			dataMap.put("msg", errorMsg);
		} else {
			constructReturnMap(reqBean);	
		}}else{
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
		return JsonUtil.fromObject(dataMap);
	}
	
	/*CodeExt Line related actions ----------Start*/
	
	@RequestMapping(value = "/getCodeExtLineList")
	@ResponseBody
	public Object getCodeExtLineList(HttpServletRequest request,HttpServletResponse response, HttpSession httpSession) {
		// parameter
		ItemCodeExtLineBean CodeExtLineBean = new ItemCodeExtLineBean(); 

		String extItemTypeId = null;
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			
			  //handle the pagination case, initialize page related information
			this.initilizePagingInfo(request);
			//set query start index
			CodeExtLineBean.setRowStartIndex(this.queryRowStartIndex);
			//set row count
			CodeExtLineBean.setRowCount(this.rows);
			extItemTypeId = this.getDecodedRequestParam(request, "extItemTypeId");
			
			
			CodeExtLineBean.setExtItemValue(this.getDecodedRequestParam(request, "dimSegment"));
			
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (!StringUtils.isBlank(extItemTypeId)) {
			CodeExtLineBean.setExtItemTypeId(Integer.parseInt(extItemTypeId));
			httpSession.removeAttribute(SESSION_KEY_CODE_HEADER);
			httpSession.setAttribute(SESSION_KEY_CODE_HEADER, extItemTypeId);
		}
		List<Map<String, Object>> list = null;
		try {
			list = rptSettingService.getCodeExtLineList(CodeExtLineBean);
		} catch (Exception e) {
			System.out.println("Error while get dimension:" + e);
		}
		int count = 0;
		try {
			count = rptSettingService.getCountByCodeExtLineBean(CodeExtLineBean);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error while get dimension count:" + e);
		}
		
		
		JSONArray jsonlist = JsonUtil.fromObject(list);
		//set rows in the map
		dataMap.put("rows", jsonlist);
		//set total in the map
		dataMap.put("total", count);}else{
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
		return JsonUtil.fromObject(dataMap);
	}
	
	@RequestMapping(value = "/saveCodeExtLine")
	@ResponseBody
	public Object saveCodeExtLineRecord(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		//get dimension bean from request
		ItemCodeExtLineBean reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			reqBean = this.constructCodeExtLineBean(request, true);
			//try to get header it from session
			String sessionHeaderId = null;
			if (session.getAttribute(SESSION_KEY_CODE_HEADER) != null) {
				sessionHeaderId = (String)session.getAttribute(SESSION_KEY_CODE_HEADER);
			}
			if (!StringUtils.isBlank(sessionHeaderId)) {
				reqBean.setExtItemTypeId(Integer.parseInt(sessionHeaderId));
			}
			if (!this.isInputValid(reqBean, true)) {
				isError = true;
				errorMsg = "输入信息有误，请检查数据格式，重新保存！";
			} else {
				String userId = (String) session.getAttribute(Constants.USER_KEY);
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());
				//save dimension into database
				int newKey = 0;
				try {
					newKey = rptSettingService.createNewCodeExtLine(reqBean);
				} catch (DuplicateKeyException de) {
					logger.error("saveDimensionValue():" + de);
					errorMsg = "不能存在重复的外部代码值，请重新输入再次提交";
				} catch (Exception e) {
					logger.error("saveCodeExtLineRecord():" + e);
				}
				if (newKey == 0) {
					isError = true;
				}
				reqBean.setExtItemValueId(newKey);
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		}
		
		if (isError) {
			dataMap.put("isError", true);
			dataMap.put("msg", errorMsg);
		} else {
			constructReturnMap(reqBean);	
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
		return JsonUtil.fromObject(dataMap);
	}
		
	@RequestMapping(value = "/updateCodeExtLine")
	@ResponseBody
	public Object updateCodeExtLineRecord(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		//get dimension bean from request
		ItemCodeExtLineBean reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			reqBean = this.constructCodeExtLineBean(request, false);
			if (!this.isInputValid(reqBean, false)) {
				isError = true;
				errorMsg = "输入信息有误，请检查数据格式，重新保存！";
			} else {
				String userId = (String) session.getAttribute(Constants.USER_KEY);
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());
				//save dimension into database
				int updateCount = 0;
				try {
					updateCount = rptSettingService.updateCodeExtLine(reqBean);
				}  catch (DuplicateKeyException de) {
					logger.error("saveDimensionValue():" + de);
					errorMsg = "不能存在重复的外部代码值，请重新输入再次提交";
				} catch (Exception e) {
					logger.error("saveDimensionValue():" + e);
				}
				if (updateCount == 0) {
					isError = true;
				}
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		}
		
		if (isError) {
			dataMap.put("isError", true);
			dataMap.put("msg", errorMsg);
		} else {
			constructReturnMap(reqBean);	
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
		return JsonUtil.fromObject(dataMap);
	}
	
	
	
	@RequestMapping(value = "/showCodeExtDimensionView")
	public String showCodeExtDimensionView(HttpServletRequest request,HttpServletResponse response, Model model) {
		//get demensionValueId
		String codeExtHeaderId = null;
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			codeExtHeaderId = this.getDecodedRequestParam(request, "codeExtId");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (!StringUtils.isBlank(codeExtHeaderId)) {
			ItemCodeExtHeaderBean parentBean = null;
			try {
				parentBean = rptSettingService.getCodeExtHeaderByByPrimaryKey(Integer.parseInt(codeExtHeaderId));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (parentBean != null) {
				model.addAttribute("codeExtHeaderBean", parentBean);
			}			
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
		return "configuration/externalCodeLineConfiguration";
	}

	/*codeExt Header related private methods*/
	private ItemCodeExtHeaderBean constructCodeExtHeaderBean(HttpServletRequest request) throws UnsupportedEncodingException {		
		ItemCodeExtHeaderBean requestcodeExtBean = null;
		if (request != null) {
			requestcodeExtBean = new ItemCodeExtHeaderBean();
			//set codeExt header id
			String codeExtHeaderId = this.getDecodedRequestParam(request, "ITEM_TYPE_ID");
			if (!StringUtils.isBlank(codeExtHeaderId)) {
				requestcodeExtBean.setExtItemTypeId(Integer.parseInt(codeExtHeaderId));
			}
			//set codeExt code
			requestcodeExtBean.setExtItemType(this.getDecodedRequestParam(request, "ITEM_TYPE"));
			//set codeExt name
			requestcodeExtBean.setDescription(this.getDecodedRequestParam(request, "DESCRIPTION"));		
		}
		return requestcodeExtBean;
	}
	
	private boolean isCodeExtInputValid(ItemCodeExtHeaderBean requestcodeExtBean, boolean isCreate) {
		boolean isValid = true;
		if (requestcodeExtBean == null) {
			return false;
		}
		
		if (!isCreate) {
			if (requestcodeExtBean.getExtItemTypeId() == 0) {
				isValid = false;
			}
		}
		if (isValid) {
			if (StringUtils.isBlank(requestcodeExtBean.getExtItemType()) || 
					StringUtils.isBlank(requestcodeExtBean.getDescription())) {
				isValid = false;
			}
		}
		return isValid;
	}
	
	
	private void constructReturnMap(ItemCodeExtHeaderBean requestcodeExtBean) {
		dataMap = new HashMap();
		if (requestcodeExtBean != null) {
			dataMap.put("ITEM_TYPE_ID", requestcodeExtBean.getExtItemTypeId());
			dataMap.put("ITEM_TYPE", requestcodeExtBean.getExtItemType());
			dataMap.put("DESCRIPTION", requestcodeExtBean.getDescription());
			dataMap.put("CREATION_DATE", requestcodeExtBean.getCreationDate());
			dataMap.put("CREATED_BY", requestcodeExtBean.getCreatedBy());
			dataMap.put("LAST_UPDATE_DATE", requestcodeExtBean.getLastUpdateDate());
			dataMap.put("LAST_UPDATED_BY", requestcodeExtBean.getLastUpdatedBy());
		}
	}
	
	
	
	
	
	/* This is the report LookUp in the Settings .  ----Mr. Wang.2016.10.27  */	
	
	
	@RequestMapping(value = "/getItemLookUpList")
	@ResponseBody
	public Object getItemLookUpList (HttpServletRequest request,HttpServletResponse response) {
		ItemLookUpHeaderBean LookUpHeaderBean =new ItemLookUpHeaderBean();

		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
	          //handle the pagination case, initialize page related information
				this.initilizePagingInfo(request);
				//set query start index
				LookUpHeaderBean.setRowStartIndex(this.queryRowStartIndex);
				//set row count
				LookUpHeaderBean.setRowCount(this.rows);
			LookUpHeaderBean.setLookUpType(this.getDecodedRequestParam(request, "lookUpCode"));
			LookUpHeaderBean.setDescription(this.getDecodedRequestParam(request, "lookUpName"));
					
		} catch (UnsupportedEncodingException e1) {
			System.out.println(e1);
		}
		
		List<Map<String, Object>> list = null;
		try {
			
		list = rptSettingService.getLookUpHeaderList(LookUpHeaderBean);
		
		} catch (Exception e) {
			System.out.println("Error while get dimension:" + e);
		}
		
		JSONArray jsonlist = JsonUtil.fromObject(list);
		//put rows into map
		dataMap.put("rows", jsonlist);
		int count = 0;
		try {
			count = rptSettingService.getCountByLookUpHeaderBean(LookUpHeaderBean);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error while get dimension count:" + e);
		}
		//put total row number in the map
		dataMap.put("total", count);	
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
		return JsonUtil.fromObject(dataMap);
	}
	
	
	
	
	@RequestMapping(value = "/saveLookUpHeader")
	@ResponseBody
	public Object saveLookUpHeader(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		//get dimension bean from request
		ItemLookUpHeaderBean reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			reqBean = this.constructLookUpHeaderBean(request);
			if (!this.isLookUpInputValid(reqBean, true)) {
				isError = true;
				errorMsg = "输入信息有误，请检查数据格式，重新保存！";
			} else {
				String userId = (String) session.getAttribute(Constants.USER_KEY);
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());
				//save dimension into database
				int newKey = 0;
				try {
					newKey = rptSettingService.createNewLookUpHeader(reqBean);
				} catch (DuplicateKeyException de) {
					logger.error("saveLookUpHeader():" + de);
					errorMsg = "不能存在重复的值集类型，请重新输入再次提交";
				} catch (Exception e) {
					logger.error("saveLookUpHeader():" + e);
				}
				if (newKey == 0) {
					isError = true;
				}
				reqBean.setLookUpTypeId(newKey);
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		}
		
		if (isError) {
			dataMap.put("isError", true);
			dataMap.put("msg", errorMsg);
		} else {
			constructReturnMap(reqBean);	
		}}else{
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
		return JsonUtil.fromObject(dataMap);
	}
		
	@RequestMapping(value = "/updateLookUpHeader")
	@ResponseBody
	public Object updateLookUpHeader(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		//get dimension bean from request
		ItemLookUpHeaderBean reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
			
		try {
			reqBean = this.constructLookUpHeaderBean(request);
			if (!this.isLookUpInputValid(reqBean, true)) {
				isError = true;
				errorMsg = "输入信息有误，请检查数据格式，重新保存！";
			} else {
				String userId = (String) session.getAttribute(Constants.USER_KEY);
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());
				//save dimension into database
				int updateCount = 0;
				try {
					updateCount = rptSettingService.updateLookUpHeader(reqBean);
				} catch (DuplicateKeyException de) {
					logger.error("updateLookUpHeader():" + de);
					errorMsg = "不能存在重复的值集类型，请重新输入再次提交";
				} catch (Exception e) {
					logger.error("updateLookUpHeader():" + e);
				}
				if (updateCount == 0) {
					isError = true;
				}
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		}
		
		if (isError) {
			dataMap.put("isError", true);
			dataMap.put("msg", errorMsg);
		} else {
			constructReturnMap(reqBean);	
		}}else{
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
		return JsonUtil.fromObject(dataMap);
	}
	
	@RequestMapping(value = "/deleteLookUpHeader")
	@ResponseBody
	public Object deleteLookUpHeader(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		//get dimension bean from request
		ItemLookUpHeaderBean reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			String lookUpHeaderId = this.getDecodedRequestParam(request, "id");		
			if (StringUtils.isBlank(lookUpHeaderId)) {
				isError = true;
				errorMsg = "Lookup头为空";
			} else {
				reqBean = new ItemLookUpHeaderBean();
				reqBean.setLookUpTypeId(Integer.parseInt(lookUpHeaderId));
			
				String userId = (String) session.getAttribute(Constants.USER_KEY);
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());
				//save dimension into database
				int delCount = 0;
				try {
					delCount = rptSettingService.deleteLookUpHeader(reqBean);
				} catch (DataReferenceException de) {
					// TODO Auto-generated catch block
					System.out.println("Reference record found while delete LOOKUP header: " + de);
					errorMsg = "没有选中需要删除的记录，请选择后再删除！";
					isError = true;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("Errors while delete LOOKUP header: " + e);
				}
				if (delCount == 0) {
					isError = true;
				}
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		} catch (Exception e) {
			isError = true;
		}
		
		if (isError) {
			dataMap.put("isError", true);
			dataMap.put("msg", errorMsg);
		} else {
			constructReturnMap(reqBean);	
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
		return JsonUtil.fromObject(dataMap);
	}
	
	/*LookUp Line related actions ----------Start*/
	
	@RequestMapping(value = "/getLookUpLineList")
	@ResponseBody
	public Object getLookUpLineList(HttpServletRequest request,HttpServletResponse response
, HttpSession httpSession) {
		// parameter
		ItemLookUpLineBean LookUpLineBean = new ItemLookUpLineBean(); 

		String lookUpTypeId = null;
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			
            //handle the pagination case, initialize page related information
			this.initilizePagingInfo(request);
			//set query start index
			LookUpLineBean.setRowStartIndex(this.queryRowStartIndex);
			//set row count
			LookUpLineBean.setRowCount(this.rows);
			
			
			lookUpTypeId = this.getDecodedRequestParam(request, "lookUpTypeId");
			
			
			LookUpLineBean.setLookUpValue(this.getDecodedRequestParam(request, "dimSegment"));
			
      //  System.out.println(LookUpLineBean.getLookUpValue()+"???????????");
			
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (!StringUtils.isBlank(lookUpTypeId)) {
			LookUpLineBean.setLookUpTypeId(Integer.parseInt(lookUpTypeId));
			httpSession.removeAttribute(SESSION_KEY_LOOKUP_HEADER);
			httpSession.setAttribute(SESSION_KEY_LOOKUP_HEADER, lookUpTypeId);
		}
		List<Map<String, Object>> list = null;
		try {
			list = rptSettingService.getLookUpLineList(LookUpLineBean);
		} catch (Exception e) {
			System.out.println("Error while get dimension:" + e);
		}
		int count = 0;
		try {
			count = rptSettingService.getCountByLookUpLineBean(LookUpLineBean);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error while get dimension count:" + e);
		}
		JSONArray jsonlist = JsonUtil.fromObject(list);
		//set rows in the map
		dataMap.put("rows", jsonlist);
		//set total in the map
		dataMap.put("total", count);
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
		return JsonUtil.fromObject(dataMap);
	}
	
	@RequestMapping(value = "/saveLookUpLine")
	@ResponseBody
	public Object saveLookUpLineRecord(HttpServletRequest request,HttpServletResponse response
, HttpSession session) {
		//get dimension bean from request
		ItemLookUpLineBean reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			reqBean = this.constructLookUpLineBean(request, true);
			//try to get header it from session
			String sessionHeaderId = null;
			if (session.getAttribute(SESSION_KEY_LOOKUP_HEADER) != null) {
				sessionHeaderId = (String)session.getAttribute(SESSION_KEY_LOOKUP_HEADER);
			}
			if (!StringUtils.isBlank(sessionHeaderId)) {
				reqBean.setLookUpTypeId(Integer.parseInt(sessionHeaderId));
			}
			
			if (!this.isInputValid(reqBean, true)) {
				isError = true;
				errorMsg = "输入信息有误，请检查数据格式，重新保存！";
			} else {
				String userId = (String) session.getAttribute(Constants.USER_KEY);
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());
				//save dimension into database
				int newKey = 0;
				try {
					newKey = rptSettingService.createNewLookUpLine(reqBean);
				} catch (DuplicateKeyException de) {
					logger.error("saveLookUpLineRecord():" + de);
					errorMsg = "不能存在重复的值代码，请重新输入再次提交";
				} catch (Exception e) {
					logger.error("saveLookUpLineRecord():" + e);
				}
				if (newKey == 0) {
					isError = true;
				}
				reqBean.setLookUpValueId(newKey);
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		}
		
		if (isError) {
			dataMap.put("isError", true);
			dataMap.put("msg", errorMsg);
		} else {
			constructReturnMap(reqBean);	
		}}else{
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
		return JsonUtil.fromObject(dataMap);
	}
		

	@RequestMapping(value = "/updateLookUpLine")
	@ResponseBody
	public Object updateLookUpLineRecord(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		//get dimension bean from request
		ItemLookUpLineBean reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			reqBean = this.constructLookUpLineBean(request, false);
			if (!this.isInputValid(reqBean, false)) {
				isError = true;
				errorMsg = "输入信息有误，请检查数据格式，重新保存！";
			} else {
				String userId = (String) session.getAttribute(Constants.USER_KEY);
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());
				//save dimension into database
				int updateCount = 0;
				try {
					updateCount = rptSettingService.updateLookUpLine(reqBean);
				} catch (DuplicateKeyException de) {
					logger.error("updateLookUpLineRecord():" + de);
					errorMsg = "不能存在重复的值代码，请重新输入再次提交";
				} catch (Exception e) {
					logger.error("updateLookUpLineRecord():" + e);
				}
				if (updateCount == 0) {
					isError = true;
				}
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		}
		
		if (isError) {
			dataMap.put("isError", true);
			dataMap.put("msg", errorMsg);
		} else {
			constructReturnMap(reqBean);	
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
		return JsonUtil.fromObject(dataMap);
	}
	
	@RequestMapping(value = "/showLookUpDimensionView")
	public String showLookUpDimensionView(HttpServletRequest request,HttpServletResponse response, Model model) {
		//get demensionValueId
		String lookUpHeaderId = null;
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			lookUpHeaderId = this.getDecodedRequestParam(request, "lookUpId");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (!StringUtils.isBlank(lookUpHeaderId)) {
			ItemLookUpHeaderBean parentBean = null;
			try {
				parentBean = rptSettingService.getLookUpHeaderByByPrimaryKey(Integer.parseInt(lookUpHeaderId));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (parentBean != null) {
				model.addAttribute("lookUpHeaderBean", parentBean);
			}			
		}}else{
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
		return "configuration/rowSetConfiguration";
	}
	
	/*lookUp Header related private methods*/
	private ItemLookUpHeaderBean constructLookUpHeaderBean(HttpServletRequest request) throws UnsupportedEncodingException {		
		ItemLookUpHeaderBean requestlookUpBean = null;
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		if (request != null) {
			requestlookUpBean = new ItemLookUpHeaderBean();
			//set lookUp header id
			String lookUpHeaderId = this.getDecodedRequestParam(request, "LOOKUP_TYPE_ID");
			if (!StringUtils.isBlank(lookUpHeaderId)) {
				requestlookUpBean.setLookUpTypeId(Integer.parseInt(lookUpHeaderId));
			}
			//set lookUp code
			requestlookUpBean.setLookUpType(this.getDecodedRequestParam(request, "LOOKUP_TYPE"));
			//set lookUp name
			requestlookUpBean.setDescription(this.getDecodedRequestParam(request, "DESCRIPTION"));		
		}}else{
//			try {
//				request.getRequestDispatcher("/error/webErr.jsp").forward(request, response);
//			} catch (ServletException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
	}
		return requestlookUpBean;
	}
	
	private boolean isLookUpInputValid(ItemLookUpHeaderBean requestlookUpBean, boolean isCreate) {
		boolean isValid = true;
		if (requestlookUpBean == null) {
			return false;
		}
		
		if (!isCreate) {
			if (requestlookUpBean.getLookUpTypeId() == 0) {
				isValid = false;
			}
		}
		if (isValid) {
			if (StringUtils.isBlank(requestlookUpBean.getLookUpType()) || 
					StringUtils.isBlank(requestlookUpBean.getDescription())) {
				isValid = false;
			}
		}
		return isValid;
	}
	
	
	private void constructReturnMap(ItemLookUpHeaderBean requestlookUpBean) {
		dataMap = new HashMap();
		if (requestlookUpBean != null) {
			dataMap.put("LOOKUP_TYPE_ID", requestlookUpBean.getLookUpTypeId());
			dataMap.put("LOOKUP_TYPE", requestlookUpBean.getLookUpType());
			dataMap.put("DESCRIPTION", requestlookUpBean.getDescription());
			dataMap.put("CREATION_DATE", requestlookUpBean.getCreationDate());
			dataMap.put("CREATED_BY", requestlookUpBean.getCreatedBy());
			dataMap.put("LAST_UPDATE_DATE", requestlookUpBean.getLastUpdateDate());
			dataMap.put("LAST_UPDATED_BY", requestlookUpBean.getLastUpdatedBy());
		}
	}
	
	
	/*CodeExt line related private methods*/
	private ItemCodeExtLineBean constructCodeExtLineBean(HttpServletRequest request, boolean isCreate) throws UnsupportedEncodingException {		
		ItemCodeExtLineBean requestCodeExtBean = null;
		if (request != null) {
			requestCodeExtBean = new ItemCodeExtLineBean();
			//set CodeExt line id
			String CodeExtLineId = this.getDecodedRequestParam(request, "ITEM_VALUE_ID");
			if (!StringUtils.isBlank(CodeExtLineId)) {
				requestCodeExtBean.setExtItemValueId(Long.parseLong(CodeExtLineId));
			}
			String CodeExtHeaderId = null;
			if (!isCreate) {
				//set CodeExt header id
				CodeExtHeaderId = this.getDecodedRequestParam(request, "ITEM_TYPE_ID");
			}
			if (!StringUtils.isBlank(CodeExtHeaderId)) {
				requestCodeExtBean.setExtItemTypeId(Integer.parseInt(CodeExtHeaderId));
			}
			//set itemValue
			requestCodeExtBean.setExtItemValue(this.getDecodedRequestParam(request, "ITEM_VALUE"));
			
			//set line DESCRIPTION 
			requestCodeExtBean.setDescription(this.getDecodedRequestParam(request, "DESCRIPTION"));
			
			//日期
			requestCodeExtBean.setStartDate(new Date());
			//requestCodeExtBean.setEndDate(new Date());
			
			requestCodeExtBean.setAttribute1(this.getDecodedRequestParam(request, "ATTRIBUTE1"));
			
			requestCodeExtBean.setAttribute2(this.getDecodedRequestParam(request, "ATTRIBUTE2"));
			
			requestCodeExtBean.setAttribute3(this.getDecodedRequestParam(request, "ATTRIBUTE3"));
			
			requestCodeExtBean.setAttribute4(this.getDecodedRequestParam(request, "ATTRIBUTE4"));
			
			requestCodeExtBean.setAttribute5(this.getDecodedRequestParam(request, "ATTRIBUTE5"));
			
			requestCodeExtBean.setAttribute6(this.getDecodedRequestParam(request, "ATTRIBUTE6"));
			
			requestCodeExtBean.setAttribute7(this.getDecodedRequestParam(request, "ATTRIBUTE7"));
			
			requestCodeExtBean.setAttribute8(this.getDecodedRequestParam(request, "ATTRIBUTE8"));
			
			requestCodeExtBean.setAttribute9(this.getDecodedRequestParam(request, "ATTRIBUTE9"));
			
			requestCodeExtBean.setAttribute10(this.getDecodedRequestParam(request, "ATTRIBUTE10"));
			
		}
		return requestCodeExtBean;
	}
	
	
	private void constructReturnMap(ItemCodeExtLineBean reqCodeExtLineBean) {
		dataMap = new HashMap();
		if (reqCodeExtLineBean != null) {
			dataMap.put("ITEM_VALUE_ID", reqCodeExtLineBean.getExtItemValueId());
			dataMap.put("ITEM_TYPE_ID", reqCodeExtLineBean.getExtItemTypeId());
			dataMap.put("ITEM_VALUE", reqCodeExtLineBean.getExtItemValue());
			dataMap.put("DESCRIPTION", reqCodeExtLineBean.getDescription());
			dataMap.put("START_DATE", reqCodeExtLineBean.getStartDate());
			dataMap.put("END_DATE", reqCodeExtLineBean.getEndDate());
			dataMap.put("ATTRIBUTE1", reqCodeExtLineBean.getAttribute1());
			dataMap.put("ATTRIBUTE2", reqCodeExtLineBean.getAttribute2());
			dataMap.put("ATTRIBUTE3", reqCodeExtLineBean.getAttribute3());
			dataMap.put("ATTRIBUTE4", reqCodeExtLineBean.getAttribute4());
			dataMap.put("ATTRIBUTE5", reqCodeExtLineBean.getAttribute5());
			dataMap.put("ATTRIBUTE6", reqCodeExtLineBean.getAttribute6());
			dataMap.put("ATTRIBUTE7", reqCodeExtLineBean.getAttribute7());
			dataMap.put("ATTRIBUTE8", reqCodeExtLineBean.getAttribute8());
			dataMap.put("ATTRIBUTE9", reqCodeExtLineBean.getAttribute9());
			dataMap.put("ATTRIBUTE10", reqCodeExtLineBean.getAttribute10());
			
			
			dataMap.put("CREATION_DATE", reqCodeExtLineBean.getCreationDate());
			dataMap.put("CREATED_BY", reqCodeExtLineBean.getCreatedBy());
			dataMap.put("LAST_UPDATE_DATE", reqCodeExtLineBean.getLastUpdateDate());
			dataMap.put("LAST_UPDATED_BY", reqCodeExtLineBean.getLastUpdatedBy());
		}
	}
	
	private ItemLookUpLineBean constructLookUpLineBean(HttpServletRequest request, boolean isCreate)throws UnsupportedEncodingException {
		ItemLookUpLineBean requestLookUpBean = null;
		if (request != null) {
			requestLookUpBean = new ItemLookUpLineBean();
			//set LookUp line id
			String LookUpLineId = this.getDecodedRequestParam(request, "LOOKUP_VALUE_ID");
			if (!StringUtils.isBlank(LookUpLineId)) {
				requestLookUpBean.setLookUpValueId(Long.parseLong(LookUpLineId));
			}
			String LookUpHeaderId = null;
			if (!isCreate) {
				//set LookUp header id
				LookUpHeaderId = this.getDecodedRequestParam(request, "LOOKUP_TYPE_ID");
			}
			if (!StringUtils.isBlank(LookUpHeaderId)) {
				requestLookUpBean.setLookUpTypeId(Integer.parseInt(LookUpHeaderId));
			}
			//set itemValue
			requestLookUpBean.setLookUpValue(this.getDecodedRequestParam(request, "LOOKUP_VALUE"));
			
			//set line DESCRIPTION 
			requestLookUpBean.setDescription(this.getDecodedRequestParam(request, "DESCRIPTION"));
			
			//日期
			requestLookUpBean.setStartDate(new Date());
			//requestLookUpBean.setEndDate();
			
			requestLookUpBean.setAttribute1(this.getDecodedRequestParam(request, "ATTRIBUTE1"));
			
			requestLookUpBean.setAttribute2(this.getDecodedRequestParam(request, "ATTRIBUTE2"));
			
			requestLookUpBean.setAttribute3(this.getDecodedRequestParam(request, "ATTRIBUTE3"));
			
			requestLookUpBean.setAttribute4(this.getDecodedRequestParam(request, "ATTRIBUTE4"));
			
			requestLookUpBean.setAttribute5(this.getDecodedRequestParam(request, "ATTRIBUTE5"));
			
			requestLookUpBean.setAttribute6(this.getDecodedRequestParam(request, "ATTRIBUTE6"));
			
			requestLookUpBean.setAttribute7(this.getDecodedRequestParam(request, "ATTRIBUTE7"));
			
			requestLookUpBean.setAttribute8(this.getDecodedRequestParam(request, "ATTRIBUTE8"));
			
			requestLookUpBean.setAttribute9(this.getDecodedRequestParam(request, "ATTRIBUTE9"));
			
			requestLookUpBean.setAttribute10(this.getDecodedRequestParam(request, "ATTRIBUTE10"));
			
		}
		return requestLookUpBean;
	}
	private void constructReturnMap(ItemLookUpLineBean reqLookUpLineBean) {
		dataMap = new HashMap();
		if (reqLookUpLineBean != null) {
			dataMap.put("LOOKUP_VALUE_ID", reqLookUpLineBean.getLookUpValueId());
			dataMap.put("LOOKUP_TYPE_ID", reqLookUpLineBean.getLookUpTypeId());
			dataMap.put("LOOKUP_VALUE", reqLookUpLineBean.getLookUpValue());
			dataMap.put("DESCRIPTION", reqLookUpLineBean.getDescription());
			dataMap.put("START_DATE", reqLookUpLineBean.getStartDate());
			dataMap.put("END_DATE", reqLookUpLineBean.getEndDate());
			dataMap.put("ATTRIBUTE1", reqLookUpLineBean.getAttribute1());
			dataMap.put("ATTRIBUTE2", reqLookUpLineBean.getAttribute2());
			dataMap.put("ATTRIBUTE3", reqLookUpLineBean.getAttribute3());
			dataMap.put("ATTRIBUTE4", reqLookUpLineBean.getAttribute4());
			dataMap.put("ATTRIBUTE5", reqLookUpLineBean.getAttribute5());
			dataMap.put("ATTRIBUTE6", reqLookUpLineBean.getAttribute6());
			dataMap.put("ATTRIBUTE7", reqLookUpLineBean.getAttribute7());
			dataMap.put("ATTRIBUTE8", reqLookUpLineBean.getAttribute8());
			dataMap.put("ATTRIBUTE9", reqLookUpLineBean.getAttribute9());
			dataMap.put("ATTRIBUTE10", reqLookUpLineBean.getAttribute10());
			
			
			dataMap.put("CREATION_DATE", reqLookUpLineBean.getCreationDate());
			dataMap.put("CREATED_BY", reqLookUpLineBean.getCreatedBy());
			dataMap.put("LAST_UPDATE_DATE", reqLookUpLineBean.getLastUpdateDate());
			dataMap.put("LAST_UPDATED_BY", reqLookUpLineBean.getLastUpdatedBy());
		}
		
	}
	
	
	
	/* RowSet Header  related methods ---------Start */
	@RequestMapping(value = "/getItemRowSetList")
	@ResponseBody
	public Object getItemRowSetList(HttpServletRequest request,HttpServletResponse response) {
		// 参数
		ItemRowSetHeaderBean reqBean = new ItemRowSetHeaderBean();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			
			
			//handle the pagination case, initialize page related information
			this.initilizePagingInfo(request);
			//set query start index
			reqBean.setRowStartIndex(this.queryRowStartIndex);
			//set row count
			reqBean.setRowCount(this.rows);

			//get other parameter from request
			reqBean.setRowSetName(this.getDecodedRequestParam(request, "RowSetCodeOne"));
			reqBean.setDescription(this.getDecodedRequestParam(request, "RowSetNameOne"));


		} catch (UnsupportedEncodingException e1) {
			System.out.println(e1);
			e1.printStackTrace();
		}

		List<Map<String, Object>> list = null;
		try {
			list = rptSettingService.getRowSetHeaderList(reqBean);
		} catch (Exception e) {
			System.out.println("Error while get dimension:" + e);
		}
		JSONArray jsonlist = JsonUtil.fromObject(list);
		// put rows into map
		dataMap.put("rows", jsonlist);
		int count = 0;

		try {
			count = rptSettingService.getCountByRowSetHeaderBean(reqBean);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error while get dimension count:" + e);
		}

		// put total row number in the map
		dataMap.put("total", count);}else{
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
		return JsonUtil.fromObject(dataMap);
	}

	@RequestMapping(value = "/saveRowSetHeader")
	@ResponseBody
	public Object saveRowSetHeader(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		// get dimension bean from request
		ItemRowSetHeaderBean reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			reqBean = this.constructRowSetHeaderBean(request);
			if (!this.isInputValid(reqBean, true)) {
				isError = true;
				errorMsg = "输入信息有误，请检查数据格式，重新保存！";
			} else {
				String userId = (String) session.getAttribute(Constants.USER_KEY);
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());
				// save dimension into database
				int newKey = 0;
				try {
					newKey = rptSettingService.createNewRowSetHeader(reqBean);
				} catch (DuplicateKeyException de) {
					logger.error("saveRowSetHeader():" + de);
					errorMsg = "不能存在重复的报表行集名称，请重新输入再次提交";
				} catch (Exception e) {
					logger.error("saveRowSetHeader():" + e);
				}
				if (newKey == 0) {
					isError = true;
				}
				reqBean.setRowSetId(newKey);
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		}

		if (isError) {
			dataMap.put("isError", true);
			dataMap.put("msg", errorMsg);
		} else {
			constructReturnMap(reqBean, true);
		}}else{
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
		return JsonUtil.fromObject(dataMap);
	}

	@RequestMapping(value = "/updateRowSetHeader")
	@ResponseBody
	public Object updateRowSetHeader(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		// get dimension bean from request
		ItemRowSetHeaderBean reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			reqBean = this.constructRowSetHeaderBean(request);
			if (!this.isInputValid(reqBean, false)) {
				isError = true;
				errorMsg = "输入信息有误，请检查数据格式，重新保存！";
			} else {
				String userId = (String) session.getAttribute(Constants.USER_KEY);
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());
				// save dimension into database
				int updateCount = 0;
				try {
					updateCount = rptSettingService.updateRowSetHeader(reqBean);
				} catch (DuplicateKeyException de) {
					logger.error("saveRowSetHeader():" + de);
					errorMsg = "不能存在重复的报表行集名称，请重新输入再次提交";
				} catch (Exception e) {
					logger.error("saveRowSetHeader():" + e);
				}
				if (updateCount == 0) {
					isError = true;
				}
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		}

		if (isError) {
			dataMap.put("isError", true);
			dataMap.put("msg", errorMsg);
		} else {
			constructReturnMap(reqBean, false);
		}}else{
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
		return JsonUtil.fromObject(dataMap);
	}
	
	
	@RequestMapping(value = "/deleteRowSetHeader")
	@ResponseBody
	public Object deleteRowSetHeader(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		//get dimension bean from request
		ItemRowSetHeaderBean reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			String rowSetId = this.getDecodedRequestParam(request, "id");
			if (StringUtils.isBlank(rowSetId)) {
				isError = true;
				errorMsg = "没有选中需要删除的记录，请选择后再删除！";
			} else {
				reqBean = new ItemRowSetHeaderBean();
				reqBean.setRowSetId(Integer.parseInt(rowSetId));
				String userId = (String) session.getAttribute(Constants.USER_KEY);
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());
				//save dimension into database
				int delCount = 0;
				try {
					delCount = rptSettingService.deleteRowSetHeader(reqBean);
				} catch (Exception e) {
					logger.error("deleteRowSetHeader():" + e);
				}
				if (delCount == 0) {
					isError = true;
				}
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		} catch (Exception e) {
			isError = true;
		}
		
		if (isError) {
			dataMap.put("isError", true);
			dataMap.put("msg", errorMsg);
		} }else{
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
		return JsonUtil.fromObject(dataMap);
	}

	/* RowSet  header related methods ---------End */

	/* RowSet Line related actions -----------Start */
	@RequestMapping(value = "/showRowSetDimensionView")
	public String showRowSetDimensionView(HttpServletRequest request,HttpServletResponse response, Model model) {
		// get demensionValueId
		String headerId = null;
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			headerId = this.getDecodedRequestParam(request, "RowSetId");		
		} catch (UnsupportedEncodingException e) {
			logger.error("showRowSetDimensionView():" + e.toString());
		}
		if (!StringUtils.isBlank(headerId)) {
			ItemRowSetHeaderBean parentBean = null;
			try {
				parentBean = rptSettingService.getRowSetHeaderByByPrimaryKey(Integer.parseInt(headerId));
			} catch (Exception e) {
				logger.error("showRowSetDimensionView():" + e.toString());
			}
			if (parentBean != null) {
				model.addAttribute("RowSetHeaderBean", parentBean);
			}
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
		return "configuration/reportRowSetDimensionConfig";
	}


	@RequestMapping(value = "/getRowSetLineList")
	@ResponseBody
	public Object getRowSetLineList(HttpServletRequest request,HttpServletResponse response, HttpSession httpSession) {
		// parameter
		ItemRowSetLineBean lineBean = new ItemRowSetLineBean();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			 //handle the pagination case, initialize page related information
			this.initilizePagingInfo(request);
			//set query start index
			lineBean.setRowStartIndex(this.queryRowStartIndex);
			//set row count
			lineBean.setRowCount(this.rows);
			 lineBean.setRowName(this.getDecodedRequestParam(request,
			 "RowName"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		String headerId = null;
		try {
			headerId = this.getDecodedRequestParam(request, "RowSetHeaderId");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (!StringUtils.isBlank(headerId)) {
			lineBean.setRowSetId(Integer.parseInt(headerId));
			httpSession.removeAttribute(SESSION_KEY_ROWSET_HEADER);
			httpSession.setAttribute(SESSION_KEY_ROWSET_HEADER, headerId);
		}
		List<Map<String, Object>> list = null;
		try {
			list = rptSettingService.getRowSetLineList(lineBean);
		} catch (Exception e) {
			System.out.println("Error while get dimension:" + e);
		}
		int count = 0;
		try {
			count = rptSettingService.getCountByRowSetLineBean(lineBean);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error while get dimension count:" + e);
		}
		JSONArray jsonlist = JsonUtil.fromObject(list);
		// set rows in the map
		dataMap.put("rows", jsonlist);
		// set total in the map
		dataMap.put("total", count);
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
		return JsonUtil.fromObject(dataMap);
	}

	@RequestMapping(value = "/saveRowSetLine")
	@ResponseBody
	public Object saveRowSetLineRecord(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		// get dimension bean from request
		ItemRowSetLineBean reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			reqBean = this.constructRowSetLineBean(request, true);
			// try to get header it from session
			String sessionHeaderId = null;
			if (session.getAttribute(SESSION_KEY_ROWSET_HEADER) != null) {
				sessionHeaderId = (String) session.getAttribute(SESSION_KEY_ROWSET_HEADER);
			}
			if (!StringUtils.isBlank(sessionHeaderId)) {
				reqBean.setRowSetId(Integer.parseInt(sessionHeaderId));
			}
			if (!this.isInputValid(reqBean, true)) {
				isError = true;
				errorMsg = "输入信息有误，请检查数据格式，重新保存！";
			} else {
				String userId = (String) session.getAttribute(Constants.USER_KEY);
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());
				// save dimension into database
				int newKey = 0;
				try {
					newKey = rptSettingService.createNewRowSetLine(reqBean);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("Errors while save dimension: " + e);
				}
				if (newKey == 0) {
					isError = true;
				}
				reqBean.setRowId(newKey);
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		}

		if (isError) {
			dataMap.put("isError", true);
			dataMap.put("msg", errorMsg);
		} else {
			constructReturnMap(reqBean, true);
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
		return JsonUtil.fromObject(dataMap);
	}

	@RequestMapping(value = "/updateRowSetLine")
	@ResponseBody
	public Object updateRowSetLineRecord(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		// get dimension bean from request
		ItemRowSetLineBean reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			reqBean = this.constructRowSetLineBean(request, false);
			if (!this.isInputValid(reqBean, false)) {
				isError = true;
				errorMsg = "输入信息有误，请检查数据格式，重新保存！";
			} else {
				String userId = (String) session.getAttribute(Constants.USER_KEY);
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());
				// save dimension into database
				int updateCount = 0;
				try {
					updateCount = rptSettingService.updateRowSetLine(reqBean);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("Errors while save dimension: " + e);
				}
				if (updateCount == 0) {
					isError = true;
				}
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		}

		if (isError) {
			dataMap.put("isError", true);
			dataMap.put("msg", errorMsg);
		} else {
			constructReturnMap(reqBean, false);
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
		return JsonUtil.fromObject(dataMap);
	}
	
	@RequestMapping(value = "/deleteRowSetLine")
	@ResponseBody
	public Object deleteRowSetLine(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		//get dimension bean from request
		ItemRowSetLineBean reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			String rowId = this.getDecodedRequestParam(request, "id");
			if (StringUtils.isBlank(rowId)) {
				isError = true;
				errorMsg = "没有选中需要删除的记录，请选择后再删除！";
			} else {
				reqBean = new ItemRowSetLineBean();
				reqBean.setRowId(Long.parseLong(rowId));
				String userId = (String) session.getAttribute(Constants.USER_KEY);
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());
				//save dimension into database
				int delCount = 0;
				try {
					delCount = rptSettingService.deleteRowSetLine(reqBean);
				} catch (Exception e) {
					logger.error("deleteRowSetHeader():" + e);
				}
				if (delCount == 0) {
					isError = true;
				}
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		} catch (Exception e) {
			isError = true;
		}
		
		if (isError) {
			dataMap.put("isError", true);
			dataMap.put("msg", errorMsg);
		} }else{
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
		return JsonUtil.fromObject(dataMap);
	}
	
	
	/* RowSet Line related actions -----------End */
	
	/*RowSet related private methods---------------start*/
	private ItemRowSetHeaderBean constructRowSetHeaderBean(HttpServletRequest request)
			throws UnsupportedEncodingException {
		ItemRowSetHeaderBean requestBean = null;
		if (request != null) {
			requestBean = new ItemRowSetHeaderBean();
			// set RowSet header id
			String headerId = this.getDecodedRequestParam(request, "ROW_SET_ID");
			if (!StringUtils.isBlank(headerId)) {
				requestBean.setRowSetId(Integer.parseInt(headerId));
			}
			requestBean.setRowSetName(this.getDecodedRequestParam(request, "ROW_SET_NAME"));
			requestBean.setRuleCode(this.getDecodedRequestParam(request, "RULE_CODE"));
			requestBean.setExtItemType(this.getDecodedRequestParam(request, "EXT_ITEM_TYPE"));
			requestBean.setDescription(this.getDecodedRequestParam(request, "DESCRIPTION"));
		}
		return requestBean;
	}
	private ItemRowSetLineBean constructRowSetLineBean(HttpServletRequest request, boolean isCreate)
			throws UnsupportedEncodingException {
		ItemRowSetLineBean requestBean = null;
		if (request != null) {
			requestBean = new ItemRowSetLineBean();
			// set RowSet line id
			String RowSetLineId = this.getDecodedRequestParam(request, "ROW_ID");
			if (!StringUtils.isBlank(RowSetLineId)) {
				requestBean.setRowId(Long.parseLong(RowSetLineId));
			}
			String headerId = null;
			if (!isCreate) {
				// set RowSet header id
				headerId = this.getDecodedRequestParam(request, "ROW_SET_ID");
			}
			if (!StringUtils.isBlank(headerId)) {
				requestBean.setRowSetId(Integer.parseInt(headerId));
			}
			// set line number
			String lineSeq = this.getDecodedRequestParam(request, "LINE_NUM");
			if (!StringUtils.isBlank(lineSeq)) {
				requestBean.setLineNum(Integer.parseInt(lineSeq));
			}
			// set line segment
			String name = this.getDecodedRequestParam(request, "ROW_NAME");
			if (!StringUtils.isBlank(name)) {
				requestBean.setRowName(name);
			}
			String rowSeq = this.getDecodedRequestParam(request, "ROW_NUM");
			
			if (!StringUtils.isBlank(rowSeq)) {
				
				requestBean.setRowNum(Integer.parseInt(rowSeq));
			}
			
			
			String changeSign = this.getDecodedRequestParam(request, "CHANGE_SIGN");
			if (!StringUtils.isBlank(changeSign)) {
				requestBean.setChangeSign(changeSign);
			}
			
			String DisplayFlag = this.getDecodedRequestParam(request, "DISPLAY_FLAG");
			if (!StringUtils.isBlank(DisplayFlag)) {
				requestBean.setDisplayFlag(DisplayFlag);
			}
			requestBean.setExternalCode(this.getDecodedRequestParam(request, "EXTERNAL_CODE"));
			requestBean.setAttribute1(this.getDecodedRequestParam(request, "ATTRIBUTE1"));
			requestBean.setAttribute2(this.getDecodedRequestParam(request, "ATTRIBUTE2"));
			requestBean.setAttribute3(this.getDecodedRequestParam(request, "ATTRIBUTE3"));
			requestBean.setAttribute4(this.getDecodedRequestParam(request, "ATTRIBUTE4"));
			requestBean.setAttribute5(this.getDecodedRequestParam(request, "ATTRIBUTE5"));
			requestBean.setAttribute6(this.getDecodedRequestParam(request, "ATTRIBUTE6"));
			requestBean.setAttribute7(this.getDecodedRequestParam(request, "ATTRIBUTE7"));
			requestBean.setAttribute8(this.getDecodedRequestParam(request, "ATTRIBUTE8"));
			requestBean.setAttribute9(this.getDecodedRequestParam(request, "ATTRIBUTE9"));
			requestBean.setAttribute10(this.getDecodedRequestParam(request, "ATTRIBUTE10"));

		}
		return requestBean;
	}
	/*RowSet related private methods---------------end*/
	
	/* RowSet 	Calculation related actions -----------Start */
	@RequestMapping(value = "/showRowSetLineDimensionView")
	public String showRowSetLineDimensionView(HttpServletRequest request,HttpServletResponse response, Model model) {
		// get demensionValueId
		String lineId = null;
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			lineId = this.getDecodedRequestParam(request, "RowId");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (!StringUtils.isBlank(lineId)) {
			ItemRowSetLineBean parentBean = null;
			try {
				parentBean = rptSettingService.getRowSetLineByByPrimaryKey(Integer.parseInt(lineId));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (parentBean != null) {
				model.addAttribute("RowSetLineBean", parentBean);
			}
		}}else{
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
		return "configuration/reportRowCalculationDimensionConfig";
	}
	
	

	@RequestMapping(value = "/getRowCalculationList")
	@ResponseBody
	public Object getRowCalculationList(HttpServletRequest request,HttpServletResponse response, HttpSession httpSession) {
		// parameter
		ItemRowCalculationBean lineBean = new ItemRowCalculationBean();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {

			
			 //handle the pagination case, initialize page related information
			this.initilizePagingInfo(request);
			//set query start index
			lineBean.setRowStartIndex(this.queryRowStartIndex);
			//set row count
			lineBean.setRowCount(this.rows);
			
			
			
			 lineBean.setCalItemCode(this.getDecodedRequestParam(request,
			 "CalName"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		String headerId = null;
		try {
			headerId = this.getDecodedRequestParam(request, "RowSetLineId");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (!StringUtils.isBlank(headerId)) {
			lineBean.setRowId(Integer.parseInt(headerId));
			httpSession.removeAttribute(SESSION_KEY_ROWCAL_HEADER);
			httpSession.setAttribute(SESSION_KEY_ROWCAL_HEADER, headerId);
		}
		List<Map<String, Object>> list = null;
		try {
			list = rptSettingService.getRowCalculationList(lineBean);
		} catch (Exception e) {
			System.out.println("Error while get dimension:" + e);
		}
		int count = 0;
		try {
			count = rptSettingService.getCountByRowCalculationBean(lineBean);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error while get dimension count:" + e);
		}
		
		JSONArray jsonlist = JsonUtil.fromObject(list);
		// set rows in the map
		dataMap.put("rows", jsonlist);
		// set total in the map
		dataMap.put("total", count);}else{
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
		return JsonUtil.fromObject(dataMap);
	}
	
	
	@RequestMapping(value = "/saveRowCalculation")
	@ResponseBody
	public Object saveRowCalculationRecord(HttpServletRequest request,HttpServletResponse response
, HttpSession session) {
		// get dimension bean from request
		ItemRowCalculationBean reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			reqBean = this.constructRowCalculationBean(request, true);
			// try to get header it from session
			String sessionHeaderId = null;
			if (session.getAttribute(SESSION_KEY_ROWCAL_HEADER) != null) {
				sessionHeaderId = (String) session.getAttribute(SESSION_KEY_ROWCAL_HEADER);
			}
			if (!StringUtils.isBlank(sessionHeaderId)) {
				reqBean.setRowId(Integer.parseInt(sessionHeaderId));
			}
			if (!this.isInputValid(reqBean, true)) {
				isError = true;
				errorMsg = "输入信息有误，请检查数据格式，重新保存！";
			} else {
				String userId = (String) session.getAttribute(Constants.USER_KEY);
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());
				// save dimension into database
				int newKey = 0;
				try {
					newKey = rptSettingService.createNewRowCalculation(reqBean);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("Errors while save dimension: " + e);
				}
				if (newKey == 0) {
					isError = true;
				}
				reqBean.setRowCalId(newKey);
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		}

		if (isError) {
			dataMap.put("isError", true);
			dataMap.put("msg", errorMsg);
		} else {
			constructReturnMap(reqBean, true);
		}}else{
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
		return JsonUtil.fromObject(dataMap);
	}
	@RequestMapping(value = "/updateRowCalculation")
	@ResponseBody
	public Object updateRowCalculationRecord(HttpServletRequest request,HttpServletResponse response
, HttpSession session) {
		// get dimension bean from request
		ItemRowCalculationBean reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();

if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			reqBean = this.constructRowCalculationBean(request, false);
			if (!this.isInputValid(reqBean, false)) {
				isError = true;
				errorMsg = "输入信息有误，请检查数据格式，重新保存！";
			} else {
				String userId = (String) session.getAttribute(Constants.USER_KEY);
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());
				// save dimension into database
				int updateCount = 0;
				try {
					updateCount = rptSettingService.updateRowCalculation(reqBean);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("Errors while save dimension: " + e);
				}
				if (updateCount == 0) {
					isError = true;
				}
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		}

		if (isError) {
			dataMap.put("isError", true);
			dataMap.put("msg", errorMsg);
		} else {
			constructReturnMap(reqBean, false);
		}}else{
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
		return JsonUtil.fromObject(dataMap);
	}

	
	@RequestMapping(value = "/deleteRowCalculation")
	@ResponseBody
	public Object deleteRowCalculation(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		//get dimension bean from request
		ItemRowCalculationBean reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			String rowCalId = this.getDecodedRequestParam(request, "id");
			if (StringUtils.isBlank(rowCalId)) {
				isError = true;
				errorMsg = "没有选中需要删除的记录，请选择后再删除！";
			} else {
				reqBean = new ItemRowCalculationBean();
				reqBean.setRowCalId(Long.parseLong(rowCalId));
				String userId = (String) session.getAttribute(Constants.USER_KEY);
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());
				//save dimension into database
				int delCount = 0;
				try {
					delCount = rptSettingService.deleteRowCalculation(reqBean);
				} catch (Exception e) {
					logger.error("deleteRowCalculation():" + e);
				}
				if (delCount == 0) {
					isError = true;
				}
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		} catch (Exception e) {
			isError = true;
		}
		
		if (isError) {
			dataMap.put("isError", true);
			dataMap.put("msg", errorMsg);
		} }else{
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
		return JsonUtil.fromObject(dataMap);
	}
	
	/* RowSet Calculation related actions -----------End */
	/*RowSet related private methods---------------start*/



	private ItemRowCalculationBean constructRowCalculationBean(HttpServletRequest request, boolean isCreate)
			throws UnsupportedEncodingException {
		ItemRowCalculationBean requestBean = null;
		if (request != null) {
			requestBean = new ItemRowCalculationBean();
			// set RowSet line id
			
			String RowCalculationId = this.getDecodedRequestParam(request, "ROW_CAL_ID");
			if (!StringUtils.isBlank(RowCalculationId)) {
				requestBean.setRowCalId(Long.parseLong(RowCalculationId));
			}
			String headerId = null;
			if (!isCreate) {
				// set RowSet header id
				headerId = this.getDecodedRequestParam(request, "ROW_ID");
			}
			if (!StringUtils.isBlank(headerId)) {
				requestBean.setRowId(Integer.parseInt(headerId));
			}
			// set line number
			String lineSeq = this.getDecodedRequestParam(request, "ROW_CAL_NUM");
			if (!StringUtils.isBlank(lineSeq)) {
				requestBean.setRowCalNum(Integer.parseInt(lineSeq));
			}
			// set line segment
			String changeSign = this.getDecodedRequestParam(request, "SIGN");  
			if (!StringUtils.isBlank(changeSign)) {
				if (Constants.OPERATOR_PLUS_CODE.equals(changeSign)) {
					requestBean.setSign(Constants.OPERATOR_PLUS_VALUE);
				} else if (Constants.OPERATOR_MINUS_CODE.equals(changeSign)) {
					requestBean.setSign(Constants.OPERATOR_MINUS_VALUE);
				} else {
					requestBean.setSign(changeSign);
				}
			}
			String CalItemCode = this.getDecodedRequestParam(request, "CAL_ITEM_CODE");
			if (!StringUtils.isBlank(CalItemCode)) {
				requestBean.setCalItemCode(CalItemCode);
			}


		}
		return requestBean;
	}
	
	private String preProcessLowHigh(String lowHighVal) {
		String result = "";
		if (!StringUtils.isBlank(lowHighVal)) {
			int pos = lowHighVal.lastIndexOf(",");
			if (pos != -1) {
				result = lowHighVal.substring(pos + 1);
			}
		}
		return result;
		
	}
	
	private boolean isLowHighValEmpty(String lowHighVal) {
		boolean isEmp = false;
		if (StringUtils.isBlank(lowHighVal)) {
			isEmp = true;
		}
		if (!isEmp && StringUtils.isBlank(lowHighVal.replace("," , ""))) {
			isEmp = true;
		}
		return isEmp;
		
		
	}
	/*RowSet related private methods---------------end*/
	
}
