package com.hausontech.hrs.api.transactionProcess.dao;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.hausontech.hrs.bean.transactionProcess.model.MrdmExcel;
import com.hausontech.hrs.bean.userManager.pojo.EasyuiDataGrid;
import com.hausontech.hrs.bean.userManager.pojo.EasyuiDataGridJson;

public interface IMrdmExcelIDao {
	
	public EasyuiDataGridJson datagrid(EasyuiDataGrid dg, MrdmExcel mrdmExcel);
	
	@Transactional
	boolean saveExcel(String excelFile, List<Map<String, Object>> list,String operator) ;

}
