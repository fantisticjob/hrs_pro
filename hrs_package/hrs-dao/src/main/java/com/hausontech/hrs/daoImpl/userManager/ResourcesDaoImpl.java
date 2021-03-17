/*package com.hausontech.hrs.daoImpl.userManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hausontech.hrs.api.IBaseDao;
import com.hausontech.hrs.api.userManager.dao.IResourcesDao;
import com.hausontech.hrs.bean.userManager.MrdmResources;
import com.hausontech.hrs.bean.userManager.MrdmroleResources;
import com.hausontech.hrs.bean.userManager.pojo.EasyuiTreeNode;
import com.hausontech.hrs.bean.userManager.pojo.Resources;
import com.hausontech.hrs.utils.ResourcesComparator;

@Repository("resourcesDao")
public class ResourcesDaoImpl implements IResourcesDao {

	private IBaseDao<MrdmResources> resourcesDao;
	private IBaseDao<MrdmroleResources> mrdmroleResourcesDao;

	@Autowired
	public void setSyroleSyresourcesDao(IBaseDao<MrdmroleResources> mrdmroleResourcesDao) {
		this.mrdmroleResourcesDao = mrdmroleResourcesDao;
	}

	@Autowired
	public void setResourcesDao(IBaseDao<MrdmResources> resourcesDao) {
		this.resourcesDao = resourcesDao;
	}

	@Transactional(readOnly = true)
	public List<EasyuiTreeNode> tree(String id) {
		String hql = "from MrdmResources t where t.mrdmResources is null order by t.seq";
		if (id != null && !id.trim().equals("")) {
			hql = "from MrdmResources t where t.mrdmResources.id ='" + id + "' order by t.seq";
		}
		List<MrdmResources> resourceses = resourcesDao.find(hql);
		List<EasyuiTreeNode> tree = new ArrayList<EasyuiTreeNode>();
		for (MrdmResources resources : resourceses) {
			tree.add(tree(resources, false));
		}
		return tree;
	}

	private EasyuiTreeNode tree(MrdmResources mrdmResources, boolean recursive) {
		EasyuiTreeNode node = new EasyuiTreeNode();
		node.setId(mrdmResources.getId());
		node.setText(mrdmResources.getText());
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("src", mrdmResources.getSrc());
		node.setAttributes(attributes);
		if (mrdmResources.getMrdmResourceses() != null && mrdmResources.getMrdmResourceses().size() > 0) {
			node.setState("closed");
			if (recursive) {// 递归查询子节�?
				List<MrdmResources> resourcesList = new ArrayList<MrdmResources>(mrdmResources.getMrdmResourceses());
				Collections.sort(resourcesList, new ResourcesComparator());// 排序
				List<EasyuiTreeNode> children = new ArrayList<EasyuiTreeNode>();
				for (MrdmResources r : resourcesList) {
					EasyuiTreeNode t = tree(r, true);
					children.add(t);
				}
				node.setChildren(children);
			}
		}
		return node;
	}

	@Transactional(readOnly = true)
	public List<Resources> treegrid(String id) {
		List<Resources> treegrid = new ArrayList<Resources>();
		String hql = "from MrdmResources t where t.mrdmResources is null order by t.seq";
		if (id != null && !id.trim().equals("")) {
			hql = "from MrdmResources t where t.mrdmResources.id = '" + id + "' order by t.seq";
		}
		List<MrdmResources> resourceses = resourcesDao.find(hql);
		for (MrdmResources res : resourceses) {
			Resources r = new Resources();
			BeanUtils.copyProperties(res, r);
			if (res.getMrdmResources() != null) {
				r.setParentId(res.getMrdmResources().getId());
				r.setParentText(res.getMrdmResources().getText());
			}
			if (res.getMrdmResourceses() != null && res.getMrdmResourceses().size() > 0) {
				r.setState("closed");
			}
			treegrid.add(r);
		}
		return treegrid;
	}

	public Resources add(Resources resources) {
		MrdmResources mrdmResources = new MrdmResources();
		BeanUtils.copyProperties(resources, mrdmResources);
		if (resources.getParentId() != null && !resources.getParentId().trim().equals("")) {
			mrdmResources.setMrdmResources(resourcesDao.load(MrdmResources.class, resources.getParentId()));
		}
		resourcesDao.save(mrdmResources);
		return resources;
	}

	public void del(Resources resources) {
		MrdmResources mrdmResources = resourcesDao.get(MrdmResources.class, resources.getId());
		if (resources != null) {
			recursiveDelete(mrdmResources);
		}
	}

	private void recursiveDelete(MrdmResources mrdmResources) {
		if (mrdmResources.getMrdmResourceses() != null && mrdmResources.getMrdmResourceses().size() > 0) {
			Set<MrdmResources> res = mrdmResources.getMrdmResourceses();
			for (MrdmResources t : res) {
				recursiveDelete(t);
			}
		}

		List<MrdmroleResources> mrdmroleResourcesList = mrdmroleResourcesDao.find("from MrdmroleResources t where t.mrdmResources=?", mrdmResources);
		if (mrdmroleResourcesList != null) {// 删除资源�?,�?要现将角色资源关系表中的相关记录删除
			for (MrdmroleResources res : mrdmroleResourcesList) {
				mrdmroleResourcesDao.delete(res);
			}
		}

		resourcesDao.delete(mrdmResources);
	}

	public Resources edit(Resources resources) {
		MrdmResources mrdmResources = resourcesDao.get(MrdmResources.class, resources.getId());
		if (mrdmResources != null) {
			BeanUtils.copyProperties(resources, mrdmResources);
			if (!mrdmResources.getId().equals("0")) {// 跟节点不可以修改上级节点
				mrdmResources.setMrdmResources(resourcesDao.get(MrdmResources.class, resources.getParentId()));
			}
		}
		return resources;
	}



}
*/