package com.hausontech.hrs.bean.allocationManager.model;

import com.hausontech.hrs.bean.AuditBean2;

public class AllocSourceRecord extends AuditBean2 implements java.io.Serializable {
	private long sourceId;
	private long allocRuleId;
	private String sourceType;
	private double constantValue;
	private String actualFlag;
	private String currencyType;
	private String currencyCode;
	private String amountType;
	private String directionCode;
	private String operator;
	private String description;
	public long getSourceId() {
		return sourceId;
	}
	public void setSourceId(long sourceId) {
		this.sourceId = sourceId;
	}
	public long getAllocRuleId() {
		return allocRuleId;
	}
	public void setAllocRuleId(long allocRuleId) {
		this.allocRuleId = allocRuleId;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	public double getConstantValue() {
		return constantValue;
	}
	public void setConstantValue(double constantValue) {
		this.constantValue = constantValue;
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
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	

}
