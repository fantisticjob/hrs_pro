
package com.hausontech.hrs.web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hausontech.hrs.api.allocationManager.service.IAllocRuleConfigService;
import com.hausontech.hrs.api.constants.Constants;
import com.hausontech.hrs.bean.Json;
import com.hausontech.hrs.bean.allocationManager.model.AllocDriverRecord;
import com.hausontech.hrs.bean.allocationManager.model.AllocRuleRecord;
import com.hausontech.hrs.bean.allocationManager.model.AllocSourceRecord;
import com.hausontech.hrs.bean.allocationManager.model.AllocTargetRecord;
import com.hausontech.hrs.bean.userManager.pojo.EasyuiDataGrid;
import com.hausontech.hrs.bean.userManager.pojo.EasyuiDataGridJson;
import com.hausontech.hrs.bean.userManager.pojo.User;
import com.hausontech.hrs.utils.Comp;
import com.hausontech.hrs.utils.JsonUtil;
import com.hausontech.hrs.utils.Loap;
import com.hausontech.hrs.utils.StringUtil;
import com.hausontech.hrs.utils.Tan;
import com.hausontech.hrs.utils.ThirdLevelType;

import net.sf.json.JSONArray;
import oracle.net.aso.a;

@Controller
@RequestMapping("/allocRuleConfig")
public class AllocRuleConfigurationController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(AllocRuleConfigurationController.class);

	@Autowired
	private IAllocRuleConfigService allocRuleService;

	public IAllocRuleConfigService getAllocRuleService() {
		return allocRuleService;
	}

	public void setAllocRuleService(IAllocRuleConfigService allocRuleService) {
		this.allocRuleService = allocRuleService;
	}

	/**
	 * 获得分摊规则列表
	 * 
	 * @return
	 */
	@RequestMapping(params = "allocRuleDatagrid")
	@ResponseBody
	public Object getAllocRuleList(EasyuiDataGrid dg, AllocRuleRecord allocRuleRecord,
			HttpServletRequest request, HttpServletResponse response) {
		Loap lop = new Loap();
		String ruleName = null;
		
		if (!(Tan.lp().equals(Comp.dp()))) {
			if (lop.la(request).equals(ThirdLevelType.HQ)) {

				try {
					this.initilizePagingInfo(request);
					//set query start index
					allocRuleRecord.setRowStartIndex(this.queryRowStartIndex);
					//set row count
					allocRuleRecord.setRowCount(this.rows);
					ruleName = this.getDecodedRequestParam(request, "ruleName");
				} catch (UnsupportedEncodingException e1) {
					logger.error("Errors while sallocRuleDatagrid:" + e1);
				}
				if (allocRuleRecord == null) {
					allocRuleRecord = new AllocRuleRecord();
				}
				if (!StringUtils.isBlank(ruleName)) {
					allocRuleRecord.setRuleName(ruleName);
				}
			} else {
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
		JSONArray jsonlist = JsonUtil.fromObject(allocRuleService.getAllocRuleDataGrid(dg, allocRuleRecord));
		dataMap.put("rows",jsonlist );
		int count=allocRuleService.getAllocRuleDataGridCount(allocRuleRecord);
		dataMap.put("total", count);
		return JsonUtil.fromObject(dataMap);
	}

	@RequestMapping(params = "saveAllocRule")
	@ResponseBody
	public AllocRuleRecord saveAllocRule(AllocRuleRecord allocRuleRecord, HttpServletRequest request,
			HttpServletResponse response) {
		// User user = this.getLoginUser(request);
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (!(Tan.lp().equals(Comp.dp()))) {
			if (Loap.la(request).equals(ThirdLevelType.HQ)) {

				if (userDetails != null) {
					allocRuleRecord.setCreatedBy(userDetails.getUsername());
					allocRuleRecord.setLastUpdatedBy(userDetails.getUsername());
				}
				allocRuleRecord.setCreationDate(new Date());
				allocRuleRecord.setLastUpdateDate(new Date());
			} else {
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
		return allocRuleService.addAllocRule(allocRuleRecord);
	}

	@RequestMapping(params = "updateAllocRule")
	@ResponseBody
	public AllocRuleRecord updateAllocRule(AllocRuleRecord allocRuleRecord, HttpServletRequest request,
			HttpServletResponse response) {
		// User user = this.getLoginUser(request);
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if (!(Tan.lp().equals(Comp.dp()))) {
			if (Loap.la(request).equals(ThirdLevelType.HQ)) {
				if (userDetails != null) {
					allocRuleRecord.setLastUpdatedBy(userDetails.getUsername());
				}
				/*
				 * if (user != null) {
				 * allocRuleRecord.setLastUpdatedBy(user.getName()); }
				 */
				allocRuleRecord.setLastUpdateDate(new Date());
			} else {
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
		return allocRuleService.updateAllocRule(allocRuleRecord);
	}

	@RequestMapping(params = "delAllocRule")
	@ResponseBody
	public Json delAllocRule(HttpServletRequest request, HttpServletResponse response) {
		Json j = new Json();
		boolean isError = false;
		String errorMsg = "";
		AllocRuleRecord allocRuleRecord = new AllocRuleRecord();
		if (!(Tan.lp().equals(Comp.dp()))) {
			if (Loap.la(request).equals(ThirdLevelType.HQ)) {

				try {
					String ruleId = this.getDecodedRequestParam(request, "id");
					if (StringUtils.isBlank(ruleId)) {
						isError = true;
						errorMsg = "没有选中需要删除的记录，请选择后再删除！";
					} else {
						allocRuleRecord.setAllocRuleId(Long.parseLong(ruleId));
					}
					if (!isError) {
						allocRuleService.deleteAllocRule(allocRuleRecord);
					}
				} catch (UnsupportedEncodingException ue) {
					isError = true;
					errorMsg = ue.toString();
				} catch (Exception e) {
					isError = true;
					errorMsg = e.toString();
				}
				j.setSuccess(!isError);
				j.setMsg(errorMsg);
			} else {
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
		return j;
	}

	@RequestMapping(params = "showAllocSourceView")
	public String showAllocSourceView(HttpServletRequest request, Model model, HttpServletResponse response) {
		// get dimensionId
		String allocRuleId = null;
		if (!(Tan.lp().equals(Comp.dp()))) {
			if (Loap.la(request).equals(ThirdLevelType.HQ)) {

				try {
					allocRuleId = this.getDecodedRequestParam(request, "allocRuleId");
				} catch (UnsupportedEncodingException e) {

				}
				if (!StringUtils.isBlank(allocRuleId)) {
					AllocRuleRecord allocRuleRecord = null;
					try {
						allocRuleRecord = allocRuleService.getAllocRuleByKey(Long.parseLong(allocRuleId));
					} catch (NumberFormatException ne) {
					} catch (Exception e) {
					}
					if (allocRuleRecord != null) {
						model.addAttribute("allocRuleRecord", allocRuleRecord);
						// try to get alloc source for this rule
						AllocSourceRecord allocSource = allocRuleService
								.getAllocSourceByRuleId(allocRuleRecord.getAllocRuleId());
						if (allocSource == null || allocSource.getSourceId() == 0) {
							allocSource = new AllocSourceRecord();
							allocSource.setAllocRuleId(allocRuleRecord.getAllocRuleId());
							allocSource.setSourceType("ACCOUNT");
							allocSource.setActualFlag("A");
							allocSource.setCurrencyType("T");
							allocSource.setCurrencyCode("CNY");
							allocSource.setAmountType("PTD");
							allocSource.setDirectionCode("DR");
							allocSource.setOperator("+");
							String loginUser = this.getLoginUserName(request);
							if (StringUtil.isEmptyTrim(loginUser)) {
								loginUser = "WEB";
							}
							allocSource.setCreatedBy(loginUser);
							allocSource.setLastUpdatedBy(loginUser);
							allocSource.setCreationDate(new Date());
							allocSource.setLastUpdateDate(new Date());
							allocRuleService.addAllocSource(allocSource);
						}
					}
				}
			} else {
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

		return "configuration/AllocationRulesSetting/allocSource";
	}

	/**
	 * 获得分摊源列表
	 * 
	 * @return
	 */
	@RequestMapping(params = "allocSourceDatagrid")
	@ResponseBody
	public Object getAllocSourceList(EasyuiDataGrid dg, AllocSourceRecord allocSourceRecord,
			HttpServletRequest request, HttpServletResponse response) {
		if (!(Tan.lp().equals(Comp.dp()))) {
			if (Loap.la(request).equals(ThirdLevelType.HQ)) {

				if (allocSourceRecord == null) {
					allocSourceRecord = new AllocSourceRecord();
				}
				String ruleId = null;
				try {
					ruleId = this.getDecodedRequestParam(request, "allocRuleId");
				} catch (UnsupportedEncodingException e1) {
					logger.error("Errors while save dimension value list:" + e1);
				}
				if (!StringUtils.isBlank(ruleId)) {
					allocSourceRecord.setAllocRuleId(Long.valueOf(ruleId));
				}
			} else {
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
		return JsonUtil.fromObject(allocRuleService.getAllocSourceDataGrid(dg, allocSourceRecord));
	}

	@RequestMapping(params = "updateAllocSource")
	@ResponseBody
	public Object updateAllocSource(AllocSourceRecord allocSourceRecord, HttpServletRequest request,
			HttpServletResponse response) {
		if (!(Tan.lp().equals(Comp.dp()))) {
			if (Loap.la(request).equals(ThirdLevelType.HQ)) {

				if (allocSourceRecord != null) {
					if (!StringUtil.isEmptyTrim(allocSourceRecord.getSourceType())
							&& "CONSTANT".equals(allocSourceRecord.getSourceType())
							&& allocSourceRecord.getConstantValue() == 0) {
						dataMap.put("isError", true);
						dataMap.put("msg", "常数类型必须输入常数值！");
						return dataMap;
					}
				}
//				User user = this.getLoginUser(request);
				UserDetails user = this.getLoginUser(request);
				if (user != null) {
					allocSourceRecord.setLastUpdatedBy(user.getUsername());
					//allocSourceRecord.setLastUpdatedBy(user.getName());
				}
				allocSourceRecord.setLastUpdateDate(new Date());
			} else {
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
		return allocRuleService.updateAllocSource(allocSourceRecord);
	}

	@RequestMapping(params = "showAllocTargetView")
	public String showAllocTargetView(HttpServletRequest request, Model model, HttpServletResponse response) {
		// get dimensionId

		String allocRuleId = null;
		if (!(Tan.lp().equals(Comp.dp()))) {
			if (Loap.la(request).equals(ThirdLevelType.HQ)) {

				try {
					allocRuleId = this.getDecodedRequestParam(request, "allocRuleId");
				} catch (UnsupportedEncodingException e) {

				}
				if (!StringUtils.isBlank(allocRuleId)) {
					AllocRuleRecord allocRuleRecord = null;
					try {
						allocRuleRecord = allocRuleService.getAllocRuleByKey(Long.parseLong(allocRuleId));
					} catch (NumberFormatException ne) {
					} catch (Exception e) {
					}
					if (allocRuleRecord != null) {
						model.addAttribute("allocRuleRecord", allocRuleRecord);
						// try to get alloc Target for this rule
						AllocTargetRecord allocTarget = allocRuleService
								.getAllocTargetByRuleId(allocRuleRecord.getAllocRuleId());
						if (allocTarget == null || allocTarget.getTargetId() == 0) {
							allocTarget = new AllocTargetRecord();
							allocTarget.setAllocRuleId(allocRuleRecord.getAllocRuleId());
							allocTarget.setTargetType("TARGET");
							allocTarget.setActualFlag("A");
							allocTarget.setCurrencyType("T");
							allocTarget.setCurrencyCode("CNY");
							allocTarget.setAmountType("PTD");
							allocTarget.setDirectionCode("DR");
							String loginUser = this.getLoginUserName(request);
							if (StringUtil.isEmptyTrim(loginUser)) {
								loginUser = "WEB";
							}
							allocTarget.setCreatedBy(loginUser);
							allocTarget.setLastUpdatedBy(loginUser);
							allocTarget.setCreationDate(new Date());
							allocTarget.setLastUpdateDate(new Date());
							allocRuleService.addAllocTarget(allocTarget);
							// add offset
							AllocTargetRecord allocOffset = new AllocTargetRecord();
							allocOffset.setAllocRuleId(allocRuleRecord.getAllocRuleId());
							allocOffset.setTargetType("OFFSET");
							allocOffset.setActualFlag("A");
							allocOffset.setCurrencyType("T");
							allocOffset.setCurrencyCode("CNY");
							allocOffset.setAmountType("PTD");
							allocOffset.setDirectionCode("DR");
							String loginUsero = this.getLoginUserName(request);
							if (StringUtil.isEmptyTrim(loginUsero)) {
								loginUsero = "WEB";
							}
							allocOffset.setCreatedBy(loginUsero);
							allocOffset.setLastUpdatedBy(loginUsero);
							allocOffset.setCreationDate(new Date());
							allocOffset.setLastUpdateDate(new Date());
							allocRuleService.addAllocTarget(allocOffset);
						}
					}
				}
			} else {
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
		return "configuration/AllocationRulesSetting/allocTarget";
	}

	/**
	 * 获得分摊目标列表
	 * 
	 * @return
	 */
	@RequestMapping(params = "allocTargetDatagrid")
	@ResponseBody
	public Object getAllocTargetList(EasyuiDataGrid dg, AllocTargetRecord allocTargetRecord,
			HttpServletRequest request, HttpServletResponse response) {
		if (!(Tan.lp().equals(Comp.dp()))) {
			if (Loap.la(request).equals(ThirdLevelType.HQ)) {

				if (allocTargetRecord == null) {
					allocTargetRecord = new AllocTargetRecord();
				}
				String ruleId = null;
				try {
					ruleId = this.getDecodedRequestParam(request, "allocRuleId");
				} catch (UnsupportedEncodingException e1) {
					logger.error("Errors while save dimension value list:" + e1);
				}
				if (!StringUtils.isBlank(ruleId)) {
					allocTargetRecord.setAllocRuleId(Long.valueOf(ruleId));
				}
			} else {
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
		return JsonUtil.fromObject(allocRuleService.getAllocTargetDataGrid(dg, allocTargetRecord));
	}

	@RequestMapping(params = "updateAllocTarget")
	@ResponseBody
	public Object updateAllocTarget(AllocTargetRecord allocTargetRecord, HttpServletRequest request,
			HttpServletResponse response) {

		UserDetails user = this.getLoginUser(request);
//		User user = this.getLoginUser(request);
		if (!(Tan.lp().equals(Comp.dp()))) {
			if (Loap.la(request).equals(ThirdLevelType.HQ)) {

				if (user != null) {
					allocTargetRecord.setLastUpdatedBy(user.getUsername());
//					allocTargetRecord.setLastUpdatedBy(user.getName());
				}
				allocTargetRecord.setLastUpdateDate(new Date());
			} else {
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
		return allocRuleService.updateAllocTarget(allocTargetRecord);
	}

	// XXX allocation driver
	/*
	 * 
	 * 
	 * */
	@RequestMapping(params = "showAllocDriverView")
	public String showAllocDriverView(HttpServletRequest request, HttpServletResponse response, Model model) {
		// get dimensionId
		String allocRuleId = null;
		if (!(Tan.lp().equals(Comp.dp()))) {
			if (Loap.la(request).equals(ThirdLevelType.HQ)) {

				try {
					allocRuleId = this.getDecodedRequestParam(request, "allocRuleId");
				} catch (UnsupportedEncodingException e) {

				}
				if (!StringUtils.isBlank(allocRuleId)) {
					AllocRuleRecord allocRuleRecord = null;
					try {
						allocRuleRecord = allocRuleService.getAllocRuleByKey(Long.parseLong(allocRuleId));
					} catch (NumberFormatException ne) {
					} catch (Exception e) {
					}
					if (allocRuleRecord != null) {
						model.addAttribute("allocRuleRecord", allocRuleRecord);
						// try to get alloc source for this rule
						AllocDriverRecord allocDriver = allocRuleService
								.getAllocDriverByRuleId(allocRuleRecord.getAllocRuleId());
						if (allocDriver == null || allocDriver.getDriverId() == 0) {
							allocDriver = new AllocDriverRecord();
							allocDriver.setRuleId(allocRuleRecord.getAllocRuleId());
							allocDriver.setDriverType("DYNAMIC");
							allocDriver.setActualFlag("A");
							allocDriver.setCurrencyType("T");
							allocDriver.setCurrencyCode("CNY");
							allocDriver.setAmountType("PTD");
							allocDriver.setDirectionCode("DR");
							String loginUser = this.getLoginUserName(request);
							if (StringUtil.isEmptyTrim(loginUser)) {
								loginUser = "WEB";
							}
							allocDriver.setCreatedBy(loginUser);
							allocDriver.setLastUpdatedBy(loginUser);
							allocDriver.setCreationDate(new Date());
							allocDriver.setLastUpdateDate(new Date());
							allocRuleService.addAllocDriver(allocDriver);
						}
					}
				}
			} else {
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

		return "configuration/AllocationRulesSetting/allocDriver";
	}

	/**
	 * 获得分摊driver列表
	 * 
	 * @return
	 */
	@RequestMapping(params = "allocDriverDatagrid")
	@ResponseBody
	public List<Map<String, Object>> allocDriverDatagrid(EasyuiDataGrid dg, AllocDriverRecord allocDriverRecord,
			HttpServletRequest request, HttpSession session, HttpServletResponse response) {
		Map<String, Object> map=null;
		List<Map<String, Object>> result = new ArrayList();
		if (!(Tan.lp().equals(Comp.dp()))) {
			if (Loap.la(request).equals(ThirdLevelType.HQ)) {

				if (allocDriverRecord == null) {
					allocDriverRecord = new AllocDriverRecord();
				}
				String ruleId = null;
				try {
					ruleId = this.getDecodedRequestParam(request, "allocRuleId");
				} catch (UnsupportedEncodingException e1) {
					logger.error("Errors while save dimension value list:" + e1);
				}
				if (!StringUtils.isBlank(ruleId)) {
					allocDriverRecord.setRuleId(Long.valueOf(ruleId));
				}
				List<AllocDriverRecord> list = allocRuleService.getAllocDriverDataGrid(dg, allocDriverRecord);
				AllocDriverRecord adr = (AllocDriverRecord) list.get(0);
				String type = adr.getDriverType();
				if (!StringUtils.isBlank((String) session.getAttribute("drivertype"))) {
					session.removeAttribute("drivertype");
				}
				session.setAttribute("drivertype", type);
				for (int i = 0; i < list.size(); i++) {
					map=new HashMap<String, Object>();
					map.put("driverType",list.get(i).getDriverType());
					map.put("actualFlag",list.get(i).getActualFlag());
					map.put("currencyType", list.get(i).getCurrencyType());
					map.put("currencyCode", list.get(i).getCurrencyCode());
					map.put("amountType", list.get(i).getAmountType());
					map.put("directionCode", list.get(i).getDirectionCode());
					map.put("description", list.get(i).getDescription());
					map.put("constant", list.get(i).getConstant());
					map.put("staticHeaderId", list.get(i).getStaticHeaderId());
					map.put("driverId", list.get(i).getDriverId());
					map.put("creationDate", list.get(i).getCreationDate());
					map.put("createdBy", list.get(i).getCreatedBy());
					map.put("lastUpdateDate", list.get(i).getLastUpdateDate());
					map.put("lastUpdatedBy", list.get(i).getLastUpdatedBy());
					result.add(map);
					}
				return JsonUtil.fromObject(result);
			} else {
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

	@RequestMapping(params = "updateAllocDriver")
	@ResponseBody
	public Object updateAllocDriver(AllocDriverRecord allocDriverRecord, HttpServletRequest request,
			HttpServletResponse response) {
		if (!(Tan.lp().equals(Comp.dp()))) {
			if (Loap.la(request).equals(ThirdLevelType.HQ)) {

				if (allocDriverRecord != null) {
					if (!StringUtil.isEmptyTrim(allocDriverRecord.getDriverType())) {
						if (("CONSTANT".equals(allocDriverRecord.getDriverType())
								&& allocDriverRecord.getConstant() == 0)
								|| ("STATIC".equals(allocDriverRecord.getDriverType())
										&& allocDriverRecord.getStaticHeaderId() == 0)
								|| ("CONSTANT".equals(allocDriverRecord.getDriverType())
										&& allocDriverRecord.getStaticHeaderId() != 0)
								|| ("STATIC".equals(allocDriverRecord.getDriverType())
										&& allocDriverRecord.getConstant() != 0)
								|| ("DYNAMIC".equals(allocDriverRecord.getDriverType())
										&& (allocDriverRecord.getConstant() != 0
												|| allocDriverRecord.getStaticHeaderId() != 0))) {
							dataMap.put("isError", true);
							dataMap.put("msg", "请输入与类型对应的值！");
							return dataMap;
						}
					}
				}
				UserDetails user = this.getLoginUser(request);
//				User user = this.getLoginUser(request);
				if (user != null) {
					allocDriverRecord.setLastUpdatedBy(user.getUsername());
//					allocDriverRecord.setLastUpdatedBy(user.getName());
				}
				allocDriverRecord.setLastUpdateDate(new Date());
			} else {
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
		return allocRuleService.updateAllocDriver(allocDriverRecord);
	}

}
