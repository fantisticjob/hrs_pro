package com.hausontech.hrs.serviceImpl.userManager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hausontech.hrs.api.userManager.dao.IResourcesDao;
import com.hausontech.hrs.api.userManager.service.IResourcesService;
import com.hausontech.hrs.bean.userManager.pojo.EasyuiTreeNode;
import com.hausontech.hrs.bean.userManager.pojo.Resources;
import com.hausontech.hrs.serviceImpl.BaseServiceImpl;

/**
 * 资源Service实现类
 * 
 * @author  
 * 
 */
@Service("resourcesService")
public class ResourcesServiceImpl extends BaseServiceImpl implements IResourcesService {

	@Autowired
	private IResourcesDao resourcesDao;
	public List<EasyuiTreeNode> tree(String id) { return resourcesDao.tree(id);};

	public List<Resources> treegrid(String id) { return resourcesDao.treegrid(id);};

	public Resources add(Resources resources) { return resourcesDao.add(resources);};

	public void del(Resources resources) { resourcesDao.del(resources);};

	public Resources edit(Resources resources) { return resourcesDao.edit(resources);};

}
