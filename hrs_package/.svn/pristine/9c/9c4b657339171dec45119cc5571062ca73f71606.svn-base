package com.hausontech.hrs.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hausontech.hrs.bean.AuditBean;

public class BaseRowMapper {
	
	protected void mapAuditColumns(ResultSet rs, AuditBean auditBean) throws SQLException {
		if (rs != null && auditBean != null) {
			auditBean.setCreationDate(rs.getDate("CREATION_DATE"));
			auditBean.setCreatedBy(rs.getString("CREATED_BY"));
			auditBean.setLastUpdateDate(rs.getDate("CREATION_DATE"));
			auditBean.setLastUpdatedBy(rs.getString("LAST_UPDATE_DATE"));
		}
	}
}
