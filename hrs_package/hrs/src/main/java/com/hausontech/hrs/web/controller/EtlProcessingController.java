package com.hausontech.hrs.web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hausontech.hrs.api.constants.Constants;
import com.hausontech.hrs.api.etlProcess.service.IKettleJobService;
import com.hausontech.hrs.bean.etlProcess.JobInstanceBean;
import com.hausontech.hrs.bean.etlProcess.ScheduleInstanceBean;
import com.hausontech.hrs.bean.etlProcess.ScheduleStatusBean;
import com.hausontech.hrs.bean.etlProcess.ScheduleTypeBean;
import com.hausontech.hrs.daoImpl.engine.quartz.QuartzManager;
import com.hausontech.hrs.utils.Comp;
import com.hausontech.hrs.utils.JsonUtil;
import com.hausontech.hrs.utils.Loap;
import com.hausontech.hrs.utils.Tan;
import com.hausontech.hrs.utils.ThirdLevelType;

import net.sf.json.JSONArray;

@Controller
@RequestMapping(value = "/etlProcessing")
public class EtlProcessingController extends BaseController{
	
	/** 组件日志 */ 
	private static Logger logger = LoggerFactory.getLogger(EtlProcessingController.class); 
	@Resource
	private IKettleJobService etlJobService;

	public IKettleJobService getEtlJobService() {
		return etlJobService;
	}

	public void setEtlJobService(IKettleJobService etlJobService) {
		this.etlJobService = etlJobService;
	}

	/*Loop group Header definition related methods ---------Start*/
	@RequestMapping(value = "/getEtlJobList")
	@ResponseBody
	public Object getEtlJobList (HttpServletRequest request,HttpServletResponse response) {
		// 参数
		JobInstanceBean jobInstanceBean = new JobInstanceBean();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			//handle the pagination case, initialize page related information
			this.initilizePagingInfo(request);
			//set query start index
			jobInstanceBean.setRowStartIndex(this.queryRowStartIndex);
			//set row count
			jobInstanceBean.setRowCount(this.rows);
			//get other parameter from request
			jobInstanceBean.setJobName(this.getDecodedRequestParam(request, "etlJobName"));
			jobInstanceBean.setStatus(this.getDecodedRequestParam(request, "etlJobStatus"));
		} catch (UnsupportedEncodingException e1) {
			logger.error("getEtlJobList():", e1);
		}
		List<Map<String, Object>> list = null;
		try {
			list = etlJobService.selectEtlJobList(jobInstanceBean);
		} catch (Exception e) {
			logger.error("getEtlJobList():", e);
		}
		JSONArray jsonlist = JsonUtil.fromObject(list);
		dataMap.put("rows", jsonlist);
		int count = 0;
		try {
			count = etlJobService.getJobCount(jobInstanceBean);
		} catch (Exception e) {
			logger.error("getEtlJobList():", e);
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
	
	
	/*获取调度列*/
	@RequestMapping(value = "/getEtlScheduleList")
	@ResponseBody
	public Object getEtlScheduleList (HttpServletRequest request,HttpServletResponse response) {
		// 参数
		ScheduleInstanceBean scheduleBean = new ScheduleInstanceBean();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			//handle the pagination case, initialize page related information
			this.initilizePagingInfo(request);
			//set query start index
			scheduleBean.setRowStartIndex(this.queryRowStartIndex);
			//set row count
			scheduleBean.setRowCount(this.rows);
			//get other parameter from request
			scheduleBean.setJobName(this.getDecodedRequestParam(request, "etlJobName"));
			scheduleBean.setStatus(this.getDecodedRequestParam(request, "etlJobStatus"));
		} catch (UnsupportedEncodingException e1) {
			logger.error("getEtlJobList():", e1);
		}
		List<Map<String, Object>> list = null;
		try {
			list = etlJobService.selectEtlScheduleList(scheduleBean);
		} catch (Exception e) {
			logger.error("getEtlJobList():", e);
		}
		JSONArray jsonlist = JsonUtil.fromObject(list);
		dataMap.put("rows", jsonlist);
		int count = 0;
		try {
			count = etlJobService.getScheduleCount(scheduleBean);
		} catch (Exception e) {
			logger.error("getEtlJobList():", e);
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
	
	
	/*动态获取jobname数据回显使用*/
	@RequestMapping(value = "/getEtlJobNameList")
	@ResponseBody
	public Object getEtlJobNameList (HttpServletRequest request,HttpServletResponse response) {
		// 参数
		JSONArray jsonlist=null;
		JobInstanceBean jobInstanceBean = new JobInstanceBean();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			//handle the pagination case, initialize page related information
			this.initilizePagingInfo(request);
		} catch (UnsupportedEncodingException e1) {
			logger.error("getEtlJobNameList():", e1);
		}
		List<Map<String, Object>> list = null;
		jobInstanceBean.setRowCount(99999);
		jobInstanceBean.setRowStartIndex(1);
		try {
			list = etlJobService.selectEtlJobList(jobInstanceBean);
		} catch (Exception e) {
			logger.error("getEtlJobNameList():", e);
		}
		jsonlist = JsonUtil.fromObject(list);
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
		return JsonUtil.fromObject(jsonlist);
	}
	
	/*获取调度执行类型数据*/
	@RequestMapping(value = "/getEtlScheduleTypeList")
	@ResponseBody
	public Object getEtlScheduleTypeList (HttpServletRequest request,HttpServletResponse response) {
		// 参数
		JSONArray jsonlist=null;
		ScheduleTypeBean typeBean = new ScheduleTypeBean();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			//handle the pagination case, initialize page related information
			this.initilizePagingInfo(request);
		} catch (UnsupportedEncodingException e1) {
			logger.error("getEtlScheduleTypeList():", e1);
		}
		List<Map<String, Object>> list = null;
		try {
			list = etlJobService.selectScheduleTypeList(typeBean);
		} catch (Exception e) {
			logger.error("getEtlScheduleTypeList():", e);
		}
		jsonlist = JsonUtil.fromObject(list);
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
		return JsonUtil.fromObject(jsonlist);
	}
	
	/*根据所有id执行ETL任务*/
	@RequestMapping(value = "/getAllEtlJobList")
	@ResponseBody
	public Object getAllEtlJobList (HttpServletRequest request,HttpServletResponse response) {
		// 参数
		JSONArray jsonlist=null;
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			//handle the pagination case, initialize page related information
			this.initilizePagingInfo(request);
		} catch (UnsupportedEncodingException e1) {
			logger.error("getAllEtlJobList():", e1);
		}
		List<Map<String, Object>> list = null;
		try {
			list = etlJobService.getAllEtlJobList();
		} catch (Exception e) {
			logger.error("getAllEtlJobList():", e);
		}
		jsonlist = JsonUtil.fromObject(list);
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
		return JsonUtil.fromObject(jsonlist);
	}
	
	/*获取调度状态数据*/
	@RequestMapping(value = "/getEtlScheduleStatusList")
	@ResponseBody
	public Object getEtlScheduleStatusList (HttpServletRequest request,HttpServletResponse response, Model model) {
		// 参数
		JSONArray jsonlist=null;
		ScheduleStatusBean statusBean = new ScheduleStatusBean();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			//handle the pagination case, initialize page related information
			this.initilizePagingInfo(request);
		} catch (UnsupportedEncodingException e1) {
			logger.error("getEtlScheduleStatusList():", e1);
		}
		List<Map<String, Object>> list = null;
		try {
			list = etlJobService.selectScheduleStatusList(statusBean);
		} catch (Exception e) {
			logger.error("getEtlScheduleStatusList():", e);
		}
		jsonlist = JsonUtil.fromObject(list);
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
		return JsonUtil.fromObject(jsonlist);
	}
	
	@RequestMapping(value = "/saveEtlSchedule")
	@ResponseBody
	public Object saveEtlSchedule(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		//get dimension bean from request
		ScheduleInstanceBean scheduleBean = null;
		boolean isError = false;
		String errorMsg = "保存信息失败：请检查输入数据，重新提交！";
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			scheduleBean = this.constructScheduleBean(request);
			String jobName=etlJobService.getNameById(scheduleBean.getJobId());
			scheduleBean.setJobName(jobName);
			if (!this.isScheduleInputValid(scheduleBean, true)) {
				isError = true;
				errorMsg = "输入信息有误，请检查数据格式，重新保存！";
			} else {
				//save job into database
				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
					    .getAuthentication()
					    .getPrincipal();

				String userId = userDetails.getUsername();
				//String userId = (String) session.getAttribute(Constants.USER_KEY);
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				scheduleBean.setCreatedBy(userId);
				scheduleBean.setCreationDate(new Date());
				scheduleBean.setLastUpdatedBy(userId);
				scheduleBean.setLastUpdateDate(new Date());
				int newKey = 0;
				try {
					newKey = etlJobService.createScheduleRecord(scheduleBean);
				} catch (Exception e) {
					request.getRequestDispatcher("/error/webErr.jsp").forward(request, response);
					logger.error("saveEtlDateJobInfo():", e);
				}
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (isError) {
			dataMap.put("isError", true);
			dataMap.put("msg", errorMsg);
		} else {
			constructScheduleReturnMap(scheduleBean);	
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
	
	@RequestMapping(value = "/updateEtlSchedule")
	@ResponseBody
	public Object updateEtlSchedule(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		//get dimension bean from request
		ScheduleInstanceBean reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			reqBean = constructScheduleBean(request);
			String jobName=etlJobService.getNameById(reqBean.getJobId());
			reqBean.setJobName(jobName);
			if (!this.isScheduleInputValid(reqBean, false)) {
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
					updateCount = etlJobService.updateScheduleRecord(reqBean);
					QuartzManager.removeJob(String.valueOf(reqBean.getScheduleId()));
				} catch (Exception e) {
					logger.error("updateEtlJbo():", e);
				}
				if (updateCount == 0) {
					isError = true;
				}
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (isError) {
			dataMap.put("isError", true);
			dataMap.put("msg", errorMsg);
		} else {
			constructScheduleReturnMap(reqBean);	
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
	
	@RequestMapping(value = "/deleteEtlSchedule")
	@ResponseBody
	public Object deleteEtlSchedule(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		//get job bean from request
		ScheduleInstanceBean scheduleBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			String ScheduleId = this.getDecodedRequestParam(request, "id");
			if (StringUtils.isBlank(ScheduleId)) {
				isError = true;
				errorMsg = "没有选中需要删除的记录，请选择后再删除！";
			} else {
				scheduleBean = new ScheduleInstanceBean();
				scheduleBean.setScheduleId(Integer.parseInt(ScheduleId));

				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
					    .getAuthentication()
					    .getPrincipal();

				String userId = userDetails.getUsername();
				//String userId = (String) session.getAttribute(Constants.USER_KEY);
				if (StringUtils.isBlank(userId)) {
					userId = "Web";
				}
				scheduleBean.setCreatedBy(userId);
				scheduleBean.setCreationDate(new Date());
				scheduleBean.setLastUpdatedBy(userId);
				scheduleBean.setLastUpdateDate(new Date());
				//save dimension into database
				int delCount = 0;
				try {
					delCount = etlJobService.deleteScheduleRecord(scheduleBean);
				} catch (Exception e) {
					logger.error("deleteEtlJob():", e);
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
			constructScheduleReturnMap(scheduleBean);	
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
	
	
	@RequestMapping(value = "/saveEtlJob")
	@ResponseBody
	public Object saveEtlJobInfo(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		//get dimension bean from request
		JobInstanceBean reqBean = null;
		boolean isError = false;
		String errorMsg = "保存信息失败：请检查输入数据，重新提交！";
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			reqBean = this.constructJobBean(request, true);
			if (!this.isJobInputValid(reqBean, true)) {
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
				//save job into database
				int newKey = 0;
				try {
					newKey = etlJobService.createJobRecord(reqBean);
				} catch (Exception e) {
					logger.error("saveEtlJobInfo():", e);
				}
				if (newKey == 0) {
					isError = true;
				}
				reqBean.setJobId(newKey);
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		}
		
		if (isError) {
			dataMap.put("isError", true);
			dataMap.put("msg", errorMsg);
		} else {
			constructJobReturnMap(reqBean);	
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
	
	@RequestMapping(value = "/updateEtlJob")
	@ResponseBody
	public Object updateEtlJbo(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		//get dimension bean from request
		JobInstanceBean reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			reqBean = constructJobBean(request, false);
			if (!this.isJobInputValid(reqBean, false)) {
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
					updateCount = etlJobService.updateJobRecord(reqBean);
				} catch (Exception e) {
					logger.error("updateEtlJbo():", e);
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
			constructJobReturnMap(reqBean);	
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
	/*删除etl任务记录*/
	@RequestMapping(value = "/deleteEtlJob")
	@ResponseBody
	public Object deleteEtlJob(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		//get job bean from request
		JobInstanceBean reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			String jobId = this.getDecodedRequestParam(request, "id");
			if (StringUtils.isBlank(jobId)) {
				isError = true;
				errorMsg = "没有选中需要删除的记录，请选择后再删除！";
			} else {
				reqBean = new JobInstanceBean();
				reqBean.setJobId(Integer.parseInt(jobId));
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
					delCount = etlJobService.deleteJobRecord(reqBean);
				} catch (Exception e) {
					logger.error("deleteEtlJob():", e);
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
			constructJobReturnMap(reqBean);	
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
	
	/*直接执行、调度执行*/
	@RequestMapping(value = "/processEtlJob")
	@ResponseBody
	public Object processEtlJob(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		//get job bean from request
		JobInstanceBean reqBean = new JobInstanceBean();
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		Map<String, Object> map = new HashMap<>();
		JSONArray jsonlist=null;
		List<Map<String, Object>> list = null;
		List<Map<String, Object>> listFlag = new ArrayList();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			String jobId = this.getDecodedRequestParam(request, "jobId");
			String scheduleId=this.getDecodedRequestParam(request, "scheduleId");
			//判断传来的scheduleId是否为空，调度执行以scheduleId为jobname任务名加入到quartz框架
			if(scheduleId!=null){
				reqBean.setScheduleId(Integer.parseInt(scheduleId));
			}
			if (StringUtils.isBlank(jobId)) {
				isError = true;
				errorMsg = "没有选中需要删除的记录，请选择后再删除！";
			} else {
				reqBean.setJobId(Integer.parseInt(jobId));
				if(scheduleId==null){
					try {
						list=etlJobService.processEtlJob(reqBean);//直接运行相关service
					} catch (Exception e) {
						logger.error("processEtlJob():", e);
						map.put("status", "2");
						map.put("message",e.toString());
						listFlag.add(map);
					}	
				}else{
					try {
						list=etlJobService.processEtlSchedule(reqBean);//调度运行相关service
					} catch (Exception e) {
						logger.error("processEtlSchedule():", e);
					}
				} 
			}
		} catch (Exception e) {
			isError = true;
		}
		jsonlist = JsonUtil.fromObject(list);
		if(list==null){
			 jsonlist = new JSONArray();
			if (listFlag==null ||listFlag.isEmpty()) {
	            return jsonlist;
	        }
	        for (Object object : listFlag) {
	        	jsonlist.add(object);
	        }
	        return jsonlist;
		}
		if (isError) {
			dataMap.put("isError", true);
			dataMap.put("msg", errorMsg);
		} else {
			constructJobReturnMap(reqBean);	
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
		return JsonUtil.fromObject(jsonlist);
	}
		
	
	@RequestMapping(value = "/getAllocJobList")
	@ResponseBody
	public Object getAllocJobList (HttpServletRequest request,HttpServletResponse response, Model model) {
		// 参数
		JobInstanceBean jobInstanceBean = new JobInstanceBean();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			//handle the pagination case, initialize page related information
			this.initilizePagingInfo(request);
			//set query start index
			jobInstanceBean.setRowStartIndex(this.queryRowStartIndex);
			//set row count
			jobInstanceBean.setRowCount(this.rows);
			//get other parameter from request
			jobInstanceBean.setJobName(this.getDecodedRequestParam(request, "allocJobName"));
			jobInstanceBean.setStatus(this.getDecodedRequestParam(request, "allocJobStatus"));
			jobInstanceBean.setJobType("allocationJob");  //add the flag to use in DAO implements   GXY 2017-03-29
		} catch (UnsupportedEncodingException e1) {
			logger.error("getEtlJobList():", e1);
		}
		List<Map<String, Object>> list = null;
		try {
			list = etlJobService.selectEtlJobList(jobInstanceBean);
		} catch (Exception e) {
			logger.error("getEtlJobList():", e);
		}
		JSONArray jsonlist = JsonUtil.fromObject(list);
		dataMap.put("rows", jsonlist);
		int count = 0;
		try {
			count = etlJobService.getJobCount(jobInstanceBean);
		} catch (Exception e) {
			logger.error("getEtlJobList():", e);
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
	
	
	@RequestMapping(value = "/saveAllocJob")
	@ResponseBody
	public Object saveAllocJobInfo(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		//get dimension bean from request
		JobInstanceBean reqBean = null;
		boolean isError = false;
		String errorMsg = "保存信息失败：请检查输入数据，重新提交！";
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			reqBean = this.constructJobBean(request, true);
			if (!this.isJobInputValid(reqBean, true)) {
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
				reqBean.setJobType("allocationJob");   //add the flag to use in DAO implements   GXY 2017-03-29
				//save job into database
				int newKey = 0;
				try {
					newKey = etlJobService.createJobRecord(reqBean);
				} catch (Exception e) {
					logger.error("saveEtlJobInfo():", e);
				}
				if (newKey == 0) {
					isError = true;
				}
				reqBean.setJobId(newKey);
			}
		} catch (UnsupportedEncodingException e) {
			isError = true;
		}
		
		if (isError) {
			dataMap.put("isError", true);
			dataMap.put("msg", errorMsg);
		} else {
			constructJobReturnMap(reqBean);	
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
	
	@RequestMapping(value = "/updateAllocJob")
	@ResponseBody
	public Object updateAllocJob(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		//get dimension bean from request
		JobInstanceBean reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			reqBean = constructJobBean(request, false);
			if (!this.isJobInputValid(reqBean, false)) {
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
				reqBean.setJobType("allocationJob");   //add the flag to use in DAO implements   GXY 2017-03-29
				//save dimension into database
				int updateCount = 0;
				try {
					updateCount = etlJobService.updateJobRecord(reqBean);
				} catch (Exception e) {
					logger.error("updateEtlJbo():", e);
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
			constructJobReturnMap(reqBean);	
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
	
	@RequestMapping(value = "/removeSchedule")
	@ResponseBody
	public void removeSchedule(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		//get job bean from request
		try {
			String scheduleId = this.getDecodedRequestParam(request, "scheduleId");
			QuartzManager.removeJob(scheduleId);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/deleteAllocJob")
	@ResponseBody
	public Object deleteAllocJob(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		//get job bean from request
		JobInstanceBean reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			String jobId = this.getDecodedRequestParam(request, "id");
			if (StringUtils.isBlank(jobId)) {
				isError = true;
				errorMsg = "没有选中需要删除的记录，请选择后再删除！";
			} else {
				reqBean = new JobInstanceBean();
				reqBean.setJobId(Integer.parseInt(jobId));
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
				reqBean.setJobType("allocationJob");   //add the flag to use in DAO implements   GXY 2017-03-29
				//save dimension into database
				int delCount = 0;
				try {
					delCount = etlJobService.deleteJobRecord(reqBean);
				} catch (Exception e) {
					logger.error("deleteEtlJob():", e);
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
			constructJobReturnMap(reqBean);	
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
	
	@RequestMapping(value = "/processAllocJob")
	@ResponseBody
	public Object processAllocJob(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		//get job bean from request
		JobInstanceBean reqBean = null;
		boolean isError = false;
		String errorMsg = ERROR_MESSAGE;
		dataMap = new HashMap();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			String jobId = this.getDecodedRequestParam(request, "jobId"); 
			if (StringUtils.isBlank(jobId)) {
				isError = true;
				errorMsg = "没有选中需要删除的记录，请选择后再删除！";
			} else {
				reqBean = new JobInstanceBean();
				reqBean.setJobId(Integer.parseInt(jobId));
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
				reqBean.setJobType("allocationJob");   //add the flag to use in DAO implements   GXY 2017-03-29
				//run the etl job
				try {
					etlJobService.processEtlJob(reqBean);
				} catch (Exception e) {
					logger.error("processEtlJob():", e);
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
			constructJobReturnMap(reqBean);	
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
	/* 
	 * 
	 * 
	 * 
	 *  end  */
	
	private JobInstanceBean constructJobBean(
			HttpServletRequest request, boolean isCreated) throws UnsupportedEncodingException {
		JobInstanceBean jobBean = null;
		if (request != null ) {
			jobBean = new JobInstanceBean();
			//set jobName
			jobBean.setJobName(this.getDecodedRequestParam(request, "JOB_NAME"));
			//set file path
			jobBean.setFilePath(this.getDecodedRequestParam(request, "FILE_PATH"));
			//set parameter list
			jobBean.setParamList(this.getDecodedRequestParam(request, "PARAM_LIST"));
			//set description
			jobBean.setDescription(this.getDecodedRequestParam(request, "DESCRIPTION"));
			//set orderId
			jobBean.setOrderId(Integer.parseInt(this.getDecodedRequestParam(request, "ORDER_ID")));
			if (!isCreated) {
				String jobId = this.getDecodedRequestParam(request, "JOB_ID");
				if (!StringUtils.isBlank(jobId)) {
					jobBean.setJobId(Integer.parseInt(jobId));
				}
			}		
		}
		return jobBean;
	}
	private ScheduleInstanceBean constructScheduleBean(
			HttpServletRequest request) throws UnsupportedEncodingException {
		ScheduleInstanceBean scheduleBean = null;
		if (request != null ) {
			scheduleBean = new ScheduleInstanceBean();
			
			scheduleBean.setJobName(this.getDecodedRequestParam(request, "JOB_NAME"));
			
			scheduleBean.setTypeProcess(this.getDecodedRequestParam(request, "TYPE_PROCESS"));
			
			scheduleBean.setValueProcess(this.getDecodedRequestParam(request, "VALUE_PROCESS"));	
		
			scheduleBean.setJobId(Integer.parseInt(this.getDecodedRequestParam(request, "JOB_ID")));
			
			scheduleBean.setConcurrent(this.getDecodedRequestParam(request, "CONCURRENT"));
			
			scheduleBean.setScheduleId(Integer.parseInt(this.getDecodedRequestParam(request, "SCHEDULE_ID")));
		}
		return scheduleBean;
	}

	private boolean isJobInputValid(JobInstanceBean jobBean, boolean isCreate) {
		boolean isValid = true;
		if (jobBean == null) {
			return false;
		}
		if (!isCreate) {
			if (jobBean.getJobId() == 0) {
				isValid = false;
			}
		}
		if (isValid) {
			if (StringUtils.isBlank(jobBean.getJobName()) || 
					StringUtils.isBlank(jobBean.getFilePath())) {
				isValid = false;
			}
		}
		return isValid;
	}
	
	private boolean isScheduleInputValid(ScheduleInstanceBean scheduleBean, boolean isCreate) {
		boolean isValid = true;
		if (scheduleBean == null) {
			return false;
		}
		if (!isCreate) {
			if (scheduleBean.getJobId() == 0) {
				isValid = false;
			}
		}
		if (isValid) {
			if (StringUtils.isBlank(scheduleBean.getJobName())) {
				isValid = false;
			}
		}
		return isValid;
	}
	
	private void constructJobReturnMap(JobInstanceBean jobBean) {
		dataMap = new HashMap();
		if (jobBean != null) {
			dataMap.put("JOB_ID", jobBean.getJobId());
			dataMap.put("JOB_NAME", jobBean.getJobName());
			dataMap.put("FILE_PATH", jobBean.getFilePath());
			dataMap.put("PARAM_LIST", jobBean.getParamList());
			dataMap.put("DESCRIPTION", jobBean.getDescription());
			dataMap.put("ORDER_ID", jobBean.getOrderId());
		}
	}

	private void constructScheduleReturnMap(ScheduleInstanceBean scheduleBean) {
		dataMap = new HashMap();
		if (scheduleBean != null) {
			dataMap.put("JOB_ID", scheduleBean.getJobId());
			dataMap.put("JOB_NAME", scheduleBean.getJobName());
			dataMap.put("TYPE_PROCESS", scheduleBean.getTypeProcess());
			dataMap.put("VALUE_PROCESS", scheduleBean.getValueProcess());
			dataMap.put("SCHEDULE_ID", scheduleBean.getScheduleId());
			//dataMap.put("STATUS", scheduleBean.getStatus());
		}
	}

}
