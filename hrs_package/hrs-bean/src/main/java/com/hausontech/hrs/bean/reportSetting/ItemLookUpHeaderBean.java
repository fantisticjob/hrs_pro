package com.hausontech.hrs.bean.reportSetting;

import org.apache.commons.lang.StringUtils;

import com.hausontech.hrs.bean.AuditBean2;

public class ItemLookUpHeaderBean extends AuditBean2{
	
	private int lookUpTypeId;
	private String lookUpType;
	private String description;
	private String lookupValSearch;
	
	public int getLookUpTypeId() {
		return lookUpTypeId;
	}
	public void setLookUpTypeId(int lookUpTypeId) {
		this.lookUpTypeId = lookUpTypeId;
	}
	public String getLookUpType() {
		return lookUpType;
	}
	public void setLookUpType(String lookUpType) {
		this.lookUpType = lookUpType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getLookupValSearch() {
		return lookupValSearch;
	}
	public void setLookupValSearch(String lookupValSearch) {
		this.lookupValSearch = lookupValSearch;
	}
	public boolean isEmpty() {
	    	if (!super.isEmpty() || lookUpTypeId != 0 ||
	    			!StringUtils.isBlank(lookUpType) ||
	    			!StringUtils.isBlank(description)) {
	    		return false;
	    	}
	    	return true;
	    }

}
