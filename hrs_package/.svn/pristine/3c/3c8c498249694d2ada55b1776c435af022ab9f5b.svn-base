/*package com.hausontech.hrs.daoImpl.transactionProcess;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hausontech.hrs.api.BaseServiceImpl;
import com.hausontech.hrs.api.IBaseDao;
import com.hausontech.hrs.api.transactionProcess.dao.IMrdmExcelIDao;
import com.hausontech.hrs.bean.transactionProcess.model.MrdmExcel;
import com.hausontech.hrs.bean.userManager.MrdmUser;
import com.hausontech.hrs.bean.userManager.pojo.EasyuiDataGrid;
import com.hausontech.hrs.bean.userManager.pojo.EasyuiDataGridJson;
import com.hausontech.hrs.utils.StringUtil;

@Repository("mrdmExcelIDao")
public class MrdmExcelIDaoImpl extends BaseServiceImpl implements IMrdmExcelIDao{

	@Autowired
	private IBaseDao<MrdmExcel> mrdmExcelDao;
	@Autowired
	private IBaseDao<MrdmUser> userDao;
	
	@Override
	public EasyuiDataGridJson datagrid(EasyuiDataGrid dg, MrdmExcel mrdmExcel) {
		LinkedList<Object> param = new LinkedList<Object>();
		EasyuiDataGridJson j = new EasyuiDataGridJson();
		StringBuffer hql = new StringBuffer();
		hql.append(" from MrdmExcel t ");
		hql.append(" where ");
		hql.append(" 1=1 ");
		if(!StringUtil.isEmptyTrim(mrdmExcel.getFileName())) {
			hql.append(" and t.fileName like ? ");
			param.add("%"+ mrdmExcel.getFileName() + "%");
		}
		if(mrdmExcel.getCreatedatetime() != null) {
			hql.append(" and t.createdatetime >= ? ");
//			param.add(DateUtil.getDateTimeFormtDate(mrdmExcel.getCreatedatetime()));
			param.add(mrdmExcel.getCreatedatetime());
		}
		
		String totalHql = " select count(*) " + hql;
		j.setTotal(mrdmExcelDao.count(totalHql, param));// 设置总记录数
		if (dg.getSort() != null) {// 设置排序
			hql.append( " order by " + dg.getSort() + " " + dg.getOrder());
		}
		List<MrdmExcel> mrdmExcelList = mrdmExcelDao.find(hql.toString(), dg.getPage(), dg.getRows(), param);// 查询分页
		j.setRows(mrdmExcelList);// 设置返回的行
		return j;
	}
	
	
	public boolean saveExcel(String excelFile, List<Map<String, Object>> list,String operator) {
		MrdmExcel mrdmExcel = new MrdmExcel();
		//mrdmExcel.setId(UUID.randomUUID().toString());
		mrdmExcel.setDataNum(String.valueOf(list.size()));
		mrdmExcel.setFileName(excelFile);
		mrdmExcel.setDataStatus("1");
		mrdmExcel.setCreatedatetime(new Date());
		mrdmExcel.setCreateBy(operator);
		mrdmExcelDao.save(mrdmExcel);		
		return true;
	}

}
*/