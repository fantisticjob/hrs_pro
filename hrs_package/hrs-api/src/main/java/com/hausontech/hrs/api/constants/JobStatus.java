package com.hausontech.hrs.api.constants;

import org.apache.commons.lang.StringUtils;

public class JobStatus {
	public static final String SCHEDULED = "scheduled";
	public static final String PROCESSING = "processing";
	public static final String SUCCESS = "success";
	public static final String FAILED = "failed";
	public static final String ROLLBACKING="rollbacking";
	public static final String ROLLBACKSUCCESS = "rollbacksuccess";
	public static final String ROLLBACKFAILED = "rollbackfailed";

	public static String getDisplayStatus(String status) {
		String dispStatus = "";
		if (!StringUtils.isBlank(status)) {
			if (SCHEDULED.equals(status)) {
				dispStatus = "未执行";
			} else if (PROCESSING.equals(status)) {
				dispStatus = "任务处理中";
			} else if (SUCCESS.equals(status)) {
				dispStatus = "处理成功";
			} else if (FAILED.equals(status)) {
				dispStatus = "处理失败";
			} else if (ROLLBACKING.equals(status)) {
				dispStatus = "回滚中";
			} else if (ROLLBACKSUCCESS.equals(status)) {
				dispStatus = "回滚成功";
			} else if (ROLLBACKFAILED.equals(status)) {
				dispStatus = "回滚失败";
			}
		}
		return dispStatus;
	}
}
