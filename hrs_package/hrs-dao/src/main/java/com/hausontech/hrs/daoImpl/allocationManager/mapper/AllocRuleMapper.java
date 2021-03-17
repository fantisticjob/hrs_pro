package com.hausontech.hrs.daoImpl.allocationManager.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.hausontech.hrs.bean.allocationManager.AllocRuleBean;


public class AllocRuleMapper implements RowMapper<AllocRuleBean> {

	@Override
	public AllocRuleBean mapRow(ResultSet rs, int rowNum) throws SQLException {
           AllocRuleBean allocRuleBean =new AllocRuleBean();
           allocRuleBean.setRuleId(rs.getInt("RULE_ID"));
           allocRuleBean.setLineNum(rs.getInt("LINE_NUM"));
           allocRuleBean.setRuleName(rs.getString("RULE_NAME"));
           allocRuleBean.setDriverType(rs.getString("DRIVER_TYPE"));
           allocRuleBean.setDescription(rs.getString("DESCRIPTION"));
           allocRuleBean.setStartDateActive(rs.getDate("START_DATE_ACTIVE"));
           allocRuleBean.setEndDateActive(rs.getDate("END_DATE_ACTIVE"));
           allocRuleBean.setCreatedBy(rs.getString("CREATED_BY"));
           allocRuleBean.setCreationDate(rs.getDate("CREATED_DATE"));
           allocRuleBean.setLastUpdatedBy(rs.getString("UPDATED_BY"));
           allocRuleBean.setLastUpdateDate(rs.getDate("UPDATED_DATE"));
           return allocRuleBean;
	}

}
