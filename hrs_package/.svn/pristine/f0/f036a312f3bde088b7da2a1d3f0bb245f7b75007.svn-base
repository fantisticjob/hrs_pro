package com.hausontech.hrs.api.userManager.service;

import java.util.List;

import com.hausontech.hrs.api.IBaseService;
import com.hausontech.hrs.bean.userManager.MrdmResources;

/**
 * 权限Service
 * 
 * @author 
 * 
 */
public interface IAuthService extends IBaseService {

	/**
	 * 查询有不要验证的资源
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
	 * 验证用户是否有访问此资源的权�?
	 * 
	 * @param userId
	 * @param requestPath
	 * @return
	 */
	public boolean checkAuth(String userId, String requestPath);

}
