package com.hausontech.hrs.web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hausontech.hrs.api.allocationManager.service.IAllocationRulesSettingService;
import com.hausontech.hrs.api.constants.Constants;
import com.hausontech.hrs.bean.AuditBean2;
import com.hausontech.hrs.bean.allocationManager.AllocDriverAccountBean;
import com.hausontech.hrs.bean.allocationManager.AllocRuleBean;
import com.hausontech.hrs.bean.allocationManager.AllocRulesGroupHaeaderBean;
import com.hausontech.hrs.bean.allocationManager.AllocRulesGroupLineBean;
import com.hausontech.hrs.bean.allocationManager.AllocSourceAccountBean;
import com.hausontech.hrs.bean.allocationManager.AllocSourceBean;
import com.hausontech.hrs.bean.allocationManager.AllocTargetAccountBean;
import com.hausontech.hrs.bean.allocationManager.DimFilterHeaderBean;
import com.hausontech.hrs.bean.allocationManager.DimFilterLineBean;
import com.hausontech.hrs.bean.allocationManager.DriverStaticHeader;
import com.hausontech.hrs.bean.allocationManager.DriverStaticLine;
import com.hausontech.hrs.bean.allocationManager.model.AllocDriverRecord;
import com.hausontech.hrs.bean.allocationManager.model.AllocTargetRecord;
import com.hausontech.hrs.bean.allocationManager.model.RoleRecord;
import com.hausontech.hrs.bean.dimensionManager.DimensionValueBean;
import com.hausontech.hrs.bean.userManager.RoleUserMapRecord;
import com.hausontech.hrs.exceptions.DataReferenceException;
import com.hausontech.hrs.utils.Comp;
import com.hausontech.hrs.utils.JsonUtil;
import com.hausontech.hrs.utils.Loap;
import com.hausontech.hrs.utils.StringUtil;
import com.hausontech.hrs.utils.Tan;
import com.hausontech.hrs.utils.ThirdLevelType;

import net.sf.json.JSONArray;

@Controller
@RequestMapping({ "/dimFilterManage" })
public class DimFilterManageController extends BaseController {
	private static final String SESSION_KEY_DEMINSIONFILTERHEADERID = "diminsionFilterHeaderId";
	private static final String SESSION_KEY_DEMINSIONFILTERHEADERNAME = "diminsionFilterHeaderName";
	private static final String SESSION_KEY_FILTERHEADERDIMSIONSEGMENT = "filterDeminsionSegment";
	private static final String SESSION_KEY_DRIVERSTATICHEADERID = "driverStaticHeaderId";
	private static final String SESSION_KEY_DRIVERSTATICHEADERNAME = "driverStaticHeaderName";
	private static final String SESSION_KEY_DRIVERSTATICDIMSIONSEGMENT = "driverStaticDeminsionSegment";
	private static final String SESSION_KEY_GROUPHEADERID = "groupHeaderId";
	private static final String SESSION_KEY_GROUPNAME = "groupName";
	private static final String SESSION_KEY_RULE_ID = "ruleId";
	private static final String SESSION_KEY_RULE_NAME = "ruleName";
	private static final String SESSION_KEY_SOURCEID = "sourceId";
	private static final String SESSION_KEY_TARGETID = "targetId";
	private static final String SESSION_KEY_TARGETTYPE = "targetType";
	private static final String SESSION_KEY_DRIVERID = "driverId";
	private static final String SESSION_KEY_DRIVERTYPE = "driverType";
	private static final String SESSION_KEY_SOURCETYPE = "sourceType";
	private static final String SESSION_KEY_STATIC_DIMENSION_SEGMENT = "staticDimension";
	
	
	

	private static Logger logger = LoggerFactory.getLogger(DimFilterManageController.class);
	@Resource
	private IAllocationRulesSettingService allocationRulesSettingService;

	public IAllocationRulesSettingService getAllocationRulesSettingService() {
		return this.allocationRulesSettingService;
	}

	public void setAllocationRulesSettingService(IAllocationRulesSettingService allocationRulesSettingService) {
		this.allocationRulesSettingService = allocationRulesSettingService;
	}

	@RequestMapping({ "/findDimFilterHeader" })
	@ResponseBody
	public Object findDimFilterHeader(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		DimFilterHeaderBean dfh = new DimFilterHeaderBean();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
	
		try {
			initilizePagingInfo(request);
			dfh.setRowStartIndex(this.queryRowStartIndex);
			dfh.setRowCount(this.rows);
			dfh.setDimensionSegment(request.getParameter("deminstionName"));
			dfh.setFilterHeaderName(request.getParameter("deminsionFilterName"));
		} catch (Exception e) {
			logger.error("Error while get dimension filter header:" + e);
		}
		List<Map<String, Object>> list = null;
		try {
			list = this.allocationRulesSettingService.findDimFilterHeader(dfh);
		} catch (Exception e) {
			logger.error("Error while get dimension filter header:" + e);
		}
		JSONArray jsonlist = JsonUtil.fromObject(list);
		this.dataMap.put("rows", jsonlist);
		int count = 0;
		try {
			count = this.allocationRulesSettingService.getCountFilterHeader(dfh);
		} catch (Exception e) {
			logger.error("Error while get dimension count:" + e);
		}
		this.dataMap.put("total", Integer.valueOf(count));
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
		return JsonUtil.fromObject(this.dataMap);
	}

	@RequestMapping({ "/saveDimFilterHeader" })
	@ResponseBody
	public Object saveDimFilterHeader(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		DimFilterHeaderBean dimFilterHeaderBean = null;
		boolean isError = false;
		String errorMsg = "保存信息失败：请检查输入数据，重新提交！";

		this.dataMap = new HashMap();

		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		
		try {
			dimFilterHeaderBean = (DimFilterHeaderBean) constructDimensionBean(request, new DimFilterHeaderBean(),
					true);
			if (!isDimFilterInputValid(dimFilterHeaderBean, true)) {
				isError = true;
				errorMsg = "输入信息有误，请检查数据格式，重新保存！";
			} else {
				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
					    .getAuthentication()
					    .getPrincipal();

				String userId = userDetails.getUsername();
				//String userId = (String) session.getAttribute("HRS_AUTH_USER");
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				dimFilterHeaderBean.setCreatedBy(userId);
				dimFilterHeaderBean.setCreationDate(new Date());
				dimFilterHeaderBean.setLastUpdatedBy(userId);
				dimFilterHeaderBean.setLastUpdateDate(new Date());
				try {
					this.allocationRulesSettingService.createNewFilterHeader(dimFilterHeaderBean);
				} catch (Exception e) {
					logger.error("error occur create a new filter header" + e);
				}
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		}
		this.dataMap.put("isError", Boolean.valueOf(true));
		this.dataMap.put("msg", errorMsg);

		constructReturnMap(dimFilterHeaderBean);
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
		return JsonUtil.fromObject(this.dataMap);
	}

	private void constructReturnMap(AuditBean2 auditBean) {
		this.dataMap = new HashMap();
		if ((auditBean instanceof DimFilterHeaderBean)) {
			DimFilterHeaderBean dimFilterHeaderBean = (DimFilterHeaderBean) auditBean;
			this.dataMap.put("FILTER_HEADER_ID", Integer.valueOf(dimFilterHeaderBean.getFilterHeaderId()));
			this.dataMap.put("FILTER_HEADER_NAME", dimFilterHeaderBean.getFilterHeaderName());
			this.dataMap.put("DIMENSION_SEGMENT", dimFilterHeaderBean.getDimensionSegment());
			this.dataMap.put("TYPE", dimFilterHeaderBean.getType());
			this.dataMap.put("DESCRIPTION", dimFilterHeaderBean.getDescription());
			this.dataMap.put("CREATED_BY", dimFilterHeaderBean.getCreatedBy());
			this.dataMap.put("CREATED_DATE", dimFilterHeaderBean.getCreationDate());
			this.dataMap.put("LAST_UPDATED_BY", dimFilterHeaderBean.getLastUpdatedBy());
			this.dataMap.put("LAST_UPDATED_DATE", dimFilterHeaderBean.getLastUpdateDate());
		} else if ((auditBean instanceof DimFilterLineBean)) {
			DimFilterLineBean dimFilterLineBean = (DimFilterLineBean) auditBean;
			this.dataMap.put("FILTER_LINE_ID", Integer.valueOf(dimFilterLineBean.getFilterLineId()));
			this.dataMap.put("FILTER_HEADER_ID", Integer.valueOf(dimFilterLineBean.getFilterHeaderId()));
			this.dataMap.put("VALUE_LOW", dimFilterLineBean.getValueLow());
			this.dataMap.put("VALUE_HIGH", dimFilterLineBean.getValueHigh());
			this.dataMap.put("INC_EXC_INDICATOR", dimFilterLineBean.getIncExcIndicator());
			this.dataMap.put("DESCRIPTION", dimFilterLineBean.getDescription());
			this.dataMap.put("CREATED_BY", dimFilterLineBean.getCreatedBy());
			this.dataMap.put("CREATED_DATE", dimFilterLineBean.getCreationDate());
			this.dataMap.put("LAST_UPDATED_BY", dimFilterLineBean.getLastUpdatedBy());
			this.dataMap.put("LAST_UPDATED_DATE", dimFilterLineBean.getLastUpdateDate());
		}
	}

	@RequestMapping({ "/getFilteredSegment" })
	@ResponseBody
	public Object getFilteredSegment(HttpServletRequest request,HttpServletResponse response) {
		
		List<Map<String, Object>> demensionlist = new ArrayList();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
			

		demensionlist = this.allocationRulesSettingService.getSegmentList("filter");
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
		return demensionlist;
	}
	@RequestMapping({ "/getFilterHeader" })
	@ResponseBody
	public Object getFilterHeader(HttpServletRequest request,HttpServletResponse response) {
		boolean isError = false;
		String errorMsg = "";
		List<Map<String, Object>> filterHeaderlist = new ArrayList();
		DimFilterHeaderBean dimFilterHeaderBean=new DimFilterHeaderBean();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
			

		try {
			dimFilterHeaderBean.setDimensionSegment(getDecodedRequestParam(request, "segment").trim());
		} catch (UnsupportedEncodingException e) {
			logger.error("getFilterHeader():" + e);
		}
		filterHeaderlist = this.allocationRulesSettingService.findDimFilterHeader(dimFilterHeaderBean);
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
		return filterHeaderlist;
	}

	@RequestMapping({ "/getSegmentList" })
	@ResponseBody
	public Object getSegmentList(HttpServletRequest request,HttpServletResponse response) {
		List<Map<String, Object>> demensionlist = new ArrayList();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
	
		demensionlist = this.allocationRulesSettingService.getSegmentList("all");
		Map<String, Object> additonalMap = new HashMap<String, Object>();
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
		return demensionlist;
	}
	
	@RequestMapping({ "/getStaticList" })
	@ResponseBody
	public Object getStaticList(HttpServletRequest request,HttpServletResponse response) {
		List<Map<String, Object>> StaticList = new ArrayList();

		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
	
		StaticList = this.allocationRulesSettingService.getStaticList();
		Map<String, Object> additonalMap = new HashMap<String, Object>();
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
		return StaticList;
	}

	@RequestMapping({ "/getRuleList" })
	@ResponseBody
	public Object getRuleList(HttpServletRequest request,HttpServletResponse response) {
		List<Map<String, Object>> RuleList = new ArrayList();

		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
	
		RuleList = this.allocationRulesSettingService.getRuleList();
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
		return RuleList;
	}

	
	
	@RequestMapping({ "/showLineValueView" })
	public String showLineValueView(HttpServletRequest request,HttpServletResponse response, Model model, HttpSession httpSession) {
		String headerid = null;
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		
		try {
			headerid = getDecodedRequestParam(request, "headerId");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (!StringUtils.isBlank(headerid)) {
			DimFilterHeaderBean parentBean = null;
			try {
				parentBean = this.allocationRulesSettingService.getDimFilterHeaderBeanById(Integer.parseInt(headerid));
			} catch (NumberFormatException ne) {
				ne.printStackTrace();
			} catch (Exception localException) {
			}
			if (parentBean != null) {
				model.addAttribute("dimensionFilterHeaderBean", parentBean);
			}
			httpSession.removeAttribute(SESSION_KEY_DEMINSIONFILTERHEADERID);
			httpSession.setAttribute(SESSION_KEY_DEMINSIONFILTERHEADERID,
					Integer.valueOf(parentBean.getFilterHeaderId()));
			httpSession.removeAttribute(SESSION_KEY_DEMINSIONFILTERHEADERNAME);
			httpSession.setAttribute(SESSION_KEY_DEMINSIONFILTERHEADERNAME, parentBean.getFilterHeaderName());
			httpSession.removeAttribute(SESSION_KEY_FILTERHEADERDIMSIONSEGMENT);
			httpSession.setAttribute(SESSION_KEY_FILTERHEADERDIMSIONSEGMENT, parentBean.getDimensionSegment());
			request.setAttribute("dimensionFilterHeaderBean", Integer.valueOf(parentBean.getFilterHeaderId()));
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
		return "configuration/AllocationRulesSetting/dimensionFilterLineConfiguration";
	}

	@RequestMapping({ "/findFilterLineValues" })
	@ResponseBody
	public Object findFilterLineValues(HttpServletRequest request,HttpServletResponse response, HttpSession httpSession) {
		DimFilterLineBean dimFilterLineBean = new DimFilterLineBean();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
			
		try {
			initilizePagingInfo(request);

			dimFilterLineBean.setRowStartIndex(this.queryRowStartIndex);

			dimFilterLineBean.setRowCount(this.rows);
		} catch (UnsupportedEncodingException e1) {
			logger.error("findFilterLineValues():" + e1);
		}
		int headerId = ((Integer) httpSession.getAttribute("diminsionFilterHeaderId")).intValue();
		dimFilterLineBean.setFilterHeaderId(headerId);

		List<Map<String, Object>> list = null;
		try {
			list = this.allocationRulesSettingService.getFilterLineValuesByCondition(dimFilterLineBean);
		} catch (Exception e) {
			logger.error("getFilterLineValuesByCondition():" + e);
		}
		int count = 0;
		try {
			count = this.allocationRulesSettingService.getCountByFilterLineValuesByCondition(dimFilterLineBean);
		} catch (Exception e) {
			logger.error("getCountByFilterLineValuesByCondition():" + e);
		}
		JSONArray jsonlist = JsonUtil.fromObject(list);

		this.dataMap.put("rows", jsonlist);
		this.dataMap.put("total", Integer.valueOf(count));
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
		return JsonUtil.fromObject(this.dataMap);
	}

	@RequestMapping({ "/updateDimFilterHeader" })
	@ResponseBody
	public Object updateDimFilterHeader(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		DimFilterHeaderBean reqBean = null;
		boolean isError = false;
		String errorMsg = "保存信息失败：请检查输入数据，重新提交！";
		this.dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
				
		try {
			reqBean = (DimFilterHeaderBean) constructDimensionBean(request, new DimFilterHeaderBean(), false);
			if (!isDimFilterInputValid(reqBean, false)) {
				isError = true;
				errorMsg = "输入信息有误，请检查数据格式，重新保存！";
			} else {
				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
					    .getAuthentication()
					    .getPrincipal();

				String userId = userDetails.getUsername();
				//String userId = (String) session.getAttribute("HRS_AUTH_USER");
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());

				int updateCount = 0;
				try {
					updateCount = this.allocationRulesSettingService.updateDimFilterHeader(reqBean);
				} catch (Exception e) {
					logger.error("Errors while save dimension:" + e);
				}
				if (updateCount == 0) {
					isError = true;
				}
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		}
		if (isError) {
			this.dataMap.put("isError", Boolean.valueOf(true));
			this.dataMap.put("msg", errorMsg);
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
		return JsonUtil.fromObject(this.dataMap);
	}

	@RequestMapping({ "/updateFilterLineValues" })
	@ResponseBody
	public Object updateFilterLineValues(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		DimFilterLineBean reqBean = null;
		boolean isError = false;
		String errorMsg = "保存信息失败：请检查输入数据，重新提交！";
		this.dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
			

		try {
			reqBean = (DimFilterLineBean) constructDimensionBean(request, new DimFilterLineBean(), false);
			int headerId = 0;
			if (session.getAttribute(SESSION_KEY_DEMINSIONFILTERHEADERID) != null) {
				headerId = Integer.parseInt(session.getAttribute(SESSION_KEY_DEMINSIONFILTERHEADERID).toString());
			}
			// String a=request.getParameter("headerId").toString();
			reqBean.setFilterHeaderId(headerId);
			if (!isDimFilterInputValid(reqBean, false)) {
				isError = true;
				errorMsg = "输入信息有误，请检查数据格式，重新保存！";
			} else {
				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
					    .getAuthentication()
					    .getPrincipal();

				String userId = userDetails.getUsername();
				//String userId = (String) session.getAttribute("HRS_AUTH_USER");
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());

				int updateCount = 0;
				try {
					updateCount = this.allocationRulesSettingService.updateFilterLineValues(reqBean);
				} catch (Exception e) {
					logger.error("Errors while updateFilterLineValues:" + e);
				}
				if (updateCount == 0) {
					isError = true;
				}
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		}
		if (isError) {
			this.dataMap.put("isError", Boolean.valueOf(true));
			this.dataMap.put("msg", errorMsg);
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
		return JsonUtil.fromObject(this.dataMap);
	}

	@RequestMapping({ "/destroyFilterLineValues" })
	@ResponseBody
	public Object destroyFilterLineValues(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		// get dimension bean from request
		DimFilterLineBean reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		
		try {
			String ruleLineId = this.getDecodedRequestParam(request, "id");
			if (StringUtils.isBlank(ruleLineId)) {
				isError = true;
				errorMsg = "没有选中需要删除的记录，请选择后再删除！";
			} else {
				reqBean = new DimFilterLineBean();
				reqBean.setFilterLineId(Integer.parseInt(ruleLineId));
				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
					    .getAuthentication()
					    .getPrincipal();

				String userId = userDetails.getUsername();
				//String userId = (String) session.getAttribute(Constants.USER_KEY);
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());
				// save dimension into database
				int delCount = 0;
				try {
					delCount = allocationRulesSettingService.destroyFilterLineValues(reqBean);
				} catch (DataReferenceException de) {
					logger.error("Reference record found while delete dimension value:" + de);
					errorMsg = "错误发生于删除FilterLineValues";
					isError = true;
				} catch (Exception e) {
					logger.error("Error happen while delete dimension value:" + e);
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

	@RequestMapping({ "/getFilterLineValueDetail" })
	@ResponseBody
	private Object getFilterLineValueDetail(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		DimensionValueBean dimensionValueBean = new DimensionValueBean();

		List<DimensionValueBean> list = null;
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){

		try {
			dimensionValueBean.setAttribute1(getDecodedRequestParam(request, "headid"));
			dimensionValueBean.setDimensionValue(getDecodedRequestParam(request, "valuedata"));

			list = this.allocationRulesSettingService.getFilterLineValueDetail(dimensionValueBean);
		} catch (Exception e) {
			logger.error("Errors while save dimension value list:" + e);
		}
		List<Map<String, String>> listback = null;
		if ((list != null) && (list.size() > 0)) {
			DimensionValueBean result = (DimensionValueBean) list.get(0);
			this.dataMap = new HashMap();
			this.dataMap.put("DIMENSION_ID", Long.valueOf(result.getDimensionId()));
			this.dataMap.put("DIM_VALUE", result.getDimensionValue());
			this.dataMap.put("DESCRIPTION", result.getDescription());
			listback.add(this.dataMap);
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
		return JsonUtil.fromObject(this.dataMap);
	}

	@RequestMapping({ "/createFilterLineValues" })
	@ResponseBody
	public Object createFilterLineValues(DimFilterLineBean obj,HttpServletResponse response, HttpServletRequest request, HttpSession session) {
		DimFilterLineBean reqBean = null;
		boolean isError = false;
		String errorMsg = "保存信息失败：请检查输入数据，重新提交！";
		this.dataMap = new HashMap();

		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
	
		try {
			reqBean = (DimFilterLineBean) constructDimensionBean(request, new DimFilterLineBean(), true);

			int headerId = 0;
			if (session.getAttribute(SESSION_KEY_DEMINSIONFILTERHEADERID) != null) {
				headerId = Integer.parseInt(session.getAttribute(SESSION_KEY_DEMINSIONFILTERHEADERID).toString());
			}
			reqBean.setFilterHeaderId(headerId);
			if (!isDimFilterInputValid(reqBean, true)) {
				isError = true;
				errorMsg = "输入信息有误，请检查数据格式，重新保存！";
			} else {
				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
					    .getAuthentication()
					    .getPrincipal();

				String userId = userDetails.getUsername();
				//String userId = (String) session.getAttribute("HRS_AUTH_USER");
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());

				int newKey = 0;
				try {
					newKey = this.allocationRulesSettingService.createFilterLineValues(reqBean);
				} catch (DuplicateKeyException de) {
					logger.error("createFilterLineValues():" + de);
					errorMsg = "请重新输入再次提交";
				} catch (Exception e) {
					logger.error("Errors while createFilterLineValues:" + e);
				}
				if (newKey == 0) {
					isError = true;
				}
				reqBean.setFilterLineId(newKey);
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		}
		if (isError) {
			this.dataMap.put("isError", Boolean.valueOf(true));
			this.dataMap.put("msg", errorMsg);
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
		return JsonUtil.fromObject(this.dataMap);
	}

	// XXX mark for DriverStaticHeader
	/*
	 * 静态因子头
	 *
	 */

	@RequestMapping({ "/findDriverStaticHeader" })
	@ResponseBody
	public Object findDriverStaticHeader(HttpServletRequest request,HttpServletResponse response
, HttpSession session) {
		DriverStaticHeader dsh = new DriverStaticHeader();

		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		
		try {
			 initilizePagingInfo(request);
			dsh.setRowStartIndex(this.queryRowStartIndex);
			dsh.setRowCount(this.rows);
			dsh.setDriverCode(request.getParameter("driverCode"));
			dsh.setDescription(request.getParameter("description"));
		} catch (Exception e) {
			logger.error("Error while get DriverStaticHeader:" + e);
		}
		List<Map<String, Object>> list = null;
		try {
			list = this.allocationRulesSettingService.findDriverStaticHeader(dsh);
		} catch (Exception e) {
			logger.error("Error while get dimension filter header:" + e);
		}
		JSONArray jsonlist = JsonUtil.fromObject(list);
		this.dataMap.put("rows", jsonlist);
		int count = 0;
		try {
			count = this.allocationRulesSettingService.getCountDriverStaticHeader(dsh);
		} catch (Exception e) {
			logger.error("Error while get dimension count:" + e);
		}
		this.dataMap.put("total", Integer.valueOf(count));
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
		return JsonUtil.fromObject(this.dataMap);
	}

	@RequestMapping({ "/saveDriverStaticHeader" })
	@ResponseBody
	public Object saveDriverStaticHeader(HttpServletRequest request,HttpServletResponse response
, HttpSession session) {
		DriverStaticHeader driverStaticHeader = null;
		boolean isError = false;
		String errorMsg = "保存信息失败：请检查输入数据，重新提交！";

		this.dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
			
		try {
			driverStaticHeader = (DriverStaticHeader) constructDimensionBean(request, new DriverStaticHeader(), true);
			if (!isDimFilterInputValid(driverStaticHeader, true)) {
				isError = true;
				errorMsg = "输入信息有误，请检查数据格式，重新保存！";
			} else {
				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
					    .getAuthentication()
					    .getPrincipal();

				String userId = userDetails.getUsername();
				//String userId = (String) session.getAttribute("HRS_AUTH_USER");
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				driverStaticHeader.setCreatedBy(userId);
				driverStaticHeader.setCreationDate(new Date());
				driverStaticHeader.setLastUpdatedBy(userId);
				driverStaticHeader.setLastUpdateDate(new Date());
				try {
					this.allocationRulesSettingService.createNewDriverStaticHeader(driverStaticHeader);
				} catch (Exception e) {
					logger.error("error occur create a new DriverStaticHeader" + e);
				}
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		}
		this.dataMap.put("isError", Boolean.valueOf(true));
		this.dataMap.put("msg", errorMsg);

		constructReturnMap(driverStaticHeader);
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
		return JsonUtil.fromObject(this.dataMap);
	}

	@RequestMapping({ "/updateDriverStaticHeader" })
	@ResponseBody
	public Object updateDriverStaticHeader(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		DriverStaticHeader reqBean = null;
		boolean isError = false;
		String errorMsg = "保存信息失败：请检查输入数据，重新提交！";
		this.dataMap = new HashMap();

		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			reqBean = (DriverStaticHeader) constructDimensionBean(request, new DriverStaticHeader(), false);
			if (!isDimFilterInputValid(reqBean, false)) {
				isError = true;
				errorMsg = "输入信息有误，请检查数据格式，重新保存！";
			} else {
				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
					    .getAuthentication()
					    .getPrincipal();

				String userId = userDetails.getUsername();
				//String userId = (String) session.getAttribute("HRS_AUTH_USER");
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());

				int updateCount = 0;
				try {
					updateCount = this.allocationRulesSettingService.updateDriverStaticHeader(reqBean);
				} catch (Exception e) {
					logger.error("Errors while save dimension:" + e);
				}
				if (updateCount == 0) {
					isError = true;
				}
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		}
		if (isError) {
			this.dataMap.put("isError", Boolean.valueOf(true));
			this.dataMap.put("msg", errorMsg);
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
		return JsonUtil.fromObject(this.dataMap);
	}

	@RequestMapping({ "/destroyDriverStaticHeader" })
	@ResponseBody
	public Object destroyDriverStaticHeader(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		// get dimension bean from request
		DriverStaticHeader reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			String ruleLineId = this.getDecodedRequestParam(request, "id");
			if (StringUtils.isBlank(ruleLineId)) {
				isError = true;
				errorMsg = "没有选中需要删除的记录，请选择后再删除！";
			} else {
				reqBean = new DriverStaticHeader();
				reqBean.setStaticHeaderId(Integer.parseInt(ruleLineId));
				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
					    .getAuthentication()
					    .getPrincipal();

				String userId = userDetails.getUsername();
				//String userId = (String) session.getAttribute(Constants.USER_KEY);
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());
				// save dimension into database
				int delCount = 0;
				try {
					delCount = allocationRulesSettingService.deleteDriverStaticHeader(reqBean);
				} catch (DataReferenceException de) {
					logger.error("Reference record found while delete dimension value:" + de);
					errorMsg = "错误发生于删除DriverStaticHeader";
					isError = true;
				} catch (Exception e) {
					logger.error("Error happen while delete dimension value:" + e);
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

	// XXX DriverStaticLine
	/**
	 * 静态因子行
	 * 
	 */
	@RequestMapping({ "/showDriverStaticLineValueView" })
	public String showDriverStaticLineValueView(HttpServletRequest request,HttpServletResponse response
, Model model, HttpSession httpSession) {
		String headerid = null;
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			headerid = getDecodedRequestParam(request, "headerId");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (!StringUtils.isBlank(headerid)) {
			DriverStaticHeader parentBean = null;
			try {
				parentBean = this.allocationRulesSettingService.getDriverStaticHeaderById(Integer.parseInt(headerid));
			} catch (NumberFormatException ne) {
				ne.printStackTrace();
			} catch (Exception localException) {
			}
			if (parentBean != null) {
				model.addAttribute("driverStaticHeader", parentBean);
			}
			/*
			 * private static final String SESSION_KEY_DRIVERSTATICHEADERID =
			 * "driverStaticHeaderId"; private static final String
			 * SESSION_KEY_DRIVERSTATICHEADERNAME = "driverStaticHeaderName";
			 * private static final String
			 * SESSION_KEY_DRIVERSTATICDIMSIONSEGMENT =
			 * "driverStaticDeminsionSegment";
			 */
			httpSession.removeAttribute(SESSION_KEY_DRIVERSTATICHEADERID);
			httpSession.setAttribute(SESSION_KEY_DRIVERSTATICHEADERID, Integer.valueOf(parentBean.getStaticHeaderId()));
			httpSession.removeAttribute(SESSION_KEY_DRIVERSTATICHEADERNAME);
			httpSession.setAttribute(SESSION_KEY_DRIVERSTATICHEADERNAME, parentBean.getDriverCode());
			httpSession.removeAttribute(SESSION_KEY_DRIVERSTATICDIMSIONSEGMENT);
			httpSession.setAttribute(SESSION_KEY_DRIVERSTATICDIMSIONSEGMENT, parentBean.getDimensionSegment());
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
		return "configuration/AllocationRulesSetting/driverStaticLineConfiguration";
	}

	@RequestMapping({ "/findDriverStaticLineValue" })
	@ResponseBody
	public Object findDriverStaticLineValue(HttpServletRequest request,HttpServletResponse response
, HttpSession httpSession) {
		DriverStaticLine driverStaticLine = new DriverStaticLine();

		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			initilizePagingInfo(request);

			driverStaticLine.setRowStartIndex(this.queryRowStartIndex);

			driverStaticLine.setRowCount(this.rows);
			driverStaticLine.setStaticHeaderId(Integer.parseInt(request.getParameter("headerId")));
			driverStaticLine.setDimValue(request.getParameter("dimValue"));
			driverStaticLine.setDriverName(request.getParameter("driverCode"));
		} catch (UnsupportedEncodingException e1) {
			logger.error("findDriverStaticLineValue():" + e1);
		}
		int headerId = ((Integer) httpSession.getAttribute(SESSION_KEY_DRIVERSTATICHEADERID)).intValue();
		driverStaticLine.setStaticHeaderId(headerId);

		List<Map<String, Object>> list = null;
		try {
			list = this.allocationRulesSettingService.findDriverStaticLine(driverStaticLine);
		} catch (Exception e) {
			logger.error("findDriverStaticLine():" + e);
		}
		int count = 0;
		try {
			count = this.allocationRulesSettingService.getCountDriverStaticLine(driverStaticLine);
		} catch (Exception e) {
			logger.error("getCountDriverStaticLine():" + e);
		}
		JSONArray jsonlist = JsonUtil.fromObject(list);

		this.dataMap.put("rows", jsonlist);
		this.dataMap.put("total", Integer.valueOf(count));

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
		return JsonUtil.fromObject(this.dataMap);
	}

	@RequestMapping({ "/createDriverStaticLineValue" })
	@ResponseBody
	public Object createDriverStaticLineValue(DriverStaticLine obj,HttpServletResponse response, HttpServletRequest request, HttpSession session) {
		DriverStaticLine reqBean = null;
		boolean isError = false;
		String errorMsg = "保存信息失败：请检查输入数据，重新提交！";
		this.dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			reqBean = (DriverStaticLine) constructDimensionBean(request, new DriverStaticLine(), true);

			int headerId = 0;
			if (session.getAttribute(SESSION_KEY_DRIVERSTATICHEADERID) != null) {
				headerId = Integer.parseInt(session.getAttribute(SESSION_KEY_DRIVERSTATICHEADERID).toString());
			}
			reqBean.setStaticHeaderId(headerId);
			if (!isDimFilterInputValid(reqBean, true)) {
				isError = true;
				errorMsg = "输入信息有误，请检查数据格式，重新保存！";
			} else {
				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
					    .getAuthentication()
					    .getPrincipal();

				String userId = userDetails.getUsername();
				//String userId = (String) session.getAttribute("HRS_AUTH_USER");
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());

				int newKey = 0;
				try {
					newKey = this.allocationRulesSettingService.createNewDriverStaticLine(reqBean);
				} catch (DuplicateKeyException de) {
					logger.error("createNewDriverStaticLine():" + de);
					errorMsg = "请重新输入再次提交";
				} catch (Exception e) {
					logger.error("Errors while createNewDriverStaticLine:" + e);
				}
				if (newKey == 0) {
					isError = true;
				}
				reqBean.setStaticLineId(newKey);
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		}
		if (isError) {
			this.dataMap.put("isError", Boolean.valueOf(true));
			this.dataMap.put("msg", errorMsg);
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
		return JsonUtil.fromObject(this.dataMap);
	}

	@RequestMapping({ "/updateDriverStaticLineValue" })
	@ResponseBody
	public Object updateDriverStaticLineValue(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		DriverStaticLine reqBean = null;
		boolean isError = false;
		String errorMsg = "保存信息失败：请检查输入数据，重新提交！";
		this.dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			reqBean = (DriverStaticLine) constructDimensionBean(request, new DriverStaticLine(), false);
			int headerId = 0;
			if (session.getAttribute(SESSION_KEY_DRIVERSTATICHEADERID) != null) {
				headerId = Integer.parseInt(session.getAttribute(SESSION_KEY_DRIVERSTATICHEADERID).toString());
			}
			// String a=request.getParameter("headerId").toString();
			reqBean.setStaticHeaderId(headerId);
			if (!isDimFilterInputValid(reqBean, false)) {
				isError = true;
				errorMsg = "输入信息有误，请检查数据格式，重新保存！";
			} else {
				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
					    .getAuthentication()
					    .getPrincipal();

				String userId = userDetails.getUsername();
				//String userId = (String) session.getAttribute("HRS_AUTH_USER");
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());

				int updateCount = 0;
				try {
					updateCount = this.allocationRulesSettingService.updateDriverStaticLine(reqBean);
				} catch (Exception e) {
					logger.error("Errors while updateDriverStaticLine:" + e);
				}
				if (updateCount == 0) {
					isError = true;
				}
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		}
		if (isError) {
			this.dataMap.put("isError", Boolean.valueOf(true));
			this.dataMap.put("msg", errorMsg);
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
		return JsonUtil.fromObject(this.dataMap);
	}

	@RequestMapping({ "/destroyDriverStaticLineValue" })
	@ResponseBody
	public Object destroyDriverStaticLineValue(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		// get dimension bean from request
		DriverStaticLine reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			String ruleLineId = this.getDecodedRequestParam(request, "id");
			if (StringUtils.isBlank(ruleLineId)) {
				isError = true;
				errorMsg = "没有选中需要删除的记录，请选择后再删除！";
			} else {
				reqBean = new DriverStaticLine();
				reqBean.setStaticLineId(Integer.parseInt(ruleLineId));
				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
					    .getAuthentication()
					    .getPrincipal();

				String userId = userDetails.getUsername();
				//String userId = (String) session.getAttribute(Constants.USER_KEY);
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());
				// save dimension into database
				int delCount = 0;
				try {
					delCount = allocationRulesSettingService.deleteDriverStaticLine(reqBean);
				} catch (DataReferenceException de) {
					logger.error("Reference record found while deleteDriverStaticLine:" + de);
					errorMsg = "错误发生于删除deleteDriverStaticLine";
					isError = true;
				} catch (Exception e) {
					logger.error("Error happen while deleteDriverStaticLine:" + e);
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

	// XXX 分摊规则组头表
	@RequestMapping({ "/findAllocRulesGroupHaeader" })
	@ResponseBody
	public Object findAllocRulesGroupHaeader(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		AllocRulesGroupHaeaderBean allocRulesGroupHaeaderBean = new AllocRulesGroupHaeaderBean();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			initilizePagingInfo(request);
			allocRulesGroupHaeaderBean.setRowStartIndex(this.queryRowStartIndex);
			allocRulesGroupHaeaderBean.setRowCount(this.rows);
			allocRulesGroupHaeaderBean.setGroupName(request.getParameter("allocRulesGroupHaeader"));
		} catch (Exception e) {
			logger.error("Error while get findAllocRulesGroupHaeader:" + e);
		}
		List<Map<String, Object>> list = null;
		try {
			list = this.allocationRulesSettingService.findAllocRulesGroupHaeader(allocRulesGroupHaeaderBean);
		} catch (Exception e) {
			logger.error("Error while get dimension filter header:" + e);
		}
		JSONArray jsonlist = JsonUtil.fromObject(list);
		this.dataMap.put("rows", jsonlist);
		int count = 0;
		try {
			count = this.allocationRulesSettingService.getCountAllocRulesGroupHaeader(allocRulesGroupHaeaderBean);
		} catch (Exception e) {
			logger.error("Error while get dimension count:" + e);
		}
		this.dataMap.put("total", Integer.valueOf(count));
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
		return JsonUtil.fromObject(this.dataMap);
	}

	@RequestMapping({ "/saveAllocRulesGroupHaeader" })
	@ResponseBody
	public Object saveAllocRulesGroupHaeader(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		AllocRulesGroupHaeaderBean allocRulesGroupHaeaderBean = null;
		boolean isError = false;
		String errorMsg = "保存信息失败：请检查输入数据，重新提交！";

		this.dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			allocRulesGroupHaeaderBean = (AllocRulesGroupHaeaderBean) constructDimensionBean(request,
					new AllocRulesGroupHaeaderBean(), true);
			if (!isDimFilterInputValid(allocRulesGroupHaeaderBean, true)) {
				isError = true;
				errorMsg = "输入信息有误，请检查数据格式，重新保存！";
			} else {
				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
					    .getAuthentication()
					    .getPrincipal();

				String userId = userDetails.getUsername();
				//String userId = (String) session.getAttribute("HRS_AUTH_USER");
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				allocRulesGroupHaeaderBean.setCreatedBy(userId);
				allocRulesGroupHaeaderBean.setCreationDate(new Date());
				allocRulesGroupHaeaderBean.setLastUpdatedBy(userId);
				allocRulesGroupHaeaderBean.setLastUpdateDate(new Date());
				try {
					this.allocationRulesSettingService.createNewAllocRulesGroupHaeader(allocRulesGroupHaeaderBean);
				} catch (Exception e) {
					logger.error("error occur create a new DriverStaticHeader" + e);
				}
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		}
		this.dataMap.put("isError", Boolean.valueOf(true));
		this.dataMap.put("msg", errorMsg);

		constructReturnMap(allocRulesGroupHaeaderBean);
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
		return JsonUtil.fromObject(this.dataMap);
	}

	@RequestMapping({ "/updateAllocRulesGroupHaeader" })
	@ResponseBody
	public Object updateAllocRulesGroupHaeader(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		AllocRulesGroupHaeaderBean reqBean = null;
		boolean isError = false;
		String errorMsg = "保存信息失败：请检查输入数据，重新提交！";
		this.dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			reqBean = (AllocRulesGroupHaeaderBean) constructDimensionBean(request, new AllocRulesGroupHaeaderBean(),
					false);
			if (!isDimFilterInputValid(reqBean, false)) {
				isError = true;
				errorMsg = "输入信息有误，请检查数据格式，重新保存！";
			} else {
				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
					    .getAuthentication()
					    .getPrincipal();

				String userId = userDetails.getUsername();
				//String userId = (String) session.getAttribute("HRS_AUTH_USER");
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());

				int updateCount = 0;
				try {
					updateCount = this.allocationRulesSettingService.updateAllocRulesGroupHaeader(reqBean);
				} catch (Exception e) {
					logger.error("Errors while save updateAllocRulesGroupHaeader:" + e);
				}
				if (updateCount == 0) {
					isError = true;
				}
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		}
		if (isError) {
			this.dataMap.put("isError", Boolean.valueOf(true));
			this.dataMap.put("msg", errorMsg);
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
		return JsonUtil.fromObject(this.dataMap);
	}

	@RequestMapping({ "/destroyAllocRulesGroupHaeader" })
	@ResponseBody
	public Object destroyAllocRulesGroupHaeader(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		// get dimension bean from request
		AllocRulesGroupHaeaderBean reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			String ruleLineId = this.getDecodedRequestParam(request, "id");
			if (StringUtils.isBlank(ruleLineId)) {
				isError = true;
				errorMsg = "没有选中需要删除的记录，请选择后再删除！";
			} else {
				reqBean = new AllocRulesGroupHaeaderBean();
				reqBean.setGroupHeaderId(Integer.parseInt(ruleLineId));
				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
					    .getAuthentication()
					    .getPrincipal();

				String userId = userDetails.getUsername();
				//String userId = (String) session.getAttribute(Constants.USER_KEY);
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());
				// save dimension into database
				int delCount = 0;
				try {
					delCount = allocationRulesSettingService.deleteAllocRulesGroupHaeader(reqBean);
				} catch (DataReferenceException de) {
					logger.error("Reference record found while delete deleteAllocRulesGroupHaeader value:" + de);
					errorMsg = "错误发生于删除deleteAllocRulesGroupHaeader";
					isError = true;
				} catch (Exception e) {
					logger.error("Error happen while delete dimension value:" + e);
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

	// XXX 分摊规则行
	/**
	 * 分摊规则行
	 */
	@RequestMapping({ "/showAllocRulesGroupLineView" })
	public String showAllocRulesGroupLineView(HttpServletRequest request,HttpServletResponse response, Model model, HttpSession httpSession) {
		String headerid = null;
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			headerid = getDecodedRequestParam(request, "headerId");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (!StringUtils.isBlank(headerid)) {
			AllocRulesGroupHaeaderBean parentBean = null;
			try {
				parentBean = this.allocationRulesSettingService
						.getAllocRulesGroupHaeaderById(Integer.parseInt(headerid));
			} catch (NumberFormatException ne) {
				ne.printStackTrace();
			} catch (Exception localException) {
			}
			if (parentBean != null) {
				model.addAttribute("allocRulesGroupHeaderBean", parentBean);
			}
			/*
			 * private static final String SESSION_KEY_GROUPHEADERID =
			 * "groupHeaderId"; private static final String
			 * SESSION_KEY_GROUPNAME = "groupName";;
			 */
			httpSession.removeAttribute(SESSION_KEY_GROUPHEADERID);
			httpSession.setAttribute(SESSION_KEY_GROUPHEADERID, Integer.valueOf(parentBean.getGroupHeaderId()));
			httpSession.removeAttribute(SESSION_KEY_GROUPNAME);
			httpSession.setAttribute(SESSION_KEY_GROUPNAME, parentBean.getGroupName());
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
		return "configuration/AllocationRulesSetting/allocRulesGroupLineConfiguration";
	}

	@RequestMapping({ "/findAllocRulesGroupLineValue" })
	@ResponseBody
	public Object findAllocRulesGroupLineValue(HttpServletRequest request,HttpServletResponse response, HttpSession httpSession) {
		AllocRulesGroupLineBean allocRulesGroupLineBean = new AllocRulesGroupLineBean();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			initilizePagingInfo(request);

			allocRulesGroupLineBean.setRowStartIndex(this.queryRowStartIndex);

			allocRulesGroupLineBean.setRowCount(this.rows);
		} catch (UnsupportedEncodingException e1) {
			logger.error("findDriverStaticLineValue():" + e1);
		}
		int headerId = ((Integer) httpSession.getAttribute(SESSION_KEY_GROUPHEADERID)).intValue();
		allocRulesGroupLineBean.setGroupHeaderId(headerId);

		List<Map<String, Object>> list = null;
		try {
			list = this.allocationRulesSettingService.findAllocRulesGroupLine(allocRulesGroupLineBean);
		} catch (Exception e) {
			logger.error("findDriverStaticLine():" + e);
		}
		int count = 0;
		try {
			count = this.allocationRulesSettingService.getCountAllocRulesGroupLine(allocRulesGroupLineBean);
		} catch (Exception e) {
			logger.error("getCountDriverStaticLine():" + e);
		}
		JSONArray jsonlist = JsonUtil.fromObject(list);

		this.dataMap.put("rows", jsonlist);
		this.dataMap.put("total", Integer.valueOf(count));
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
		return JsonUtil.fromObject(this.dataMap);
	}

	@RequestMapping({ "/createAllocRulesGroupLine" })
	@ResponseBody
	public Object createAllocRulesGroupLine(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		AllocRulesGroupLineBean reqBean = null;
		boolean isError = false;
		String errorMsg = "保存信息失败：请检查输入数据，重新提交！";
		this.dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			reqBean = (AllocRulesGroupLineBean) constructDimensionBean(request, new AllocRulesGroupLineBean(), true);

			int headerId = 0;
			if (session.getAttribute(SESSION_KEY_GROUPHEADERID) != null) {
				headerId = Integer.parseInt(session.getAttribute(SESSION_KEY_GROUPHEADERID).toString());
			}
			reqBean.setGroupHeaderId(headerId);
			if (!isDimFilterInputValid(reqBean, true)) {
				isError = true;
				errorMsg = "输入信息有误，请检查数据格式，重新保存！";
			} else {
				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
					    .getAuthentication()
					    .getPrincipal();

				String userId = userDetails.getUsername();
				//String userId = (String) session.getAttribute("HRS_AUTH_USER");
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());

				int newKey = 0;
				try {
					newKey = this.allocationRulesSettingService.createNewAllocRulesGroupLine(reqBean);
				} catch (DuplicateKeyException de) {
					logger.error("createNewAllocRulesGroupLine():" + de);
					errorMsg = "请重新输入再次提交";
				} catch (Exception e) {
					logger.error("Errors while createNewAllocRulesGroupLine:" + e);
				}
				if (newKey == 0) {
					isError = true;
				}
				reqBean.setGroupLineId(newKey);
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		}
		if (isError) {
			this.dataMap.put("isError", Boolean.valueOf(true));
			this.dataMap.put("msg", errorMsg);
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
		return JsonUtil.fromObject(this.dataMap);
	}

	@RequestMapping({ "/updateAllocRulesGroupLineValue" })
	@ResponseBody
	public Object updateAllocRulesGroupLineValue(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		AllocRulesGroupLineBean reqBean = null;
		boolean isError = false;
		String errorMsg = "保存信息失败：请检查输入数据，重新提交！";
		this.dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			reqBean = (AllocRulesGroupLineBean) constructDimensionBean(request, new AllocRulesGroupLineBean(), false);
			int headerId = 0;
			if (session.getAttribute(SESSION_KEY_GROUPHEADERID) != null) {
				headerId = Integer.parseInt(session.getAttribute(SESSION_KEY_GROUPHEADERID).toString());
			}
			// String a=request.getParameter("headerId").toString();
			reqBean.setGroupHeaderId(headerId);
			if (!isDimFilterInputValid(reqBean, false)) {
				isError = true;
				errorMsg = "输入信息有误，请检查数据格式，重新保存！";
			} else {
				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
					    .getAuthentication()
					    .getPrincipal();

				String userId = userDetails.getUsername();
				//String userId = (String) session.getAttribute("HRS_AUTH_USER");
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());

				int updateCount = 0;
				try {
					updateCount = this.allocationRulesSettingService.updateAllocRulesGroupLine(reqBean);
				} catch (Exception e) {
					logger.error("Errors while updateAllocRulesGroupLine:" + e);
				}
				if (updateCount == 0) {
					isError = true;
				}
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		}
		if (isError) {
			this.dataMap.put("isError", Boolean.valueOf(true));
			this.dataMap.put("msg", errorMsg);
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
		return JsonUtil.fromObject(this.dataMap);
	}

	@RequestMapping({ "/destroyDriverAllocRulesGroupLineValue" })
	@ResponseBody
	public Object destroyDriverAllocRulesGroupLineValue(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		// get dimension bean from request
		AllocRulesGroupLineBean reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			String ruleLineId = this.getDecodedRequestParam(request, "id");
			if (StringUtils.isBlank(ruleLineId)) {
				isError = true;
				errorMsg = "没有选中需要删除的记录，请选择后再删除！";
			} else {
				reqBean = new AllocRulesGroupLineBean();
				reqBean.setGroupLineId(Integer.parseInt(ruleLineId));
				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
					    .getAuthentication()
					    .getPrincipal();

				String userId = userDetails.getUsername();
				//String userId = (String) session.getAttribute(Constants.USER_KEY);
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());
				// save dimension into database
				int delCount = 0;
				try {
					delCount = allocationRulesSettingService.deleteAllocRulesGroupLine(reqBean);
				} catch (DataReferenceException de) {
					logger.error("Reference record found while destroyDriverAllocRulesGroupLineValue:" + de);
					errorMsg = "错误发生于删除destroyDriverAllocRulesGroupLineValue";
					isError = true;
				} catch (Exception e) {
					logger.error("Error happen while destroyDriverAllocRulesGroupLineValue:" + e);
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

	// XXX AllocRuleBean

	@RequestMapping({ "/findAllocRule" })
	@ResponseBody
	public Object findAllocRule(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		AllocRuleBean findbean = new AllocRuleBean();

		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			findbean.setRowStartIndex(this.queryRowStartIndex);
			findbean.setRowCount(this.rows);
			findbean.setRuleName(request.getParameter("ruleName"));
		} catch (Exception e) {
			logger.error("Error while get findAllocRule:" + e);
		}
		List<Map<String, Object>> list = null;
		try {
			list = this.allocationRulesSettingService.findAllocRule(findbean);
		} catch (Exception e) {
			logger.error("Error while get dimension filter header:" + e);
		}
		JSONArray jsonlist = JsonUtil.fromObject(list);
		this.dataMap.put("rows", jsonlist);
		int count = 0;
		try {
			count = this.allocationRulesSettingService.getCountAllocRule(findbean);
		} catch (Exception e) {
			logger.error("Error while get dimension count:" + e);
		}
		this.dataMap.put("total", Integer.valueOf(count));
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
		return JsonUtil.fromObject(this.dataMap);
	}

	@RequestMapping({ "/saveAllocRule" })
	@ResponseBody
	public Object saveAllocRule(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		AllocRuleBean saveBean = null;
		boolean isError = false;
		String errorMsg = "保存信息失败：请检查输入数据，重新提交！";

		this.dataMap = new HashMap();

		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			saveBean = (AllocRuleBean) constructDimensionBean(request, new AllocRuleBean(), true);
			if (!isDimFilterInputValid(saveBean, true)) {
				isError = true;
				errorMsg = "输入信息有误，请检查数据格式，重新保存！";
			} else {
				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
					    .getAuthentication()
					    .getPrincipal();

				String userId = userDetails.getUsername();
				//String userId = (String) session.getAttribute("HRS_AUTH_USER");
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				saveBean.setCreatedBy(userId);
				saveBean.setCreationDate(new Date());
				saveBean.setLastUpdatedBy(userId);
				saveBean.setLastUpdateDate(new Date());
				try {
					this.allocationRulesSettingService.createNewAllocRule(saveBean);
				} catch (Exception e) {
					logger.error("error occur create a new DriverStaticHeader" + e);
				}
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		}
		this.dataMap.put("isError", Boolean.valueOf(true));
		this.dataMap.put("msg", errorMsg);

		constructReturnMap(saveBean);

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
		return JsonUtil.fromObject(this.dataMap);
	}

	@RequestMapping({ "/updateAllocRule" })
	@ResponseBody
	public Object updateAllocRule(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		AllocRuleBean reqBean = null;
		boolean isError = false;
		String errorMsg = "保存信息失败：请检查输入数据，重新提交！";
		this.dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			reqBean = (AllocRuleBean) constructDimensionBean(request, new AllocRuleBean(), false);
			if (!isDimFilterInputValid(reqBean, false)) {
				isError = true;
				errorMsg = "输入信息有误，请检查数据格式，重新保存！";
			} else {
				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
					    .getAuthentication()
					    .getPrincipal();

				String userId = userDetails.getUsername();
				//String userId = (String) session.getAttribute("HRS_AUTH_USER");
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());

				int updateCount = 0;
				try {
					updateCount = this.allocationRulesSettingService.updateAllocRule(reqBean);
				} catch (Exception e) {
					logger.error("Errors while save updateAllocRule:" + e);
				}
				if (updateCount == 0) {
					isError = true;
				}
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		}
		if (isError) {
			this.dataMap.put("isError", Boolean.valueOf(true));
			this.dataMap.put("msg", errorMsg);
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
		return JsonUtil.fromObject(this.dataMap);
	}

	@RequestMapping({ "/destroyAllocRule" })
	@ResponseBody
	public Object destroyAllocRule(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		// get dimension bean from request
		AllocRuleBean reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			String ruleLineId = this.getDecodedRequestParam(request, "id");
			if (StringUtils.isBlank(ruleLineId)) {
				isError = true;
				errorMsg = "没有选中需要删除的记录，请选择后再删除！";
			} else {
				reqBean = new AllocRuleBean();
				reqBean.setRuleId(Integer.parseInt(ruleLineId));
				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
					    .getAuthentication()
					    .getPrincipal();

				String userId = userDetails.getUsername();
				//String userId = (String) session.getAttribute(Constants.USER_KEY);
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());
				// save dimension into database
				int delCount = 0;
				try {
					delCount = allocationRulesSettingService.deleteAllocRule(reqBean);
				} catch (DataReferenceException de) {
					logger.error("Reference record found while delete deleteAllocRule value:" + de);
					errorMsg = "错误发生于删除deleteAllocRule";
					isError = true;
				} catch (Exception e) {
					logger.error("Error happen while delete deleteAllocRule value:" + e);
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

	// XXX
	/**
	 * 分摊source账户
	 * 
	 */
	@RequestMapping({ "/showAllocSourceAccountValueView" })
	public String showAllocSourceAccountValueView(HttpServletRequest request,HttpServletResponse response, Model model, HttpSession httpSession) {
		String allocSourceId = null;
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			allocSourceId = getDecodedRequestParam(request, "allocSourceId");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (!StringUtils.isBlank(allocSourceId)) {
			AllocSourceBean parentBean = null;
			try {
				parentBean = this.allocationRulesSettingService.getAllocSourceById(Integer.parseInt(allocSourceId));
			} catch (NumberFormatException ne) {
				ne.printStackTrace();
			} catch (Exception localException) {
			}
			if (parentBean != null) {
				model.addAttribute("allocSource", parentBean);
			}
			/*
			 * private static final String SESSION_KEY_SOURCEID= "sourceId";
			 * private static final String SESSION_KEY_SOURCENAME =
			 * "sourceName";
			 */
			httpSession.removeAttribute(SESSION_KEY_SOURCEID);
			httpSession.setAttribute(SESSION_KEY_SOURCEID, parentBean.getSourceId());
			AllocRuleBean grandparentBean = null;
			try {
				grandparentBean = this.allocationRulesSettingService.getAllocRuleById(parentBean.getRuleId());
			} catch (Exception e) {
				logger.error("showAllocSourceAccountValueView():" + e);
				e.printStackTrace();
			}
			/*
			 * private static final String SESSION_KEY_RULE_ID= "ruleId";
			 * private static final String SESSION_KEY_RULE_NAME= "ruleName";
			 * 
			 */
			httpSession.removeAttribute(SESSION_KEY_RULE_ID);
			httpSession.setAttribute(SESSION_KEY_RULE_ID, grandparentBean.getRuleId());
			httpSession.removeAttribute(SESSION_KEY_RULE_NAME);
			httpSession.setAttribute(SESSION_KEY_RULE_NAME, grandparentBean.getRuleName());
			
			
			//进行对sourceaccount进行初始化操作 ： 1.判定是否已经初始化过 ，如果未初始化，进行初始化。
			AllocSourceAccountBean allocSourceAccountBean= new AllocSourceAccountBean();
			allocSourceAccountBean.setSourceId(Integer.parseInt(allocSourceId));
			int countSourceAccountBycondition=0;
			try {
				countSourceAccountBycondition=this.allocationRulesSettingService.getCountAllocSourceAccountTrue(allocSourceAccountBean);
			   
			} catch (Exception e) {
				logger.error("showAllocSourceAccountValueViewAddNewMessage():" + e);
				e.printStackTrace();
			}
			if(countSourceAccountBycondition==0){
				List<Map<String, Object>> demensionlist = new ArrayList();
				demensionlist = this.allocationRulesSettingService.getSegmentList("all");
				Map<String, Object> additonalMap = new HashMap<String, Object>();
				List <String> keyList=new ArrayList<String>();
				for (Map map:demensionlist){
					String value=(String) map.get("DIM_SEGMENT");
					keyList.add(value);
				}
				for(String value:keyList){
					AllocSourceAccountBean reqBean=new AllocSourceAccountBean();

					UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
						    .getAuthentication()
						    .getPrincipal();

					String userId = userDetails.getUsername();
					//String userId = this.getLoginUserName(request);
					if (StringUtils.isBlank(userId)) {
						userId = "Web";
					}
					reqBean.setCreatedBy(userId);
					reqBean.setCreationDate(new Date());
					reqBean.setLastUpdatedBy(userId);
					reqBean.setLastUpdateDate(new Date());
					reqBean.setSourceId(Integer.parseInt(allocSourceId));
					reqBean.setDimensionSegment(value);
				    try {
						this.allocationRulesSettingService.createNewAllocSourceAccount(reqBean);
					} catch (Exception e) {
						logger.error("showAllocSourceAccountValueViewAddNewMessage():" + e);
						e.printStackTrace();
					}
				}
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
		return "configuration/AllocationRulesSetting/allocSourceAccountConfiguration";
	}

	@RequestMapping({ "/findAllocSourceAccount" })
	@ResponseBody
	public Object findAllocSourceAccount(HttpServletRequest request,HttpServletResponse response, HttpSession httpSession) {
		AllocSourceAccountBean allocSourceAccountBean = new AllocSourceAccountBean();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			initilizePagingInfo(request);
			allocSourceAccountBean.setRowStartIndex(this.queryRowStartIndex);
			allocSourceAccountBean.setRowCount(this.rows);
		} catch (UnsupportedEncodingException e1) {
			logger.error("findDriverStaticLineValue():" + e1);
		}
		int headerId = ((Integer) httpSession.getAttribute(SESSION_KEY_SOURCEID)).intValue();
		allocSourceAccountBean.setSourceId(headerId);

		List<Map<String, Object>> list = null;
		try {
			list = this.allocationRulesSettingService.findAllocSourceAccount(allocSourceAccountBean);
		} catch (Exception e) {
			logger.error("findDriverStaticLine():" + e);
		}
		int count = 0;
		try {
			count = this.allocationRulesSettingService.getCountAllocSourceAccount(allocSourceAccountBean);
		} catch (Exception e) {
			logger.error("getCountDriverStaticLine():" + e);
		}
		JSONArray jsonlist = JsonUtil.fromObject(list);

		this.dataMap.put("rows", jsonlist);
		this.dataMap.put("total", Integer.valueOf(count));
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
		return JsonUtil.fromObject(this.dataMap);
	}
	
	@RequestMapping({ "/updateAllocSourceAccount" })
	@ResponseBody
	public Object updateAllocSourceAccount(AllocSourceAccountBean obj,HttpServletResponse response, HttpServletRequest request,
			HttpSession session) {
		AllocSourceAccountBean reqBean = null;
		boolean isError = false;
		String errorMsg = "保存信息失败：请检查输入数据，重新提交！";
		this.dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			reqBean = (AllocSourceAccountBean) constructDimensionBean(request, new AllocSourceAccountBean(), true);

			int sourceId = 0;
			if (session.getAttribute(SESSION_KEY_SOURCEID) != null) {
				sourceId = Integer.parseInt(session.getAttribute(SESSION_KEY_SOURCEID).toString());
			}
			reqBean.setSourceId(sourceId);
			if (!isDimFilterInputValid(reqBean, true)) {
				isError = true;				
				errorMsg = "输入信息有误，请检查是否同时提交维值与筛选条件，请检查数据格式，重新保存！";
			} else {
				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
					    .getAuthentication()
					    .getPrincipal();

				String userId = userDetails.getUsername();
				//String userId = this.getLoginUserName(request);
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());

				int newKey = 0;
				if (reqBean.getSourceAccId() == 0) {
					try {
						newKey = this.allocationRulesSettingService.createNewAllocSourceAccount(reqBean);
					} catch (Exception e) {
						logger.error("createNewAllocSourceAccount():" + e);
						errorMsg = "请重新输入再次提交";
						isError = true;
					}

				} else {
					try {
						newKey = this.allocationRulesSettingService.updateAllocSourceAccount(reqBean);
					} catch (Exception e) {
						logger.error("updateAllocSourceAccount():" + e);
						errorMsg = "请重新输入再次提交";
						isError = true;
					}
				}
				if (newKey == 0) {
					isError = true;
					errorMsg = "请重新输入再次提交";
				}

			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
			
		}
		if (isError) {
			this.dataMap.put("isError", Boolean.valueOf(true));
			this.dataMap.put("msg", errorMsg);
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
		return JsonUtil.fromObject(this.dataMap);
	}
	/*
	 * @RequestMapping({ "/updateDriverStaticLineValue" })
	 * 
	 * @ResponseBody public Object
	 * updateDriverStaticLineValue(HttpServletRequest request, HttpSession
	 * session) { DriverStaticLine reqBean = null; boolean isError = false;
	 * String errorMsg = "保存信息失败：请检查输入数据，重新提交！"; this.dataMap = new HashMap();
	 * try { reqBean = (DriverStaticLine) constructDimensionBean(request, new
	 * DriverStaticLine(), false); int headerId = 0; if
	 * (session.getAttribute(SESSION_KEY_DRIVERSTATICHEADERID) != null) {
	 * headerId =
	 * Integer.parseInt(session.getAttribute(SESSION_KEY_DRIVERSTATICHEADERID).
	 * toString()); } // String a=request.getParameter("headerId").toString();
	 * reqBean.setStaticHeaderId(headerId); if (!isDimFilterInputValid(reqBean,
	 * false)) { isError = true; errorMsg = "输入信息有误，请检查数据格式，重新保存！"; } else {
	 * 
	 *
				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
					    .getAuthentication()
					    .getPrincipal();

				String userId = userDetails.getUsername();//String userId = (String) session.getAttribute("HRS_AUTH_USER"); if
	 * (StringUtils.isBlank(userId)) { userId = "Web"; }
	 * reqBean.setCreatedBy(userId); reqBean.setCreationDate(new Date());
	 * reqBean.setLastUpdatedBy(userId); reqBean.setLastUpdateDate(new Date());
	 * 
	 * int updateCount = 0; try { updateCount =
	 * this.allocationRulesSettingService.updateDriverStaticLine(reqBean); }
	 * catch (Exception e) { logger.error("Errors while updateDriverStaticLine:"
	 * + e); } if (updateCount == 0) { isError = true; } } } catch
	 * (UnsupportedEncodingException e) { isError = true; } if (isError) {
	 * this.dataMap.put("isError", Boolean.valueOf(true));
	 * this.dataMap.put("msg", errorMsg); } else { constructReturnMap(reqBean);
	 * } return JsonUtil.fromObject(this.dataMap); }
	 */
	
	// XXX
	/**
	 * 分摊target账户
	 * 
	 */
	@RequestMapping({ "/showAllocTargetAccountValueView" })
	public String showAllocTargetAccountValueView(HttpServletRequest request,HttpServletResponse response, Model model, HttpSession httpSession) {
		String allocTargetId = null;
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			allocTargetId = getDecodedRequestParam(request, "allocTargetId");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (!StringUtils.isBlank(allocTargetId)) {
			AllocTargetRecord parentBean = null;
			try {
				parentBean = this.allocationRulesSettingService.getAllocTargetById(Integer.parseInt(allocTargetId));
			} catch (NumberFormatException ne) {
				ne.printStackTrace();
			} catch (Exception localException) {
			}
			if (parentBean != null) {
				model.addAttribute("allocTarget", parentBean);
			}
			/*
			 * private static final String SESSION_KEY_SOURCEID= "sourceId";
			 * private static final String SESSION_KEY_SOURCENAME =
			 * "sourceName";
			 */
			httpSession.removeAttribute(SESSION_KEY_TARGETID);
			httpSession.setAttribute(SESSION_KEY_TARGETID, parentBean.getTargetId());
			httpSession.removeAttribute(SESSION_KEY_TARGETTYPE);
			httpSession.setAttribute(SESSION_KEY_TARGETTYPE, parentBean.getTargetType());
			AllocRuleBean grandparentBean = null;
			try {
				grandparentBean = this.allocationRulesSettingService.getAllocRuleById((int)parentBean.getAllocRuleId());
			} catch (Exception e) {
				logger.error("showAllocSourceAccountValueView():" + e);
				e.printStackTrace();
			}
			List<Map<String, Object>> list = null;
			try {
				list=this.allocationRulesSettingService.findAllType(parentBean.getAllocRuleId());
			} catch (Exception e) {
				logger.error("findAllType ruleid():" + e);
				e.printStackTrace();
			}
			Map<String,Object> map=list.get(0);
			String sourceType=(String)map.get("SOURCE_TYPE");
			String driverType=(String)map.get("DRIVER_TYPE");
			String staticDriverDim=(String)map.get("STATIC_DIMENSION_SEGMENT");
			//TODO   add sourceType driverType into session
			httpSession.removeAttribute(SESSION_KEY_DRIVERTYPE);
			httpSession.setAttribute(SESSION_KEY_DRIVERTYPE, driverType);
			httpSession.removeAttribute(SESSION_KEY_SOURCETYPE);
			httpSession.setAttribute(SESSION_KEY_SOURCETYPE, sourceType);
			httpSession.removeAttribute(SESSION_KEY_STATIC_DIMENSION_SEGMENT);
			httpSession.setAttribute(SESSION_KEY_STATIC_DIMENSION_SEGMENT, staticDriverDim);
			/*
			 * private static final String SESSION_KEY_RULE_ID= "ruleId";
			 * private static final String SESSION_KEY_RULE_NAME= "ruleName";
			 * 
			 */
			httpSession.removeAttribute(SESSION_KEY_RULE_ID);
			httpSession.setAttribute(SESSION_KEY_RULE_ID, grandparentBean.getRuleId());
			httpSession.removeAttribute(SESSION_KEY_RULE_NAME);
			httpSession.setAttribute(SESSION_KEY_RULE_NAME, grandparentBean.getRuleName());
			
			
			//进行对targetaccount进行初始化操作 ： 1.判定是否已经初始化过 ，如果未初始化，进行初始化。
			AllocTargetAccountBean requestBean= new AllocTargetAccountBean();
			requestBean.setTargetId(Integer.parseInt(allocTargetId));
			int count=0;
			try {
				count=this.allocationRulesSettingService.getCountAllocTargetAccountTrue(requestBean);
			} catch (Exception e) {
				logger.error("showAllocSourceAccountValueViewAddNewMessage():" + e);
				e.printStackTrace();
			}
			if(count==0){
				List<Map<String, Object>> demensionlist = new ArrayList();
				demensionlist = this.allocationRulesSettingService.getSegmentList("all");
				Map<String, Object> additonalMap = new HashMap<String, Object>();
				List <String> keyList=new ArrayList<String>();
				for (Map mapValue:demensionlist){
					String value=(String) mapValue.get("DIM_SEGMENT");
					keyList.add(value);
				}
				for(String value:keyList){
					AllocTargetAccountBean reqBean=new AllocTargetAccountBean();

					UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
						    .getAuthentication()
						    .getPrincipal();

					String userId = userDetails.getUsername();
					//String userId = this.getLoginUserName(request);
					if (StringUtils.isBlank(userId)) {
						userId = "Web";
					}
					reqBean.setCreatedBy(userId);
					reqBean.setCreationDate(new Date());
					reqBean.setLastUpdatedBy(userId);
					reqBean.setLastUpdateDate(new Date());
					reqBean.setTargetId(Integer.parseInt(allocTargetId));
					reqBean.setDimensionSegment(value);
				    try {
						this.allocationRulesSettingService.createNewAllocTargetAccount(reqBean);
					} catch (Exception e) {
						logger.error("showAllocSourceAccountValueViewAddNewMessage():" + e);
						e.printStackTrace();
					}
				}
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
		return "configuration/AllocationRulesSetting/allocTargetAccountConfiguration";
	}

	@RequestMapping({ "/findAllocTargetAccount" })
	@ResponseBody
	public Object findAllocTargetAccount(HttpServletRequest request,HttpServletResponse response, HttpSession httpSession) {
		AllocTargetAccountBean allocTargetAccountBean = new AllocTargetAccountBean();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			initilizePagingInfo(request);

			allocTargetAccountBean.setRowStartIndex(this.queryRowStartIndex);

			allocTargetAccountBean.setRowCount(this.rows);
		} catch (UnsupportedEncodingException e1) {
			logger.error("findAllocTargetAccount():" + e1);
		}
		int headerId = ( (Long)httpSession.getAttribute(SESSION_KEY_TARGETID)).intValue();
		allocTargetAccountBean.setTargetId(headerId);

		List<Map<String, Object>> list = null;
		try {
			list = this.allocationRulesSettingService.findAllocTargetAccount(allocTargetAccountBean);
		} catch (Exception e) {
			logger.error("findAllocTargetAccount():" + e);
		}
		int count = 0;
		try {
			count = this.allocationRulesSettingService.getCountAllocTargetAccount(allocTargetAccountBean);
		} catch (Exception e) {
			logger.error("getCountAllocTargetAccount():" + e);
		}
		JSONArray jsonlist = JsonUtil.fromObject(list);

		this.dataMap.put("rows", jsonlist);
		this.dataMap.put("total", Integer.valueOf(count));
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
		return JsonUtil.fromObject(this.dataMap);
	}

	@RequestMapping({ "/updateAllocTargetAccount" })
	@ResponseBody
	public Object updateAllocTargetAccount(AllocSourceAccountBean obj,HttpServletResponse response, HttpServletRequest request,
			HttpSession session) {
		AllocTargetAccountBean reqBean = null;
		boolean isError = false;
		String errorMsg = "保存信息失败：请检查输入数据，重新提交！";
		this.dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			reqBean = (AllocTargetAccountBean) constructDimensionBean(request, new AllocTargetAccountBean(), true);
			int targetId = 0;
			if (session.getAttribute(SESSION_KEY_TARGETID) != null) {
				targetId = Integer.parseInt(session.getAttribute(SESSION_KEY_TARGETID).toString());
			}
			reqBean.setTargetId(targetId);
			;
			if (!isDimFilterInputValid(reqBean, true)) {
				isError = true;
				errorMsg = "请检查您的填写，type为‘value’时维值不可为空，非‘value’时维值必须为空！";
				throw new Exception();
			} else {
				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
					    .getAuthentication()
					    .getPrincipal();

				String userId = userDetails.getUsername();
				//String userId = (String) session.getAttribute("HRS_AUTH_USER");
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());

				int newKey = 0;
				if (reqBean.getTargetAccId() == 0) {

					try {
						newKey = this.allocationRulesSettingService.createNewAllocTargetAccount(reqBean);
					} catch (Exception e) {
						logger.error("createNewAllocSourceAccount():" + e);
						errorMsg = "请重新输入再次提交";
						isError = true;
					}

				} else {
					try {
						newKey = this.allocationRulesSettingService.updateAllocTargetAccount(reqBean);
					} catch (Exception e) {
						logger.error("updateAllocSourceAccount():" + e);
						errorMsg = "请重新输入再次提交";
						isError = true;
					}
				}
				if (newKey == 0) {
					isError = true;
					errorMsg = "请重新输入再次提交";
				}

			}
		} catch (Exception e) {
			isError = true;

			if (isError) {
				this.dataMap.put("isError", Boolean.valueOf(true));
				this.dataMap.put("msg", errorMsg);
			} else {
				constructReturnMap(reqBean);
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
		return JsonUtil.fromObject(this.dataMap);
	}
	
	// XXX
	/**
	 * 分摊DRIVER账户
	 * 
	 */
	@RequestMapping({ "/showAllocDriverAccountValueView" })
	public String showAllocDriverAccountValueView(HttpServletRequest request,HttpServletResponse response, Model model, HttpSession httpSession) {
		String allocDriverId = null;
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			allocDriverId = getDecodedRequestParam(request, "allocDriverId");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (!StringUtils.isBlank(allocDriverId)) {
			AllocDriverRecord parentBean = null;
			try {
				parentBean = this.allocationRulesSettingService.getAllocDriverById(Integer.parseInt(allocDriverId));
			} catch (NumberFormatException ne) {
				ne.printStackTrace();
			} catch (Exception localException) {
			}
			if (parentBean != null) {
				model.addAttribute("allocDriver", parentBean);
			}
			/*
			 * private static final String SESSION_KEY_SOURCEID= "sourceId";
			 * private static final String SESSION_KEY_SOURCENAME =
			 * "sourceName";
			 */
			httpSession.removeAttribute(SESSION_KEY_DRIVERID);
			httpSession.setAttribute(SESSION_KEY_DRIVERID, parentBean.getDriverId());
			AllocRuleBean grandparentBean = null;
			try {
				grandparentBean = this.allocationRulesSettingService.getAllocRuleById((int)parentBean.getRuleId());
			} catch (Exception e) {
				logger.error("showAllocDriverAccountValueView():" + e);
				e.printStackTrace();
			}
			/*
			 * private static final String SESSION_KEY_RULE_ID= "ruleId";
			 * private static final String SESSION_KEY_RULE_NAME= "ruleName";
			 * 
			 */
			httpSession.removeAttribute(SESSION_KEY_RULE_ID);
			httpSession.setAttribute(SESSION_KEY_RULE_ID, grandparentBean.getRuleId());
			httpSession.removeAttribute(SESSION_KEY_RULE_NAME);
			httpSession.setAttribute(SESSION_KEY_RULE_NAME, grandparentBean.getRuleName());
			
			//进行对driver account进行初始化操作 ： 1.判定是否已经初始化过 ，如果未初始化，进行初始化。
			AllocDriverAccountBean requestBean= new AllocDriverAccountBean();
			requestBean.setDrvierId(Integer.parseInt(allocDriverId));
			int count=0;
			try {
				count=this.allocationRulesSettingService.getCountAllocDriverAccountTrue(requestBean);
			} catch (Exception e) {
				logger.error("showAllocDriverAccountValueViewAddNewMessage():" + e);
				e.printStackTrace();
			}
			if(count==0){
				List<Map<String, Object>> demensionlist = new ArrayList();
				demensionlist = this.allocationRulesSettingService.getSegmentList("all");
				Map<String, Object> additonalMap = new HashMap<String, Object>();
				List <String> keyList=new ArrayList<String>();
				for (Map map:demensionlist){
					String value=(String) map.get("DIM_SEGMENT");
					keyList.add(value);
				}
				for(String value:keyList){
					AllocDriverAccountBean reqBean=new AllocDriverAccountBean();

					UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
						    .getAuthentication()
						    .getPrincipal();

					String userId = userDetails.getUsername();
					//String userId = this.getLoginUserName(request);
					if (StringUtils.isBlank(userId)) {
						userId = "Web";
					}
					reqBean.setCreatedBy(userId);
					reqBean.setCreationDate(new Date());
					reqBean.setLastUpdatedBy(userId);
					reqBean.setLastUpdateDate(new Date());
					reqBean.setDrvierId(Integer.parseInt(allocDriverId));
					reqBean.setDimensionSegment(value);
				    try {
						this.allocationRulesSettingService.createNewAllocDriverAccount(reqBean);
					} catch (Exception e) {
						logger.error("showAllocDriverAccountValueViewAddNewMessage():" + e);
						e.printStackTrace();
					}
				}
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
		return "configuration/AllocationRulesSetting/allocDriverAccountConfiguration";
	}

	@RequestMapping({ "/findAllocDriverAccount" })
	@ResponseBody
	public Object findAllocDriverAccount(HttpServletRequest request,HttpServletResponse response, HttpSession httpSession) {
		AllocDriverAccountBean allocDriverAccountBean = new AllocDriverAccountBean();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			initilizePagingInfo(request);
			allocDriverAccountBean.setRowStartIndex(this.queryRowStartIndex);
			allocDriverAccountBean.setRowCount(this.rows);
		} catch (UnsupportedEncodingException e1) {
			logger.error("findAllocDriverAccount():" + e1);
		}
		int headerId = ((Long) httpSession.getAttribute(SESSION_KEY_DRIVERID)).intValue();
		allocDriverAccountBean.setDrvierId(headerId);

		List<Map<String, Object>> list = null;
		try {
			list = this.allocationRulesSettingService.findAllocDriverAccount(allocDriverAccountBean);
		} catch (Exception e) {
			logger.error("findDriverStaticLine():" + e);
		}
		int count = 0;
		try {
			count = this.allocationRulesSettingService.getCountAllocDriverAccount(allocDriverAccountBean);
		} catch (Exception e) {
			logger.error("getCountDriverStaticLine():" + e);
		}
		JSONArray jsonlist = JsonUtil.fromObject(list);

		this.dataMap.put("rows", jsonlist);
		this.dataMap.put("total", Integer.valueOf(count));
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
		return JsonUtil.fromObject(this.dataMap);
	}
	
	@RequestMapping({ "/updateAllocDriverAccount" })
	@ResponseBody
	public Object updateAllocDriverAccount(AllocDriverAccountBean obj,HttpServletResponse response, HttpServletRequest request,
			HttpSession session) {
		AllocDriverAccountBean reqBean = null;
		boolean isError = false;
		String errorMsg = "保存信息失败：请检查输入数据，重新提交！";
		this.dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			reqBean = (AllocDriverAccountBean) constructDimensionBean(request, new AllocDriverAccountBean(), true);

			int driverId = 0;
			if (session.getAttribute(SESSION_KEY_DRIVERID) != null) {
				driverId = Integer.parseInt(session.getAttribute(SESSION_KEY_DRIVERID).toString());
			}
			reqBean.setDrvierId(driverId);
			if (!isDimFilterInputValid(reqBean, true)) {
				isError = true;				
				errorMsg = "输入信息有误，请检查是否同时提交维值与筛选条件，请检查数据格式，重新保存！";
			} else {
				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
					    .getAuthentication()
					    .getPrincipal();

				String userId = userDetails.getUsername();
				//String userId = this.getLoginUserName(request);
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());

				int newKey = 0;
				if (reqBean.getDrvierAccId() == 0) {
					try {
						newKey = this.allocationRulesSettingService.createNewAllocDriverAccount(reqBean);
					} catch (Exception e) {
						System.out.println(e);
						logger.error("createNewAllocDriverAccount():" + e);
						errorMsg = "请重新输入再次提交";
						isError = true;
					}

				} else {
					try {
						newKey = this.allocationRulesSettingService.updateAllocDriverAccount(reqBean);
					} catch (Exception e) {
						logger.error("updateAllocDriverAccount():" + e);
						errorMsg = "请重新输入再次提交";
						isError = true;
					}
				}
				if (newKey == 0) {
					isError = true;
					errorMsg = "请重新输入再次提交";
				}

			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
			
		}
		if (isError) {
			this.dataMap.put("isError", Boolean.valueOf(true));
			this.dataMap.put("msg", errorMsg);
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
		return JsonUtil.fromObject(this.dataMap);
	}
	
	
	@RequestMapping(value = "/getDimFilterList")
	@ResponseBody
	public Object getDimFilterList(HttpServletRequest request, HttpSession httpSession) {
		// 参数
		boolean isError = false;
		String errorMsg = "";
		DimFilterHeaderBean dimFilterBean = new DimFilterHeaderBean();
		List<Map<String, Object>> list = null;
		String dimCode = null;
		try {
			//GET DIM id FOR QUERY
			dimCode = this.getDecodedRequestParam(request, "dimCode");
			if (StringUtil.isEmptyTrim(dimCode)) {
				isError = true;
				errorMsg = "Dimension Code is empty";
			} else {
				if (!isError) {
					//set dim id
					dimFilterBean.setDimensionSegment(dimCode);
					//get other parameter from request
					list = allocationRulesSettingService.getDimFilterListForCombo(dimFilterBean);
				}
			}
		} catch (Exception e) {
			logger.error("Errors while save dimension value list:" + e);
			isError = true;
			errorMsg = "Errors while save dimension value list";
		} 

		if (isError) {
			dataMap.put("isError", true);
			dataMap.put("msg", errorMsg);
			return dataMap;
		} else {
			return list;
		}
	}
	
	@RequestMapping(value = "/getDimFilterDispName")
	@ResponseBody
	public Object getDimFilterDispName(HttpServletRequest request,HttpServletResponse response, HttpSession httpSession) {
		// 参数	
		DimFilterHeaderBean filterHeader = new DimFilterHeaderBean();	
		List<Map<String, Object>> list = null;
		int filterId = 0;
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			String filterIdStr= this.getDecodedRequestParam(request, "filterId");
			if (!StringUtil.isEmptyTrim(filterIdStr)) {
				filterId = Integer.parseInt(filterIdStr);
			}
			if (filterId != 0) {
				filterHeader.setFilterHeaderId(filterId);
			}
			list = allocationRulesSettingService.getDimFilterListForCombo(filterHeader);
		} catch (Exception e) {
			logger.error("Errors while save dimension value list:" + e);
		}
		if (list != null && 0 < list.size()) {
			dataMap = new HashMap();
			dataMap.putAll(list.get(0));
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
	
	@RequestMapping(value = "/getTargetTypeList")
	@ResponseBody
	public Object getTargetTypeList(HttpServletRequest request,HttpServletResponse response, HttpSession httpSession) {
		// 参数	targetType='+targetType+'&sourceType='+sourceType+'&driverType='+driverType+'&dimCode=' + row.DIMENSION_SEGMENT;
		String targetType=null;
		String sourceType=null;
		String driverType=null;
		String dimCode=null;
		String staticDim=null;
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		 try {
			targetType= this.getDecodedRequestParam(request, "targetType");
			 sourceType= this.getDecodedRequestParam(request, "sourceType");
			 driverType= this.getDecodedRequestParam(request, "driverType");
			 dimCode= this.getDecodedRequestParam(request, "dimCode");
			 staticDim= this.getDecodedRequestParam(request, "staticDim");
		} catch (Exception e) {
			logger.error("Errors while getTargetTypeListe" + e);
			e.printStackTrace();
		}
		 StringBuffer sb=new StringBuffer();
		 String val1="{\"value\":\"As Source\",\"key\":\"SOURCE\"},";
		 String val2="{\"value\":\"As Driver\",\"key\":\"DRIVER\"},";
		 String val3="{\"value\":\"Match Source & Driver\",\"key\":\"MATCH\"},";
		 String val4="{\"value\":\"VALUE\",\"key\":\"VALUE\"}";
		 sb.append("[");
		 if(!"CONSTANT".equals(sourceType)){
			 sb.append(val1);
		 }
		 if(!("CONSTANT".equals(driverType)||("STATIC".equals(sourceType)&&!staticDim.equals(dimCode)))){
			 sb.append(val2);
		 }
		 if("TARGET".equals(targetType)){
			 sb.append(val3);
		 }
		 sb.append(val4);
		 sb.append("]");
		 
		return sb.toString();
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
		return null;
	}
	
	
	
	// XXX 角色配置
	@RequestMapping({ "/findRoleUserMap" })
	@ResponseBody
	public Object findRoleUserMap(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		RoleUserMapRecord requestbean = new RoleUserMapRecord();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			requestbean.setRowStartIndex(this.queryRowStartIndex);
			requestbean.setRowCount(this.rows);
		} catch (Exception e) {
			logger.error("Error while get findAllocRulesGroupHaeader:" + e);
		}
		List<Map<String, Object>> list = null;
		try {
			list = this.allocationRulesSettingService.findRoleUserMap(requestbean);
		} catch (Exception e) {
			logger.error("Error while get dimension filter header:" + e);
		}
		JSONArray jsonlist = JsonUtil.fromObject(list);
		this.dataMap.put("rows", jsonlist);
		int count = 0;
		try {
			count = this.allocationRulesSettingService.getCountRoleUserMap(requestbean);
		} catch (Exception e) {
			logger.error("Error while get dimension count:" + e);
		}
		this.dataMap.put("total", Integer.valueOf(count));
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
		return JsonUtil.fromObject(this.dataMap);
	}

	@RequestMapping({ "/saveRoleUserMap" })
	@ResponseBody
	public Object saveRoleUserMap(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		RoleUserMapRecord requestbean = new RoleUserMapRecord();
		boolean isError = false;
		String errorMsg = "保存信息失败：请检查输入数据，重新提交！";

		this.dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			requestbean = (RoleUserMapRecord) constructDimensionBean(request,
					new RoleUserMapRecord(), true);
			if (!isDimFilterInputValid(requestbean, true)) {
				isError = true;
				errorMsg = "输入信息有误，请检查数据格式，重新保存！";
			} else {
				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
					    .getAuthentication()
					    .getPrincipal();

				String userId = userDetails.getUsername();
				//String userId = (String) session.getAttribute("HRS_AUTH_USER");
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				requestbean.setCreatedBy(userId);
				requestbean.setCreationDate(new Date());
				requestbean.setLastUpdatedBy(userId);
				requestbean.setLastUpdateDate(new Date());
				try {
					this.allocationRulesSettingService.saveRoleUserMap(requestbean);
				} catch (Exception e) {
					logger.error("error occur create a new DriverStaticHeader" + e);
				}
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		}
		this.dataMap.put("isError", Boolean.valueOf(true));
		this.dataMap.put("msg", errorMsg);

		constructReturnMap(requestbean);
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
		return JsonUtil.fromObject(this.dataMap);
	}

	@RequestMapping({ "/updateRoleUserMap" })
	@ResponseBody
	public Object updateRoleUserMap(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		RoleUserMapRecord reqBean = new RoleUserMapRecord();
		boolean isError = false;
		String errorMsg = "保存信息失败：请检查输入数据，重新提交！";
		this.dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			reqBean = (RoleUserMapRecord) constructDimensionBean(request, new RoleUserMapRecord(),
					false);
			if (!isDimFilterInputValid(reqBean, false)) {
				isError = true;
				errorMsg = "输入信息有误，请检查数据格式，重新保存！";
			} else {
				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
					    .getAuthentication()
					    .getPrincipal();

				String userId = userDetails.getUsername();
				//String userId = (String) session.getAttribute("HRS_AUTH_USER");
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());

				int updateCount = 0;
				try {
					updateCount = this.allocationRulesSettingService.updateRoleUserMap(reqBean);
				} catch (Exception e) {
					logger.error("Errors while save updateAllocRulesGroupHaeader:" + e);
				}
				if (updateCount == 0) {
					isError = true;
				}
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		}
		if (isError) {
			this.dataMap.put("isError", Boolean.valueOf(true));
			this.dataMap.put("msg", errorMsg);
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
		return JsonUtil.fromObject(this.dataMap);
	}

	@RequestMapping({ "/destroyRoleUserMap" })
	@ResponseBody
	public Object destroyRoleUserMap(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		// get dimension bean from request
		RoleUserMapRecord reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			String id = this.getDecodedRequestParam(request, "ID");
			if (StringUtils.isBlank(id)) {
				isError = true;
				errorMsg = "没有选中需要删除的记录，请选择后再删除！";
			} else {
				reqBean =  new RoleUserMapRecord();
				reqBean.setId(id);
				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
					    .getAuthentication()
					    .getPrincipal();

				String userId = userDetails.getUsername();
				//String userId = (String) session.getAttribute(Constants.USER_KEY);
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				reqBean.setCreatedBy(userId);
				reqBean.setCreationDate(new Date());
				reqBean.setLastUpdatedBy(userId);
				reqBean.setLastUpdateDate(new Date());
				// save dimension into database
				int delCount = 0;
				try {
					delCount = allocationRulesSettingService.deleteRoleUserMap(reqBean);
				} catch (DataReferenceException de) {
					logger.error("Reference record found while delete deleteAllocRulesGroupHaeader value:" + de);
					errorMsg = "错误发生于删除deleteAllocRulesGroupHaeader";
					isError = true;
				} catch (Exception e) {
					logger.error("Error happen while delete dimension value:" + e);
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

	
	@RequestMapping({ "/getRoleList" })
	@ResponseBody
	public Object getRoleList(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		RoleRecord requestbean = new RoleRecord();

		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			requestbean.setRowStartIndex(this.queryRowStartIndex);
			requestbean.setRowCount(this.rows);
		} catch (Exception e) {
			logger.error("Error while get findAllocRulesGroupHaeader:" + e);
		}
		List<Map<String, Object>> list = null;
		try {
			list = this.allocationRulesSettingService.getRoleList(requestbean);
		} catch (Exception e) {
			logger.error("Error while get dimension filter header:" + e);
		}
		JSONArray jsonlist = JsonUtil.fromObject(list);
		this.dataMap.put("rows", jsonlist);
		int count = 0;
		try {
			count = this.allocationRulesSettingService.getCountRoleList(requestbean);
		} catch (Exception e) {
			logger.error("Error while get dimension count:" + e);
		}
		this.dataMap.put("total", Integer.valueOf(count));
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
		return JsonUtil.fromObject(this.dataMap);
	}
	
	
	private boolean isDimFilterInputValid(AuditBean2 auditBean, boolean isCreate) {
		boolean isValid = true;
		if (auditBean == null) {
			isValid = false;
		} else if ((auditBean instanceof DimFilterHeaderBean)) {
			DimFilterHeaderBean dimFilterHeaderBean = (DimFilterHeaderBean) auditBean;
			if ((!isCreate) && ((StringUtils.isBlank(dimFilterHeaderBean.getFilterHeaderName()))
					|| (StringUtils.isBlank(dimFilterHeaderBean.getDimensionSegment())))) {
				isValid = false;
			}
		} else if ((auditBean instanceof DimFilterLineBean)) {
			DimFilterLineBean dimFilterLineBean = (DimFilterLineBean) auditBean;
			if ((!isCreate) && ((dimFilterLineBean.getFilterHeaderId() == 0)
					|| (StringUtils.isBlank(dimFilterLineBean.getValueHigh()))
					|| (StringUtils.isBlank(dimFilterLineBean.getValueLow()))
					|| (StringUtils.isBlank(dimFilterLineBean.getIncExcIndicator())))) {
				isValid = false;
			}
		} else if (auditBean instanceof DriverStaticHeader) {
			DriverStaticHeader driverStaticHeader = (DriverStaticHeader) auditBean;
			if ((!isCreate) && (driverStaticHeader.getStaticHeaderId() == 0)
					|| (StringUtils.isBlank(driverStaticHeader.getDriverCode()))
					|| (StringUtils.isBlank(driverStaticHeader.getDimensionSegment()))) {
				isValid = false;
			}
		} else if (auditBean instanceof DriverStaticLine) {
			DriverStaticLine driverStaticLine = (DriverStaticLine) auditBean;
			if ((!isCreate) && (driverStaticLine.getStaticLineId() == 0)
					|| (StringUtils.isBlank(driverStaticLine.getDimValue()))
					|| (driverStaticLine.getStaticHeaderId() == 0)
					|| (StringUtils.isBlank(driverStaticLine.getDriverName()))
					|| (driverStaticLine.getProportion() == 0)) {
				isValid = false;
			}
		} else if (auditBean instanceof AllocRulesGroupHaeaderBean) {
			AllocRulesGroupHaeaderBean validBean = (AllocRulesGroupHaeaderBean) auditBean;
			if ((!isCreate) && (validBean.getGroupHeaderId() == 0) || (StringUtils.isBlank(validBean.getGroupName()))
					|| (validBean.getGroupNum() == 0)) {
				isValid = false;
			}
		} else if (auditBean instanceof AllocRulesGroupLineBean) {
			AllocRulesGroupLineBean validBean = (AllocRulesGroupLineBean) auditBean;
			if ((!isCreate) && (validBean.getGroupLineId() == 0) || (validBean.getLineNum() == 0)
					|| (validBean.getRuleId() == 0)) {
				isValid = false;
			}
		} else if (auditBean instanceof AllocRuleBean) {
			AllocRuleBean validBean = (AllocRuleBean) auditBean;
			if ((!isCreate) && (validBean.getRuleId() == 0) || (StringUtils.isBlank(validBean.getRuleName()))
					|| (validBean.getLineNum() == 0) || (StringUtils.isBlank(validBean.getDriverType()))) {
				isValid = false;
			}
		} else if (auditBean instanceof AllocSourceAccountBean) {
			AllocSourceAccountBean validBean = (AllocSourceAccountBean) auditBean;
			if ((StringUtils.isBlank(validBean.getDimensionSegment())) || ((validBean.getFilterHeaderId() != 0)
							&& (!StringUtils.isBlank(validBean.getDimensionValue())))) {
				isValid = false;
			}
		}else if (auditBean instanceof AllocTargetAccountBean) {
			AllocTargetAccountBean validBean = (AllocTargetAccountBean) auditBean;
			if ((StringUtils.isBlank(validBean.getDimensionSegment()))) {
				isValid = false;
			}
			if(("VALUE".equals(validBean.getDimAllocType())&&StringUtils.isBlank(validBean.getDimensionValue()))||(!"VALUE".equals(validBean.getDimAllocType())&&!StringUtils.isBlank(validBean.getDimensionValue()))){
				isValid=false;
			}
		} else if (auditBean instanceof AllocDriverAccountBean) {
			AllocDriverAccountBean validBean = (AllocDriverAccountBean) auditBean;
			if ((StringUtils.isBlank(validBean.getDimensionSegment())) || ((validBean.getFilterHeaderId() != 0)
							&& (!StringUtils.isBlank(validBean.getDimensionValue())))) {
				isValid = false;
			}
		}else if (auditBean instanceof RoleUserMapRecord) {
			RoleUserMapRecord validBean = (RoleUserMapRecord) auditBean;
			if ((!isCreate) && (StringUtils.isBlank(validBean.getId()))||(StringUtils.isBlank(validBean.getRoleId())) ||(StringUtils.isBlank(validBean.getUserId())) ) {
				isValid = false;
			}
		}
		return isValid;
	}

	private AuditBean2 constructDimensionBean(HttpServletRequest request, AuditBean2 auditBean, boolean isCreated)
			throws UnsupportedEncodingException {
		if ((auditBean != null) && (request != null)) {
			if ((auditBean instanceof DimFilterHeaderBean)) {
				DimFilterHeaderBean dimFilterHeaderBean = (DimFilterHeaderBean) auditBean;
				String filterHeaderId = getDecodedRequestParam(request, "FILTER_HEADER_ID");
				if (!StringUtils.isEmpty(filterHeaderId)) {
					dimFilterHeaderBean.setFilterHeaderId(Integer.parseInt(filterHeaderId));
				}
				dimFilterHeaderBean.setFilterHeaderName(getDecodedRequestParam(request, "FILTER_HEADER_NAME"));
				dimFilterHeaderBean.setDimensionSegment(getDecodedRequestParam(request, "DIMENSION_SEGMENT"));
				dimFilterHeaderBean.setDescription(getDecodedRequestParam(request, "DESCRIPTION"));
				dimFilterHeaderBean.setType(getDecodedRequestParam(request, "TYPE"));
				return dimFilterHeaderBean;
			}
			if ((auditBean instanceof DimFilterLineBean)) {
				DimFilterLineBean dimFilterLineBean = (DimFilterLineBean) auditBean;
				String filterLineId = getDecodedRequestParam(request, "FILTER_LINE_ID");
				if (!StringUtils.isBlank(filterLineId)) {
					dimFilterLineBean.setFilterLineId(Integer.parseInt(filterLineId));
				}
				dimFilterLineBean.setValueLow(getDecodedRequestParam(request, "VALUE_LOW"));
				dimFilterLineBean.setValueHigh(getDecodedRequestParam(request, "VALUE_HIGH"));
				dimFilterLineBean.setIncExcIndicator(getDecodedRequestParam(request, "INC_EXC_INDICATOR"));
				dimFilterLineBean.setDescription(getDecodedRequestParam(request, "DESCRIPTION"));

				return dimFilterLineBean;
			}
			if (auditBean instanceof DriverStaticHeader) {
				DriverStaticHeader driverStaticHeader = new DriverStaticHeader();

				String staticHeaderId = getDecodedRequestParam(request, "STATIC_HEADER_ID");
				if (!StringUtils.isBlank(staticHeaderId)) {
					driverStaticHeader.setStaticHeaderId(Integer.parseInt(staticHeaderId));
				}
				driverStaticHeader.setDriverCode(getDecodedRequestParam(request, "DRIVER_CODE"));
				driverStaticHeader.setDescription(getDecodedRequestParam(request, "DESCRIPTION"));
				driverStaticHeader.setDimensionSegment(getDecodedRequestParam(request, "DIMENSION_SEGMENT"));
				driverStaticHeader
						.setStartDateActive(this.todate(getDecodedRequestParam(request, "START_DATE_ACTIVE")));
				driverStaticHeader.setEndDateActive(this.todate(getDecodedRequestParam(request, "END_DATE_ACTIVE")));
				return driverStaticHeader;
			}
			if (auditBean instanceof DriverStaticLine) {
				DriverStaticLine driverStaticLine = new DriverStaticLine();

				String staticLineId = getDecodedRequestParam(request, "STATIC_LINE_ID");
				if (!StringUtils.isBlank(staticLineId)) {
					driverStaticLine.setStaticLineId(Integer.parseInt(staticLineId));
				}
				driverStaticLine.setDimValue(getDecodedRequestParam(request, "DIM_VALUE"));
				driverStaticLine.setDriverName(getDecodedRequestParam(request, "DRIVER_NAME"));
				driverStaticLine.setDescription(getDecodedRequestParam(request, "DESCRIPTION"));
				driverStaticLine.setProportion(Double.parseDouble(getDecodedRequestParam(request, "PROPORTION")));
				driverStaticLine.setStartDateActive(this.todate(getDecodedRequestParam(request, "START_DATE_ACTIVE")));
				driverStaticLine.setEndDateActive(this.todate(getDecodedRequestParam(request, "END_DATE_ACTIVE")));
				return driverStaticLine;
			}
			if (auditBean instanceof AllocRulesGroupHaeaderBean) {
				AllocRulesGroupHaeaderBean allocRulesGroupHaeaderBean = new AllocRulesGroupHaeaderBean();

				String groupHeaderId = getDecodedRequestParam(request, "GROUP_HEADER_ID");
				if (!StringUtils.isBlank(groupHeaderId)) {
					allocRulesGroupHaeaderBean.setGroupHeaderId(Integer.parseInt(groupHeaderId));
				}
				allocRulesGroupHaeaderBean.setGroupNum(Integer.parseInt(getDecodedRequestParam(request, "GROUP_NUM")));
				allocRulesGroupHaeaderBean.setGroupName(getDecodedRequestParam(request, "GROUP_NAME"));
				allocRulesGroupHaeaderBean.setDescription(getDecodedRequestParam(request, "DESCRIPTION"));
				allocRulesGroupHaeaderBean
						.setStartDateActive(this.todate(getDecodedRequestParam(request, "START_DATE_ACTIVE")));
				allocRulesGroupHaeaderBean
						.setEndDateActive(this.todate(getDecodedRequestParam(request, "END_DATE_ACTIVE")));
				return allocRulesGroupHaeaderBean;
			}
			if (auditBean instanceof AllocRulesGroupLineBean) {
				AllocRulesGroupLineBean allocRulesGroupLineBean = new AllocRulesGroupLineBean();

				String groupLineId = getDecodedRequestParam(request, "GROUP_LINE_ID");
				if (!StringUtils.isBlank(groupLineId)) {
					allocRulesGroupLineBean.setGroupLineId(Integer.parseInt(groupLineId));
				}
				allocRulesGroupLineBean.setLineNum(Integer.parseInt(getDecodedRequestParam(request, "LINE_NUM")));
				allocRulesGroupLineBean.setRuleId(Integer.parseInt(getDecodedRequestParam(request, "RULE_ID")));
				return allocRulesGroupLineBean;
			}
			if (auditBean instanceof AllocRuleBean) {
				AllocRuleBean allocRuleBean = new AllocRuleBean();

				String ruleId = getDecodedRequestParam(request, "RULE_ID");
				if (!StringUtils.isBlank(ruleId)) {
					allocRuleBean.setRuleId(Integer.parseInt(ruleId));
				}
				allocRuleBean.setLineNum(Integer.parseInt(getDecodedRequestParam(request, "LINE_NUM")));
				allocRuleBean.setRuleName(getDecodedRequestParam(request, "RULE_NAME"));
				allocRuleBean.setDescription(getDecodedRequestParam(request, "DESCRIPTION"));
				allocRuleBean.setDriverType(getDecodedRequestParam(request, "DRIVER_TYPE"));
				allocRuleBean.setStartDateActive(this.todate(getDecodedRequestParam(request, "START_DATE_ACTIVE")));
				allocRuleBean.setEndDateActive(this.todate(getDecodedRequestParam(request, "END_DATE_ACTIVE")));

				/*
				 * there is some parameter not use
				 * allocRuleBean.setSourceId(Integer.parseInt(
				 * getDecodedRequestParam(request, "SOURCE_ID")));
				 * allocRuleBean.setDriverId(Integer.parseInt(
				 * getDecodedRequestParam(request, "DRIVER_ID")));
				 * allocRuleBean.setTargetId(Integer.parseInt(
				 * getDecodedRequestParam(request, "TARGET_ID")));
				 * allocRuleBean.setOffsetId(Integer.parseInt(
				 * getDecodedRequestParam(request, "OFFSET_ID")));
				 */
				return allocRuleBean;
			}
			if (auditBean instanceof AllocSourceAccountBean) {
				AllocSourceAccountBean allocSourceAccountBean = new AllocSourceAccountBean();

				String sourceAccId = getDecodedRequestParam(request, "SOURCE_ACC_ID");
				if (!StringUtils.isBlank(sourceAccId)) {
					allocSourceAccountBean.setSourceAccId(Integer.parseInt(sourceAccId));
				}
				String filterHeaderId=getDecodedRequestParam(request, "FILTER_HEADER_ID");
				if(!StringUtils.isBlank(filterHeaderId)){
				allocSourceAccountBean
						.setFilterHeaderId(Integer.parseInt(filterHeaderId));
				}else{
					allocSourceAccountBean
					.setFilterHeaderId(0);	
				}
				allocSourceAccountBean.setDimensionSegment(getDecodedRequestParam(request, "DIMENSION_SEGMENT"));
				allocSourceAccountBean.setDimensionValue(getDecodedRequestParam(request, "DIMENSION_VALUE"));
				allocSourceAccountBean.setDescription(getDecodedRequestParam(request, "DESCRIPTION"));
				String sourceId=getDecodedRequestParam(request, "SOURCE_ID");
				if(!StringUtils.isBlank(sourceId)){
					allocSourceAccountBean.setSourceId(Integer.parseInt(sourceId));
				}
				return allocSourceAccountBean;
			}
			if (auditBean instanceof AllocTargetAccountBean) {
				AllocTargetAccountBean allocTargetAccountBean = new AllocTargetAccountBean();

				String targetAccId = getDecodedRequestParam(request, "TARGET_ACC_ID");
				if (!StringUtils.isBlank(targetAccId)) {
					allocTargetAccountBean.setTargetAccId(Integer.parseInt(targetAccId));
				}
				allocTargetAccountBean.setDimAllocType(getDecodedRequestParam(request, "DIM_AllOC_TYPE"));
				allocTargetAccountBean.setDimensionSegment(getDecodedRequestParam(request, "DIMENSION_SEGMENT"));
				allocTargetAccountBean.setDimensionValue(getDecodedRequestParam(request, "DIMENSION_VALUE"));
				allocTargetAccountBean.setDescription(getDecodedRequestParam(request, "DESCRIPTION"));
				String targetId=getDecodedRequestParam(request, "TARGET_ID");
				if(!StringUtils.isBlank(targetId)){
					allocTargetAccountBean.setTargetId(Integer.parseInt(targetId));
				}
				return allocTargetAccountBean;
			}if(auditBean instanceof RoleUserMapRecord){
				RoleUserMapRecord roleUserMapRecord =new RoleUserMapRecord();
				if(!StringUtils.isBlank(getDecodedRequestParam(request, "ID"))){
					roleUserMapRecord.setId(getDecodedRequestParam(request, "ID"));
				}
				roleUserMapRecord.setRoleId(getDecodedRequestParam(request, "ROLE_ID"));
				roleUserMapRecord.setUserId(getDecodedRequestParam(request, "USER_ID"));
				return roleUserMapRecord;
			}
			if (auditBean instanceof AllocDriverAccountBean) {
				AllocDriverAccountBean allocDriverAccountBean = new AllocDriverAccountBean();

				String driverAccId = getDecodedRequestParam(request, "DRVIER_ACC_ID");
				if (!StringUtils.isBlank(driverAccId)) {
					allocDriverAccountBean.setDrvierAccId(Integer.parseInt(driverAccId));
				}
				String filterHeaderId=getDecodedRequestParam(request, "FILTER_HEADER_ID");
				if(!StringUtils.isBlank(filterHeaderId)){
					allocDriverAccountBean
						.setFilterHeaderId(Integer.parseInt(filterHeaderId));
				}else{
					allocDriverAccountBean
					.setFilterHeaderId(0);	
				}
				allocDriverAccountBean.setDimensionSegment(getDecodedRequestParam(request, "DIMENSION_SEGMENT"));
				allocDriverAccountBean.setDimensionValue(getDecodedRequestParam(request, "DIMENSION_VALUE"));
				allocDriverAccountBean.setDescription(getDecodedRequestParam(request, "DESCRIPTION"));
				String driverId=getDecodedRequestParam(request, "DRVIER_ID");
				if(!StringUtils.isBlank(driverId)){
					allocDriverAccountBean.setDrvierId(Integer.parseInt(driverId));
				}
				return allocDriverAccountBean;
			}
		}
		return null;
	}

	private Date todate(String dateString) {
		Date date = null;
		if (StringUtils.isBlank(dateString)) {
			return null;
		} else {
			SimpleDateFormat formater = new SimpleDateFormat();
			formater.applyPattern("yyyy-MM-dd");
			try {
				date = formater.parse(dateString);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return date;
		}
	}
}
