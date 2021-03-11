package com.hausontech.hrs.daoImpl.reportSetting.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.hausontech.hrs.bean.reportSetting.DimensionValueSetBean;

public class DimValueSetRowMapper implements RowMapper<DimensionValueSetBean> {

	@Override
	public DimensionValueSetBean mapRow(ResultSet rs, int arg1) throws SQLException {
		DimensionValueSetBean dimValBean = new DimensionValueSetBean();
		dimValBean.setDimensionId(rs.getInt("DIMENSION_ID"));
		dimValBean.setDimensionCode(rs.getString("DIMENSION_CODE"));
		dimValBean.setDimensionDescription(rs.getString("DIMENSION_NAME"));
		dimValBean.setDimSegment(rs.getString("DIM_SEGMENT"));
		
		dimValBean.setDimValueId(rs.getInt("DIM_VALUE_ID"));
		dimValBean.setDimValue(rs.getString("DIM_VALUE"));
		dimValBean.setDimValDescription(rs.getString("DESCRIPTION"));
		dimValBean.setSummaryFlag(rs.getString("SUMMARY_FLAG"));
		return dimValBean;
	}

}
