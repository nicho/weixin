/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.gamewin.weixin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gamewin.weixin.entity.User;

public interface UserDao extends PagingAndSortingRepository<User, Long>, JpaSpecificationExecutor<User>  {
	User findByLoginName(String loginName);
	
	@Query("SELECT t FROM User t WHERE upuser.id =?1 OR upuser.id IN (SELECT id FROM User WHERE upuser.id=?1)")
	List<User> findByTwoAdmin(Long id);
}
