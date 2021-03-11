package com.hausontech.hrs.bean.transactionProcess;

import com.hausontech.hrs.bean.AuditBean;

public class TransactionProcessTypeBean extends AuditBean{
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private String code;
	private String name;

}
