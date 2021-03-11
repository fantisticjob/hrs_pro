package com.hausontech.hrs.daoImpl.reportSetting.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.hausontech.hrs.bean.reportSetting.ItemRowSetHeaderBean;
import com.hausontech.hrs.daoImpl.BaseRowMapper;

public class RowSetHeaderRowMapper extends BaseRowMapper implements RowMapper<ItemRowSetHeaderBean> {

	@Override
	public ItemRowSetHeaderBean mapRow(ResultSet rs, int rowNum) throws SQLException {
		ItemRowSetHeaderBean codeHeader = new ItemRowSetHeaderBean();
		codeHeader.setRowSetId(rs.getInt("ROW_SET_ID"));
		codeHeader.setRowSetName(rs.getString("ROW_SET_NAME"));
		codeHeader.setRuleCode(rs.getString("RULE_CODE"));
		codeHeader.setExtItemType(rs.getString("EXT_ITEM_TYPE"));
		codeHeader.setDescription(rs.getString("DESCRIPTION"));
		this.mapAuditColumns(rs, codeHeader);
		// TODO Auto-generated method stub
		return codeHeader;
	}

}
