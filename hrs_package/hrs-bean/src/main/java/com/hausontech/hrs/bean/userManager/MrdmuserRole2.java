package com.hausontech.hrs.bean.userManager;

import java.io.Serializable;

public class MrdmuserRole2 implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//Fields
	private String id;
	private String userId;
	private String roleId;
	
	@Override
	public String toString() {
		return "MrdmuserRole2 [id=" + id + ", userId=" + userId + ", roleId=" + roleId + "]";
	}

	public MrdmuserRole2(String id, String userId, String roleId) {
		super();
		this.id = id;
		this.userId = userId;
		this.roleId = roleId;
	}

	public MrdmuserRole2(String id) {
		super();
		this.id = id;
	}

	public MrdmuserRole2() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
}
