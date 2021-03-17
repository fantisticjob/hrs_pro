package com.hausontech.hrs.daoImpl.allocationManager.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.hausontech.hrs.bean.allocationManager.AllocRulesGroupHaeaderBean;

public class AllocRulesGroupHaeaderMapper implements RowMapper<AllocRulesGroupHaeaderBean> {

	@Override
	public AllocRulesGroupHaeaderBean mapRow(ResultSet rs, int rowNum) throws SQLException {
		AllocRulesGroupHaeaderBean allocRulesGroupHaeaderBean =new AllocRulesGroupHaeaderBean();
		allocRulesGroupHaeaderBean.setGroupHeaderId(rs.getInt("GROUP_HEADER_ID"));
		allocRulesGroupHaeaderBean.setGroupNum(rs.getInt("GROUP_NUM"));
		allocRulesGroupHaeaderBean.setGroupName(rs.getString("GROUP_NAME"));
		allocRulesGroupHaeaderBean.setStartDateActive(rs.getDate("START_DATE_ACTIVE"));
		allocRulesGroupHaeaderBean.setEndDateActive(rs.getDate("END_DATE_ACTIVE"));
		allocRulesGroupHaeaderBean.setDescription(rs.getString("DESCRIPTION"));
		allocRulesGroupHaeaderBean.setCreatedBy(rs.getString("CREATED_BY"));
		allocRulesGroupHaeaderBean.setCreationDate(rs.getDate("CREATED_DATE"));
		allocRulesGroupHaeaderBean.setLastUpdatedBy(rs.getString("UPDATED_BY"));
		allocRulesGroupHaeaderBean.setLastUpdateDate(rs.getDate("UPDATED_DATE"));
		return allocRulesGroupHaeaderBean;
	}

}
