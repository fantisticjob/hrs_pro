package com.hausontech.hrs.daoImpl.etlProcess.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.hausontech.hrs.bean.etlProcess.JobInstanceBean;
import com.hausontech.hrs.bean.etlProcess.ScheduleInstanceBean;
import com.hausontech.hrs.bean.etlProcess.ScheduleStatusBean;
import com.hausontech.hrs.bean.etlProcess.ScheduleTypeBean;

@Repository
public interface JobInstanceMapper {
	/*获取ETL任务界面数据*/
    public List<JobInstanceBean> getEtlJobList(@Param("start")String start,@Param("jobName") String jobName,@Param("status") String status,@Param("count") String count)throws SQLException;;
    /*增加ETL任务记录*/
    public int saveEtlJob(JobInstanceBean record)throws SQLException;
    /*更新ETL任务记录*/
    public int updateByPrimaryKey(JobInstanceBean record)throws SQLException;
    /*删除etl调度任务记录*//*删除etl任务记录*/
    public int deleteJobRecord(@Param("tableName")String tableName,@Param("primaryKey") String primaryKey)throws SQLException;
	/*更新etl任务记录*/
    public int updateJobRecord(JobInstanceBean jobBean)throws SQLException;
	/*获取ETL任务界面总数*/
    public int countByCondition(@Param("jobName") String jobName,@Param("status") String status)throws SQLException;
	/*获取任务调度界面数据总数 */
    public int countByConditionSchedule(@Param("jobName") String jobName,@Param("status") String status)throws SQLException;
	/*保存ETL任务执行实例*/
    public void saveEtlJobInstance(JobInstanceBean dbJob)throws SQLException;
	/*更新ETL任务执行实例*/
	public int updateEtlJobInsRecord(JobInstanceBean dbJob)throws SQLException;
	/*获取任务调度界面数据*/
	public List<ScheduleInstanceBean> getEtlScheduleList(@Param("start")String start,@Param("jobName") String jobName,@Param("status") String status,@Param("count") String count)throws SQLException;
	/*增加任务调度记录*/
	public int saveScheduleRecord(ScheduleInstanceBean scheduleRecord)throws SQLException;
	/*删除任务调度记录*/
	public int deleteScheduleRecord(long scheduleId)throws SQLException;
	/*更新ETL任务调度*/
	public int updateScheduleRecord(ScheduleInstanceBean scheduleRecord)throws SQLException;
	/*获取调度执行类型数据*/
	public List<ScheduleTypeBean> getEtlScheduleTypeList()throws SQLException;
	/*获取调度状态数据*/
	public List<ScheduleStatusBean> getEtlScheduleStatusList()throws SQLException;
	/*根据ETLjobID获取ETL任务记录*/
	public JobInstanceBean getJobInfoById(long jobId)throws SQLException;
	/*根据调度ID获取调度任务记录*/
	public ScheduleInstanceBean getScheduleInfoById(long scheduleId)throws SQLException;
	/*根据ETLjobID获取ETL任务名称 */
	public String getNameById(long id)throws SQLException;
	/*获取所有JOBID*/
	public List<JobInstanceBean> getAllEtlJobList()throws SQLException;
	/*获取状态ID、状态MAP*/
	public String getStatus(@Param("statusId")String statusId,@Param("statusType")String statusType)throws SQLException;
}