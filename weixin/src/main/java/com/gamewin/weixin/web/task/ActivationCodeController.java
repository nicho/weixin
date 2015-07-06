/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.gamewin.weixin.web.task;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;

import com.gamewin.weixin.entity.ActivationCode;
import com.gamewin.weixin.entity.User;
import com.gamewin.weixin.service.account.ShiroDbRealm.ShiroUser;
import com.gamewin.weixin.service.task.ActivationCodeService;
import com.google.common.collect.Maps;

/**
 * ActivationCode管理的Controller, 使用Restful风格的Urls:
 * 
 * List page : GET /activationCode/ Create page : GET /activationCode/create Create
 * action : POST /activationCode/create Update page : GET /activationCode/update/{id}
 * Update action : POST /activationCode/update Delete action : GET
 * /activationCode/delete/{id}
 * 
 * @author calvin
 */
@Controller
@RequestMapping(value = "/activationCode")
public class ActivationCodeController {
	 
	private static final String PAGE_SIZE = "10";

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("title", "标题");
	}

	@Autowired
	private ActivationCodeService activationCodeService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Long userId = getCurrentUserId();

		Page<ActivationCode> activationCodes = activationCodeService.getUserActivationCode(userId, searchParams, pageNumber, pageSize,
				sortType);

		model.addAttribute("activationCodes", activationCodes);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "activationCode/activationCodeList";
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("activationCode", new ActivationCode());
		model.addAttribute("action", "create");
		return "activationCode/activationCodeForm";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid ActivationCode newActivationCode, RedirectAttributes redirectAttributes,ServletRequest request) {
		User user = new User(getCurrentUserId());
		String activationCodeCount=request.getParameter("activationCodeCount");
		if(!StringUtils.isEmpty(activationCodeCount))
		{
			Integer activationCodeCountInt=Integer.parseInt(activationCodeCount);
			if(activationCodeCountInt>0)
			{
				for (int i = 0; i < activationCodeCountInt; i++) {
					 ActivationCode newActCode=new ActivationCode();
					 newActCode.setActivationCode(UUID.randomUUID().toString().replaceAll("-",""));
					 newActCode.setActivationCodeType(newActivationCode.getActivationCodeType());
					 newActCode.setCreateDate(new Date());
					 newActCode.setTitle(newActivationCode.getTitle());
					 newActCode.setDescription(newActivationCode.getDescription());
					 newActCode.setUser(user);
					 newActCode.setIsdelete(0);
					 activationCodeService.saveActivationCode(newActCode);
				}
			}
		}
	   
		redirectAttributes.addFlashAttribute("message", "创建激活码成功");
		return "redirect:/activationCode/";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("task", activationCodeService.getActivationCode(id));
		model.addAttribute("action", "update");
		return "activationCode/activationCodeForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("activationCode") ActivationCode activationCode,
			RedirectAttributes redirectAttributes, ServletRequest request) {
		 
		try { 
			activationCodeService.saveActivationCode(activationCode);
			redirectAttributes.addFlashAttribute("message", "更新任务成功");
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "更新任务失败");
			return "redirect:/activationCode/";
		}
		return "redirect:/activationCode/";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		ActivationCode entity = activationCodeService.getActivationCode(id);
		entity.setIsdelete(1);
		activationCodeService.saveActivationCode(entity);
		redirectAttributes.addFlashAttribute("message", "删除任务成功");
		return "redirect:/activationCode/";
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2
	 * Preparable二次部分绑定的效果,先根据form的id从数据库查出ActivationCode对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getActivationCode(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("activationCode", activationCodeService.getActivationCode(id));
		}
	}

	/**
	 * 取出Shiro中的当前用户Id.
	 */
	private Long getCurrentUserId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.id;
	}
	
	
	@RequestMapping(value = "disabled/{id}")
	public String disabled(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		ActivationCode activationCode = activationCodeService.getActivationCode(id);
		activationCode.setStatus("disabled");
		activationCodeService.saveActivationCode(activationCode);
		redirectAttributes.addFlashAttribute("message", "失效激活码'" + activationCode.getActivationCode() + "'成功");
		return "redirect:/activationCode/";
	}

}
