package com.hausontech.hrs.daoImpl.etlProcess.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.hausontech.hrs.bean.etlProcess.ScheduleInstanceBean;
import com.hausontech.hrs.daoImpl.BaseRowMapper;

public class EtlScheduleRowMapper extends BaseRowMapper implements RowMapper<ScheduleInstanceBean> {
	@Override
	public ScheduleInstanceBean mapRow(ResultSet rs, int arg1) throws SQLException {
		ScheduleInstanceBean scheduleBean = new ScheduleInstanceBean();
		scheduleBean.setJobId(rs.getInt("JOB_ID"));
		scheduleBean.setTypeProcess(rs.getString("TYPE_PROCESS"));
		scheduleBean.setValueProcess(rs.getString("VALUE_PROCESS"));
		// TODO Auto-generated method stub
		return scheduleBean;
	}

}
