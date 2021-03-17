package com.hausontech.hrs.bean.reportSetting;

import org.apache.commons.lang.StringUtils;

import com.hausontech.hrs.bean.AuditBean2;

public class ItemRowCalculationBean extends AuditBean2{
	private long rowCalId;
	private long rowId;
	private long rowCalNum;
	private String sign;
	private String calItemCode;
	public long getRowCalId() {
		return rowCalId;
	}
	public void setRowCalId(long rowCalId) {
		this.rowCalId = rowCalId;
	}
	public long getRowId() {
		return rowId;
	}
	public void setRowId(long rowId) {
		this.rowId = rowId;
	}
	public long getRowCalNum() {
		return rowCalNum;
	}
	public void setRowCalNum(long rowCalNum) {
		this.rowCalNum = rowCalNum;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getCalItemCode() {
		return calItemCode;
	}
	public void setCalItemCode(String calItemCode) {
		this.calItemCode = calItemCode;
	}
	
	public boolean isEmpty() {
		if (!super.isEmpty() || rowId != 0 || rowCalId != 0 ||

				rowCalNum != 0 || !StringUtils.isBlank(sign) 
				|| !StringUtils.isBlank(calItemCode)) {
			return false;
		}
		return true;
	}
	
	
	
	
	

}
