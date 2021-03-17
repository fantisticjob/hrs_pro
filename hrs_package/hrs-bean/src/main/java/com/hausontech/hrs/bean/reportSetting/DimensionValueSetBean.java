package com.hausontech.hrs.bean.reportSetting;

public class DimensionValueSetBean {
	private int dimensionId;
	private String dimensionCode;
	private String dimensionDescription;
	private String dimSegment;
	
	private int dimValueId;
	private String dimValue;
	private String dimValDescription;
	private String summaryFlag;
	
	private String dispValueName;
	
	
	public String getDispValueName() {
		return dispValueName;
	}
	public void setDispValueName(String dispValueName) {
		this.dispValueName = dispValueName;
	}
	public int getDimensionId() {
		return dimensionId;
	}
	public void setDimensionId(int dimensionId) {
		this.dimensionId = dimensionId;
	}
	public String getDimensionCode() {
		return dimensionCode;
	}
	public void setDimensionCode(String dimensionCode) {
		this.dimensionCode = dimensionCode;
	}
	public String getDimensionDescription() {
		return dimensionDescription;
	}
	public void setDimensionDescription(String dimensionDescription) {
		this.dimensionDescription = dimensionDescription;
	}
	public String getDimSegment() {
		return dimSegment;
	}
	public void setDimSegment(String dimSegment) {
		this.dimSegment = dimSegment;
	}
	public int getDimValueId() {
		return dimValueId;
	}
	public void setDimValueId(int dimValueId) {
		this.dimValueId = dimValueId;
	}
	public String getDimValue() {
		return dimValue;
	}
	public void setDimValue(String dimValue) {
		this.dimValue = dimValue;
	}
	public String getDimValDescription() {
		return dimValDescription;
	}
	public void setDimValDescription(String dimValDescription) {
		this.dimValDescription = dimValDescription;
	}
	public String getSummaryFlag() {
		return summaryFlag;
	}
	public void setSummaryFlag(String summaryFlag) {
		this.summaryFlag = summaryFlag;
	}

}
