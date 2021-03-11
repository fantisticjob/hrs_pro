package com.hausontech.hrs.bean.userManager;

/**
 * MrdmroleResources entity. @author MyEclipse Persistence Tools
 */

public class MrdmroleResources implements java.io.Serializable {

	// Fields

	private String id;
	private MrdmRole mrdmRole;
	private MrdmResources mrdmResources;

	// Constructors

	/** default constructor */
	public MrdmroleResources() {
	}

	/** full constructor */
	public MrdmroleResources(String id, MrdmRole mrdmRole,
			MrdmResources mrdmResources) {
		this.id = id;
		this.mrdmRole = mrdmRole;
		this.mrdmResources = mrdmResources;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MrdmRole getMrdmRole() {
		return this.mrdmRole;
	}

	public void setMrdmRole(MrdmRole mrdmRole) {
		this.mrdmRole = mrdmRole;
	}

	public MrdmResources getMrdmResources() {
		return this.mrdmResources;
	}

	public void setMrdmResources(MrdmResources mrdmResources) {
		this.mrdmResources = mrdmResources;
	}

}