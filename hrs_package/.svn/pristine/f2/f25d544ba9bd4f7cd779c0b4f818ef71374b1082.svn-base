package com.hausontech.hrs.bean;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class AuditBean implements java.io.Serializable {
	
	private String userName;
    private Date creationDate;
    private String createdBy;
    private Date lastUpdateDate;
    private String lastUpdatedBy;
    
    //only used for pagination
    private int rowStartIndex;
    private int rowCount; 
    
    public int getRowStartIndex() {
		return rowStartIndex;
	}

	public void setRowStartIndex(int rowStartIndex) {
		this.rowStartIndex = rowStartIndex;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
    }
    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy == null ? null : lastUpdatedBy.trim();
    }

    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean isEmpty() {
    	if (!StringUtils.isBlank(createdBy) ||
    			creationDate != null ||
    			lastUpdateDate != null ||
    			!StringUtils.isBlank(lastUpdatedBy)) {
    		return false;
    	}
    	return true;
    }
    
	public String getDecodedRequestParam(HttpServletRequest request, String paraName) throws UnsupportedEncodingException {
		String paraValue = null;
		if (request != null && !StringUtils.isBlank(request.getParameter(paraName))) {
			paraValue = java.net.URLDecoder.decode(request.getParameter(paraName),"UTF-8");
		}
		return paraValue;	
	}
}
