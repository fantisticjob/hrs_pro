package com.hausontech.hrs.api.etlProcess.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import com.hausontech.hrs.bean.etlProcess.JobInstanceBean;
import com.hausontech.hrs.bean.etlProcess.ScheduleInstanceBean;
import com.hausontech.hrs.bean.etlProcess.ScheduleStatusBean;
import com.hausontech.hrs.bean.etlProcess.ScheduleTypeBean;

public interface IJobProcessDao {
	
	//public List<Map<String, Object>> getEtlJobList(JobInstanceBean jobInsBean) throws SQLException;
	public int countByCondition(JobInstanceBean jobInsBean) throws SQLException;
	public int saveJobRecord(JobInstanceBean jobRecord) throws SQLException;
	public int updateJobRecord(JobInstanceBean record) throws SQLException;
	public int deleteJobRecord(JobInstanceBean record) throws SQLException;
	public JobInstanceBean getJobInfoById (long jobId) throws SQLException;
	public int saveEtlJobInstance(JobInstanceBean jobInsRecord) throws SQLException;
	
	public List<Map<String, Object>> updateEtlJobInsRecord(JobInstanceBean jobInsRecord) throws SQLException;
	public JobInstanceBean getAllocJobInfoById(int jobId) throws SQLException;

	public int saveScheduleRecord(ScheduleInstanceBean scheduleRecord) throws SQLException;
	public List<Map<String, Object>> getEtlScheduleList(ScheduleInstanceBean scheduleBean)throws SQLException;
	public int deleteScheduleRecord(ScheduleInstanceBean scheduleRecord) throws SQLException;
	public int updateScheduleRecord(ScheduleInstanceBean scheduleRecord) throws SQLException;
	public String getNameById(long id)throws SQLException;
	public int countScheduleByCondition(ScheduleInstanceBean scheduleBean)throws SQLException;
	public List<Map<String, Object>> getEtlScheduleTypeList(ScheduleTypeBean typeBean)throws SQLException;
	public int deleteScheduleRecordForJob(JobInstanceBean record) throws SQLException;
	public List<Map<String, Object>> getEtlScheduleStatusList(ScheduleStatusBean statusBean)throws SQLException;
	public ScheduleInstanceBean getScheduleInfoById(long scheduleId) throws SQLException;
	public List<Map<String, Object>> getAllEtlJobList()throws SQLException;
	public List<Map<String, Object>> getAllScheduleList()throws SQLException;


}
