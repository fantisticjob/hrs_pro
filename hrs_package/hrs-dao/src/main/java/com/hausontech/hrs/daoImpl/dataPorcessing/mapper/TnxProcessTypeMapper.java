package com.hausontech.hrs.daoImpl.dataPorcessing.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.hausontech.hrs.bean.transactionProcess.TransactionProcessTypeBean;
import com.hausontech.hrs.daoImpl.BaseRowMapper;


public class TnxProcessTypeMapper extends BaseRowMapper implements RowMapper<TransactionProcessTypeBean>{

	@Override
	public TransactionProcessTypeBean mapRow(ResultSet rs, int rowNum) throws SQLException {
		TransactionProcessTypeBean transactionProcessTypeBean=new TransactionProcessTypeBean();
		transactionProcessTypeBean.setCode(rs.getString("LOOKUP_VALUE"));
		transactionProcessTypeBean.setName(rs.getString("DESCRIPTION"));
		return transactionProcessTypeBean;
	}


}
