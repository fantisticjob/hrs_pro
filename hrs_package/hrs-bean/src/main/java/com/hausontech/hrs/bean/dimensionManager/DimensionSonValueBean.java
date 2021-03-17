package com.hausontech.hrs.bean.dimensionManager;

import org.apache.commons.lang.StringUtils;

import com.hausontech.hrs.bean.AuditBean2;

public class DimensionSonValueBean extends AuditBean2 {
	private long hierarchyId;
	private String dimValue;
	private long dimensionId;
	private String childDimValueLow;
	private String childDimValueHigh;
	private String source;
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public long getHierarchyId() {
		return hierarchyId;
	}
	public void setHierarchyId(long hierarchyId) {
		this.hierarchyId = hierarchyId;
	}
	public long getDimensionId() {
		return dimensionId;
	}
	public void setDimensionId(long dimensionId) {
		this.dimensionId = dimensionId;
	}
	public String getDimValue() {
		return dimValue;
	}
	public void setDimValue(String dimValue) {
		this.dimValue = dimValue;
	}
	public String getChildDimValueLow() {
		return childDimValueLow;
	}
	public void setChildDimValueLow(String childDimValueLow) {
		this.childDimValueLow = childDimValueLow;
	}
	public String getChildDimValueHigh() {
		return childDimValueHigh;
	}
	public void setChildDimValueHigh(String childDimValueHigh) {
		this.childDimValueHigh = childDimValueHigh;
	}
	
	
    public boolean isEmpty() {
    	if (dimensionId != 0 || hierarchyId != 0 || 
    			!StringUtils.isBlank(dimValue) ||
    			!StringUtils.isBlank(childDimValueLow) || 
    			!StringUtils.isBlank(childDimValueHigh) ||
    			!super.isEmpty()) {
    		return false;
    	}
    	return true;
    }
	

}
