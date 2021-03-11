package com.hausontech.hrs.bean.etlProcess;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.hausontech.hrs.bean.AuditBean;

public class JobInstanceBean extends AuditBean {
	
	private int jobId;
	private int scheduleId;
	private String jobName;
	private String filePath;
	private String parameList;
	private String description;
	private String concurrent;
	private int lastJobInstanceId;
	private Date startTime;
	private Date endTime;
	private int elapsedTime;
	private String status;
	private String message;
	private Map<String, String> parameterMap = new HashMap<String, String>();
	private String jobType;
	
	
	
	public String getConcurrent() {
		return concurrent;
	}
	public void setConcurrent(String concurrent) {
		this.concurrent = concurrent;
	}
	public int getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
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
	
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getParameList() {
		return parameList;
	}
	public void setParameList(String parameList) {
		this.parameList = parameList;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getLastJobInstanceId() {
		return lastJobInstanceId;
	}
	public void setLastJobInstanceId(int lastJobInstanceId) {
		this.lastJobInstanceId = lastJobInstanceId;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public int getElapsedTime() {
		return elapsedTime;
	}
	public void setElapsedTime(int elapsedTime) {
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

    public Map<String, String> getParameterMap() {
		return parameterMap;
	}
	public void setParameterMap(Map<String, String> parameterMap) {
		this.parameterMap = parameterMap;
	}
	
	public String getJobType() {
		return jobType;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	public void setEtlParameter(String name, String value) {
		if (parameterMap.containsKey(name)) {
			parameterMap.remove(name);
		}
		parameterMap.put(name, value);
	}
	
	public String getEtlParamByName(String name) {
		return  parameterMap.get(name);
	}
	
	public void removeEtlParamByName(String name) {
		parameterMap.remove(name);
	}
	
	public Set<String> getEltParameterNameSet() {
		return this.parameterMap.keySet();
	}
	
	public boolean isEmpty() {
    	if (jobId != 0 ||
    			!StringUtils.isBlank(jobName) || !StringUtils.isBlank(status) ||
    			!super.isEmpty()) {
    		return false;
    	}
    	return true;
    }
	@Override
	public String toString() {
		return "JobInstanceBean [jobId=" + jobId + ", scheduleId=" + scheduleId + ", jobName=" + jobName + ", filePath="
				+ filePath + ", parameList=" + parameList + ", description=" + description + ", concurrent="
				+ concurrent + ", lastJobInstanceId=" + lastJobInstanceId + ", startTime=" + startTime + ", endTime="
				+ endTime + ", elapsedTime=" + elapsedTime + ", status=" + status + ", message=" + message
				+ ", parameterMap=" + parameterMap + ", jobType=" + jobType + "]";
	}
	
	
}
