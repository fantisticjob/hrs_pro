//package com.hausontech.hrs.serviceImpl.userManager;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.hausontech.hrs.api.BaseServiceImpl;
//import com.hausontech.hrs.api.userManager.dao.IRoleDao;
//import com.hausontech.hrs.api.userManager.service.IRoleService;
//import com.hausontech.hrs.bean.userManager.pojo.EasyuiTreeNode;
//import com.hausontech.hrs.bean.userManager.pojo.Role;
//
///**
// * 角色Service实现类
// *
// * @author
// *
// */
//
//
//@Service("roleService")
//public class RoleServiceImpl extends BaseServiceImpl implements IRoleService {
//
//	@Autowired
//	private IRoleDao roleDao;
//
//	public List<EasyuiTreeNode> tree(String id) {
//		return roleDao.tree(id);
//
//	}
//
//	public List<Role> treegrid(String id) {
//		return roleDao.treegrid(id);
//	}
//
//	public Role add(Role role) {
//		return roleDao.add(role);
//	}
//
//	public void del(Role role) {
//		roleDao.del(role);
//	}
//
//	public Role edit(Role role) {
//		return roleDao.edit(role);
//	}
//
//}
