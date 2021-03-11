package com.hausontech.hrs.bean.dimensionManager;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.hausontech.hrs.bean.AuditBean;

public class DimensionValueBean extends AuditBean {
	private long dimensionValueId;
	private long dimensionId;
	private String dimensionValue;
	private String description;
	private String accountType;
	private String summaryFlag;
	private String dimensionLevel;
	private Date startDate;
	private Date endDate;
	private String source;
	private String attribute1;
	private String attribute2;
	private String attribute3;
	private String attribute4;
	private String attribute5;
	private String attribute6;
	private String attribute7;
	private String attribute8;
	private String attribute9;
	private String attribute10;
	private boolean securityEnabled = false;

	public boolean isSecurityEnabled() {
		return securityEnabled;
	}
	public void setSecurityEnabled(boolean securityEnabled) {
		this.securityEnabled = securityEnabled;
	}
	public String getAttribute1() {
		return attribute1;
	}
	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}
	public String getAttribute2() {
		return attribute2;
	}
	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}
	public String getAttribute3() {
		return attribute3;
	}
	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}
	public String getAttribute4() {
		return attribute4;
	}
	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}
	public String getAttribute5() {
		return attribute5;
	}
	public void setAttribute5(String attribute5) {
		this.attribute5 = attribute5;
	}
	public String getAttribute6() {
		return attribute6;
	}
	public void setAttribute6(String attribute6) {
		this.attribute6 = attribute6;
	}
	public String getAttribute7() {
		return attribute7;
	}
	public void setAttribute7(String attribute7) {
		this.attribute7 = attribute7;
	}
	public String getAttribute8() {
		return attribute8;
	}
	public void setAttribute8(String attribute8) {
		this.attribute8 = attribute8;
	}
	public String getAttribute9() {
		return attribute9;
	}
	public void setAttribute9(String attribute9) {
		this.attribute9 = attribute9;
	}
	public String getAttribute10() {
		return attribute10;
	}
	public void setAttribute10(String attribute10) {
		this.attribute10 = attribute10;
	}
public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	/*	private String isFinAcctFlag;
	
	public String getIsFinAcctFlag() {
		return isFinAcctFlag;
	}
	public void setIsFinAcctFlag(String isFinAcctFlag) {
		this.isFinAcctFlag = isFinAcctFlag;
	}*/
	public long getDimensionValueId() {
		return dimensionValueId;
	}
	public void setDimensionValueId(long dimensionValueId) {
		this.dimensionValueId = dimensionValueId;
	}
	public long getDimensionId() {
		return dimensionId;
	}
	public void setDimensionId(long dimensionId) {
		this.dimensionId = dimensionId;
	}
	public String getDimensionValue() {
		return dimensionValue;
	}
	public void setDimensionValue(String dimensionValue) {
		this.dimensionValue = dimensionValue;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getSummaryFlag() {
		return summaryFlag;
	}
	public void setSummaryFlag(String summaryFlag) {
		this.summaryFlag = summaryFlag;
	}
	public String getDimensionLevel() {
		return dimensionLevel;
	}
	public void setDimensionLevel(String dimensionLevel) {
		this.dimensionLevel = dimensionLevel;
	}
	
    public boolean isEmpty() {
    	if (dimensionId != 0 || dimensionValueId != 0 || 
    			!StringUtils.isBlank(dimensionValue) ||
    			!StringUtils.isBlank(description) || 
    			!StringUtils.isBlank(accountType) ||
    			!StringUtils.isBlank(summaryFlag) || 
    			!StringUtils.isBlank(dimensionLevel) ||
    			!super.isEmpty()) {
    		return false;
    	}
    	return true;
    }
	

}
