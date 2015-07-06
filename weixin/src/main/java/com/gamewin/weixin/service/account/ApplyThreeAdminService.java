/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.gamewin.weixin.service.account;

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

import com.gamewin.weixin.entity.ApplyThreeAdmin;
import com.gamewin.weixin.repository.ApplyThreeAdminDao;
import com.gamewin.weixin.util.MemcachedObjectType;
import com.gamewin.weixin.util.MobileHttpClient;

// Spring Bean的标识.
@Component
// 类中所有public函数都纳入事务管理的标识.
@Transactional
public class ApplyThreeAdminService {

	private ApplyThreeAdminDao applyThreeAdminDao;

	@Autowired(required = false)
	private SpyMemcachedClient memcachedClient;

	public ApplyThreeAdmin getApplyThreeAdmin(Long id) {
		return applyThreeAdminDao.findOne(id);
	}

	public void saveApplyThreeAdmin(ApplyThreeAdmin entity) {
		applyThreeAdminDao.save(entity);
	}

	public void deleteApplyThreeAdmin(Long id) {
		applyThreeAdminDao.delete(id);
	}

	public List<ApplyThreeAdmin> getAllApplyThreeAdmin() {
		return (List<ApplyThreeAdmin>) applyThreeAdminDao.findAll();
	}

	public Page<ApplyThreeAdmin> getUserApplyThreeAdmin(Long userId, Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<ApplyThreeAdmin> spec = buildSpecification(userId, searchParams);

		return applyThreeAdminDao.findAll(spec, pageRequest);
	}
	public List<ApplyThreeAdmin> getUserApplyThreeAdmin_createQr(Long userId) {  
		Map<String, SearchFilter> filters =new HashMap<String, SearchFilter>();
		filters.put("user.id", new SearchFilter("user.id", Operator.EQ, userId));
		filters.put("isdelete", new SearchFilter("isdelete", Operator.EQ, "0"));
		Specification<ApplyThreeAdmin> spec = DynamicSpecifications.bySearchFilter(filters.values(), ApplyThreeAdmin.class); 
		return applyThreeAdminDao.findAll(spec);
	}
	public Page<ApplyThreeAdmin> getUserApplyThreeAdminAudit(Long userId, Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType); 
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		filters.put("upuser.id", new SearchFilter("upuser.id", Operator.EQ, userId));
		filters.put("isdelete", new SearchFilter("isdelete", Operator.EQ, "0"));
		filters.put("status", new SearchFilter("status", Operator.EQ, "submit"));
		Specification<ApplyThreeAdmin> spec = DynamicSpecifications.bySearchFilter(filters.values(), ApplyThreeAdmin.class);
		return applyThreeAdminDao.findAll(spec, pageRequest);
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
	private Specification<ApplyThreeAdmin> buildSpecification(Long userId, Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		filters.put("user.id", new SearchFilter("user.id", Operator.EQ, userId));
		filters.put("isdelete", new SearchFilter("isdelete", Operator.EQ, "0"));
		Specification<ApplyThreeAdmin> spec = DynamicSpecifications.bySearchFilter(filters.values(), ApplyThreeAdmin.class);
		return spec;
	}

	@Autowired
	public void setApplyThreeAdminDao(ApplyThreeAdminDao applyThreeAdminDao) {
		this.applyThreeAdminDao = applyThreeAdminDao;
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

 

	public ApplyThreeAdmin getApplyThreeAdminByUser(Long userid) {
		Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
		filters.put("user.id", new SearchFilter("user.id", Operator.EQ, userid));
		Specification<ApplyThreeAdmin> spec = DynamicSpecifications.bySearchFilter(filters.values(), ApplyThreeAdmin.class);
		List<ApplyThreeAdmin> list = applyThreeAdminDao.findAll(spec);
		if (list != null && list.size() > 0)
			return list.get(0);
		else
			return null;
	}
	
}
