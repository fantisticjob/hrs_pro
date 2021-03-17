package com.hausontech.hrs.api.allocationManager.dao;

import com.hausontech.hrs.bean.allocationManager.model.AllocDriverRecord;
import com.hausontech.hrs.bean.allocationManager.model.AllocRuleRecord;
import com.hausontech.hrs.bean.allocationManager.model.AllocSourceRecord;
import com.hausontech.hrs.bean.allocationManager.model.AllocTargetRecord;
import com.hausontech.hrs.bean.userManager.pojo.EasyuiDataGrid;
import com.hausontech.hrs.bean.userManager.pojo.EasyuiDataGridJson;

public interface IAllocRuleConfigDao {
	
	public EasyuiDataGridJson getAllocRuleDataGrid(
			EasyuiDataGrid dg, AllocRuleRecord allocRuleRecord);
	
	public AllocRuleRecord getAllocRuleByKey(long ruleId);
	
	public AllocRuleRecord addAllocRule(AllocRuleRecord allocRule); 

	public AllocRuleRecord updateAllocRule(AllocRuleRecord allocRule);
	
	
	public void deleteAllocRule(AllocRuleRecord allocRule);
	
	public EasyuiDataGridJson getAllocSourceDataGrid(
			EasyuiDataGrid dg, AllocSourceRecord allocSourceRecord);
	
	public AllocSourceRecord getAllocSourceByKey(long ruleSourceId);
	
	public AllocSourceRecord updateAllocSource(AllocSourceRecord allocSource);
	
	public AllocSourceRecord getAllocSourceByRuleId(long ruleId);
	
	public AllocSourceRecord addAllocSource(AllocSourceRecord allocSource);

	public AllocTargetRecord getAllocTargetByRuleId(long allocRuleId);

	public AllocTargetRecord addAllocTarget(AllocTargetRecord allocTarget);
	 
	public EasyuiDataGridJson getAllocTargetDataGrid(EasyuiDataGrid dg, AllocTargetRecord allocTargetRecord);

	public AllocTargetRecord updateAllocTarget(AllocTargetRecord allocTargetRecord);
	
	public EasyuiDataGridJson getAllocDriverDataGrid(
			EasyuiDataGrid dg, AllocDriverRecord allocDriverRecord);
	
	
	public AllocDriverRecord updateAllocDriver(AllocDriverRecord allocDriver);
	
	public AllocDriverRecord getAllocDriverByRuleId(long ruleId);
	
	public AllocDriverRecord addAllocDriver(AllocDriverRecord allocDriver);

}
