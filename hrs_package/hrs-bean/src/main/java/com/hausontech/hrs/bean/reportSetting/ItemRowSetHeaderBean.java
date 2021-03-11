package com.hausontech.hrs.bean.reportSetting;

import org.apache.commons.lang.StringUtils;

import com.hausontech.hrs.bean.AuditBean;

public class ItemRowSetHeaderBean extends AuditBean {

	private int rowSetId;
	private String rowSetName;
	private String ruleCode;
	private String extItemType;
	private String description;
	
	public int getRowSetId() {
		return rowSetId;
	}
	public void setRowSetId(int rowSetId) {
		this.rowSetId = rowSetId;
	}
	public String getRowSetName() {
		return rowSetName;
	}
	public void setRowSetName(String rowSetName) {
		this.rowSetName = rowSetName;
	}
	public String getRuleCode() {
		return ruleCode;
	}
	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
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
	    	if (!super.isEmpty() || rowSetId != 0 ||
	    			!StringUtils.isBlank(rowSetName) ||
	    			!StringUtils.isBlank(extItemType) ||
	    			
	    			!StringUtils.isBlank(ruleCode)||
	    			!StringUtils.isBlank(description) ) {
	    		return false;
	    	}
	    	return true;
	    }

	
}
