package com.hausontech.hrs.bean.allocationManager.model;

import com.hausontech.hrs.bean.AuditBean2;

public class AllocTargetRecord extends AuditBean2 {
	private long targetId;
	private long allocRuleId;
	private String targetType;
	private String actualFlag;
	private String currencyType;
	private String currencyCode;
	private String amountType;
	private String directionCode;
	private String description;
	public long getTargetId() {
		return targetId;
	}
	public void setTargetId(long targetId) {
		this.targetId = targetId;
	}
	public long getAllocRuleId() {
		return allocRuleId;
	}
	public void setAllocRuleId(long allocRuleId) {
		this.allocRuleId = allocRuleId;
	}
	public String getTargetType() {
		return targetType;
	}
	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}
	public String getActualFlag() {
		return actualFlag;
	}
	public void setActualFlag(String actualFlag) {
		this.actualFlag = actualFlag;
	}
	public String getCurrencyType() {
		return currencyType;
	}
	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getAmountType() {
		return amountType;
	}
	public void setAmountType(String amountType) {
		this.amountType = amountType;
	}
	public String getDirectionCode() {
		return directionCode;
	}
	public void setDirectionCode(String directionCode) {
		this.directionCode = directionCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
