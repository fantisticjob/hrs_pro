package com.hausontech.hrs.daoImpl.reportSetting.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.hausontech.hrs.bean.reportSetting.ItemCodeExtHeaderBean;
import com.hausontech.hrs.daoImpl.BaseRowMapper;




public class CodeExtHeaderRowMapper extends BaseRowMapper implements RowMapper<ItemCodeExtHeaderBean> {

	@Override
	public ItemCodeExtHeaderBean mapRow(ResultSet rs, int rowNum) throws SQLException {
		ItemCodeExtHeaderBean codeHeader = new ItemCodeExtHeaderBean();
		codeHeader.setExtItemTypeId(rs.getInt("ITEM_TYPE_ID"));
		codeHeader.setExtItemType(rs.getString("ITEM_TYPE"));
		codeHeader.setDescription(rs.getString("DESCRIPTION"));
		this.mapAuditColumns(rs, codeHeader);
		// TODO Auto-generated method stub
		return codeHeader;
	}
	
	
	
	

}
