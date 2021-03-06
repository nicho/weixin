/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.gamewin.weixin.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gamewin.weixin.entity.User;
import com.gamewin.weixin.entity.UserTree;
import com.gamewin.weixin.entity.UserTree2;

/**
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * 
 * @author calvin
 */
@MyBatisRepository
public interface UserMybatisDao {
 
	List<User> getUserByUpUserlist(Long id);
	List<User> getUserAllUserlist();
	List<UserTree> getUserTree();
	List<UserTree2> getUserTree2(@Param("userid") Long  userid);
}
