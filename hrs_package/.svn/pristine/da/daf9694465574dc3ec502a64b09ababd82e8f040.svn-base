package com.hausontech.hrs.api.transactionProcess.service;

import java.util.List;
import java.util.Map;

import com.hausontech.hrs.api.IBaseService;
import com.hausontech.hrs.bean.transactionProcess.model.MrdmExcel;
import com.hausontech.hrs.bean.userManager.pojo.EasyuiDataGrid;
import com.hausontech.hrs.bean.userManager.pojo.EasyuiDataGridJson;

public interface IMrdmExcelService extends IBaseService {

	/**
	 * 列表获取
	 * @param dg
	 * @param mrdmExcel
	 * @return
	 */
	public EasyuiDataGridJson datagrid(EasyuiDataGrid dg,  MrdmExcel mrdmExcel);
	/**
	 * 导入数据写入数据 库
	 * @param object
	 * @return
	 * @throws Exception 
	 */
	public boolean saveExcel(String fileNmae,List<Map<String, Object>> list, String operator) throws Exception;
}
