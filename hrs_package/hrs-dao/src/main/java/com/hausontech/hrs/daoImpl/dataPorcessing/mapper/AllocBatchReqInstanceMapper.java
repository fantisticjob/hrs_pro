package com.hausontech.hrs.daoImpl.dataPorcessing.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.hausontech.hrs.bean.dataProcess.AllocRequestInstanceBean;
import com.hausontech.hrs.bean.dataProcess.AllocRequestInstanceRecord;
import com.hausontech.hrs.daoImpl.BaseRowMapper;

public class AllocBatchReqInstanceMapper   extends BaseRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		AllocRequestInstanceRecord instanceBean =new AllocRequestInstanceRecord();
		instanceBean.setInstanceId(rs.getLong("INSTANCE_ID"));
		instanceBean.setRuleId(rs.getInt("RULE_ID"));
		instanceBean.setRuleType(rs.getString("RULE_TYPE"));
		instanceBean.setPeriod(rs.getString("PERIOD"));
		instanceBean.setDescription(rs.getString("DESCRIPTION"));
		this.mapAuditColumns(rs, instanceBean);
		return instanceBean;
	}

}
