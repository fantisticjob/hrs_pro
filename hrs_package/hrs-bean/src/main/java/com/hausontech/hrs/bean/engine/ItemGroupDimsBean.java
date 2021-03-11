package com.hausontech.hrs.bean.engine;

public class ItemGroupDimsBean extends AuditBean {
	private long groupDimId;  
	private long groupId;
	private int seqNumber;
	private String dimCode;
	private String dimName;
	
	public long getGroupDimId() {
		return groupDimId;
	}
	public void setGroupDimId(long groupDimId) {
		this.groupDimId = groupDimId;
	}
	public long getGroupId() {
		return groupId;
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
	public int getSeqNumber() {
		return seqNumber;
	}
	public void setSeqNumber(int seqNumber) {
		this.seqNumber = seqNumber;
	}
	public String getDimCode() {
		return dimCode;
	}
	public void setDimCode(String dimCode) {
		this.dimCode = dimCode;
	}
	public String getDimName() {
		return dimName;
	}
	public void setDimName(String dimName) {
		this.dimName = dimName;
	}
	
	
}
