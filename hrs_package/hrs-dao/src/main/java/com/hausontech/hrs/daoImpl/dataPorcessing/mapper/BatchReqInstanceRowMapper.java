package com.hausontech.hrs.daoImpl.dataPorcessing.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.hausontech.hrs.bean.dataProcess.RequestInstanceBean;
import com.hausontech.hrs.daoImpl.BaseRowMapper;

public class BatchReqInstanceRowMapper extends BaseRowMapper implements RowMapper<RequestInstanceBean> {
	
	@Override
	public RequestInstanceBean mapRow(ResultSet rs, int arg1) throws SQLException {
		RequestInstanceBean instanceBean = new RequestInstanceBean();
		
		instanceBean.setInstanceId(rs.getLong("INSTANCE_ID"));
		instanceBean.setLedgerId(rs.getInt("LEDGER_ID"));
		instanceBean.setFinElementType(rs.getString("FIN_ELEMENT_TYPE"));
		instanceBean.setPeriodFrom(rs.getString("PERIOD_FROM"));
		instanceBean.setPeriodTo(rs.getString("PERIOD_TO"));
		instanceBean.setStartDate(rs.getDate("START_TIME"));
		instanceBean.setEndDate(rs.getDate("END_TIME"));
		instanceBean.setElapsedTime(rs.getLong("ELAPSED_TIME"));
		instanceBean.setStatus(rs.getString("STATUS"));
		instanceBean.setStatus(rs.getString("MESSAGE"));
		this.mapAuditColumns(rs, instanceBean);
		return instanceBean;
	}
}
