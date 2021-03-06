package com.hausontech.hrs.serviceImpl.dataProcessing;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hausontech.hrs.api.constants.JobStatus;
import com.hausontech.hrs.api.dataProcessing.dao.IDataProcessDao;
import com.hausontech.hrs.api.dataProcessing.service.IDataProcessService;
import com.hausontech.hrs.api.engine.ICalculationDao;
import com.hausontech.hrs.bean.dataProcess.AllocRequestInstanceBean;
import com.hausontech.hrs.bean.dataProcess.AllocRequestInstanceHistoryRecord;
import com.hausontech.hrs.bean.dataProcess.AllocRequestInstanceRecord;
import com.hausontech.hrs.bean.dataProcess.AllocScheduleInstanceBean;
import com.hausontech.hrs.bean.dataProcess.LedgerBean;
import com.hausontech.hrs.bean.dataProcess.PeriodBean;
import com.hausontech.hrs.bean.dataProcess.RequestInstanceBean;
import com.hausontech.hrs.bean.engine.CalculationProcessorBean;
import com.hausontech.hrs.bean.etlProcess.ScheduleStatusBean;
import com.hausontech.hrs.bean.transactionProcess.TransactionProcessTypeBean;
import com.hausontech.hrs.daoImpl.IBaseDao2;

import com.hausontech.hrs.daoImpl.dataPorcessing.mapper.AllocRequestInstanceMapper;
//import com.hausontech.hrs.daoImpl.dataPorcessing.mapper.AllocScheduleInstanceMapper;
import com.hausontech.hrs.daoImpl.dataPorcessing.mapper.CalculationProcessorMapper;
import com.hausontech.hrs.daoImpl.dataPorcessing.mapper.FinElementMapper;
import com.hausontech.hrs.daoImpl.dataPorcessing.mapper.HaeAllocInstanceMapper;
import com.hausontech.hrs.daoImpl.dataPorcessing.mapper.LedgerMapper;
import com.hausontech.hrs.daoImpl.dataPorcessing.mapper.PeriodMapper;
import com.hausontech.hrs.daoImpl.dataPorcessing.mapper.RequestInstanceMapper;
import com.hausontech.hrs.daoImpl.engine.HrsBatchPreparedStatementSetter;
import com.hausontech.hrs.daoImpl.engine.HrsStatement;
import com.hausontech.hrs.daoImpl.engine.core.HrsCalProcessorBatchProxy;
import com.hausontech.hrs.daoImpl.engine.core.HrsThreadManagerImpl;
import com.hausontech.hrs.daoImpl.engine.quartz.QuartzManager;
import com.hausontech.hrs.daoImpl.engine.quartz.ScheduleTypeQuartzAddJob;
import com.hausontech.hrs.daoImpl.etlProcess.mapper.JobInstanceMapper;
import com.hausontech.hrs.daoImpl.transactionProcess.mapper.TransactionProcTypeMapper;
import com.hausontech.hrs.exceptions.DuplicateJobRunningException;
import com.hausontech.hrs.utils.DateUtil;

import oracle.jdbc.OracleTypes;

@Service
public class DataProcessServiceImpl implements IDataProcessService {

	// ???????????????
	private static final int BATCH_SIZE = 5000;

//	@Autowired
//	private IDataProcessDao dataProcessDao;

	@Autowired
	private LedgerMapper ledgerMapper;
	@Autowired
	private HaeAllocInstanceMapper allocInstanceMapper;
	@Autowired
	private PeriodMapper periodMapper;

	@Autowired
	private FinElementMapper elementMapper;
	@Autowired
	private RequestInstanceMapper requestInstanceMapper;// ????????????
	@Autowired
	private JobInstanceMapper jobMapper;
	@Autowired
	private AllocRequestInstanceMapper allocrequestInstanceMapper;
	@Autowired
	private IBaseDao2 baseDao2;
	@Autowired
	private CalculationProcessorMapper calculationProcessorMapper;
	@Autowired
	private TransactionProcTypeMapper transactionProcTypeMapper;
	
	
	

	public TransactionProcTypeMapper getTransactionProcTypeMapper() {
		return transactionProcTypeMapper;
	}

	public void setTransactionProcTypeMapper(TransactionProcTypeMapper transactionProcTypeMapper) {
		this.transactionProcTypeMapper = transactionProcTypeMapper;
	}

	public CalculationProcessorMapper getCalculationProcessorMapper() {
		return calculationProcessorMapper;
	}

	public void setCalculationProcessorMapper(CalculationProcessorMapper calculationProcessorMapper) {
		this.calculationProcessorMapper = calculationProcessorMapper;
	}

	public IBaseDao2 getBaseDao2() {
		return baseDao2;
	}

	public AllocRequestInstanceMapper getAllocrequestInstanceMapper() {
		return allocrequestInstanceMapper;
	}

	public void setAllocrequestInstanceMapper(AllocRequestInstanceMapper allocrequestInstanceMapper) {
		this.allocrequestInstanceMapper = allocrequestInstanceMapper;
	}

	public void setLedgerMapper(LedgerMapper ledgerMapper) {
		this.ledgerMapper = ledgerMapper;
	}

	public PeriodMapper getPeriodMapper() {
		return periodMapper;
	}

	public void setPeriodMapper(PeriodMapper periodMapper) {
		this.periodMapper = periodMapper;
	}

	public void setBaseDao2(IBaseDao2 baseDao2) {
		this.baseDao2 = baseDao2;
	}

	public HaeAllocInstanceMapper getAllocInstanceMapper() {
		return allocInstanceMapper;
	}

	public void setAllocInstanceMapper(HaeAllocInstanceMapper allocInstanceMapper) {
		this.allocInstanceMapper = allocInstanceMapper;
	}

	public FinElementMapper getElementMapper() {
		return elementMapper;
	}

	public void setElementMapper(FinElementMapper elementMapper) {
		this.elementMapper = elementMapper;
	}

	public RequestInstanceMapper getRequestInstanceMapper() {
		return requestInstanceMapper;
	}

	public void setRequestInstanceMapper(RequestInstanceMapper requestInstanceMapper) {
		this.requestInstanceMapper = requestInstanceMapper;
	}

	public JobInstanceMapper getJobMapper() {
		return jobMapper;
	}

	public void setJobMapper(JobInstanceMapper jobMapper) {
		this.jobMapper = jobMapper;
	}

	public LedgerMapper getLedgerMapper() {
		return ledgerMapper;
	}

//	public IDataProcessDao getDataProcessDao() {
//		return dataProcessDao;
//	}
//
//	public void setDataProcessDao(IDataProcessDao dataProcessDao) {
//		this.dataProcessDao = dataProcessDao;
//	}

	private ICalculationDao calculationDao;

	public ICalculationDao getCalculationDao() {
		return calculationDao;
	}

	public void setCalculationDao(ICalculationDao calculationDao) {
		this.calculationDao = calculationDao;
	}

	// @Override
	// public List<Map<String, Object>>
	// getJobRunningInstanceList(RequestInstanceBean queryBean) throws Exception
	// {
	// if (queryBean == null) {
	// throw new Exception("no query bean found");
	// }
	// List<Map<String, Object>> result = null;
	// try {
	// result = dataProcessDao.getRequestInstanceList(queryBean);
	// } catch (SQLException se) {
	// System.out.println(se.toString());
	// throw new Exception(se);
	// }
	// // TODO Auto-generated method stub
	// return result;
	// }

	@Override
	public List<Map<String, Object>> getJobRunningInstanceList(RequestInstanceBean queryBean) throws Exception {
		if (queryBean == null) {
			throw new Exception("no query bean found");
		}
		List<Map<String, Object>> result = new ArrayList();
		List<RequestInstanceBean> result1 = null;
		Map<String, Object> map = null;
		if (queryBean == null) {
			throw new Exception("The Reuqest bean is null.");
		}

		// result = dataProcessDao.getRequestInstanceList(requestBean);
		result1 = requestInstanceMapper.getRequestInstanceList(queryBean);
		for (int i = 0; i < result1.size(); i++) {
			map = new HashMap<String, Object>();
			map.put("INSTANCE_ID", result1.get(i).getInstanceId());
			map.put("LEDGER_ID", result1.get(i).getLedgerId());
			map.put("FIN_ELEMENT_TYPE", result1.get(i).getFinElementType());
			map.put("PERIOD_FROM", result1.get(i).getPeriodFrom());
			map.put("PERIOD_TO", result1.get(i).getPeriodTo());
			map.put("START_TIME", result1.get(i).getStartDate());
			map.put("END_TIME", result1.get(i).getEndDate());
			map.put("ELAPSED_TIME", result1.get(i).getElapsedTime());
			map.put("STATUS", result1.get(i).getStatus());
			map.put("MESSAGE", result1.get(i).getMessage());
			result.add(map);
		}
		return result;
	}

	// @Override
	// public List<LedgerBean> getLedgerList() throws Exception {
	// List<LedgerBean> result = null;
	// try {
	// result = dataProcessDao.getLedgerList();
	// } catch (SQLException se) {
	// System.out.println(se.toString());
	// throw new Exception(se);
	// }
	// return result;
	// }
	//
	@Override
	public List<LedgerBean> getLedgerList() throws Exception {
		List<LedgerBean> result = null;
		result = ledgerMapper.getLedgerList();
		// result = dataProcessDao.getLedgerList();
		return result;
	}

	// @Override
	// public List<PeriodBean> getPeriodList() throws Exception {
	// List<PeriodBean> result = null;
	// try {
	// result = dataProcessDao.getPeriodList();
	// } catch (SQLException se) {
	// System.out.println(se.toString());
	// throw new Exception(se);
	// }
	// return result;
	// }

	@Override
	public List<PeriodBean> getPeriodList() throws Exception {
		List<PeriodBean> result = null;
		// result = dataProcessDao.getPeriodList();
		result = periodMapper.getPeriodList();
		return result;
	}

	// @Override
	// public AllocScheduleInstanceBean getScheduleAllocJobById(String
	// scheduleId) throws Exception {
	// // TODO Auto-generated method stub
	// AllocScheduleInstanceBean result = null;
	// result=dataProcessDao.getScheduleById(scheduleId);
	// return result;
	// }

	@Override
	public AllocScheduleInstanceBean getScheduleAllocJobById(String scheduleId) throws Exception {
		// TODO Auto-generated method stub
		AllocScheduleInstanceBean result = null;

		result = allocInstanceMapper.getScheduleById(scheduleId);

		return result;
	}

	// @Override
	// public List<Map<String, Object>> getLedgerListMapForDisplay() throws
	// Exception {
	// List<Map<String, Object>> result = null;
	// try {
	// result = dataProcessDao.getLedgerListMapForDisplay();
	// } catch (SQLException se) {
	// System.out.println(se.toString());
	// throw new Exception(se);
	// }
	// return result;
	// }

	// ??????????????????List<Map<String, Object>>??????????????????????????????JSON??????
	@Override
	public List<Map<String, Object>> getLedgerListMapForDisplay() throws Exception {

		List<Map<String, Object>> result = new ArrayList();
		List<LedgerBean> result1 = null;
		Map<String, Object> map = null;
		result1 = ledgerMapper.getLedgerList();
		for (int i = 0; i < result1.size(); i++) {
			map = new HashMap<String, Object>();
			map.put("LEDGERID", result1.get(i).getLederCode());
			map.put("LEDGERNAME", result1.get(i).getLederName());
			result.add(map);
		}

		return result;
	}

	// @Override
	// public List<Map<String, Object>> getPeriodListMapForDisplay() throws
	// Exception {
	// List<Map<String, Object>> result = null;
	// try {
	// result = dataProcessDao.getPeriodListMapForDisplay();
	// } catch (SQLException se) {
	// throw new Exception(se);
	// }
	// return result;
	// }

	@Override
	public List<Map<String, Object>> getPeriodListMapForDisplay() throws Exception {
		List<Map<String, Object>> result = new ArrayList();
		List<PeriodBean> result1 = null;
		Map<String, Object> map = null;
		// result1=periodMapper.getPeriodList();
		result1 = periodMapper.getPeriodListMapForDisplay();
		// result = dataProcessDao.getPeriodListMapForDisplay();
		for (int i = 0; i < result1.size(); i++) {
			map = new HashMap<String, Object>();
			map.put("PERIODID", result1.get(i).getPeriodName());
			map.put("PERIODNAME", result1.get(i).getPeriodName());
			result.add(map);
		}
		return result;
	}

	// @Override
	// public List<Map<String, Object>> getFinElementMapForDisplay() throws
	// Exception {
	// List<Map<String, Object>> result = null;
	// try {
	// result = dataProcessDao.getFinElementMapForDisplay();
	// } catch (SQLException se) {
	// throw new Exception(se);
	// }
	// return result;
	// }

	@Override
	public List<Map<String, Object>> getFinElementMapForDisplay() throws Exception {
		List<Map<String, Object>> result = null;
		result = elementMapper.getFinElementMapForDisplay();
		return result;
	}

	// @Override
	// public List<Map<String, Object>>
	// getRequestInstanceList(RequestInstanceBean requestBean) throws Exception
	// {
	// if (requestBean == null) {
	// throw new Exception("The Reuqest bean is null.");
	// }
	// List<Map<String, Object>> result = null;
	// try {
	// result = dataProcessDao.getRequestInstanceList(requestBean);
	// } catch (SQLException se) {
	// System.out.println(se.toString());
	// throw new Exception(se);
	// }
	// return result;
	// }

	@Override
	public List<Map<String, Object>> getRequestInstanceList(RequestInstanceBean requestBean) throws Exception {

		List<Map<String, Object>> result = new ArrayList();
		List<RequestInstanceBean> result1 = null;
		Map<String, Object> map = null;
		if (requestBean == null) {
			throw new Exception("The Reuqest bean is null.");
		}

		// result = dataProcessDao.getRequestInstanceList(requestBean);
		result1 = requestInstanceMapper.getRequestInstanceList(requestBean);
		for (int i = 0; i < result1.size(); i++) {
			map = new HashMap<String, Object>();
			map.put("INSTANCE_ID", result1.get(i).getInstanceId());
			map.put("LEDGER_ID", result1.get(i).getLedgerId());
			map.put("FIN_ELEMENT_TYPE", result1.get(i).getFinElementType());
			map.put("PERIOD_FROM", result1.get(i).getPeriodFrom());
			map.put("PERIOD_TO", result1.get(i).getPeriodTo());
			map.put("START_TIME", result1.get(i).getStartDate());
			map.put("END_TIME", result1.get(i).getEndDate());
			map.put("ELAPSED_TIME", result1.get(i).getElapsedTime());
			map.put("STATUS", result1.get(i).getStatus());
			map.put("MESSAGE", result1.get(i).getMessage());
			result.add(map);
		}
		return result;
	}

	// @Override
	// public int getBatchJobCount(RequestInstanceBean jobInsBean) throws
	// Exception {
	// if (jobInsBean == null) {
	// throw new Exception("no job request bean found");
	// }
	// int result = 0;
	// try {
	// result = dataProcessDao.getBatchInstanceNumber(jobInsBean);
	// } catch (SQLException se) {
	// throw new Exception(se);
	// }
	// return result;
	// }

	@Override
	public int getBatchJobCount(RequestInstanceBean jobInsBean) throws Exception {
		if (jobInsBean == null) {
			throw new Exception("no job request bean found");
		}
		int result = 0;
		result = requestInstanceMapper.getBatchInstanceNumber(jobInsBean);
		// result = dataProcessDao.getBatchInstanceNumber(jobInsBean);
		return result;
	}

	// @Override
	// public long createBatchJobRecord(RequestInstanceBean jobRecord) throws
	// Exception {
	// if (jobRecord == null) {
	// throw new Exception("The job bean is null.");
	// }
	// long result = 0;
	// try {
	// result = dataProcessDao.saveBatchJobRecord(jobRecord);
	// } catch (SQLException se) {
	// throw new Exception(se);
	// }
	// return result;
	// }

	// ??????????????????
	@Override
	public long createBatchJobRecord(RequestInstanceBean jobRecord) throws Exception {
		if (jobRecord == null) {
			throw new Exception("The job bean is null.");
		}
		int result = 0;
		long primaryKey = baseDao2.getAutoGeneratedPrimaryKey("HRS_CORE_REQUEST_INSTANCE_S");
		// result = dataProcessDao.saveBatchJobRecord(jobRecord);
		jobRecord.setInstanceId(primaryKey);
		result = requestInstanceMapper.saveBatchJobRecord(jobRecord);
		return primaryKey;
	}

	// //??????????????????
	// @Override
	// public int updateBatchJobRecord(RequestInstanceBean jobBean) throws
	// Exception {
	// if (jobBean == null || jobBean.getInstanceId() == 0) {
	// throw new Exception("The Job Bean is null or job instance id is empty.");
	// }
	// int result = 0;
	// try {
	// // try to get dimension information
	// result = dataProcessDao.updateBatchJobRecord(jobBean);
	// } catch (SQLException se) {
	// throw new Exception(se);
	// }
	// return result;
	// }

	// ??????????????????
	@Override
	public int updateBatchJobRecord(RequestInstanceBean jobBean) throws Exception {
		if (jobBean == null || jobBean.getInstanceId() == 0) {
			throw new Exception("The Job Bean is null or job instance id is empty.");
		}
		int result = 0;
		// try to get dimension information
		// result = dataProcessDao.updateBatchJobRecord(jobBean);
		result = requestInstanceMapper.updateBatchJobRecord(jobBean);
		return result;
	}

	// //????????????
	// @Override
	// public int deleteBatchJobRecord(RequestInstanceBean jobBean) throws
	// Exception {
	// if (jobBean == null || jobBean.getInstanceId() == 0) {
	// throw new Exception("The Job Bean is null or job instance id is empty.");
	// }
	// int delCount = 0;
	// try {
	// // try to delete rule header
	// delCount = dataProcessDao.deleteBatchJobRecord(jobBean);
	// } catch (SQLException se) {
	// throw new Exception(se);
	// }
	// return delCount;
	// }

	// ????????????
	@Override
	public int deleteBatchJobRecord(RequestInstanceBean jobBean) throws Exception {
		if (jobBean == null || jobBean.getInstanceId() == 0) {
			throw new Exception("The Job Bean is null or job instance id is empty.");
		}
		int delCount = 0;
		// try to delete rule header
		delCount = requestInstanceMapper.deleteBatchJobRecord(jobBean);
		return delCount;
	}

	// public void calculate(RequestInstanceBean requestBean) throws
	// DuplicateJobRunningException, Exception {
	// // get content and calculation items
	// Map<String, List<CalculationProcessorBean>> contentList = null;
	// List<CalculationProcessorBean> calculationResult = null;
	// boolean isError = false;
	// String errorMsg = "";
	// long startTime = System.currentTimeMillis();
	// try {
	// RequestInstanceBean queryBean = new RequestInstanceBean();
	// queryBean.setStatus(JobStatus.PROCESSING);
	// int runningCount = this.getBatchJobCount(queryBean);
	// if (0 < runningCount) {
	// throw new DuplicateJobRunningException();
	// }
	// // set job instance status
	// String status=allocInstanceMapper.getStatus(null, JobStatus.PROCESSING);
	// requestBean.setStatus(status);
	// requestBean.setStartDate(new Date());
	// requestBean.setLastUpdateDate(new Date());
	// requestBean.setMessage("");
	// //dataProcessDao.updateBatchJobRecord(requestBean);//
	// requestInstanceMapper.updateBatchJobRecord(requestBean);
	// // Execute content
	// // CalculationDao calDao = new CalculationDao();
	// this.calculationDao.generateFinIndex(requestBean);///@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	//
	// // get item constant list
	// contentList = calculationDao.getItemContents(requestBean);
	// if (0 < contentList.size()) {
	// // calculation in the group
	// calculationResult = this.calculateInGroup(contentList);
	// }
	// } catch (DuplicateJobRunningException de) {
	// isError = true;
	// errorMsg = "?????????????????????????????????????????????????????????????????????";
	// throw de;
	// } catch (Exception e) {
	// isError = true;
	// errorMsg = e.toString();
	// System.out.println("Error happens when calculation: " + e);
	// }
	//
	// // if there's result , then insert result into db
	// if (calculationResult != null && !calculationResult.isEmpty()) {
	// try {
	// calculationDao.insertCalculationItems(calculationResult,
	// requestBean.getRequestUser(),
	// requestBean.getInstanceId());
	// } catch (SQLException e) {
	// isError = true;
	// errorMsg = e.toString();
	// }
	// }
	// requestBean.setStatus(allocInstanceMapper.getStatus(null,
	// JobStatus.SUCCESS));
	// if (isError) {
	// requestBean.setStatus(allocInstanceMapper.getStatus(null,
	// JobStatus.FAILED));
	// requestBean.setMessage(errorMsg);
	// }
	// requestBean.setEndDate(new Date());
	// requestBean.setElapsedTime(System.currentTimeMillis() - startTime);
	// //dataProcessDao.updateBatchJobRecord(requestBean);
	// requestInstanceMapper.updateBatchJobRecord(requestBean);
	// }

	public void calculate(RequestInstanceBean requestBean) throws DuplicateJobRunningException, Exception {
		// get content and calculation items
		Map<String, List<CalculationProcessorBean>> contentList = new HashMap<String, List<CalculationProcessorBean>>();
		Map<String, Object> map = new HashMap<String, Object>();

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
			String status = allocInstanceMapper.getStatus(null, JobStatus.PROCESSING);
			requestBean.setStatus(status);
			requestBean.setStartDate(new Date());
			requestBean.setLastUpdateDate(new Date());
			requestBean.setMessage("");
			// dataProcessDao.updateBatchJobRecord(requestBean);//
			requestInstanceMapper.updateBatchJobRecord(requestBean);
			// Execute content
			// CalculationDao calDao = new CalculationDao();

			calculationProcessorMapper.generateFinIndex(requestBean);
			// this.calculationDao.generateFinIndex(requestBean);
			// get item constant list
			Map<String, Object> param = new HashMap<>();
			param.put("finElementType", requestBean.getFinElementType());
			param.put("ledgerId", requestBean.getLedgerId());
			param.put("periodFrom", requestBean.getPeriodFrom());
			param.put("periodTo", requestBean.getPeriodTo());
			// param.put("periodTo",requestBean.getPeriodFrom());
			param.put("instanceId", requestBean.getInstanceId());
			param.put("result", OracleTypes.CURSOR);
			List<CalculationProcessorBean> results = calculationProcessorMapper.getItemContents(param);
			results = (List<CalculationProcessorBean>) param.get("result");
			int groupNumber = 1;
			CalculationProcessorBean currentCalBean = null;
			CalculationProcessorBean previousCalBean = null;
			List<CalculationProcessorBean> innerList = null;
			boolean isConstantExsit = false;
			for (int i = 0; i < results.size(); i++) {
				// construct calculate bean object
				currentCalBean = results.get(i);
				// if record is not constant
				if (!"CONSTANT".equals(currentCalBean.getCalItemCode())) {
					// if it is the first record
					if (previousCalBean == null) {
						// set previous record to current one
						previousCalBean = currentCalBean;
						// initialize inner list
						if (innerList == null) {
							innerList = new ArrayList<CalculationProcessorBean>();
							innerList.add(currentCalBean);
						}
					} else {
						// if previous record and current has same conditions,
						// put them in the same list
						if (previousCalBean.equals(currentCalBean)) {
							// initialize inner list
							if (innerList == null) {
								innerList = new ArrayList<CalculationProcessorBean>();
							}
							innerList.add(currentCalBean);
						} else {
							// if previous and current are not in same
							// conditions
							contentList.put(String.valueOf(groupNumber), innerList);
							groupNumber++;
							// re-initialize inner list
							innerList = new ArrayList<CalculationProcessorBean>();
							// add current record
							innerList.add(currentCalBean);
						}
						// reset previous record to current one
						previousCalBean = currentCalBean;
					}
				} else { // if record is constant, need to add to each group
					isConstantExsit = true;
					// if
					if (0 < innerList.size()) {
						contentList.put(String.valueOf(groupNumber), innerList);
						// re-initialize inner list
						innerList = new ArrayList<CalculationProcessorBean>();
					}
					if (0 < contentList.size()) {
						// loop to add constant record to the group
						for (List<CalculationProcessorBean> value : contentList.values()) {
							addConstantInGroup(value, currentCalBean);
						}
					}
				}

			}
			if (!isConstantExsit && innerList != null && 0 < innerList.size()) {
				contentList.put(String.valueOf(groupNumber), innerList);
			}

			//contentList = this.calculationDao.getItemContents(requestBean);
			if (0 < contentList.size()) {
				// calculation in the group
				calculationResult = this.calculateInGroup(contentList);
			}
		} catch (DuplicateJobRunningException de) {
			isError = true;
			errorMsg = "?????????????????????????????????????????????????????????????????????";
			throw de;
		} catch (Exception e) {
			isError = true;
			errorMsg = e.toString();
			System.out.println("Error happens when calculation: " + e);
		}

		// // if there's result , then insert result into db
		// if (calculationResult != null && !calculationResult.isEmpty()) {
		// try {
		// calculationDao.insertCalculationItems(calculationResult,
		// requestBean.getRequestUser(),
		// requestBean.getInstanceId());
		// } catch (SQLException e) {
		// isError = true;
		// errorMsg = e.toString();
		// }
		// }

		// if there's result , then insert result into db
		if (calculationResult != null && !calculationResult.isEmpty()) {
			try {

				List<CalculationProcessorBean> batchList = new ArrayList<CalculationProcessorBean>();
				int counter = 0;
				for (CalculationProcessorBean calBean : calculationResult) {
					counter++;
					String createdUser = requestBean.getRequestUser();
					Long instanceId = requestBean.getInstanceId();
					calBean.setCreatedBy(createdUser);
					calBean.setUpdatedBy(createdUser);
					calBean.setRequestInstanceId(instanceId);
					map.put("calBean", calBean);
					map.put("createdUser", createdUser);
					map.put("instanceId", instanceId);

					batchList.add(calBean);
					if (counter == BATCH_SIZE) {
						// this.jdbcTemplate.batchUpdate(sqlString, new
						// HrsBatchPreparedStatementSetter(batchList));
						calculationProcessorMapper.insertCalculationItems(map);// ?????????
						// calculationDao.insertCalculationItems(calculationResult,
						// requestBean.getRequestUser(),requestBean.getInstanceId());

						counter = 0;
						batchList = new ArrayList<CalculationProcessorBean>();
					}
				}

				if (counter != 0) {
					try {
						// this.jdbcTemplate.batchUpdate(sqlString, new
						// HrsBatchPreparedStatementSetter(batchList));
						calculationProcessorMapper.insertCalculationItems(map);
					} catch (Exception e) {
						throw new SQLException(e.toString());
					}
				}

			} catch (SQLException e) {
				isError = true;
				errorMsg = e.toString();
			}
		}
		requestBean.setStatus(allocInstanceMapper.getStatus(null, JobStatus.SUCCESS));
		if (isError) {
			requestBean.setStatus(allocInstanceMapper.getStatus(null, JobStatus.FAILED));
			requestBean.setMessage(errorMsg);
		}
		requestBean.setEndDate(new Date());
		requestBean.setElapsedTime(System.currentTimeMillis() - startTime);
		// dataProcessDao.updateBatchJobRecord(requestBean);
		requestInstanceMapper.updateBatchJobRecord(requestBean);
	}

	// public long getRequestInstanceId() throws Exception {
	// return dataProcessDao.getRequestInstanceId();
	// }

	public long getRequestInstanceId() throws Exception {
		// return dataProcessDao.getRequestInstanceId();
		return baseDao2.getAutoGeneratedPrimaryKey("HRS_CORE_REQUEST_INSTANCE_S");
	}

	// @Override
	// public RequestInstanceBean
	// getRequestInstanceByPrimaryKey(RequestInstanceBean requestBean) throws
	// Exception {
	// if (requestBean == null || requestBean.getInstanceId() == 0) {
	// throw new Exception("The Reuqest bean is null.");
	// }
	// RequestInstanceBean result = null;
	// try {
	// result = dataProcessDao.getRequestInstanceByPrimaryKey(requestBean);
	// } catch (SQLException se) {
	// throw new Exception(se);
	// }
	// return result;
	// }

	@Override
	public RequestInstanceBean getRequestInstanceByPrimaryKey(RequestInstanceBean requestBean) throws Exception {
		if (requestBean == null || requestBean.getInstanceId() == 0) {
			throw new Exception("The Reuqest bean is null.");
		}
		RequestInstanceBean result = null;
		result = requestInstanceMapper.getRequestInstanceByPrimaryKey(requestBean);
		return result;
	}

	
//	@Override
//	public List<TransactionProcessTypeBean> getTransationProcessTypeList() throws Exception {
//		List<TransactionProcessTypeBean> result = null;
//		try {
//			result = dataProcessDao.getTransationProcessTypeList();pppp
//		} catch (SQLException se) {
//			System.out.println(se.toString());
//			throw new Exception(se);
//		}
//		return result;
//	}

	@Override
	public List<TransactionProcessTypeBean> getTransationProcessTypeList() throws Exception {
		List<TransactionProcessTypeBean> result = null;
		result = transactionProcTypeMapper.getTransationProcessTypeList();
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

	// @Override
	// public List<Map<String, Object>>
	// getAllocRequestInstanceList(AllocRequestInstanceBean requestBean)
	// throws Exception {
	// if (requestBean == null) {
	// throw new Exception("The Reuqest bean is null.");
	// }
	// List<Map<String, Object>> result = null;
	// try {
	// result = dataProcessDao.getAllocRequestInstanceList(requestBean);
	// } catch (SQLException se) {
	// System.out.println(se.toString());
	// throw new Exception(se);
	// }
	// return result;
	// }

	@Override
	public List<Map<String, Object>> getAllocRequestInstanceList(AllocRequestInstanceBean requestBean)
			throws Exception {
		if (requestBean == null) {
			throw new Exception("The Reuqest bean is null.");
		}
		/* ??????????????????????????????Map */
		List<Map<String, Object>> result = new ArrayList();
		Map<String, Object> map = new HashMap<String, Object>();
		List<AllocRequestInstanceBean> result1 = null;
		result1 = allocrequestInstanceMapper.getAllocRequestInstanceList(requestBean);
		for (int i = 0; i < result1.size(); i++) {
			map = new HashMap<String, Object>();
			map.put("INSTANCE_ID", result1.get(i).getInstanceId());
			map.put("RULE_ID", result1.get(i).getRuleId());
			map.put("RULE_TYPE", result1.get(i).getRuleType());
			map.put("PERIOD", result1.get(i).getPeriod());
			map.put("START_TIME", result1.get(i).getStartDate());
			map.put("END_TIME", result1.get(i).getEndDate());
			map.put("ELAPSED_TIME", result1.get(i).getElapsedTime());
			map.put("STATUS", result1.get(i).getStatus());
			map.put("MESSAGE", result1.get(i).getMessage());
			result.add(map);
		}
		return result;
	}

	// @Override
	// public int getAllocBatchJobCount(AllocRequestInstanceBean jobInsBean)
	// throws Exception {
	// if (jobInsBean == null) {
	// throw new Exception("no job request bean found");
	// }
	// int result = 0;
	// try {
	// result = dataProcessDao.getAllocBatchInstanceNumber(jobInsBean);
	// } catch (SQLException se) {
	// throw new Exception(se);
	// }
	// return result;
	// }

	@Override
	public int getAllocBatchJobCount(AllocRequestInstanceBean jobInsBean) throws Exception {
		if (jobInsBean == null) {
			throw new Exception("no job request bean found");
		}
		int result = 0;
		result = allocrequestInstanceMapper.getAllocBatchInstanceNumber(jobInsBean);
		return result;
	}

	// @Override
	// public long createAllocBatchJobRecord(AllocRequestInstanceBean jobRecord)
	// throws Exception {
	// if (jobRecord == null) {
	// throw new Exception("The job bean is null.");
	// }
	// long result = 0;
	// try {
	// result = dataProcessDao.saveAllocBatchJobRecord(jobRecord);
	// } catch (SQLException se) {
	// throw new Exception(se);
	// }
	// return result;
	// }

	// @Override
	// public long createAllocBatchJobRecord(AllocRequestInstanceBean jobRecord)
	// throws Exception {
	// if (jobRecord == null) {
	// throw new Exception("The job bean is null.");
	// }
	// long result = 0;
	// try {
	// result = dataProcessDao.saveAllocBatchJobRecord(jobRecord);//@@@@@@
	// } catch (SQLException se) {
	// throw new Exception(se);
	// }
	// return result;
	// }

	@Override
	public long createAllocBatchJobRecord(AllocRequestInstanceBean jobRecord) throws Exception {
		if (jobRecord == null) {
			throw new Exception("The job bean is null.");
		}
		long result = 0;
		long primaryKey = baseDao2.getAutoGeneratedPrimaryKey("HRS_CORE_A_REQUEST_INSTANCE_S");
		jobRecord.setInstanceId(primaryKey);
		result = allocrequestInstanceMapper.saveAllocBatchJobRecord(jobRecord);// @@@@@@
		return result;
	}

	// @Override
	// public int updateAllocBatchJobRecord(AllocRequestInstanceBean jobBean)
	// throws Exception {
	// if (jobBean == null || jobBean.getInstanceId() == 0) {
	// throw new Exception("The Job Bean is null or job instance id is empty.");
	// }
	// int result = 0;
	// try {
	// // try to get dimension information
	// result = dataProcessDao.updateAllocBatchJobRecord(jobBean);
	// } catch (SQLException se) {
	// throw new Exception(se);
	// }
	// return result;
	// }

	@Override
	public int updateAllocBatchJobRecord(AllocRequestInstanceBean jobBean) throws Exception {
		if (jobBean == null || jobBean.getInstanceId() == 0) {
			throw new Exception("The Job Bean is null or job instance id is empty.");
		}
		int result = 0;
		// try to get dimension information
		result = allocrequestInstanceMapper.updateAllocBatchJobRecord(jobBean);
		return result;
	}

	@Override
	public int updateAllocSchedule(AllocScheduleInstanceBean reqBean) throws Exception {
		// TODO Auto-generated method stub
		if (reqBean == null || reqBean.getInstanceId() == 0) {
			throw new Exception("The Job Bean is null or job instance id is empty.");
		}
		int result = 0;
		// try to get dimension information
		result = allocInstanceMapper.updateAllocSchedule(reqBean);
		return result;
	}

	// @Override
	// public int deleteAllocBatchJobRecord(AllocRequestInstanceBean jobBean)
	// throws Exception {
	// if (jobBean == null || jobBean.getInstanceId() == 0) {
	// throw new Exception("The Job Bean is null or job instance id is empty.");
	// }
	// int delCount = 0;
	// try {
	// // try to delete rule header
	// delCount = dataProcessDao.deleteAllocBatchJobRecord(jobBean);
	// } catch (SQLException se) {
	// throw new Exception(se);
	// }
	// return delCount;
	// }

	@Override
	public int deleteAllocBatchJobRecord(AllocRequestInstanceBean jobBean) throws Exception {
		if (jobBean == null || jobBean.getInstanceId() == 0) {
			throw new Exception("The Job Bean is null or job instance id is empty.");
		}
		int delCount = 0;
		// try to delete rule header
		delCount = allocrequestInstanceMapper.deleteAllocBatchJobRecord(jobBean);
		return delCount;
	}

	// @Override
	// public List<Map<String, Object>> getAvailablePeriodListMapForDisplay()
	// throws Exception {
	// List<Map<String, Object>> result = null;
	// try {
	// result = dataProcessDao.getAvailablePeriodListMapForDisplay();
	// } catch (SQLException se) {
	// throw new Exception(se);
	// }
	// return result;
	// }

	@Override
	public List<Map<String, Object>> getAvailablePeriodListMapForDisplay() throws Exception {

		List<Map<String, Object>> result = new ArrayList();
		List<PeriodBean> result1 = null;
		Map<String, Object> map = null;
		result1 = periodMapper.getAvailablePeriodListMapForDisplay();
		for (int i = 0; i < result1.size(); i++) {
			map = new HashMap<String, Object>();
			map.put("PERIODID", result1.get(i).getPeriodName());
			map.put("PERIODNAME", result1.get(i).getPeriodName());
			result.add(map);
		}

		return result;
	}

	// @Override
	// public List<PeriodBean> getAvailablePeriodList() throws Exception {
	// List<PeriodBean> result = null;
	// try {
	// result = dataProcessDao.getAvailablePeriodList();
	// } catch (SQLException se) {
	// System.out.println(se.toString());
	// throw new Exception(se);
	// }
	// return result;
	// }

	@Override
	public List<PeriodBean> getAvailablePeriodList() throws Exception {
		List<PeriodBean> result = null;
		result = periodMapper.getAvailablePeriodList();
		return result;
	}

	@Override
	public List<Map<String, Object>> getGroupRuleList() throws Exception {
		List<Map<String, Object>> result = null;
		try {
			result = allocInstanceMapper.getGroupRuleList();
		} catch (Exception e) {
			throw new Exception(e);
		}
		return result;

	}

	@Override
	public List<Map<String, Object>> getruleList(String type) throws Exception {
		List<Map<String, Object>> result = null;
		try {
			result = allocInstanceMapper.getruleList(type);
		} catch (Exception se) {
			throw new Exception(se);
		}
		return result;

	}

	// @Override
	// public AllocRequestInstanceBean
	// getAllocRequestInstanceByPrimaryKey(AllocRequestInstanceBean requestBean)
	// throws Exception {
	// if (requestBean == null || requestBean.getInstanceId() == 0) {
	// throw new Exception("The Reuqest bean is null.");
	// }
	// AllocRequestInstanceBean result = null;
	// try {
	// result = dataProcessDao.getAllocRequestInstanceByPrimaryKey(requestBean);
	// } catch (SQLException se) {
	// throw new Exception(se);
	// }
	// return result;
	// }

	@Override
	public AllocRequestInstanceBean getAllocRequestInstanceByPrimaryKey(AllocRequestInstanceBean requestBean)
			throws Exception {
		if (requestBean == null || requestBean.getInstanceId() == 0) {
			throw new Exception("The Reuqest bean is null.");
		}
		AllocRequestInstanceBean result = null;
		result = allocrequestInstanceMapper.getAllocRequestInstanceByPrimaryKey(requestBean);
		return result;
	}
	// @Override
	// public void calculateAllocJob(AllocRequestInstanceBean requestBean)
	// throws Exception {
	// // get content and calculation items
	// Map<String, List<CalculationProcessorBean>> contentList = null;
	// List<CalculationProcessorBean> calculationResult = null;
	// boolean isError = false;
	// String errorMsg = "";
	// long startTime = System.currentTimeMillis();
	// try {
	// AllocRequestInstanceBean queryBean = new AllocRequestInstanceBean();
	// queryBean.setStatus(JobStatus.PROCESSING);
	// int runningCount = this.getAllocBatchJobCount(queryBean);
	// if (0 < runningCount) {
	// throw new DuplicateJobRunningException();
	// }
	// // set job instance status
	// requestBean.setStatus(JobStatus.PROCESSING);
	// requestBean.setStartDate(new Date());
	// requestBean.setMessage("");
	// allocrequestInstanceMapper.updateAllocBatchJobRecord(requestBean);
	//
	// // Execute content
	// // CalculationDao calDao = new CalculationDao();
	// // this.calculationDao.generateFinIndexAlloc(requestBean);
	// // get item constant list
	//
	// //@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	// Map map = new HashMap();
	// map.put("returnvalue", "");
	// map.put("historyId", requestHistoryBean.getHistoryId());
	// allocInstanceMapper.getItemContentsAlloc(map);
	// returnvalue=(String)map.get("returnvalue");
	// //@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	//
	// int returnvalue =
	// calculationDao.getItemContentsAlloc(requestBean);//@@@@@@@
	//
	//
	// /*
	// * if (0 < contentList.size()) { //calculation in the group
	// * calculationResult = this.calculateInGroup(contentList); }
	// */
	// if (returnvalue == 2) {
	// isError = true;
	// }
	// } catch (DuplicateJobRunningException de) {
	// isError = true;
	// errorMsg = "?????????????????????????????????????????????????????????????????????";
	// requestBean.setStatus("ERROR");
	// allocrequestInstanceMapper.updateAllocBatchJobRecord(requestBean);
	// throw de;
	// } catch (Exception e) {
	// isError = true;
	// errorMsg = e.toString();
	// requestBean.setStatus("ERROR");
	// allocrequestInstanceMapper.updateAllocBatchJobRecord(requestBean);
	// System.out.println("Error happens when calculation: " + e);
	// }
	//
	// // if there's result , then insert result into db
	// /*
	// * if (calculationResult != null && !calculationResult.isEmpty()) { try
	// * { calculationDao.insertCalculationItems(calculationResult,
	// * requestBean.getRequestUser(), requestBean.getInstanceId()); } catch
	// * (SQLException e) { isError = true; errorMsg = e.toString(); } }
	// */
	//
	// if (isError) {
	// requestBean.setStatus(allocInstanceMapper.getStatus(null,
	// JobStatus.FAILED));
	// requestBean.setMessage(errorMsg);
	// } else {
	// requestBean.setStatus(allocInstanceMapper.getStatus(null,
	// JobStatus.SUCCESS));
	// }
	// requestBean.setEndDate(new Date());
	// requestBean.setElapsedTime(System.currentTimeMillis() - startTime);
	// allocrequestInstanceMapper.updateAllocBatchJobRecord(requestBean);
	//
	// }

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
			// dataProcessDao.updateAllocBatchJobRecord(requestBean);
			allocrequestInstanceMapper.updateAllocBatchJobRecord(requestBean);

			// Execute content
			// CalculationDao calDao = new CalculationDao();
			// this.calculationDao.generateFinIndexAlloc(requestBean);
			// get item constant list

			// int returnvalue =
			// calculationDao.getItemContentsAlloc(requestBean);

			Map map = new HashMap();
			map.put("processResult", "");
			map.put("instanceId", requestBean.getInstanceId());
			calculationProcessorMapper.getItemContentsAlloc(map);
			int returnvalue = (int) map.get("processResult");
			/*
			 * if (0 < contentList.size()) { //calculation in the group
			 * calculationResult = this.calculateInGroup(contentList); }
			 */
			if (returnvalue == 2) {
				isError = true;
			}
		} catch (DuplicateJobRunningException de) {
			isError = true;
			errorMsg = "?????????????????????????????????????????????????????????????????????";
			requestBean.setStatus("ERROR");
			//dataProcessDao.updateAllocBatchJobRecord(requestBean);
			allocrequestInstanceMapper.updateAllocBatchJobRecord(requestBean);

			throw de;
		} catch (Exception e) {
			isError = true;
			errorMsg = e.toString();
			requestBean.setStatus("ERROR");
			// dataProcessDao.updateAllocBatchJobRecord(requestBean);
			allocrequestInstanceMapper.updateAllocBatchJobRecord(requestBean);
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
			requestBean.setStatus(allocInstanceMapper.getStatus(null, JobStatus.FAILED));
			requestBean.setMessage(errorMsg);
		} else {
			requestBean.setStatus(allocInstanceMapper.getStatus(null, JobStatus.SUCCESS));
		}
		requestBean.setEndDate(new Date());
		requestBean.setElapsedTime(System.currentTimeMillis() - startTime);
		// dataProcessDao.updateAllocBatchJobRecord(requestBean);
		allocrequestInstanceMapper.updateAllocBatchJobRecord(requestBean);

	}

	@Override
	public List<Map<String, Object>> getAllocRequestInstance(AllocRequestInstanceRecord requestBean) throws Exception {
		if (requestBean == null) {
			throw new Exception("The Reuqest bean is null.");
		}
		Map<String, Object> map = null;
		List<Map<String, Object>> result = new ArrayList();
		List<AllocRequestInstanceRecord> result1 = null;
		String start = String.valueOf(requestBean.getRowStartIndex());
		String count = String.valueOf(requestBean.getRowCount());
		String ruleType = requestBean.getRuleType();
		String period = requestBean.getPeriod();
		result1 = allocInstanceMapper.getAllocRequestInstance(start, ruleType, period, count);
		for (int i = 0; i < result1.size(); i++) {
			map = new HashMap<String, Object>();
			map.put("INSTANCE_ID", result1.get(i).getInstanceId());
			map.put("RULE_ID", result1.get(i).getRuleId());
			map.put("RULE_TYPE", result1.get(i).getRuleType());
			map.put("PERIOD", result1.get(i).getPeriod());
			map.put("DESCRIPTION", result1.get(i).getDescription());
			map.put("CREATION_DATE", result1.get(i).getCreationDate());
			map.put("CREATED_BY", result1.get(i).getCreatedBy());
			map.put("LAST_UPDATE_DATE", result1.get(i).getLastUpdateDate());
			map.put("LAST_UPDATED_BY", result1.get(i).getLastUpdatedBy());
			result.add(map);
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> getAllocScheduleInstance(AllocScheduleInstanceBean scheduleBean) throws Exception {
		if (scheduleBean == null) {
			throw new Exception("The Reuqest bean is null.");
		}
		Map<String, Object> map = null;
		long scheduleId = 0;
		List<Map<String, Object>> result = new ArrayList();
		List<AllocScheduleInstanceBean> result1 = null;
		String start = String.valueOf(scheduleBean.getRowStartIndex());
		String count = String.valueOf(scheduleBean.getRowCount());
		String ruleType = scheduleBean.getRuleType();
		result1 = allocInstanceMapper.getAllocScheduleInstance(start, ruleType, null, count);
		for (int i = 0; i < result1.size(); i++) {
			map = new HashMap<String, Object>();
			scheduleId = result1.get(i).getScheduleId();
			map.put("INSTANCE_ID", result1.get(i).getInstanceId());
			map.put("SCHEDULE_ID", scheduleId);
			map.put("RULE_TYPE", result1.get(i).getRuleType());
			map.put("TYPE_PROCESS", result1.get(i).getTypeProcess());
			map.put("DESCRIPTION", result1.get(i).getDescription());
			map.put("CONCURRENT", result1.get(i).getConcurrent());
			map.put("VALUE_PROCESS", result1.get(i).getValueProcess());
			map.put("STATUS", QuartzManager.getJobStatue(String.valueOf(scheduleId)));
			map.put("CREATED_DATE", result1.get(i).getCreationDate());
			map.put("CREATED_BY", result1.get(i).getCreatedBy());
			map.put("LAST_UPDATE_DATE", result1.get(i).getLastUpdateDate());
			map.put("LAST_UPDATED_BY", result1.get(i).getLastUpdatedBy());
			result.add(map);
		}
		return result;
	}

	@Override
	public int getAllocRequestInstanceCount(AllocRequestInstanceRecord jobInsBean) throws Exception {
		if (jobInsBean == null) {
			throw new Exception("no job request bean found");
		}
		int result = 0;
		String ruleType = jobInsBean.getRuleType();
		String period = jobInsBean.getPeriod();
		result = allocInstanceMapper.getAllocRequestInstanceCount(ruleType, period);
		return result;
	}

	@Override
	public int getAllocScheduleInstanceCount(AllocScheduleInstanceBean scheduleBean) throws Exception {
		if (scheduleBean == null) {
			throw new Exception("no job request bean found");
		}
		int result = 0;
		String ruleType = scheduleBean.getRuleType();
		result = allocInstanceMapper.getAllocScheduleInstanceCount(ruleType);
		return result;
	}

	@Override
	public long saveAllocRequestInstance(AllocRequestInstanceRecord jobRecord) throws Exception {
		if (jobRecord == null) {
			throw new Exception("The job bean is null.");
		}
		int result = 0;
		long primaryKey = baseDao2.getAutoGeneratedPrimaryKey("HAE_ALLOC_INSTANCE_S");
		jobRecord.setInstanceId(primaryKey);
		result = allocInstanceMapper.saveAllocRequestInstance(jobRecord);
		return result;
	}

	@Override
	public int saveAllocScheduleInstance(AllocScheduleInstanceBean scheduleBean) throws Exception {
		// TODO Auto-generated method stub
		if (scheduleBean == null) {
			throw new Exception("The job bean is null.");
		}
		int result = 0;
		long primaryKey = baseDao2.getAutoGeneratedPrimaryKey("HAE_ALLOC_SCHEDULE_S");
		scheduleBean.setScheduleId(primaryKey);
		result = allocInstanceMapper.saveAllocScheduleInstance(scheduleBean);
		return result;
	}

	@Override
	public int updateAllocRequestInstance(AllocRequestInstanceRecord jobBean) throws Exception {
		if (jobBean == null || jobBean.getInstanceId() == 0) {
			throw new Exception("The Job Bean is null or job instance id is empty.");
		}
		int result = 0;
		// try to get dimension information
		result = allocInstanceMapper.updateAllocRequestInstance(jobBean);
		return result;
	}

	@Override
	public int deleteAllocRequestInstance(AllocRequestInstanceRecord jobBean) throws Exception {
		if (jobBean == null || jobBean.getInstanceId() == 0) {
			throw new Exception("The Job Bean is null or job instance id is empty.");
		}
		int delCount = 0;
		int delCount1 = 0;
		// ?????????etl???job??????????????????????????????
		delCount1 = allocInstanceMapper.deleteAllocRequestInstance("HAE_ALLOC_SCHEDULE",
				String.valueOf(jobBean.getInstanceId()));
		delCount = allocInstanceMapper.deleteAllocRequestInstance("HAE_ALLOC_INSTANCE",
				String.valueOf(jobBean.getInstanceId()));
		return delCount > delCount1 ? delCount : delCount1;
	}

	@Override
	public int deleteScheduleRecord(AllocScheduleInstanceBean scheduleBean) throws Exception {
		// TODO Auto-generated method stub
		if (scheduleBean == null || scheduleBean.getScheduleId() == 0) {
			throw new Exception("The Job Bean is null or job instance id is empty.");
		}
		int delCount = 0;
		// try to delete rule header
		delCount = allocInstanceMapper.deleteScheduleRecordForInstanceId(scheduleBean.getScheduleId());
		return delCount;
	}

	@Override
	public AllocRequestInstanceRecord getAllocRequestInstanceByPrimaryKey(AllocRequestInstanceRecord requestBean)
			throws Exception {
		if (requestBean == null || requestBean.getInstanceId() == 0) {
			throw new Exception("The Reuqest bean is null.");
		}
		AllocRequestInstanceRecord result = null;
		result = allocInstanceMapper.getAllocRequestInstanceByPrimaryKey(requestBean.getInstanceId());
		return result;
	}

	@Override
	public void scheduleCalculateAllocJobForInstacneHistory(AllocScheduleInstanceBean scheduleBean,
			AllocRequestInstanceHistoryRecord requestHistoryBean) throws Exception {
		// TODO Auto-generated method stub
		long scheduleId = scheduleBean.getScheduleId();
		String status = QuartzManager.getJobStatue(String.valueOf(scheduleId));
		// ???????????????
		String scheduleData = scheduleBean.getValueProcess();
		// ??????????????????
		String concurrent = scheduleBean.getConcurrent();
		// ????????????
		String scheduleType = scheduleBean.getTypeProcess();
		// ??????????????????CRON????????????????????????quartz??????job
		String time = ScheduleTypeQuartzAddJob.getQuartzTimeByType(scheduleType, scheduleData);
		/*
		 * STATE_BLOCKED 4 ?????? STATE_COMPLETE 2 ?????? STATE_ERROR 3 ?????? STATE_NONE -1
		 * ????????? STATE_NORMAL 0 ?????? STATE_PAUSED 1 ??????
		 * ??????quartz??????????????????NONE????????????????????????job???????????????job
		 */
		if (status.equals("NONE")) {
			// ????????????job
			if (concurrent.equals("1")) {
				QuartzManager.addJob(String.valueOf(scheduleId), QuartzJob.class, time, requestHistoryBean,
						allocInstanceMapper, baseDao2);
			} else {
				QuartzManager.addJob(String.valueOf(scheduleId), QuartzStatefulJob.class, time, requestHistoryBean,
						allocInstanceMapper, baseDao2);
			}
			// String.valueOf(scheduleId)????????????QuartzJob.class????????????????????????time
			// ??????cron?????????????????????
		} else {
			// ????????????Job
			if (concurrent.equals("1")) {
				QuartzManager.modifyJobTime(String.valueOf(scheduleId), QuartzJob.class, time, requestHistoryBean,
						allocInstanceMapper, baseDao2);
			} else {
				QuartzManager.modifyJobTime(String.valueOf(scheduleId), QuartzStatefulJob.class, time,
						requestHistoryBean, allocInstanceMapper, baseDao2);
			}
		}
	}

	@Override
	public void calculateAllocJobForInstacneHistory(AllocRequestInstanceHistoryRecord requestHistoryBean)
			throws Exception {
		// get content and calculation items
		boolean isError = false;
		String errorMsg = "";
		String returnvalue = "";
		long startTime = System.currentTimeMillis();
		try {
			AllocRequestInstanceHistoryRecord queryBean = new AllocRequestInstanceHistoryRecord();
			requestHistoryBean.setStatus(JobStatus.PROCESSING);
			int processingCount = this.getAllocRequestInstanceHistoryRecordCount(requestHistoryBean);
			requestHistoryBean.setStatus(JobStatus.ROLLBACKING);
			int rollbackingCount = this.getAllocRequestInstanceHistoryRecordCount(requestHistoryBean);
			int runningCount = processingCount + rollbackingCount;
			if (0 < runningCount) {
				throw new DuplicateJobRunningException();
			} else {
				// set job instance status
				requestHistoryBean.setStatus(JobStatus.PROCESSING);
				requestHistoryBean.setStartTime(new Date());
				requestHistoryBean.setMessage("");
				allocInstanceMapper.updateAllocRequestInstanceHistoryRecord(requestHistoryBean);

				// Execute content
				// CalculationDao calDao = new CalculationDao();
				// this.calculationDao.generateFinIndexAlloc(requestBean);
				// get item constant list

				Map map = new HashMap();
				map.put("returnvalue", "");
				map.put("historyId", requestHistoryBean.getHistoryId());
				allocInstanceMapper.getItemContentsAlloc(map);
				returnvalue = (String) map.get("returnvalue");
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
			errorMsg = "?????????????????????????????????????????????????????????????????????";
			requestHistoryBean.setStatus("ERROR");
			allocInstanceMapper.updateAllocRequestInstanceHistoryRecord(requestHistoryBean);
			throw de;
		} catch (Exception e) {
			isError = true;
			errorMsg = e.toString();
			requestHistoryBean.setStatus("ERROR");
			allocInstanceMapper.updateAllocRequestInstanceHistoryRecord(requestHistoryBean);
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
			requestHistoryBean.setStatus(allocInstanceMapper.getStatus(null, JobStatus.FAILED));
			requestHistoryBean.setMessage(errorMsg);
		} else {
			requestHistoryBean.setStatus(allocInstanceMapper.getStatus(null, JobStatus.SUCCESS));
		}
		requestHistoryBean.setStatus(returnvalue);
		requestHistoryBean.setEndTime(new Date());
		requestHistoryBean.setElapsedTime(System.currentTimeMillis() - startTime);
		allocInstanceMapper.updateAllocRequestInstanceHistoryRecord(requestHistoryBean);

	}

	public int getAllocRequestInstanceHistoryRecordCount(AllocRequestInstanceHistoryRecord queryBean) throws Exception {
		if (queryBean == null) {
			throw new Exception("no job request bean found");
		}
		int result = 0;
		result = allocInstanceMapper.getAllocRequestInstanceHistoryRecordCount(String.valueOf(queryBean.getInstanceId()), queryBean.getStatus());
		return result;
	}

	@Override
	public long saveAllocRequestHistoryInstance(AllocRequestInstanceHistoryRecord requestHistoryBean) throws Exception {
		if (requestHistoryBean == null) {
			throw new Exception("The job bean is null.");
		}
		long primaryKey = baseDao2.getAutoGeneratedPrimaryKey("HAE_ALLOC_INSTANCE_HISTORY_S");
		requestHistoryBean.setHistoryId(primaryKey);
		allocInstanceMapper.saveAllocRequestInstanceHistoryRecord(requestHistoryBean);
		return primaryKey;
	}

	@Override
	public List<Map<String, Object>> findAllocInstanceHistory(AllocRequestInstanceHistoryRecord requestBean)
			throws Exception {
		Map<String, Object> map = null;
		List<Map<String, Object>> result = new ArrayList();
		List<AllocRequestInstanceHistoryRecord> result1 = null;
		String start = String.valueOf(requestBean.getRowStartIndex());
		String count = String.valueOf(requestBean.getRowCount());
		result1 = allocInstanceMapper.findAllocInstanceHistory(start, String.valueOf(requestBean.getInstanceId()),
				count);
		for (int i = 0; i < result1.size(); i++) {
			map = new HashMap<String, Object>();
			map.put("INSTANCE_ID", result1.get(i).getInstanceId());
			map.put("RULE_ID", result1.get(i).getRuleId());
			map.put("HISTORY_ID", result1.get(i).getHistoryId());
			map.put("PERIOD", result1.get(i).getPeriod());
			map.put("GROUP_HEADER_ID", result1.get(i).getGroupHeaderId());
			map.put("START_TIME", result1.get(i).getStartTime());
			map.put("END_TIME", result1.get(i).getEndTime());
			map.put("ELAPSED_TIME", result1.get(i).getElapsedTime());
			map.put("STATUS", result1.get(i).getStatus());
			map.put("MESSAGE", result1.get(i).getMessage());
			map.put("ROLLBACK_TIME", result1.get(i).getRollbackTime());
			map.put("ATTRIBUTE1", result1.get(i).getAttribute1());
			map.put("ATTRIBUTE2", result1.get(i).getAttribute2());
			map.put("ATTRIBUTE3", result1.get(i).getAttribute3());
			map.put("ATTRIBUTE4", result1.get(i).getAttribute4());
			map.put("ATTRIBUTE5", result1.get(i).getAttribute5());
			map.put("CREATION_DATE", result1.get(i).getCreationDate());
			map.put("CREATED_BY", result1.get(i).getCreatedBy());
			map.put("LAST_UPDATE_DATE", result1.get(i).getLastUpdateDate());
			map.put("LAST_UPDATED_BY", result1.get(i).getLastUpdatedBy());
			result.add(map);
		}
		return result;
	}

	@Override
	public int getCountAllocInstanceHistory(AllocRequestInstanceHistoryRecord requestBean) throws Exception {
		int result = 0;
		try {
			result = allocInstanceMapper
					.getAllocRequestInstanceHistoryRecordCount(String.valueOf(requestBean.getInstanceId()), null);
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
		result = allocInstanceMapper.getAllocRequestInstanceHistoryByPrimaryKey(requestBean.getHistoryId());
		return result;
	}

	@Override
	public void calculateRollbackAllocJobForInstacneHistory(AllocRequestInstanceHistoryRecord requestHistoryBean)
			throws Exception {
		// get content and calculation items
		boolean isError = false;
		String errorMsg = "";
		long startTime = System.currentTimeMillis();
		String returnvalue = "";
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
			allocInstanceMapper.updateAllocRequestInstanceHistoryRecord(requestHistoryBean);

			Map map = new HashMap();
			map.put("returnvalue", "");
			map.put("historyId", requestHistoryBean.getHistoryId());
			allocInstanceMapper.getRollbackItemContentsAlloc(map);
			returnvalue = (String) map.get("returnvalue");

			/*
			 * if (0 < contentList.size()) { //calculation in the group
			 * calculationResult = this.calculateInGroup(contentList); }
			 */
			if (!"R101".equals(returnvalue)) {
				isError = true;
			}
		} catch (DuplicateJobRunningException de) {
			isError = true;
			errorMsg = "?????????????????????????????????????????????????????????????????????";
			requestHistoryBean.setStatus("R102");
			allocInstanceMapper.updateAllocRequestInstanceHistoryRecord(requestHistoryBean);
			throw de;
		} catch (Exception e) {
			isError = true;
			errorMsg = e.toString();
			System.out.println("Error happens when calculation: " + e);
			requestHistoryBean.setStatus("R102");
			allocInstanceMapper.updateAllocRequestInstanceHistoryRecord(requestHistoryBean);
		}

		// if there's result , then insert result into db
		/*
		 * if (calculationResult != null && !calculationResult.isEmpty()) { try
		 * { calculationDao.insertCalculationItems(calculationResult,
		 * requestBean.getRequestUser(), requestBean.getInstanceId()); } catch
		 * (SQLException e) { isError = true; errorMsg = e.toString(); } }
		 */

		if (isError) {
			requestHistoryBean.setStatus(allocInstanceMapper.getStatus(null, JobStatus.ROLLBACKFAILED));
			requestHistoryBean.setMessage(errorMsg);
		} else {
			requestHistoryBean.setStatus(allocInstanceMapper.getStatus(null, JobStatus.ROLLBACKSUCCESS));
		}
		// update for use result to give status
		requestHistoryBean.setStatus(returnvalue);
		requestHistoryBean.setEndTime(new Date());
		requestHistoryBean.setElapsedTime(System.currentTimeMillis() - startTime);
		requestHistoryBean.setRollbackTime(new Date());
		allocInstanceMapper.updateAllocRequestInstanceHistoryRecord(requestHistoryBean);

	}

	@Override
	public List<Long> getruleListForLoop(AllocRequestInstanceRecord requestBean) throws Exception {

		return allocInstanceMapper.getruleListForLoop(requestBean.getRuleId());
	}
	/*
	 * C_LOG_STATE_WARNING1 CONSTANT VARCHAR2(50) := 'W101'; C_LOG_MSG_WARNING1
	 * CONSTANT VARCHAR2(200) := '??????:???????????????'; C_LOG_STATE_WARNING2 CONSTANT
	 * VARCHAR2(50) := 'W102'; C_LOG_MSG_WARNING2 CONSTANT VARCHAR2(200) :=
	 * '??????:?????????????????????????????????'; C_LOG_STATE_WARNING3 CONSTANT VARCHAR2(50) := 'W103';
	 * C_LOG_MSG_WARNING3 CONSTANT VARCHAR2(200) := '??????:????????????????????????0';
	 * C_LOG_STATE_WARNING4 CONSTANT VARCHAR2(50) := 'W104'; C_LOG_MSG_WARNING4
	 * CONSTANT VARCHAR2(200) := '??????:???????????????????????????????????????????????????'; C_LOG_STATE_WARNING5
	 * CONSTANT VARCHAR2(50) := 'W105'; C_LOG_MSG_WARNING5 CONSTANT
	 * VARCHAR2(200) := '??????:???????????????'; C_LOG_STATE_ERROR CONSTANT VARCHAR2(50) :=
	 * 'E101'; C_LOG_MSG_ERROR CONSTANT VARCHAR2(200) := '??????????????????';
	 * C_LOG_STATE_SUCCESS CONSTANT VARCHAR2(50) := 'S101'; C_LOG_MSG_SUCCESS
	 * CONSTANT VARCHAR2(200) := '??????????????????';
	 * 
	 * 
	 * 
	 * 
	 * C_LOG_STATE_ROLLBACK CONSTANT VARCHAR2(50) := 'R101'; C_LOG_MSG_ROLLBACK
	 * CONSTANT VARCHAR2(200) := '???????????????'; C_LOG_STATE_ROLLBACKERROR CONSTANT
	 * VARCHAR2(50) := 'R102'; C_LOG_MSG_ROLLBACKERROR CONSTANT VARCHAR2(200) :=
	 * '??????????????????';
	 */

	@Override
	public String getStatusId(String scheduled) throws Exception {
		String statusId = allocInstanceMapper.getStatus(null, scheduled);
		return statusId;
	}

	private void addConstantInGroup(List<CalculationProcessorBean> targetList, CalculationProcessorBean constantBean) {
		if (targetList != null && 0 < targetList.size()) {
			CalculationProcessorBean contentRecord = targetList.get(0);
			if (constantBean.isContantBelongSameGroup(contentRecord)) {
				targetList.add(constantBean);
			}
		}
	}

	private CalculationProcessorBean createCalProcessorBean(ResultSet rs) throws SQLException {
		CalculationProcessorBean result = new CalculationProcessorBean();
		//
		int col = 1;
		// set item rule code
		result.setRuleCode(rs.getString(col++));
		// set report item code
		result.setItemCode(rs.getString(col++));
		// set seq number
		result.setLineNum(rs.getInt(col++));
		// set operator
		result.setSign(rs.getString(col++));
		// set op iterm code
		result.setCalItemCode(rs.getString(col++));
		// set legerId
		result.setLedgerId(rs.getLong(col++));
		// set actual flag
		result.setActualFlag(rs.getString(col++));
		// set currency code
		result.setCurrencyCode(rs.getString(col++));
		// set currency type
		result.setCurrencyType(rs.getString(col++));
		// set element
		result.setFinElement(rs.getString(col++));
		// set period name
		result.setPeriodName(rs.getString(col++));
		// set period year
		result.setPeriodYear(rs.getString(col++));
		// set period number
		result.setPeriodNumber(rs.getString(col++));
		// set segment 1
		result.setSegment1(rs.getString(col++));
		// set segment 2
		result.setSegment2(rs.getString(col++));
		// set segment 3
		result.setSegment3(rs.getString(col++));
		// set segment 4
		result.setSegment4(rs.getString(col++));
		// set segment 5
		result.setSegment5(rs.getString(col++));
		// set segment 6
		result.setSegment6(rs.getString(col++));
		// set segment 7
		result.setSegment7(rs.getString(col++));
		// set segment 8
		result.setSegment8(rs.getString(col++));
		// set segment 9
		result.setSegment9(rs.getString(col++));
		// set segment 10
		result.setSegment10(rs.getString(col++));
		// set segment 11
		result.setSegment11(rs.getString(col++));
		// set segment 12
		result.setSegment12(rs.getString(col++));
		// set segment 13
		result.setSegment13(rs.getString(col++));
		// set segment 14
		result.setSegment14(rs.getString(col++));
		// set segment 15
		result.setSegment15(rs.getString(col++));
		// set segment 16
		result.setSegment16(rs.getString(col++));
		// set segment 17
		result.setSegment17(rs.getString(col++));
		// set segment 18
		result.setSegment18(rs.getString(col++));
		// set segment 19
		result.setSegment19(rs.getString(col++));
		// set segment 20
		result.setSegment20(rs.getString(col++));

		// set begin balance dr
		result.setBeginBalanceDR(rs.getDouble(col++));
		// set begin balance cr
		result.setBeginBalanceCR(rs.getDouble(col++));
		// set period net dr
		result.setPeriodDR(rs.getDouble(col++));
		// set period net cr
		result.setPeriodCR(rs.getDouble(col++));
		// set end balance dr
		result.setEndBalanceDR(rs.getDouble(col++));
		// set end balance cr
		result.setEndBalanceCR(rs.getDouble(col++));

		return result;
	}

	private void setInsertParameters(HrsStatement stmt, CalculationProcessorBean calBean) throws SQLException {
		int i = stmt.getStartParamIndex();
		// set items code
		stmt.setString(i++, calBean.getItemCode());
		// set item group code
		stmt.setString(i++, calBean.getRuleCode());
		// set item financial element
		stmt.setString(i++, calBean.getFinElement());
		// set ledger id
		stmt.setLong(i++, calBean.getLedgerId());
		// set actual flag
		stmt.setString(i++, calBean.getActualFlag());
		// set currency type
		stmt.setString(i++, calBean.getCurrencyType());
		// set currency code
		stmt.setString(i++, calBean.getCurrencyCode());
		// set segment 1
		stmt.setString(i++, calBean.getSegment1());
		// set segment 2
		stmt.setString(i++, calBean.getSegment2());
		// set segment 3
		stmt.setString(i++, calBean.getSegment3());
		// set segment 4
		stmt.setString(i++, calBean.getSegment4());
		// set segment 5
		stmt.setString(i++, calBean.getSegment5());
		// set segment 6
		stmt.setString(i++, calBean.getSegment6());
		// set segment 7
		stmt.setString(i++, calBean.getSegment7());
		// set segment 8
		stmt.setString(i++, calBean.getSegment8());
		// set segment 9
		stmt.setString(i++, calBean.getSegment9());
		// set segment 10
		stmt.setString(i++, calBean.getSegment10());
		// set segment 11
		stmt.setString(i++, calBean.getSegment11());
		// set segment 12
		stmt.setString(i++, calBean.getSegment12());
		// set segment 13
		stmt.setString(i++, calBean.getSegment13());
		// set segment 14
		stmt.setString(i++, calBean.getSegment14());
		// set segment 15
		stmt.setString(i++, calBean.getSegment15());
		// set segment 16
		stmt.setString(i++, calBean.getSegment16());
		// set segment 17
		stmt.setString(i++, calBean.getSegment17());
		// set segment 18
		stmt.setString(i++, calBean.getSegment18());
		// set segment 19
		stmt.setString(i++, calBean.getSegment19());
		// set segment 20
		stmt.setString(i++, calBean.getSegment20());
		// set period name
		stmt.setString(i++, calBean.getPeriodName());
		// set period number
		stmt.setString(i++, calBean.getPeriodNumber());
		// set period year
		stmt.setString(i++, calBean.getPeriodYear());
		// set begin balance dr
		stmt.setDouble(i++,
				new BigDecimal(calBean.getBeginBalanceDR()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		// set begin balance cr
		stmt.setDouble(i++,
				new BigDecimal(calBean.getBeginBalanceCR()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		// set PERIOD_NET_DR
		stmt.setDouble(i++, new BigDecimal(calBean.getPeriodDR()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		// set PERIOD_NET_CR
		stmt.setDouble(i++, new BigDecimal(calBean.getPeriodCR()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		// set end balance dr
		stmt.setDouble(i++,
				new BigDecimal(calBean.getEndBalanceDR()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		// set end balance cr
		stmt.setDouble(i++,
				new BigDecimal(calBean.getEndBalanceCR()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		// set request instance Id
		stmt.setLong(i++, calBean.getRequestInstanceId());
		// set create date
		stmt.setDate(i++, DateUtil.toSqlDate(new Date()));
		// set created by user
		stmt.setString(i++, calBean.getCreatedBy());
		// set update
		stmt.setDate(i++, DateUtil.toSqlDate(new Date()));
		// set updated by user
		stmt.setString(i++, calBean.getUpdatedBy());
	}

}
