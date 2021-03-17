/*package com.hausontech.hrs.daoImpl.userManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hausontech.hrs.api.IBaseDao;
import com.hausontech.hrs.api.userManager.dao.IUserDao;
import com.hausontech.hrs.bean.userManager.MrdmResources;
import com.hausontech.hrs.bean.userManager.MrdmRole;
import com.hausontech.hrs.bean.userManager.MrdmUser;
import com.hausontech.hrs.bean.userManager.MrdmroleResources;
import com.hausontech.hrs.bean.userManager.MrdmuserRole;
import com.hausontech.hrs.bean.userManager.pojo.EasyuiDataGrid;
import com.hausontech.hrs.bean.userManager.pojo.EasyuiDataGridJson;
import com.hausontech.hrs.bean.userManager.pojo.User;
import com.hausontech.hrs.utils.Encrypt;
import com.hausontech.hrs.utils.StringUtil;

@Repository("userDao")
public class UserDaoImpl implements IUserDao {
	
	@Autowired
	private IBaseDao<MrdmUser> userDao;
	@Autowired
	private IBaseDao<MrdmuserRole> mrdmuserRoleDao;
	@Autowired
	private IBaseDao<MrdmRole> roleDao;

	public User reg(User user) {
		user.setId(UUID.randomUUID().toString());
		user.setPassword(Encrypt.e(user.getPassword()));
		user.setCreatedatetime(new Date());
		user.setModifydatetime(new Date());
		MrdmUser u = new MrdmUser();
		BeanUtils.copyProperties(user, u);
		userDao.save(u);
		return user;
	}

	@Transactional(readOnly = true)
	public User login(User user) {
		MrdmUser u = userDao.get("from MrdmUser u where u.userName=?", user.getName()); 
				//userDao.get("from MrdmUser u where u.userName=? and u.password=?", user.getName(), Encrypt.e(user.getPassword()));
		if (u != null) {
			String encryptedPwd = Encrypt.e(user.getPassword());
			if (!StringUtil.isEmptyTrim(encryptedPwd) && 
					(encryptedPwd.equals(u.getPassword()) || encryptedPwd.toUpperCase().equals(u.getPassword()))) {				
				if (u.getMrdmuserRoles() != null && 0 < u.getMrdmuserRoles().size()) {
					BeanUtils.copyProperties(u, user);
					//set user role
					Iterator iter = u.getMrdmuserRoles().iterator();
					while(iter.hasNext()) {
						MrdmuserRole userRoleMap = (MrdmuserRole)iter.next();
						MrdmRole usrRole = userRoleMap.getMrdmRole();
						if (usrRole != null && user != null) {
							user.setRoleId(usrRole.getId());
							user.setRoleText(usrRole.getText());
						}
					}
				}
				return user;
			}
		}
		return null;
	}

	@Transactional(readOnly = true)
	public EasyuiDataGridJson datagrid(EasyuiDataGrid dg, User user) {
		EasyuiDataGridJson j = new EasyuiDataGridJson();
		String hql = " from MrdmUser t where 1=1 ";
		LinkedList<Object> values = new LinkedList<Object>();
		if (user != null) {// 添加查询条件
			if (user.getName() != null && !user.getName().trim().equals("")) {
				hql += " and t.name like ? ";
				values.add("%" + user.getName().trim() + "%");
			}
			if (user.getCreatedatetimeStart() != null) {
				hql += " and t.createdatetime>=? ";
				values.add(user.getCreatedatetimeStart());
			}
			if (user.getCreatedatetimeEnd() != null) {
				hql += " and t.createdatetime<=? ";
				values.add(user.getCreatedatetimeEnd());
			}
			if (user.getModifydatetimeStart() != null) {
				hql += " and t.modifydatetime>=? ";
				values.add(user.getModifydatetimeStart());
			}
			if (user.getModifydatetimeEnd() != null) {
				hql += " and t.modifydatetime<=? ";
				values.add(user.getModifydatetimeEnd());
			}
		}
		String totalHql = " select count(*) " + hql;
		j.setTotal(userDao.count(totalHql, values));// 设置总记录数
		if (dg.getSort() != null) {// 设置排序
			hql += " order by " + dg.getSort() + " " + dg.getOrder();
		}
		List<MrdmUser> userList = userDao.find(hql, dg.getPage(), dg.getRows(), values);// 查询分页

		List<User> users = new ArrayList<User>();
		if (userList != null && userList.size() > 0) {// 转换模型
			for (MrdmUser us : userList) {
				User u = new User();
				BeanUtils.copyProperties(us, u);

				Set<MrdmuserRole> mrdmuserRoleSet = us.getMrdmuserRoles();
				if (mrdmuserRoleSet != null && mrdmuserRoleSet.size() > 0) {
					boolean b = false;
					String roleId = "";
					String roleText = "";
					for (MrdmuserRole res : mrdmuserRoleSet) {
						if (!b) {
							b = true;
						} else {
							roleId += ",";
							roleText += ",";
						}
						roleId += res.getMrdmRole().getId();
						roleText += res.getMrdmRole().getText();
					}
					u.setRoleId(roleId);
					u.setRoleText(roleText);
				}

				users.add(u);
			}
		}
		j.setRows(users);// 设置返回的行
		return j;
	}

	@Transactional(readOnly = true)
	public List<User> combobox(String q) {
		if (q == null) {
			q = "";
		}
		String hql = " from MrdmUser t where name like ? ";
//		List<Syuser> l = userDao.find(hql, 1, 10);
		LinkedList<Object> list = new LinkedList<Object>();
		list.add("%" + q.trim() + "%");
		List<MrdmUser> l = userDao.find(hql, list);
		List<User> ul = new ArrayList<User>();
		if (l != null && l.size() > 0) {
			for (MrdmUser mrdmUser : l) {
				User u = new User();
				BeanUtils.copyProperties(mrdmUser, u);
				ul.add(u);
			}
		}
		return ul;
	}

	public User add(User user) {
		user.setId(UUID.randomUUID().toString());
		user.setPassword(Encrypt.e(user.getPassword()));
		if (user.getCreatedatetime() == null) {
			user.setCreatedatetime(new Date());
		}
		if (user.getModifydatetime() == null) {
			user.setModifydatetime(new Date());
		}
		
		MrdmUser mrdmUser = new MrdmUser();
		BeanUtils.copyProperties(user, mrdmUser);
		userDao.save(mrdmUser);

		if (user.getRoleId() != null && !user.getRoleId().equals("")) {
			for (String id : user.getRoleId().split(",")) {
				MrdmuserRole mrdmuserRole = new MrdmuserRole();
				mrdmuserRole.setId(UUID.randomUUID().toString());
				mrdmuserRole.setMrdmRole(roleDao.get(MrdmRole.class, id));
				mrdmuserRole.setMrdmUser(mrdmUser);
				mrdmuserRoleDao.save(mrdmuserRole);
			}
		}

		return user;
	}

	public void editUsersRole(String userIds, String roleId) {
		for (String userId : userIds.split(",")) {
			MrdmUser mrdmUser = userDao.get(MrdmUser.class, userId);
			if (mrdmUser != null) {
				List<MrdmuserRole> mrdmuserRoleList = mrdmuserRoleDao.find("from MrdmuserRole t where t.mrdmUser=?", mrdmUser);
				if (mrdmuserRoleList != null && mrdmuserRoleList.size() > 0) {
					for (MrdmuserRole res : mrdmuserRoleList) {
						mrdmuserRoleDao.delete(res);
					}
				}
				if (roleId != null && !roleId.equals("")) {
					for (String id : roleId.split(",")) {
						MrdmRole mrdmRole = roleDao.get(MrdmRole.class, id);
						if (mrdmRole != null) {
							MrdmuserRole mrdmuserRole = new MrdmuserRole();
							mrdmuserRole.setId(UUID.randomUUID().toString());
							mrdmuserRole.setMrdmRole(mrdmRole);
							mrdmuserRole.setMrdmUser(mrdmUser);
							mrdmuserRoleDao.save(mrdmuserRole);
						}
					}
				}

			}
		}
	}

	public User edit(User user) {
		MrdmUser mrdmUser = userDao.get(MrdmUser.class, user.getName());
		if (mrdmUser != null) {
			if (user.getPassword() != null && !user.getPassword().trim().equals("")) {
				mrdmUser.setPassword(Encrypt.e(user.getPassword()));
			}
			if (user.getCreatedatetime() == null) {
				mrdmUser.setCreationDate(new Date());
			}
			if (user.getModifydatetime() == null) {
				mrdmUser.setLastUpdateDate(new Date());
			}
			BeanUtils.copyProperties(mrdmUser, user);

			List<MrdmuserRole> mrdmuserRoleList = mrdmuserRoleDao.find("from MrdmuserRole t where t.mrdmUser=?", mrdmUser);
			if (mrdmuserRoleList != null && mrdmuserRoleList.size() > 0) {
				for (MrdmuserRole res : mrdmuserRoleList) {
					mrdmuserRoleDao.delete(res);
				}
			}
			if (user.getRoleId() != null && !user.getRoleId().equals("")) {
				for (String id : user.getRoleId().split(",")) {
					MrdmRole mrdmRole = roleDao.get(MrdmRole.class, id);
					if (mrdmRole != null) {
						MrdmuserRole mrdmuserRole = new MrdmuserRole();
						mrdmuserRole.setId(UUID.randomUUID().toString());
						mrdmuserRole.setMrdmRole(mrdmRole);
						mrdmuserRole.setMrdmUser(mrdmUser);
						mrdmuserRoleDao.save(mrdmuserRole);
					}
				}
			}
		}
		return user;
	}

	public void del(String ids) {
		for (String id : ids.split(",")) {
			MrdmUser mrdmUser = userDao.get(MrdmUser.class, id);
			if (mrdmUser != null) {
				Set<MrdmuserRole> mrdmuserRoleset = mrdmUser.getMrdmuserRoles();
				if (mrdmuserRoleset != null && mrdmuserRoleset.size() > 0) {
					List<MrdmuserRole> mrdmuserRoleList = mrdmuserRoleDao.find("from MrdmuserRole t where t.mrdmUser=?", mrdmUser);
					if (mrdmuserRoleList != null && mrdmuserRoleList.size() > 0) {
						for (MrdmuserRole res : mrdmuserRoleList) {
							mrdmuserRoleDao.delete(res);
						}
					}
				}
				userDao.delete(mrdmUser);
			}
		}
	}

	public User getUserInfo(User user) {
		MrdmUser mrdmUser = userDao.get(MrdmUser.class, user.getName());
		BeanUtils.copyProperties(mrdmUser, user);
		String roleText = "";
		String resourcesText = "";
		Set<MrdmuserRole> userRoleSet = mrdmUser.getMrdmuserRoles();
		if (userRoleSet != null && userRoleSet.size() > 0) {
			for (MrdmuserRole res : userRoleSet) {
				if (!roleText.equals("")) {
					roleText += ",";
				}
				MrdmRole syrole = res.getMrdmRole();
				roleText += syrole.getText();

				Set<MrdmroleResources> mrdmroleResourcesSet = syrole.getMrdmroleResourceses();
				if (mrdmroleResourcesSet != null && mrdmroleResourcesSet.size() > 0) {
					for (MrdmroleResources re : mrdmroleResourcesSet) {
						if (!resourcesText.equals("")) {
							resourcesText += ",";
						}
						MrdmResources syresources = re.getMrdmResources();
						resourcesText += syresources.getText();
					}
				}
			}
		}
		user.setRoleText(roleText);
		user.setResourcesText(resourcesText);
		return user;
	}

	public User editUserInfo(User user) {
		if (user.getOldPassword() != null && !user.getOldPassword().trim().equals("") && user.getPassword() != null && !user.getPassword().trim().equals("")) {
			MrdmUser syuser = userDao.get("from MrdmUser t where t.userName=? and t.password=?", user.getName(), Encrypt.e(user.getOldPassword()));
			if (syuser != null) {
				syuser.setPassword(Encrypt.e(user.getPassword()));
				return user;
			}
		}
		return null;
	}

}
*/