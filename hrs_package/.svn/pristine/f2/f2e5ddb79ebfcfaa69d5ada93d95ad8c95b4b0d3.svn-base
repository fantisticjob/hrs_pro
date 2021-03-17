/*package com.hausontech.hrs.daoImpl.allocationManager;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hausontech.hrs.api.IBaseDao;
import com.hausontech.hrs.api.allocationManager.dao.IAllocRuleConfigDao;
import com.hausontech.hrs.bean.allocationManager.model.AllocDriverRecord;
import com.hausontech.hrs.bean.allocationManager.model.AllocRuleRecord;
import com.hausontech.hrs.bean.allocationManager.model.AllocSourceRecord;
import com.hausontech.hrs.bean.allocationManager.model.AllocTargetRecord;
import com.hausontech.hrs.bean.userManager.pojo.EasyuiDataGrid;
import com.hausontech.hrs.bean.userManager.pojo.EasyuiDataGridJson;
import com.hausontech.hrs.utils.StringUtil;

@Repository("allocRuleDao")
public class AllocRuleConfigDaoImpl implements IAllocRuleConfigDao {
	
	private IBaseDao<AllocRuleRecord> allocRuleDao;
	private IBaseDao<AllocSourceRecord> allocSourceDao;
	private IBaseDao<AllocTargetRecord> allocTargetDao;
	private IBaseDao<AllocDriverRecord> allocDriverDao;
	@Autowired
	public void setAllocRuleDao(IBaseDao<AllocRuleRecord> allocRuleDao) {
		this.allocRuleDao = allocRuleDao;
	}
	
	@Autowired
	public void setAllocSourceDao(IBaseDao<AllocSourceRecord> allocSourceDao) {
		this.allocSourceDao = allocSourceDao;
	}
	
	@Autowired
	public void setAllocTargetDao(IBaseDao<AllocTargetRecord> allocTargetDao) {
		this.allocTargetDao = allocTargetDao;
	}
	
	@Autowired
	public void setAllocDriverDao(IBaseDao<AllocDriverRecord> allocDriverDao) {
		this.allocDriverDao = allocDriverDao;
	}

	@Override
	@Transactional(readOnly = true)
	public EasyuiDataGridJson getAllocRuleDataGrid(
			EasyuiDataGrid dg, AllocRuleRecord allocRuleRecord) {
		
		EasyuiDataGridJson j = new EasyuiDataGridJson();
		String hql = " from AllocRuleRecord t where 1=1 ";
		LinkedList<Object> values = new LinkedList<Object>();
		if (allocRuleRecord != null) {// 添加查询条件
			if (!StringUtil.isEmptyTrim(allocRuleRecord.getDriverType())) {
				hql += " and t.driverType = ? ";
				values.add(allocRuleRecord.getDriverType().trim());
			}
			if (!StringUtil.isEmptyTrim(allocRuleRecord.getRuleName())) {
				hql += " and t.ruleName = ? ";
				values.add(allocRuleRecord.getRuleName().trim());
			}
		}
		String totalHql = " select count(*) " + hql;
		j.setTotal(allocRuleDao.count(totalHql, values));// 设置总记录数
		if (dg.getSort() != null) {// 设置排序
			hql += " order by " + dg.getSort() + " " + dg.getOrder();
		}
		List<AllocRuleRecord> allocRuleList = 
				allocRuleDao.find(hql, dg.getPage(), dg.getRows(), values);// 查询分页
		j.setRows(allocRuleList);// 设置返回的行
		return j;
	}
	
	@Override
	public AllocRuleRecord getAllocRuleByKey(long ruleId) {
		String hql = " from TransactionProcessHeader t where 1=1 ";
		LinkedList<Object> values = new LinkedList<Object>();
		hql += " and t.typeCode = ? ";
		values.add(txnProcHeaderId);
		

		return allocRuleDao.get(AllocRuleRecord.class, ruleId);

	}

	@Override
	public AllocRuleRecord addAllocRule(AllocRuleRecord allocRule) {
		try {
			if (allocRule.getCreationDate() == null) {
				allocRule.setCreationDate(new Date());
			}
			if (allocRule.getLastUpdateDate() == null) {
				allocRule.setLastUpdateDate(new Date());
			}
			allocRuleDao.save(allocRule);
		} catch (Exception e) {
			System.out.print(e.toString());
		}

		return allocRule;
	}

	@Override
	public AllocRuleRecord updateAllocRule(AllocRuleRecord allocRule) {
		if (allocRule.getLastUpdateDate() == null) {
			allocRule.setLastUpdateDate(new Date());
		}
		allocRuleDao.update(allocRule);
		return allocRule;
	}

	@Override
	public void deleteAllocRule(AllocRuleRecord allocRule) {
		allocRuleDao.delete(allocRule);
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public EasyuiDataGridJson getAllocSourceDataGrid(
			EasyuiDataGrid dg, AllocSourceRecord allocSourceRecord) {
		
		EasyuiDataGridJson j = new EasyuiDataGridJson();
		String hql = " from AllocSourceRecord t where 1=1 ";
		LinkedList<Object> values = new LinkedList<Object>();
		if (allocSourceRecord != null) {// 添加查询条件
			if (!StringUtil.isEmptyTrim(allocSourceRecord.getSourceType())) {
				hql += " and t.sourceType = ? ";
				values.add(allocSourceRecord.getSourceType().trim());
			}
			
			if (!StringUtil.isEmptyTrim(allocSourceRecord.getActualFlag())) {
				hql += " and t.actualFlag = ? ";
				values.add(allocSourceRecord.getActualFlag().trim());
			}
			
			if (allocSourceRecord.getAllocRuleId() != 0) {
				hql += " and t.allocRuleId = ? ";
				values.add(allocSourceRecord.getAllocRuleId());
			}
			//TODO add more query conditions
		}
		String totalHql = " select count(*) " + hql;
		j.setTotal(allocSourceDao.count(totalHql, values));// 设置总记录数
		if (dg.getSort() != null) {// 设置排序
			hql += " order by " + dg.getSort() + " " + dg.getOrder();
		}
		List<AllocSourceRecord> allocSourceList = 
				allocSourceDao.find(hql, dg.getPage(), dg.getRows(), values);// 查询分页
		j.setRows(allocSourceList);// 设置返回的行
		return j;
	}
	
	@Override
	public AllocSourceRecord getAllocSourceByKey(long ruleSourceId) {
		String hql = " from TransactionProcessHeader t where 1=1 ";
		LinkedList<Object> values = new LinkedList<Object>();
		hql += " and t.typeCode = ? ";
		values.add(txnProcHeaderId);
		

		return allocSourceDao.get(AllocSourceRecord.class, ruleSourceId);

	}

	@Override
	public AllocSourceRecord updateAllocSource(AllocSourceRecord allocSource) {
		if (allocSource.getLastUpdateDate() == null) {
			allocSource.setLastUpdateDate(new Date());
		}
		allocSourceDao.update(allocSource);
        String hql="update AllocRuleRecord t set t.isSourceOver = 'Y' where allocRuleId = "+allocSource.getAllocRuleId();
        allocRuleDao.executeHql(hql);
		return allocSource;
		
	}
	
	
	@Override
	public AllocSourceRecord getAllocSourceByRuleId(long ruleId) {
		String hql = " from TransactionProcessHeader t where 1=1 ";
		LinkedList<Object> values = new LinkedList<Object>();
		hql += " and t.typeCode = ? ";
		values.add(txnProcHeaderId);
		
	
		String hql = " from AllocSourceRecord t where t.allocRuleId = ?";	
		return allocSourceDao.get(hql, ruleId);
	}
	
	@Override
	public AllocSourceRecord addAllocSource(AllocSourceRecord allocSource) {		
		if (allocSource.getCreationDate() == null) {
			allocSource.setCreationDate(new Date());
		}
		if (allocSource.getLastUpdateDate() == null) {
			allocSource.setLastUpdateDate(new Date());
		}
		allocSourceDao.save(allocSource);
        String hql="update AllocRuleRecord t set t.isSourceOver = 'Y' where allocRuleId = "+allocSource.getAllocRuleId();
        allocRuleDao.executeHql(hql);
        
		return allocSource;
	}

	@Override
	public AllocTargetRecord getAllocTargetByRuleId(long allocRuleId) {
		String hql = " from AllocTargetRecord t where t.allocRuleId = ?";	
		return allocTargetDao.get(hql, allocRuleId);
	}

	@Override
	public AllocTargetRecord addAllocTarget(AllocTargetRecord allocTarget) {
		if (allocTarget.getCreationDate() == null) {
			allocTarget.setCreationDate(new Date());
		}
		if (allocTarget.getLastUpdateDate() == null) {
			allocTarget.setLastUpdateDate(new Date());
		}
		allocTargetDao.save(allocTarget);
		return allocTarget;
	}

	@Override
	public EasyuiDataGridJson getAllocTargetDataGrid(EasyuiDataGrid dg, AllocTargetRecord allocTargetRecord) {
		EasyuiDataGridJson j = new EasyuiDataGridJson();
		String hql = " from AllocTargetRecord t where 1=1 ";
		LinkedList<Object> values = new LinkedList<Object>();
		if (allocTargetRecord != null) {// 添加查询条件
			if (!StringUtil.isEmptyTrim(allocTargetRecord.getTargetType())) {
				hql += " and t.targetType = ? ";
				values.add(allocTargetRecord.getTargetType().trim());
			}
			
			if (!StringUtil.isEmptyTrim(allocTargetRecord.getActualFlag())) {
				hql += " and t.actualFlag = ? ";
				values.add(allocTargetRecord.getActualFlag().trim());
			}
			
			if (allocTargetRecord.getAllocRuleId() != 0) {
				hql += " and t.allocRuleId = ? ";
				values.add(allocTargetRecord.getAllocRuleId());
			}
			//TODO add more query conditions
		}
		String totalHql = " select count(*) " + hql;
		j.setTotal(allocTargetDao.count(totalHql, values));// 设置总记录数
		if (dg.getSort() != null) {// 设置排序
			hql += " order by " + dg.getSort() + " " + dg.getOrder();
		}
		List<AllocTargetRecord> allocTargetList = 
				allocTargetDao.find(hql, dg.getPage(), dg.getRows(), values);// 查询分页
		j.setRows(allocTargetList);// 设置返回的行
		return j;
	}

	@Override
	public AllocTargetRecord updateAllocTarget(AllocTargetRecord allocTargetRecord) {
		if (allocTargetRecord.getLastUpdateDate() == null) {
			allocTargetRecord.setLastUpdateDate(new Date());
		}
		allocTargetDao.update(allocTargetRecord);

		return allocTargetRecord;
	}
	
	
	
	
	@Override
	@Transactional(readOnly = true)
	public EasyuiDataGridJson getAllocDriverDataGrid(
			EasyuiDataGrid dg, AllocDriverRecord allocDriverRecord) {
		
		EasyuiDataGridJson j = new EasyuiDataGridJson();
		String hql = " from AllocDriverRecord t where 1=1 ";
		LinkedList<Object> values = new LinkedList<Object>();
		if (allocDriverRecord != null) {// 添加查询条件
			if (!StringUtil.isEmptyTrim(allocDriverRecord.getDriverType())) {
				hql += " and t.driverType = ? ";
				values.add(allocDriverRecord.getDriverType().trim());
			}
			
			if (!StringUtil.isEmptyTrim(allocDriverRecord.getActualFlag())) {
				hql += " and t.actualFlag = ? ";
				values.add(allocDriverRecord.getActualFlag().trim());
			}
			
			if (allocDriverRecord.getRuleId() != 0) {
				hql += " and t.ruleId = ? ";
				values.add(allocDriverRecord.getRuleId());
			}
			//TODO add more query conditions
		}
		String totalHql = " select count(*) " + hql;
		j.setTotal(allocDriverDao.count(totalHql, values));// 设置总记录数
		if (dg.getSort() != null) {// 设置排序
			hql += " order by " + dg.getSort() + " " + dg.getOrder();
		}
		List<AllocDriverRecord> allocDriverList = 
				allocDriverDao.find(hql, dg.getPage(), dg.getRows(), values);// 查询分页
		j.setRows(allocDriverList);// 设置返回的行
		return j;
	}
	


	@Override
	public AllocDriverRecord updateAllocDriver(AllocDriverRecord allocDriver) {
		if (allocDriver.getLastUpdateDate() == null) {
			allocDriver.setLastUpdateDate(new Date());
		}
		allocDriverDao.update(allocDriver);
        String hql="update AllocRuleRecord t set t.isDriverOver = 'Y' where allocRuleId = "+allocDriver.getRuleId();
        allocRuleDao.executeHql(hql);
		
		return allocDriver;
	}
	
	
	@Override
	public AllocDriverRecord getAllocDriverByRuleId(long ruleId) {
		String hql = " from TransactionProcessHeader t where 1=1 ";
		LinkedList<Object> values = new LinkedList<Object>();
		hql += " and t.typeCode = ? ";
		values.add(txnProcHeaderId);
		
	
		String hql = " from AllocDriverRecord t where t.ruleId = ?";	
		return allocDriverDao.get(hql, ruleId);
	}
	
	@Override
	public AllocDriverRecord addAllocDriver(AllocDriverRecord allocDriver) {		
		if (allocDriver.getCreationDate() == null) {
			allocDriver.setCreationDate(new Date());
		}
		if (allocDriver.getLastUpdateDate() == null) {
			allocDriver.setLastUpdateDate(new Date());
		}
		allocDriverDao.save(allocDriver);
        String hql="update AllocRuleRecord t set t.isDriverOver = 'Y' where allocRuleId = "+allocDriver.getRuleId();
        allocRuleDao.executeHql(hql);
		return allocDriver;
	}
}
*/