package com.hausontech.hrs.serviceImpl.userManager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hausontech.hrs.api.userManager.dao.IUserDao;
import com.hausontech.hrs.api.userManager.service.IUserService;
import com.hausontech.hrs.bean.userManager.pojo.EasyuiDataGrid;
import com.hausontech.hrs.bean.userManager.pojo.EasyuiDataGridJson;
import com.hausontech.hrs.bean.userManager.pojo.User;
import com.hausontech.hrs.serviceImpl.BaseServiceImpl;

/**
 * 用户Service
 * 
 */
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl implements IUserService {
	
	@Autowired	
	private IUserDao userDao;
	
	/**
	 * 用户注册
	 * 
	 * @param user
	 *            用户信息
	 * @return User
	 */
	public User reg(User user) {
		return userDao.reg( user);
	}

	/**
	 * 用户登录
	 * 
	 * @param user
	 *            用户信息
	 * @return User
	 */
	public User login(User user) {
		return userDao.login(user);
	}

	public EasyuiDataGridJson datagrid(EasyuiDataGrid dg, User user) {
		return userDao.datagrid(dg, user);
	}

	public List<User> combobox(String q) {
		return userDao.combobox(q);
	}

	public User add(User user) {
		return userDao.add(user);
	}

	public User edit(User user) {
		return userDao.edit(user);
	}

	public void del(String ids) {
		userDao.del(ids);
	}

	public void editUsersRole(String userIds, String roleId) {
		userDao.editUsersRole(userIds, roleId);
	}

	public User getUserInfo(User user) {
		return userDao.getUserInfo(user);
	}

	public User editUserInfo(User user) {
		return userDao.editUserInfo(user);
	}
	
	
	
}
