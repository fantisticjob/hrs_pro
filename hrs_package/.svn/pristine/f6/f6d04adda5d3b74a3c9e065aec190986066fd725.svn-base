package com.hausontech.hrs.daoImpl.etlProcess.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.hausontech.hrs.bean.etlProcess.JobInstanceBean;
import com.hausontech.hrs.daoImpl.BaseRowMapper;

public class EtlJobRowMapper extends BaseRowMapper implements RowMapper<JobInstanceBean> {
	@Override
	public JobInstanceBean mapRow(ResultSet rs, int arg1) throws SQLException {
		JobInstanceBean jobBean = new JobInstanceBean();
		jobBean.setJobId(rs.getInt("JOB_ID"));
		jobBean.setJobName(rs.getString("JOB_NAME"));
		jobBean.setFilePath(rs.getString("FILE_PATH"));
		jobBean.setParamList(rs.getString("PARAM_LIST"));
		// TODO Auto-generated method stub
		return jobBean;
	}

}
