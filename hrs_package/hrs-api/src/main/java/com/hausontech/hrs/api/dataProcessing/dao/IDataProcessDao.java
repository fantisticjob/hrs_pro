package com.hausontech.hrs.api.dataProcessing.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.hausontech.hrs.bean.dataProcess.AllocRequestInstanceBean;
import com.hausontech.hrs.bean.dataProcess.AllocRequestInstanceHistoryRecord;
import com.hausontech.hrs.bean.dataProcess.AllocRequestInstanceRecord;
import com.hausontech.hrs.bean.dataProcess.AllocScheduleInstanceBean;
import com.hausontech.hrs.bean.dataProcess.LedgerBean;
import com.hausontech.hrs.bean.dataProcess.PeriodBean;
import com.hausontech.hrs.bean.dataProcess.RequestInstanceBean;
import com.hausontech.hrs.bean.transactionProcess.TransactionProcessTypeBean;

public interface IDataProcessDao {
	
	public List<PeriodBean> getPeriodList() throws SQLException;
	
	List<Map<String, Object>> getRequestInstanceList(RequestInstanceBean requestBean) throws SQLException;
	
	public long getRequestInstanceId() throws SQLException;
	
	public List<LedgerBean> getLedgerList() throws SQLException;
	

	
	public long saveBatchJobRecord(RequestInstanceBean jobRecord) throws SQLException;
	
	public int updateBatchJobRecord(RequestInstanceBean record) throws SQLException;
	
	public int deleteBatchJobRecord(RequestInstanceBean record) throws SQLException;
	
	public List<Map<String, Object>> getLedgerListMapForDisplay() throws SQLException;
	
	public List<Map<String, Object>> getPeriodListMapForDisplay() throws SQLException;
	
	public List<Map<String, Object>> getFinElementMapForDisplay() throws SQLException;
	
	public RequestInstanceBean getRequestInstanceByPrimaryKey(RequestInstanceBean requestBean) throws SQLException;

	public List<TransactionProcessTypeBean> getTransationProcessTypeList()throws SQLException;

	public int getAllocBatchInstanceNumber(AllocRequestInstanceBean jobInsBean) throws SQLException;

	public List<Map<String, Object>> getAllocRequestInstanceList(AllocRequestInstanceBean requestBean) throws SQLException;

	int getBatchInstanceNumber(RequestInstanceBean requestBean) throws SQLException;

	public long saveAllocBatchJobRecord(AllocRequestInstanceBean jobRecord) throws SQLException;

	public int updateAllocBatchJobRecord(AllocRequestInstanceBean jobBean) throws SQLException;

	public int deleteAllocBatchJobRecord(AllocRequestInstanceBean jobBean) throws SQLException;

	public List<Map<String, Object>> getAvailablePeriodListMapForDisplay() throws SQLException;

	public List<PeriodBean> getAvailablePeriodList() throws SQLException;

	public List<Map<String, Object>> getGroupRuleList() throws SQLException;

	public List<Map<String, Object>> getruleList(String type) throws SQLException;

	public AllocRequestInstanceBean getAllocRequestInstanceByPrimaryKey(AllocRequestInstanceBean requestBean) throws SQLException;

	public List<Map<String, Object>> getAllocRequestInstance(AllocRequestInstanceRecord requestBean) throws SQLException;

	public int getAllocRequestInstanceCount(AllocRequestInstanceRecord jobInsBean) throws SQLException;

	public long saveAllocRequestInstance(AllocRequestInstanceRecord jobRecord) throws SQLException;

	public int updateAllocRequestInstance(AllocRequestInstanceRecord jobBean) throws SQLException;

	public int deleteAllocRequestInstance(AllocRequestInstanceRecord jobBean) throws SQLException;

	public AllocRequestInstanceRecord getAllocRequestInstanceByPrimaryKey(AllocRequestInstanceRecord requestBean) throws SQLException;

	public long saveAllocRequestInstanceHistoryRecord(AllocRequestInstanceHistoryRecord requestHistoryBean) throws SQLException;

	public int updateAllocRequestInstanceHistoryRecord(AllocRequestInstanceHistoryRecord requestHistoryBean)throws SQLException;

	public int getAllocRequestInstanceHistoryRecordCount(AllocRequestInstanceHistoryRecord queryBean) throws SQLException;

	public List<Map<String, Object>> findAllocInstanceHistory(AllocRequestInstanceHistoryRecord requestBean) throws SQLException;

	public List<Long> getruleListForLoop(AllocRequestInstanceRecord requestBean) throws SQLException;

	public AllocRequestInstanceHistoryRecord getAllocRequestInstanceHistoryByPrimaryKey(
			AllocRequestInstanceHistoryRecord requestBean)throws SQLException;

	public int saveAllocScheduleInstance(AllocScheduleInstanceBean scheduleBean) throws SQLException;

	public List<Map<String, Object>> getAllocScheduleInstance(AllocScheduleInstanceBean scheduleBean)throws SQLException;

	public int getAllocScheduleInstanceCount(AllocScheduleInstanceBean scheduleBean)throws SQLException;

	public int deleteScheduleRecord(AllocScheduleInstanceBean scheduleBean)throws SQLException;

	public int updateAllocSchedule(AllocScheduleInstanceBean reqBean)throws SQLException;

	public AllocScheduleInstanceBean getScheduleById(String scheduleId)throws SQLException;

	public int deleteScheduleRecordForJob(AllocRequestInstanceRecord jobBean)throws SQLException;






	

}
