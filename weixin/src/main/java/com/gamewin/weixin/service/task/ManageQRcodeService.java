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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.cache.memcached.SpyMemcachedClient;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.gamewin.weixin.entity.ManageQRcode;
import com.gamewin.weixin.mybatis.ManageQRcodeMybatisDao;
import com.gamewin.weixin.repository.ManageQRcodeDao;
import com.gamewin.weixin.util.MemcachedObjectType;
import com.gamewin.weixin.util.MobileHttpClient;
import com.github.pagehelper.PageHelper;

// Spring Bean的标识.
@Component
// 类中所有public函数都纳入事务管理的标识.
@Transactional
public class ManageQRcodeService {

	private ManageQRcodeDao manageQRcodeDao;

	@Autowired(required = false)
	private SpyMemcachedClient memcachedClient;
	@Autowired
	private ManageQRcodeMybatisDao manageQRcodeMybatisDao;
	
	public ManageQRcode getManageQRcode(Long id) {
		return manageQRcodeDao.findOne(id);
	}

	public void saveManageQRcode(ManageQRcode entity) {
		manageQRcodeDao.save(entity);
	}

	public void deleteManageQRcode(Long id) {
		manageQRcodeDao.delete(id);
	}

	public List<ManageQRcode> getAllManageQRcode() {
		return (List<ManageQRcode>) manageQRcodeDao.findAll();
	}
	
	public List<ManageQRcode> getUserManageQRcodeByTaskId(Long taskId, Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageHelper.startPage(pageNumber, pageSize); 
		return manageQRcodeMybatisDao.getUserManageQRcodeByTaskId(taskId);
	}
	public List<ManageQRcode> getMyUserManageQRcodeByTaskId(Long taskId,Long userId, Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageHelper.startPage(pageNumber, pageSize); 
		return manageQRcodeMybatisDao.getUserManageQRcodeByTaskId(taskId);
	}
	public Integer getUserManageQRcodeByTaskIdAndQrType(Long taskId,String qrType) {
		return manageQRcodeMybatisDao.getUserManageQRcodeByTaskIdAndQrType(taskId, qrType);
	}
	
	 	public Page<ManageQRcode> getUserManageQRcode(Long userId, Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<ManageQRcode> spec = buildSpecification(userId, searchParams);

		return manageQRcodeDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if ("title".equals(sortType)) {
			sort = new Sort(Direction.ASC, "title");
		}

		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<ManageQRcode> buildSpecification(Long userId, Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		filters.put("user.id", new SearchFilter("user.id", Operator.EQ, userId));
		filters.put("isdelete", new SearchFilter("isdelete", Operator.EQ, "0"));
		Specification<ManageQRcode> spec = DynamicSpecifications.bySearchFilter(filters.values(), ManageQRcode.class);
		return spec;
	}

	@Autowired
	public void setManageQRcodeDao(ManageQRcodeDao manageQRcodeDao) {
		this.manageQRcodeDao = manageQRcodeDao;
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

 

	public ManageQRcode getManageQRcodeByUser(Long userid) {
		Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
		filters.put("user.id", new SearchFilter("user.id", Operator.EQ, userid));
		Specification<ManageQRcode> spec = DynamicSpecifications.bySearchFilter(filters.values(), ManageQRcode.class);
		List<ManageQRcode> list = manageQRcodeDao.findAll(spec);
		if (list != null && list.size() > 0)
			return list.get(0);
		else
			return null;
	}
	
}
