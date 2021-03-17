package com.hausontech.hrs.daoImpl.allocationManager.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.hausontech.hrs.bean.allocationManager.AllocSourceBean;


public class AllocSourceMapper  implements RowMapper<AllocSourceBean>  {

	@Override
	public AllocSourceBean mapRow(ResultSet rs, int rowNum) throws SQLException {
		AllocSourceBean allocSourceBean=new AllocSourceBean();
		allocSourceBean.setSourceId(rs.getInt("SOURCE_ID"));
		allocSourceBean.setRuleId(rs.getInt("RULE_ID"));
		allocSourceBean.setSourceType(rs.getString("SOURCE_TYPE"));
		allocSourceBean.setConstant(rs.getDouble("CONSTANT"));
		allocSourceBean.setActualFlag(rs.getString("ACTUAL_FLAG"));
		allocSourceBean.setCurrencyType(rs.getString("CURRENCY_TYPE"));
		allocSourceBean.setCurrencyCode(rs.getString("CURRENCY_CODE"));
		allocSourceBean.setAmountType(rs.getString("AMOUNT_TYPE"));
		allocSourceBean.setDirectionCode(rs.getString("DIRECTION_CODE"));
		allocSourceBean.setOperator(rs.getString("OPERATOR"));
		allocSourceBean.setDescription(rs.getString("DESCRIPTION"));
		allocSourceBean.setCreatedBy(rs.getString("CREATED_BY"));
		allocSourceBean.setCreationDate(rs.getDate("CREATED_DATE"));
		allocSourceBean.setLastUpdatedBy(rs.getString("UPDATED_BY"));
		allocSourceBean.setLastUpdateDate(rs.getDate("UPDATED_DATE"));
		return allocSourceBean;
	}

}
