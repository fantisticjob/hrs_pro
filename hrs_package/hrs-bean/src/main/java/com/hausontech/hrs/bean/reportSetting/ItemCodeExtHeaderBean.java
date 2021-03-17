package com.hausontech.hrs.bean.reportSetting;

import org.apache.commons.lang.StringUtils;

import com.hausontech.hrs.bean.AuditBean2;

public class ItemCodeExtHeaderBean extends AuditBean2{

	private int extItemTypeId;
	private String extItemType;
	private String description;

	public int getExtItemTypeId() {
		return extItemTypeId;
	}
	public void setExtItemTypeId(int extItemTypeId) {
		this.extItemTypeId = extItemTypeId;
	}
	public String getExtItemType() {
		return extItemType;
	}
	public void setExtItemType(String extItemType) {
		this.extItemType = extItemType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public boolean isEmpty() {
    	if (!super.isEmpty() || extItemTypeId != 0 ||
    			!StringUtils.isBlank(extItemType) ||
    			!StringUtils.isBlank(description)) {
    		return false;
    	}
    	return true;
    }
}
