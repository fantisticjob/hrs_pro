package com.hausontech.hrs.api.userManager.service;

import java.util.List;

import com.hausontech.hrs.api.IBaseService;
import com.hausontech.hrs.bean.userManager.pojo.EasyuiTreeNode;
import com.hausontech.hrs.bean.userManager.pojo.Menu;

/**
 * 菜单Service
 * 
 * @author  
 * 
 */
public interface IMenuService extends IBaseService {

	/**
	 * 获得菜单�?
	 * 
	 * @param id
	 * @return
	 */
	public List<EasyuiTreeNode> tree(String id);

	public List<Menu> treegrid(String id);

	public Menu add(Menu menu);

	public void del(Menu menu);

	public Menu edit(Menu menu);

}
