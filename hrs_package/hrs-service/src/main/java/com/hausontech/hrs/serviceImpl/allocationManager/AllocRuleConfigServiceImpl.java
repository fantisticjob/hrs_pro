package com.hausontech.hrs.serviceImpl.allocationManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hausontech.hrs.api.allocationManager.dao.IAllocRuleConfigDao;
import com.hausontech.hrs.api.allocationManager.service.IAllocRuleConfigService;
import com.hausontech.hrs.bean.allocationManager.model.AllocDriverRecord;
import com.hausontech.hrs.bean.allocationManager.model.AllocRuleRecord;
import com.hausontech.hrs.bean.allocationManager.model.AllocSourceRecord;
import com.hausontech.hrs.bean.allocationManager.model.AllocTargetRecord;
import com.hausontech.hrs.bean.userManager.pojo.EasyuiDataGrid;
import com.hausontech.hrs.bean.userManager.pojo.EasyuiDataGridJson;
import com.hausontech.hrs.daoImpl.IBaseDao2;
import com.hausontech.hrs.daoImpl.allocationManager.mapper.HaeDimFilterHeaderMapper;

@Service("allocRuleService")
public class AllocRuleConfigServiceImpl implements IAllocRuleConfigService {
	
	//private IAllocRuleConfigDao allocRuleDao;
	
	@Autowired
	private HaeDimFilterHeaderMapper  haeFilterHeaderMapper;
	
	
	public HaeDimFilterHeaderMapper getHaeFilterHeaderMapper() {
		return haeFilterHeaderMapper;
	}

	public void setHaeFilterHeaderMapper(HaeDimFilterHeaderMapper haeFilterHeaderMapper) {
		this.haeFilterHeaderMapper = haeFilterHeaderMapper;
	}

	/*public IAllocRuleConfigDao getAllocRuleDao() {
		return allocRuleDao;
	}*/

	@Autowired
	private IBaseDao2 baseDao2;
	public IBaseDao2 getBaseDao2() {
		return baseDao2;
	}

	public void setBaseDao2(IBaseDao2 baseDao2) {
		this.baseDao2 = baseDao2;
	}
	
	/*@Autowired
	public void setAllocRuleDao(IAllocRuleConfigDao allocRuleDao) {
		this.allocRuleDao = allocRuleDao;
	}*/
		
	@Override
	@Transactional(readOnly = true)
	public List<Map<String, Object>> getAllocRuleDataGrid(
			EasyuiDataGrid dg, AllocRuleRecord allocRuleRecord) {
		Map<String, Object> map=null;
		List<Map<String, Object>> result = new ArrayList();
		List<AllocRuleRecord> result1 = null;
		String start=String.valueOf(allocRuleRecord.getRowStartIndex());
		String count=String.valueOf(allocRuleRecord.getRowCount());
		String ruleName=allocRuleRecord.getRuleName();
		try {
			result1 = haeFilterHeaderMapper.getAllocRuleDataGrid(start,ruleName,count);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < result1.size(); i++) {
			map=new HashMap<String, Object>();
			map.put("lineNumber",result1.get(i).getLineNumber());
			map.put("ruleName",result1.get(i).getRuleName());
			map.put("driverType", result1.get(i).getDriverType());
			map.put("startDateActive", result1.get(i).getStartDateActive());
			map.put("endDateActive", result1.get(i).getEndDateActive());
			map.put("source", result1.get(i).getSourceId());
			map.put("target", result1.get(i).getTargetId());
			map.put("allocRuleId", result1.get(i).getAllocRuleId());
			map.put("isSourceOver", result1.get(i).getIsSourceOver());
			map.put("isDriverOver", result1.get(i).getIsDriverOver());
			map.put("DESCRIPTION", result1.get(i).getDescription());
			map.put("creationDate", result1.get(i).getCreationDate());
			map.put("createdBy", result1.get(i).getCreatedBy());
			map.put("lastUpdateDate", result1.get(i).getLastUpdateDate());
			map.put("lastUpdatedBy", result1.get(i).getLastUpdatedBy());	
			result.add(map);
			}
		return result;
	}

	@Override
	public AllocRuleRecord addAllocRule(AllocRuleRecord allocRule)  {		
		long primaryKey = (long) baseDao2.getAutoGeneratedPrimaryKey("HAE_ALLOC_RULE_S");
		allocRule.setAllocRuleId(primaryKey);
		try {
			int result =haeFilterHeaderMapper.addAllocRule(allocRule);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return allocRule;
	}

	@Override
	public AllocRuleRecord updateAllocRule(AllocRuleRecord allocRule) {		
		try {
			int result=haeFilterHeaderMapper.updateAllocRule(allocRule);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return allocRule;
	}

	@Override
	public void deleteAllocRule(AllocRuleRecord allocRule) {
		try {
			haeFilterHeaderMapper.deleteAllocRule(allocRule.getAllocRuleId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public AllocRuleRecord getAllocRuleByKey(long ruleId) {
		// TODO Auto-generated method stub
		AllocRuleRecord record=null;
		try {
			record= haeFilterHeaderMapper.getAllocRuleByKey(ruleId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return record;
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public List<Map<String, Object>> getAllocSourceDataGrid(
			EasyuiDataGrid dg, AllocSourceRecord allocSourceRecord) {
		Map<String, Object> map=null;
		List<Map<String, Object>> result = new ArrayList();
		List<AllocSourceRecord> list = null;
		String start=String.valueOf(dg.getPage());
		String count=String.valueOf(dg.getRows());
		String sourceType=allocSourceRecord.getSourceType();
		String ruleId=String.valueOf(allocSourceRecord.getAllocRuleId());
		try {
			list = haeFilterHeaderMapper.getAllocSourceDataGrid(start,sourceType,ruleId,count);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < list.size(); i++) {
			map=new HashMap<String, Object>();
			map.put("allocRuleId",list.get(i).getAllocRuleId());
			map.put("sourceType",list.get(i).getSourceType());
			map.put("actualFlag",list.get(i).getActualFlag());
			map.put("currencyType", list.get(i).getCurrencyType());
			map.put("currencyCode", list.get(i).getCurrencyCode());
			map.put("amountType", list.get(i).getAmountType());
			map.put("directionCode", list.get(i).getDirectionCode());
			map.put("description", list.get(i).getDescription());
			map.put("constantValue", list.get(i).getConstantValue());
			map.put("operator", list.get(i).getOperator());
			map.put("sourceId", list.get(i).getSourceId());
			result.add(map);
			}
		return result;
	}
	
	@Override
	public AllocSourceRecord getAllocSourceByKey(long ruleSourceId) {
		// TODO Auto-generated method stub
		AllocSourceRecord record=null;
		try {
			record= haeFilterHeaderMapper.getAllocSourceByKey(ruleSourceId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return record;
	}
	
	@Override
	public AllocSourceRecord updateAllocSource(AllocSourceRecord allocSourceRecord) {		
		 
		try {
			int result	=haeFilterHeaderMapper.updateAllocSource(allocSourceRecord);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return allocSourceRecord;
	}
	
	@Override
	public AllocSourceRecord getAllocSourceByRuleId(long ruleId) {
		// TODO Auto-generated method stub
		AllocSourceRecord record=null;
		try {
			return haeFilterHeaderMapper.getAllocSourceByRuleId(ruleId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return record;
	}
	
	@Override
	public AllocSourceRecord addAllocSource(AllocSourceRecord allocSource) {		
		long primaryKey = (long) baseDao2.getAutoGeneratedPrimaryKey("HAE_ALLOC_SOURCE_S");
		allocSource.setSourceId(primaryKey);
		try {
			int result=haeFilterHeaderMapper.addAllocSource(allocSource);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return allocSource;
	}

	@Override
	public AllocTargetRecord getAllocTargetByRuleId(long allocRuleId) {
		AllocTargetRecord record=null;
		try {
			record= haeFilterHeaderMapper.getAllocTargetByRuleId(allocRuleId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return record;
	}

	@Override
	public AllocTargetRecord addAllocTarget(AllocTargetRecord allocTarget) {
		// TODO Auto-generated method stub
		long primaryKey = (long) baseDao2.getAutoGeneratedPrimaryKey("HAE_ALLOC_TARGET_S");
		allocTarget.setTargetId(primaryKey);
		try {
			int result=haeFilterHeaderMapper.addAllocTarget(allocTarget);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return allocTarget;
	}

	@Override
	public List<Map<String, Object>> getAllocTargetDataGrid(EasyuiDataGrid dg, AllocTargetRecord allocTargetRecord) {
		Map<String, Object> map=null;
		List<Map<String, Object>> result = new ArrayList();
		List<AllocTargetRecord> list = null;
		String start=String.valueOf(dg.getPage());
		String count=String.valueOf(dg.getRows());
		String targetType=allocTargetRecord.getTargetType();
		String ruleId=String.valueOf(allocTargetRecord.getAllocRuleId());
		try {
			list = haeFilterHeaderMapper.getAllocTargetDataGrid(start,targetType,ruleId,count);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < list.size(); i++) {
			map=new HashMap<String, Object>();
			map.put("targetType",list.get(i).getTargetType());
			map.put("actualFlag",list.get(i).getActualFlag());
			map.put("currencyType", list.get(i).getCurrencyType());
			map.put("currencyCode", list.get(i).getCurrencyCode());
			map.put("amountType", list.get(i).getAmountType());
			map.put("directionCode", list.get(i).getDirectionCode());
			map.put("description", list.get(i).getDescription());
			map.put("targetId", list.get(i).getTargetId());
			result.add(map);
			}
		return result;
	}

	@Override
	public AllocTargetRecord updateAllocTarget(AllocTargetRecord allocTargetRecord) {
		
		 try {
			int result=haeFilterHeaderMapper.updateAllocTarget(allocTargetRecord);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return allocTargetRecord;
	}
	
	
	
	/*
	 * AllocDriver
	 * */
	
	@Override
	@Transactional(readOnly = true)
	public List<AllocDriverRecord> getAllocDriverDataGrid(
			EasyuiDataGrid dg, AllocDriverRecord allocDriverRecord) {
		List<AllocDriverRecord> result = null;
		String start=String.valueOf(dg.getPage());
		String count=String.valueOf(dg.getRows());
		String ruleId=String.valueOf(allocDriverRecord.getRuleId());
		try {
			result = haeFilterHeaderMapper.getAllocDriverDataGrid(start,ruleId,count);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	
	@Override
	public AllocDriverRecord updateAllocDriver(AllocDriverRecord allocDriverRecord) {		
		try {
			int result=haeFilterHeaderMapper.updateAllocDriver(allocDriverRecord);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return allocDriverRecord;
	}
	
	@Override
	public AllocDriverRecord getAllocDriverByRuleId(long ruleId) {
		AllocDriverRecord record=null;
		try {
			record= haeFilterHeaderMapper.getAllocDriverByRuleId(ruleId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return record;
	}
	
	@Override
	public AllocDriverRecord addAllocDriver(AllocDriverRecord allocDriver) {		
		long primaryKey = (long) baseDao2.getAutoGeneratedPrimaryKey("HAE_ALLOC_DRIVER_S");
		allocDriver.setDriverId(primaryKey);
		try {
			int result=haeFilterHeaderMapper.addAllocDriver(allocDriver);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return allocDriver;
	}

	@Override
	public int getAllocRuleDataGridCount(AllocRuleRecord allocRuleRecord) {
		// TODO Auto-generated method stub
		String ruleName=allocRuleRecord.getRuleName();
		int result=0;
		try {
			result = haeFilterHeaderMapper.getAllocRuleDataGridCount(ruleName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}


}