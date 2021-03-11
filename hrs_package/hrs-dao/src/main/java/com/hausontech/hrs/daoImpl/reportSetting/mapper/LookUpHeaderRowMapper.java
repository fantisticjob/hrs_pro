package com.hausontech.hrs.daoImpl.reportSetting.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.hausontech.hrs.bean.reportSetting.ItemLookUpHeaderBean;
import com.hausontech.hrs.daoImpl.BaseRowMapper;

public class LookUpHeaderRowMapper extends BaseRowMapper implements RowMapper<ItemLookUpHeaderBean>{

	@Override
	public ItemLookUpHeaderBean mapRow(ResultSet rs, int rowNum) throws SQLException {
		ItemLookUpHeaderBean lookUpHeader = new ItemLookUpHeaderBean();
		lookUpHeader.setLookUpTypeId(rs.getInt("LOOKUP_TYPE_ID"));
		lookUpHeader.setLookUpType(rs.getString("LOOKUP_TYPE"));
		lookUpHeader.setDescription(rs.getString("DESCRIPTION"));
		this.mapAuditColumns(rs, lookUpHeader);
		// TODO Auto-generated method stub
		return lookUpHeader;
	}

}
