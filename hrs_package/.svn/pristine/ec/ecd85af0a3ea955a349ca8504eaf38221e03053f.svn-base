package com.hausontech.hrs.api;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hausontech.hrs.bean.AuditBean2;

public interface IBaseBeanService {
	/**
	 * Used for creating entity bean with request
	 * @param request
	 * @param isCreated
	 * @return
	 * @throws Exception
	 */
	public void constructBeanWithRequest(HttpServletRequest request, boolean isCreated) throws Exception;
	
	/**
	 * Used for checking whether user input is valid
	 * @param request
	 * @param isCreated
	 * @return
	 * @throws Exception
	 */
	public int inputValidate(boolean isCreated) throws Exception;
	
	/**
	 * Used for constructing return map after creatioin/update operation
	 * @param request
	 * @param isCreated
	 * @return
	 * @throws Exception
	 */
	public Map constructReturnMap(boolean isCreated);
}
