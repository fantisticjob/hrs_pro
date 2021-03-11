package com.hausontech.hrs.utils;

import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hausontech.hrs.bean.userManager.MrdmMenu;


/**
 * 菜单排序
 * 
 * @author  
 * 
 */
public class MenuComparator implements Comparator<MrdmMenu> {

	private static final Logger logger = LoggerFactory.getLogger(MenuComparator.class);

	public int compare(MrdmMenu o1, MrdmMenu o2) {
		int i1 = o1.getSeq() != null ? o1.getSeq().intValue() : -1;
		int i2 = o2.getSeq() != null ? o2.getSeq().intValue() : -1;
		logger.debug("i1[" + i1 + "]-i2[" + i2 + "]=" + (i1 - i2));
		return i1 - i2;
	}

}
