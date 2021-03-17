package com.hausontech.hrs.daoImpl.reportSetting.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.hausontech.hrs.bean.reportSetting.ItemRowSetLineBean;
import com.hausontech.hrs.daoImpl.BaseRowMapper;

public class RowSetLineRowMapper extends BaseRowMapper implements RowMapper<ItemRowSetLineBean> {

	@Override
	public ItemRowSetLineBean mapRow(ResultSet rs, int rowNum) throws SQLException {
		ItemRowSetLineBean codeHeader = new ItemRowSetLineBean();
		codeHeader.setRowId(rs.getInt("ROW_ID"));
		codeHeader.setRowSetId(rs.getInt("ROW_SET_ID"));
		codeHeader.setLineNum(rs.getInt("LINE_NUM"));
		codeHeader.setRowName(rs.getString("ROW_NAME"));
		codeHeader.setRowNum(rs.getInt("ROW_NUM"));
		codeHeader.setChangeSign(rs.getString("CHANGE_SIGN"));
		codeHeader.setDisplayFlag(rs.getString("DISPLAY_FLAG"));
		codeHeader.setExternalCode(rs.getString("EXTERNAL_CODE"));
		
		codeHeader.setAttribute1(rs.getString("ATTRIBUTE1"));
		codeHeader.setAttribute2(rs.getString("ATTRIBUTE2"));
		codeHeader.setAttribute3(rs.getString("ATTRIBUTE3"));
		codeHeader.setAttribute4(rs.getString("ATTRIBUTE4"));
		codeHeader.setAttribute5(rs.getString("ATTRIBUTE5"));
		codeHeader.setRuleCode(rs.getString("RULE_CODE"));		
		this.mapAuditColumns(rs, codeHeader);
		
		// TODO Auto-generated method stub
		return codeHeader;
	}

}
