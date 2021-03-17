package com.hausontech.hrs.bean.dataProcess;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hausontech.hrs.bean.AuditBean2;
import com.hausontech.hrs.bean.IBaseBeanService;
import com.hausontech.hrs.bean.transactionProcess.TransactionProcessTypeBean;

public class RequestInstanceForTnxBean extends AuditBean2 implements IBaseBeanService{
    private String ledgerCode;
    private String periodCode;
    private String tnxTypeCode;
	private List<LedgerBean> ledgerList;
	private List<PeriodBean> periodList;
	private List<TransactionProcessTypeBean> tnxTypelist;

	public String getLedgerCode() {
		return ledgerCode;
	}

	public void setLedgerCode(String ledgerCode) {
		this.ledgerCode = ledgerCode;
	}

	public String getPeriodCode() {
		return periodCode;
	}

	public void setPeriodCode(String periodCode) {
		this.periodCode = periodCode;
	}

	public String getTnxTypeCode() {
		return tnxTypeCode;
	}

	public void setTnxTypeCode(String tnxTypeCode) {
		this.tnxTypeCode = tnxTypeCode;
	}

	public List<LedgerBean> getLedgerList() {
		return ledgerList;
	}

	public void setLedgerList(List<LedgerBean> ledgerList) {
		this.ledgerList = ledgerList;
	}

	public List<PeriodBean> getPeriodList() {
		return periodList;
	}

	public void setPeriodList(List<PeriodBean> periodList) {
		this.periodList = periodList;
	}

	public List<TransactionProcessTypeBean> getTnxTypelist() {
		return tnxTypelist;
	}

	public void setTnxTypelist(List<TransactionProcessTypeBean> tnxTypelist) {
		this.tnxTypelist = tnxTypelist;
	}

	@Override
	public void constructBeanWithRequest(HttpServletRequest request, boolean isCreated) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int inputValidate(boolean isCreated) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Map constructReturnMap(boolean isCreated) {
		// TODO Auto-generated method stub
		return null;
	}

}
