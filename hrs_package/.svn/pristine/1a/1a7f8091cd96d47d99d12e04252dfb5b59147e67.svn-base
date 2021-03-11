package com.hausontech.hrs.serviceImpl.dataProcessing;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hausontech.hrs.api.constants.JobStatus;
import com.hausontech.hrs.api.dataProcessing.dao.IDataProcessDao;
import com.hausontech.hrs.api.dataProcessing.service.IDataProcessService;
import com.hausontech.hrs.api.engine.ICalculationDao;
import com.hausontech.hrs.bean.dataProcess.AllocRequestInstanceBean;
import com.hausontech.hrs.bean.dataProcess.AllocRequestInstanceHistoryRecord;
import com.hausontech.hrs.bean.dataProcess.AllocRequestInstanceRecord;
import com.hausontech.hrs.bean.dataProcess.LedgerBean;
import com.hausontech.hrs.bean.dataProcess.PeriodBean;
import com.hausontech.hrs.bean.dataProcess.RequestInstanceBean;
import com.hausontech.hrs.bean.engine.CalculationProcessorBean;
import com.hausontech.hrs.bean.transactionProcess.TransactionProcessTypeBean;
import com.hausontech.hrs.daoImpl.engine.core.HrsCalProcessorBatchProxy;
import com.hausontech.hrs.daoImpl.engine.core.HrsThreadManagerImpl;
import com.hausontech.hrs.exceptions.DuplicateJobRunningException;

public class DataProcessServiceImpl implements IDataProcessService {

	private IDataProcessDao dataProcessDao;

	public IDataProcessDao getDataProcessDao() {
		return dataProcessDao;
	}

	public void setDataProcessDao(IDataProcessDao dataProcessDao) {
		this.dataProcessDao = dataProcessDao;
	}

	private ICalculationDao calculationDao;

	public ICalculationDao getCalculationDao() {
		return calculationDao;
	}

	public void setCalculationDao(ICalculationDao calculationDao) {
		this.calculationDao = calculationDao;
	}

	@Override
	public List<Map<String, Object>> getJobRunningInstanceList(RequestInstanceBean queryBean) throws Exception {
		if (queryBean == null) {
			throw new Exception("no query bean found");
		}
		List<Map<String, Object>> result = null;
		try {
			result = dataProcessDao.getRequestInstanceList(queryBean);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		// TODO Auto-generated method stub
		return result;
	}

	@Override
	public List<LedgerBean> getLedgerList() throws Exception {
		List<LedgerBean> result = null;
		try {
			result = dataProcessDao.getLedgerList();
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public List<PeriodBean> getPeriodList() throws Exception {
		List<PeriodBean> result = null;
		try {
			result = dataProcessDao.getPeriodList();
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> getLedgerListMapForDisplay() throws Exception {
		List<Map<String, Object>> result = null;
		try {
			result = dataProcessDao.getLedgerListMapForDisplay();
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> getPeriodListMapForDisplay() throws Exception {
		List<Map<String, Object>> result = null;
		try {
			result = dataProcessDao.getPeriodListMapForDisplay();
		} catch (SQLException se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> getFinElementMapForDisplay() throws Exception {
		List<Map<String, Object>> result = null;
		try {
			result = dataProcessDao.getFinElementMapForDisplay();
		} catch (SQLException se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> getRequestInstanceList(RequestInstanceBean requestBean) throws Exception {
		if (requestBean == null) {
			throw new Exception("The Reuqest bean is null.");
		}
		List<Map<String, Object>> result = null;
		try {
			result = dataProcessDao.getRequestInstanceList(requestBean);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int getBatchJobCount(RequestInstanceBean jobInsBean) throws Exception {
		if (jobInsBean == null) {
			throw new Exception("no job request bean found");
		}
		int result = 0;
		try {
			result = dataProcessDao.getBatchInstanceNumber(jobInsBean);
		} catch (SQLException se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public long createBatchJobRecord(RequestInstanceBean jobRecord) throws Exception {
		if (jobRecord == null) {
			throw new Exception("The job bean is null.");
		}
		long result = 0;
		try {
			result = dataProcessDao.saveBatchJobRecord(jobRecord);
		} catch (SQLException se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int updateBatchJobRecord(RequestInstanceBean jobBean) throws Exception {
		if (jobBean == null || jobBean.getInstanceId() == 0) {
			throw new Exception("The Job Bean is null or job instance id is empty.");
		}
		int result = 0;
		try {
			// try to get dimension information
			result = dataProcessDao.updateBatchJobRecord(jobBean);
		} catch (SQLException se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int deleteBatchJobRecord(RequestInstanceBean jobBean) throws Exception {
		if (jobBean == null || jobBean.getInstanceId() == 0) {
			throw new Exception("The Job Bean is null or job instance id is empty.");
		}
		int delCount = 0;
		try {
			// try to delete rule header
			delCount = dataProcessDao.deleteBatchJobRecord(jobBean);
		} catch (SQLException se) {
			throw new Exception(se);
		}
		return delCount;
	}

	public void calculate(RequestInstanceBean requestBean) throws DuplicateJobRunningException, Exception {
		// get content and calculation items
		Map<String, List<CalculationProcessorBean>> contentList = null;
		List<CalculationProcessorBean> calculationResult = null;
		boolean isError = false;
		String errorMsg = "";
		long startTime = System.currentTimeMillis();
		try {
			RequestInstanceBean queryBean = new RequestInstanceBean();
			queryBean.setStatus(JobStatus.PROCESSING);
			int runningCount = this.getBatchJobCount(queryBean);
			if (0 < runningCount) {
				throw new DuplicateJobRunningException();
			}
			// set job instance status
			requestBean.setStatus(JobStatus.PROCESSING);
			requestBean.setStartDate(new Date());
			requestBean.setMessage("");
			dataProcessDao.updateBatchJobRecord(requestBean);

			// Execute content
			// CalculationDao calDao = new CalculationDao();
			this.calculationDao.generateFinIndex(requestBean);
			// get item constant list
			contentList = calculationDao.getItemContents(requestBean);
			if (0 < contentList.size()) {
				// calculation in the group
				calculationResult = this.calculateInGroup(contentList);
			}
		} catch (DuplicateJobRunningException de) {
			isError = true;
			errorMsg = "已经有任务处于运行状态，不允许多个任务同时执行";
			throw de;
		} catch (Exception e) {
			isError = true;
			errorMsg = e.toString();
			System.out.println("Error happens when calculation: " + e);
		}

		// if there's result , then insert result into db
		if (calculationResult != null && !calculationResult.isEmpty()) {
			try {
				calculationDao.insertCalculationItems(calculationResult, requestBean.getRequestUser(),
						requestBean.getInstanceId());
			} catch (SQLException e) {
				isError = true;
				errorMsg = e.toString();
			}
		}
		requestBean.setStatus(JobStatus.SUCCESS);
		if (isError) {
			requestBean.setStatus(JobStatus.FAILED);
			requestBean.setMessage(errorMsg);
		}
		requestBean.setEndDate(new Date());
		requestBean.setElapsedTime(System.currentTimeMillis() - startTime);
		dataProcessDao.updateBatchJobRecord(requestBean);
	}

	public long getRequestInstanceId() throws Exception {
		return dataProcessDao.getRequestInstanceId();
	}

	@Override
	public RequestInstanceBean getRequestInstanceByPrimaryKey(RequestInstanceBean requestBean) throws Exception {
		if (requestBean == null || requestBean.getInstanceId() == 0) {
			throw new Exception("The Reuqest bean is null.");
		}
		RequestInstanceBean result = null;
		try {
			result = dataProcessDao.getRequestInstanceByPrimaryKey(requestBean);
		} catch (SQLException se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public List<TransactionProcessTypeBean> getTransationProcessTypeList() throws Exception {
		List<TransactionProcessTypeBean> result = null;
		try {
			result = dataProcessDao.getTransationProcessTypeList();
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}

	private List<CalculationProcessorBean> calculateInGroup(Map<String, List<CalculationProcessorBean>> contentList)
			throws InterruptedException {
		// initialize thread manager
		HrsThreadManagerImpl hrsThreadMgr = new HrsThreadManagerImpl(contentList.size());
		List<CalculationProcessorBean> resultBeanList = new ArrayList<CalculationProcessorBean>();
		// start to loop the map
		for (List<CalculationProcessorBean> value : contentList.values()) {
			// initialize group result bean which will be passed to Batch
			// processor to hold each group result
			CalculationProcessorBean groupCalResult = new CalculationProcessorBean();
			// initialize batch processor for processing group index records
			HrsCalProcessorBatchProxy processor = new HrsCalProcessorBatchProxy(value, groupCalResult);
			// add each group result bean in the result beans list
			resultBeanList.add(groupCalResult);
			// invoke thread manager to run the thread service
			hrsThreadMgr.run(processor);
		}
		// wait for all thread are done
		hrsThreadMgr.waitForAll();
		return resultBeanList;
	}

	@Override
	public List<Map<String, Object>> getAllocRequestInstanceList(AllocRequestInstanceBean requestBean)
			throws Exception {
		if (requestBean == null) {
			throw new Exception("The Reuqest bean is null.");
		}
		List<Map<String, Object>> result = null;
		try {
			result = dataProcessDao.getAllocRequestInstanceList(requestBean);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int getAllocBatchJobCount(AllocRequestInstanceBean jobInsBean) throws Exception {
		if (jobInsBean == null) {
			throw new Exception("no job request bean found");
		}
		int result = 0;
		try {
			result = dataProcessDao.getAllocBatchInstanceNumber(jobInsBean);
		} catch (SQLException se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public long createAllocBatchJobRecord(AllocRequestInstanceBean jobRecord) throws Exception {
		if (jobRecord == null) {
			throw new Exception("The job bean is null.");
		}
		long result = 0;
		try {
			result = dataProcessDao.saveAllocBatchJobRecord(jobRecord);
		} catch (SQLException se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int updateAllocBatchJobRecord(AllocRequestInstanceBean jobBean) throws Exception {
		if (jobBean == null || jobBean.getInstanceId() == 0) {
			throw new Exception("The Job Bean is null or job instance id is empty.");
		}
		int result = 0;
		try {
			// try to get dimension information
			result = dataProcessDao.updateAllocBatchJobRecord(jobBean);
		} catch (SQLException se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int deleteAllocBatchJobRecord(AllocRequestInstanceBean jobBean) throws Exception {
		if (jobBean == null || jobBean.getInstanceId() == 0) {
			throw new Exception("The Job Bean is null or job instance id is empty.");
		}
		int delCount = 0;
		try {
			// try to delete rule header
			delCount = dataProcessDao.deleteAllocBatchJobRecord(jobBean);
		} catch (SQLException se) {
			throw new Exception(se);
		}
		return delCount;
	}

	@Override
	public List<Map<String, Object>> getAvailablePeriodListMapForDisplay() throws Exception {
		List<Map<String, Object>> result = null;
		try {
			result = dataProcessDao.getAvailablePeriodListMapForDisplay();
		} catch (SQLException se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public List<PeriodBean> getAvailablePeriodList() throws Exception {
		List<PeriodBean> result = null;
		try {
			result = dataProcessDao.getAvailablePeriodList();
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> getGroupRuleList() throws Exception {
		List<Map<String, Object>> result = null;
		try {
			result = dataProcessDao.getGroupRuleList();
		} catch (Exception e) {
			throw new Exception(e);
		}
		return result;

	}

	@Override
	public List<Map<String, Object>> getruleList(String type) throws Exception {
		List<Map<String, Object>> result = null;
		try {
			result = dataProcessDao.getruleList(type);
		} catch (Exception se) {
			throw new Exception(se);
		}
		return result;

	}

	@Override
	public AllocRequestInstanceBean getAllocRequestInstanceByPrimaryKey(AllocRequestInstanceBean requestBean)
			throws Exception {
		if (requestBean == null || requestBean.getInstanceId() == 0) {
			throw new Exception("The Reuqest bean is null.");
		}
		AllocRequestInstanceBean result = null;
		try {
			result = dataProcessDao.getAllocRequestInstanceByPrimaryKey(requestBean);
		} catch (SQLException se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public void calculateAllocJob(AllocRequestInstanceBean requestBean) throws Exception {
		// get content and calculation items
		Map<String, List<CalculationProcessorBean>> contentList = null;
		List<CalculationProcessorBean> calculationResult = null;
		boolean isError = false;
		String errorMsg = "";
		long startTime = System.currentTimeMillis();
		try {
			AllocRequestInstanceBean queryBean = new AllocRequestInstanceBean();
			queryBean.setStatus(JobStatus.PROCESSING);
			int runningCount = this.getAllocBatchJobCount(queryBean);
			if (0 < runningCount) {
				throw new DuplicateJobRunningException();
			}
			// set job instance status
			requestBean.setStatus(JobStatus.PROCESSING);
			requestBean.setStartDate(new Date());
			requestBean.setMessage("");
			dataProcessDao.updateAllocBatchJobRecord(requestBean);

			// Execute content
			// CalculationDao calDao = new CalculationDao();
			// this.calculationDao.generateFinIndexAlloc(requestBean);
			// get item constant list
			int returnvalue = calculationDao.getItemContentsAlloc(requestBean);
			/*
			 * if (0 < contentList.size()) { //calculation in the group
			 * calculationResult = this.calculateInGroup(contentList); }
			 */
			if (returnvalue == 2) {
				isError = true;
			}
		} catch (DuplicateJobRunningException de) {
			isError = true;
			errorMsg = "已经有任务处于运行状态，不允许多个任务同时执行";
			requestBean.setStatus("ERROR");
			dataProcessDao.updateAllocBatchJobRecord(requestBean);
			throw de;
		} catch (Exception e) {
			isError = true;
			errorMsg = e.toString();
			requestBean.setStatus("ERROR");
			dataProcessDao.updateAllocBatchJobRecord(requestBean);
			System.out.println("Error happens when calculation: " + e);
		}

		// if there's result , then insert result into db
		/*
		 * if (calculationResult != null && !calculationResult.isEmpty()) { try
		 * { calculationDao.insertCalculationItems(calculationResult,
		 * requestBean.getRequestUser(), requestBean.getInstanceId()); } catch
		 * (SQLException e) { isError = true; errorMsg = e.toString(); } }
		 */

		if (isError) {
			requestBean.setStatus(JobStatus.FAILED);
			requestBean.setMessage(errorMsg);
		} else {
			requestBean.setStatus(JobStatus.SUCCESS);
		}
		requestBean.setEndDate(new Date());
		requestBean.setElapsedTime(System.currentTimeMillis() - startTime);
		dataProcessDao.updateAllocBatchJobRecord(requestBean);

	}

	@Override
	public List<Map<String, Object>> getAllocRequestInstance(AllocRequestInstanceRecord requestBean) throws Exception {
		if (requestBean == null) {
			throw new Exception("The Reuqest bean is null.");
		}
		List<Map<String, Object>> result = null;
		try {
			result = dataProcessDao.getAllocRequestInstance(requestBean);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int getAllocRequestInstanceCount(AllocRequestInstanceRecord jobInsBean) throws Exception {
		if (jobInsBean == null) {
			throw new Exception("no job request bean found");
		}
		int result = 0;
		try {
			result = dataProcessDao.getAllocRequestInstanceCount(jobInsBean);
		} catch (SQLException se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public long saveAllocRequestInstance(AllocRequestInstanceRecord jobRecord) throws Exception {
		if (jobRecord == null) {
			throw new Exception("The job bean is null.");
		}
		long result = 0;
		try {
			result = dataProcessDao.saveAllocRequestInstance(jobRecord);
		} catch (SQLException se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int updateAllocRequestInstance(AllocRequestInstanceRecord jobBean) throws Exception {
		if (jobBean == null || jobBean.getInstanceId() == 0) {
			throw new Exception("The Job Bean is null or job instance id is empty.");
		}
		int result = 0;
		try {
			// try to get dimension information
			result = dataProcessDao.updateAllocRequestInstance(jobBean);
		} catch (SQLException se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int deleteAllocRequestInstance(AllocRequestInstanceRecord jobBean) throws Exception {
		if (jobBean == null || jobBean.getInstanceId() == 0) {
			throw new Exception("The Job Bean is null or job instance id is empty.");
		}
		int delCount = 0;
		try {
			// try to delete rule header
			delCount = dataProcessDao.deleteAllocRequestInstance(jobBean);
		} catch (SQLException se) {
			throw new Exception(se);
		}
		return delCount;
	}

	@Override
	public AllocRequestInstanceRecord getAllocRequestInstanceByPrimaryKey(AllocRequestInstanceRecord requestBean)
			throws Exception {
		if (requestBean == null || requestBean.getInstanceId() == 0) {
			throw new Exception("The Reuqest bean is null.");
		}
		AllocRequestInstanceRecord result = null;
		try {
			result = dataProcessDao.getAllocRequestInstanceByPrimaryKey(requestBean);
		} catch (SQLException se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public void calculateAllocJobForInstacneHistory(AllocRequestInstanceHistoryRecord requestHistoryBean)
			throws Exception {
		// get content and calculation items
		boolean isError = false;
		String errorMsg = "";
		String returnvalue ="";
		long startTime = System.currentTimeMillis();
		try {
			AllocRequestInstanceHistoryRecord queryBean = new AllocRequestInstanceHistoryRecord();
			queryBean.setStatus(JobStatus.PROCESSING);
			int processingCount = this.getAllocRequestInstanceHistoryRecordCount(queryBean);
			queryBean.setStatus(JobStatus.ROLLBACKING);
			int rollbackingCount = this.getAllocRequestInstanceHistoryRecordCount(queryBean);
			int runningCount = processingCount + rollbackingCount;
			if (0 < runningCount) {
				throw new DuplicateJobRunningException();
			} else {
				// set job instance status
				requestHistoryBean.setStatus(JobStatus.PROCESSING);
				requestHistoryBean.setStartTime(new Date());
				requestHistoryBean.setMessage("");
				dataProcessDao.updateAllocRequestInstanceHistoryRecord(requestHistoryBean);

				// Execute content
				// CalculationDao calDao = new CalculationDao();
				// this.calculationDao.generateFinIndexAlloc(requestBean);
				// get item constant list
				 returnvalue = calculationDao.getItemContentsAlloc(requestHistoryBean);
				/*
				 * if (0 < contentList.size()) { //calculation in the group
				 * calculationResult = this.calculateInGroup(contentList); }
				 */
				if (!"S101".equals(returnvalue)) {
					isError = true;
				}
			}
		} catch (DuplicateJobRunningException de) {

			isError = true;
			errorMsg = "已经有任务处于运行状态，不允许多个任务同时执行";
			requestHistoryBean.setStatus("ERROR");
			dataProcessDao.updateAllocRequestInstanceHistoryRecord(requestHistoryBean);
			throw de;
		} catch (Exception e) {
			isError = true;
			errorMsg = e.toString();
			requestHistoryBean.setStatus("ERROR");
			dataProcessDao.updateAllocRequestInstanceHistoryRecord(requestHistoryBean);
			System.out.println("Error happens when calculation: " + e);
		}

		// if there's result , then insert result into db
		/*
		 * if (calculationResult != null && !calculationResult.isEmpty()) { try
		 * { calculationDao.insertCalculationItems(calculationResult,
		 * requestBean.getRequestUser(), requestBean.getInstanceId()); } catch
		 * (SQLException e) { isError = true; errorMsg = e.toString(); } }
		 */

		if (isError) {
			requestHistoryBean.setStatus(JobStatus.FAILED);
			requestHistoryBean.setMessage(errorMsg);
		} else {
			requestHistoryBean.setStatus(JobStatus.SUCCESS);
		}
		requestHistoryBean.setStatus(returnvalue);
		requestHistoryBean.setEndTime(new Date());
		requestHistoryBean.setElapsedTime(System.currentTimeMillis() - startTime);
		dataProcessDao.updateAllocRequestInstanceHistoryRecord(requestHistoryBean);

	}

	public int getAllocRequestInstanceHistoryRecordCount(AllocRequestInstanceHistoryRecord queryBean) throws Exception {
		if (queryBean == null) {
			throw new Exception("no job request bean found");
		}
		int result = 0;
		try {
			result = dataProcessDao.getAllocRequestInstanceHistoryRecordCount(queryBean);
		} catch (SQLException se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public long saveAllocRequestHistoryInstance(AllocRequestInstanceHistoryRecord requestHistoryBean) throws Exception {
		if (requestHistoryBean == null) {
			throw new Exception("The job bean is null.");
		}
		long result = 0;
		try {
			result = dataProcessDao.saveAllocRequestInstanceHistoryRecord(requestHistoryBean);
		} catch (SQLException se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> findAllocInstanceHistory(AllocRequestInstanceHistoryRecord requestBean)
			throws Exception {
		return this.dataProcessDao.findAllocInstanceHistory(requestBean);
	}

	@Override
	public int getCountAllocInstanceHistory(AllocRequestInstanceHistoryRecord requestBean) throws Exception {
		int result = 0;
		try {
			result = this.dataProcessDao.getAllocRequestInstanceHistoryRecordCount(requestBean);
		} catch (Exception se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public AllocRequestInstanceHistoryRecord getAllocRequestInstanceHistoryByPrimaryKey(
			AllocRequestInstanceHistoryRecord requestBean) throws Exception {
		if (requestBean == null || requestBean.getHistoryId() == 0) {
			throw new Exception("The Reuqest bean is null.");
		}
		AllocRequestInstanceHistoryRecord result = null;
		try {
			result = dataProcessDao.getAllocRequestInstanceHistoryByPrimaryKey(requestBean);
		} catch (SQLException se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public void calculateRollbackAllocJobForInstacneHistory(AllocRequestInstanceHistoryRecord requestHistoryBean)
			throws Exception {
		// get content and calculation items
		boolean isError = false;
		String errorMsg = "";
		long startTime = System.currentTimeMillis();
		String returnvalue="";
		try {
			AllocRequestInstanceHistoryRecord queryBean = new AllocRequestInstanceHistoryRecord();
			queryBean.setStatus(JobStatus.PROCESSING);
			int processingCount = this.getAllocRequestInstanceHistoryRecordCount(queryBean);
			int runningCount = processingCount;
			if (0 < runningCount) {
				throw new DuplicateJobRunningException();
			}
			// set job instance status
			requestHistoryBean.setStatus(JobStatus.ROLLBACKING);
			requestHistoryBean.setStartTime(new Date());
			requestHistoryBean.setMessage("");
			dataProcessDao.updateAllocRequestInstanceHistoryRecord(requestHistoryBean);

			// Execute content
			// CalculationDao calDao = new CalculationDao();
			// this.calculationDao.generateFinIndexAlloc(requestBean);
			// get item constant list
			 returnvalue = calculationDao.getRollbackItemContentsAlloc(requestHistoryBean);
			/*
			 * if (0 < contentList.size()) { //calculation in the group
			 * calculationResult = this.calculateInGroup(contentList); }
			 */
			if ( !"R101".equals(returnvalue)) {
				isError = true;
			}
		} catch (DuplicateJobRunningException de) {
			isError = true;
			errorMsg = "已经有任务处于运行状态，不允许多个任务同时执行";
			requestHistoryBean.setStatus("R102");
			dataProcessDao.updateAllocRequestInstanceHistoryRecord(requestHistoryBean);
			throw de;
		} catch (Exception e) {
			isError = true;
			errorMsg = e.toString();
			System.out.println("Error happens when calculation: " + e);
			requestHistoryBean.setStatus("R102");
			dataProcessDao.updateAllocRequestInstanceHistoryRecord(requestHistoryBean);
		}

		// if there's result , then insert result into db
		/*
		 * if (calculationResult != null && !calculationResult.isEmpty()) { try
		 * { calculationDao.insertCalculationItems(calculationResult,
		 * requestBean.getRequestUser(), requestBean.getInstanceId()); } catch
		 * (SQLException e) { isError = true; errorMsg = e.toString(); } }
		 */

		if (isError) {
			requestHistoryBean.setStatus(JobStatus.ROLLBACKFAILED);
			requestHistoryBean.setMessage(errorMsg);
		} else {
			requestHistoryBean.setStatus(JobStatus.ROLLBACKSUCCESS);
		}
		//update for use result to give status
		requestHistoryBean.setStatus(returnvalue);
		requestHistoryBean.setEndTime(new Date());
		requestHistoryBean.setElapsedTime(System.currentTimeMillis() - startTime);
		requestHistoryBean.setRollbackTime(new Date());
		dataProcessDao.updateAllocRequestInstanceHistoryRecord(requestHistoryBean);

	}

	@Override
	public List<Long> getruleListForLoop(AllocRequestInstanceRecord requestBean) throws Exception {

		return dataProcessDao.getruleListForLoop(requestBean);
	}
	/*
	 C_LOG_STATE_WARNING1      CONSTANT VARCHAR2(50) := 'W101';
	  C_LOG_MSG_WARNING1        CONSTANT VARCHAR2(200) := '警告:分摊源为空';
	  C_LOG_STATE_WARNING2      CONSTANT VARCHAR2(50) := 'W102';
	  C_LOG_MSG_WARNING2        CONSTANT VARCHAR2(200) := '警告:分摊因子为空，无法分摊';
	  C_LOG_STATE_WARNING3      CONSTANT VARCHAR2(50) := 'W103';
	  C_LOG_MSG_WARNING3        CONSTANT VARCHAR2(200) := '警告:分摊结果合计不为0';
	  C_LOG_STATE_WARNING4      CONSTANT VARCHAR2(50) := 'W104';
	  C_LOG_MSG_WARNING4        CONSTANT VARCHAR2(200) := '警告:存在分摊因子为空的分摊源，无法分摊';
	  C_LOG_STATE_WARNING5      CONSTANT VARCHAR2(50) := 'W105';
	  C_LOG_MSG_WARNING5        CONSTANT VARCHAR2(200) := '警告:期间未打开';
	  C_LOG_STATE_ERROR         CONSTANT VARCHAR2(50) := 'E101';
	  C_LOG_MSG_ERROR           CONSTANT VARCHAR2(200) := '分摊处理异常';
	  C_LOG_STATE_SUCCESS       CONSTANT VARCHAR2(50) := 'S101';
	  C_LOG_MSG_SUCCESS         CONSTANT VARCHAR2(200) := '分摊处理成功';




	  C_LOG_STATE_ROLLBACK      CONSTANT VARCHAR2(50) := 'R101';
	  C_LOG_MSG_ROLLBACK        CONSTANT VARCHAR2(200) := '分摊已回滚';
	  C_LOG_STATE_ROLLBACKERROR CONSTANT VARCHAR2(50) := 'R102';
	  C_LOG_MSG_ROLLBACKERROR   CONSTANT VARCHAR2(200) := '分摊回滚失败';*/

}
