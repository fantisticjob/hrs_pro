package com.hausontech.hrs.bean.allocationManager.model;

import com.hausontech.hrs.bean.AuditBean2;

public class AllocSourceAccountRecord extends AuditBean2 {
	private long sourceAccId;
	private long sourceId;
	private long ledgerId;
	private String finElement;
	private String dimensionSegment;
	private String dimensionValue;
	private long filterHeaderId;
	private String description;
	public long getSourceAccId() {
		return sourceAccId;
	}
	public void setSourceAccId(long sourceAccId) {
		this.sourceAccId = sourceAccId;
	}
	public long getSourceId() {
		return sourceId;
	}
	public void setSourceId(long sourceId) {
		this.sourceId = sourceId;
	}
	public long getLedgerId() {
		return ledgerId;
	}
	public void setLedgerId(long ledgerId) {
		this.ledgerId = ledgerId;
	}
	public String getFinElement() {
		return finElement;
	}
	public void setFinElement(String finElement) {
		this.finElement = finElement;
	}
	public String getDimensionSegment() {
		return dimensionSegment;
	}
	public void setDimensionSegment(String dimensionSegment) {
		this.dimensionSegment = dimensionSegment;
	}
	public String getDimensionValue() {
		return dimensionValue;
	}
	public void setDimensionValue(String dimensionValue) {
		this.dimensionValue = dimensionValue;
	}
	public long getFilterHeaderId() {
		return filterHeaderId;
	}
	public void setFilterHeaderId(long filterHeaderId) {
		this.filterHeaderId = filterHeaderId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}


}
