package com.hausontech.hrs.exceptions;



public class BsException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String msg;
	private static final String[] EXCEPTION_MSG = new String[]{" aaa",
																"数据库主键冲突！","for key"};
	public BsException(String msg){
		super(msg);
		this.msg = msg;
	}
	
	public static BsException getException(String msg){
		for(String exceptionMsg:EXCEPTION_MSG){
			
			if(msg!=null&&msg.contains(exceptionMsg)){
				return new BsException(exceptionMsg);
			}
		}
		
		return  null;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
