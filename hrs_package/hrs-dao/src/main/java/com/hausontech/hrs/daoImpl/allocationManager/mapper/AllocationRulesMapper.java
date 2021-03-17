package com.hausontech.hrs.daoImpl.allocationManager.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.hausontech.hrs.bean.allocationManager.DimFilterHeaderBean;
import com.hausontech.hrs.daoImpl.BaseRowMapper;

public class AllocationRulesMapper extends BaseRowMapper implements RowMapper<DimFilterHeaderBean> {
	public DimFilterHeaderBean mapRow(ResultSet rs, int rowNum) throws SQLException {
		DimFilterHeaderBean dimFilterHeaderBean = new DimFilterHeaderBean();

		dimFilterHeaderBean.setFilterHeaderId(rs.getInt("FILTER_HEADER_ID"));
		dimFilterHeaderBean.setFilterHeaderName(rs.getString("FILTER_HEADER_NAME"));
		dimFilterHeaderBean.setDimensionSegment(rs.getString("DIMENSION_SEGMENT"));
		dimFilterHeaderBean.setType(rs.getString("TYPE"));
		dimFilterHeaderBean.setDescription(rs.getString("DESCRIPTION"));

		return dimFilterHeaderBean;
	}
}
