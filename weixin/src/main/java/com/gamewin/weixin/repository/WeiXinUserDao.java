/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.gamewin.weixin.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gamewin.weixin.entity.WeiXinUser;

public interface WeiXinUserDao extends PagingAndSortingRepository<WeiXinUser, Long>, JpaSpecificationExecutor<WeiXinUser>  {
	
	@Query("SELECT COUNT(DISTINCT t.fromUserName) FROM WeiXinUser  t WHERE  t.event='subscribe' AND t.eventKey=?1")
	int selectSubscribeByUserId(String key);
}
