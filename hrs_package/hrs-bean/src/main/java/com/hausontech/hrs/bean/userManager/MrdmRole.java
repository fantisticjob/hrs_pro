package com.hausontech.hrs.bean.userManager;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * MrdmRole entity. @author MyEclipse Persistence Tools
 */

public class MrdmRole implements java.io.Serializable {

	// Fields

	private String id;
	private MrdmRole mrdmRole;
	private String text;
	private BigDecimal seq;
	private String descript;
	private Set mrdmRoles = new HashSet(0);
	private Set mrdmroleResourceses = new HashSet(0);
	private Set mrdmuserRoles = new HashSet(0);

	// Constructors

	/** default constructor */
	public MrdmRole() {
	}

	/** minimal constructor */
	public MrdmRole(String id, BigDecimal seq) {
		this.id = id;
		this.seq = seq;
	}

	/** full constructor */
	public MrdmRole(String id, MrdmRole mrdmRole, String text, BigDecimal seq,
			String descript, Set mrdmRoles, Set mrdmroleResourceses,
			Set mrdmuserRoles) {
		this.id = id;
		this.mrdmRole = mrdmRole;
		this.text = text;
		this.seq = seq;
		this.descript = descript;
		this.mrdmRoles = mrdmRoles;
		this.mrdmroleResourceses = mrdmroleResourceses;
		this.mrdmuserRoles = mrdmuserRoles;
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

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public BigDecimal getSeq() {
		return this.seq;
	}

	public void setSeq(BigDecimal seq) {
		this.seq = seq;
	}

	public String getDescript() {
		return this.descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public Set getMrdmRoles() {
		return this.mrdmRoles;
	}

	public void setMrdmRoles(Set mrdmRoles) {
		this.mrdmRoles = mrdmRoles;
	}

	public Set getMrdmroleResourceses() {
		return this.mrdmroleResourceses;
	}

	public void setMrdmroleResourceses(Set mrdmroleResourceses) {
		this.mrdmroleResourceses = mrdmroleResourceses;
	}

	public Set getMrdmuserRoles() {
		return this.mrdmuserRoles;
	}

	public void setMrdmuserRoles(Set mrdmuserRoles) {
		this.mrdmuserRoles = mrdmuserRoles;
	}

}