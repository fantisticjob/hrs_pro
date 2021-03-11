package com.hausontech.hrs.bean.userManager;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.hausontech.hrs.bean.AuditBean;

/**
 * MrdmUser entity. @author MyEclipse Persistence Tools
 */

public class MrdmUser extends AuditBean implements java.io.Serializable {
	// Fields
	private String userName;
	private String fullName;
	private String password;
	private Set<MrdmuserRole> mrdmuserRoles = new HashSet<MrdmuserRole>();

	// Constructors

	/** default constructor */
	public MrdmUser() {
	}

	public MrdmUser(String userName, String fullName, String password, Set<MrdmuserRole> mrdmuserRoles) {
		super();
		this.userName = userName;
		this.fullName = fullName;
		this.password = password;
		this.mrdmuserRoles = mrdmuserRoles;
	}

	/** minimal constructor */
	public MrdmUser(String name, String password) {
		this.userName = name;
		this.password = password;
	}
	
	public MrdmUser(String userName, String password, String fullName) {
		this.userName = userName;
		this.password = password;
		this.fullName = fullName;
	}

	/** full constructor */
	public MrdmUser(String name, String password, String fullName,
			Date createdDatetime, Date modifyDatetime,
			Set mrdmuserRoles) {
		this.userName = name;
		this.password = password;
		this.fullName = fullName;
		this.setCreationDate(createdDatetime);
		this.setLastUpdateDate(modifyDatetime);
		this.mrdmuserRoles = mrdmuserRoles;
	}

	// Property accessors
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Set getMrdmuserRoles() {
		return this.mrdmuserRoles;
	}
	public void setMrdmuserRoles(Set mrdmuserRoles) {
		this.mrdmuserRoles = mrdmuserRoles;
	}
}