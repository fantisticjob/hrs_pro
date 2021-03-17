package com.hausontech.hrs.api.engine;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.hausontech.hrs.bean.dataProcess.AllocRequestInstanceBean;
import com.hausontech.hrs.bean.dataProcess.AllocRequestInstanceHistoryRecord;
import com.hausontech.hrs.bean.dataProcess.RequestInstanceBean;
import com.hausontech.hrs.bean.engine.CalculationProcessorBean;

public interface ICalculationDao {
	public void generateFinIndex(RequestInstanceBean requestBean) throws SQLException;
	public Map<String, List<CalculationProcessorBean>> getItemContents(RequestInstanceBean requestBean) throws SQLException;
	public int insertCalculationItems(List<CalculationProcessorBean> calculationResultList, String createdUser, long instanceId) throws SQLException;
	public int getItemContentsAlloc(AllocRequestInstanceBean requestBean)throws SQLException;
	public void generateFinIndexAlloc(AllocRequestInstanceBean requestBean)throws SQLException;
	public String getItemContentsAlloc(AllocRequestInstanceHistoryRecord requestHistoryBean) throws SQLException;
	public String getRollbackItemContentsAlloc(AllocRequestInstanceHistoryRecord requestHistoryBean) throws SQLException;

}
