package com.hausontech.hrs.daoImpl.dataPorcessing.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.hausontech.hrs.bean.dataProcess.AllocRequestInstanceBean;
import com.hausontech.hrs.bean.dataProcess.AllocRequestInstanceRecord;
import com.hausontech.hrs.bean.dataProcess.AllocScheduleInstanceBean;
import com.hausontech.hrs.daoImpl.BaseRowMapper;

public class AllocScheduleInstanceMapper1   extends BaseRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		AllocScheduleInstanceBean instanceBean =new AllocScheduleInstanceBean();
		instanceBean.setScheduleId(rs.getInt("SCHEDULE_ID"));
		instanceBean.setInstanceId(rs.getLong("INSTANCE_ID"));
		instanceBean.setTypeProcess(rs.getString("TYPE_PROCESS"));
		instanceBean.setValueProcess(rs.getString("VALUE_PROCESS"));
		instanceBean.setConcurrent(rs.getString("CONCURRENT"));
		return instanceBean;
	}

}
