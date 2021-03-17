/*package com.hausontech.hrs.daoImpl.transactionProcess;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hausontech.hrs.api.IBaseDao;
import com.hausontech.hrs.api.transactionProcess.dao.ITransactionProcessDao;
import com.hausontech.hrs.bean.transactionProcess.TransactionProcType;
import com.hausontech.hrs.bean.transactionProcess.TransactionProcessHeader;
import com.hausontech.hrs.bean.transactionProcess.TransactionProcessLine;
import com.hausontech.hrs.bean.userManager.pojo.EasyuiDataGrid;
import com.hausontech.hrs.bean.userManager.pojo.EasyuiDataGridJson;
import com.hausontech.hrs.utils.StringUtil;

@Repository("txnProcessDao")
public class TransactionProcessDaoImpl implements ITransactionProcessDao {
	
	private IBaseDao<TransactionProcessHeader> txnProcessDao;
	private IBaseDao<TransactionProcType> txnProcessTypeDao;
	private IBaseDao<TransactionProcessLine> txnProcessLineDao;
	
	@Autowired
	public void setTxnProcessDao(IBaseDao<TransactionProcessHeader> txnProcessDao) {
		this.txnProcessDao = txnProcessDao;
	}
	@Autowired
	public void setTxnProcessTypeDao(IBaseDao<TransactionProcType> txnProcessTypeDao) {
		this.txnProcessTypeDao = txnProcessTypeDao;
	}
	@Autowired
	public void setTxnProcessLineDao(IBaseDao<TransactionProcessLine> txnProcessLineDao) {
		this.txnProcessLineDao = txnProcessLineDao;
	}

	@Override
	@Transactional(readOnly = true)
	public EasyuiDataGridJson getTransactionProcHeaderDataGrid(
			EasyuiDataGrid dg, TransactionProcessHeader txnHeader) {
		EasyuiDataGridJson j = new EasyuiDataGridJson();
		String hql = " from TransactionProcessHeader t where 1=1 ";
		LinkedList<Object> values = new LinkedList<Object>();
		if (txnHeader != null) {// 添加查询条件
			if (!StringUtil.isEmptyTrim(txnHeader.getTypeCode())) {
				hql += " and t.typeCode = ? ";
				values.add(txnHeader.getTypeCode().trim());
			}
			if (txnHeader.getLedgerId() != 0) {
				hql += " and t.ledgerId = ? ";
				values.add(txnHeader.getLedgerId());
			}
			if (!StringUtil.isEmptyTrim(txnHeader.getCompanySegValue())) {
				hql += " and t.companySegValue = ? ";
				values.add(txnHeader.getCompanySegValue().trim());
			}
			if (!StringUtil.isEmptyTrim(txnHeader.getAuditFlag())) {
				hql += " and t.auditFlag = ? ";
				values.add(txnHeader.getAuditFlag().trim());
			}
			if (!StringUtil.isEmptyTrim(txnHeader.getPeriodName())) {
				hql += " and t.periodName = ? ";
				values.add(txnHeader.getPeriodName().trim());
			}
			if (!StringUtil.isEmptyTrim(txnHeader.getTransferFlag())) {
				hql += " and t.transferFlag = ? ";
				values.add(txnHeader.getTransferFlag().trim());
			}
		}
		String totalHql = " select count(*) " + hql;
		j.setTotal(txnProcessDao.count(totalHql, values));// 设置总记录数
		if (dg.getSort() != null) {// 设置排序
			hql += " order by " + dg.getSort() + " " + dg.getOrder();
		}
		List<TransactionProcessHeader> transactionList = 
				txnProcessDao.find(hql, dg.getPage(), dg.getRows(), values);// 查询分页

		j.setRows(transactionList);// 设置返回的行
		return j;
	}
	
	@Override
	@Transactional(readOnly = true)
	public EasyuiDataGridJson getTransactionProcHeaderListWithSec(
			EasyuiDataGrid dg, TransactionProcessHeader header, String userName) {
		EasyuiDataGridJson j = new EasyuiDataGridJson();
		//set total number
		j.setTotal((long)this.getTotalHeaderNumberWithSec(header, userName));
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("typeCode", header.getTypeCode() == null ? "" : header.getTypeCode());
		parmMap.put("ledgerId", header.getLedgerId() == 0 ? "" : header.getLedgerId());
		parmMap.put("compCode", header.getCompanySegValue() == null ? "" : header.getCompanySegValue());
		parmMap.put("period", header.getPeriodName() == null ? "" : header.getPeriodName());
		parmMap.put("auditFlag", header.getAuditFlag() == null ? "" : header.getAuditFlag());
		parmMap.put("transferFlag", header.getTransferFlag() == null ? "" : header.getTransferFlag());
		parmMap.put("userName", userName);
				
		List<TransactionProcessHeader> transactionTypeList = 
				this.txnProcessDao.findByDefinedSql(
						"getTransactionProcHeaderWithSecurity",dg.getPage(), dg.getRows(), parmMap);
		
		j.setRows(transactionTypeList);

		return j;
	}
	
	@Override
	public TransactionProcessHeader getTransactionProcHeaderByKey(long txnProcHeaderId) {
		String hql = " from TransactionProcessHeader t where 1=1 ";
		LinkedList<Object> values = new LinkedList<Object>();
		hql += " and t.typeCode = ? ";
		values.add(txnProcHeaderId);
		

		return txnProcessDao.get(TransactionProcessHeader.class, txnProcHeaderId);

	}

	@Override
	public TransactionProcessHeader add(TransactionProcessHeader txnHeader) {		
		if (txnHeader.getCreationDate() == null) {
			txnHeader.setCreationDate(new Date());
		}
		if (txnHeader.getLastUpdateDate() == null) {
			txnHeader.setLastUpdateDate(new Date());
		}
		txnProcessDao.save(txnHeader);
		return txnHeader;
	}

	@Override
	public TransactionProcessHeader update(TransactionProcessHeader txnHeader) {
		if (txnHeader.getLastUpdateDate() == null) {
			txnHeader.setLastUpdateDate(new Date());
		}
		txnProcessDao.update(txnHeader);
		return txnHeader;
	}

	@Override
	public void delete(TransactionProcessHeader txnHeader) {
		txnProcessDao.delete(txnHeader);
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<TransactionProcType> getTransactionProcTypeList() {
		String hql = " from TransactionProcType";
		List<TransactionProcType> transactionTypeList = 
				this.txnProcessTypeDao.find(hql, new LinkedList<Object>());

		return transactionTypeList;
	}
	
	@Override
	@Transactional(readOnly = true)
	public EasyuiDataGridJson getTransactionProcLineDataGrid(
			EasyuiDataGrid dg, TransactionProcessLine txnProcLine) {
		EasyuiDataGridJson j = new EasyuiDataGridJson();
		String hql = " from TransactionProcessLine t where 1=1 ";
		LinkedList<Object> values = new LinkedList<Object>();
		if (txnProcLine != null) {// 添加查询条件
			if (txnProcLine.getTxnHeaderId() != 0) {
				hql += " and t.txnHeaderId = ? ";
				values.add(txnProcLine.getTxnHeaderId());
			}
			if (txnProcLine.getLedgerId() != 0) {
				hql += " and t.ledgerId = ? ";
				values.add(txnProcLine.getLedgerId());
			}
			if (!StringUtil.isEmptyTrim(txnProcLine.getFinElement())) {
				hql += " and t.finElement = ? ";
				values.add(txnProcLine.getFinElement().trim());
			}
		}
		String totalHql = " select count(*) " + hql;
		j.setTotal(txnProcessLineDao.count(totalHql, values));// 设置总记录数
		if (dg.getSort() != null) {// 设置排序
			hql += " order by " + dg.getSort() + " " + dg.getOrder();
		}
		List<TransactionProcessLine> transactionList = 
				txnProcessLineDao.find(hql, dg.getPage(), dg.getRows(), values);// 查询分页
		j.setRows(transactionList);// 设置返回的行
		return j;
	}
	
	@Override
	@Transactional(readOnly = true)
	public EasyuiDataGridJson getTransactionProcLineListWithSec(
			EasyuiDataGrid dg, TransactionProcessLine txnProcLine, String userName) {
		EasyuiDataGridJson j = new EasyuiDataGridJson();
		//set total number
		j.setTotal((long)this.getTotalLineNumberWithSec(txnProcLine, userName));
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("txnProcHeaderId", txnProcLine.getTxnHeaderId() == 0 ? "" : txnProcLine.getTxnHeaderId());
		parmMap.put("ledgerId", txnProcLine.getLedgerId() == 0 ? "" : txnProcLine.getLedgerId());
		parmMap.put("finElement", txnProcLine.getFinElement() == null ? "" : txnProcLine.getFinElement());
		parmMap.put("userName", userName);
				
		List<TransactionProcessLine> transactionTypeList = 
				this.txnProcessLineDao.findByDefinedSql(
						"getTransactionProcLineWithSecurity",dg.getPage(), dg.getRows(), parmMap);		
		j.setRows(transactionTypeList);
		return j;
	}
	
	@Override
	public TransactionProcessLine createTransactionProcLine(TransactionProcessLine txnProcLine) {		
		if (txnProcLine.getCreationDate() == null) {
			txnProcLine.setCreationDate(new Date());
		}
		if (txnProcLine.getLastUpdateDate() == null) {
			txnProcLine.setLastUpdateDate(new Date());
		}
		txnProcessLineDao.save(txnProcLine);
		return txnProcLine;
	}

	@Override
	public TransactionProcessLine updateTransactionProcLine(TransactionProcessLine txnProcLine) {
		if (txnProcLine.getLastUpdateDate() == null) {
			txnProcLine.setLastUpdateDate(new Date());
		}
		txnProcessLineDao.update(txnProcLine);
		return txnProcLine;
	}

	@Override
	public void deleteTransactionProcLine(TransactionProcessLine txnProcLine) {
		txnProcessLineDao.delete(txnProcLine);	
	}	
	
	private Long getTotalHeaderNumber(TransactionProcessHeader txnHeader) {
		String hql = " from TransactionProcessHeader t where 1=1 ";
		LinkedList<Object> values = new LinkedList<Object>();
		if (txnHeader != null) {// 添加查询条件
			if (!StringUtil.isEmptyTrim(txnHeader.getTypeCode())) {
				hql += " and t.typeCode = ? ";
				values.add(txnHeader.getTypeCode().trim());
			}
			if (txnHeader.getLedgerId() != 0) {
				hql += " and t.ledgerId = ? ";
				values.add(txnHeader.getLedgerId());
			}
			if (!StringUtil.isEmptyTrim(txnHeader.getCompanySegValue())) {
				hql += " and t.companySegValue = ? ";
				values.add(txnHeader.getCompanySegValue().trim());
			}
			if (!StringUtil.isEmptyTrim(txnHeader.getAuditFlag())) {
				hql += " and t.auditFlag = ? ";
				values.add(txnHeader.getAuditFlag().trim());
			}
			if (!StringUtil.isEmptyTrim(txnHeader.getPeriodName())) {
				hql += " and t.periodName = ? ";
				values.add(txnHeader.getPeriodName().trim());
			}
			if (!StringUtil.isEmptyTrim(txnHeader.getTransferFlag())) {
				hql += " and t.transferFlag = ? ";
				values.add(txnHeader.getTransferFlag().trim());
			}
		}
		String totalHql = " select count(*) " + hql;
		
		return txnProcessDao.count(totalHql, values);
	}
	
	
	private int getTotalHeaderNumberWithSec(TransactionProcessHeader header, String userName) {
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("typeCode", header.getTypeCode() == null ? "" : header.getTypeCode());
		parmMap.put("ledgerId", header.getLedgerId() == 0 ? "" : header.getLedgerId());
		parmMap.put("compCode", header.getCompanySegValue() == null ? "" : header.getCompanySegValue());
		parmMap.put("period", header.getPeriodName() == null ? "" : header.getPeriodName());
		parmMap.put("auditFlag", header.getAuditFlag() == null ? "" : header.getAuditFlag());
		parmMap.put("transferFlag", header.getTransferFlag() == null ? "" : header.getTransferFlag());
		parmMap.put("userName", userName);
				
		return txnProcessDao.countByDefinedSql("getTxnProcHeaderCountWithSecurity", parmMap);
	}
	

	private Long getTotalLineNumber(TransactionProcessLine txnProcLine) {
		String hql = " from TransactionProcessLine t where 1=1 ";
		LinkedList<Object> values = new LinkedList<Object>();
		if (txnProcLine != null) {// 添加查询条件
			if (txnProcLine.getLedgerId() != 0) {
				hql += " and t.ledgerId = ? ";
				values.add(txnProcLine.getLedgerId());
			}
			if (!StringUtil.isEmptyTrim(txnProcLine.getFinElement())) {
				hql += " and t.finElement = ? ";
				values.add(txnProcLine.getFinElement().trim());
			}
		}
		String totalHql = " select count(*) " + hql;	
		return txnProcessTypeDao.count(totalHql, values);
	}
	
	private int getTotalLineNumberWithSec(TransactionProcessLine txnProcLine, String userName) {
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("txnProcHeaderId", txnProcLine.getTxnHeaderId() == 0 ? "" : txnProcLine.getTxnHeaderId());
		parmMap.put("ledgerId", txnProcLine.getLedgerId() == 0 ? "" : txnProcLine.getLedgerId());
		parmMap.put("finElement", txnProcLine.getFinElement() == null ? "" : txnProcLine.getFinElement());
		parmMap.put("userName", userName);
				
		return txnProcessLineDao.countByDefinedSql("getTxnProcLineCountWithSecurity", parmMap);
	}
	
}
*/