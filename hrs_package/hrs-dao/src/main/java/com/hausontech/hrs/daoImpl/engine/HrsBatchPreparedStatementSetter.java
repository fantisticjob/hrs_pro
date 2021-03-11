package com.hausontech.hrs.daoImpl.engine;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import com.hausontech.hrs.bean.engine.CalculationProcessorBean;
import com.hausontech.hrs.utils.DateUtil;

public class HrsBatchPreparedStatementSetter implements BatchPreparedStatementSetter {

	private List<CalculationProcessorBean> calculationResultList;
	
    public HrsBatchPreparedStatementSetter(List<CalculationProcessorBean> processList){  
    	calculationResultList = processList;  
    } 
    public HrsBatchPreparedStatementSetter(){  
    	calculationResultList = new ArrayList<CalculationProcessorBean>();  
    }  
	
	@Override
	public void setValues(PreparedStatement ps, int i) throws SQLException {
		if (calculationResultList != null && !calculationResultList.isEmpty()) {
			CalculationProcessorBean calProcessBean = calculationResultList.get(i);
			if (calProcessBean != null) {
				setInsertParameters(ps, calProcessBean);
			}
		}	
	}
	@Override
	public int getBatchSize() {
		return calculationResultList.size();
	}
	private void setInsertParameters(PreparedStatement stmt, CalculationProcessorBean calBean) throws SQLException {
		int i = 1;
		// set items code
		stmt.setString(i++, calBean.getItemCode());
		// set item group code
		stmt.setString(i++, calBean.getRuleCode());
		// set item financial element
		stmt.setString(i++, calBean.getFinElement());
		// set ledger id
		stmt.setLong(i++, calBean.getLedgerId());
		// set actual flag
		stmt.setString(i++, calBean.getActualFlag());
		// set currency type
		stmt.setString(i++, calBean.getCurrencyType());
		// set currency code
		stmt.setString(i++, calBean.getCurrencyCode());
		// set segment 1
		stmt.setString(i++, calBean.getSegment1());
		// set segment 2
		stmt.setString(i++, calBean.getSegment2());
		// set segment 3
		stmt.setString(i++, calBean.getSegment3());
		// set segment 4
		stmt.setString(i++, calBean.getSegment4());
		// set segment 5
		stmt.setString(i++, calBean.getSegment5());
		// set segment 6
		stmt.setString(i++, calBean.getSegment6());
		// set segment 7
		stmt.setString(i++, calBean.getSegment7());
		// set segment 8
		stmt.setString(i++, calBean.getSegment8());
		// set segment 9
		stmt.setString(i++, calBean.getSegment9());
		// set segment 10
		stmt.setString(i++, calBean.getSegment10());
		// set segment 11
		stmt.setString(i++, calBean.getSegment11());
		// set segment 12
		stmt.setString(i++, calBean.getSegment12());
		// set segment 13
		stmt.setString(i++, calBean.getSegment13());
		// set segment 14
		stmt.setString(i++, calBean.getSegment14());
		// set segment 15
		stmt.setString(i++, calBean.getSegment15());
		// set segment 16
		stmt.setString(i++, calBean.getSegment16());
		// set segment 17
		stmt.setString(i++, calBean.getSegment17());
		// set segment 18
		stmt.setString(i++, calBean.getSegment18());
		// set segment 19
		stmt.setString(i++, calBean.getSegment19());
		// set segment 20
		stmt.setString(i++, calBean.getSegment20());
		// set period name
		stmt.setString(i++, calBean.getPeriodName());
		// set period number
		stmt.setString(i++, calBean.getPeriodNumber());
		// set period year
		stmt.setString(i++, calBean.getPeriodYear());
		// set begin balance dr
		stmt.setDouble(i++, new BigDecimal(calBean.getBeginBalanceDR()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
		// set begin balance cr
		stmt.setDouble(i++, new BigDecimal(calBean.getBeginBalanceCR()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
		// set PERIOD_NET_DR
		stmt.setDouble(i++, new BigDecimal(calBean.getPeriodDR()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
		// set PERIOD_NET_CR
		stmt.setDouble(i++, new BigDecimal(calBean.getPeriodCR()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
		// set end balance dr
		stmt.setDouble(i++, new BigDecimal(calBean.getEndBalanceDR()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
		// set end balance cr
		stmt.setDouble(i++,new BigDecimal(calBean.getEndBalanceCR()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
		// set request instance Id
		stmt.setLong(i++, calBean.getRequestInstanceId());
		// set create date
		stmt.setDate(i++, DateUtil.toSqlDate(new Date()));
		// set created by user
		stmt.setString(i++, calBean.getCreatedBy());
		// set update
		stmt.setDate(i++, DateUtil.toSqlDate(new Date()));
		// set updated by user
		stmt.setString(i++, calBean.getUpdatedBy());
	}
}
