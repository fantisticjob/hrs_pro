package com.hausontech.hrs.daoImpl.reportSetting.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.hausontech.hrs.bean.reportSetting.ItemRowCalculationBean;
import com.hausontech.hrs.daoImpl.BaseRowMapper;

public class RuleSetCalRowMapper extends BaseRowMapper implements RowMapper<ItemRowCalculationBean> {
	@Override
	public ItemRowCalculationBean mapRow(ResultSet rs, int arg1) throws SQLException {
		ItemRowCalculationBean ruleHeader = new ItemRowCalculationBean();
		ruleHeader.setRowCalId(rs.getInt("ROW_CAL_ID"));
		ruleHeader.setRowId(rs.getLong("ROW_ID"));
		ruleHeader.setRowCalNum(rs.getLong("ROW_CAL_NUM"));
		ruleHeader.setSign(rs.getString("SIGN"));
		ruleHeader.setCalItemCode(rs.getString("CAL_ITEM_CODE"));
		// TODO Auto-generated method stub
		return ruleHeader;
	}

}
