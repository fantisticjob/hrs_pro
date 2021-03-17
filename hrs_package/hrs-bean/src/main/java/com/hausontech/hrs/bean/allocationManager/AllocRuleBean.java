package com.hausontech.hrs.bean.allocationManager;

import java.util.Date;

import com.hausontech.hrs.bean.AuditBean2;

public class AllocRuleBean extends AuditBean2 {
	private int ruleId;
	private int lineNum;
	private String ruleName;
	private String driverType;
	private String description; 
	private Date startDateActive;
	private Date endDateActive;
	private int sourceId;
	private int driverId;
	private int targetId;
	private int offsetId;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getRuleId() {
		return ruleId;
	}
	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}
	public int getLineNum() {
		return lineNum;
	}
	public void setLineNum(int lineNum) {
		this.lineNum = lineNum;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getDriverType() {
		return driverType;
	}
	public void setDriverType(String driverType) {
		this.driverType = driverType;
	}
	public Date getStartDateActive() {
		return startDateActive;
	}
	public void setStartDateActive(Date startDateActive) {
		this.startDateActive = startDateActive;
	}
	public Date getEndDateActive() {
		return endDateActive;
	}
	public void setEndDateActive(Date endDateActive) {
		this.endDateActive = endDateActive;
	}
	public int getSourceId() {
		return sourceId;
	}
	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}
	public int getDriverId() {
		return driverId;
	}
	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}
	public int getTargetId() {
		return targetId;
	}
	public void setTargetId(int targetId) {
		this.targetId = targetId;
	}
	public int getOffsetId() {
		return offsetId;
	}
	public void setOffsetId(int offsetId) {
		this.offsetId = offsetId;
	}

}
