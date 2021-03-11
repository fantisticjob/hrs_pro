package com.hausontech.hrs.daoImpl.reportSetting.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.hausontech.hrs.bean.reportSetting.ItemGroupRuleHeaderBean;
import com.hausontech.hrs.daoImpl.BaseRowMapper;

public class RuleHeaderRowMapper extends BaseRowMapper implements RowMapper<ItemGroupRuleHeaderBean> {
	@Override
	public ItemGroupRuleHeaderBean mapRow(ResultSet rs, int arg1) throws SQLException {
		ItemGroupRuleHeaderBean ruleHeader = new ItemGroupRuleHeaderBean();
		ruleHeader.setRuleHeaderId(rs.getInt("RULE_HEADER_ID"));
		ruleHeader.setRuleCode(rs.getString("RULE_CODE"));
		ruleHeader.setDescription(rs.getString("DESCRIPTION"));
		this.mapAuditColumns(rs, ruleHeader);
		// TODO Auto-generated method stub
		return ruleHeader;
	}

}
