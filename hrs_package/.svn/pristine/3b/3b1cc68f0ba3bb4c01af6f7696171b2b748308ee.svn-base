package com.hausontech.hrs.daoImpl.reportSetting.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.hausontech.hrs.bean.reportSetting.ItemHeaderBean;
import com.hausontech.hrs.daoImpl.BaseRowMapper;

public class ItemHeaderRowMapper extends BaseRowMapper implements RowMapper<ItemHeaderBean> {

	@Override
	public ItemHeaderBean mapRow(ResultSet rs, int arg1) throws SQLException {
		ItemHeaderBean itemHeader = new ItemHeaderBean();
		itemHeader.setItemHeaderId(rs.getLong("ITEM_HEADER_ID"));
		itemHeader.setItemCode(rs.getString("ITEM_CODE"));
		itemHeader.setItemDescription(rs.getString("DESCRIPTION"));
		itemHeader.setRuleCode(rs.getString("RULE_CODE"));
		this.mapAuditColumns(rs, itemHeader);
		// TODO Auto-generated method stub
		return itemHeader;
	}

}
