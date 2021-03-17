package com.hausontech.hrs.bean.dataProcess;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.hausontech.hrs.bean.AuditBean2;
import com.hausontech.hrs.bean.IBaseBeanService;
import com.hausontech.hrs.bean.JobStatus;
import com.hausontech.hrs.bean.allocationManager.AllocRuleBean;

public class AllocRequestInstanceBean extends AuditBean2 implements IBaseBeanService {
	private long instanceId;
	private String ruleType;
	private long ruleId;
	private String period;
	private Date startDate;
	private Date endDate;
	private long elapsedTime;
	private String status;
	private String message;
	private String requestUser;
	private List<PeriodBean> periodList;
	private List<AllocRuleBean> ruleList;
	private List<AllocRuleBean> typeList;
	public long getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(long instanceId) {
		this.instanceId = instanceId;
	}

	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

	public long getRuleId() {
		return ruleId;
	}

	public void setRuleId(long ruleId) {
		this.ruleId = ruleId;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
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

	public String getRequestUser() {
		return requestUser;
	}

	public void setRequestUser(String requestUser) {
		this.requestUser = requestUser;
	}

	public List<PeriodBean> getPeriodList() {
		return periodList;
	}

	public void setPeriodList(List<PeriodBean> periodList) {
		this.periodList = periodList;
	}

	public List<AllocRuleBean> getRuleList() {
		return ruleList;
	}

	public void setRuleList(List<AllocRuleBean> ruleList) {
		this.ruleList = ruleList;
	}

	public List<AllocRuleBean> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<AllocRuleBean> typeList) {
		this.typeList = typeList;
	}
    public boolean isEmpty() {
    	if (ruleId != 0 || 
    			!StringUtils.isBlank(ruleType) ||
    			!StringUtils.isBlank(period) || 
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
			//set ruleId 
			String ruleId = this.getDecodedRequestParam(request, "RULE_ID");
			if (!StringUtils.isBlank(ruleId)) {
				this.setRuleId(Integer.parseInt(ruleId));
			}		
			//set fin element
			this.setRuleType(this.getDecodedRequestParam(request, "RULE_TYPE"));
			//set period 
			this.setPeriod(this.getDecodedRequestParam(request, "PERIOD"));


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
		if (this.getRuleId() == 0 || StringUtils.isBlank(this.getRuleType()) ||
				StringUtils.isBlank(this.getPeriod())  ) {
			errorCode = -1;
		}
		return errorCode;
	}

	@Override
	public Map constructReturnMap(boolean isCreated) {
		Map dataMap = new HashMap();
		dataMap.put("INSTANCE_ID", this.getInstanceId());
		dataMap.put("RULE_ID", this.getRuleId());
		dataMap.put("RULE_TYPE", this.getRuleType());
		dataMap.put("PERIOD", this.getPeriod());
		dataMap.put("START_TIME", this.getStartDate());
		dataMap.put("END_TIME", this.getEndDate());
		dataMap.put("ELAPSED_TIME", this.getElapsedTime());
		dataMap.put("STATUS", JobStatus.getDisplayStatus(this.getStatus()));
		dataMap.put("MESSAGE", this.getMessage());
		return dataMap;
	}

}
