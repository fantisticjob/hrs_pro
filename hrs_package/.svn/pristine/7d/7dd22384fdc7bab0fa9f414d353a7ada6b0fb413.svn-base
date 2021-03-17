package com.hausontech.hrs.web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

import com.hausontech.hrs.api.constants.Constants;
import com.hausontech.hrs.api.dimensionManager.service.IRptGeneratorConfigService;
import com.hausontech.hrs.bean.AuditBean2;
import com.hausontech.hrs.bean.dimensionManager.DimensionBean;
import com.hausontech.hrs.bean.dimensionManager.DimensionSonValueBean;
import com.hausontech.hrs.bean.dimensionManager.DimensionValueBean;
import com.hausontech.hrs.bean.reportSetting.ItemLookUpHeaderBean;
import com.hausontech.hrs.exceptions.DataReferenceException;
import com.hausontech.hrs.utils.Comp;
import com.hausontech.hrs.utils.JsonUtil;
import com.hausontech.hrs.utils.Loap;
import com.hausontech.hrs.utils.StringUtil;
import com.hausontech.hrs.utils.Tan;
import com.hausontech.hrs.utils.ThirdLevelType;

import net.sf.json.JSONArray;

@Controller
@RequestMapping(value = "/dimension")
public class DimensionController extends BaseController {
	
	/** 组件日志 */ 
	private static Logger logger = LoggerFactory.getLogger(DimensionController.class);  
	
	@Resource
	private IRptGeneratorConfigService rptGenConfigService;
	
	private static final String SESSION_KEY_DIMVALUE="dimension_dimvalue";
	private static final String SESSION_KEY_DIMENSIONID="dimension_dimensionid";
	private static final String SESSION_KEY_DIMENSION_HEADER = "dimension_header_key";
	public IRptGeneratorConfigService getRptGenConfigService() {
		return rptGenConfigService;
	}

	public void setRptGenConfigService(IRptGeneratorConfigService rptGenConfigService) {
		this.rptGenConfigService = rptGenConfigService;
	}
	
	@RequestMapping(value = "/findDimensionConfig")
	@ResponseBody
	public Object findDimensionInfo(HttpServletRequest request,HttpServletResponse response) {
		// 参数
		DimensionBean dimBean = new DimensionBean();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
			
		try {
			//handle the pagination case, initialize page related information
			this.initilizePagingInfo(request);
			//set query start index
			dimBean.setRowStartIndex(this.queryRowStartIndex);
			//set row count
			dimBean.setRowCount(this.rows);
			//get other parameter from request
			dimBean.setDimensionCode(this.getDecodedRequestParam(request, "dimensionCode"));
			dimBean.setDimensionName(this.getDecodedRequestParam(request, "dimensionName"));
		} catch (UnsupportedEncodingException e1) {
			logger.error("Error while get dimension:" + e1);
		}
		List<Map<String, Object>> list = null;
		try {
			list = rptGenConfigService.getByCondition(dimBean);
		} catch (Exception e) {
			logger.error("Error while get dimension:" + e);
		}
		JSONArray jsonlist = JsonUtil.fromObject(list);
		dataMap.put("rows", jsonlist);
		int count = 0;
		try {
			count = rptGenConfigService.getCountByCondition(dimBean);
		} catch (Exception e) {
			logger.error("Error while get dimension count:" + e);
		}
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
	
	@RequestMapping(value = "/getSegmentList")
	@ResponseBody
	public Object getSegmentList(HttpServletRequest request,HttpServletResponse response
) {
		
		//Construct dimensionSegList
		List<Map<String, Object>> demensionlist = new ArrayList<Map<String, Object>>();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
	
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("dimSegmentId", "SEGMENT1");
		map.put("dimSegmentName", "SEGMENT1");
		demensionlist.add(map);
		
		map = new HashMap<String, Object>();
		map.put("dimSegmentId", "SEGMENT2");
		map.put("dimSegmentName", "SEGMENT2");
		demensionlist.add(map);
		
		map = new HashMap<String, Object>();
		map.put("dimSegmentId", "SEGMENT3");
		map.put("dimSegmentName", "SEGMENT3");
		demensionlist.add(map);
		
		map = new HashMap<String, Object>();
		map.put("dimSegmentId", "SEGMENT4");
		map.put("dimSegmentName", "SEGMENT4");
		demensionlist.add(map);
		
		map = new HashMap<String, Object>();
		map.put("dimSegmentId", "SEGMENT5");
		map.put("dimSegmentName", "SEGMENT5");
		demensionlist.add(map);
		
		map = new HashMap<String, Object>();
		map.put("dimSegmentId", "SEGMENT6");
		map.put("dimSegmentName", "SEGMENT6");
		demensionlist.add(map);
		
		map = new HashMap<String, Object>();
		map.put("dimSegmentId", "SEGMENT7");
		map.put("dimSegmentName", "SEGMENT7");
		demensionlist.add(map);
		
		map = new HashMap<String, Object>();
		map.put("dimSegmentId", "SEGMENT8");
		map.put("dimSegmentName", "SEGMENT8");
		demensionlist.add(map);
		
		map = new HashMap<String, Object>();
		map.put("dimSegmentId", "SEGMENT9");
		map.put("dimSegmentName", "SEGMENT9");
		demensionlist.add(map);
		
		map = new HashMap<String, Object>();
		map.put("dimSegmentId", "SEGMENT10");
		map.put("dimSegmentName", "SEGMENT10");
		demensionlist.add(map);
		
		map = new HashMap<String, Object>();
		map.put("dimSegmentId", "SEGMENT11");
		map.put("dimSegmentName", "SEGMENT11");
		demensionlist.add(map);
		
		map = new HashMap<String, Object>();
		map.put("dimSegmentId", "SEGMENT12");
		map.put("dimSegmentName", "SEGMENT12");
		demensionlist.add(map);
		
		map = new HashMap<String, Object>();
		map.put("dimSegmentId", "SEGMENT13");
		map.put("dimSegmentName", "SEGMENT13");
		demensionlist.add(map);
		
		map = new HashMap<String, Object>();
		map.put("dimSegmentId", "SEGMENT14");
		map.put("dimSegmentName", "SEGMENT14");
		demensionlist.add(map);
		
		map = new HashMap<String, Object>();
		map.put("dimSegmentId", "SEGMENT15");
		map.put("dimSegmentName", "SEGMENT15");
		demensionlist.add(map);
		
		map = new HashMap<String, Object>();
		map.put("dimSegmentId", "SEGMENT16");
		map.put("dimSegmentName", "SEGMENT16");
		demensionlist.add(map);
		
		map = new HashMap<String, Object>();
		map.put("dimSegmentId", "SEGMENT17");
		map.put("dimSegmentName", "SEGMENT17");
		demensionlist.add(map);
		
		map = new HashMap<String, Object>();
		map.put("dimSegmentId", "SEGMENT18");
		map.put("dimSegmentName", "SEGMENT18");
		demensionlist.add(map);
		
		map = new HashMap<String, Object>();
		map.put("dimSegmentId", "SEGMENT19");
		map.put("dimSegmentName", "SEGMENT19");
		demensionlist.add(map);
		
		map = new HashMap<String, Object>();
		map.put("dimSegmentId", "SEGMENT20");
		map.put("dimSegmentName", "SEGMENT20");
		demensionlist.add(map);
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
		return JsonUtil.fromObject(demensionlist);	
	}
	
	@RequestMapping(value = "/getAccountTypeList")
	@ResponseBody
	public Object getAccountTypeList(HttpServletRequest request,HttpServletResponse response) {
		//Construct dimensionSegList
		List<Map<String, Object>> demensionlist = new ArrayList<Map<String, Object>>();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
	
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("acctTypeId", "A");
		map.put("acctTypeName", "资产");
		demensionlist.add(map);
		
		map = new HashMap<String, Object>();
		map.put("acctTypeId", "L");
		map.put("acctTypeName", "负债");
		demensionlist.add(map);
		
		map = new HashMap<String, Object>();
		map.put("acctTypeId", "O");
		map.put("acctTypeName", "所有者权益");
		demensionlist.add(map);
		
		map = new HashMap<String, Object>();
		map.put("acctTypeId", "R");
		map.put("acctTypeName", "收入权益");
		demensionlist.add(map);
		
		map = new HashMap<String, Object>();
		map.put("acctTypeId", "E");
		map.put("acctTypeName", "费用权益");
		demensionlist.add(map);
		
		map = new HashMap<String, Object>();
		map.put("acctTypeId", "E");
		map.put("acctTypeName", "统计");
		demensionlist.add(map);
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
		return JsonUtil.fromObject(demensionlist);
		
	}
	
	@RequestMapping(value = "/getDimLevelList")
	@ResponseBody
	public Object getDimLevelList(HttpServletRequest request,HttpServletResponse response) {
		
		//Construct dimensionSegList
		List<Map<String, Object>> demensionlist = new ArrayList<Map<String, Object>>();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
			

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dimLevId", "LEV1");
		map.put("dimLevName", "一级科目");
		demensionlist.add(map);
		
		map = new HashMap<String, Object>();
		map.put("dimLevId", "LEV2");
		map.put("dimLevName", "二级科目");
		demensionlist.add(map);
		
		map = new HashMap<String, Object>();
		map.put("dimLevId", "LEV3");
		map.put("dimLevName", "三级科目");
		demensionlist.add(map);
		
		map = new HashMap<String, Object>();
		map.put("dimLevId", "LEV3");
		map.put("dimLevName", "四级科目");
		demensionlist.add(map);
		
		map = new HashMap<String, Object>();
		map.put("dimLevId", "LEV3");
		map.put("dimLevName", "五级科目");
		demensionlist.add(map);
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
		return JsonUtil.fromObject(demensionlist);
		
	}
	
	
	@RequestMapping(value = "/saveDimensionConfig")
	@ResponseBody
	public Object saveDimensionInfo(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		//get dimension bean from request
		DimensionBean reqBean = null;
		boolean isError = false;
		String errorMsg = "保存信息失败：请检查输入数据，重新提交！";
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){

		try {
			reqBean = (DimensionBean)this.constructDimensionBean(request, new DimensionBean(), true);
			if (!this.isDimensionInputValid(reqBean, true)) {
				isError = true;
				errorMsg = "输入信息有误，请检查数据格式，重新保存！";
			} else {
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
				//save dimension into database
				long newKey = 0;
				try {
					newKey = rptGenConfigService.createNewDimension(reqBean);
				} catch (DuplicateKeyException de) {
					//System.out.println(de);
					logger.error("saveDimensionInfo():" + de);
					errorMsg = "不能存在重复的维度字段，请重新选择后再次提交";
				} catch (Exception e) {
					logger.error("saveDimensionInfo():" + e);
					//System.out.println(e);
				}
				if (newKey == 0) {
					isError = true;
				}
				reqBean.setDimensionId(newKey);
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
	
	@RequestMapping(value = "/updateDimensionConfig")
	@ResponseBody
	public Object updateDimensionInfo(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		//get dimension bean from request
		DimensionBean reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
	
		try {
			reqBean = (DimensionBean)this.constructDimensionBean(request, new DimensionBean(), false);
			if (!this.isDimensionInputValid(reqBean, false)) {
				isError = true;
				errorMsg = "输入信息有误，请检查数据格式，重新保存！";
			} else {
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
				//save dimension into database
				int updateCount = 0;
				try {
					updateCount = rptGenConfigService.updateDimensionHeader(reqBean);
				} catch (Exception e) {
					//System.out.println(e);
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
	
	@RequestMapping(value = "/showDimensionValueView")
	public String viewDimensionValuePage(HttpServletRequest request,HttpServletResponse response, Model model) {
		//get dimensionId
		String demensionId= null;
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
			

		try {
			demensionId = this.getDecodedRequestParam(request, "demensionId");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!StringUtils.isBlank(demensionId)) {
			DimensionBean parentBean = null;
			try {
				parentBean = rptGenConfigService.getDimensionByPrimaryKey(Long.parseLong(demensionId));
			} catch (NumberFormatException ne) {
				// TODO Auto-generated catch block
				ne.printStackTrace();
			} catch(Exception e) {
				
			}
			if (parentBean != null) {
				model.addAttribute("dimensionBean", parentBean);
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
		return "configuration/dimensionValueList";
	}
	
	
	@RequestMapping(value = "/findDimensionValueList")
	@ResponseBody
	public Object findDimensionValueList(HttpServletRequest request,HttpServletResponse response, HttpSession httpSession) {
		// 参数
		DimensionValueBean dimValueBean = new DimensionValueBean();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
	
		try {
		    //handle the pagination case, initialize page related information
			this.initilizePagingInfo(request);
			//set query start index
			dimValueBean.setRowStartIndex(this.queryRowStartIndex);
			//set row count
			dimValueBean.setRowCount(this.rows);
			//get other parameter from request
			dimValueBean.setDimensionValue(this.getDecodedRequestParam(request, "dimValueCode"));
			dimValueBean.setDescription(this.getDecodedRequestParam(request, "dimValueName"));
		} catch (UnsupportedEncodingException e1) {
			logger.error("Errors while get dimension value list:" + e1);
		}
		
		String dimensionId = null;
		try {
			dimensionId = this.getDecodedRequestParam(request, "dimensionId");
		} catch (UnsupportedEncodingException e1) {
			logger.error("Errors while save dimension value list:" + e1);
		}
		if (!StringUtils.isBlank(dimensionId)) {
			dimValueBean.setDimensionId(Long.valueOf(dimensionId));
			httpSession.removeAttribute(SESSION_KEY_DIMENSION_HEADER);
			httpSession.setAttribute(SESSION_KEY_DIMENSION_HEADER, dimensionId);
		}		
		List<Map<String, Object>> list = null;
		try {
			list = rptGenConfigService.getDimensionValueListByCondition(dimValueBean);
		} catch (Exception e) {
			//System.out.println(e);
			logger.error("Errors while save dimension value list:" + e);
		}
		int count = 0;
		try {
			count = rptGenConfigService.getCountByCondition(dimValueBean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
	
	@RequestMapping(value = "/getDimValList")
	@ResponseBody
	public Object getDimValList(HttpServletRequest request,HttpServletResponse response, HttpSession httpSession) {
		// 参数
		boolean isError = false;
		String errorMsg = "";
		DimensionValueBean dimValueBean = new DimensionValueBean();
		ItemLookUpHeaderBean lookUpHeaderBean = new ItemLookUpHeaderBean();
		List<Map<String, Object>> list = null;
		String dimCode = null;
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
			

		try {
			//GET DIM id FOR QUERY
			dimCode = this.getDecodedRequestParam(request, "dimCode");
			if (StringUtil.isEmptyTrim(dimCode)) {
				isError = true;
				errorMsg = "Dimension Code is empty";
			} else {
				//process ledger and fin element
				if ("FIN_ELEMENT".equals(dimCode) || "LEDGER_ID".equals(dimCode)) {
					//lookUpHeaderBean.setLookUpType(dimCode);
					if("FIN_ELEMENT".equals(dimCode)){
						lookUpHeaderBean.setLookUpType("HRS_CORE_FIN_ELEMENT");
					}
					if("LEDGER_ID".equals(dimCode)){
						lookUpHeaderBean.setLookUpType("HRS_CORE_LEDGER");
					}
					//get search operation
					String operation = this.getDecodedRequestParam(request, "performOperation");
					if (!StringUtils.isBlank(operation) && operation.equals("comboSearch")) {
						lookUpHeaderBean.setLookupValSearch(this.getDecodedRequestParam(request, "comboxDimCode"));
					} else {
						String inputQuery = this.getDecodedRequestParam(request, "q");
						if (!StringUtils.isBlank(inputQuery)) {
							lookUpHeaderBean.setLookupValSearch(Constants.PERCENT_CHARACTER + inputQuery.trim() + Constants.PERCENT_CHARACTER);
						}
					}
				    //handle the pagination case, initialize page related information
					this.initilizePagingInfo(request);
					//set query start index
					lookUpHeaderBean.setRowStartIndex(this.queryRowStartIndex);
					//set row count
					lookUpHeaderBean.setRowCount(this.rows);
					//get other parameter from request
					list = rptGenConfigService.getLookUpValueListAsDimension(lookUpHeaderBean);
					
				} else { //process dimension
					DimensionBean  dimension = 
							rptGenConfigService.getDimensionByDimSegment(dimCode.trim());
					if (dimension == null || dimension.getDimensionId() == 0) {
						isError = true;
						errorMsg = "No Dimension found in DB, dimensionCode = " +  dimCode;
					}
					if (!isError) {
						//set dim id
						dimValueBean.setDimensionId(dimension.getDimensionId());
						//get search operation
						String operation = this.getDecodedRequestParam(request, "performOperation");
						if (!StringUtils.isBlank(operation) && operation.equals("comboSearch")) {
							dimValueBean.setDimensionValue(this.getDecodedRequestParam(request, "comboxDimCode"));
						} else {
							String inputQuery = this.getDecodedRequestParam(request, "q");
							if (!StringUtils.isBlank(inputQuery)) {
								dimValueBean.setDimensionValue(Constants.PERCENT_CHARACTER + inputQuery.trim() + Constants.PERCENT_CHARACTER);
							}
						}
						dimValueBean.setUserName(this.getLoginUserName(request));
						String securityFlag = this.getDecodedRequestParam(request, "security");
						if (!StringUtil.isEmptyTrim(securityFlag) && "true".equals(securityFlag)) {
							dimValueBean.setSecurityEnabled(true);
						} else {
							dimValueBean.setSecurityEnabled(false);
						}
					    //handle the pagination case, initialize page related information
						this.initilizePagingInfo(request);
						//set query start index
						dimValueBean.setRowStartIndex(this.queryRowStartIndex);
						//set row count
						dimValueBean.setRowCount(this.rows);
						//set summary flag is "N"
				        dimValueBean.setSummaryFlag("N");
						//get other parameter from request
						list = rptGenConfigService.getDimensionValueListByCondition(dimValueBean);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Errors while save dimension value list:" + e);
			isError = true;
			errorMsg = "Errors while save dimension value list";
		} 
		int count = 0;
		if (isError) {
			dataMap.put("isError", true);
			dataMap.put("msg", errorMsg);
		} else {
			if ("FIN_ELEMENT".equals(dimCode) || "LEDGER_ID".equals(dimCode)) {
				try {
					count = rptGenConfigService.countLookUpValueListByCondition(lookUpHeaderBean);
				} catch (Exception e) {
					logger.error("Errors while get lookup value count:" + e);
				}				
			} else {
				try {
					count = rptGenConfigService.getCountByCondition(dimValueBean);
				} catch (Exception e) {
					logger.error("Errors while get dimension value count:" + e);
				}
			}
			JSONArray jsonlist = JsonUtil.fromObject(list);	
			dataMap.put("rows", jsonlist);
			dataMap.put("total", count);
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
	
	@RequestMapping(value = "/getDimValueDispName")
	@ResponseBody
	public Object getDimensionValueDispName(HttpServletRequest request,HttpServletResponse response, HttpSession httpSession) {
		// 参数
		DimensionValueBean dimValueBean = new DimensionValueBean();	
		ItemLookUpHeaderBean lookUpHeaderBean = new ItemLookUpHeaderBean();
		List<DimensionValueBean> list = null;
		List<Map<String, Object>> otherList = null;
		boolean isError = false;
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){

		try {			
			//GET DIM id FOR QUERY
			String dimCode = this.getDecodedRequestParam(request, "dimCode");
			if (StringUtil.isEmptyTrim(dimCode)) {
				isError = true;
			} else {
				String dimValue = this.getDecodedRequestParam(request, "selDimValueCode");
				if ("FIN_ELEMENT".equals(dimCode) || "LEDGER_ID".equals(dimCode)) {
					//lookUpHeaderBean.setLookUpType(dimCode);
					if("FIN_ELEMENT".equals(dimCode)){
						lookUpHeaderBean.setLookUpType("HRS_CORE_FIN_ELEMENT");
					}
					if("LEDGER_ID".equals(dimCode)){
						lookUpHeaderBean.setLookUpType("HRS_CORE_LEDGER");
					}
					lookUpHeaderBean.setLookupValSearch(dimValue);
					//get other parameter from request
					otherList = rptGenConfigService.getLookUpValueListAsDimension(lookUpHeaderBean);
				} else {
					DimensionBean  dimension = 
							rptGenConfigService.getDimensionByDimSegment(dimCode.trim());
					if (!isError) {
						//set dim id
						dimValueBean.setDimensionId(dimension.getDimensionId());
						//try to set value code
						dimValueBean.setDimensionValue(dimValue);
						//get other parameter from request
						list = rptGenConfigService.getDimensionValueByCondition(dimValueBean);
					}
				}
			}

		} catch (Exception e) {
			logger.error("Errors while save dimension value list:" + e);
		}
		if (list != null && 0 < list.size()) {
			DimensionValueBean result = list.get(0);
			dataMap = new HashMap();
			dataMap.put("DIMENSION_ID", result.getDimensionId());
			dataMap.put("DIM_VALUE", result.getDimensionValue());
			dataMap.put("DESCRIPTION", result.getDescription());
			dataMap.put("DISP_VALUE", result.getDimensionValue() + "-" + result.getDescription());
			return JsonUtil.fromObject(dataMap);
		} else if (otherList != null && 0 < otherList.size()) {
			dataMap = new HashMap();
			dataMap.putAll(otherList.get(0));
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

	@RequestMapping(value = "/saveDimensionValue")
	@ResponseBody
	public Object saveDimensionValue(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		//get dimension bean from request
		DimensionValueBean reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();

		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			reqBean = (DimensionValueBean)this.constructDimensionBean(request, new DimensionValueBean(), true);
			//try to get header it from session
			String dimensionId = null;
			if (session.getAttribute(SESSION_KEY_DIMENSION_HEADER) != null) {
				dimensionId = (String)session.getAttribute(SESSION_KEY_DIMENSION_HEADER);
			}
			if (!StringUtils.isBlank(dimensionId)) {
				reqBean.setDimensionId(Integer.parseInt(dimensionId));
			}
			if (!this.isDimensionInputValid(reqBean, true)) {
				isError = true;
				errorMsg = "输入信息有误，请检查数据格式，重新保存！";
			} else {
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
				reqBean.setStartDate(new Date());
				reqBean.setSource("MANNUAL");
				//save dimension into database
				int newKey = 0;
				try {
					newKey = rptGenConfigService.createNewDimensionValueRecord(reqBean);
				} catch (DuplicateKeyException de) {
					//System.out.println(de);
					logger.error("saveDimensionValue():" + de);
					errorMsg = "不能存在重复的维度名称，请重新输入再次提交";
				} catch (Exception e) {
					//System.out.println(e);
					logger.error("Errors while save dimensionv value:" + e);
				}
				if (newKey == 0) {
					isError = true;
				}
				reqBean.setDimensionValueId(newKey);
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
	
	@RequestMapping(value = "/updateDimensionValue")
	@ResponseBody
	public Object updateDimensionValue(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		//get dimension bean from request
		DimensionValueBean reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
			

		try {
			reqBean = (DimensionValueBean)this.constructDimensionBean(request, new DimensionValueBean(), false);
			if (!this.isDimensionInputValid(reqBean, false)) {
				isError = true;
				errorMsg = "输入信息有误，请检查数据格式，重新保存！";
			} else {
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
				reqBean.setSource("MANNUAL");
				//save dimension into database
				int updateCount = 0;
				try {
					updateCount = rptGenConfigService.updateDimensionValueRecord(reqBean);
				} catch (DuplicateKeyException de) {
					logger.error("saveDimensionValue():" + de);
					errorMsg = "不能存在重复的维度名称，请重新输入再次提交";
				} catch (Exception e) {
					logger.error("Errors while update dimension value:" + e);
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
	
	@RequestMapping(value = "/deleteDimensionValue")
	@ResponseBody
	public Object deleteDimensionValue(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		//get dimension bean from request
		DimensionValueBean reqBean = null;
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
				reqBean = new DimensionValueBean();
				reqBean.setDimensionValueId(Long.parseLong(ruleLineId));
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
				//save dimension into database
				int delCount = 0;
				try {
					delCount = rptGenConfigService.deleteDimensionValueRecord(reqBean);
				} catch (DataReferenceException de) {
					// TODO Auto-generated catch block
					logger.error("Reference record found while delete dimension value:" + de);
					errorMsg = "报表表项引用循环组，请先删除或更新使用该循环组的表项，再删除循环组！";
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
	
	
	@RequestMapping(value = "/showDimensionValueSubList")
	public String showDimensionValueSubList(HttpServletRequest request,HttpServletResponse response, Model model) {
		//get demensionValueId
		String demensionValueId= null;
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
			

		try {
			demensionValueId = this.getDecodedRequestParam(request, "demensionValueId");
		} catch (UnsupportedEncodingException e) {
			logger.error("showDimensionValueSubList():" + e);
		}
		if (!StringUtils.isBlank(demensionValueId)) {
			DimensionBean parentBean = null;
			try {
				parentBean = rptGenConfigService.getDimensionByPrimaryKey(Long.parseLong(demensionValueId));
			} catch (NumberFormatException ne) {
				logger.error("showDimensionValueSubList():" + ne);
			} catch (Exception e) {
				logger.error("showDimensionValueSubList():" + e);
			}
			if (parentBean != null) {
				model.addAttribute("dimensionBean", parentBean);
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
		return "configuration/dimensionValueSubList";
	}
		
	
	private AuditBean2 constructDimensionBean(
			HttpServletRequest request, AuditBean2 auditBean, boolean isCreated) throws UnsupportedEncodingException {	
		
		if (request != null && auditBean != null) {
			
			if (auditBean instanceof DimensionBean) {
				DimensionBean requestDimBean = (DimensionBean)auditBean;
				String dimensionId = this.getDecodedRequestParam(request, "DIMENSION_ID");
				if (!StringUtils.isBlank(dimensionId)) {
					requestDimBean.setDimensionId(Long.parseLong(dimensionId));
				}
				//set dimension sequence
				String dimensionSeqNum = this.getDecodedRequestParam(request, "DIM_SEQ");
				if (!StringUtils.isBlank(dimensionSeqNum)) {
					requestDimBean.setDimSeq(Long.parseLong(dimensionSeqNum));
				}
				//set dimension code
				requestDimBean.setDimensionCode(this.getDecodedRequestParam(request, "DIMENSION_CODE"));
				//set dimension name
				requestDimBean.setDimensionName(this.getDecodedRequestParam(request, "DIMENSION_NAME"));
				//set dimension segment
				requestDimBean.setDimSegment(this.getDecodedRequestParam(request, "DIM_SEGMENT"));
				//set finance account flag
				requestDimBean.setFinAccountFlag(this.getDecodedRequestParam(request, "FIN_ACCOUNT_FLAG"));	
				return requestDimBean;
			} else if (auditBean instanceof DimensionValueBean) {
				DimensionValueBean dmValueBean = (DimensionValueBean)auditBean;
				String dimensionValueId = this.getDecodedRequestParam(request, "DIM_VALUE_ID");
				if (!StringUtils.isBlank(dimensionValueId)) {
					dmValueBean.setDimensionValueId(Long.parseLong(dimensionValueId));
				}
				if (!isCreated) {
					String dimensionId = this.getDecodedRequestParam(request, "DIMENSION_ID");
					if (!StringUtils.isBlank(dimensionId)) {
						dmValueBean.setDimensionId(Long.parseLong(dimensionId));
					}
				}
				//set value code
				dmValueBean.setDimensionValue(this.getDecodedRequestParam(request, "DIM_VALUE"));
				//set value name
				dmValueBean.setDescription(this.getDecodedRequestParam(request, "DESCRIPTION"));
				//account type
				String acctType = this.getDecodedRequestParam(request, "ACCOUNT_TYPE");
				if (StringUtils.isBlank(acctType) || "-1".equals(acctType)) {
					dmValueBean.setAccountType("-1");
				} else {
					dmValueBean.setAccountType(acctType);
				}
				//set dim level
				dmValueBean.setDimensionLevel(this.getDecodedRequestParam(request, "DIM_LEVEL"));
				//set summary flag
				dmValueBean.setSummaryFlag(this.getDecodedRequestParam(request, "SUMMARY_FLAG"));			
				//set attribute1
				dmValueBean.setAttribute1(this.getDecodedRequestParam(request, "ATTRIBUTE1"));
				//set attribute2
				dmValueBean.setAttribute2(this.getDecodedRequestParam(request, "ATTRIBUTE2"));
				//set attribute3
				dmValueBean.setAttribute3(this.getDecodedRequestParam(request, "ATTRIBUTE3"));
				//set attribute4
				dmValueBean.setAttribute4(this.getDecodedRequestParam(request, "ATTRIBUTE4"));
				//set attribute5
				dmValueBean.setAttribute5(this.getDecodedRequestParam(request, "ATTRIBUTE5"));
				//set attribute6
				dmValueBean.setAttribute6(this.getDecodedRequestParam(request, "ATTRIBUTE6"));
				//set attribute7
				dmValueBean.setAttribute7(this.getDecodedRequestParam(request, "ATTRIBUTE7"));
				//set attribute8
				dmValueBean.setAttribute8(this.getDecodedRequestParam(request, "ATTRIBUTE8"));
				//set attribute9
				dmValueBean.setAttribute9(this.getDecodedRequestParam(request, "ATTRIBUTE9"));
				//set attribute10
				dmValueBean.setAttribute10(this.getDecodedRequestParam(request, "ATTRIBUTE10"));
				return dmValueBean;				
			}else if (auditBean instanceof DimensionSonValueBean) {
				DimensionSonValueBean dmValueBean = (DimensionSonValueBean)auditBean;
				String hierarchyId = this.getDecodedRequestParam(request, "HIERARCHY_ID");
				if (!StringUtils.isBlank(hierarchyId)) {
					dmValueBean.setHierarchyId(Long.parseLong(hierarchyId));
				}
				if (!isCreated) {
					String dimensionId = this.getDecodedRequestParam(request, "DIMENSION_ID");
					String dimValue =this.getDecodedRequestParam(request, "DIM_VALUE");
					if (!StringUtils.isBlank(dimensionId)&&!StringUtils.isBlank(dimensionId)) {
						dmValueBean.setDimensionId(Long.parseLong(dimensionId));
						dmValueBean.setDimValue(dimValue);
					}
				}
				

				String dimvaluelow = this.getDecodedRequestParam(request, "CHILD_DIM_VALUE_LOW");
				if (!StringUtils.isBlank(dimvaluelow)) {
					dmValueBean.setChildDimValueLow(dimvaluelow);
				} 
				String childDimValueHigh = this.getDecodedRequestParam(request, "CHILD_DIM_VALUE_HIGH");
				if (!StringUtils.isBlank(childDimValueHigh)) {
					dmValueBean.setChildDimValueHigh(childDimValueHigh);
				} 
				return dmValueBean;
				
			}
			
		}
		return auditBean;
	}

	private boolean isDimensionInputValid(AuditBean2 auditBean, boolean isCreate) {
		boolean isValid = true;
		if (auditBean == null) {
			return false;
		}
		if (auditBean instanceof DimensionBean ) {
			DimensionBean requestDimBean = (DimensionBean)auditBean;
			if (!isCreate) {
				if (requestDimBean.getDimensionId() == 0) {
					isValid = false;
				}
			}
			if (isValid) {
				if (requestDimBean.getDimSeq() == 0 || StringUtils.isBlank(requestDimBean.getDimensionCode()) || 
						StringUtils.isBlank(requestDimBean.getDimensionName()) || 
						StringUtils.isBlank(requestDimBean.getDimSegment())) {
					isValid = false;
				}
			}
		} else if (auditBean instanceof DimensionValueBean) {
			DimensionValueBean dimValueBean = (DimensionValueBean)auditBean;
			if (!isCreate) {
				if (dimValueBean.getDimensionValueId() == 0) {
					isValid = false;
				}
			}
			if (isValid) {
				if (StringUtils.isBlank(dimValueBean.getAccountType()) || StringUtils.isBlank(dimValueBean.getDescription()) ||
                    StringUtils.isBlank(dimValueBean.getDimensionValue()) ||
						StringUtils.isBlank(dimValueBean.getSummaryFlag())) {
					isValid = false;
				}
			}
		}
		else if (auditBean instanceof DimensionSonValueBean) {
			DimensionSonValueBean dimValueBean = (DimensionSonValueBean)auditBean;
			if (!isCreate) {
				if (dimValueBean.getHierarchyId() == 0) {
					isValid = false;
				}
			}
			if (isValid) {
				if (StringUtils.isBlank(dimValueBean.getDimValue()) || dimValueBean.getDimensionId()==0 ||
						StringUtils.isBlank(dimValueBean.getChildDimValueLow()) || 
						StringUtils.isBlank(dimValueBean.getChildDimValueHigh())) {
					isValid = false;
				}
			}
		}

		return isValid;
	}
	
	private void constructReturnMap(AuditBean2 auditBean) {
		dataMap = new HashMap();
		if (auditBean != null) {
			if (auditBean instanceof DimensionBean) {
				DimensionBean requestDimBean = (DimensionBean)auditBean;
				dataMap.put("DIMENSION_ID", requestDimBean.getDimensionId());
				dataMap.put("DIM_SEQ", requestDimBean.getDimSeq());
				dataMap.put("DIMENSION_CODE", requestDimBean.getDimensionCode());
				dataMap.put("DIMENSION_NAME", requestDimBean.getDimensionName());
				dataMap.put("DIM_SEGMENT", requestDimBean.getDimSegment());
				dataMap.put("FIN_ACCOUNT_FLAG", requestDimBean.getFinAccountFlag());
				
				dataMap.put("CREATION_DATE", requestDimBean.getCreationDate());
				dataMap.put("CREATED_BY", requestDimBean.getCreatedBy());
				dataMap.put("LAST_UPDATE_DATE", requestDimBean.getLastUpdateDate());
				dataMap.put("LAST_UPDATED_BY", requestDimBean.getLastUpdatedBy());
			} else if (auditBean instanceof DimensionValueBean) {
				DimensionValueBean dmValueBean = (DimensionValueBean)auditBean;
				dataMap.put("DIM_VALUE_ID", dmValueBean.getDimensionValueId());
				dataMap.put("DIMENSION_ID", dmValueBean.getDimensionId());
				dataMap.put("DIM_VALUE", dmValueBean.getDimensionValue());
				dataMap.put("DESCRIPTION", dmValueBean.getDescription());
				dataMap.put("ACCOUNT_TYPE", dmValueBean.getAccountType());
				dataMap.put("SUMMARY_FLAG", dmValueBean.getSummaryFlag());
				dataMap.put("DIM_LEVEL", dmValueBean.getDimensionLevel());
				dataMap.put("CREATION_DATE", dmValueBean.getCreationDate());
				dataMap.put("CREATED_BY", dmValueBean.getCreatedBy());
				dataMap.put("LAST_UPDATE_DATE", dmValueBean.getLastUpdateDate());
				dataMap.put("LAST_UPDATED_BY", dmValueBean.getLastUpdatedBy());
			}	
			else if (auditBean instanceof DimensionSonValueBean) {
				DimensionSonValueBean dmValueBean = (DimensionSonValueBean)auditBean;
				dataMap.put("HIERARCHY_ID", dmValueBean.getHierarchyId());
				dataMap.put("DIM_VALUE", dmValueBean.getDimValue());
				dataMap.put("DIMENSION_ID", dmValueBean.getDimensionId());
				dataMap.put("CHILD_DIM_VALUE_LOW", dmValueBean.getChildDimValueLow());
				dataMap.put("CHILD_DIM_VALUE_HIGH", dmValueBean.getChildDimValueHigh());
				
				
				
				
				dataMap.put("CREATION_DATE", dmValueBean.getCreationDate());
				dataMap.put("CREATED_BY", dmValueBean.getCreatedBy());
				dataMap.put("LAST_UPDATE_DATE", dmValueBean.getLastUpdateDate());
				dataMap.put("LAST_UPDATED_BY", dmValueBean.getLastUpdatedBy());
			}	
			}
	}
	
	
	
	
	/* DimensionValue Line related actions -----------Start */	
	
	@RequestMapping(value = "/showDimensionSonValueSubList")

	public String showDimensionSonValueSubList(HttpServletRequest request,HttpServletResponse response, Model model) {
	
		String headDim= null;
		String superHeadDim= null;

		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		
		try {
			headDim = this.getDecodedRequestParam(request, "headDim");
			superHeadDim = this.getDecodedRequestParam(request, "superHeadDim");			
			
		} catch (UnsupportedEncodingException ue) {
			logger.error("showDimensionSonValueSubList():" + ue);
		}
		if (!StringUtils.isBlank(headDim)&&!StringUtils.isBlank(superHeadDim)) {
			DimensionValueBean parentBean = null;
			try {
				parentBean = rptGenConfigService.getDimensionValueByPrimaryKey(Long.parseLong(headDim),Long.parseLong(superHeadDim));
			} catch (NumberFormatException ne) {
				logger.error("showDimensionSonValueSubList():" + ne);
			} catch(Exception e) {
				logger.error("showDimensionSonValueSubList():" + e);
			}

			if (parentBean != null) {
				model.addAttribute("dimensionValueBean", parentBean);
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
		return "configuration/dimensionSonValueList";
	}
	
	@RequestMapping(value = "/findDimensionSonValueList")
	@ResponseBody
	public Object findDimensionSonValueList(HttpServletRequest request,HttpServletResponse response, HttpSession httpSession) {
		// 参数
		DimensionSonValueBean dimValueSonBean = new DimensionSonValueBean();

		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
			

		try {
			 //handle the pagination case, initialize page related information
			this.initilizePagingInfo(request);
			//set query start index
			dimValueSonBean.setRowStartIndex(this.queryRowStartIndex);
			//set row count
			dimValueSonBean.setRowCount(this.rows);
		} catch (UnsupportedEncodingException e1) {
			logger.error("findDimensionSonValueList():" + e1);
		}
		
		String dimensionId = null;
		String dimValue=null;
		try {
			dimensionId = this.getDecodedRequestParam(request, "dimensionId");
			dimValue=this.getDecodedRequestParam(request, "dimensionValue");
		} catch (UnsupportedEncodingException e1) {
			logger.error("findDimensionSonValueList():" + e1);
		}
		if (!StringUtils.isBlank(dimensionId)&&!StringUtils.isBlank(dimValue)) {
			dimValueSonBean.setDimensionId(Long.valueOf(dimensionId));
			dimValueSonBean.setDimValue(dimValue);
			httpSession.removeAttribute(SESSION_KEY_DIMENSIONID);
			httpSession.setAttribute(SESSION_KEY_DIMENSIONID, dimensionId);
			httpSession.removeAttribute(SESSION_KEY_DIMVALUE);
			httpSession.setAttribute(SESSION_KEY_DIMVALUE, dimValue);
		}		
		List<Map<String, Object>> list = null;
		try {
			list = rptGenConfigService.getDimensionSonValueListByCondition(dimValueSonBean);
		} catch (Exception e) {
			logger.error("findDimensionSonValueList():" + e);
		}
		int count = 0;
		try {
			count = rptGenConfigService.getCountByDimensionSonValueBean(dimValueSonBean);			
		} catch (Exception e) {
			logger.error("findDimensionSonValueList():" + e);
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
	
	@RequestMapping(value = "/saveDimensionSonValue")
	@ResponseBody
	public Object saveDimensionSonValue(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		//get dimension bean from request
		DimensionSonValueBean reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
	
		try {
			reqBean = (DimensionSonValueBean)this.constructDimensionBean(request, new DimensionSonValueBean(), true);
			//try to get header it from session
			String dimensionId = null;
			String dimValue=null;
			
			if (session.getAttribute(SESSION_KEY_DIMENSIONID) != null&&session.getAttribute(SESSION_KEY_DIMVALUE) != null) {
				dimensionId = (String)session.getAttribute(SESSION_KEY_DIMENSIONID);
				dimValue = (String)session.getAttribute(SESSION_KEY_DIMVALUE);
			}
			if (!StringUtils.isBlank(dimensionId)&&!StringUtils.isBlank(dimValue)) {
			
				reqBean.setDimensionId(Long.valueOf(dimensionId));
				reqBean.setDimValue(dimValue);	
			}
			if (!this.isDimensionInputValid(reqBean, true)) {
				isError = true;
				errorMsg = "输入信息有误，请检查数据格式，重新保存！";
			} else {
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
                reqBean.setSource("100");
				//save dimension into database
				int newKey = 0;
				try {
					newKey = rptGenConfigService.createNewDimensionSonValueRecord(reqBean);
				} catch (Exception e) {
					//System.out.println(e);
					logger.error("saveDimensionSonValue():" + e);
				}
				if (newKey == 0) {
					isError = true;
				}
				reqBean.setHierarchyId(newKey);
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
	
	@RequestMapping(value = "/updateDimensionSonValue")
	@ResponseBody
	public Object updateDimensionSonValue(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		//get dimension bean from request
		DimensionSonValueBean reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
			
		try {
			reqBean = (DimensionSonValueBean)this.constructDimensionBean(request, new DimensionSonValueBean(), false);
			if (!this.isDimensionInputValid(reqBean, false)) {
				isError = true;
				errorMsg = "输入信息有误，请检查数据格式，重新保存！";
			} else {
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
				
				//save dimension into database
				int updateCount = 0;
				try {
					updateCount = rptGenConfigService.updateDimensionSonValueRecord(reqBean);
				} catch (Exception e) {
					//System.out.println(e);
					logger.error("updateDimensionSonValue():" + e);
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
	
	
	
	
	
	
}
