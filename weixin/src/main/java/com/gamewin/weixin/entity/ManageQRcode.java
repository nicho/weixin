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
@Table(name = "wx_manage_QRcode")
public class ManageQRcode extends IdEntity {

	private String title;
	private String qrcodeType;// 二维码类型
	private String qrUrl;// 二维码url
	private String qrState;// 二维码状态
	private Date qrValidityDate;// 二维码有效期
	private Integer qrSubscribeCount; // 二维码关注数
	private Integer qrSubscribeAdminCount; // 二维码管理关注数
	private Date createDate;
	private String description;
	private Integer isdelete;
	private User user;
	private ManageTask task;// 主任务
	private String imageUrl;

	public Integer getQrSubscribeAdminCount() {
		return qrSubscribeAdminCount;
	}

	public void setQrSubscribeAdminCount(Integer qrSubscribeAdminCount) {
		this.qrSubscribeAdminCount = qrSubscribeAdminCount;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getQrUrl() {
		return qrUrl;
	}

	public void setQrUrl(String qrUrl) {
		this.qrUrl = qrUrl;
	}

	public String getQrState() {
		return qrState;
	}

	public void setQrState(String qrState) {
		this.qrState = qrState;
	}

	public Date getQrValidityDate() {
		return qrValidityDate;
	}

	public void setQrValidityDate(Date qrValidityDate) {
		this.qrValidityDate = qrValidityDate;
	}

	public Integer getQrSubscribeCount() {
		return qrSubscribeCount;
	}

	public void setQrSubscribeCount(Integer qrSubscribeCount) {
		this.qrSubscribeCount = qrSubscribeCount;
	}

	public String getQrcodeType() {
		return qrcodeType;
	}

	public void setQrcodeType(String qrcodeType) {
		this.qrcodeType = qrcodeType;
	}

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
