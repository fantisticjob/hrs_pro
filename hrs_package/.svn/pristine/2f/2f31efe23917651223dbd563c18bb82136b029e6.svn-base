package com.hausontech.hrs.api.userManager.dao;

import java.util.List;

import com.hausontech.hrs.bean.userManager.pojo.EasyuiDataGrid;
import com.hausontech.hrs.bean.userManager.pojo.EasyuiDataGridJson;
import com.hausontech.hrs.bean.userManager.pojo.User;

public interface IUserDao {

	/**
	 * 用户注册
	 * 
	 * @param user
	 *            用户信息
	 * @return User
	 */
	public User reg(User user);

	/**
	 * 用户登录
	 * 
	 * @param user
	 *            用户信息
	 * @return User
	 */
	public User login(User user);

	public EasyuiDataGridJson datagrid(EasyuiDataGrid dg, User user);

	public List<User> combobox(String q);

	public User add(User user);

	public User edit(User user);

	public void del(String ids);

	public void editUsersRole(String userIds, String roleId);

	public User getUserInfo(User user);

	public User editUserInfo(User user);
	
	
}
