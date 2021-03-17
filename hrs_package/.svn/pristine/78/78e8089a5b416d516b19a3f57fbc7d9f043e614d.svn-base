/*package com.hausontech.hrs.daoImpl.userManager;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hausontech.hrs.api.IBaseDao;
import com.hausontech.hrs.api.userManager.dao.IAuthDao;
import com.hausontech.hrs.bean.userManager.MrdmResources;
import com.hausontech.hrs.bean.userManager.MrdmRole;
import com.hausontech.hrs.bean.userManager.MrdmUser;
import com.hausontech.hrs.bean.userManager.MrdmroleResources;
import com.hausontech.hrs.bean.userManager.MrdmuserRole;

@Repository("authServiceDao")
public class AuthDaoImpl implements IAuthDao {

	private IBaseDao<MrdmResources> resourcesDao;
	private IBaseDao<MrdmUser> userDao;

	@Autowired
	public void setUserDao(IBaseDao<MrdmUser> userDao) {
		this.userDao = userDao;
	}

	@Autowired
	public void setResourcesDao(IBaseDao<MrdmResources> resourcesDao) {
		this.resourcesDao = resourcesDao;
	}

	@Transactional(readOnly = true)
	public List<MrdmResources> offResourcesList() {
		return resourcesDao.find("from MrdmResources t where t.onoff != '1'");
	}

	@Transactional(readOnly = true)
	public MrdmResources getSyresourcesByRequestPath(String requestPath) {
		return resourcesDao.get("from MrdmResources t where t.src=?", requestPath);
	}

	@Transactional(readOnly = true)
	public boolean checkAuth(String userId, String requestPath) {
		boolean b = false;
		MrdmUser syuser = userDao.get(MrdmUser.class, userId);
		Set<MrdmuserRole> MrdmuserRoleSet = syuser.getMrdmuserRoles();// 用户和角色关�?
		for (MrdmuserRole mrdmroleResources : MrdmuserRoleSet) {
			MrdmRole mrdmRole = mrdmroleResources.getMrdmRole();
			Set<MrdmroleResources> mrdmroleResourcesSet = mrdmRole.getMrdmroleResourceses();// 角色和资源关�?
			for (MrdmroleResources res : mrdmroleResourcesSet) {
				MrdmResources mrdmResources = res.getMrdmResources();
				if (mrdmResources.getSrc() != null && requestPath.equals(mrdmResources.getSrc())) {
					return true;
				}
			}
		}
		return b;
	}
}
*/