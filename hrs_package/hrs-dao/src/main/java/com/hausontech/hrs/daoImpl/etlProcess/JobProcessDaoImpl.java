/*package com.hausontech.hrs.daoImpl.etlProcess;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.hausontech.hrs.api.etlProcess.dao.IJobProcessDao;
import com.hausontech.hrs.bean.etlProcess.JobInstanceBean;
import com.hausontech.hrs.bean.etlProcess.ScheduleInstanceBean;
import com.hausontech.hrs.bean.etlProcess.ScheduleStatusBean;
import com.hausontech.hrs.bean.etlProcess.ScheduleTypeBean;
import com.hausontech.hrs.daoImpl.BaseDaoImpl;
import com.hausontech.hrs.daoImpl.engine.quartz.QuartzManager;
import com.hausontech.hrs.daoImpl.etlProcess.mapper.EtlJobRowMapper;
import com.hausontech.hrs.daoImpl.etlProcess.mapper.EtlScheduleRowMapper;

public class JobProcessDaoImpl extends BaseDaoImpl implements IJobProcessDao {

	@Override
	public List<Map<String, Object>> getEtlJobList(JobInstanceBean jobInsBean) throws SQLException {
		String strCondition = BuildCondition(jobInsBean);
		StringBuffer sql = new StringBuffer();
		List<Map<String, Object>> list = null;
		try {
			if (StringUtils.isBlank(jobInsBean.getJobType())) {

				sql.append("SELECT ");
				sql.append(
						" job.JOB_ID, job.JOB_NAME, job.FILE_PATH, job.PARAM_LIST, job.DESCRIPTION,job.ORDER_ID ,jobIns.INSTANCE_ID, jobIns.START_TIME, jobIns.END_TIME, jobIns.ELAPSED_TIME, jobIns.MESSAGE,jobIns.STATUS ");
				if (jobInsBean.getRowStartIndex() != 0 && jobInsBean.getRowCount() != 0) {
					sql.append(", ROW_NUMBER() OVER (ORDER BY job.ORDER_ID) AS row_number");
				}
				sql.append(" FROM HRS_ETL_JOB job,");
				sql.append(
						" (SELECT a.job_id, a.instance_id, b.start_time, b.end_time, b.elapsed_time, b.status, b.message "
								+ " FROM (SELECT JOB_ID, MAX(INSTANCE_ID) INSTANCE_ID "
								+ " FROM HRS_ETL_JOB_INSTANCE GROUP BY JOB_ID) a, "
								+ " HRS_ETL_JOB_INSTANCE b WHERE a.instance_id = b.instance_id and a.job_id=b.job_id) jobIns ");

				sql.append(" WHERE job.JOB_ID = jobIns.JOB_ID(+) ");
			}
			 else if get the attribute jobType have contents 2017-03-29 GXY 
			else if (jobInsBean.getJobType().equals("allocationJob")) {

				sql.append("SELECT ");
				sql.append(
						" job.JOB_ID, job.JOB_NAME, job.FILE_PATH, job.PARAM_LIST, job.DESCRIPTION, jobIns.INSTANCE_ID, jobIns.START_TIME, jobIns.END_TIME, jobIns.ELAPSED_TIME, jobIns.STATUS, jobIns.MESSAGE ");
				if (jobInsBean.getRowStartIndex() != 0 && jobInsBean.getRowCount() != 0) {
					sql.append(", ROW_NUMBER() OVER (ORDER BY job.JOB_ID) AS row_number");
				}
				sql.append(" FROM HAE_ALLOC_JOB job");
				sql.append(
						" (SELECT a.job_id, a.instance_id, b.start_time, b.end_time, b.elapsed_time, b.status, b.message "
								+ " FROM (SELECT JOB_ID, MAX(INSTANCE_ID) INSTANCE_ID "
								+ " FROM HAE_ALLOC_JOB_INSTANCE GROUP BY JOB_ID) a, "
								+ " HAE_ALLOC_JOB_INSTANCE b WHERE a.instance_id = b.instance_id and a.job_id=b.job_id) jobIns ");

				sql.append(" WHERE job.JOB_ID = jobIns.JOB_ID(+) ");
			}

			if (null != strCondition && strCondition.length() > 0) {
				sql.append(" AND " + strCondition);
			}
			if (jobInsBean.getRowStartIndex() != 0 && jobInsBean.getRowCount() != 0) {
				sql = this.pageSql(sql, jobInsBean.getRowStartIndex(), jobInsBean.getRowCount());
			}
			list = namedjdbcTemplate.getJdbcOperations().queryForList(sql.toString());
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getEtlScheduleList(ScheduleInstanceBean jobInsBean) throws SQLException {
		String strCondition = BuildCondition(jobInsBean);
		StringBuffer sql = new StringBuffer();
		List<Map<String, Object>> list = null;
		try {
			if (StringUtils.isBlank(null)) {
				sql.append("SELECT ");
				sql.append(
						" sch.JOB_ID, job.JOB_NAME,  sch.TYPE_PROCESS, sch.VALUE_PROCESS, sch.concurrent,sch.SCHEDULE_ID ");
				if (jobInsBean.getRowStartIndex() != 0 && jobInsBean.getRowCount() != 0) {
					sql.append(", ROW_NUMBER() OVER (ORDER BY job.JOB_ID) AS row_number");
				}
				sql.append(" FROM HRS_ETL_JOB job,HRS_ETL_SCHEDULE sch  , ");
				sql.append(
						" (SELECT a.job_id, a.instance_id, b.start_time, b.end_time, b.elapsed_time, b.status, b.message "
								+ " FROM (SELECT JOB_ID, MAX(INSTANCE_ID) INSTANCE_ID "
								+ " FROM HRS_ETL_JOB_INSTANCE GROUP BY JOB_ID) a, "
								+ " HRS_ETL_JOB_INSTANCE b WHERE a.instance_id = b.instance_id and a.job_id=b.job_id) jobIns ");

				sql.append(" WHERE job.JOB_ID = jobIns.JOB_ID(+) and SCH.JOB_ID=JOB.JOB_ID ");
			}
			 else if get the attribute jobType have contents 2017-03-29 GXY 
			else if (jobInsBean.getJobType().equals("allocationJob")) {

				sql.append("SELECT ");
				sql.append(
						" job.JOB_ID, job.JOB_NAME, job.FILE_PATH, job.PARAM_LIST, job.DESCRIPTION, jobIns.INSTANCE_ID, jobIns.START_TIME, jobIns.END_TIME, jobIns.ELAPSED_TIME, jobIns.STATUS, jobIns.MESSAGE ");
				if (jobInsBean.getRowStartIndex() != 0 && jobInsBean.getRowCount() != 0) {
					sql.append(", ROW_NUMBER() OVER (ORDER BY job.JOB_ID) AS row_number");
				}
				sql.append(" FROM HAE_ALLOC_JOB job,");
				sql.append(
						" (SELECT a.job_id, a.instance_id, b.start_time, b.end_time, b.elapsed_time, b.status, b.message "
								+ " FROM (SELECT JOB_ID, MAX(INSTANCE_ID) INSTANCE_ID "
								+ " FROM HAE_ALLOC_JOB_INSTANCE GROUP BY JOB_ID) a, "
								+ " HAE_ALLOC_JOB_INSTANCE b WHERE a.instance_id = b.instance_id and a.job_id=b.job_id) jobIns ");

				sql.append(" WHERE job.JOB_ID = jobIns.JOB_ID(+) ");
			}

			if (null != strCondition && strCondition.length() > 0) {
				sql.append(" AND " + strCondition);
			}
			if (jobInsBean.getRowStartIndex() != 0 && jobInsBean.getRowCount() != 0) {
				sql = this.pageSql(sql, jobInsBean.getRowStartIndex(), jobInsBean.getRowCount());
			}
			list = namedjdbcTemplate.getJdbcOperations().queryForList(sql.toString());
			// ????????????list??????scheduleId????????????quartz?????????job??????????????????????????????STATUS???
			String[] arr = new String[list.size()];// ????????????
			if (list != null && list.size() > 0) {
				for (int z = 0; z < arr.length; z++) {
					arr[z] = list.get(z).get("SCHEDULE_ID").toString();
				}
				for (int z = 0; z < arr.length; z++) {
					list.get(z).put("STATUS", QuartzManager.getJobStatue(arr[z]));
				}
			}
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return list;
	}

	@Override
	public int countByCondition(JobInstanceBean jobInsBean) throws SQLException {
		try {
			boolean flagStatus = jobInsBean.getStatus() != null && jobInsBean.getStatus().length() > 0;
			String strCondition = BuildCondition(jobInsBean);
			StringBuffer sql = new StringBuffer();
			if (flagStatus) {
				if (StringUtils.isBlank(jobInsBean.getJobType())) {
					sql.append(
							" select count(1) from (select distinct job.*, jobIns.STATUS  from HRS_ETL_JOB job ,HRS_ETL_JOB_INSTANCE jobIns where jobIns.JOB_ID=job.JOB_ID ");
					
					 * else if get the attribute jobType have contents
					 * 2017-03-29 GXY
					 
				} else if (jobInsBean.getJobType().equals("allocationJob")) {
					sql.append(" select count(1) from HAE_ALLOC_JOB ");
				}
				if (null != strCondition && strCondition.length() > 0) {
					sql.append(" and " + strCondition + ")");
				}
			} else {
				if (StringUtils.isBlank(jobInsBean.getJobType())) {
					sql.append(" select count(1) from HRS_ETL_JOB job ");
					
					 * else if get the attribute jobType have contents
					 * 2017-03-29 GXY
					 
				} else if (jobInsBean.getJobType().equals("allocationJob")) {
					sql.append(" select count(1) from HAE_ALLOC_JOB ");
				}
				if (null != strCondition && strCondition.length() > 0) {
					sql.append(" where " + strCondition);
				}
			}
			return (int) namedjdbcTemplate.getJdbcOperations().queryForObject(sql.toString(), int.class);
		} catch (Exception e) {
			throw new SQLException(e);
		}
	}

	@Override
	public int countScheduleByCondition(ScheduleInstanceBean jobInsBean) throws SQLException {
		try {
			boolean flagStatus = jobInsBean.getStatus() != null && jobInsBean.getStatus().length() > 0;
			String strCondition = BuildCondition(jobInsBean);
			StringBuffer sql = new StringBuffer();
			if (flagStatus) {
				if (StringUtils.isBlank(jobInsBean.getJobType())) {
					sql.append(
							"select count(1) from (select distinct sch.*, jobIns.STATUS  from HRS_ETL_SCHEDULE sch,HRS_ETL_JOB_INSTANCE jobIns");
					
					 * else if get the attribute jobType have contents
					 * 2017-03-29 GXY
					 
				} else if (jobInsBean.getJobType().equals("allocationJob")) {
					sql.append(" select count(1) from HAE_ALLOC_JOB ");
				}
				boolean flagName = jobInsBean.getJobName() != null && jobInsBean.getJobName().length() > 0;
				if (flagName) {
					sql.append(",HRS_ETL_JOB job");
				}
				if (null != strCondition && strCondition.length() > 0) {
					sql.append(" where " + strCondition + "And jobIns.JOB_ID=sch.JOB_ID");
				}
				if (flagName) {
					sql.append(" and JOB.JOB_ID=SCH.JOB_ID");
				}
				sql.append(" )");
			} else {
				if (StringUtils.isBlank(jobInsBean.getJobType())) {
					sql.append(" select count(1) from HRS_ETL_SCHEDULE sch");
					
					 * else if get the attribute jobType have contents
					 * 2017-03-29 GXY
					 
				} else if (jobInsBean.getJobType().equals("allocationJob")) {
					sql.append(" select count(1) from HAE_ALLOC_JOB ");
				}
				boolean flagName = jobInsBean.getJobName() != null && jobInsBean.getJobName().length() > 0;
				if (flagName) {
					sql.append(",HRS_ETL_JOB job");
				}
				if (null != strCondition && strCondition.length() > 0) {
					sql.append(" where " + strCondition);
				}
				if (flagName) {
					sql.append(" and JOB.JOB_ID=SCH.JOB_ID");
				}
			}
			return (int) namedjdbcTemplate.getJdbcOperations().queryForObject(sql.toString(), int.class);
		} catch (Exception e) {
			throw new SQLException(e);
		}
	}

	@Override
	public int saveJobRecord(JobInstanceBean jobRecord) throws SQLException {
		int primaryKey = 0;
		try {
			// get primary key from sequence

			if (StringUtils.isBlank(jobRecord.getJobType())) {
				primaryKey = (int) this.getAutoGeneratedPrimaryKey("HRS_ETL_JOB_S");
				
				 * else if get the attribute jobType have contents 2017-03-29
				 * GXY
				 
			} else if (jobRecord.getJobType().equals("allocationJob")) {
				primaryKey = (int) this.getAutoGeneratedPrimaryKey("HAE_ALLOC_JOB_S");
			}
			jobRecord.setJobId(primaryKey);
			String sql = null;

			if (StringUtils.isBlank(jobRecord.getJobType())) {
				sql = "INSERT INTO HRS_ETL_JOB(JOB_ID, JOB_NAME, FILE_PATH, PARAM_LIST,ORDER_ID, DESCRIPTION "
						+ this.AUDIT_QUERY_STRING + ")"
						+ " VALUES(:jobId, :jobName, :filePath, :parameList,:orderId, :description,"
						+ ":creationDate, :createdBy, :lastUpdateDate, :lastUpdatedBy)";
				
				 * else if get the attribute jobType have contents 2017-03-29
				 * GXY
				 
			} else if (jobRecord.getJobType().equals("allocationJob")) {
				sql = "INSERT INTO HAE_ALLOC_JOB(JOB_ID, JOB_NAME, FILE_PATH, PARAM_LIST, DESCRIPTION "
						+ this.AUDIT_QUERY_STRING + ")"
						+ " VALUES(:jobId, :jobName, :filePath, :parameList, :description,"
						+ ":creationDate, :createdBy, :lastUpdateDate, :lastUpdatedBy)";
			}
			SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(jobRecord);
			// GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
			this.namedjdbcTemplate.update(sql, namedParameters);// ,
																// generatedKeyHolder,
																// new
																// String[]{"DIMENSION_ID"});
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return primaryKey;
	}

	@Override
	public int saveScheduleRecord(ScheduleInstanceBean scheduleRecord) throws SQLException {
		int primaryKey = 0;
		try {
			// get primary key from sequence
			primaryKey = (int) this.getAutoGeneratedPrimaryKey("HRS.HRS_ETL_SCHEDULE_S");
			scheduleRecord.setScheduleId(primaryKey);
			String sql = null;
			if (StringUtils.isBlank(null)) {
				sql = "INSERT INTO HRS_ETL_SCHEDULE(JOB_ID, TYPE_PROCESS, VALUE_PROCESS,CONCURRENT,SCHEDULE_ID"
						+ this.AUDIT_QUERY_STRING + ")" + " VALUES(:jobId, :typeProcess, :valueProcess,"
						+ ":concurrent,:scheduleId, :creationDate, :createdBy, :lastUpdateDate, :lastUpdatedBy)";
				
				 * else if get the attribute jobType have contents 2017-03-29
				 * GXY
				 
			} else if (scheduleRecord.getJobName().equals("allocationJob")) {
				sql = "INSERT INTO HAE_ALLOC_JOB(JOB_ID, JOB_NAME, FILE_PATH, PARAM_LIST, DESCRIPTION "
						+ this.AUDIT_QUERY_STRING + ")"
						+ " VALUES(:jobId, :jobName, :filePath, :parameList, :description,"
						+ ":creationDate, :createdBy, :lastUpdateDate, :lastUpdatedBy)";
			}
			SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(scheduleRecord);
			// GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
			this.namedjdbcTemplate.update(sql, namedParameters);// ,
																// generatedKeyHolder,
																// new
																// String[]{"DIMENSION_ID"});
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return primaryKey;
	}

	@Override
	public int saveEtlJobInstance(JobInstanceBean jobInsRecord) throws SQLException {
		int primaryKey = 0;
		try {
			// get primary key from sequence
			if (StringUtils.isBlank(jobInsRecord.getJobType())) {
				primaryKey = (int) this.getAutoGeneratedPrimaryKey("HRS_ETL_JOB_INSTANCE_S");
				
				 * else if get the attribute jobType have contents 2017-03-29
				 * GXY
				 
			} else if (jobInsRecord.getJobType().equals("allocationJob")) {
				primaryKey = (int) this.getAutoGeneratedPrimaryKey("HAE_ALLOC_JOB_INSTANCE_S");
			}
			String status = jobInsRecord.getStatus();
			//status = GetStatusDescriptionByStatusId.getStatus(status);
			jobInsRecord.setStatus(status);
			jobInsRecord.setLastJobInstanceId(primaryKey);
			jobInsRecord.setStartTime(new Date());
			jobInsRecord.setCreationDate(new Date());
			jobInsRecord.setLastUpdateDate(new Date());
			jobInsRecord.setCreatedBy("web");
			jobInsRecord.setLastUpdatedBy("web");
			long scheduleId = jobInsRecord.getScheduleId();
			StringBuffer sql = new StringBuffer();
			if (StringUtils.isBlank(jobInsRecord.getJobType())) {
				sql.append(
						"INSERT INTO HRS_ETL_JOB_INSTANCE(INSTANCE_ID, JOB_ID, START_TIME, END_TIME, STATUS, MESSAGE "
								+ this.AUDIT_QUERY_STRING);
				if (scheduleId > 0) {
					sql.append(",SCHEDULE_ID");
				}
				sql.append(
						")  VALUES(:lastJobInstanceId, :jobId, :startTime, :endTime, :status, :message,:creationDate, :createdBy, :lastUpdateDate, :lastUpdatedBy");
				if (scheduleId > 0) {
					sql.append(",:scheduleId");
				}
				sql.append(")");
				
				 * else if get the attribute jobType have contents 2017-03-29
				 * GXY
				 
			} else if (jobInsRecord.getJobType().equals("allocationJob")) {
				sql.append(
						"INSERT INTO HAE_ALLOC_JOB_INSTANCE(INSTANCE_ID, JOB_ID, START_TIME, END_TIME, STATUS, MESSAGE "
								+ this.AUDIT_QUERY_STRING + ")"
								+ " VALUES(:lastJobInstanceId, :jobId, :startTime, :endTime, :status, :message,"
								+ ":creationDate, :createdBy, :lastUpdateDate, :lastUpdatedBy)");
			}

			SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(jobInsRecord);
			// GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
			this.namedjdbcTemplate.update(sql.toString(), namedParameters);// ,
																			// generatedKeyHolder,
																			// new
																			// String[]{"DIMENSION_ID"});
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return primaryKey;
	}

	@Override
	public List<Map<String, Object>> updateEtlJobInsRecord(JobInstanceBean jobInsRecord) throws SQLException {
		int updateNum = 0;
		List<Map<String, Object>> list =  new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<>();
		try {
			jobInsRecord.setEndTime(new Date());
			jobInsRecord.setLastUpdateDate(new Date());
			StringBuffer sql = new StringBuffer();
			String status = jobInsRecord.getStatus();
			String message = jobInsRecord.getMessage();
			int length = message.length();
			int errorNumber = Integer.parseInt(message.substring(length - 9, length - 8));
			if (errorNumber > 0) {
				status = "failed";
			}
			//status = GetStatusDescriptionByStatusId.getStatus(status);
			jobInsRecord.setStatus(status);
			map.put("status", status);
			map.put("message",message);
			list.add(map);
			if (StringUtils.isBlank(jobInsRecord.getJobType())) {
				sql.append("UPDATE HRS_ETL_JOB_INSTANCE SET");
				
				 * else if get the attribute jobType have contents 2017-03-29
				 * GXY
				 
			} else if (jobInsRecord.getJobType().equals("allocationJob")) {
				sql.append("UPDATE HAE_ALLOC_JOB_INSTANCE SET");
			}
			sql.append(" END_TIME = NVL(:endTime, END_TIME),");
			sql.append(" ELAPSED_TIME = NVL(:elapsedTime, ELAPSED_TIME),");
			sql.append(" STATUS = NVL(:status, STATUS),");
			sql.append(" MESSAGE = NVL(:message, MESSAGE),");
			sql.append(" LAST_UPDATE_DATE = NVL(:lastUpdateDate,LAST_UPDATE_DATE),");
			sql.append(" LAST_UPDATED_BY = NVL(:lastUpdatedBy,LAST_UPDATED_BY)");
			sql.append(" WHERE INSTANCE_ID = :lastJobInstanceId");

			SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(jobInsRecord);
			// GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
			updateNum = this.namedjdbcTemplate.update(sql.toString(), namedParameters);
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return list;
	}

	@Override
	public int updateJobRecord(JobInstanceBean record) throws SQLException {
		int updateNum = 0;
		try {
			StringBuffer sql = new StringBuffer();
			if (StringUtils.isBlank(record.getJobType())) {
				sql.append("UPDATE HRS_ETL_JOB SET");
				
				 * else if get the attribute jobType have contents 2017-03-29
				 * GXY
				 
			} else if (record.getJobType().equals("allocationJob")) {
				sql.append("UPDATE HAE_ALLOC_JOB SET");
			}

			sql.append(" JOB_NAME = NVL(:jobName, JOB_NAME),");
			sql.append(" FILE_PATH = NVL(:filePath, FILE_PATH),");
			sql.append(" PARAM_LIST = :parameList,");
			sql.append(" DESCRIPTION = :description,");
			sql.append(" ORDER_ID = :orderId,");
			sql.append(" LAST_UPDATE_DATE = NVL(:lastUpdateDate,LAST_UPDATE_DATE),");
			sql.append(" LAST_UPDATED_BY = NVL(:lastUpdatedBy,LAST_UPDATED_BY)");
			sql.append(" WHERE JOB_ID = :jobId");

			SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(record);
			// GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
			updateNum = this.namedjdbcTemplate.update(sql.toString(), namedParameters);
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return updateNum;
	}

	@Override
	public int updateScheduleRecord(ScheduleInstanceBean scheduleRecord) throws SQLException {
		int updateNum = 0;
		try {
			StringBuffer sql = new StringBuffer();
			if (StringUtils.isBlank(null)) {
				sql.append("UPDATE HRS_ETL_SCHEDULE SET");
				
				 * else if get the attribute jobType have contents 2017-03-29
				 * GXY
				 
			} else if (scheduleRecord.getJobName().equals("allocationJob")) {
				sql.append("UPDATE HAE_ALLOC_JOB SET");
			}
			sql.append(" TYPE_PROCESS = NVL(:typeProcess, TYPE_PROCESS),");
			sql.append(" VALUE_PROCESS = NVL(:valueProcess, VALUE_PROCESS),");
			sql.append(" CONCURRENT = NVL(:concurrent,CONCURRENT),");
			sql.append(" LAST_UPDATE_DATE = NVL(:lastUpdateDate,LAST_UPDATE_DATE),");
			sql.append(" LAST_UPDATED_BY = NVL(:lastUpdatedBy,LAST_UPDATED_BY)");
			sql.append(" WHERE SCHEDULE_ID = :scheduleId");

			SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(scheduleRecord);
			// GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
			updateNum = this.namedjdbcTemplate.update(sql.toString(), namedParameters);
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return updateNum;
	}

	@Override
	public int deleteJobRecord(JobInstanceBean record) throws SQLException {
		int delNum = 0;
		try {
			StringBuffer sql = new StringBuffer();

			if (StringUtils.isBlank(record.getJobType())) {
				sql.append("DELETE HRS_ETL_JOB");
				
				 * else if get the attribute jobType have contents 2017-03-29
				 * GXY
				 
			} else if (record.getJobType().equals("allocationJob")) {
				sql.append("DELETE HAE_ALLOC_JOB");
			}
			sql.append(" WHERE JOB_ID=:jobId");
			SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(record);
			// GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
			delNum = this.namedjdbcTemplate.update(sql.toString(), namedParameters);// ,
																					// //
																					// String[]{"DIMENSION_ID"});
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return delNum;
	}

	@Override
	public int deleteScheduleRecordForJob(JobInstanceBean record) throws SQLException {
		int delNum = 0;
		try {
			StringBuffer sql = new StringBuffer();

			if (StringUtils.isBlank(record.getJobType())) {
				sql.append("DELETE HRS_ETL_SCHEDULE");
				
				 * else if get the attribute jobType have contents 2017-03-29
				 * GXY
				 
			} else if (record.getJobType().equals("allocationJob")) {
				sql.append("DELETE HAE_ALLOC_JOB");
			}
			sql.append(" WHERE JOB_ID=:jobId");
			SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(record);
			// GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
			delNum = this.namedjdbcTemplate.update(sql.toString(), namedParameters);// ,
																					// //
																					// String[]{"DIMENSION_ID"});
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return delNum;
	}

	@Override
	public int deleteScheduleRecord(ScheduleInstanceBean record) throws SQLException {
		int delNum = 0;
		try {
			StringBuffer sql = new StringBuffer();

			if (StringUtils.isBlank(record.getJobType())) {
				sql.append("DELETE HRS_ETL_SCHEDULE");
				
				 * else if get the attribute jobType have contents 2017-03-29
				 * GXY
				 
			} else if (record.getJobType().equals("allocationJob")) {
				sql.append("DELETE HAE_ALLOC_JOB");
			}
			sql.append(" WHERE SCHEDULE_ID=:scheduleId");
			SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(record);
			// GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
			delNum = this.namedjdbcTemplate.update(sql.toString(), namedParameters);// ,
																					// //
																					// String[]{"DIMENSION_ID"});
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return delNum;
	}

	@Override
	public JobInstanceBean getJobInfoById(long jobId) throws SQLException {
		StringBuffer sql = new StringBuffer();
		JobInstanceBean result = null;
		try {
			sql.append("SELECT ");
			sql.append(" JOB_ID, JOB_NAME, FILE_PATH, PARAM_LIST ");
			sql.append(" FROM HRS_ETL_JOB ");
			sql.append(" WHERE JOB_ID=" + jobId);
			result = (JobInstanceBean) namedjdbcTemplate.getJdbcOperations().queryForObject(sql.toString(),
					new EtlJobRowMapper());
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return result;
	}

	@Override
	public ScheduleInstanceBean getScheduleInfoById(long scheduleId) throws SQLException {
		StringBuffer sql = new StringBuffer();
		ScheduleInstanceBean result = null;
		try {
			sql.append("SELECT ");
			sql.append(" JOB_ID, TYPE_PROCESS, VALUE_PROCESS, CONCURRENT ");
			sql.append(" FROM HRS_ETL_SCHEDULE ");
			sql.append(" WHERE SCHEDULE_ID=" + scheduleId);
			result = (ScheduleInstanceBean) namedjdbcTemplate.getJdbcOperations().queryForObject(sql.toString(),
					new EtlScheduleRowMapper());
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return result;
	}

	
	 * DAO not have this flag distinguish request ,so service give a new method
	 * to differentiate
	 
	@Override
	public JobInstanceBean getAllocJobInfoById(int jobId) throws SQLException {
		StringBuffer sql = new StringBuffer();
		JobInstanceBean result = null;
		try {
			sql.append("SELECT ");
			sql.append(" JOB_ID, JOB_NAME, FILE_PATH, PARAM_LIST ");
			sql.append(" FROM HAE_ALLOC_JOB ");
			sql.append(" WHERE JOB_ID=" + jobId);
			result = (JobInstanceBean) namedjdbcTemplate.getJdbcOperations().queryForObject(sql.toString(),
					new EtlJobRowMapper());
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return result;
	}

	private String BuildCondition(JobInstanceBean condition) {
		StringBuffer sb = new StringBuffer();
		if (condition != null) {
			if (null != condition && condition.getJobId() != 0) {
				sb.append("job.JOB_ID=" + condition.getJobId() + " ");
				return sb.toString();
			}
			boolean s = !condition.isEmpty();
			// ???????????? ???????????????????????? ??????????????????????????????????????????????????????????????????????????????
			if (null != condition && !condition.isEmpty()) {
				String jobName = condition.getJobName();
				if (null != jobName && !jobName.isEmpty()) {
					sb.append(" job.JOB_NAME like '%" + jobName + "%' ");
				}
				String status = condition.getStatus();
				if (null != status && !status.isEmpty()) {
					if (sb.length() > 0) {
						sb.append(" and ");
					}
					sb.append(" jobIns.STATUS = " + status);
				}
			}
		}
		return sb.toString();
	}

	private String BuildCondition(ScheduleInstanceBean condition) {
		StringBuffer sb = new StringBuffer();
		if (condition != null) {
			if (null != condition && condition.getJobId() != 0) {
				sb.append("job.JOB_ID=" + condition.getJobId() + " ");
				return sb.toString();
			}
			boolean s = !condition.isEmpty();
			// ???????????? ???????????????????????? ??????????????????????????????????????????????????????????????????????????????
			if (null != condition && !condition.isEmpty()) {
				String jobName = condition.getJobName();
				if (null != jobName && !jobName.isEmpty()) {
					sb.append(" job.JOB_NAME like '%" + jobName + "%' ");
				}
				String status = condition.getStatus();
				if (null != status && !status.isEmpty()) {
					if (sb.length() > 0) {
						sb.append(" and ");
					}
					sb.append(" jobIns.STATUS = " + status);
				}
			}
		}
		return sb.toString();
	}

	@Override
	public String getNameById(long id) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "select JOB_NAME  from HRS_ETL_JOB   where JOB_ID=" + id;
		Map<String, Object> result = this.jdbcTemplate.queryForMap(sql);
		String result1 = (String) result.get("JOB_NAME");
		return result1;
	}

	@Override
	public List<Map<String, Object>> getEtlScheduleTypeList(ScheduleTypeBean typeBean) throws SQLException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = null;
		String sql = "select SCHEDULE_TYPE_ID ,SCHEDULE_TYPE,SCHEDULE_DESCRIPTION from HRS_SCHEDULE_TYPE";
		list = this.jdbcTemplate.queryForList(sql);
		return list;
	}

	@Override
	public List<Map<String, Object>> getEtlScheduleStatusList(ScheduleStatusBean statusBean) throws SQLException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = null;
		String sql = "select STATUS_ID ,STATUS_TYPE,STATUS_DESCRIPTION from HRS_STATUS";
		list = this.jdbcTemplate.queryForList(sql);
		return list;
	}

	@Override
	public List<Map<String, Object>> getAllEtlJobList() throws SQLException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = null;
		String sql = "select JOB_ID from HRS_ETL_JOB order by ORDER_ID";
		list = this.jdbcTemplate.queryForList(sql);
		return list;
	}

	@Override
	public List<Map<String, Object>> getAllScheduleList() throws SQLException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = null;
		String sql = "select JOB_ID,SCHEDULE_ID from HRS_ETL_SCHEDULE";
		list = this.jdbcTemplate.queryForList(sql);
		return list;
	}

}
*/