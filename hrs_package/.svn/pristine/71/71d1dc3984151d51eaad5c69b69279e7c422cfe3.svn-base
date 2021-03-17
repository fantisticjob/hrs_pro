package com.hausontech.hrs.bean.etlProcess;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.hausontech.hrs.bean.AuditBean2;

public class JobInstanceBean extends AuditBean2 {
	
	private long jobId;
	private long scheduleId;
	private String jobName;
	private int orderId;
	private String filePath;
	private String paramList;
	private String description;
	private long lastJobInstanceId;
	private Date startTime;
	private Date endTime;
	private int elapsedTime;
	private String status;
	private String message;
	private Map<String, String> parameterMap = new HashMap<String, String>();
	private String jobType;
	
	
	
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	
	public long getLastJobInstanceId() {
		return lastJobInstanceId;
	}
	public void setLastJobInstanceId(long lastJobInstanceId) {
		this.lastJobInstanceId = lastJobInstanceId;
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
	public String getParamList() {
		return paramList;
	}
	public void setParamList(String paramList) {
		this.paramList = paramList;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
		return "JobInstanceBean [jobId=" + jobId + ", scheduleId=" + scheduleId + ", jobName=" + jobName + ", orderId="
				+ orderId + ", filePath=" + filePath + ", paramList=" + paramList + ", description=" + description
				+ ", lastJobInstanceId=" + lastJobInstanceId + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", elapsedTime=" + elapsedTime + ", status=" + status + ", message=" + message + ", parameterMap="
				+ parameterMap + ", jobType=" + jobType + "]";
	}
	
	
	
	
	
	
}
