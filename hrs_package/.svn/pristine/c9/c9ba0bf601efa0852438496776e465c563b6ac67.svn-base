package com.hausontech.hrs.daoImpl.allocationManager.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.hausontech.hrs.bean.allocationManager.DimFilterHeaderBean;
import com.hausontech.hrs.bean.allocationManager.DriverStaticHeader;



public class DriverStaticHeaderMapper  implements RowMapper<DriverStaticHeader> {

	@Override
	public DriverStaticHeader mapRow(ResultSet rs, int rowNum) throws SQLException {
		DriverStaticHeader driverStaticHeader =new DriverStaticHeader();
		driverStaticHeader.setStaticHeaderId(rs.getInt("STATIC_HEADER_ID"));
		driverStaticHeader.setDriverCode(rs.getString("DRIVER_CODE"));
		driverStaticHeader.setDescription(rs.getString("DESCRIPTION"));
		driverStaticHeader.setDimensionSegment(rs.getString("DIMENSION_SEGMENT"));
		driverStaticHeader.setStartDateActive(rs.getDate("START_DATE_ACTIVE"));
		driverStaticHeader.setEndDateActive(rs.getDate("END_DATE_ACTIVE"));
		driverStaticHeader.setCreatedBy(rs.getString("CREATED_BY"));
		driverStaticHeader.setCreationDate(rs.getDate("CREATED_DATE"));
		driverStaticHeader.setLastUpdatedBy(rs.getString("LAST_UPDATED_BY"));
		driverStaticHeader.setLastUpdateDate(rs.getDate("LAST_UPDATED_DATE"));
		return driverStaticHeader;
	}

}
