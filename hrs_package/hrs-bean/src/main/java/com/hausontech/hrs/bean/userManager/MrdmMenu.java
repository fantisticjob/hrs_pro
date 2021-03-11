package com.hausontech.hrs.bean.userManager;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * MrdmMenu entity. @author MyEclipse Persistence Tools
 */

public class MrdmMenu implements java.io.Serializable {

	// Fields

	private String id;
	private MrdmMenu mrdmMenu;
	private String text;
	private String iconcls;
	private String src;
	private BigDecimal seq;
	private Set mrdmMenus = new HashSet(0);

	// Constructors

	/** default constructor */
	public MrdmMenu() {
	}

	/** minimal constructor */
	public MrdmMenu(String id, String text, BigDecimal seq) {
		this.id = id;
		this.text = text;
		this.seq = seq;
	}

	/** full constructor */
	public MrdmMenu(String id, MrdmMenu mrdmMenu, String text, String iconcls,
			String src, BigDecimal seq, Set mrdmMenus) {
		this.id = id;
		this.mrdmMenu = mrdmMenu;
		this.text = text;
		this.iconcls = iconcls;
		this.src = src;
		this.seq = seq;
		this.mrdmMenus = mrdmMenus;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MrdmMenu getMrdmMenu() {
		return this.mrdmMenu;
	}

	public void setMrdmMenu(MrdmMenu mrdmMenu) {
		this.mrdmMenu = mrdmMenu;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIconcls() {
		return this.iconcls;
	}

	public void setIconcls(String iconcls) {
		this.iconcls = iconcls;
	}

	public String getSrc() {
		return this.src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public BigDecimal getSeq() {
		return this.seq;
	}

	public void setSeq(BigDecimal seq) {
		this.seq = seq;
	}

	public Set getMrdmMenus() {
		return this.mrdmMenus;
	}

	public void setMrdmMenus(Set mrdmMenus) {
		this.mrdmMenus = mrdmMenus;
	}

}