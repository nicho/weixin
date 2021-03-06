/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.gamewin.weixin.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gamewin.weixin.entity.GameCode;

public interface GameCodeDao extends PagingAndSortingRepository<GameCode, Long>, JpaSpecificationExecutor<GameCode> {
  
}
