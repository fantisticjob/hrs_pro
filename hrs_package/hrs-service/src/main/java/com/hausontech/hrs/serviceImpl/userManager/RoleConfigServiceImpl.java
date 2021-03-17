//package com.hausontech.hrs.serviceImpl.userManager;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.hausontech.hrs.api.userManager.dao.IRoleConfigDao;
//import com.hausontech.hrs.api.userManager.service.IRoleConfigService;
//import com.hausontech.hrs.bean.userManager.RoleUserMapRecord;
//import com.hausontech.hrs.bean.userManager.pojo.EasyuiDataGrid;
//import com.hausontech.hrs.bean.userManager.pojo.EasyuiDataGridJson;
//@Service("roleConfigService")
//public class RoleConfigServiceImpl implements IRoleConfigService {
//
//	private IRoleConfigDao roleConfigDao;
//
//	@Autowired
//	public IRoleConfigDao getRoleConfigDao() {
//		return roleConfigDao;
//	}
//	@Autowired
//	public void setRoleConfigDao(IRoleConfigDao roleConfigDao) {
//		this.roleConfigDao = roleConfigDao;
//	}
//	@Override
//	public EasyuiDataGridJson getRoleUserMapDataGrid(EasyuiDataGrid dg, RoleUserMapRecord requestBean) {
//		return roleConfigDao.getRoleUserMapDataGrid(dg,requestBean);
//	}
//	@Override
//	public RoleUserMapRecord saveRoleUserMap(RoleUserMapRecord requestBean) {
//		return roleConfigDao.saveRoleUserMap(requestBean);
//	}
//	@Override
//	public RoleUserMapRecord updateRoleUserMap(RoleUserMapRecord requestBean) {
//		return roleConfigDao.updateRoleUserMap(requestBean);
//	}
//	@Override
//	public RoleUserMapRecord deleteRoleUserMap(RoleUserMapRecord requestBean) {
//		// TODO Auto-generated method stub
//		return roleConfigDao.deleteRoleUserMap(requestBean);
//	}
//
//
//
//}
