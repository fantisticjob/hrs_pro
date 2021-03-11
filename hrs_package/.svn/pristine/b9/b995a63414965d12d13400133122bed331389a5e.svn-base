package com.hausontech.hrs.api.dataProcessing.service;

import java.util.List;
import java.util.Map;

import com.hausontech.hrs.bean.dataProcess.AllocRequestInstanceBean;
import com.hausontech.hrs.bean.dataProcess.AllocRequestInstanceHistoryRecord;
import com.hausontech.hrs.bean.dataProcess.AllocRequestInstanceRecord;
import com.hausontech.hrs.bean.dataProcess.LedgerBean;
import com.hausontech.hrs.bean.dataProcess.PeriodBean;
import com.hausontech.hrs.bean.dataProcess.RequestInstanceBean;
import com.hausontech.hrs.bean.transactionProcess.TransactionProcessTypeBean;
import com.hausontech.hrs.exceptions.DuplicateJobRunningException;

public interface IDataProcessService {
	
	public List<Map<String, Object>> getJobRunningInstanceList(RequestInstanceBean queryBean) throws Exception;
	
	public List<PeriodBean> getPeriodList() throws Exception;
	
	public List<Map<String, Object>> getRequestInstanceList(RequestInstanceBean requestBean) throws Exception;
	
	public void calculate(RequestInstanceBean requestBean) throws DuplicateJobRunningException, Exception;
	
	public long getRequestInstanceId() throws Exception;
	
	List<LedgerBean> getLedgerList() throws Exception;
	
	public int getBatchJobCount(RequestInstanceBean jobInsBean) throws Exception;
	
	public long createBatchJobRecord(RequestInstanceBean jobRecord) throws Exception;
	
	public int updateBatchJobRecord(RequestInstanceBean jobBean) throws Exception;
	
	public int deleteBatchJobRecord(RequestInstanceBean jobBean) throws Exception;
	
	public List<Map<String, Object>> getLedgerListMapForDisplay() throws Exception;
	
	public List<Map<String, Object>> getPeriodListMapForDisplay() throws Exception;
	
	public List<Map<String, Object>> getFinElementMapForDisplay() throws Exception;
	
	public RequestInstanceBean getRequestInstanceByPrimaryKey(RequestInstanceBean requestBean) throws Exception;

	public List<TransactionProcessTypeBean> getTransationProcessTypeList() throws Exception;

	public List<Map<String, Object>> getAllocRequestInstanceList(AllocRequestInstanceBean requestQueryBean) throws Exception;

	public int getAllocBatchJobCount(AllocRequestInstanceBean requestQueryBean)throws Exception;

	public long createAllocBatchJobRecord(AllocRequestInstanceBean reqBean) throws Exception;

	public int updateAllocBatchJobRecord(AllocRequestInstanceBean reqBean) throws Exception;

	public int deleteAllocBatchJobRecord(AllocRequestInstanceBean reqBean) throws Exception;

	public List<Map<String, Object>> getAvailablePeriodListMapForDisplay() throws Exception;

	public List<PeriodBean> getAvailablePeriodList() throws Exception;

	public List<Map<String, Object>> getGroupRuleList() throws Exception;

	public List<Map<String, Object>> getruleList(String type) throws Exception;

	public AllocRequestInstanceBean getAllocRequestInstanceByPrimaryKey(AllocRequestInstanceBean requestBean) throws Exception;

	public void calculateAllocJob(AllocRequestInstanceBean requestBean) throws Exception;

	public List<Map<String, Object>> getAllocRequestInstance(AllocRequestInstanceRecord requestQueryBean) throws Exception;

	public int getAllocRequestInstanceCount(AllocRequestInstanceRecord requestQueryBean) throws Exception;

	public long saveAllocRequestInstance(AllocRequestInstanceRecord reqBean) throws Exception;

	public int updateAllocRequestInstance(AllocRequestInstanceRecord reqBean) throws Exception;

	public int deleteAllocRequestInstance(AllocRequestInstanceRecord reqBean) throws Exception;

	public AllocRequestInstanceRecord getAllocRequestInstanceByPrimaryKey(AllocRequestInstanceRecord requestBean) throws Exception;

	public void calculateAllocJobForInstacneHistory(AllocRequestInstanceHistoryRecord requestHistoryBean) throws Exception;

	public long saveAllocRequestHistoryInstance(AllocRequestInstanceHistoryRecord requestHistoryBean) throws Exception;

	public List<Map<String, Object>> findAllocInstanceHistory(AllocRequestInstanceHistoryRecord requestBean) throws Exception;

	public int getCountAllocInstanceHistory(AllocRequestInstanceHistoryRecord requestBean) throws Exception;

	public AllocRequestInstanceHistoryRecord getAllocRequestInstanceHistoryByPrimaryKey(
			AllocRequestInstanceHistoryRecord requestHistoryBean) throws Exception;

	public void calculateRollbackAllocJobForInstacneHistory(AllocRequestInstanceHistoryRecord requestHistoryBean) throws Exception;

	public List<Long> getruleListForLoop(AllocRequestInstanceRecord requestBean) throws Exception;





}
