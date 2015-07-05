/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.gamewin.weixin.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

//JPA标识
@Entity
@Table(name = "wx_Apply_Admin")
public class ApplyThreeAdmin extends IdEntity {

	private String userName; 
	private String ssnumber; 
	private String address; 
	private Date createDate;
	private String description;  
	private String status;  
	private Integer isdelete; 
	private User user; 
	private User upuser; 
	private String approvalOpinion;  
	public ApplyThreeAdmin() {
	}

	public ApplyThreeAdmin(Long id) {
		this.id = id;
	} 
	
	
	
	public String getApprovalOpinion() {
		return approvalOpinion;
	}

	public void setApprovalOpinion(String approvalOpinion) {
		this.approvalOpinion = approvalOpinion;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

 

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSsnumber() {
		return ssnumber;
	}

	public void setSsnumber(String ssnumber) {
		this.ssnumber = ssnumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(Integer isdelete) {
		this.isdelete = isdelete;
	}
	// JPA 基于USER_ID列的多对一关系定义
	@ManyToOne
	@JoinColumn(name = "upuser_id")
	public User getUpuser() {
		return upuser;
	}

	public void setUpuser(User upuser) {
		this.upuser = upuser;
	}

	// JPA 基于USER_ID列的多对一关系定义
	@ManyToOne
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	 
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
