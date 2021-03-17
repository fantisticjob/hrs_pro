package com.hausontech.hrs.bean.transactionProcess;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hausontech.hrs.bean.AuditBean2;
import com.hausontech.hrs.bean.JsonDateSerializer2;

public class TransactionProcessHeader extends AuditBean2 implements java.io.Serializable {
	
	private long txnHeaderId;	
	private String typeCode;
	private int ledgerId;
	private String companySegValue;
	private String currencyCode;
	private String periodName;
	private String description;
	private String auditFlag;
	private String transferFlag;
	
	private Date transactionDate;

	public TransactionProcessHeader(){}	
	
	public long getTxnHeaderId() {
		return txnHeaderId;
	}
	public void setTxnHeaderId(long txnHeaderId) {
		this.txnHeaderId = txnHeaderId;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public int getLedgerId() {
		return ledgerId;
	}
	public void setLedgerId(int ledgerId) {
		this.ledgerId = ledgerId;
	}
	public String getCompanySegValue() {
		return companySegValue;
	}
	public void setCompanySegValue(String companySegValue) {
		this.companySegValue = companySegValue;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getPeriodName() {
		return periodName;
	}
	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}
	
	@JsonSerialize(using = JsonDateSerializer2.class)
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAuditFlag() {
		return auditFlag;
	}
	public void setAuditFlag(String auditFlag) {
		this.auditFlag = auditFlag;
	}
	public String getTransferFlag() {
		return transferFlag;
	}
	public void setTransferFlag(String transferFlag) {
		this.transferFlag = transferFlag;
	}
}
