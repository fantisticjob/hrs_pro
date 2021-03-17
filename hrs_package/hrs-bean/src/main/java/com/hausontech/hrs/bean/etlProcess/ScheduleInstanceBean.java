package com.hausontech.hrs.bean.etlProcess;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.hausontech.hrs.bean.AuditBean2;

public class ScheduleInstanceBean extends AuditBean2 {
	
	private long jobId;
	private String jobName;
	private String typeProcess;
	private String valueProcess;
	private String concurrent;
	private long scheduleId;
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
	
	public long getJobId() {
		return jobId;
	}
	public void setJobId(long jobId) {
		this.jobId = jobId;
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
		return "ScheduleInstanceBean [jobId=" + jobId + ", jobName=" + jobName + ", typeProcess=" + typeProcess
				+ ", valueProcess=" + valueProcess + ", concurrent=" + concurrent + ", scheduleId=" + scheduleId
				+ ", jobType=" + jobType + ", status=" + status + "]";
	}
	
	
}
