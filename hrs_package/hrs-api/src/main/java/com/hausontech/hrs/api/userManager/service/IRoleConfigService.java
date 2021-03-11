package com.hausontech.hrs.api.userManager.service;

import com.hausontech.hrs.bean.userManager.RoleUserMapRecord;
import com.hausontech.hrs.bean.userManager.pojo.EasyuiDataGrid;
import com.hausontech.hrs.bean.userManager.pojo.EasyuiDataGridJson;

public interface IRoleConfigService {

	public EasyuiDataGridJson getRoleUserMapDataGrid(EasyuiDataGrid dg, RoleUserMapRecord requestBean) ;

	public RoleUserMapRecord saveRoleUserMap(RoleUserMapRecord requestBean) ;

	public RoleUserMapRecord updateRoleUserMap(RoleUserMapRecord requestBean);

	public RoleUserMapRecord deleteRoleUserMap(RoleUserMapRecord requestBean);

}
