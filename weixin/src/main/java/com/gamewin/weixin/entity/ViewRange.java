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
@Table(name = "wx_user_viewrange")
public class ViewRange extends IdEntity {
  
	private Date updateDate;
	private Date createDate; 
	private Integer isdelete; 
	private User user;
	private ManageTask task; 
	
	public ViewRange() {
	}

	public ViewRange(Long id) {
		this.id = id;
	}
	// JPA 基于USER_ID列的多对一关系定义
	 
	public Integer getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(Integer isdelete) {
		this.isdelete = isdelete;
	}
  
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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


	 
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	@ManyToOne
	@JoinColumn(name = "task_id")
	public ManageTask getTask() {
		return task;
	}

	public void setTask(ManageTask task) {
		this.task = task;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
