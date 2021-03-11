package com.hausontech.hrs.utils;

import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hausontech.hrs.bean.userManager.MrdmRole;

/**
 * 角色排序
 * 
 * @author  
 * 
 */
public class RoleComparator implements Comparator<MrdmRole> {

	private static final Logger logger = LoggerFactory.getLogger(RoleComparator.class);

	public int compare(MrdmRole o1, MrdmRole o2) {
		int i1 = o1.getSeq() != null ? o1.getSeq().intValue() : -1;
		int i2 = o2.getSeq() != null ? o2.getSeq().intValue() : -1;
		logger.debug("i1[" + i1 + "]-i2[" + i2 + "]=" + (i1 - i2));
		return i1 - i2;
	}

}
