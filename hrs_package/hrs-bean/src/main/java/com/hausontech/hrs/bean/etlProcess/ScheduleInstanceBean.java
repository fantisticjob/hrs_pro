package com.hausontech.hrs.bean.etlProcess;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.hausontech.hrs.bean.AuditBean;

public class ScheduleInstanceBean extends AuditBean {
	
	private int jobId;
	private String jobName;
	private String typeProcess;
	private String valueProcess;
	private int scheduleId;
	private String jobType;
	private String status;
	public String getJobType() {
		return jobType;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	public boolean isEmpty() {
    	if (jobId != 0 ||
    			!StringUtils.isBlank(jobName) || !StringUtils.isBlank(status)||
    			!super.isEmpty()) {
    		return false;
    	}
    	return true;
    }
	public int getJobId() {
		return jobId;
	}
	public void setJobId(int jobId) {
		this.jobId = jobId;
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
	public int getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(int scheduleId) {
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
		return "ScheduleInstanceBean [jobId=" + jobId + ", jobName=" + jobName + ", typeProcess=" + typeProcess
				+ ", valueProcess=" + valueProcess + ", scheduleId=" + scheduleId + ", jobType=" + jobType + ", status="
				+ status + "]";
	}
	
}
