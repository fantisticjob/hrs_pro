package com.hausontech.hrs.serviceImpl.etlProcess;

import java.io.File;
import java.sql.SQLException;
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

import com.hausontech.hrs.api.etlProcess.dao.IJobProcessDao;
import com.hausontech.hrs.api.etlProcess.service.IKettleJobService;
import com.hausontech.hrs.bean.etlProcess.JobInstanceBean;
import com.hausontech.hrs.bean.etlProcess.ScheduleInstanceBean;
import com.hausontech.hrs.bean.etlProcess.ScheduleTypeBean;
import com.hausontech.hrs.daoImpl.engine.quartz.QuartzManager;
import com.hausontech.hrs.daoImpl.engine.quartz.ScheduleTypeQuartzAddJob;

public class KettleJobServiceImpl implements IKettleJobService {

	private static final String EXTENDED_PARAM_TOKEN = ",";
	private IJobProcessDao jobProcessDao;
	/** 组件日志 */ 
	private static Logger logger = LoggerFactory.getLogger(KettleJobServiceImpl.class); 	
	public IJobProcessDao getJobProcessDao() {
		return jobProcessDao;
	}

	public void setJobProcessDao(IJobProcessDao jobProcessDao) {
		this.jobProcessDao = jobProcessDao;
	}
	
	@Override
	public List<Map<String, Object>> selectEtlJobList(JobInstanceBean jobInsBean) throws Exception {
		if (jobInsBean == null) {
			throw new Exception("no query bean found");
		}
		List<Map<String, Object>> result = null;
		try {
			result = jobProcessDao.getEtlJobList(jobInsBean);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		} 
		return result;
	}
	
	@Override
	public int getJobCount(JobInstanceBean jobInsBean) throws Exception {
		if (jobInsBean == null) {
			throw new Exception("no query bean found");
		}
		int result = 0;
		try {
			result = jobProcessDao.countByCondition(jobInsBean);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		} 
		return result;
	}
	
	
	@Override
	public int createJobRecord(JobInstanceBean jobRecord) throws Exception {
		if (jobRecord == null) {
			throw new Exception("The job bean is null.");
		}
		int result = 0;
		try {
			result = jobProcessDao.saveJobRecord(jobRecord);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}
	

	@Override
	public int updateJobRecord(JobInstanceBean jobBean) throws Exception {
		if (jobBean == null || jobBean.getJobId() == 0) {
			throw new Exception("The Job Bean is null or job id is empty.");
		}
		int result = 0;
		try {
			//try to get dimension information 
			result = jobProcessDao.updateJobRecord(jobBean);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}
	
	/*@Override
	public int deleteJobRecord(JobInstanceBean jobBean) throws Exception {
		if (jobBean == null || jobBean.getJobId() == 0) {
			throw new Exception("The Job Bean is null or job id is empty.");
		}
		int delCount = 0;
		try {
			//try to delete rule header
			delCount = jobProcessDao.deleteJobRecord(jobBean);	
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return delCount;
	}*/
	@Override
	public int deleteJobRecord(JobInstanceBean jobBean) throws Exception {
		if (jobBean == null || jobBean.getJobId() == 0) {
			throw new Exception("The Job Bean is null or job id is empty.");
		}
		int delCount = 0;
		int delCount1 = 0;
		try {
			//若删除etl的job对应调度跟着全部删除
			delCount1 = jobProcessDao.deleteScheduleRecordForJob(jobBean);
			delCount = jobProcessDao.deleteJobRecord(jobBean);	
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return delCount>delCount1?delCount:delCount1;
	}
	
	/*@Override
	public void processEtlJob(JobInstanceBean jobBean) throws Exception {
		if (jobBean == null || jobBean.getJobId() == 0) {
			throw new Exception("The Job Bean is null or job id is empty.");
		}
		JobInstanceBean dbJob = null;
		try {
			//try to get job from db
			
			
			if(StringUtils.isBlank(jobBean.getJobType())){
				dbJob = jobProcessDao.getJobInfoById(jobBean.getJobId());
			else if  get the attribute jobType have contents   2017-03-29 GXY
			}else if(jobBean.getJobType().equals("allocationJob")){
				dbJob = jobProcessDao.getAllocJobInfoById(jobBean.getJobId());
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
			dbJob.setStatus("Processing");
			dbJob.setMessage("ETL job is running");
			
			jobProcessDao.saveEtlJobInstance(dbJob);
			
			long startTime = System.currentTimeMillis();
			Job job = this.runJobFromFileSystem(dbJob);
			long ecalTime = System.currentTimeMillis() - startTime;
			dbJob.setElapsedTime((int)ecalTime);
		    dbJob.setStatus("Done");
	
			jobProcessDao.updateEtlJobInsRecord(dbJob);
		} catch (SQLException se) {
			throw new Exception(se);
		} 
	}*/
	
	@Override
	public List<Map<String, Object>> processEtlJob(JobInstanceBean jobBean) throws Exception {
		if (jobBean == null || jobBean.getJobId() == 0) {
			throw new Exception("The Job Bean is null or job id is empty.");
		}
		JobInstanceBean dbJob = null;
		List<Map<String, Object>> list = null;
		try {
			//try to get job from db
			if(StringUtils.isBlank(jobBean.getJobType())){
				dbJob = jobProcessDao.getJobInfoById(jobBean.getJobId());
				dbJob.setScheduleId(jobBean.getScheduleId());
			/*else if  get the attribute jobType have contents   2017-03-29 GXY*/
			}else if(jobBean.getJobType().equals("allocationJob")){
				dbJob = jobProcessDao.getAllocJobInfoById(jobBean.getJobId());
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
			dbJob.setStatus("Processing");
			dbJob.setMessage("ETL job is running");
			jobProcessDao.saveEtlJobInstance(dbJob);
			long startTime = System.currentTimeMillis();
			Job job = this.runJobFromFileSystem(dbJob);	
			long ecalTime = System.currentTimeMillis() - startTime;
			dbJob.setElapsedTime((int)ecalTime);
		    dbJob.setStatus("Done");
		    list=jobProcessDao.updateEtlJobInsRecord(dbJob);
		} catch (SQLException se) {
			System.out.println(se);
			throw new Exception(se);
		}
		return list; 
	}
	
	private void processExtendedParameters(JobInstanceBean jobBean) {
		String parameterString = jobBean.getParameList();
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
	
	public Job runJobFromFileSystem(JobInstanceBean jobObject) throws Exception {
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

	@Override
	public List<Map<String, Object>> selectEtlScheduleList(ScheduleInstanceBean scheduleBean)throws Exception {
		if (scheduleBean == null) {
			throw new Exception("no query bean found");
		}
		//String current="";
		List<Map<String, Object>> result = null;
		try {
			result = jobProcessDao.getEtlScheduleList(scheduleBean);
			String[] current = new String[result.size()];// 声明数组
			if (result != null && result.size() > 0) {
				for (int z = 0; z < current.length; z++) {
					current[z] = result.get(z).get("JOB_ID").toString();
				}
				for (int z = 0; z < current.length; z++) {
					result.get(z).put("CONCURRENT", jobProcessDao.getCurrent(Integer.parseInt(current[z])));
				}
			}
			/*for (int i = 0; i < result.size(); i++) {
				current=jobProcessDao.getCurrent(result[i].getJobId());
			}*/
			
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		} 
		return result;
	}

	@Override
	public int createScheduleRecord(ScheduleInstanceBean scheduleRecord) throws Exception {
		if (scheduleRecord == null) {
			throw new Exception("The job bean is null.");
		}
		int result = 0;
		try {
			result = jobProcessDao.saveScheduleRecord(scheduleRecord);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}

	

	@Override
	public int deleteScheduleRecord(ScheduleInstanceBean scheduleBean) throws Exception {
		if (scheduleBean == null || scheduleBean.getScheduleId() == 0) {
			throw new Exception("The Job Bean is null or job id is empty.");
		}
		int delCount = 0;
		try {
			//try to delete rule header
			delCount = jobProcessDao.deleteScheduleRecord(scheduleBean);	
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return delCount;
	}

	@Override
	public int updateScheduleRecord(ScheduleInstanceBean scheduleRecord) throws Exception {
		if (scheduleRecord == null || scheduleRecord.getJobId() == 0) {
			throw new Exception("The Job Bean is null or job id is empty.");
		}
		int result = 0;
		try {
			//try to get dimension information 
			result = jobProcessDao.updateScheduleRecord(scheduleRecord);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public String getNameById(int id) throws Exception {
		// TODO Auto-generated method stub
		String result="";
		result = jobProcessDao.getNameById(id);
		return result;
	}

	@Override
	public int getScheduleCount(ScheduleInstanceBean scheduleBean) throws Exception {
		if (scheduleBean == null) {
			throw new Exception("no query bean found");
		}
		int result = 0;
		try {
			result = jobProcessDao.countScheduleByCondition(scheduleBean);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		} 
		return result;
	}

	@Override
	public List<Map<String, Object>> selectScheduleTypeList(ScheduleTypeBean typeBean) throws Exception {
		if (typeBean == null) {
			throw new Exception("no query bean found");
		}
		List<Map<String, Object>> result = null;
		try {
			result = jobProcessDao.getEtlScheduleTypeList(typeBean);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		} 
		return result;
	}

	@Override
	public List<Map<String, Object>> processEtlSchedule(JobInstanceBean jobBean) throws Exception {
		if (jobBean == null || jobBean.getJobId() == 0) {
			throw new Exception("The Job Bean is null or job id is empty.");
		}
		JobInstanceBean dbJob = null;
		ScheduleInstanceBean scheduleBean=null;
		int scheduleId=jobBean.getScheduleId();
		try {
			//try to get job from db
			if(StringUtils.isBlank(jobBean.getJobType())){
				dbJob = jobProcessDao.getJobInfoById(jobBean.getJobId());
				scheduleBean=jobProcessDao.getScheduleInfoById(scheduleId);
				dbJob.setScheduleId(scheduleId);
			/*else if  get the attribute jobType have contents   2017-03-29 GXY*/
			}else if(jobBean.getJobType().equals("allocationJob")){
				dbJob = jobProcessDao.getAllocJobInfoById(jobBean.getJobId());
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
			String concurrent=dbJob.getConcurrent();
			System.out.println(concurrent+"-----------------");
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
					QuartzManager.addJob(String.valueOf(scheduleId), QuartzJob.class,time,dbJob,jobProcessDao);
				}else if(concurrent.equals("2")){
					QuartzManager.addJob(String.valueOf(scheduleId), QuartzStatefulJob.class,time,dbJob,jobProcessDao);
				}
				//String.valueOf(scheduleId)任务名，QuartzJob.class调度执行任务类，time 就是cron表达式时间设置
			}else{
				//更新执行Job
				if(concurrent.equals("1")){
					QuartzManager.modifyJobTime(String.valueOf(scheduleId),QuartzJob.class, time,dbJob,jobProcessDao);
				}else if(concurrent.equals("2")){
					QuartzManager.modifyJobTime(String.valueOf(scheduleId),QuartzStatefulJob.class, time,dbJob,jobProcessDao);
				}
			}
		} catch (SQLException se) {
			throw new Exception(se);
		}
		return null; 
	}

	@Override
	public List<Map<String, Object>> getAllEtlJobList() throws Exception {
		// TODO Auto-generated method stub
		List<Map<String, Object>> result = null;
		try {
			result = jobProcessDao.getAllEtlJobList();
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		} 
		return result;
	}

	@Override
	public List<Map<String, Object>> getAllScheduleList() throws Exception {
		// TODO Auto-generated method stub
		List<Map<String, Object>> result = null;
		try {
			result = jobProcessDao.getAllScheduleList();
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		} 
		return result;
	}
}
