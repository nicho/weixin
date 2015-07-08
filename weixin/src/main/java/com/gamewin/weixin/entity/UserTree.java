package com.gamewin.weixin.entity;

import java.util.List;

import javax.persistence.OneToMany;

import com.google.common.collect.Lists;

public class UserTree {
	Long id;
	String text;
	List<UserTree> children= Lists.newArrayList();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	@OneToMany(mappedBy = "user")
	public List<UserTree> getChildren() {
		return children;
	}

	public void setChildren(List<UserTree> children) {
		this.children = children;
	}

 
}
