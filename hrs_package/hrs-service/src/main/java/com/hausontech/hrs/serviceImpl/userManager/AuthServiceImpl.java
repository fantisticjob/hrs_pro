/*package com.hausontech.hrs.serviceImpl.userManager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hausontech.hrs.api.BaseServiceImpl;
import com.hausontech.hrs.api.userManager.dao.IAuthDao;
import com.hausontech.hrs.api.userManager.service.IAuthService;
import com.hausontech.hrs.bean.userManager.MrdmResources;

*//**
 * 权限Service
 * 
 * @author  
 * 
 *//*
@Service("authService")
public class AuthServiceImpl extends BaseServiceImpl implements IAuthService {

	@Autowired
	private IAuthDao authServiceDao;
	*//**
	 * 查询所有不需要验证的资源
	 * 
	 * @return
	 *//*
	public List<MrdmResources> offResourcesList() { return authServiceDao.offResourcesList();}

	*//**
	 * 通过资源路径获得资源对象
	 * 
	 * @param requestPath
	 * @return
	 *//*
	public MrdmResources getSyresourcesByRequestPath(String requestPath) { return authServiceDao.getSyresourcesByRequestPath(requestPath);}

	*//**
	 * 验证用户是否有访问此资源的权限
	 * 
	 * @param userId
	 * @param requestPath
	 * @return
	 *//*
	public boolean checkAuth(String userId, String requestPath) { 
		return authServiceDao.checkAuth(userId, requestPath);}

}
*/