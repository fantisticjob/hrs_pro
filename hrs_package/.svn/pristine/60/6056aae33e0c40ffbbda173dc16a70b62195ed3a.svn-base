package com.hausontech.hrs.api.userManager.dao;

import java.util.List;

import com.hausontech.hrs.bean.userManager.MrdmResources;

public interface IAuthDao {
	
	/**
	 * 查询所有不需要验证的资源
	 * 
	 * @return
	 */
	public List<MrdmResources> offResourcesList();

	/**
	 * 通过资源路径获得资源对象
	 * 
	 * @param requestPath
	 * @return
	 */
	public MrdmResources getSyresourcesByRequestPath(String requestPath);

	/**
	 * 验证用户是否有访问此资源的权限
	 * 
	 * @param userId
	 * @param requestPath
	 * @return
	 */
	public boolean checkAuth(String userId, String requestPath);

}
