package com.hausontech.hrs.bean.dimensionManager;

import org.apache.commons.lang.StringUtils;

import com.hausontech.hrs.bean.AuditBean2;

public class DimensionBean extends AuditBean2 {
	
    private long dimensionId;

    private long dimSeq;

    private String dimensionCode;

    private String dimensionName;

    private String dimSegment;

    private String finAccountFlag;

    

    public long getDimensionId() {
        return dimensionId;
    }

    public void setDimensionId(long dimensionId) {
        this.dimensionId = dimensionId;
    }

    public long getDimSeq() {
        return dimSeq;
    }

    public void setDimSeq(long dimSeq) {
        this.dimSeq = dimSeq;
    }

    public String getDimensionCode() {
        return dimensionCode;
    }

    public void setDimensionCode(String dimensionCode) {
        this.dimensionCode = dimensionCode == null ? null : dimensionCode.trim();
    }

    public String getDimensionName() {
        return dimensionName;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName == null ? null : dimensionName.trim();
    }

    public String getDimSegment() {
        return dimSegment;
    }

    public void setDimSegment(String dimSegment) {
        this.dimSegment = dimSegment == null ? null : dimSegment.trim();
    }

    public String getFinAccountFlag() {
        return finAccountFlag;
    }

    public void setFinAccountFlag(String finAccountFlag) {
        this.finAccountFlag = finAccountFlag == null ? null : finAccountFlag.trim();
    }
    
    public boolean isEmpty() {
    	if (dimensionId != 0 || dimSeq != 0 || 
    			!StringUtils.isBlank(dimensionCode) ||
    			!StringUtils.isBlank(dimensionName) || 
    			!StringUtils.isBlank(dimSegment) ||
    			!StringUtils.isBlank(finAccountFlag) || !super.isEmpty()) {
    		return false;
    	}
    	return true;
    }
    
}