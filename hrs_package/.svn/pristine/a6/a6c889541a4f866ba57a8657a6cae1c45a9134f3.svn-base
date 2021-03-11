package com.hausontech.hrs.daoImpl.dimensionManager.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.hausontech.hrs.bean.dimensionManager.DimensionBean;
import com.hausontech.hrs.daoImpl.BaseRowMapper;

public class DimensionRowMapper extends BaseRowMapper implements RowMapper<DimensionBean> {
	
	@Override
	public DimensionBean mapRow(ResultSet rs, int arg1) throws SQLException {
		DimensionBean dimBean = new DimensionBean();
		dimBean.setDimensionId(rs.getLong("DIMENSION_ID"));
		dimBean.setDimSeq(rs.getLong("DIM_SEQ"));
		dimBean.setDimensionCode(rs.getString("DIMENSION_CODE"));
		dimBean.setDimensionName(rs.getString("DIMENSION_NAME"));
		dimBean.setDimSegment(rs.getString("DIM_SEGMENT"));
		dimBean.setFinAccountFlag(rs.getString("FIN_ACCOUNT_FLAG"));
		this.mapAuditColumns(rs, dimBean);
		return dimBean;
	}
}
