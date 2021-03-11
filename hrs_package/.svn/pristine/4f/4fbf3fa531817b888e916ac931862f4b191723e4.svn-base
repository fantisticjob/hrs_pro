package com.hausontech.hrs.daoImpl.userManager;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hausontech.hrs.api.IBaseDao;
import com.hausontech.hrs.api.userManager.dao.IRoleConfigDao;
import com.hausontech.hrs.bean.allocationManager.model.RoleRecord;
import com.hausontech.hrs.bean.userManager.RoleUserMapRecord;
import com.hausontech.hrs.bean.userManager.pojo.EasyuiDataGrid;
import com.hausontech.hrs.bean.userManager.pojo.EasyuiDataGridJson;
import com.hausontech.hrs.daoImpl.BaseDaoImpl;
import com.hausontech.hrs.utils.StringUtil;

@Repository("roleConfigDao")
public class RoleConfigDaoImpl extends BaseDaoImpl implements IRoleConfigDao {
	private IBaseDao<RoleRecord> roleRecordDao;
	private IBaseDao<RoleUserMapRecord> roleUserMapRecordDao;
    @Autowired
	public void setRoleRecord(IBaseDao<RoleRecord> roleRecord) {
		this.roleRecordDao = roleRecord;
	}
    
    @Autowired
	public void setRoleUserMapRecordDao(IBaseDao<RoleUserMapRecord> roleUserMapRecordDao) {
		this.roleUserMapRecordDao = roleUserMapRecordDao;
	}


	@Override
	public EasyuiDataGridJson getRoleUserMapDataGrid(EasyuiDataGrid dg, RoleUserMapRecord requestBean) {
		
		EasyuiDataGridJson j = new EasyuiDataGridJson();
		String hql = " from RoleUserMapRecord t where 1=1 ";
		LinkedList<Object> values = new LinkedList<Object>();
		if (requestBean != null) {// 添加查询条件
			if (!StringUtil.isEmptyTrim(requestBean.getRoleId())) {
				hql += " and t.roleId = ? ";
				values.add(requestBean.getRoleId().trim());
			}
		}
		String totalHql = " select count(*) " + hql;
		j.setTotal(roleUserMapRecordDao.count(totalHql, values));// 设置总记录数
		if (dg.getSort() != null) {// 设置排序
			hql += " order by " + dg.getSort() + " " + dg.getOrder();
		}
		List<RoleUserMapRecord> roleUserMapRecordList = 
				roleUserMapRecordDao.find(hql, dg.getPage(), dg.getRows(), values);// 查询分页
		j.setRows(roleUserMapRecordList);// 设置返回的行
		return j;
	}

	@Override
	public RoleUserMapRecord saveRoleUserMap(RoleUserMapRecord requestBean) {
		roleUserMapRecordDao.save(requestBean);
		return requestBean;
	}

	@Override
	public RoleUserMapRecord updateRoleUserMap(RoleUserMapRecord requestBean) {
		roleUserMapRecordDao.update(requestBean);
		return requestBean;
	}

	@Override
	public RoleUserMapRecord deleteRoleUserMap(RoleUserMapRecord requestBean) {
		roleUserMapRecordDao.delete(requestBean);
		return requestBean;
	}

}
