/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.gamewin.weixin.web.task;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.time.DateUtils;
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

import com.gamewin.weixin.entity.ManageQRcode;
import com.gamewin.weixin.entity.User;
import com.gamewin.weixin.service.account.ShiroDbRealm.ShiroUser;
import com.gamewin.weixin.service.task.ManageQRcodeService;
import com.google.common.collect.Maps;

/**
 * ManageQRcode管理的Controller, 使用Restful风格的Urls:
 * 
 * List page : GET /manageQRcode/ Create page : GET /manageQRcode/create Create
 * action : POST /manageQRcode/create Update page : GET /manageQRcode/update/{id}
 * Update action : POST /manageQRcode/update Delete action : GET
 * /manageQRcode/delete/{id}
 * 
 * @author calvin
 */
@Controller
@RequestMapping(value = "/manageQRcode")
public class ManageQRcodeController {
	 
	private static final String PAGE_SIZE = "10";

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("title", "标题");
	}

	@Autowired
	private ManageQRcodeService manageQRcodeService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Long userId = getCurrentUserId();

		Page<ManageQRcode> manageQRcodes = manageQRcodeService.getUserManageQRcode(userId, searchParams, pageNumber, pageSize,
				sortType);

		model.addAttribute("manageQRcodes", manageQRcodes);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "manageQRcode/manageQRcodeList";
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("manageQRcode", new ManageQRcode());
		model.addAttribute("action", "create");
		return "manageQRcode/manageQRcodeForm";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid ManageQRcode newManageQRcode, RedirectAttributes redirectAttributes) {
		User user = new User(getCurrentUserId());
		newManageQRcode.setUser(user);
		newManageQRcode.setIsdelete(0);
		manageQRcodeService.saveManageQRcode(newManageQRcode);
		redirectAttributes.addFlashAttribute("message", "创建任务成功");
		return "redirect:/manageQRcode/";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("task", manageQRcodeService.getManageQRcode(id));
		model.addAttribute("action", "update");
		return "manageQRcode/manageQRcodeForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("manageQRcode") ManageQRcode manageQRcode,
			RedirectAttributes redirectAttributes, ServletRequest request) {
		String createDateStr = request.getParameter("createDateStr");
		String endDateStr = request.getParameter("endDateStr");

		try {
			manageQRcode.setCreateDate(DateUtils.parseDate(createDateStr, "yyyy-MM-dd"));
			manageQRcode.setEndDate(DateUtils.parseDate(endDateStr, "yyyy-MM-dd"));
			manageQRcodeService.saveManageQRcode(manageQRcode);
			redirectAttributes.addFlashAttribute("message", "更新任务成功");
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "更新任务失败");
			return "redirect:/manageQRcode/";
		}
		return "redirect:/manageQRcode/";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		ManageQRcode entity = manageQRcodeService.getManageQRcode(id);
		entity.setIsdelete(1);
		manageQRcodeService.saveManageQRcode(entity);
		redirectAttributes.addFlashAttribute("message", "删除任务成功");
		return "redirect:/manageQRcode/";
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2
	 * Preparable二次部分绑定的效果,先根据form的id从数据库查出ManageQRcode对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getManageQRcode(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("manageQRcode", manageQRcodeService.getManageQRcode(id));
		}
	}

	/**
	 * 取出Shiro中的当前用户Id.
	 */
	private Long getCurrentUserId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.id;
	}

}
