package com.hausontech.hrs.api.allocationManager.service;

import java.util.List;
import java.util.Map;

import com.hausontech.hrs.bean.allocationManager.model.AllocDriverRecord;
import com.hausontech.hrs.bean.allocationManager.model.AllocRuleRecord;
import com.hausontech.hrs.bean.allocationManager.model.AllocSourceRecord;
import com.hausontech.hrs.bean.allocationManager.model.AllocTargetRecord;
import com.hausontech.hrs.bean.userManager.pojo.EasyuiDataGrid;
import com.hausontech.hrs.bean.userManager.pojo.EasyuiDataGridJson;

public interface IAllocRuleConfigService {
	
	public List<Map<String, Object>> getAllocRuleDataGrid(
			EasyuiDataGrid dg, AllocRuleRecord allocRuleRecord);
	
	public AllocRuleRecord getAllocRuleByKey(long ruleId);
	
	public AllocRuleRecord addAllocRule(AllocRuleRecord allocRule);
	
	public AllocRuleRecord updateAllocRule(AllocRuleRecord allocRule);
	
	
	public void deleteAllocRule(AllocRuleRecord allocRule);
	
	public List<Map<String, Object>> getAllocSourceDataGrid(
			EasyuiDataGrid dg, AllocSourceRecord allocSourceRecord);
	
	public AllocSourceRecord getAllocSourceByKey(long ruleSourceId);
	
	public AllocSourceRecord updateAllocSource(AllocSourceRecord allocSourceRecord);
	
	public AllocSourceRecord getAllocSourceByRuleId(long ruleId);
	
	public AllocSourceRecord addAllocSource(AllocSourceRecord allocSource);

	public AllocTargetRecord getAllocTargetByRuleId(long allocRuleId);

	public AllocTargetRecord addAllocTarget(AllocTargetRecord allocTarget);

	public List<Map<String, Object>> getAllocTargetDataGrid(EasyuiDataGrid dg, AllocTargetRecord allocTargetRecord);

	public AllocTargetRecord updateAllocTarget(AllocTargetRecord allocTargetRecord);
	
	public List<AllocDriverRecord> getAllocDriverDataGrid(
			EasyuiDataGrid dg, AllocDriverRecord allocDriverRecord);
	
	public AllocDriverRecord updateAllocDriver(AllocDriverRecord allocDriverRecord);
	
	public AllocDriverRecord getAllocDriverByRuleId(long ruleId);
	
	public AllocDriverRecord addAllocDriver(AllocDriverRecord allocDriver);

	public int getAllocRuleDataGridCount(AllocRuleRecord allocRuleRecord);
}
