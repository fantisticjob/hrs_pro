package com.hausontech.hrs.api.etlProcess.service;

import java.util.List;
import java.util.Map;
import com.hausontech.hrs.bean.etlProcess.JobInstanceBean;
import com.hausontech.hrs.bean.etlProcess.ScheduleInstanceBean;
import com.hausontech.hrs.bean.etlProcess.ScheduleStatusBean;
import com.hausontech.hrs.bean.etlProcess.ScheduleTypeBean;

public interface IKettleJobService {
	
	public List<Map<String, Object>> selectEtlJobList(JobInstanceBean jobInsBean) throws Exception;
	public int getJobCount(JobInstanceBean jobInsBean) throws Exception;
	public int createJobRecord(JobInstanceBean jobRecord) throws Exception;
	public int updateJobRecord(JobInstanceBean jobBean) throws Exception;
	public int deleteJobRecord(JobInstanceBean jobBean) throws Exception;
	public int createScheduleRecord(ScheduleInstanceBean scheduleRecord) throws Exception;
	public List<Map<String, Object>> processEtlJob(JobInstanceBean jobBean) throws Exception;
	public List<Map<String, Object>> selectEtlScheduleList(ScheduleInstanceBean scheduleBean)throws Exception;
	public int deleteScheduleRecord(ScheduleInstanceBean scheduleBean) throws Exception;
	public int updateScheduleRecord(ScheduleInstanceBean scheduleRecord) throws Exception;
	public String getNameById(long id)throws Exception;
	public int getScheduleCount(ScheduleInstanceBean scheduleBean)throws Exception;
	public List<Map<String, Object>> selectScheduleTypeList(ScheduleTypeBean typeBean)throws Exception;
	public List<Map<String, Object>> selectScheduleStatusList(ScheduleStatusBean statusBean)throws Exception;
	public List<Map<String, Object>> processEtlSchedule(JobInstanceBean reqBean)throws Exception;
	public List<Map<String, Object>> getAllEtlJobList()throws Exception;
}
