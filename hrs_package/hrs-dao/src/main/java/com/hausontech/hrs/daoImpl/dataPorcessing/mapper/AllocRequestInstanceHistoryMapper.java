package com.hausontech.hrs.daoImpl.dataPorcessing.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.hausontech.hrs.bean.dataProcess.AllocRequestInstanceHistoryRecord;
import com.hausontech.hrs.bean.dataProcess.AllocRequestInstanceRecord;
import com.hausontech.hrs.daoImpl.BaseRowMapper;

public class AllocRequestInstanceHistoryMapper  extends BaseRowMapper  implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		AllocRequestInstanceHistoryRecord instanceHistoryBean =new AllocRequestInstanceHistoryRecord();
		instanceHistoryBean.setInstanceId(rs.getLong("INSTANCE_ID"));
		instanceHistoryBean.setHistoryId(rs.getLong("HISTORY_ID"));
		instanceHistoryBean.setRuleId(rs.getInt("RULE_ID"));
		instanceHistoryBean.setPeriod(rs.getString("PERIOD"));
        instanceHistoryBean.setStartTime(rs.getDate("START_TIME"));
        instanceHistoryBean.setEndTime(rs.getDate("END_TIME"));
        instanceHistoryBean.setElapsedTime(rs.getLong("ELAPSED_TIME"));
        instanceHistoryBean.setStatus(rs.getString("STATUS"));
        instanceHistoryBean.setMessage(rs.getString("MESSAGE"));
        instanceHistoryBean.setRollbackTime(rs.getDate("ROLLBACK_TIME"));
		this.mapAuditColumns(rs, instanceHistoryBean);
		return instanceHistoryBean;
	}

}
