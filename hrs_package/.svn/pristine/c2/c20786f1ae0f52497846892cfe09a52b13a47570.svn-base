package com.hausontech.hrs.api.transactionProcess.dao;

import java.util.List;

import com.hausontech.hrs.bean.transactionProcess.TransactionProcType;
import com.hausontech.hrs.bean.transactionProcess.TransactionProcessHeader;
import com.hausontech.hrs.bean.transactionProcess.TransactionProcessLine;
import com.hausontech.hrs.bean.userManager.pojo.EasyuiDataGrid;
import com.hausontech.hrs.bean.userManager.pojo.EasyuiDataGridJson;

public interface ITransactionProcessDao {
		
	public EasyuiDataGridJson getTransactionProcHeaderDataGrid(
			EasyuiDataGrid dg, TransactionProcessHeader txnHeader);
	
	public EasyuiDataGridJson getTransactionProcHeaderListWithSec(
			EasyuiDataGrid dg, TransactionProcessHeader header, String userName);
	
	public TransactionProcessHeader getTransactionProcHeaderByKey(long txnProcHeaderId); 
	
	public TransactionProcessHeader add(TransactionProcessHeader txnHeader);
	
	public TransactionProcessHeader update(TransactionProcessHeader txnHeader);
	
	public void delete(TransactionProcessHeader txnHeader);
		
	public List<TransactionProcType> getTransactionProcTypeList();
	

	
	public EasyuiDataGridJson getTransactionProcLineDataGrid(
			EasyuiDataGrid dg, TransactionProcessLine txnProcLine);
	public EasyuiDataGridJson getTransactionProcLineListWithSec(
			EasyuiDataGrid dg, TransactionProcessLine txnProcLine, String userName);
	
	public TransactionProcessLine createTransactionProcLine(TransactionProcessLine txnProcLine);
	
	public TransactionProcessLine updateTransactionProcLine(TransactionProcessLine txnProcLine);
	
	public void deleteTransactionProcLine(TransactionProcessLine txnProcLine);
}
