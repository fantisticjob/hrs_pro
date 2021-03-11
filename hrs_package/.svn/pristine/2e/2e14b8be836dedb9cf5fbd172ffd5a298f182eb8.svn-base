package com.hausontech.hrs.daoImpl.dimensionManager.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.hausontech.hrs.bean.dimensionManager.DimensionValueBean;
import com.hausontech.hrs.daoImpl.BaseRowMapper;

public class DimensionRowValueMapper  extends BaseRowMapper implements RowMapper<DimensionValueBean>  {

	@Override
	public DimensionValueBean mapRow(ResultSet rs, int rowNum) throws SQLException {
	
			DimensionValueBean dimvalBean = new DimensionValueBean();
			dimvalBean.setDimensionId(rs.getLong("DIMENSION_ID"));
			dimvalBean.setDimensionValueId(rs.getLong("DIM_VALUE_ID"));
			dimvalBean.setDimensionValue(rs.getString("DIM_VALUE"));
			dimvalBean.setDescription(rs.getString("DESCRIPTION"));
			dimvalBean.setAccountType(rs.getString("ACCOUNT_TYPE"));
			dimvalBean.setSummaryFlag(rs.getString("SUMMARY_FLAG"));
			dimvalBean.setDimensionLevel(rs.getString("DIM_LEVEL"));
		    dimvalBean.setStartDate(rs.getDate("START_DATE"));
		    dimvalBean.setStartDate(rs.getDate("END_DATE"));
			this.mapAuditColumns(rs, dimvalBean);
			return dimvalBean;
	}

}