package com.hausontech.hrs.api.userManager.service;

import java.util.List;

import com.hausontech.hrs.api.IBaseService;
import com.hausontech.hrs.bean.userManager.pojo.EasyuiDataGrid;
import com.hausontech.hrs.bean.userManager.pojo.EasyuiDataGridJson;
import com.hausontech.hrs.bean.userManager.pojo.User;

/**
 * 用户Service
 * 
 */
public interface IUserService extends IBaseService {

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

	/**
	 * 后台向前台返回JSON，用于easyui的datagrid
	 * @param dg
	 * @param user
	 * @return
	 */
	public EasyuiDataGridJson datagrid(EasyuiDataGrid dg, User user);

	/**
	 * 组合框
	 * @param q
	 * @return
	 */
	public List<User> combobox(String q);

	public User add(User user);

	public User edit(User user);

	public void del(String ids);

	public void editUsersRole(String userIds, String roleId);

	public User getUserInfo(User user);

	public User editUserInfo(User user);

}
