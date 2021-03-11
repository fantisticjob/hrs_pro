package com.hausontech.hrs.daoImpl.dataPorcessing;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.hausontech.hrs.api.dataProcessing.dao.IDataProcessDao;
import com.hausontech.hrs.bean.AuditBean;
import com.hausontech.hrs.bean.dataProcess.AllocRequestInstanceBean;
import com.hausontech.hrs.bean.dataProcess.AllocRequestInstanceHistoryRecord;
import com.hausontech.hrs.bean.dataProcess.AllocRequestInstanceRecord;
import com.hausontech.hrs.bean.dataProcess.LedgerBean;
import com.hausontech.hrs.bean.dataProcess.PeriodBean;
import com.hausontech.hrs.bean.dataProcess.RequestInstanceBean;
import com.hausontech.hrs.bean.transactionProcess.TransactionProcessTypeBean;
import com.hausontech.hrs.daoImpl.BaseDaoImpl;
import com.hausontech.hrs.daoImpl.dataPorcessing.mapper.AllocBatchReqInstanceMapper;
import com.hausontech.hrs.daoImpl.dataPorcessing.mapper.AllocBatchReqInstanceRowMapper;
import com.hausontech.hrs.daoImpl.dataPorcessing.mapper.AllocRequestInstanceHistoryMapper;
import com.hausontech.hrs.daoImpl.dataPorcessing.mapper.BatchReqInstanceRowMapper;
import com.hausontech.hrs.daoImpl.dataPorcessing.mapper.LedgerRowMapper;
import com.hausontech.hrs.daoImpl.dataPorcessing.mapper.PeriodRowMapper;
import com.hausontech.hrs.daoImpl.dataPorcessing.mapper.TnxProcessTypeMapper;

public class DataProcessDaoImpl extends BaseDaoImpl implements IDataProcessDao {

	@Override
	public List<PeriodBean> getPeriodList() throws SQLException {
		StringBuffer sql = new StringBuffer();
		List<PeriodBean> result = null;
		try {
			sql.append("SELECT ");
			sql.append(" PERIOD_NAME, PERIOD_YEAR, PERIOD_NUM, QUARTER_NUM, ADJ_FLAG " + this.AUDIT_QUERY_STRING);
			sql.append(" FROM HRS_CORE_FIN_PERIOD ");
			sql.append(" ORDER BY PERIOD_NAME DESC");
			result = namedjdbcTemplate.getJdbcOperations().query(sql.toString(), new PeriodRowMapper());
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return result;
	}
	
	@Override
	public List<LedgerBean> getLedgerList() throws SQLException {
		StringBuffer sql = new StringBuffer();
		List<LedgerBean> result = null;
		try {
			sql.append("SELECT lookupVal.LOOKUP_VALUE, lookupVal.DESCRIPTION");
			sql.append(" FROM HRS_CORE_LOOKUP_TYPE lookupType, HRS_CORE_LOOKUP_VALUE lookupVal ");
			sql.append(" where lookupType.Lookup_Type_Id = lookupVal.Lookup_Type_Id and lookupType.Lookup_Type='HRS_CORE_LEDGER'");
			sql.append(" ORDER BY lookupVal.Lookup_Value");
			result = namedjdbcTemplate.getJdbcOperations().query(sql.toString(), new LedgerRowMapper());
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return result;
	}
	
	@Override
	public List<Map<String, Object>> getLedgerListMapForDisplay() throws SQLException {
		StringBuffer sql = new StringBuffer();
		List<Map<String, Object>> result = null;
		try {
			sql.append("SELECT lookupVal.LOOKUP_VALUE ledgerId, lookupVal.DESCRIPTION ledgerName");
			sql.append(" FROM HRS_CORE_LOOKUP_TYPE lookupType, HRS_CORE_LOOKUP_VALUE lookupVal ");
			sql.append(" where lookupType.Lookup_Type_Id = lookupVal.Lookup_Type_Id and lookupType.Lookup_Type='HRS_CORE_LEDGER'");
			sql.append(" ORDER BY ledgerId");
			result = namedjdbcTemplate.getJdbcOperations().queryForList(sql.toString());
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return result;
	}
	
	@Override
	public List<Map<String, Object>> getFinElementMapForDisplay() throws SQLException {
		StringBuffer sql = new StringBuffer();
		List<Map<String, Object>> result = null;
		try {
			sql.append("SELECT lookupVal.LOOKUP_VALUE finEleCode, lookupVal.DESCRIPTION finEleName");
			sql.append(" FROM HRS_CORE_LOOKUP_TYPE lookupType, HRS_CORE_LOOKUP_VALUE lookupVal ");
			sql.append(" where lookupType.Lookup_Type_Id = lookupVal.Lookup_Type_Id and lookupType.Lookup_Type='HRS_CORE_FIN_ELEMENT'");
			sql.append(" ORDER BY finEleCode");
			result = namedjdbcTemplate.getJdbcOperations().queryForList(sql.toString());
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return result;
	}
	
	@Override
	public List<Map<String, Object>> getPeriodListMapForDisplay() throws SQLException {
		StringBuffer sql = new StringBuffer();
		List<Map<String, Object>> result = null;
		try {
			sql.append("SELECT ");
			sql.append(" PERIOD_NAME periodId, PERIOD_NAME periodName ");
			sql.append(" FROM HRS_CORE_FIN_PERIOD ");
			sql.append(" ORDER BY periodId DESC");
			result = namedjdbcTemplate.getJdbcOperations().queryForList(sql.toString());
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return result;
	}
	
	public List<Map<String, Object>> getRequestInstanceList(RequestInstanceBean requestBean) throws SQLException {
		String strCondition = buildCondition(requestBean);
		StringBuffer sql = new StringBuffer();
		List<Map<String, Object>> list = null;
		try {
			sql.append("SELECT ");
			sql.append(" INSTANCE_ID, LEDGER_ID, FIN_ELEMENT_TYPE, PERIOD_FROM, PERIOD_TO,START_TIME, END_TIME, ELAPSED_TIME, STATUS, MESSAGE " + AUDIT_QUERY_STRING);
			if (requestBean.getRowStartIndex() != 0 && requestBean.getRowCount() != 0) {
				sql.append(", ROW_NUMBER() OVER (ORDER BY INSTANCE_ID DESC) AS row_number");
			}
			sql.append(" FROM HRS_CORE_REQUEST_INSTANCE ");
			if (null != strCondition && strCondition.length() > 0) {
				sql.append(" WHERE " + strCondition);
			}
			sql.append(" ORDER BY INSTANCE_ID DESC");
			if (requestBean.getRowStartIndex() != 0 && requestBean.getRowCount() != 0) {
				sql = this.pageSql(sql, requestBean.getRowStartIndex(), requestBean.getRowCount());
			}
			list = namedjdbcTemplate.getJdbcOperations().queryForList(sql.toString());
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return list;
	}
	
	@Override
	public RequestInstanceBean getRequestInstanceByPrimaryKey(RequestInstanceBean requestBean) throws SQLException {
		StringBuffer sql = new StringBuffer();
		RequestInstanceBean result = null;
		try {
			sql.append("SELECT ");
			sql.append(" INSTANCE_ID, LEDGER_ID, FIN_ELEMENT_TYPE, PERIOD_FROM, PERIOD_TO,START_TIME, END_TIME, ELAPSED_TIME, STATUS, MESSAGE " + AUDIT_QUERY_STRING);
			sql.append(" FROM HRS_CORE_REQUEST_INSTANCE ");
			sql.append(" WHERE INSTANCE_ID = " + requestBean.getInstanceId());
			result = (RequestInstanceBean)namedjdbcTemplate.getJdbcOperations().queryForObject(sql.toString(), new BatchReqInstanceRowMapper());
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return result;
	}
	
	
	@Override
	public List<TransactionProcessTypeBean> getTransationProcessTypeList() throws SQLException {
		StringBuffer sql = new StringBuffer();
		List<TransactionProcessTypeBean> result = null;
		try {
			sql.append("SELECT LV.LOOKUP_VALUE,LV.DESCRIPTION FROM HRS_CORE_LOOKUP_TYPE  LT,"
					+ "HRS_CORE_LOOKUP_VALUE LV WHERE LT.LOOKUP_TYPE='HRS_CORE_TXN_TYPE' AND LV.LOOKUP_TYPE_ID"
					+ "=LT.LOOKUP_TYPE_ID");
			result = namedjdbcTemplate.getJdbcOperations().query(sql.toString(), new TnxProcessTypeMapper());
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return result;
	}

	@Override
	public int getBatchInstanceNumber(RequestInstanceBean requestBean) throws SQLException {
		try {
			String strCondition = buildCondition(requestBean);
			StringBuffer sql = new StringBuffer();
			sql.append(" select count(1) from HRS_CORE_REQUEST_INSTANCE ");
			if (null != strCondition && strCondition.length() > 0) {
				sql.append(" where " + strCondition);
			}
			return (int)namedjdbcTemplate.getJdbcOperations().queryForObject(sql.toString(), int.class);
		} catch (Exception e) {
			throw new SQLException(e);
		}
	}
	
	@Override
	public long saveBatchJobRecord(RequestInstanceBean jobRecord) throws SQLException {
		long primaryKey = 0;
		try {
			//get primary key from sequence
			primaryKey = this.getAutoGeneratedPrimaryKey("HRS_CORE_REQUEST_INSTANCE_S");
			jobRecord.setInstanceId(primaryKey);
			String sql = "INSERT INTO HRS_CORE_REQUEST_INSTANCE(INSTANCE_ID, LEDGER_ID, FIN_ELEMENT_TYPE, PERIOD_FROM, PERIOD_TO, START_TIME, STATUS " + this.AUDIT_QUERY_STRING + ")" +
			 		      " VALUES(:instanceId, :ledgerId, :finElementType, :periodFrom, :periodTo, :startDate, :status, " +
		                          ":creationDate, :createdBy, :lastUpdateDate, :lastUpdatedBy)";				
			SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(jobRecord);
			//GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		    this.namedjdbcTemplate.update(sql, namedParameters);//, generatedKeyHolder, new String[]{"DIMENSION_ID"});		    
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return primaryKey;
	}
	
	@Override
	public int updateBatchJobRecord(RequestInstanceBean record) throws SQLException {
		int updateNum = 0;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE HRS_CORE_REQUEST_INSTANCE SET");
			sql.append(" LEDGER_ID = NVL(:ledgerId, LEDGER_ID),");
			sql.append(" FIN_ELEMENT_TYPE = NVL(:finElementType, FIN_ELEMENT_TYPE),");
			sql.append(" PERIOD_FROM = NVL(:periodFrom, PERIOD_FROM),");
			sql.append(" PERIOD_TO = NVL(:periodTo, PERIOD_TO),");
			sql.append(" START_TIME = NVL(:startDate, START_TIME),");
			sql.append(" END_TIME = NVL(:endDate, END_TIME),");
			sql.append(" ELAPSED_TIME = NVL(:elapsedTime, ELAPSED_TIME),");
			sql.append(" STATUS = NVL(:status, STATUS),");
			sql.append(" MESSAGE = :message,");
			
			sql.append(" LAST_UPDATE_DATE = NVL(:lastUpdateDate,LAST_UPDATE_DATE),");
			sql.append(" LAST_UPDATED_BY = NVL(:lastUpdatedBy,LAST_UPDATED_BY)");
			sql.append(" WHERE INSTANCE_ID = :instanceId");

			SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(record);
			// GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
			updateNum = this.namedjdbcTemplate.update(sql.toString(), namedParameters);
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return updateNum;
	}
	
	@Override
	public int deleteBatchJobRecord(RequestInstanceBean record) throws SQLException {
		int delNum = 0;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("DELETE HRS_CORE_REQUEST_INSTANCE");
			sql.append(" WHERE INSTANCE_ID = :instanceId");

			SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(record);
			// GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
			delNum = this.namedjdbcTemplate.update(sql.toString(), namedParameters);// ,																	// String[]{"DIMENSION_ID"});
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return delNum;
	}
	
	public long getRequestInstanceId() throws SQLException {
		return this.getAutoGeneratedPrimaryKey("HRS_CORE_REQUEST_INSTANCE_S");
	}
	
	private String buildCondition(AuditBean condition) {		
		StringBuffer sb = new StringBuffer();
		if (condition != null) {
			if (condition instanceof RequestInstanceBean) {
				RequestInstanceBean searchBean = (RequestInstanceBean)condition;
				if(null != searchBean && searchBean.getInstanceId() != 0){
					sb.append("INSTANCE_ID="+ searchBean.getInstanceId() +" ");
					return sb.toString();
				}
						
				//前台页面 摸人加载数据也是 默认触发查询按钮产生的，所以，查询条件肯定是有的了。
				if(null != searchBean && !searchBean.isEmpty()) {
					int ledgerId = searchBean.getLedgerId();
					if(ledgerId != 0){
						sb.append(" LEDGER_ID = "+ ledgerId + " ");	
					}
					
					String finEleType = searchBean.getFinElementType();
					if(null != finEleType && !finEleType.isEmpty()){
						if(sb.length()>0) {
							sb.append(" and ");
						}
						sb.append(" FIN_ELEMENT_TYPE = '"+ finEleType + "' ");	
					}
					
					String periodFrom = searchBean.getPeriodFrom();
					if(null != periodFrom && !periodFrom.isEmpty()){
						if(sb.length()>0) {
							sb.append(" and ");
						}
						sb.append(" (PERIOD_FROM = '"+ periodFrom + "' ");	
					}
					
					String periodTo = searchBean.getPeriodTo();
					if(null != periodTo && !periodTo.isEmpty()){
						if(sb.length()>0) {
							sb.append(" and ");
						}
						sb.append(" (PERIOD_TO = '"+ periodTo + "' ");	
					}
					
					String status = searchBean.getStatus();
					if(null != status && !status.isEmpty()){
						if(sb.length()>0) {
							sb.append(" and ");
						}
						sb.append(" STATUS = '"+ status + "' ");	
					}				
				}
			} 
			if (condition instanceof AllocRequestInstanceBean) {
				AllocRequestInstanceBean searchBean = (AllocRequestInstanceBean)condition;
				if(null != searchBean && searchBean.getInstanceId() != 0){
					sb.append("INSTANCE_ID="+ searchBean.getInstanceId() +" ");
					return sb.toString();
				}
						
				//前台页面 摸人加载数据也是 默认触发查询按钮产生的，所以，查询条件肯定是有的了。
				if(null != searchBean && !searchBean.isEmpty()) {
					long ruleId = searchBean.getRuleId();
					if(ruleId != 0){
						sb.append(" RULE_ID = "+ ruleId + " ");	
					}
					
					String RuleType = searchBean.getRuleType();
					if(null != RuleType && !RuleType.isEmpty()){
						if(sb.length()>0) {
							sb.append(" and ");
						}
						sb.append(" RULE_TYPE = '"+ RuleType + "' ");	
					}
					
					String period = searchBean.getPeriod();
					if(null != period && !period.isEmpty()){
						if(sb.length()>0) {
							sb.append(" and ");
						}
						sb.append(" (PERIOD = '"+ period + "' ");	
					}
					

					
					String status = searchBean.getStatus();
					if(null != status && !status.isEmpty()){
						if(sb.length()>0) {
							sb.append(" and ");
						}
						sb.append(" STATUS = '"+ status + "' ");	
					}				
				}
			} 
			if (condition instanceof AllocRequestInstanceRecord) {
				AllocRequestInstanceRecord searchBean = (AllocRequestInstanceRecord)condition;
				if(null != searchBean && searchBean.getInstanceId() != 0){
					sb.append("INSTANCE_ID="+ searchBean.getInstanceId() +" ");
					return sb.toString();
				}
						
				//前台页面 摸人加载数据也是 默认触发查询按钮产生的，所以，查询条件肯定是有的了。
				if(null != searchBean && !searchBean.isEmpty()) {
					long ruleId = searchBean.getRuleId();
					if(ruleId != 0){
						sb.append(" RULE_ID = "+ ruleId + " ");	
					}
					
					String RuleType = searchBean.getRuleType();
					if(null != RuleType && !RuleType.isEmpty()){
						if(sb.length()>0) {
							sb.append(" and ");
						}
						sb.append(" RULE_TYPE = '"+ RuleType + "' ");	
					}
					
					String period = searchBean.getPeriod();
					if(null != period && !period.isEmpty()){
						if(sb.length()>0) {
							sb.append(" and ");
						}
						sb.append(" (PERIOD = '"+ period + "' ");	
					}
						
				}
			} 
			if (condition instanceof AllocRequestInstanceHistoryRecord) {
				AllocRequestInstanceHistoryRecord searchBean = (AllocRequestInstanceHistoryRecord)condition;
				if(null != searchBean && searchBean.getInstanceId() != 0){
					sb.append("INSTANCE_ID="+ searchBean.getInstanceId() +" ");
					return sb.toString();
				}
						

				if(null != searchBean ) {
					long ruleId = searchBean.getRuleId();
					if(ruleId != 0){
						sb.append(" RULE_ID = "+ ruleId + " ");	
					}
					
					
					String period = searchBean.getPeriod();
					if(null != period && !period.isEmpty()){
						if(sb.length()>0) {
							sb.append(" and ");
						}
						sb.append(" (PERIOD = '"+ period + "' ");	
					}
					String status = searchBean.getStatus();
					if(null != status && !status.isEmpty()){
						if(sb.length()>0) {
							sb.append(" and ");
						}
						sb.append(" STATUS = '"+ status + "' ");	
					}					
				}
			} 
			
		}
		return sb.toString();
	}

	@Override
	public int getAllocBatchInstanceNumber(AllocRequestInstanceBean jobInsBean) throws SQLException {
		try {
			String strCondition = buildCondition(jobInsBean);
			StringBuffer sql = new StringBuffer();
			sql.append(" select count(1) from HRS_CORE_A_REQUEST_INSTANCE ");
			if (null != strCondition && strCondition.length() > 0) {
				sql.append(" where " + strCondition);
			}
			return (int)namedjdbcTemplate.getJdbcOperations().queryForObject(sql.toString(), int.class);
		} catch (Exception e) {
			throw new SQLException(e);
		}
	}

	@Override
	public List<Map<String, Object>> getAllocRequestInstanceList(AllocRequestInstanceBean requestBean)
			throws SQLException {
		String strCondition = buildCondition(requestBean);
		StringBuffer sql = new StringBuffer();
		List<Map<String, Object>> list = null;
		try {
			sql.append("SELECT ");
			sql.append(" INSTANCE_ID,RULE_ID, RULE_TYPE , PERIOD,START_TIME, END_TIME, ELAPSED_TIME, STATUS, MESSAGE " + AUDIT_QUERY_STRING);
			if (requestBean.getRowStartIndex() != 0 && requestBean.getRowCount() != 0) {
				sql.append(", ROW_NUMBER() OVER (ORDER BY INSTANCE_ID DESC) AS row_number");
			}
			sql.append(" FROM HRS_CORE_A_REQUEST_INSTANCE ");
			if (null != strCondition && strCondition.length() > 0) {
				sql.append(" WHERE " + strCondition);
			}
			sql.append(" ORDER BY INSTANCE_ID DESC");
			if (requestBean.getRowStartIndex() != 0 && requestBean.getRowCount() != 0) {
				sql = this.pageSql(sql, requestBean.getRowStartIndex(), requestBean.getRowCount());
			}
			list = namedjdbcTemplate.getJdbcOperations().queryForList(sql.toString());
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return list;
	}

	@Override
	public long saveAllocBatchJobRecord(AllocRequestInstanceBean jobRecord) throws SQLException {
		long primaryKey = 0;
		try {
			//get primary key from sequence
			primaryKey = this.getAutoGeneratedPrimaryKey("HRS_CORE_A_REQUEST_INSTANCE_S");
			jobRecord.setInstanceId(primaryKey);
			String sql = "INSERT INTO HRS_CORE_A_REQUEST_INSTANCE(INSTANCE_ID,RULE_ID, RULE_TYPE , PERIOD, START_TIME, STATUS " + this.AUDIT_QUERY_STRING + ")" +
			 		      " VALUES(:instanceId, :ruleId, :ruleType, :period, :startDate, :status, " +
		                          ":creationDate, :createdBy, :lastUpdateDate, :lastUpdatedBy)";				
			SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(jobRecord);
			//GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		    this.namedjdbcTemplate.update(sql, namedParameters);//, generatedKeyHolder, new String[]{"DIMENSION_ID"});		    
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return primaryKey;
	}

	@Override
	public int updateAllocBatchJobRecord(AllocRequestInstanceBean record) throws SQLException {
		int updateNum = 0;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE HRS_CORE_A_REQUEST_INSTANCE SET");
			sql.append(" RULE_ID = NVL(:ruleId, RULE_ID),");
			sql.append(" RULE_TYPE = NVL(:ruleType, RULE_TYPE),");
			sql.append(" PERIOD = NVL(:period, PERIOD),");
			sql.append(" START_TIME = NVL(:startDate, START_TIME),");
			sql.append(" END_TIME = NVL(:endDate, END_TIME),");
			sql.append(" ELAPSED_TIME = NVL(:elapsedTime, ELAPSED_TIME),");
			sql.append(" STATUS = NVL(:status, STATUS),");
			sql.append(" MESSAGE = :message,");
			
			sql.append(" LAST_UPDATE_DATE = NVL(:lastUpdateDate,LAST_UPDATE_DATE),");
			sql.append(" LAST_UPDATED_BY = NVL(:lastUpdatedBy,LAST_UPDATED_BY)");
			sql.append(" WHERE INSTANCE_ID = :instanceId");

			SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(record);
			// GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
			updateNum = this.namedjdbcTemplate.update(sql.toString(), namedParameters);
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return updateNum;
	}

	@Override
	public int deleteAllocBatchJobRecord(AllocRequestInstanceBean record) throws SQLException {
		int delNum = 0;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("DELETE HRS_CORE_A_REQUEST_INSTANCE");
			sql.append(" WHERE INSTANCE_ID = :instanceId");

			SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(record);
			// GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
			delNum = this.namedjdbcTemplate.update(sql.toString(), namedParameters);// ,																	// String[]{"DIMENSION_ID"});
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return delNum;
	}

	@Override
	public List<Map<String, Object>> getAvailablePeriodListMapForDisplay() throws SQLException {
		StringBuffer sql = new StringBuffer();
		List<Map<String, Object>> result = null;
		try {
			sql.append("SELECT ");
			sql.append(" PERIOD_NAME periodId, PERIOD_NAME periodName ");
			sql.append(" FROM HRS_CORE_FIN_PERIOD WHERE PERIOD_STATUS='O' ");
			sql.append(" ORDER BY periodId DESC");
			result = namedjdbcTemplate.getJdbcOperations().queryForList(sql.toString());
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return result;
	}

	@Override
	public List<PeriodBean> getAvailablePeriodList() throws SQLException {
		StringBuffer sql = new StringBuffer();
		List<PeriodBean> result = null;
		try {
			sql.append("SELECT ");
			sql.append(" PERIOD_NAME, PERIOD_YEAR, PERIOD_NUM, QUARTER_NUM, ADJ_FLAG " + this.AUDIT_QUERY_STRING);
			sql.append(" FROM HRS_CORE_FIN_PERIOD  WHERE PERIOD_STATUS='O'  ");
			sql.append(" ORDER BY PERIOD_NAME DESC");
			result = namedjdbcTemplate.getJdbcOperations().query(sql.toString(), new PeriodRowMapper());
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> getGroupRuleList() throws SQLException {
		StringBuffer sql = new StringBuffer();
		List<Map<String, Object>> result = null;
		try {
			sql.append("SELECT GROUP_HEADER_ID RULE_ID,GROUP_NAME RULE_NAME FROM "
					+ "HAE_ALLOC_RULES_GROUP_HAEADER  ORDER BY GROUP_NUM DESC");
			result = namedjdbcTemplate.getJdbcOperations().queryForList(sql.toString());
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> getruleList(String type) throws SQLException {
		StringBuffer sql = new StringBuffer();
		List<Map<String, Object>> result = null;
		try {
			sql.append("SELECT R.RULE_ID,R.RULE_NAME FROM HAE_ALLOC_RULE R,HAE_ALLOC_DRIVER D "
					+ "WHERE R.RULE_ID=D.RULE_ID AND D.DRIVER_TYPE='"+type+"' ORDER BY R.RULE_ID DESC");
			result = namedjdbcTemplate.getJdbcOperations().queryForList(sql.toString());
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return result;
	}

	@Override
	public AllocRequestInstanceBean getAllocRequestInstanceByPrimaryKey(AllocRequestInstanceBean requestBean)
			throws SQLException {
		StringBuffer sql = new StringBuffer();
		AllocRequestInstanceBean result = null;
		try {
			sql.append("SELECT ");
			sql.append(" INSTANCE_ID,RULE_ID, RULE_TYPE , PERIOD, START_TIME, END_TIME, ELAPSED_TIME, STATUS, MESSAGE " + AUDIT_QUERY_STRING);
			sql.append(" FROM HRS_CORE_A_REQUEST_INSTANCE ");
			sql.append(" WHERE INSTANCE_ID = " + requestBean.getInstanceId());
			result = (AllocRequestInstanceBean)namedjdbcTemplate.getJdbcOperations().queryForObject(sql.toString(), new AllocBatchReqInstanceRowMapper());
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> getAllocRequestInstance(AllocRequestInstanceRecord requestBean)
			throws SQLException {
		String strCondition = buildCondition(requestBean);
		StringBuffer sql = new StringBuffer();
		List<Map<String, Object>> list = null;
		try {
			sql.append("SELECT ");
			sql.append(" INSTANCE_ID,RULE_ID, RULE_TYPE , PERIOD,DESCRIPTION " + AUDIT_QUERY_STRING);
			if (requestBean.getRowStartIndex() != 0 && requestBean.getRowCount() != 0) {
				sql.append(", ROW_NUMBER() OVER (ORDER BY INSTANCE_ID DESC) AS row_number");
			}
			sql.append(" FROM HAE_ALLOC_INSTANCE ");
			if (null != strCondition && strCondition.length() > 0) {
				sql.append(" WHERE " + strCondition);
			}
			sql.append(" ORDER BY INSTANCE_ID DESC");
			if (requestBean.getRowStartIndex() != 0 && requestBean.getRowCount() != 0) {
				sql = this.pageSql(sql, requestBean.getRowStartIndex(), requestBean.getRowCount());
			}
			list = namedjdbcTemplate.getJdbcOperations().queryForList(sql.toString());
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return list;
	}

	@Override
	public int getAllocRequestInstanceCount(AllocRequestInstanceRecord jobInsBean) throws SQLException {
		try {
			String strCondition = buildCondition(jobInsBean);
			StringBuffer sql = new StringBuffer();
			sql.append(" select count(1) from HAE_ALLOC_INSTANCE ");
			if (null != strCondition && strCondition.length() > 0) {
				sql.append(" where " + strCondition);
			}
			return (int)namedjdbcTemplate.getJdbcOperations().queryForObject(sql.toString(), int.class);
		} catch (Exception e) {
			throw new SQLException(e);
		}
	}

	@Override
	public long saveAllocRequestInstance(AllocRequestInstanceRecord jobRecord) throws SQLException {
		long primaryKey = 0;
		try {
			//get primary key from sequence
			primaryKey = this.getAutoGeneratedPrimaryKey("HAE_ALLOC_INSTANCE_S");
			jobRecord.setInstanceId(primaryKey);
			String sql = "INSERT INTO HAE_ALLOC_INSTANCE(INSTANCE_ID,RULE_ID, RULE_TYPE , PERIOD, DESCRIPTION " + this.AUDIT_QUERY_STRING + ")" +
			 		      " VALUES(:instanceId, :ruleId, :ruleType, :period, :description, " +
		                          ":creationDate, :createdBy, :lastUpdateDate, :lastUpdatedBy)";				
			SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(jobRecord);
			//GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		    this.namedjdbcTemplate.update(sql, namedParameters);//, generatedKeyHolder, new String[]{"DIMENSION_ID"});		    
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return primaryKey;
	}

	@Override
	public int updateAllocRequestInstance(AllocRequestInstanceRecord record) throws SQLException {
		int updateNum = 0;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE HAE_ALLOC_INSTANCE SET");
			sql.append(" RULE_ID = NVL(:ruleId, RULE_ID),");
			sql.append(" RULE_TYPE = NVL(:ruleType, RULE_TYPE),");
			sql.append(" PERIOD = NVL(:period, PERIOD),");			
			sql.append(" DESCRIPTION = NVL(:description, DESCRIPTION),");		
			sql.append(" LAST_UPDATE_DATE = NVL(:lastUpdateDate,LAST_UPDATE_DATE),");
			sql.append(" LAST_UPDATED_BY = NVL(:lastUpdatedBy,LAST_UPDATED_BY)");
			sql.append(" WHERE INSTANCE_ID = :instanceId");

			SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(record);
			// GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
			updateNum = this.namedjdbcTemplate.update(sql.toString(), namedParameters);
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return updateNum;
	}

	@Override
	public int deleteAllocRequestInstance(AllocRequestInstanceRecord record) throws SQLException {
		int delNum = 0;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("DELETE HAE_ALLOC_INSTANCE");
			sql.append(" WHERE INSTANCE_ID = :instanceId");

			SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(record);
			// GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
			delNum = this.namedjdbcTemplate.update(sql.toString(), namedParameters);// ,																	// String[]{"DIMENSION_ID"});
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return delNum;
	}

	@Override
	public AllocRequestInstanceRecord getAllocRequestInstanceByPrimaryKey(AllocRequestInstanceRecord requestBean)
			throws SQLException {
		StringBuffer sql = new StringBuffer();
		AllocRequestInstanceRecord result = null;
		try {
			sql.append("SELECT ");
			sql.append("  INSTANCE_ID,RULE_ID, RULE_TYPE , PERIOD, DESCRIPTION " + AUDIT_QUERY_STRING);
			sql.append(" FROM HAE_ALLOC_INSTANCE ");
			sql.append(" WHERE INSTANCE_ID = " + requestBean.getInstanceId());
			result = (AllocRequestInstanceRecord)namedjdbcTemplate.getJdbcOperations().queryForObject(sql.toString(), new AllocBatchReqInstanceMapper());
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return result;
	}

	@Override
	public long saveAllocRequestInstanceHistoryRecord(AllocRequestInstanceHistoryRecord requestHistoryBean)
			throws SQLException {
		long primaryKey = 0;
		try {
			//get primary key from sequence
			primaryKey = this.getAutoGeneratedPrimaryKey("HAE_ALLOC_INSTANCE_HISTORY_S");
			requestHistoryBean.setHistoryId(primaryKey);
			String sql = " insert into hae_alloc_instance_history ("
					+ "INSTANCE_ID, HISTORY_ID, RULE_ID, GROUP_HEADER_ID, PERIOD, START_TIME, END_TIME, ELAPSED_TIME, STATUS, "
					+ "MESSAGE, ROLLBACK_TIME, ATTRIBUTE1, ATTRIBUTE2, ATTRIBUTE3, ATTRIBUTE4, ATTRIBUTE5, "
					+ "CREATION_DATE, CREATED_BY, LAST_UPDATE_DATE, LAST_UPDATED_BY) values ("
					+ ":instanceId, :historyId, :ruleId, :groupHeaderId, :period, :startTime, :endTime, :elapsedTime, :status, :message, :rollbackTime,"
					+ " :attribute1, :attribute2, :attribute3, :attribute4, :attribute5, " 
					+ ":creationDate, :createdBy, :lastUpdateDate, :lastUpdatedBy)";				
			SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(requestHistoryBean);
			//GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		    this.namedjdbcTemplate.update(sql, namedParameters);//, generatedKeyHolder, new String[]{"DIMENSION_ID"});		    
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return primaryKey;
	}

	@Override
	public int updateAllocRequestInstanceHistoryRecord(AllocRequestInstanceHistoryRecord requestHistoryBean)
			throws SQLException {
		int updateNum = 0;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE hae_alloc_instance_history SET");
			sql.append(" INSTANCE_ID = NVL(:instanceId, INSTANCE_ID),");
			sql.append(" PERIOD = NVL(:period, PERIOD),");
			sql.append(" RULE_ID = NVL(:ruleId, RULE_ID),");
			sql.append(" START_TIME = NVL(:startTime, START_TIME),");
			sql.append(" END_TIME = NVL(:endTime, END_TIME),");
			sql.append(" ELAPSED_TIME = NVL(:elapsedTime, ELAPSED_TIME),");
			sql.append(" STATUS = NVL(:status, STATUS),");
			sql.append(" MESSAGE = :message,");
			sql.append(" ROLLBACK_TIME = NVL(:rollbackTime, ROLLBACK_TIME),");
			sql.append(" LAST_UPDATE_DATE = NVL(:lastUpdateDate,LAST_UPDATE_DATE),");
			sql.append(" LAST_UPDATED_BY = NVL(:lastUpdatedBy,LAST_UPDATED_BY)");
			sql.append(" WHERE HISTORY_ID = :historyId");

			SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(requestHistoryBean);
			// GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
			updateNum = this.namedjdbcTemplate.update(sql.toString(), namedParameters);
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return updateNum;
	}

	@Override
	public int getAllocRequestInstanceHistoryRecordCount(AllocRequestInstanceHistoryRecord queryBean)
			throws SQLException {
		try {
			String strCondition = buildCondition(queryBean);
			StringBuffer sql = new StringBuffer();
			sql.append(" select count(1) from hae_alloc_instance_history ");
			if (null != strCondition && strCondition.length() > 0) {
				sql.append(" where " + strCondition);
			}
			return (int)namedjdbcTemplate.getJdbcOperations().queryForObject(sql.toString(), int.class);
		} catch (Exception e) {
			throw new SQLException(e);
		}
	}

	@Override
	public List<Map<String, Object>> findAllocInstanceHistory(AllocRequestInstanceHistoryRecord requestBean)
			throws SQLException {
		String strCondition = buildCondition(requestBean);
		StringBuffer sql = new StringBuffer();
		List<Map<String, Object>> list = null;
		try {
			sql.append(" SELECT "
					+ "H.INSTANCE_ID, H.HISTORY_ID, H.RULE_ID,H.GROUP_HEADER_ID, H.PERIOD, H.START_TIME, H.END_TIME, H.ELAPSED_TIME, H.STATUS, H.MESSAGE, "
					+ "H.ROLLBACK_TIME, H.ATTRIBUTE1, H.ATTRIBUTE2, H.ATTRIBUTE3, H.ATTRIBUTE4, H.ATTRIBUTE5, H.CREATION_DATE, H.CREATED_BY, "
					+ "H.LAST_UPDATE_DATE, H.LAST_UPDATED_BY,  CASE WHEN H.ROLLBACKSTATUS = 'Y' AND H.ROW_NUM = 1 THEN 'Y' ELSE NULL END CANBEROLLBACK ");
					
			if (requestBean.getRowStartIndex() != 0 && requestBean.getRowCount() != 0) {
				sql.append(", ROW_NUMBER() OVER(ORDER BY HISTORY_ID DESC) AS ROW_NUMBER ");
			}
			sql.append( "FROM ( SELECT INNER.*, ROW_NUMBER() OVER(PARTITION BY ROLLBACKSTATUS ORDER BY HISTORY_ID DESC) AS ROW_NUM FROM ( "
					+ "SELECT INSTANCE_ID, HISTORY_ID, RULE_ID,GROUP_HEADER_ID, PERIOD, START_TIME, END_TIME, ELAPSED_TIME, STATUS,"
					+ " CASE WHEN STATUS = 'S101' OR STATUS = 'R102' THEN 'Y' ELSE NULL END ROLLBACKSTATUS, "
					+ "MESSAGE, ROLLBACK_TIME, ATTRIBUTE1, ATTRIBUTE2, ATTRIBUTE3, ATTRIBUTE4, ATTRIBUTE5, CREATION_DATE, "
					+ "CREATED_BY, LAST_UPDATE_DATE, LAST_UPDATED_BY  "
					+ "FROM HAE_ALLOC_INSTANCE_HISTORY  ) INNER ");
			            if (null != strCondition && strCondition.length() > 0) {
			          	sql.append(" WHERE " + strCondition);
		            	}
		        	sql.append("ORDER BY HISTORY_ID DESC) H ");
			if (requestBean.getRowStartIndex() != 0 && requestBean.getRowCount() != 0) {
				sql = this.pageSql(sql, requestBean.getRowStartIndex(), requestBean.getRowCount());
			}
			list = namedjdbcTemplate.getJdbcOperations().queryForList(sql.toString());
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return list;
	}

	@Override
	public List<Long> getruleListForLoop(AllocRequestInstanceRecord requestBean) throws SQLException {
		StringBuffer sql = new StringBuffer();
		List<Map<String, Object>> list = null;
		 List<Long> returnlist=new ArrayList<Long>();
		try {
			sql.append("SELECT L.RULE_ID FROM HAE_ALLOC_RULES_GROUP_HAEADER H, "
					+ "HAE_ALLOC_RULES_GROUP_LINE L WHERE "
					+ " H.GROUP_HEADER_ID=L.GROUP_LINE_ID AND "
					+ " H.GROUP_HEADER_ID= "+requestBean.getRuleId()
					+ " ORDER BY L.LINE_NUM");
			list = namedjdbcTemplate.getJdbcOperations().queryForList(sql.toString());
			 for(Map a:list){
				long ruleId=(long)a.get("RULE_ID");
				returnlist.add(ruleId);
			 }
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return returnlist;
	}

	@Override
	public AllocRequestInstanceHistoryRecord getAllocRequestInstanceHistoryByPrimaryKey(
			AllocRequestInstanceHistoryRecord requestBean) throws SQLException {
		StringBuffer sql = new StringBuffer();
		AllocRequestInstanceHistoryRecord result = null;
		try {
			sql.append("SELECT INSTANCE_ID, HISTORY_ID, RULE_ID, PERIOD, START_TIME, END_TIME, ELAPSED_TIME, STATUS, MESSAGE, ROLLBACK_TIME, ATTRIBUTE1, "
					+ "ATTRIBUTE2, ATTRIBUTE3, ATTRIBUTE4, ATTRIBUTE5, CREATION_DATE, CREATED_BY, "
					+ "LAST_UPDATE_DATE, LAST_UPDATED_BY FROM HAE_ALLOC_INSTANCE_HISTORY"
					+ " WHERE HISTORY_ID= " + requestBean.getHistoryId());
			result = (AllocRequestInstanceHistoryRecord)namedjdbcTemplate.getJdbcOperations().queryForObject(sql.toString(), new AllocRequestInstanceHistoryMapper());
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return result;
	}


	
	
}
