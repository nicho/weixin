/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.gamewin.weixin.web.account;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

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

import com.gamewin.weixin.entity.User;
import com.gamewin.weixin.service.account.AccountService;
import com.gamewin.weixin.service.account.ShiroDbRealm.ShiroUser;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;

/**
 * 管理员管理用户的Controller.
 * 
 * @author calvin
 */
@Controller
@RequestMapping(value = "/admin/user")
public class UserAdminController {
	private static final String PAGE_SIZE = "10";

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("title", "标题");
	}
	
	private static Map<String, String> allStatus = Maps.newHashMap();

	static {
		allStatus.put("enabled", "有效");
		allStatus.put("disabled", "无效");
		allStatus.put("Audit", "审批中");
	}
	
	@Autowired
	private AccountService accountService;

	
	@RequestMapping(value = "auditUserlist",method = RequestMethod.GET)
	public String auditUserlist(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
	 
		Page<User> users = accountService.getUserByAuditUserlist(user.id, searchParams, pageNumber, pageSize, sortType);

		model.addAttribute("users", users);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		model.addAttribute("allStatus", allStatus);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "audit/auditUserList";
	}
	
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		String usertype=user.getRoles();
		Page<User> users =null;
		if("admin".equals(usertype))
		{
			users = accountService.getUser(usertype, searchParams, pageNumber, pageSize, sortType);
		} else if ("TwoAdmin".equals(usertype)) {
		  	 List<User> usersx = accountService.getUserByUpTwoAdminUserlist(user.id,searchParams, pageNumber, pageSize, sortType);
		  	 PageInfo<User> page = new PageInfo<User>(usersx);
		  	model.addAttribute("page", page);
		  	model.addAttribute("usersx", usersx);
			//users = accountService.getUserByUpTwoAdminUserlist(user.id, searchParams, pageNumber, pageSize, sortType);
		} else if ("ThreeAdmin".equals(usertype)) {
			users = accountService.getUserByUpUserlist(user.id, searchParams, pageNumber, pageSize, sortType);
		}
		
		model.addAttribute("users", users);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		model.addAttribute("allStatus", allStatus);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "account/adminUserList";
	}
	
 
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("user", accountService.getUser(id));
		return "account/adminUserForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
		accountService.updateUser(user);
		redirectAttributes.addFlashAttribute("message", "更新用户" + user.getLoginName() + "成功");
		return "redirect:/admin/user";
	}
	
	@RequestMapping(value = "disabled/{id}")
	public String disabled(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		User user = accountService.getUser(id);
		user.setStatus("disabled");
		accountService.updateUser(user);
		redirectAttributes.addFlashAttribute("message", "失效用户" + user.getLoginName() + "成功");
		return "redirect:/admin/user";
	}
	
	@RequestMapping(value = "auditPass/{id}")
	public String auditPass(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		User user = accountService.getUser(id);
		ShiroUser nowuser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		if(user.getUpuser().getLoginName().equals(nowuser.getLoginName()))
		{
			user.setStatus("enabled");
			accountService.updateUser(user);
			redirectAttributes.addFlashAttribute("message", "用户" + user.getLoginName() + "注册成功");
		 
			return "redirect:/admin/user/auditUserlist";
		}else
		{
			redirectAttributes.addFlashAttribute("message", "非法操作!");
			return "redirect:/admin/user/auditUserlist";
		}

	}
 
	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		User user = accountService.getUser(id);
		accountService.deleteUser(user);
		redirectAttributes.addFlashAttribute("message", "删除用户" + user.getLoginName() + "成功");
		return "redirect:/admin/user";
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2 Preparable二次部分绑定的效果,先根据form的id从数据库查出User对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getUser(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("user", accountService.getUser(id));
		}
	}
}
