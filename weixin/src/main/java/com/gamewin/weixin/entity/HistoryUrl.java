/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.gamewin.weixin.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name = "wx_history_url")
public class HistoryUrl extends IdEntity {
	private Long taskId;
	private String userIp;
	private Long qrcodeId;
	private Date createDate;

 

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
 

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getQrcodeId() {
		return qrcodeId;
	}

	public void setQrcodeId(Long qrcodeId) {
		this.qrcodeId = qrcodeId;
	}

	public HistoryUrl() {
	}

	public HistoryUrl(Long id) {
		this.id = id;
	}

	  

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}