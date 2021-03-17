package com.hausontech.hrs.serviceImpl.userManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.hausontech.hrs.api.BaseServiceImpl;
import com.hausontech.hrs.api.userManager.dao.IUserDao;
import com.hausontech.hrs.api.userManager.service.IUserService;
import com.hausontech.hrs.bean.userManager.MrdmUser;
import com.hausontech.hrs.bean.userManager.MrdmuserRole2;
import com.hausontech.hrs.bean.userManager.pojo.EasyuiDataGrid;
import com.hausontech.hrs.bean.userManager.pojo.EasyuiDataGridJson;
import com.hausontech.hrs.bean.userManager.pojo.User;
import com.hausontech.hrs.daoImpl.userManager.mapper.MrdmUserMapper;
import com.hausontech.hrs.daoImpl.userManager.mapper.MrdmuserRole2Mapper;
import com.hausontech.hrs.utils.Encrypt;

/**
 * 用户Service
 * 
 */
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl implements IUserService {
	
//	@Autowired	
//	private IUserDao userDao;
	
	@Autowired
	private MrdmUserMapper mrdmUserMapper;
	
	@Autowired
	private MrdmuserRole2Mapper mrdmuserRole2Mapper;
	
	/**
	 * 用户注册
	 * 
	 * @param user
	 *            用户信息
	 * @return User
	 */
//	public User reg(User user) {
//		return userDao.reg( user);
//	}

	/**
	 * 用户登录
	 * 
	 * @param user
	 *            用户信息
	 * @return User
	 */
//	public User login(User user) {
//		return userDao.login(user);
//	}
//
//	public EasyuiDataGridJson datagrid(EasyuiDataGrid dg, User user) {
//		return userDao.datagrid(dg, user);
//	}

	/* (non-Javadoc)
	 * 显示所有用户信息（包括用户所对应的角色）
	 * @see com.hausontech.hrs.api.userManager.service.IUserService#combobox(java.lang.String)
	 */
	public List<User> combobox(String q) {
		List<MrdmUser> list = mrdmUserMapper.selectAll();
		List<User> re = new ArrayList<User>();
		for(MrdmUser m : list){
			List<MrdmuserRole2> li = mrdmuserRole2Mapper.selectByUserId(m.getUserName());
			String roleId = "";
			for(MrdmuserRole2 ur:li){
				roleId = roleId.concat(ur.getRoleId());
				roleId = roleId.concat(",");
			}
			roleId = roleId.substring(0,roleId.length()-1);
			User user = new User();
			user.setId(m.getUserName());
			user.setName(m.getFullName());
			user.setPassword(m.getPassword());
			user.setCreatedatetime(m.getCreationDate());
			user.setModifydatetime(m.getLastUpdateDate());
			user.setRoleId(roleId);
			
			re.add(user);
		}
		return re;
	}

	/* (non-Javadoc)
	 * 添加所有用户信息（包括用户所对应的角色）
	 * @see com.hausontech.hrs.api.userManager.service.IUserService#add(com.hausontech.hrs.bean.userManager.pojo.User)
	 */
	public int add(User user) {
		MrdmUser record = new MrdmUser();
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		record.setCreatedBy(userDetails.getUsername());
		record.setCreationDate(user.getCreatedatetime());
		record.setFullName(user.getName());
		record.setPassword(user.getPassword());
		record.setLastUpdateDate(new Date());
		record.setUserName(user.getId());
		record.setLastUpdatedBy(userDetails.getUsername());
		String roleId[] = user.getRoleId().split(",");
		for(int i = 0;i<roleId.length;i++){
			MrdmuserRole2 record2 = new MrdmuserRole2();
			record2.setId(UUID.randomUUID().toString());
			record2.setRoleId(roleId[i]);
			record2.setUserId(user.getId());
			mrdmuserRole2Mapper.insert(record2);
		}
		
		return mrdmUserMapper.insert(record);
	}

	/* (non-Javadoc)
	 * 修改所有用户信息（包括用户所对应的角色）
	 * @see com.hausontech.hrs.api.userManager.service.IUserService#edit(com.hausontech.hrs.bean.userManager.pojo.User)
	 */
	public int edit(User user) {
		MrdmUser record = new MrdmUser();
		record.setCreationDate(user.getCreatedatetime());
		record.setFullName(user.getName());
		if (user.getPassword() != null && !user.getPassword().trim().equals("")&&user.getPassword().length()!=32) {
			record.setPassword(Encrypt.e(user.getPassword()));
		}
		record.setLastUpdateDate(new Date());
		record.setUserName(user.getId());
		mrdmuserRole2Mapper.deleteByUserId(user.getId());
		for(String roleId : user.getRoleId().split(",")){
			MrdmuserRole2 record2 = new MrdmuserRole2();
			record2.setId(UUID.randomUUID().toString());
			record2.setRoleId(roleId);
			record2.setUserId(user.getId());
			mrdmuserRole2Mapper.insert(record2);
		}
		
		return mrdmUserMapper.updateByPrimaryKeySelective(record);
	}

	/* (non-Javadoc)
	 * 删除所有用户信息（包括用户所对应的角色）
	 * @see com.hausontech.hrs.api.userManager.service.IUserService#del(java.lang.String)
	 */
	public void del(String ids) {
		String[] li = ids.split(",");
		for (int i = 0; i < li.length; i++) {
			mrdmuserRole2Mapper.deleteByUserId(li[i]);
			mrdmUserMapper.deleteByPrimaryKey(li[i]);
		}
	}

	/* Unused
	 * @see com.hausontech.hrs.api.userManager.service.IUserService#editUsersRole(java.lang.String, java.lang.String)
	 */
//	public void editUsersRole(String userIds, String roleId) {
//		userDao.editUsersRole(userIds, roleId);
//	}

//	public User getUserInfo(User user) {
//		User u = new User();
//		
//		
//		return userDao.getUserInfo(u);
//	}
//
//	public User editUserInfo(User user) {
//		return userDao.editUserInfo(user);
//	}

	/* (non-Javadoc)
	 * 查询一共有多少条记录
	 * @see com.hausontech.hrs.api.userManager.service.IUserService#selectCount()
	 */
	@Override
	public int selectCount() {
		return mrdmUserMapper.selectCount();
	}
	
	
	
}
