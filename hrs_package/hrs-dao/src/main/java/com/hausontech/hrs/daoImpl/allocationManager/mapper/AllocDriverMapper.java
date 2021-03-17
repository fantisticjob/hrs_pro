package com.hausontech.hrs.daoImpl.allocationManager.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.hausontech.hrs.bean.allocationManager.model.AllocDriverRecord;
public class AllocDriverMapper implements RowMapper<AllocDriverRecord> {

	@Override
	public AllocDriverRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
		AllocDriverRecord allocDriverRecord=new AllocDriverRecord();
		allocDriverRecord.setDriverId(rs.getInt("DRIVER_ID"));
		allocDriverRecord.setRuleId(rs.getInt("RULE_ID"));
		allocDriverRecord.setDriverType(rs.getString("DRIVER_TYPE"));
		allocDriverRecord.setStaticHeaderId(rs.getLong("STATIC_HEADER_ID"));
		allocDriverRecord.setConstant(rs.getDouble("CONSTANT"));
		allocDriverRecord.setActualFlag(rs.getString("ACTUAL_FLAG"));
		allocDriverRecord.setCurrencyType(rs.getString("CURRENCY_TYPE"));
		allocDriverRecord.setCurrencyCode(rs.getString("CURRENCY_CODE"));
		allocDriverRecord.setAmountType(rs.getString("AMOUNT_TYPE"));
		allocDriverRecord.setDirectionCode(rs.getString("DIRECTION_CODE"));
		allocDriverRecord.setDescription(rs.getString("DESCRIPTION"));
		allocDriverRecord.setCreatedBy(rs.getString("CREATED_BY"));
		allocDriverRecord.setCreationDate(rs.getDate("CREATED_DATE"));
		allocDriverRecord.setLastUpdatedBy(rs.getString("UPDATED_BY"));
		allocDriverRecord.setLastUpdateDate(rs.getDate("UPDATED_DATE"));
		return allocDriverRecord;
	}

}
