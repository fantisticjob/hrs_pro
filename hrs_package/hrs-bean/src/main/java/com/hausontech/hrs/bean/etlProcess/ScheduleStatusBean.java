package com.hausontech.hrs.bean.etlProcess;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.hausontech.hrs.bean.AuditBean2;

public class ScheduleStatusBean{
	
	private int statusId;
	private String statusType;
	private String statusDescription;
	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	public String getStatusType() {
		return statusType;
	}
	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}
	public String getStatusDescription() {
		return statusDescription;
	}
	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}
	@Override
	public String toString() {
		return "ScheduleStatusBean [statusId=" + statusId + ", statusType=" + statusType + ", statusDescription="
				+ statusDescription + "]";
	}
	
	
	
}
