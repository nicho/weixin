/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.gamewin.weixin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gamewin.weixin.entity.ActivationCode;

public interface ActivationCodeDao extends PagingAndSortingRepository<ActivationCode, Long>, JpaSpecificationExecutor<ActivationCode> {

	Page<ActivationCode> findByUserId(Long id, Pageable pageRequest);
 
}
