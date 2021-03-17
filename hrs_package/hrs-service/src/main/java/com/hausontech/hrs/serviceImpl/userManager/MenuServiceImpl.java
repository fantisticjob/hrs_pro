//package com.hausontech.hrs.serviceImpl.userManager;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.hausontech.hrs.api.BaseServiceImpl;
//import com.hausontech.hrs.api.userManager.dao.IMenuDao;
//import com.hausontech.hrs.api.userManager.service.IMenuService;
//import com.hausontech.hrs.bean.userManager.pojo.EasyuiTreeNode;
//import com.hausontech.hrs.bean.userManager.pojo.Menu;
//
///**
// * 菜单Service实现类
// * 
// * @author  
// * 
// */
//@Service("menuService")
//public class MenuServiceImpl extends BaseServiceImpl implements IMenuService {
//
//	@Autowired
//	private IMenuDao menuServiceDao;
//	/**
//	 * 获得菜单树
//	 * 
//	 * @param id
//	 * @return
//	 */
//	public List<EasyuiTreeNode> tree(String id) { return menuServiceDao.tree(id);}
//	
//	public List<Menu> treegrid(String id) { return menuServiceDao.treegrid(id);}
//
//	public Menu add(Menu menu) { return menuServiceDao.add(menu);}
//
//	public void del(Menu menu) { menuServiceDao.del(menu);}
//
//	public Menu edit(Menu menu) { return menuServiceDao.edit(menu);}
//
//}
