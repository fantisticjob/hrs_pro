package com.hausontech.hrs.daoImpl.dataPorcessing.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.hausontech.hrs.bean.dataProcess.PeriodBean;
import com.hausontech.hrs.daoImpl.BaseRowMapper;

public class PeriodRowMapper extends BaseRowMapper implements RowMapper<PeriodBean> {

	@Override
	public PeriodBean mapRow(ResultSet rs, int arg1) throws SQLException {
		PeriodBean periodBean = new PeriodBean();
		periodBean.setPeriodName(rs.getString("PERIOD_NAME"));
		periodBean.setPeriodYear(rs.getInt("PERIOD_YEAR"));
		periodBean.setPeriodNum(rs.getInt("PERIOD_NUM"));
		periodBean.setQuarterNum(rs.getInt("QUARTER_NUM"));
		periodBean.setAdjFlag(rs.getString("ADJ_FLAG"));
		this.mapAuditColumns(rs, periodBean);
		return periodBean;
	}

}
