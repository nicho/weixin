/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.gamewin.weixin.service.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.cache.memcached.SpyMemcachedClient;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.gamewin.weixin.entity.ManageTask;
import com.gamewin.weixin.entity.ViewRange;
import com.gamewin.weixin.mybatis.ManageTaskMybatisDao;
import com.gamewin.weixin.repository.ManageQRcodeDao;
import com.gamewin.weixin.repository.ManageTaskDao;
import com.gamewin.weixin.repository.ViewRangeDao;
import com.gamewin.weixin.util.MemcachedObjectType;
import com.gamewin.weixin.util.MobileHttpClient;
import com.github.pagehelper.PageHelper;

// Spring Bean的标识.
@Component
// 类中所有public函数都纳入事务管理的标识.
@Transactional
public class ManageTaskService {

	private ManageTaskDao manageTaskDao;

	@Autowired(required = false)
	private SpyMemcachedClient memcachedClient;
	@Autowired
	private ManageTaskMybatisDao manageTaskMybatisDao;
	@Autowired
	private ViewRangeDao viewRangeDao;
	@Autowired
	private ManageQRcodeDao manageQRcodeDao;
	

	public ManageTask getManageTask(Long id) {
		return manageTaskDao.findOne(id);
	}
	
	public void invalidAllQRCode(Long taskId) {
		manageQRcodeDao.invalidAllQRCode(taskId);
	}
	public void saveManageTask(ManageTask entity) {
		manageTaskDao.save(entity);
	}

	public void saveViewRange(ViewRange entity) {
		viewRangeDao.save(entity);
	}

	public void deleteManageTask(Long id) {
		manageTaskDao.delete(id);
	}

	public List<ManageTask> getAllManageTask() {
		return (List<ManageTask>) manageTaskDao.findAll();
	}

	public List<ManageTask> getUserManageTask_createQr(Long userId) {
		Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
		filters.put("user.id", new SearchFilter("user.id", Operator.EQ, userId));
		filters.put("isdelete", new SearchFilter("isdelete", Operator.EQ, "0"));
		Specification<ManageTask> spec = DynamicSpecifications.bySearchFilter(filters.values(), ManageTask.class);
		return manageTaskDao.findAll(spec);
	}

	public List<ManageTask> getUserMyManageTask(Long userId, Map<String, Object> searchParams, int pageNumber, int pageSize, String sortType) {
		PageHelper.startPage(pageNumber, pageSize);
		return manageTaskMybatisDao.getUserMyManageTask(userId);
	}

	public List<ManageTask> getUserManageTask(Long userId, Map<String, Object> searchParams, int pageNumber, int pageSize, String sortType) {
		PageHelper.startPage(pageNumber, pageSize);
		return manageTaskMybatisDao.getUserManageTask(userId);
	}

	@Autowired
	public void setManageTaskDao(ManageTaskDao manageTaskDao) {
		this.manageTaskDao = manageTaskDao;
	}

	public String getAccessToken() {
		String key = MemcachedObjectType.WEIXIN.getPrefix() + "AccessToken";

		String accessToken = memcachedClient.get(key);
		if (StringUtils.isEmpty(accessToken)) {
			try {
				accessToken = MobileHttpClient.getAccessToken();
				memcachedClient.set(key, MemcachedObjectType.WEIXIN.getExpiredTime(), accessToken);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return accessToken;
	}

	public ManageTask getManageTaskByUser(Long userid) {
		Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
		filters.put("user.id", new SearchFilter("user.id", Operator.EQ, userid));
		Specification<ManageTask> spec = DynamicSpecifications.bySearchFilter(filters.values(), ManageTask.class);
		List<ManageTask> list = manageTaskDao.findAll(spec);
		if (list != null && list.size() > 0)
			return list.get(0);
		else
			return null;
	}

}
