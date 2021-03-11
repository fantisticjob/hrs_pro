package com.hausontech.hrs.bean.reportSetting;

import org.apache.commons.lang.StringUtils;

import com.hausontech.hrs.bean.AuditBean;

public class ItemGroupRuleHeaderBean extends AuditBean {
	private int ruleHeaderId;
	private String ruleCode;
	private String description;
	public int getRuleHeaderId() {
		return ruleHeaderId;
	}
	public void setRuleHeaderId(int ruleHeaderId) {
		this.ruleHeaderId = ruleHeaderId;
	}
	public String getRuleCode() {
		return ruleCode;
	}
	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
    public boolean isEmpty() {
    	if (!super.isEmpty() || ruleHeaderId != 0 ||
    			!StringUtils.isBlank(ruleCode) ||
    			!StringUtils.isBlank(description)) {
    		return false;
    	}
    	return true;
    }
}
