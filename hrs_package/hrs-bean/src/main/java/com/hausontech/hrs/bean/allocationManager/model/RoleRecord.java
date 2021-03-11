package com.hausontech.hrs.bean.allocationManager.model;

import com.hausontech.hrs.bean.AuditBean;

public class RoleRecord extends AuditBean{
 private String  id;
 private  String pid;
 private  String text;
 private int seq;
 private String description;
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getPid() {
	return pid;
}
public void setPid(String pid) {
	this.pid = pid;
}
public String getText() {
	return text;
}
public void setText(String text) {
	this.text = text;
}
public int getSeq() {
	return seq;
}
public void setSeq(int seq) {
	this.seq = seq;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}

}
