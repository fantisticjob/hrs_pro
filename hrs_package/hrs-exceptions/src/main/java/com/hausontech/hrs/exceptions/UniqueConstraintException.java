package com.hausontech.hrs.exceptions;

public class UniqueConstraintException extends Exception {
	public UniqueConstraintException() {
	}

	public UniqueConstraintException(String msg) {
		super(msg);
	}
}
