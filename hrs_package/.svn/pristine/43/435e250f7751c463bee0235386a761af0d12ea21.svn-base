package com.hausontech.hrs.daoImpl.allocationManager.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.hausontech.hrs.bean.allocationManager.model.AllocTargetRecord;

public class AllocTargetMapper implements RowMapper<AllocTargetRecord>  {

	@Override
	public AllocTargetRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
		AllocTargetRecord allocTargetRecord=new AllocTargetRecord();
		allocTargetRecord.setTargetId(rs.getInt("TARGET_ID"));
		allocTargetRecord.setAllocRuleId(rs.getInt("RULE_ID"));
		allocTargetRecord.setTargetType(rs.getString("TYPE"));
		allocTargetRecord.setActualFlag(rs.getString("ACTUAL_FLAG"));
		allocTargetRecord.setCurrencyType(rs.getString("CURRENCY_TYPE"));
		allocTargetRecord.setCurrencyCode(rs.getString("CURRENCY_CODE"));
		allocTargetRecord.setAmountType(rs.getString("AMOUNT_TYPE"));
		allocTargetRecord.setDirectionCode(rs.getString("DIRECTION_CODE"));
		allocTargetRecord.setDescription(rs.getString("DESCRIPTION"));
		allocTargetRecord.setCreatedBy(rs.getString("CREATED_BY"));
		allocTargetRecord.setCreationDate(rs.getDate("CREATED_DATE"));
		allocTargetRecord.setLastUpdatedBy(rs.getString("UPDATED_BY"));
		allocTargetRecord.setLastUpdateDate(rs.getDate("UPDATED_DATE"));
		return allocTargetRecord;
	}

}
