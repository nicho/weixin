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
import org.hibernate.validator.constraints.NotBlank;

//JPA标识
@Entity
@Table(name = "wx_manage_task")
public class ManageTask extends IdEntity {

	private String title;
	private Date startDate;
	private Date endDate;
	private Date createDate;
	private String description; 
	private Integer taskCount; 
	private Integer isdelete; 
	private User user;
	private String viewrangeType;
	private String weixinGd;
	private String weixinLs;
	private String weixinApk;
	private String weixinOther;
	private String apkUrl;
	private String otherUrl;
	private String state;
	private Integer finishTaskCount; 
	private Integer finishTaskAdminCount; 
	
	public Integer getFinishTaskCount() {
		return finishTaskCount;
	}

	public void setFinishTaskCount(Integer finishTaskCount) {
		this.finishTaskCount = finishTaskCount;
	}

	public Integer getFinishTaskAdminCount() {
		return finishTaskAdminCount;
	}

	public void setFinishTaskAdminCount(Integer finishTaskAdminCount) {
		this.finishTaskAdminCount = finishTaskAdminCount;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getWeixinGd() {
		return weixinGd;
	}

	public void setWeixinGd(String weixinGd) {
		this.weixinGd = weixinGd;
	}

	public String getWeixinLs() {
		return weixinLs;
	}

	public void setWeixinLs(String weixinLs) {
		this.weixinLs = weixinLs;
	}

	public String getWeixinApk() {
		return weixinApk;
	}

	public void setWeixinApk(String weixinApk) {
		this.weixinApk = weixinApk;
	}

	public String getWeixinOther() {
		return weixinOther;
	}

	public void setWeixinOther(String weixinOther) {
		this.weixinOther = weixinOther;
	}

	public String getApkUrl() {
		return apkUrl;
	}

	public void setApkUrl(String apkUrl) {
		this.apkUrl = apkUrl;
	}

	public String getOtherUrl() {
		return otherUrl;
	}

	public void setOtherUrl(String otherUrl) {
		this.otherUrl = otherUrl;
	}

	public ManageTask() {
	}

	public ManageTask(Long id) {
		this.id = id;
	}
 
	public String getViewrangeType() {
		return viewrangeType;
	}

	public void setViewrangeType(String viewrangeType) {
		this.viewrangeType = viewrangeType;
	}

	public Integer getIsdelete() {
		return isdelete;
	}
	 

	public void setIsdelete(Integer isdelete) {
		this.isdelete = isdelete;
	}

	public Integer getTaskCount() {
		return taskCount;
	}

	public void setTaskCount(Integer taskCount) {
		this.taskCount = taskCount;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	// JSR303 BeanValidator的校验规则
	@NotBlank
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
