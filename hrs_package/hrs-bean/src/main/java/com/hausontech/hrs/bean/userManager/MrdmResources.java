package com.hausontech.hrs.bean.userManager;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * MrdmResources entity. @author MyEclipse Persistence Tools
 */

public class MrdmResources implements java.io.Serializable {

	// Fields

	private String id;
	private MrdmResources mrdmResources;
	private String text;
	private BigDecimal seq;
	private String src;
	private String descript;
	private String onoff;
	private Set mrdmResourceses = new HashSet(0);
	private Set mrdmroleResourceses = new HashSet(0);

	// Constructors

	/** default constructor */
	public MrdmResources() {
	}

	/** minimal constructor */
	public MrdmResources(String id, BigDecimal seq) {
		this.id = id;
		this.seq = seq;
	}

	/** full constructor */
	public MrdmResources(String id, MrdmResources mrdmResources, String text,
			BigDecimal seq, String src, String descript, String onoff,
			Set mrdmResourceses, Set mrdmroleResourceses) {
		this.id = id;
		this.mrdmResources = mrdmResources;
		this.text = text;
		this.seq = seq;
		this.src = src;
		this.descript = descript;
		this.onoff = onoff;
		this.mrdmResourceses = mrdmResourceses;
		this.mrdmroleResourceses = mrdmroleResourceses;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MrdmResources getMrdmResources() {
		return this.mrdmResources;
	}

	public void setMrdmResources(MrdmResources mrdmResources) {
		this.mrdmResources = mrdmResources;
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

	public String getSrc() {
		return this.src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getDescript() {
		return this.descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public String getOnoff() {
		return this.onoff;
	}

	public void setOnoff(String onoff) {
		this.onoff = onoff;
	}

	public Set getMrdmResourceses() {
		return this.mrdmResourceses;
	}

	public void setMrdmResourceses(Set mrdmResourceses) {
		this.mrdmResourceses = mrdmResourceses;
	}

	public Set getMrdmroleResourceses() {
		return this.mrdmroleResourceses;
	}

	public void setMrdmroleResourceses(Set mrdmroleResourceses) {
		this.mrdmroleResourceses = mrdmroleResourceses;
	}

}