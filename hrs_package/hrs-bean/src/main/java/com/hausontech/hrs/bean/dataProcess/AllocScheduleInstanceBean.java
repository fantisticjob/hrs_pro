package com.hausontech.hrs.bean.dataProcess;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.hausontech.hrs.bean.AuditBean2;

public class AllocScheduleInstanceBean extends AuditBean2 {
	
	private long instanceId;
	private String jobName;
	private String typeProcess;
	private String valueProcess;
	private String concurrent;
	private long scheduleId;
	private String jobType;
	private String ruleType;
	private String description;
	private String status;
	
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRuleType() {
		return ruleType;
	}
	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}
	public String getJobType() {
		return jobType;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	public boolean isEmpty() {
    	if (instanceId != 0 ||
    			!StringUtils.isBlank(jobName) || !StringUtils.isBlank(status)||!StringUtils.isBlank(ruleType)||
    			!super.isEmpty()) {
    		return false;
    	}
    	return true;
    }
	public long getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(long instanceId) {
		this.instanceId = instanceId;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getTypeProcess() {
		return typeProcess;
	}
	public void setTypeProcess(String typeProcess) {
		this.typeProcess = typeProcess;
	}
	public String getValueProcess() {
		return valueProcess;
	}
	public void setValueProcess(String valueProcess) {
		this.valueProcess = valueProcess;
	}
	public String getConcurrent() {
		return concurrent;
	}
	public void setConcurrent(String concurrent) {
		this.concurrent = concurrent;
	}
	
	
	public long getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(long scheduleId) {
		this.scheduleId = scheduleId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	@Override
	public String toString() {
		return "AllocScheduleInstanceBean [instanceId=" + instanceId + ", jobName=" + jobName + ", typeProcess="
				+ typeProcess + ", valueProcess=" + valueProcess + ", concurrent=" + concurrent + ", scheduleId="
				+ scheduleId + ", jobType=" + jobType + ", ruleType=" + ruleType + ", description=" + description
				+ ", status=" + status + "]";
	}
	public void constructBeanWithRequest(HttpServletRequest request, boolean b)  throws Exception{
		// TODO Auto-generated method stub
		if (request != null) {
			//set job instance id
			String instanceId = this.getDecodedRequestParam(request, "INSTANCE_ID");
			if (!StringUtils.isBlank(instanceId)) {
				this.setInstanceId(Long.parseLong(instanceId));
			}
			//set ruleId 
			String scheduleId = this.getDecodedRequestParam(request, "SCHEDULE_ID");
			if (!StringUtils.isBlank(scheduleId)) {
				this.setScheduleId(Integer.parseInt(scheduleId));
			}		
			//set description
			this.setTypeProcess(this.getDecodedRequestParam(request, "TYPE_PROCESS"));
			//set fin element
			this.setValueProcess(this.getDecodedRequestParam(request, "VALUE_PROCESS"));
			//set period 
			this.setConcurrent(this.getDecodedRequestParam(request, "CONCURRENT"));


		}
	}
	public int inputValidate(boolean b) {
		// TODO Auto-generated method stub
		int errorCode = 0;
		/*if (!isCreated) {
			if (this.getInstanceId() == 0) {
				errorCode = -1;
			}
		}
		if (this.getRuleId() == 0 || StringUtils.isBlank(this.getRuleType()) ||
				StringUtils.isBlank(this.getPeriod())  ) {
			errorCode = -1;
		}*/
		return errorCode;
	}
	public Map constructReturnMap(boolean b) {
		// TODO Auto-generated method stub
		Map dataMap = new HashMap();
		dataMap.put("INSTANCE_ID", this.getInstanceId());
		dataMap.put("SCHEDULE_ID", this.getScheduleId());
		dataMap.put("TYPE_PROCESS", this.getTypeProcess());
		dataMap.put("VALUE_PROCESS", this.getValueProcess());
		dataMap.put("CONCURRENT", this.getConcurrent());
		dataMap.put("DESCRIPTION", this.getDescription());
		return dataMap;
	}
}
