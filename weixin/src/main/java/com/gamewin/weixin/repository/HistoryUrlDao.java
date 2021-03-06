/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.gamewin.weixin.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gamewin.weixin.entity.HistoryUrl;

public interface HistoryUrlDao extends PagingAndSortingRepository<HistoryUrl, Long>, JpaSpecificationExecutor<HistoryUrl>  {
	
	@Query("SELECT COUNT(t.taskId) FROM HistoryUrl  t WHERE  t.userIp=?1 AND t.qrcodeId=?2")
	Integer selectHistoryUrlByuserIpAndqrcodeId(String userIp,Long qrcodeId);
}
