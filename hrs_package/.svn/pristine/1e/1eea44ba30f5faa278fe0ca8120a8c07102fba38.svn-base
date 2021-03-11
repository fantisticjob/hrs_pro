package com.hausontech.hrs.web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hausontech.hrs.api.constants.Constants;
import com.hausontech.hrs.api.constants.JobStatus;
import com.hausontech.hrs.api.dataProcessing.service.IDataProcessService;
import com.hausontech.hrs.bean.allocationManager.AllocRuleBean;
import com.hausontech.hrs.bean.dataProcess.AllocRequestInstanceBean;
import com.hausontech.hrs.bean.dataProcess.AllocRequestInstanceHistoryRecord;
import com.hausontech.hrs.bean.dataProcess.AllocRequestInstanceRecord;
import com.hausontech.hrs.bean.dataProcess.LedgerBean;
import com.hausontech.hrs.bean.dataProcess.PeriodBean;
import com.hausontech.hrs.bean.dataProcess.RequestInstanceBean;
import com.hausontech.hrs.exceptions.DuplicateJobRunningException;
import com.hausontech.hrs.utils.Comp;
import com.hausontech.hrs.utils.JsonUtil;
import com.hausontech.hrs.utils.Loap;
import com.hausontech.hrs.utils.Tan;
import com.hausontech.hrs.utils.ThirdLevelType;

import net.sf.json.JSONArray;

@Controller
@RequestMapping(value = "/dataProcessing")
public class DataProcessController extends BaseController {
	private final static String SESSION_KEY_REQUESTINSTANCEID = "requestInstanceId";
	private final static String SESSION_KEY_REQUESTINSTANCENAME = "requestInstanceName";
	/** 组件日志 */
	private static Logger logger = LoggerFactory.getLogger(DataProcessController.class);

	@Resource
	private IDataProcessService dataProcessService;

	public IDataProcessService getDataProcessService() {
		return dataProcessService;
	}

	public void setDataProcessService(IDataProcessService dataProcessService) {
		this.dataProcessService = dataProcessService;
	}

	/* Loop group Header definition related methods ---------Start */
	@RequestMapping(value = "/showDataProcessView")
	public String showDataProcessView(HttpServletRequest request,HttpServletResponse response, Model model) {
		// 初始化执行参数
		RequestInstanceBean requestInsBean = new RequestInstanceBean();
		// get ledgerList
		List<LedgerBean> ledgerList = null;
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			ledgerList = dataProcessService.getLedgerList();
		} catch (Exception e) {
			logger.error("Error happen when get ledger list," + e);
		}

		if (ledgerList != null && 0 < ledgerList.size()) {
			Iterator<LedgerBean> iter = ledgerList.iterator();
			while (iter.hasNext()) {
				LedgerBean tmp = iter.next();
				try {
					Integer.parseInt(tmp.getLederCode());
				} catch (Exception e) {
					iter.remove();
					logger.warn("账套Id为非数字类型，不能处理：" + "LedgerId=" + tmp.getLederCode() + ", ledgerName="
							+ tmp.getLederName());
				}
			}
		}

		if (ledgerList != null && 0 < ledgerList.size()) {
			requestInsBean.setLedgerSelectionList(ledgerList);
		}
		// get period List
		List<PeriodBean> periodList = null;
		try {
			periodList = dataProcessService.getPeriodList();
		} catch (Exception e) {
			logger.error("Error happen when get period list," + e);
		}
		if (periodList != null && 0 < periodList.size()) {
			requestInsBean.setPeriodList(periodList);
		}
		model.addAttribute("executCondition", requestInsBean);
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
		return "configuration/dataCalculation";
	}

	@RequestMapping(value = "/getLedgerList")
	@ResponseBody
	public Object getLedgerList(HttpServletRequest request,HttpServletResponse response) {
		List<Map<String, Object>> ledgerList = null;
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		
		try {
			ledgerList = dataProcessService.getLedgerListMapForDisplay();
		} catch (Exception e) {
			logger.error("Error occurs when get ledger List," + e);
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
		return JsonUtil.fromObject(ledgerList);
	}

	@RequestMapping(value = "/getPeriodList")
	@ResponseBody
	public Object getPeriodList(HttpServletRequest request,HttpServletResponse response) {
		List<Map<String, Object>> periodList = null;
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		
		try {
			periodList = dataProcessService.getPeriodListMapForDisplay();
		} catch (Exception e) {
			logger.error("Error occurs when get period List," + e);
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
		return JsonUtil.fromObject(periodList);
	}

	@RequestMapping(value = "/getAvailablePeriodList")
	@ResponseBody
	public Object getAvailablePeriodList(HttpServletRequest request,HttpServletResponse response) {
		List<Map<String, Object>> periodList = null;
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
			
		try {
			periodList = dataProcessService.getAvailablePeriodListMapForDisplay();
		} catch (Exception e) {
			logger.error("Error occurs when get period List," + e);
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
		return JsonUtil.fromObject(periodList);
	}

	@RequestMapping(value = "/getFinElementList")
	@ResponseBody
	public Object getFinElementList(HttpServletRequest request,HttpServletResponse response) {
		
		List<Map<String, Object>> finEleList = null;
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
	
		try {
			finEleList = dataProcessService.getFinElementMapForDisplay();
		} catch (Exception e) {
			logger.error("Error occurs when get ledger List," + e);
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
		return JsonUtil.fromObject(finEleList);
	}

	@RequestMapping(value = "/getFinElementListFilter")
	@ResponseBody
	public Object getFinElementListFilter(HttpServletRequest request,HttpServletResponse response) {
		List<Map<String, Object>> finEleList = null;
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
	
		try {
			finEleList = dataProcessService.getFinElementMapForDisplay();
		} catch (Exception e) {
			logger.error("Error occurs when get ledger List," + e);
		}
		Iterator<Map<String, Object>> iterator = finEleList.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> tmp = iterator.next();
			String id = (String) tmp.get("FINELECODE");
			if (id.startsWith("1")) {
				iterator.remove();
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
		return JsonUtil.fromObject(finEleList);
	}

	/* Loop group Header definition related methods ---------Start */
	@RequestMapping(value = "/getRequestInstanceList")
	@ResponseBody
	public Object getRequestInstanceList(HttpServletRequest request,HttpServletResponse response) {
		// 参数
		RequestInstanceBean requestQueryBean = new RequestInstanceBean();

if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			// handle the pagination case, initialize page related information
			this.initilizePagingInfo(request);
			// set query start index
			requestQueryBean.setRowStartIndex(this.queryRowStartIndex);
			// set row count
			requestQueryBean.setRowCount(this.rows);

			String legerId = this.getDecodedRequestParam(request, "ledgerId");
			if (!StringUtils.isBlank(legerId)) {
				requestQueryBean.setLedgerId(Integer.parseInt(legerId));
			}
			requestQueryBean.setFinElementType(this.getDecodedRequestParam(request, "finElementType"));
			requestQueryBean.setPeriodFrom(this.getDecodedRequestParam(request, "periodFrom"));
			requestQueryBean.setPeriodTo(this.getDecodedRequestParam(request, "periodTo"));
			requestQueryBean.setStatus(this.getDecodedRequestParam(request, "status"));
		} catch (UnsupportedEncodingException e1) {
			logger.error("getRequestInstanceList():" + e1.toString());
		}

		List<Map<String, Object>> list = null;
		try {
			list = dataProcessService.getRequestInstanceList(requestQueryBean);
		} catch (Exception e) {
			logger.error("getRequestInstanceList():" + e.toString());
		}
		if (list != null && 0 < list.size()) {
			for (Map<String, Object> jobIns : list) {
				if (jobIns != null) {
					if (jobIns.containsKey("ELAPSED_TIME") && jobIns.get("ELAPSED_TIME") != null) {
						java.math.BigDecimal eclapseSec = (java.math.BigDecimal) jobIns.get("ELAPSED_TIME");
						if (eclapseSec != null && 0 < eclapseSec.longValue()) {
							jobIns.put("ELAPSED_TIME", this.formatEclapseTime(eclapseSec.longValue()));
						}
					}
					if (jobIns.containsKey("STATUS") && jobIns.get("STATUS") != null) {
						String status = (String) jobIns.get("STATUS");
						if (!StringUtils.isBlank(status)) {
							jobIns.put("STATUS", JobStatus.getDisplayStatus(status));
						}
					}
				}
			}
		}

		JSONArray jsonlist = JsonUtil.fromObject(list);
		// put rows into map
		dataMap.put("rows", jsonlist);
		int count = 0;
		try {
			count = dataProcessService.getBatchJobCount(requestQueryBean);
		} catch (Exception e) {
			logger.error("Error happens when get job count," + e);
		}
		// put total row number in the map
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

	private String formatEclapseTime(long miSecs) {
		String result = "";
		if (0 < miSecs) {
			int ss = 1000;
			int mi = ss * 60;
			int hh = mi * 60;

			long hour = miSecs / hh;
			long minute = (miSecs - hour * hh) / mi;
			long second = (miSecs - hour * hh - minute * mi) / ss;

			String strHour = hour < 10 ? "0" + hour : "" + hour;
			String strMinute = minute < 10 ? "0" + minute : "" + minute;
			String strSecond = second < 10 ? "0" + second : "" + second;

			result = strHour + ":" + strMinute + ":" + strSecond;
		}
		return result;
	}

	@RequestMapping(value = "/saveBatchJob")
	@ResponseBody
	public Object saveBatchJob(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		// get dimension bean from request
		RequestInstanceBean reqBean = new RequestInstanceBean();
		boolean isError = false;
		String errorMsg = "保存信息失败：请检查输入数据，重新提交！";
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			// initialize bean with request
			reqBean.constructBeanWithRequest(request, true);
			if (reqBean.inputValidate(true) != 0) {
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
				// set start time
				reqBean.setStartDate(new Date());
				// set status
				reqBean.setStatus(JobStatus.SCHEDULED);
				// save job into database
				long newKey = 0;
				try {
					newKey = dataProcessService.createBatchJobRecord(reqBean);
				} catch (Exception e) {
					logger.error("Error happens when create batch job instance," + e);
				}
				if (newKey == 0) {
					isError = true;
				}
				reqBean.setInstanceId(newKey);
			}
		} catch (Exception e) {
			isError = true;
			logger.error("Error happens when create batch job instance," + e);
		}

		if (isError) {
			dataMap.put("isError", true);
			dataMap.put("msg", errorMsg);
		} else {
			dataMap = reqBean.constructReturnMap(true);
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

	@RequestMapping(value = "/updateBatchJob")
	@ResponseBody
	public Object updateBatchJob(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		// get dimension bean from request
		RequestInstanceBean reqBean = new RequestInstanceBean();
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
			
		try {
			reqBean.constructBeanWithRequest(request, false);
			if (reqBean.inputValidate(false) != 0) {
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
				reqBean.setStartDate(new Date());
				reqBean.setStatus(JobStatus.SCHEDULED);
				// save dimension into database
				int updateCount = 0;
				try {
					updateCount = dataProcessService.updateBatchJobRecord(reqBean);
				} catch (Exception e) {
					logger.error("Error happens when update batch job instance," + e);
				}
				if (updateCount == 0) {
					isError = true;
				}
			}
		} catch (Exception e) {
			isError = true;
			logger.error("Error happens when update batch job instance," + e);
		}

		if (isError) {
			dataMap.put("isError", true);
			dataMap.put("msg", errorMsg);
		} else {
			dataMap = reqBean.constructReturnMap(false);
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

	@RequestMapping(value = "/deleteBatchJob")
	@ResponseBody
	public Object deleteBatchJob(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		// get job bean from request
		RequestInstanceBean reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
			
		try {
			String jobInsId = this.getDecodedRequestParam(request, "id");
			if (StringUtils.isBlank(jobInsId)) {
				isError = true;
				errorMsg = "没有选中需要删除的记录，请选择后再删除！";
			} else {
				reqBean = new RequestInstanceBean();
				reqBean.setInstanceId(Long.parseLong(jobInsId));
				String userId = (String) session.getAttribute(Constants.USER_KEY);
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
					delCount = dataProcessService.deleteBatchJobRecord(reqBean);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("Errors while delete rule header: " + e);
				}
				// if there's no record delete
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
			dataMap = reqBean.constructReturnMap(false);
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

	@RequestMapping(value = "/performCalculation")
	@ResponseBody
	public Object performCalculation(HttpServletRequest request,HttpServletResponse response, HttpSession httpSession,
			@ModelAttribute RequestInstanceBean requestBean) {
		// get session user
		String userId = (String) httpSession.getAttribute(Constants.USER_KEY);

if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
			

		if (StringUtils.isBlank(userId)) {
			userId = "Web";
		}
		requestBean.setRequestUser(userId);
		boolean isError = false;
		String message = "任务处理完成";
		// perform calculation
		try {
			// GET INSTNCE ID
			String jobInstanceId = this.getDecodedRequestParam(request, "jobId");
			if (!StringUtils.isBlank(jobInstanceId)) {
				requestBean.setInstanceId(Long.parseLong(jobInstanceId));
			}
			// try to get instacne
			requestBean = dataProcessService.getRequestInstanceByPrimaryKey(requestBean);
			if (requestBean == null || requestBean.getInstanceId() == 0) {
				throw new Exception("No instance found in the db");
			}
			requestBean.setRequestUser("Web");
			dataProcessService.calculate(requestBean);
		} catch (DuplicateJobRunningException de) {
			isError = true;
			message = "多个任务不能同时运行";
		} catch (Exception e) {
			message = "任务处理失败";
			logger.error("performCalculation():" + e.toString());

		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", !isError);
		result.put("message", message);

		return JsonUtil.fromObject(result);
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

	@RequestMapping(value = "/getAllocRequestInstanceList")
	@ResponseBody
	public Object getAllocRequestInstanceList(HttpServletRequest request,HttpServletResponse response) {
		// 参数
		AllocRequestInstanceBean requestQueryBean = new AllocRequestInstanceBean();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
try {
			// handle the pagination case, initialize page related information
			this.initilizePagingInfo(request);
			// set query start index
			requestQueryBean.setRowStartIndex(this.queryRowStartIndex);
			// set row count
			requestQueryBean.setRowCount(this.rows);

			String ruleId = this.getDecodedRequestParam(request, "ruleId");
			if (!StringUtils.isBlank(ruleId)) {
				requestQueryBean.setRuleId(Integer.parseInt(ruleId));
			}
			requestQueryBean.setRuleType(this.getDecodedRequestParam(request, "RuleType"));
			requestQueryBean.setPeriod(this.getDecodedRequestParam(request, "period"));
			requestQueryBean.setStatus(this.getDecodedRequestParam(request, "status"));
		} catch (UnsupportedEncodingException e1) {
			logger.error("getRequestInstanceList():" + e1.toString());
		}

		List<Map<String, Object>> list = null;
		try {
			list = dataProcessService.getAllocRequestInstanceList(requestQueryBean);
		} catch (Exception e) {
			logger.error("getRequestInstanceList():" + e.toString());
		}
		if (list != null && 0 < list.size()) {
			for (Map<String, Object> jobIns : list) {
				if (jobIns != null) {
					if (jobIns.containsKey("ELAPSED_TIME") && jobIns.get("ELAPSED_TIME") != null) {
						java.math.BigDecimal eclapseSec = (java.math.BigDecimal) jobIns.get("ELAPSED_TIME");
						if (eclapseSec != null && 0 < eclapseSec.longValue()) {
							jobIns.put("ELAPSED_TIME", this.formatEclapseTime(eclapseSec.longValue()));
						}
					}
					if (jobIns.containsKey("STATUS") && jobIns.get("STATUS") != null) {
						String status = (String) jobIns.get("STATUS");
						if (!StringUtils.isBlank(status)) {
							jobIns.put("STATUS", JobStatus.getDisplayStatus(status));
						}
					}
				}
			}
		}

		JSONArray jsonlist = JsonUtil.fromObject(list);
		// put rows into map
		dataMap.put("rows", jsonlist);
		int count = 0;
		try {
			count = dataProcessService.getAllocBatchJobCount(requestQueryBean);
		} catch (Exception e) {
			logger.error("Error happens when get job count," + e);
		}
		// put total row number in the map
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

	@RequestMapping(value = "/saveAllocBatchJob")
	@ResponseBody
	public Object saveAllocBatchJob(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		// get dimension bean from request
		AllocRequestInstanceBean reqBean = new AllocRequestInstanceBean();
		boolean isError = false;
		String errorMsg = "保存信息失败：请检查输入数据，重新提交！";
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
				
		try {
			// initialize bean with request
			reqBean.constructBeanWithRequest(request, true);
			if (reqBean.inputValidate(true) != 0) {
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
				// set start time
				reqBean.setStartDate(new Date());
				// set status
				reqBean.setStatus(JobStatus.SCHEDULED);
				// save job into database
				long newKey = 0;
				try {
					newKey = dataProcessService.createAllocBatchJobRecord(reqBean);
				} catch (Exception e) {
					logger.error("Error happens when create batch job instance," + e);
				}
				if (newKey == 0) {
					isError = true;
				}
				reqBean.setInstanceId(newKey);
			}
		} catch (Exception e) {
			isError = true;
			logger.error("Error happens when create batch job instance," + e);
		}

		if (isError) {
			dataMap.put("isError", true);
			dataMap.put("msg", errorMsg);
		} else {
			dataMap = reqBean.constructReturnMap(true);
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

	@RequestMapping(value = "/updateAllocBatchJob")
	@ResponseBody
	public Object updateAllocBatchJob(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		// get dimension bean from request
		AllocRequestInstanceBean reqBean = new AllocRequestInstanceBean();
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
			
		try {
			reqBean.constructBeanWithRequest(request, false);
			if (reqBean.inputValidate(false) != 0) {
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
				reqBean.setStartDate(new Date());
				reqBean.setStatus(JobStatus.SCHEDULED);
				// save dimension into database
				int updateCount = 0;
				try {
					updateCount = dataProcessService.updateAllocBatchJobRecord(reqBean);
				} catch (Exception e) {
					logger.error("Error happens when update batch job instance," + e);
				}
				if (updateCount == 0) {
					isError = true;
				}
			}
		} catch (Exception e) {
			isError = true;
			logger.error("Error happens when update batch job instance," + e);
		}

		if (isError) {
			dataMap.put("isError", true);
			dataMap.put("msg", errorMsg);
		} else {
			dataMap = reqBean.constructReturnMap(false);
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

	@RequestMapping(value = "/deleteAllocBatchJob")
	@ResponseBody
	public Object deleteAllocBatchJob(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		// get job bean from request
		AllocRequestInstanceBean reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		
		try {
			String jobInsId = this.getDecodedRequestParam(request, "id");
			if (StringUtils.isBlank(jobInsId)) {
				isError = true;
				errorMsg = "没有选中需要删除的记录，请选择后再删除！";
			} else {
				reqBean = new AllocRequestInstanceBean();
				reqBean.setInstanceId(Long.parseLong(jobInsId));
				String userId = (String) session.getAttribute(Constants.USER_KEY);
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
					delCount = dataProcessService.deleteAllocBatchJobRecord(reqBean);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("Errors while delete rule header: " + e);
				}
				// if there's no record delete
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
			dataMap = reqBean.constructReturnMap(false);
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

	@RequestMapping(value = "/showAllocDataProcessView")
	public String showAllocDataProcessView(HttpServletRequest request,HttpServletResponse response, Model model) {
		// 初始化执行参数
		AllocRequestInstanceBean requestInsBean = new AllocRequestInstanceBean();
		// get ledgerList
		/*
		 * List<LedgerBean> ledgerList = null; try { ledgerList =
		 * dataProcessService.getLedgerList(); } catch (Exception e) {
		 * logger.error("Error happen when get ledger list," + e); }
		 * 
		 * if (ledgerList != null && 0 < ledgerList.size()) {
		 * Iterator<LedgerBean> iter = ledgerList.iterator();
		 * while(iter.hasNext()) { LedgerBean tmp = iter.next(); try {
		 * Integer.parseInt(tmp.getLederCode()); } catch (Exception e) {
		 * iter.remove(); logger.warn("账套Id为非数字类型，不能处理：" + "LedgerId=" +
		 * tmp.getLederCode() + ", ledgerName=" + tmp.getLederName()); } } }
		 */
		List<AllocRuleBean> allocRuleList = new ArrayList<AllocRuleBean>();
		AllocRuleBean tempbean1 = new AllocRuleBean();
		tempbean1.setDriverType("GROUP");
		tempbean1.setDescription("分摊规则组");
		allocRuleList.add(tempbean1);
		AllocRuleBean tempbean2 = new AllocRuleBean();
		tempbean2.setDriverType("DYNAMIC");
		tempbean2.setDescription("动态因子");
		allocRuleList.add(tempbean2);
		AllocRuleBean tempbean3 = new AllocRuleBean();
		tempbean3.setDriverType("STATIC");
		tempbean3.setDescription("静态因子");
		allocRuleList.add(tempbean3);
		AllocRuleBean tempbean4 = new AllocRuleBean();
		tempbean4.setDriverType("CONSTANT");
		tempbean4.setDescription("常数");
		allocRuleList.add(tempbean4);
		requestInsBean.setTypeList(allocRuleList);
		/*
		 * if (ledgerList != null && 0 < ledgerList.size()) {
		 * requestInsBean.setLedgerSelectionList(ledgerList); }
		 */
		// get period List
		List<PeriodBean> periodList = null;
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
			

		try {
			// periodList = dataProcessService.getPeriodList();
			periodList = dataProcessService.getAvailablePeriodList();
		} catch (Exception e) {
			logger.error("Error happen when get period list," + e);
		}
		if (periodList != null && 0 < periodList.size()) {
			requestInsBean.setPeriodList(periodList);
		}
		model.addAttribute("executCondition", requestInsBean);
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
		return "configuration/AllocationRulesSetting/allocJobDataCalculation";
	}

	@RequestMapping(value = "/getRuleIdForSelect")
	@ResponseBody
	public Object getRuleIdForSelect(HttpServletRequest request,HttpServletResponse response) {
		String type = null;
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		try {
			type = getDecodedRequestParam(request, "type");
		} catch (UnsupportedEncodingException e1) {
			logger.error("error ouccer in get rule list");
			e1.printStackTrace();
		}
		List<Map<String, Object>> result = null;
		try {
			if ("GROUP".equals(type)) {
				result = dataProcessService.getGroupRuleList();
			} else {
				result = dataProcessService.getruleList(type);
			}

		} catch (Exception e) {
			logger.error("Error occurs when get ledger List," + e);
		}
		return JsonUtil.fromObject(result);
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

	@RequestMapping(value = "/AllocPerformCalculation")
	@ResponseBody
	public Object AllocPerformCalculation(HttpServletRequest request,HttpServletResponse response
, HttpSession httpSession,
			@ModelAttribute AllocRequestInstanceBean requestBean) {
		// get session user
		String userId = (String) httpSession.getAttribute(Constants.USER_KEY);
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
			

			if (StringUtils.isBlank(userId)) {
			userId = "Web";
		}
		requestBean.setRequestUser(userId);
		boolean isError = false;
		String message = "任务处理完成";
		// perform calculation
		try {
			// GET INSTNCE ID
			String jobInstanceId = this.getDecodedRequestParam(request, "jobId");
			if (!StringUtils.isBlank(jobInstanceId)) {
				requestBean.setInstanceId(Long.parseLong(jobInstanceId));
			}
			// try to get instacne
			requestBean = dataProcessService.getAllocRequestInstanceByPrimaryKey(requestBean);
			if (requestBean == null || requestBean.getInstanceId() == 0) {
				throw new Exception("No instance found in the db");
			}
			requestBean.setRequestUser("Web");
			dataProcessService.calculateAllocJob(requestBean);
		} catch (DuplicateJobRunningException de) {
			isError = true;
			message = "多个任务不能同时运行";
		} catch (Exception e) {
			message = "任务处理失败";
			logger.error("performCalculation():" + e.toString());

		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", !isError);
		result.put("message", message);

		return JsonUtil.fromObject(result);
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

	@RequestMapping(value = "/getAllocRequestInstance")
	@ResponseBody
	public Object getAllocRequestInstance(HttpServletRequest request,HttpServletResponse response) {
		// 参数
		AllocRequestInstanceRecord requestQueryBean = new AllocRequestInstanceRecord();

		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
			

		try {
			// handle the pagination case, initialize page related information
			this.initilizePagingInfo(request);
			// set query start index
			requestQueryBean.setRowStartIndex(this.queryRowStartIndex);
			// set row count
			requestQueryBean.setRowCount(this.rows);

			String ruleId = this.getDecodedRequestParam(request, "ruleId");
			if (!StringUtils.isBlank(ruleId)) {
				requestQueryBean.setRuleId(Integer.parseInt(ruleId));
			}
			requestQueryBean.setRuleType(this.getDecodedRequestParam(request, "RuleType"));
			requestQueryBean.setPeriod(this.getDecodedRequestParam(request, "period"));
		} catch (UnsupportedEncodingException e1) {
			logger.error("getRequestInstanceList():" + e1.toString());
		}

		List<Map<String, Object>> list = null;
		try {
			list = dataProcessService.getAllocRequestInstance(requestQueryBean);
		} catch (Exception e) {
			logger.error("getRequestInstanceList():" + e.toString());
		}

		JSONArray jsonlist = JsonUtil.fromObject(list);
		// put rows into map
		dataMap.put("rows", jsonlist);
		int count = 0;
		try {
			count = dataProcessService.getAllocRequestInstanceCount(requestQueryBean);
		} catch (Exception e) {
			logger.error("Error happens when get job count," + e);
		}
		// put total row number in the map
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

	@RequestMapping(value = "/saveAllocRequestInstance")
	@ResponseBody
	public Object saveAllocRequestInstance(HttpServletRequest request,HttpServletResponse response
, HttpSession session) {
		// get dimension bean from request
		AllocRequestInstanceRecord reqBean = new AllocRequestInstanceRecord();
		boolean isError = false;
		String errorMsg = "保存信息失败：请检查输入数据，重新提交！";
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
	
		try {
			// initialize bean with request
			reqBean.constructBeanWithRequest(request, true);
			if (reqBean.inputValidate(true) != 0) {
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
				// save job into database
				long newKey = 0;
				try {
					newKey = dataProcessService.saveAllocRequestInstance(reqBean);
				} catch (Exception e) {
					logger.error("Error happens when create batch job instance," + e);
				}
				if (newKey == 0) {
					isError = true;
				}
				reqBean.setInstanceId(newKey);
			}
		} catch (Exception e) {
			isError = true;
			logger.error("Error happens when create batch job instance," + e);
		}

		if (isError) {
			dataMap.put("isError", true);
			dataMap.put("msg", errorMsg);
		} else {
			dataMap = reqBean.constructReturnMap(true);
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

	@RequestMapping(value = "/updateAllocRequestInstance")
	@ResponseBody
	public Object updateAllocRequestInstance(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		// get dimension bean from request
		AllocRequestInstanceRecord reqBean = new AllocRequestInstanceRecord();
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();

		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){

		try {
			reqBean.constructBeanWithRequest(request, false);
			if (reqBean.inputValidate(false) != 0) {
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
					updateCount = dataProcessService.updateAllocRequestInstance(reqBean);
				} catch (Exception e) {
					logger.error("Error happens when update batch job instance," + e);
				}
				if (updateCount == 0) {
					isError = true;
				}
			}
		} catch (Exception e) {
			isError = true;
			logger.error("Error happens when update batch job instance," + e);
		}

		if (isError) {
			dataMap.put("isError", true);
			dataMap.put("msg", errorMsg);
		} else {
			dataMap = reqBean.constructReturnMap(false);
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

	@RequestMapping(value = "/deleteAllocRequestInstance")
	@ResponseBody
	public Object deleteAllocRequestInstance(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		// get job bean from request
		AllocRequestInstanceRecord reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
	
		try {
			String jobInsId = this.getDecodedRequestParam(request, "id");
			if (StringUtils.isBlank(jobInsId)) {
				isError = true;
				errorMsg = "没有选中需要删除的记录，请选择后再删除！";
			} else {
				reqBean = new AllocRequestInstanceRecord();
				reqBean.setInstanceId(Long.parseLong(jobInsId));
				String userId = (String) session.getAttribute(Constants.USER_KEY);
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
					delCount = dataProcessService.deleteAllocRequestInstance(reqBean);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("Errors while delete rule header: " + e);
				}
				// if there's no record delete
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
			dataMap = reqBean.constructReturnMap(false);
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

	@RequestMapping(value = "/AllocRequestInstanceLineCalculation")
	@ResponseBody
	public Object AllocRequestInstanceLineCalculation(HttpServletRequest request,HttpServletResponse response, HttpSession httpSession,
			@ModelAttribute AllocRequestInstanceRecord requestBean) {
		// get session user
		String userId = (String) httpSession.getAttribute(Constants.USER_KEY);

		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
			

		if (StringUtils.isBlank(userId)) {
			userId = "Web";
		}
		requestBean.setRequestUser(userId);
		boolean isError = false;
		String message = "任务处理完成";
		// perform calculation
		try {
			// GET INSTNCE ID
			String jobInstanceId = this.getDecodedRequestParam(request, "jobId");
			if (!StringUtils.isBlank(jobInstanceId)) {
				requestBean.setInstanceId(Long.parseLong(jobInstanceId));
			}
			// try to get instacne
			requestBean = dataProcessService.getAllocRequestInstanceByPrimaryKey(requestBean);
			if (requestBean == null || requestBean.getInstanceId() == 0) {
				throw new Exception("No instance found in the db");
			}
			requestBean.setRequestUser("Web");
			// request instance save as history;
			AllocRequestInstanceHistoryRecord requestHistoryBean = null;
			if ("GROUP".equals(requestBean.getRuleType())) {
				List<Long> ruleIdList = new ArrayList<Long>();
				ruleIdList = dataProcessService.getruleListForLoop(requestBean);
				if (ruleIdList.size() == 0) {
					throw new Exception("this group dont have any line");
				} else {
					for (long ruleId : ruleIdList) {
						requestBean.setRuleId(ruleId);
						requestHistoryBean = this.getHistoryRecordByInstanceRecord(requestBean);
						requestHistoryBean.setGroupHeaderId(requestBean.getRuleId());
						long primaryKey = dataProcessService.saveAllocRequestHistoryInstance(requestHistoryBean);
						if (primaryKey != 0) {
							requestHistoryBean.setHistoryId(primaryKey);
						}
						dataProcessService.calculateAllocJobForInstacneHistory(requestHistoryBean);
					}
				}
			} else {

				requestHistoryBean = this.getHistoryRecordByInstanceRecord(requestBean);

				long primaryKey = dataProcessService.saveAllocRequestHistoryInstance(requestHistoryBean);
				if (primaryKey != 0) {
					requestHistoryBean.setHistoryId(primaryKey);
				}
				dataProcessService.calculateAllocJobForInstacneHistory(requestHistoryBean);
			}
		} catch (DuplicateJobRunningException de) {
			isError = true;
			message = "多个任务不能同时运行";
		} catch (Exception e) {
			message = "任务处理失败";
			logger.error("performCalculation():" + e.toString());

		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", !isError);
		result.put("message", message);
		return JsonUtil.fromObject(result);
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

	private AllocRequestInstanceHistoryRecord getHistoryRecordByInstanceRecord(AllocRequestInstanceRecord requestBean) {
		AllocRequestInstanceHistoryRecord requestHistoryBean = new AllocRequestInstanceHistoryRecord();
		requestHistoryBean.setInstanceId(requestBean.getInstanceId());
		requestHistoryBean.setRuleId(requestBean.getRuleId());
		requestHistoryBean.setPeriod(requestBean.getPeriod());
		requestHistoryBean.setCreatedBy("Web");
		requestHistoryBean.setCreationDate(new Date());
		requestHistoryBean.setLastUpdatedBy("Web");
		requestHistoryBean.setLastUpdateDate(new Date());
		requestHistoryBean.setStartTime(new Date());
		requestHistoryBean.setStatus(JobStatus.SCHEDULED);
		return requestHistoryBean;
	}

	@RequestMapping({ "/showAllocInstanceHistoryView" })
	public String showAllocInstanceHistoryView(HttpServletRequest request,HttpServletResponse response, Model model, HttpSession httpSession) {
		String instanceId = null;
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
	
		try {
			instanceId = getDecodedRequestParam(request, "instanceId");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (/*!StringUtils.isBlank(instanceId)*/true) {
			AllocRequestInstanceRecord parentBean = new AllocRequestInstanceRecord();
			/*parentBean.setInstanceId(Long.parseLong(instanceId));
			try {
				parentBean = this.dataProcessService.getAllocRequestInstanceByPrimaryKey(parentBean);
			} catch (NumberFormatException ne) {
				ne.printStackTrace();
			} catch (Exception localException) {
			}*/
			//if (parentBean != null) {
				model.addAttribute("requestInstance", parentBean);
			//}
			String description = " ";
			if (!StringUtils.isBlank(parentBean.getDescription())) {
				description = parentBean.getDescription();
			}
			httpSession.removeAttribute(SESSION_KEY_REQUESTINSTANCEID);
			httpSession.setAttribute(SESSION_KEY_REQUESTINSTANCEID, parentBean.getInstanceId());
			httpSession.removeAttribute(SESSION_KEY_REQUESTINSTANCENAME);
			httpSession.setAttribute(SESSION_KEY_REQUESTINSTANCENAME, description);
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
		return "configuration/AllocationRulesSetting/allocJobInstanceHistoryCalculation";
	}

	@RequestMapping({ "/findAllocInstanceHistory" })
	@ResponseBody
	public Object findAllocInstanceHistory(HttpServletRequest request,HttpServletResponse response, HttpSession httpSession) {
		AllocRequestInstanceHistoryRecord requestBean = new AllocRequestInstanceHistoryRecord();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){

		try {
			initilizePagingInfo(request);
			requestBean.setRowStartIndex(this.queryRowStartIndex);
			requestBean.setRowCount(this.rows);
		} catch (UnsupportedEncodingException e1) {
			logger.error("findDriverStaticLineValue():" + e1);
		}
		/*
		 * 历史记录全部查询出来
		 * 
		 * */
		//long headerId = ((Long) httpSession.getAttribute(SESSION_KEY_REQUESTINSTANCEID)).longValue();
		//requestBean.setInstanceId(headerId);
		/*
		 * add condition
		 */
		List<Map<String, Object>> list = null;
		try {
			list = this.dataProcessService.findAllocInstanceHistory(requestBean);
		} catch (Exception e) {
			logger.error("findDriverStaticLine():" + e);
		}
		int count = 0;
		try {
			count = this.dataProcessService.getCountAllocInstanceHistory(requestBean);
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

	@RequestMapping(value = "/rollbackAllocRequestInstanceLineCalculation")
	@ResponseBody
	public Object rollbackAllocRequestInstanceLineCalculation(HttpServletRequest request,HttpServletResponse response, HttpSession httpSession,
			@ModelAttribute AllocRequestInstanceRecord requestBean) {
		// get session user
		String userId = (String) httpSession.getAttribute(Constants.USER_KEY);
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.QP)){
		
		if (StringUtils.isBlank(userId)) {
			userId = "Web";
		}
		// requestBean.setRequestUser(userId);
		boolean isError = false;
		String message = "任务处理完成";
		AllocRequestInstanceHistoryRecord requestHistoryBean = new AllocRequestInstanceHistoryRecord();
		// perform calculation
		try {
			// GET INSTNCE ID
			String jobInstanceHistoryId = this.getDecodedRequestParam(request, "jobId");
			if (!StringUtils.isBlank(jobInstanceHistoryId)) {
				requestHistoryBean.setHistoryId(Long.parseLong(jobInstanceHistoryId));
			}
			// try to get requestHistoryBean
			requestHistoryBean = dataProcessService.getAllocRequestInstanceHistoryByPrimaryKey(requestHistoryBean);
			if (requestHistoryBean == null || requestHistoryBean.getHistoryId() == 0) {
				throw new Exception("No requestHistoryBean found in the db");
			}
			// requestBean.setRequestUser("Web");
			requestHistoryBean.setLastUpdateDate(new Date());
			requestHistoryBean.setLastUpdatedBy(userId);
			dataProcessService.calculateRollbackAllocJobForInstacneHistory(requestHistoryBean);
		} catch (DuplicateJobRunningException de) {
			isError = true;
			message = "多个任务不能同时运行";
		} catch (Exception e) {
			message = "任务处理失败";
			logger.error("performCalculation():" + e.toString());

		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", !isError);
		result.put("message", message);

		return JsonUtil.fromObject(result);
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
}