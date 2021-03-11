package com.hausontech.hrs.bean.reportSetting;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.hausontech.hrs.bean.AuditBean;

public class ItemHeaderBean extends AuditBean {
	private long itemHeaderId;
	private String itemCode;
	private String itemDescription;
	private String ruleCode;
	private Date startDate;
	private String isContent;
	private String isCalculation;
		
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public long getItemHeaderId() {
		return itemHeaderId;
	}
	public void setItemHeaderId(long itemHeaderId) {
		this.itemHeaderId = itemHeaderId;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getItemDescription() {
		return itemDescription;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	public String getRuleCode() {
		return ruleCode;
	}
	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}
	
    public boolean isEmpty() {
    	if (!super.isEmpty() || itemHeaderId != 0 ||
    			!StringUtils.isBlank(itemCode) ||
    			!StringUtils.isBlank(itemDescription) || 
    			!StringUtils.isBlank(ruleCode) ||
    			startDate == null) {
    		return false;
    	}
    	return true;
    }
}
