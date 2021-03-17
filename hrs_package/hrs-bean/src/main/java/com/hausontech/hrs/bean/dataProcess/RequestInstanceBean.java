package com.hausontech.hrs.bean.dataProcess;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.hausontech.hrs.bean.AuditBean2;
import com.hausontech.hrs.bean.IBaseBeanService;

public class RequestInstanceBean extends AuditBean2 implements IBaseBeanService {
	private long instanceId;
	private int ledgerId;
	private String finElementType;
	private String periodFrom;
	private String periodTo;
	private Date startDate;
	private Date endDate;
	private long elapsedTime;
	private String status;
	private String message;
	private String requestUser;
	private List<LedgerBean> ledgerSelectionList;
	private List<NameValuePair> finEleSelectionList;
	private List<PeriodBean> periodList;
	
	{	//initialize financial element
		finEleSelectionList = new ArrayList<NameValuePair>();
		finEleSelectionList.add(new NameValuePair("财务", "F"));
		finEleSelectionList.add(new NameValuePair("非财务", "NF"));
		finEleSelectionList.add(new NameValuePair("全部", "A"));
	}
	
	public String getRequestUser() {
		return requestUser;
	}
	public void setRequestUser(String requestUser) {
		this.requestUser = requestUser;
	}
	public List<NameValuePair> getFinEleSelectionList() {
		return finEleSelectionList;
	}
	public void setFinEleSelectionList(List<NameValuePair> finEleSelectionList) {
		this.finEleSelectionList = finEleSelectionList;
	}
	public List<LedgerBean> getLedgerSelectionList() {
		return ledgerSelectionList;
	}
	public void setLedgerSelectionList(List<LedgerBean> ledgerSelectionList) {
		this.ledgerSelectionList = ledgerSelectionList;
	}
	public List<PeriodBean> getPeriodList() {
		return periodList;
	}
	public void setPeriodList(List<PeriodBean> periodList) {
		this.periodList = periodList;
	}
	public long getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(long instanceId) {
		this.instanceId = instanceId;
	}
	public int getLedgerId() {
		return ledgerId;
	}
	public void setLedgerId(int ledgerId) {
		this.ledgerId = ledgerId;
	}
	public String getFinElementType() {
		return finElementType;
	}
	public void setFinElementType(String finElementType) {
		this.finElementType = finElementType;
	}
	public String getPeriodFrom() {
		return periodFrom;
	}
	public void setPeriodFrom(String periodFrom) {
		this.periodFrom = periodFrom;
	}
	public String getPeriodTo() {
		return periodTo;
	}
	public void setPeriodTo(String periodTo) {
		this.periodTo = periodTo;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public long getElapsedTime() {
		return elapsedTime;
	}
	public void setElapsedTime(long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
    public boolean isEmpty() {
    	if (ledgerId != 0 || 
    			!StringUtils.isBlank(finElementType) ||
    			!StringUtils.isBlank(periodFrom) || 
    			!StringUtils.isBlank(periodTo) || 
    			!StringUtils.isBlank(status) ||
    			!super.isEmpty()) {
    		return false;
    	}
    	return true;
    }
	@Override
	public void constructBeanWithRequest(HttpServletRequest request, boolean isCreated) throws Exception {
		if (request != null) {
			//set job instance id
			String instanceId = this.getDecodedRequestParam(request, "INSTANCE_ID");
			if (!StringUtils.isBlank(instanceId)) {
				this.setInstanceId(Long.parseLong(instanceId));
			}
			//set ledger id
			String legerId = this.getDecodedRequestParam(request, "LEDGER_ID");
			if (!StringUtils.isBlank(legerId)) {
				this.setLedgerId(Integer.parseInt(legerId));
			}		
			//set fin element
			this.setFinElementType(this.getDecodedRequestParam(request, "FIN_ELEMENT_TYPE"));
			//set period from
			this.setPeriodFrom(this.getDecodedRequestParam(request, "PERIOD_FROM"));
			//set period to
			this.setPeriodTo(this.getDecodedRequestParam(request, "PERIOD_TO"));
		}
	}
	@Override
	public int inputValidate(boolean isCreated) throws Exception {
		int errorCode = 0;
		if (!isCreated) {
			if (this.getInstanceId() == 0) {
				errorCode = -1;
			}
		}
		if (this.getLedgerId() == 0 || StringUtils.isBlank(this.getFinElementType()) ||
				StringUtils.isBlank(this.getPeriodFrom()) ||
				StringUtils.isBlank(this.getPeriodTo()) ) {
			errorCode = -1;
		}
		return errorCode;
	}
	@Override
	public Map constructReturnMap(boolean isCreated) {
		Map dataMap = new HashMap();
		dataMap.put("INSTANCE_ID", this.getInstanceId());
		dataMap.put("LEDGER_ID", this.getLedgerId());
		dataMap.put("FIN_ELEMENT_TYPE", this.getFinElementType());
		dataMap.put("PERIOD_FROM", this.getPeriodFrom());
		dataMap.put("PERIOD_TO", this.getPeriodTo());
		dataMap.put("START_TIME", this.getStartDate());
		dataMap.put("END_TIME", this.getEndDate());
		dataMap.put("ELAPSED_TIME", this.getElapsedTime());
		dataMap.put("STATUS", com.hausontech.hrs.bean.JobStatus.getDisplayStatus(this.getStatus()));
		dataMap.put("MESSAGE", this.getMessage());
		return dataMap;
	}
}
    
