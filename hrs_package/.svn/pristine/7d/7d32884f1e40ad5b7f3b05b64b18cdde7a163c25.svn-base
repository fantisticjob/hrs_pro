package com.hausontech.hrs.daoImpl.dataPorcessing.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.hausontech.hrs.bean.dataProcess.AllocRequestInstanceBean;
import com.hausontech.hrs.daoImpl.BaseRowMapper;

public class AllocBatchReqInstanceRowMapper  extends BaseRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		AllocRequestInstanceBean instanceBean =new AllocRequestInstanceBean();
		instanceBean.setInstanceId(rs.getLong("INSTANCE_ID"));
		instanceBean.setRuleId(rs.getInt("RULE_ID"));
		instanceBean.setRuleType(rs.getString("RULE_TYPE"));
		instanceBean.setPeriod(rs.getString("PERIOD"));
		instanceBean.setStartDate(rs.getDate("START_TIME"));
		instanceBean.setEndDate(rs.getDate("END_TIME"));
		instanceBean.setElapsedTime(rs.getLong("ELAPSED_TIME"));
		instanceBean.setStatus(rs.getString("STATUS"));
		instanceBean.setStatus(rs.getString("MESSAGE"));
		this.mapAuditColumns(rs, instanceBean);
		return instanceBean;
	}

}
