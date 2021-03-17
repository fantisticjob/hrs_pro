package com.hausontech.hrs.bean.transactionProcess;

import com.hausontech.hrs.bean.AuditBean2;

public class TransactionProcessTypeBean extends AuditBean2{
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
