/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.gamewin.weixin.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gamewin.weixin.entity.HistoryWeixin;

public interface HistoryWeixinDao extends PagingAndSortingRepository<HistoryWeixin, Long>, JpaSpecificationExecutor<HistoryWeixin>  {
	
	@Query("SELECT COUNT(t.taskId) FROM HistoryWeixin  t WHERE  t.taskId=?1 AND t.fromUserName=?2")
	Integer selectHistoryWeixinBytaskId(Long taskId,String fromUserName);
}
