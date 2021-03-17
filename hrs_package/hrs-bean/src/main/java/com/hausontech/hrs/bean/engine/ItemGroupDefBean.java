package com.hausontech.hrs.bean.engine;

import java.util.Date;

public class ItemGroupDefBean extends AuditBean {
	//primary key of item group
	public long groupId;
	//item group code
	public String groupCode;
	//item group description
	public String description;
	//when the group take effective
	public Date effectiveStartDate;
	//when the group expired
	public Date effectiveEndDate;
	
	public long getGroupId() {
		return groupId;
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getEffectiveStartDate() {
		return effectiveStartDate;
	}
	public void setEffectiveStartDate(Date effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}
	public Date getEffectiveEndDate() {
		return effectiveEndDate;
	}
	public void setEffectiveEndDate(Date effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}
	
	
}
