package com.hausontech.hrs.serviceImpl.userManager;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hausontech.hrs.bean.userManager.LoginUserDetail;
import com.hausontech.hrs.bean.userManager.MrdmUser;
import com.hausontech.hrs.bean.userManager.MrdmuserRole2;
import com.hausontech.hrs.bean.userManager.pojo.User;
import com.hausontech.hrs.daoImpl.userManager.mapper.MrdmRoleMapper;
import com.hausontech.hrs.daoImpl.userManager.mapper.MrdmUserMapper;
import com.hausontech.hrs.daoImpl.userManager.mapper.MrdmuserRole2Mapper;

@Service("loginUserServiceImpl")
public class LoginUserServiceImpl implements UserDetailsService{


	@Autowired
	private MrdmUserMapper mrdmUserMapper;
	@Autowired
	private MrdmRoleMapper mrdmRoleMapper;
	@Autowired
	private MrdmuserRole2Mapper mrdmuserRole2Mapper;
	
	public MrdmUserMapper getMrdmUserMapper() {
		return mrdmUserMapper;
	}

	public void setMrdmUserMapper(MrdmUserMapper mrdmUserMapper) {
		this.mrdmUserMapper = mrdmUserMapper;
	}

	public MrdmRoleMapper getMrdmRoleMapper() {
		return mrdmRoleMapper;
	}

	public void setMrdmRoleMapper(MrdmRoleMapper mrdmRoleMapper) {
		this.mrdmRoleMapper = mrdmRoleMapper;
	}

	public MrdmuserRole2Mapper getMrdmuserRole2Mapper() {
		return mrdmuserRole2Mapper;
	}

	public void setMrdmuserRole2Mapper(MrdmuserRole2Mapper mrdmuserRole2Mapper) {
		this.mrdmuserRole2Mapper = mrdmuserRole2Mapper;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		 User user = this.getUserInfo(username);
		 String roles = user.getRoleText();
		 Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		 if (0<roles.length()) {
			String[] ss = roles.split(",");
			for (int i = 0; i < ss.length; i++) {
				SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(ss[i]);
				grantedAuthorities.add(grantedAuthority);
			}
		}
		 LoginUserDetail userDetail = new LoginUserDetail();
		 userDetail.setAuthorities(grantedAuthorities);
		 userDetail.setUser(user);
	     return userDetail;
		
	}


	
	public User getUserInfo(String username) {
		MrdmUser mrdmUser = mrdmUserMapper.selectByPrimaryKey(username);
		User user = new User();
		user.setId(mrdmUser.getUserName());
		user.setName(mrdmUser.getFullName());
		user.setPassword(mrdmUser.getPassword());
		user.setCreatedatetime(mrdmUser.getCreationDate());
		user.setModifydatetime(mrdmUser.getLastUpdateDate());
		
		String roleText = "";
		List<MrdmuserRole2> userRoleSet = (List<MrdmuserRole2>) mrdmuserRole2Mapper.selectByUserId(username);
		if (userRoleSet != null && userRoleSet.size() > 0) {
			for (int i = 0; i < userRoleSet.size(); i++) {
				MrdmuserRole2 res = userRoleSet.get(i);
				if (!roleText.equals("")) {
					roleText =roleText+ ",";
				}
				String roleId = res.getRoleId();
				roleText =roleText+ mrdmRoleMapper.selectByPrimaryKey(roleId).getText();
			}
			
		}
		user.setRoleText(roleText);
		return user;
	}


	
	
	
}
