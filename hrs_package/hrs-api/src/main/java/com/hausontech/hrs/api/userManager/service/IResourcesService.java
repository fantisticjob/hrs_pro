package com.hausontech.hrs.api.userManager.service;

import java.util.List;

import com.hausontech.hrs.api.IBaseService;
import com.hausontech.hrs.bean.userManager.pojo.EasyuiTreeNode;
import com.hausontech.hrs.bean.userManager.pojo.Resources;

/**
 * 资源Service
 * 
 * @author  
 * 
 */
public interface IResourcesService extends IBaseService {

	public List<EasyuiTreeNode> tree(String id);

	public List<Resources> treegrid(String id);

	public Resources add(Resources resources);

	public void del(Resources resources);

	public Resources edit(Resources resources);

}
