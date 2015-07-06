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

import com.gamewin.weixin.entity.ActivationCode;
import com.gamewin.weixin.repository.ActivationCodeDao;
import com.gamewin.weixin.util.MemcachedObjectType;
import com.gamewin.weixin.util.MobileHttpClient;

// Spring Bean的标识.
@Component
// 类中所有public函数都纳入事务管理的标识.
@Transactional
public class ActivationCodeService {

	private ActivationCodeDao activationCodeDao;

	@Autowired(required = false)
	private SpyMemcachedClient memcachedClient;

	public ActivationCode getActivationCode(Long id) {
		return activationCodeDao.findOne(id);
	}

	public void saveActivationCode(ActivationCode entity) {
		activationCodeDao.save(entity);
	}

	public void deleteActivationCode(Long id) {
		activationCodeDao.delete(id);
	}

	public List<ActivationCode> getAllActivationCode() {
		return (List<ActivationCode>) activationCodeDao.findAll();
	}

	public Page<ActivationCode> getUserActivationCode(Long userId, Map<String, Object> searchParams, int pageNumber, int pageSize, String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<ActivationCode> spec = buildSpecification(userId, searchParams);

		return activationCodeDao.findAll(spec, pageRequest);
	}

	public List<ActivationCode> getUserActivationCode_createQr(Long userId) {
		Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
		filters.put("user.id", new SearchFilter("user.id", Operator.EQ, userId));
		filters.put("isdelete", new SearchFilter("isdelete", Operator.EQ, "0"));
		Specification<ActivationCode> spec = DynamicSpecifications.bySearchFilter(filters.values(), ActivationCode.class);
		return activationCodeDao.findAll(spec);
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
	private Specification<ActivationCode> buildSpecification(Long userId, Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		filters.put("user.id", new SearchFilter("user.id", Operator.EQ, userId));
		filters.put("isdelete", new SearchFilter("isdelete", Operator.EQ, "0"));
		Specification<ActivationCode> spec = DynamicSpecifications.bySearchFilter(filters.values(), ActivationCode.class);
		return spec;
	}

	@Autowired
	public void setActivationCodeDao(ActivationCodeDao activationCodeDao) {
		this.activationCodeDao = activationCodeDao;
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

	public ActivationCode getActivationCodeByUser(Long userid) {
		Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
		filters.put("user.id", new SearchFilter("user.id", Operator.EQ, userid));
		Specification<ActivationCode> spec = DynamicSpecifications.bySearchFilter(filters.values(), ActivationCode.class);
		List<ActivationCode> list = activationCodeDao.findAll(spec);
		if (list != null && list.size() > 0)
			return list.get(0);
		else
			return null;
	}

}
