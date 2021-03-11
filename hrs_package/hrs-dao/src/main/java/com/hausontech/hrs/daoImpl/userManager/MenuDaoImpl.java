package com.hausontech.hrs.daoImpl.userManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hausontech.hrs.api.IBaseDao;
import com.hausontech.hrs.api.userManager.dao.IMenuDao;
import com.hausontech.hrs.bean.userManager.MrdmMenu;
import com.hausontech.hrs.bean.userManager.pojo.EasyuiTreeNode;
import com.hausontech.hrs.bean.userManager.pojo.Menu;
import com.hausontech.hrs.utils.MenuComparator;

@Repository("menuServiceDao")
public class MenuDaoImpl implements IMenuDao{
	
	private IBaseDao<MrdmMenu> menuDao;

	@Autowired
	public void setMenuDao(IBaseDao<MrdmMenu> menuDao) {
		this.menuDao = menuDao;
	}

	@Transactional(readOnly = true)
	public List<EasyuiTreeNode> tree(String id) {
		LinkedList<Object> list  = new LinkedList<Object>();
		String hql = "from MrdmMenu t where t.mrdmMenu is null order by t.seq";
		if (id != null && !id.trim().equals("")) {
			hql = "from MrdmMenu t where t.mrdmMenu.id = ? order by t.seq";
			list.add(id);
		}
	
		List<MrdmMenu> mrdmMenu = menuDao.find(hql,list);
		List<EasyuiTreeNode> tree = new ArrayList<EasyuiTreeNode>();
		for (MrdmMenu menu : mrdmMenu) {
			tree.add(tree(menu, false));
		}
		return tree;
	}

	private EasyuiTreeNode tree(MrdmMenu menu, boolean recursive) {
		EasyuiTreeNode node = new EasyuiTreeNode();
		node.setId(menu.getId());
		node.setText(menu.getText());
		node.setIconCls(menu.getIconcls());
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("src", menu.getSrc());
		node.setAttributes(attributes);
		if (menu.getMrdmMenus() != null && menu.getMrdmMenus().size() > 0) {
			node.setState("closed");
			if (recursive) {// 递归查询子节�?
				List<MrdmMenu> menuList = new ArrayList<MrdmMenu>(menu.getMrdmMenus());
				Collections.sort(menuList, new MenuComparator());// 排序
				
				List<EasyuiTreeNode> children = new ArrayList<EasyuiTreeNode>();
				for (MrdmMenu m : menuList) {
					EasyuiTreeNode t = tree(m, true);
					children.add(t);
				}
				node.setChildren(children);
			}
		}
		return node;
	}

	@Transactional(readOnly = true)
	public List<Menu> treegrid(String id) {
		List<Menu> treegrid = new ArrayList<Menu>();
		String hql = "from MrdmMenu t where t.mrdmMenu is null order by t.seq";
		if (id != null && !id.trim().equals("")) {
			hql = "from MrdmMenu t where t.mrdmMenu.id = '" + id + "' order by t.seq";
		}
		
		List<MrdmMenu> menus = menuDao.find(hql);
		for (MrdmMenu mrdmMenu : menus) {
			Menu m = new Menu();
			BeanUtils.copyProperties(mrdmMenu, m);
			if (mrdmMenu.getMrdmMenu() != null) {
				m.setParentId(mrdmMenu.getMrdmMenu().getId());
				m.setParentText(mrdmMenu.getMrdmMenu().getText());
			}
			m.setIconCls(mrdmMenu.getIconcls());
			if (mrdmMenu.getMrdmMenus() != null && mrdmMenu.getMrdmMenus().size() > 0) {
				m.setState("closed");
			}
			treegrid.add(m);
		}
		return treegrid;
	}

	public Menu add(Menu menu) {
		MrdmMenu mrdmMenu = new MrdmMenu();
		BeanUtils.copyProperties(menu, mrdmMenu);
		mrdmMenu.setIconcls(menu.getIconCls());
		if (menu.getParentId() != null && !menu.getParentId().trim().equals("")) {
			mrdmMenu.setMrdmMenu(menuDao.load(MrdmMenu.class, menu.getParentId()));
		}
		menuDao.save(mrdmMenu);
		return menu;
	}

	public void del(Menu menu) {
		MrdmMenu symenu = menuDao.get(MrdmMenu.class, menu.getId());
		if (symenu != null) {
			recursiveDelete(symenu);
		}
	}

	private void recursiveDelete(MrdmMenu mrdmMenu) {
		if (mrdmMenu.getMrdmMenus() != null && mrdmMenu.getMrdmMenus().size() > 0) {
			Set<MrdmMenu> menus = mrdmMenu.getMrdmMenus();
			for (MrdmMenu t : menus) {
				recursiveDelete(t);
			}
		}
		menuDao.delete(mrdmMenu);
	}

	public Menu edit(Menu menu) {
		MrdmMenu mrdmMenu = menuDao.get(MrdmMenu.class, menu.getId());
		if (mrdmMenu != null) {
			BeanUtils.copyProperties(menu, mrdmMenu);
			mrdmMenu.setIconcls(menu.getIconCls());
			if (!mrdmMenu.getId().equals("0")) {// 根节点不可以修改上级节点
				mrdmMenu.setMrdmMenu(menuDao.get(MrdmMenu.class, menu.getParentId()));
			}
		}
		return menu;
	}

}
