package com.hausontech.hrs.serviceImpl.etlProcess;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import org.apache.commons.lang.StringUtils;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.Result;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.logging.LogLevel;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hausontech.hrs.api.etlProcess.dao.IJobProcessDao;
import com.hausontech.hrs.api.etlProcess.service.IKettleJobService;
import com.hausontech.hrs.bean.etlProcess.JobInstanceBean;
import com.hausontech.hrs.bean.etlProcess.ScheduleInstanceBean;
import com.hausontech.hrs.bean.etlProcess.ScheduleStatusBean;
import com.hausontech.hrs.bean.etlProcess.ScheduleTypeBean;
import com.hausontech.hrs.daoImpl.IBaseDao2;
import com.hausontech.hrs.daoImpl.engine.quartz.QuartzManager;
import com.hausontech.hrs.daoImpl.engine.quartz.ScheduleTypeQuartzAddJob;
import com.hausontech.hrs.daoImpl.etlProcess.mapper.JobInstanceMapper;
import com.hausontech.hrs.serviceImpl.etlProcess.QuartzJob;
import com.hausontech.hrs.serviceImpl.etlProcess.QuartzStatefulJob;

@Service
public class KettleJobServiceImpl implements IKettleJobService {

	private static final String EXTENDED_PARAM_TOKEN = ",";
	//private IJobProcessDao jobProcessDao;
	@Autowired
	private JobInstanceMapper jobMapper;
	
	public JobInstanceMapper getJobMapper() {
		return jobMapper;
	}

	public void setJobMapper(JobInstanceMapper jobMapper) {
		this.jobMapper = jobMapper;
	}
	
	@Autowired
	private IBaseDao2 baseDao2;
	public IBaseDao2 getBaseDao2() {
		return baseDao2;
	}

	public void setBaseDao2(IBaseDao2 baseDao2) {
		this.baseDao2 = baseDao2;
	}


	/** 组件日志 */ 
	private static Logger logger = LoggerFactory.getLogger(KettleJobServiceImpl.class); 	
	/*public IJobProcessDao getJobProcessDao() {
		return jobProcessDao;
	}

	public void setJobProcessDao(IJobProcessDao jobProcessDao) {
		this.jobProcessDao = jobProcessDao;
	}*/
	
	@SuppressWarnings({ "unchecked", "null" })
	@Override
	public List<Map<String, Object>> selectEtlJobList(JobInstanceBean jobInsBean) throws Exception {
		if (jobInsBean == null) {
			throw new Exception("no query bean found");
		}
		List<Map<String, Object>> result =new ArrayList();
		Map<String, Object> map=null;
		List<JobInstanceBean> result1 = null;
		/*获取ETL任务界面数据转为Map*/
		String start=String.valueOf(jobInsBean.getRowStartIndex());
				String count=String.valueOf(jobInsBean.getRowCount());
				String jobName=jobInsBean.getJobName();
				String status=jobInsBean.getStatus();
			result1 = jobMapper.getEtlJobList(start,jobName,status,count);
			for (int i = 0; i < result1.size(); i++) {
				map=new HashMap<String, Object>();
				map.put("JOB_ID",result1.get(i).getJobId());
				map.put("JOB_NAME",result1.get(i).getJobName());
				map.put("FILE_PATH", result1.get(i).getFilePath());
				map.put("PARAM_LIST",result1.get(i).getParamList());
				map.put("DESCRIPTION",result1.get(i).getDescription());
				map.put("START_TIME", result1.get(i).getStartTime());
				map.put("END_TIME", result1.get(i).getEndTime());
				map.put("ELAPSED_TIME", result1.get(i).getElapsedTime());
				map.put("ORDER_ID", result1.get(i).getOrderId());
				map.put("MESSAGE", result1.get(i).getMessage());
				map.put("STATUS", result1.get(i).getStatus());
				map.put("CREATION_DATE", result1.get(i).getCreationDate());
				map.put("CREATED_BY", result1.get(i).getCreatedBy());
				map.put("LAST_UPDATE_DATE", result1.get(i).getLastUpdateDate());
				map.put("LAST_UPDATED_BY", result1.get(i).getLastUpdatedBy());
				result.add(map);
				}
		return result;
	}

	@Override
	public List<Map<String, Object>> selectScheduleTypeList(ScheduleTypeBean typeBean) throws Exception {
		if (typeBean == null) {
			throw new Exception("no query bean found");
		}
		/*获取调度执行类型数据转为map*/
		Map<String, Object> map=null;
		List<Map<String, Object>> result = new ArrayList();
		List<ScheduleTypeBean> result1 = null;
		result1 = jobMapper.getEtlScheduleTypeList();
		for (int i = 0; i < result1.size(); i++) {
			map=new HashMap<String, Object>();
			map.put("SCHEDULE_TYPE_ID",result1.get(i).getScheduleTypeId());
			map.put("SCHEDULE_TYPE",result1.get(i).getScheduleType());
			map.put("SCHEDULE_DESCRIPTION", result1.get(i).getScheduleDescription());
			result.add(map);
			}
		return result;
	}
	
	@Override
	public List<Map<String, Object>> selectEtlScheduleList(ScheduleInstanceBean scheduleBean) throws Exception {
		if (scheduleBean == null) {
			throw new Exception("no query bean found");
		}
		List<Map<String, Object>> result =new ArrayList();
		Map<String, Object> map=new HashMap<String, Object>();
		long scheduleId=0;
		List<ScheduleInstanceBean> result1 = null;
		/*获取任务调度界面数据转为MAP*/
		String start=String.valueOf(scheduleBean.getRowStartIndex());
				String count=String.valueOf(scheduleBean.getRowCount());
				String jobName=scheduleBean.getJobName();
				String status=scheduleBean.getStatus();
			result1 = jobMapper.getEtlScheduleList(start,jobName,status,count);
			for (int i = 0; i < result1.size(); i++) {
				map=new HashMap<String, Object>();
				scheduleId=result1.get(i).getScheduleId();
				map.put("JOB_ID",result1.get(i).getJobId());
				map.put("JOB_NAME",result1.get(i).getJobName());
				map.put("TYPE_PROCESS", result1.get(i).getTypeProcess());
				map.put("VALUE_PROCESS",result1.get(i).getValueProcess());
				map.put("CONCURRENT",result1.get(i).getConcurrent());
				map.put("SCHEDULE_ID", scheduleId);
				map.put("STATUS", QuartzManager.getJobStatue(String.valueOf(scheduleId)));
				map.put("CREATION_DATE", result1.get(i).getCreationDate());
				map.put("CREATED_BY", result1.get(i).getCreatedBy());
				map.put("LAST_UPDATE_DATE", result1.get(i).getLastUpdateDate());
				map.put("LAST_UPDATED_BY", result1.get(i).getLastUpdatedBy());
				result.add(map);
				}
		return result;
	}
	

	@Override
	public List<Map<String, Object>> selectScheduleStatusList(ScheduleStatusBean statusBean) throws Exception {
		// TODO Auto-generated method stub
		if (statusBean == null) {
			throw new Exception("no query bean found");
		}
		/*获取调度状态数据转为Map*/
		List<Map<String, Object>> result =new ArrayList();
		Map<String, Object> map=new HashMap<String, Object>();
		List<ScheduleStatusBean> result1 = null;
		result1 = jobMapper.getEtlScheduleStatusList(); 
		for (int i = 0; i < result1.size(); i++) {
			map=new HashMap<String, Object>();
			map.put("STATUS_ID",result1.get(i).getStatusId());
			map.put("STATUS_TYPE",result1.get(i).getStatusType());
			map.put("STATUS_DESCRIPTION",result1.get(i).getStatusDescription());
			result.add(map);
			}
		return result;
	}
	
	@Override
	public int getJobCount(JobInstanceBean jobInsBean) throws Exception {
		if (jobInsBean == null) {
			throw new Exception("no query bean found");
		}
		int result = 0;
		String jobName=jobInsBean.getJobName();
		String status=jobInsBean.getStatus();
		result = jobMapper.countByCondition(jobName,status); 
		return result;
	}
	
	@Override
	public int getScheduleCount(ScheduleInstanceBean scheduleBean) throws Exception {
		if (scheduleBean == null) {
			throw new Exception("no query bean found");
		}
		int result = 0;
		String jobName=scheduleBean.getJobName();
		String status=scheduleBean.getStatus();
		result = jobMapper.countByConditionSchedule(jobName,status); 
		return result;
	}
	
	@Override
	public int createJobRecord(JobInstanceBean jobRecord) throws Exception {
		if (jobRecord == null) {
			throw new Exception("The job bean is null.");
		}
		/*增加ETL任务记录*/
		/*获取ETL任务记录主键*/
		long primaryKey=baseDao2.getAutoGeneratedPrimaryKey("HRS_ETL_JOB_S");
		jobRecord.setJobId(primaryKey);
		int result = 0;
		result = jobMapper.saveEtlJob(jobRecord);
		return result;
	}
	
	@Override
	public int createScheduleRecord(ScheduleInstanceBean scheduleRecord) throws Exception {
		if (scheduleRecord == null) {
			throw new Exception("The job bean is null.");
		}
		int result = 0;
		long primaryKey=baseDao2.getAutoGeneratedPrimaryKey("HRS_ETL_SCHEDULE_S");
		scheduleRecord.setScheduleId(primaryKey);
		/*增加任务调度记录*/
		result = jobMapper.saveScheduleRecord(scheduleRecord);
		return result;
	}

	@Override
	public int updateJobRecord(JobInstanceBean jobBean) throws Exception {
		if (jobBean == null || jobBean.getJobId() == 0) {
			throw new Exception("The Job Bean is null or job id is empty.");
		}
		int result = 0;
		/*更新etl任务记录*/
		result = jobMapper.updateJobRecord(jobBean);
		return result;
	}
	
	@Override
	public int updateScheduleRecord(ScheduleInstanceBean scheduleRecord) throws Exception {
		if (scheduleRecord == null || scheduleRecord.getJobId() == 0) {
			throw new Exception("The Job Bean is null or job id is empty.");
		}
		int result = 0;
		/*更新ETL任务调度*/
		result = jobMapper.updateScheduleRecord(scheduleRecord);
		if(result>0){
			QuartzManager.removeJob(String.valueOf(scheduleRecord.getScheduleId()));
		}
		return result;
	}
	
	@Override
	public int deleteJobRecord(JobInstanceBean jobBean) throws Exception {
		if (jobBean == null || jobBean.getJobId() == 0) {
			throw new Exception("The Job Bean is null or job id is empty.");
		}
		int delCount = 0;
		int delCount1 = 0;
		//若删除etl的job对应调度跟着全部删除
		long primaryKey=jobBean.getJobId();
		/*删除etl调度任务记录*/
		delCount1 = jobMapper.deleteJobRecord("HRS_ETL_SCHEDULE",String.valueOf(primaryKey));
		/*删除etl任务记录*/
		delCount = jobMapper.deleteJobRecord("HRS_ETL_JOB",String.valueOf(primaryKey));
		return delCount>delCount1?delCount:delCount1;
	}
	
	@Override
	public int deleteScheduleRecord(ScheduleInstanceBean scheduleBean) throws Exception {
		if (scheduleBean == null || scheduleBean.getScheduleId() == 0) {
			throw new Exception("The Job Bean is null or job id is empty.");
		}
		int delCount = 0;
		//try to delete rule header
		delCount = jobMapper.deleteScheduleRecord(scheduleBean.getScheduleId());
		return delCount;
	}
	
	@SuppressWarnings("null")
	@Override
	public List<Map<String, Object>> processEtlJob(JobInstanceBean jobBean) throws Exception {
		if (jobBean == null || jobBean.getJobId() == 0) {
			throw new Exception("The Job Bean is null or job id is empty.");
		}
		int updateNumber=0;
		long primaryKey=0;
		JobInstanceBean dbJob = new JobInstanceBean();
		Map<String, Object> map = new HashMap<>();
		List<Map<String, Object>> list = new ArrayList();
		try {
			//try to get job from db
			if(StringUtils.isBlank(jobBean.getJobType())){
				dbJob = jobMapper.getJobInfoById(jobBean.getJobId());
				dbJob.setScheduleId(jobBean.getScheduleId());
			/*else if  get the attribute jobType have contents   2017-03-29 GXY*/
			}else if(jobBean.getJobType().equals("allocationJob")){
				//dbJob = jobProcessDao.getAllocJobInfoById(jobBean.getJobId());
			}
			
			if (dbJob == null || dbJob.getJobId() == 0) {
				throw new Exception("No job found in DB with job Id: " + jobBean.getJobId());
			}
			if (StringUtils.isBlank(dbJob.getFilePath())) {
				throw new Exception("no file path found for the job");
			}
			//process parameter list
			processExtendedParameters(dbJob);
			//process etl job
			this.initKettleEnv();
			//insert job instance
			dbJob.setStatus("processing");
			dbJob.setMessage("ETL job is running");
			String status = dbJob.getStatus();
			status = jobMapper.getStatus(null,status);
			dbJob.setStatus(status);
			primaryKey=baseDao2.getAutoGeneratedPrimaryKey("HRS_ETL_JOB_INSTANCE_S");
			dbJob.setLastJobInstanceId(primaryKey);
			dbJob.setStartTime(new Date());
			dbJob.setCreationDate(new Date());
			dbJob.setLastUpdateDate(new Date());
			dbJob.setCreatedBy("web");
			dbJob.setLastUpdatedBy("web");
			/*保存ETL任务执行实例*/
			jobMapper.saveEtlJobInstance(dbJob);
			long startTime = System.currentTimeMillis();
			Job job = this.runJobFromFileSystem(dbJob);	
			long ecalTime = System.currentTimeMillis() - startTime;
			dbJob.setElapsedTime((int)ecalTime);
		    dbJob.setStatus("success");
		    dbJob.setEndTime(new Date());
		    dbJob.setLastUpdateDate(new Date());
			status = dbJob.getStatus();
			String message = dbJob.getMessage();
			int length = message.length();
			int errorNumber = Integer.parseInt(message.substring(length - 9, length - 8));
			if (errorNumber > 0) {
				status = "failed";
			}
			status = jobMapper.getStatus(null,status);
			dbJob.setStatus(status);
			map.put("status", status);
			map.put("message",message);
			list.add(map);
			/*更新ETL任务执行实例*/
		    updateNumber=jobMapper.updateEtlJobInsRecord(dbJob);
		} catch (SQLException se) {
			throw new Exception(se);
		}
		return list; 
	}
	
	@Override
	public List<Map<String, Object>> processEtlSchedule(JobInstanceBean jobBean) throws Exception {
		if (jobBean == null || jobBean.getJobId() == 0) {
			throw new Exception("The Job Bean is null or job id is empty.");
		}
		JobInstanceBean dbJob = null;
		ScheduleInstanceBean scheduleBean=null;
		long scheduleId=jobBean.getScheduleId();
		try {
			//try to get job from db
			if(StringUtils.isBlank(jobBean.getJobType())){
				/*根据ETLjobID获取ETL任务记录*/
				dbJob = jobMapper.getJobInfoById(jobBean.getJobId());
				/*根据调度ID获取调度任务记录*/
				scheduleBean=jobMapper.getScheduleInfoById(scheduleId);
				dbJob.setScheduleId(scheduleId);
			/*else if  get the attribute jobType have contents   2017-03-29 GXY*/
			}else if(jobBean.getJobType().equals("allocationJob")){
				//dbJob = jobProcessDao.getAllocJobInfoById(jobBean.getJobId());
			}
			
			if (dbJob == null || dbJob.getJobId() == 0) {
				throw new Exception("No job found in DB with job Id: " + jobBean.getJobId());
			}
			if (StringUtils.isBlank(dbJob.getFilePath())) {
				throw new Exception("no file path found for the job");
			}
			//process parameter list
			processExtendedParameters(dbJob);
			//判断quartz框架job执行状态
			String status=QuartzManager.getJobStatue(String.valueOf(scheduleId));
			//执行区间值
			String scheduleData=scheduleBean.getValueProcess();
			//判断是否并行
			String concurrent=scheduleBean.getConcurrent();
			//执行类型
			String scheduleType=scheduleBean.getTypeProcess();
			//封装方法获取CRON表达式，动态调度quartz框架job
			String time=ScheduleTypeQuartzAddJob.getQuartzTimeByType(scheduleType, scheduleData);
			/*STATE_BLOCKED 4 阻塞 
			STATE_COMPLETE 2 完成 
			STATE_ERROR 3 错误 
			STATE_NONE -1 不存在 
			STATE_NORMAL 0 正常 
			STATE_PAUSED 1 暂停
			判断quartz框架中状态为NONE（不存在）则增加job，否则更新job
			*/
			if(status.equals("NONE")){
				//增加执行job
				if(concurrent.equals("1")){
					QuartzManager.addJob(String.valueOf(scheduleId), QuartzJob.class,time,dbJob,jobMapper,baseDao2);
				}else {
					QuartzManager.addJob(String.valueOf(scheduleId), QuartzStatefulJob.class,time,dbJob,jobMapper,baseDao2);
				}
				//String.valueOf(scheduleId)任务名，QuartzJob.class调度执行任务类，time 就是cron表达式时间设置
			}else{
				//更新执行Job
				if(concurrent.equals("1")){
					QuartzManager.modifyJobTime(String.valueOf(scheduleId),QuartzJob.class, time,dbJob,jobMapper,baseDao2);
				}else {
					QuartzManager.modifyJobTime(String.valueOf(scheduleId),QuartzStatefulJob.class, time,dbJob,jobMapper,baseDao2);
				}
			}
		} catch (SQLException se) {
			throw new Exception(se);
		}
		return null; 
	}
	
	private void processExtendedParameters(JobInstanceBean jobBean) {
		String parameterString = jobBean.getParamList();
		if (!StringUtils.isBlank(parameterString)) {
			StringTokenizer strtok = new StringTokenizer(parameterString, EXTENDED_PARAM_TOKEN);
	        while (strtok.hasMoreTokens()) {
	            String parameter = strtok.nextToken();
	            int idx = parameter.indexOf("=");
	            if (0 < idx) {
	            	String key = parameter.substring(0, idx);
	            	String value = parameter.substring(idx + 1);	            	
	            	if (!StringUtils.isBlank(key) && !StringUtils.isBlank(value)) {
	            		jobBean.setEtlParameter(key, value);
	            	}
	            }	   
	        }
		}
	}
	
	Job runJobFromFileSystem(JobInstanceBean jobObject) throws Exception {
		try {
			//check whether file exist
			String filePath = jobObject.getFilePath();
			File etlFile = new File(filePath);
			if (!etlFile.exists()) {
				throw new Exception ("no file found in the path: " + filePath);
			}
			JobMeta jobMeta = new JobMeta(filePath, null);
			
			Map<String, String> configParamList = jobObject.getParameterMap();
			String[] declaredParameters = jobMeta.listParameters();
			for (int i = 0; i < declaredParameters.length; ++i) {
				String parameterName = declaredParameters[i];
				String description = jobMeta.getParameterDescription(parameterName);
				String defaultValue = jobMeta.getParameterDefault(parameterName);
				String parameterValue = configParamList.get(parameterName);
				
				if (!StringUtils.isBlank(parameterValue)) {
					jobMeta.setParameterValue(parameterName, parameterValue);
				} else {
					jobMeta.setParameterValue(parameterName, defaultValue);
				}
			}

			Job job = new Job(null, jobMeta);
			job.setLogLevel(LogLevel.DETAILED);
			
			logger.info("\nStarting job");
			job.start();
			job.waitUntilFinished();
			Result result = job.getResult();
			String outcome = "\nJob " + filePath + " executed with result: " + result.getResult() + " and " + result.getNrErrors() + " errors\n";
			logger.info(outcome);
			jobObject.setMessage(outcome);
			return job;
		} catch (Exception e) {
			throw e;
		}
	}

	public void initKettleEnv() throws KettleException {
		KettleEnvironment.init();
	}
	/*根据ETLjobID获取ETL任务名称 */
	@Override
	public String getNameById(long id) throws Exception {
		// TODO Auto-generated method stub
		String result="";
		result = jobMapper.getNameById(id);
		return result;
	}
	/*获取所有JOBID*/
	@Override
	public List<Map<String, Object>> getAllEtlJobList() throws Exception {
		// TODO Auto-generated method stub 
		List<Map<String, Object>> result =new ArrayList();
		List<JobInstanceBean> result1 = null;
		Map<String, Object> map=new HashMap<String, Object>();
		result1=jobMapper.getAllEtlJobList();
			for (int i = 0; i < result1.size(); i++) {
				map=new HashMap<String, Object>();
				map.put("JOB_ID",result1.get(i).getJobId());
				result.add(map);
			} 
		return result;
	}

	
	
}

