package com.hausontech.hrs.bean.reportSetting;

import org.apache.commons.lang.StringUtils;

import com.hausontech.hrs.bean.AuditBean2;

public class ItemGroupRuleLineBean extends AuditBean2 {
	
	private long ruleLineId;
	private int ruleHeaderId;
	private String dimSegment;
	private String dimSegDescription;
	private int ruleLineSeq;
	
	public long getRuleLineId() {
		return ruleLineId;
	}
	public void setRuleLineId(long ruleLineId) {
		this.ruleLineId = ruleLineId;
	}
	public int getRuleHeaderId() {
		return ruleHeaderId;
	}
	public void setRuleHeaderId(int ruleHeaderId) {
		this.ruleHeaderId = ruleHeaderId;
	}
	public String getDimSegment() {
		return dimSegment;
	}
	public void setDimSegment(String dimSegment) {
		this.dimSegment = dimSegment;
	}
	
    public String getDimSegDescription() {
		return dimSegDescription;
	}
	public void setDimSegDescription(String dimSegDescription) {
		this.dimSegDescription = dimSegDescription;
	}
	public int getRuleLineSeq() {
		return ruleLineSeq;
	}
	public void setRuleLineSeq(int ruleLineSeq) {
		this.ruleLineSeq = ruleLineSeq;
	}
	
	public boolean isEmpty() {
    	if (!super.isEmpty() || ruleLineId != 0 ||
    			ruleHeaderId != 0 ||
    			ruleLineSeq != 0 ||
    			StringUtils.isBlank(dimSegDescription) || 
    			!StringUtils.isBlank(dimSegment)) {
    		return false;
    	}
    	return true;
    }

}
