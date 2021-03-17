package com.hausontech.hrs.serviceImpl.transactionProcess;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hausontech.hrs.api.BaseServiceImpl;
import com.hausontech.hrs.api.transactionProcess.dao.ITransactionProcessDao;
import com.hausontech.hrs.api.transactionProcess.service.ITransactionProcessService;
import com.hausontech.hrs.bean.transactionProcess.TransactionProcType;
import com.hausontech.hrs.bean.transactionProcess.TransactionProcessHeader;
import com.hausontech.hrs.bean.transactionProcess.TransactionProcessLine;
import com.hausontech.hrs.bean.userManager.pojo.EasyuiDataGrid;
import com.hausontech.hrs.bean.userManager.pojo.EasyuiDataGridJson;
import com.hausontech.hrs.daoImpl.transactionProcess.mapper.TransactionProcTypeMapper;
import com.hausontech.hrs.daoImpl.transactionProcess.mapper.TransactionProcessHeaderMapper;
import com.hausontech.hrs.daoImpl.transactionProcess.mapper.TransactionProcessLineMapper;

/**
 * @author Hauson-XT
 *
 */
/**
 * @author Hauson-XT
 *
 */
@Service("txnProcessService")
public class TransactionProcessServiceImpl extends BaseServiceImpl implements ITransactionProcessService{
	
//	private ITransactionProcessDao txnProcessDao;
	
	@Autowired
	private TransactionProcessHeaderMapper txnProcessMapper;
	
	@Autowired
	private TransactionProcTypeMapper txnProcTypeMapper;
	
	@Autowired
	private TransactionProcessLineMapper txnProcLineMapper;

//	@Autowired
//	public void setTxnProcessDao(ITransactionProcessDao txnProcessDao) {
//		this.txnProcessDao = txnProcessDao;
//	}
//
//	@Override
//	public EasyuiDataGridJson datagrid(EasyuiDataGrid dg, TransactionProcessHeader txnHeader) {
//		return txnProcessDao.getTransactionProcHeaderDataGrid(dg, txnHeader);
//	}
	
	@Override
	public EasyuiDataGridJson getTransactionProcHeaderListWithSec(
			EasyuiDataGrid dg, TransactionProcessHeader txnHeader, String userName) {
		EasyuiDataGridJson j = new EasyuiDataGridJson();
		j.setRows(txnProcessMapper.selectAll());
		j.setTotal(txnProcessMapper.selectCount());
		
		return j;
	}
	
	@Override
	public TransactionProcessHeader getTransactionProcHeaderByKey(long txnProcHeaderId) {
		//return txnProcessDao.getTransactionProcHeaderByKey(txnProcHeaderId);
		
		return txnProcessMapper.selectByPrimaryKey(txnProcHeaderId);
	}
	
	@Override
	public TransactionProcessHeader add(TransactionProcessHeader txnHeader) {
		txnProcessMapper.insert(txnHeader);
		return txnHeader;
	}

	@Override
	public TransactionProcessHeader update(TransactionProcessHeader txnHeader) {
		//return txnProcessDao.update(txnHeader);
		txnProcessMapper.updateByPrimaryKeySelective(txnHeader);
		return txnHeader;
	}

	@Override
	public void delete(TransactionProcessHeader txnHeader) {
		txnProcessMapper.deleteByPrimaryKey(txnHeader.getTxnHeaderId());
	}

	@Override
	public List<TransactionProcType> getTransactionProcTypeList() {

		return txnProcTypeMapper.selectAll();
	}
	
	
	/**
	 * 未找见controller使用
	 */
//	@Override
//	public EasyuiDataGridJson getTransactionProcLineDataGrid(
//			EasyuiDataGrid dg, TransactionProcessLine txnProcLine) {
//		return txnProcessDao.getTransactionProcLineDataGrid(dg, txnProcLine);
//	}
	
	@Override
	public EasyuiDataGridJson getTransactionProcLineListWithSec(
			EasyuiDataGrid dg, TransactionProcessLine txnProcLine, String userName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dg", dg);
		map.put("txnProcLine", txnProcLine);
		map.put("userName", userName);
		
		EasyuiDataGridJson json = new EasyuiDataGridJson();
		json.setRows(txnProcLineMapper.selectByHeaderID(map));
		json.setTotal(txnProcLineMapper.selectCountByHeaderId(map));
		return json;
	}

	@Override
	public TransactionProcessLine createTransactionProcLine(TransactionProcessLine txnProcLine) {
		// TODO Auto-generated method stub
		if (txnProcLine.getCreationDate() == null) {
			txnProcLine.setCreationDate(new Date());
		}
		if (txnProcLine.getLastUpdateDate() == null) {
			txnProcLine.setLastUpdateDate(new Date());
		}
		txnProcLineMapper.insertSelective(txnProcLine);
		return txnProcLine;
	}

	@Override
	public TransactionProcessLine updateTransactionProcLine(TransactionProcessLine txnProcLine) {
		if (txnProcLine.getLastUpdateDate() == null) {
			txnProcLine.setLastUpdateDate(new Date());
		}
		txnProcLineMapper.updateByPrimaryKeySelective(txnProcLine);
		return txnProcLine;
	}

	@Override
	public void deleteTransactionProcLine(TransactionProcessLine txnProcLine) {
		txnProcLineMapper.deleteByPrimaryKey(txnProcLine.getTxnLineId());//txnProcessDao.deleteTransactionProcLine(txnProcLine);
	}
}
