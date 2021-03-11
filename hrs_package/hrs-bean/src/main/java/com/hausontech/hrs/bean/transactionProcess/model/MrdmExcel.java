package com.hausontech.hrs.bean.transactionProcess.model;

import java.sql.Timestamp;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hausontech.hrs.bean.JsonDateSerializer;

/**
 * MrdmExcel entity. @author MyEclipse Persistence Tools
 */

public class MrdmExcel implements java.io.Serializable {

	// Fields

	private long id;
	private String fileName;
	private String dataNum;
	private Date createdatetime;
	private String dataStatus;
	private String createBy;

	// Constructors

	/** default constructor */
	public MrdmExcel() {
	}

	/** minimal constructor */
	public MrdmExcel(Long id, String fileName, String dataNum,
			String dataStatus) {
		this.id = id;
		this.fileName = fileName;
		this.dataNum = dataNum;
		this.dataStatus = dataStatus;
	}

	/** full constructor */
	public MrdmExcel(long id, String fileName, String dataNum,
			Timestamp createdatetime, String dataStatus) {
		this.id = id;
		this.fileName = fileName;
		this.dataNum = dataNum;
		this.createdatetime = createdatetime;
		this.dataStatus = dataStatus;
	}

	// Property accessors

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDataNum() {
		return this.dataNum;
	}

	public void setDataNum(String dataNum) {
		this.dataNum = dataNum;
	}
	
	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getCreatedatetime() {
		return createdatetime;
	}

	public void setCreatedatetime(Date createdatetime) {
		this.createdatetime = createdatetime;
	}

	public String getDataStatus() {
		return this.dataStatus;
	}

	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

}