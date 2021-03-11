package com.hausontech.hrs.exceptions;

public class DuplicateJobRunningException extends Exception {
	public DuplicateJobRunningException() {
	}

	public DuplicateJobRunningException(String msg) {
		super(msg);
	}
}
