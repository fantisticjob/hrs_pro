package com.hausontech.hrs.daoImpl.dataPorcessing.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.hausontech.hrs.bean.dataProcess.LedgerBean;
import com.hausontech.hrs.daoImpl.BaseRowMapper;

public class LedgerRowMapper  extends BaseRowMapper implements RowMapper<LedgerBean> {
	@Override
	public LedgerBean mapRow(ResultSet rs, int arg1) throws SQLException {
		LedgerBean legerBean = new LedgerBean();
		legerBean.setLederCode(rs.getString("LOOKUP_VALUE"));
		legerBean.setLederName(rs.getString("DESCRIPTION"));
		return legerBean;
	}
}
