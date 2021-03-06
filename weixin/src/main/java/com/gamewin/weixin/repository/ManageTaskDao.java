/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.gamewin.weixin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gamewin.weixin.entity.ManageTask;

public interface ManageTaskDao extends PagingAndSortingRepository<ManageTask, Long>, JpaSpecificationExecutor<ManageTask> {

	Page<ManageTask> findByUserId(Long id, Pageable pageRequest);

	@Modifying
	@Query("delete from ManageTask task where task.user.id=?1")
	void deleteByUserId(Long id);
	 
}
