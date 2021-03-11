package com.hausontech.hrs.daoImpl.userManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hausontech.hrs.api.IBaseDao;
import com.hausontech.hrs.api.userManager.dao.IRoleDao;
import com.hausontech.hrs.bean.userManager.MrdmResources;
import com.hausontech.hrs.bean.userManager.MrdmRole;
import com.hausontech.hrs.bean.userManager.MrdmroleResources;
import com.hausontech.hrs.bean.userManager.MrdmuserRole;
import com.hausontech.hrs.bean.userManager.pojo.EasyuiTreeNode;
import com.hausontech.hrs.bean.userManager.pojo.Role;
import com.hausontech.hrs.utils.RoleComparator;

@Repository("RoleDao")
public class RoleDaoImpl implements IRoleDao{
	
	@Autowired
	private IBaseDao<MrdmRole> roleDao;
	@Autowired
	private IBaseDao<MrdmroleResources> mrdmroleResourcesDao;
	@Autowired
	private IBaseDao<MrdmuserRole> mrdmuserRoleDao;
	@Autowired
	private IBaseDao<MrdmResources> resourcesDao;

	@Transactional(readOnly = true)
	public List<EasyuiTreeNode> tree(String id) {
		LinkedList<Object> params = new LinkedList<Object>();
		String hql = "from MrdmRole t where t.mrdmRole is null order by t.seq";
		if (!StringUtils.isEmpty(id)) {
			hql = "from MrdmRole t where t.mrdmRole.id =? order by t.seq";
			params.add(id);
		}
		List<MrdmRole> mrdmRoleList = roleDao.find(hql, params);
		List<EasyuiTreeNode> tree = new ArrayList<EasyuiTreeNode>();
		for (MrdmRole role : mrdmRoleList) {
			tree.add(tree(role, false));
		}
		return tree;
	}

	private EasyuiTreeNode tree(MrdmRole mrdmRole, boolean recursive) {
		EasyuiTreeNode node = new EasyuiTreeNode();
		node.setId(mrdmRole.getId());
		node.setText(mrdmRole.getText());
		Map<String, Object> attributes = new HashMap<String, Object>();
		node.setAttributes(attributes);
		if (mrdmRole.getMrdmRoles() != null && mrdmRole.getMrdmRoles().size() > 0) {
			node.setState("closed");
			if (recursive) {// 递归查询子节�?
				List<MrdmRole> mrdmRoleList = new ArrayList<MrdmRole>(mrdmRole.getMrdmRoles());
				Collections.sort(mrdmRoleList, new RoleComparator());// 排序
				List<EasyuiTreeNode> children = new ArrayList<EasyuiTreeNode>();
				for (MrdmRole r : mrdmRoleList) {
					EasyuiTreeNode t = tree(r, true);
					children.add(t);
				}
				node.setChildren(children);
			}
		}
		return node;
	}

	@Transactional(readOnly = true)
	public List<Role> treegrid(String id) {
		List<Role> treegrid = new ArrayList<Role>();
		LinkedList<Object> params = new LinkedList<Object>();
		String hql = "from MrdmRole t where t.mrdmRole is null order by t.seq";
		if (!StringUtils.isEmpty(id)) {
			hql = "from MrdmRole t where t.mrdmRole.id = ? order by t.seq";
			params.add(id);
		}
		List<MrdmRole> mrdmRoleList = roleDao.find(hql, params);
		for (MrdmRole role : mrdmRoleList) {
			Role r = new Role();
			BeanUtils.copyProperties(role, r);
			if (role.getMrdmRole() != null) {
				r.setParentId(role.getMrdmRole().getId());
				r.setParentText(role.getMrdmRole().getText());
			}
			if (role.getMrdmRoles() != null && role.getMrdmRoles().size() > 0) {
				r.setState("closed");
			}
			if (role.getMrdmroleResourceses() != null && role.getMrdmroleResourceses().size() > 0) {
				String resourcesTextList = "";
				String resourcesIdList = "";
				boolean b = false;
				Set<MrdmroleResources> mrdmroleResourcesSet = role.getMrdmroleResourceses();
				for (MrdmroleResources ces : mrdmroleResourcesSet) {
					MrdmResources mrdmResources = ces.getMrdmResources();
					if (!b) {
						b = true;
					} else {
						resourcesTextList += ",";
						resourcesIdList += ",";
					}
					resourcesTextList += mrdmResources.getText();
					resourcesIdList += mrdmResources.getId();
				}
				r.setResourcesId(resourcesIdList);
				r.setResourcesText(resourcesTextList);
			}
			treegrid.add(r);
		}
		return treegrid;
	}

	public Role add(Role role) {
		MrdmRole mrdmRole = new MrdmRole();
		BeanUtils.copyProperties(role, mrdmRole);
		if (role.getParentId() != null && !role.getParentId().trim().equals("")) {
			mrdmRole.setMrdmRole(roleDao.load(MrdmRole.class, role.getParentId()));
		}
		roleDao.save(mrdmRole);
		return role;
	}

	public void del(Role role) {
		MrdmRole mrdmRole = roleDao.get(MrdmRole.class, role.getId());
		if (role != null) {
			recursiveDelete(mrdmRole);
		}
	}

	private void recursiveDelete(MrdmRole role) {
		if (role.getMrdmRoles() != null && role.getMrdmRoles().size() > 0) {
			Set<MrdmRole> roleSet = role.getMrdmRoles();
			for (MrdmRole t : roleSet) {
				recursiveDelete(t);
			}
		}

		List<MrdmroleResources> mrdmroleResourcesList = mrdmroleResourcesDao.find("from MrdmroleResources t where t.mrdmRole=?", role);
		if (mrdmroleResourcesList != null && mrdmroleResourcesList.size() > 0) {
			for (MrdmroleResources res : mrdmroleResourcesList) {
				mrdmroleResourcesDao.delete(res);
			}
		}

		List<MrdmuserRole> mrdmuserRoleList = mrdmuserRoleDao.find("from MrdmuserRole t where t.mrdmRole=?", role);
		if (mrdmuserRoleList != null && mrdmuserRoleList.size() > 0) {
			for (MrdmuserRole res : mrdmuserRoleList) {
				mrdmuserRoleDao.delete(res);
			}
		}

		roleDao.delete(role);
	}

	public Role edit(Role role) {
		MrdmRole mrdmRole = roleDao.get(MrdmRole.class, role.getId());
		if (mrdmRole != null) {
			BeanUtils.copyProperties(role, mrdmRole);
			if (!mrdmRole.getId().equals("0")) {// 跟节点不可以修改上级节点
				mrdmRole.setMrdmRole(roleDao.get(MrdmRole.class, role.getParentId()));
			}

			List<MrdmroleResources> mrdmroleResourcesList = mrdmroleResourcesDao.find("from MrdmroleResources t where t.mrdmRole=?", mrdmRole);
			for (MrdmroleResources res : mrdmroleResourcesList) {
				mrdmroleResourcesDao.delete(res);
			}

			if (role.getResourcesId() != null && !role.getResourcesId().equals("")) {// 保存角色和资源的关系
				String[] resourceIds = role.getResourcesId().split(",");
				for (String resourceId : resourceIds) {
					MrdmroleResources mrdmroleResources = new MrdmroleResources();// 关系
					MrdmResources mrdmResources = resourcesDao.get(MrdmResources.class, resourceId);// 资源
					mrdmroleResources.setId(UUID.randomUUID().toString());
					mrdmroleResources.setMrdmRole(mrdmRole);
					mrdmroleResources.setMrdmResources(mrdmResources);
					mrdmroleResourcesDao.save(mrdmroleResources);
				}
			}
		}
		
		return role;
	}

}
