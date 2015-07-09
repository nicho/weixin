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

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;

import com.gamewin.weixin.entity.ApplyThreeAdmin;
import com.gamewin.weixin.entity.User;
import com.gamewin.weixin.model.UserDto;
import com.gamewin.weixin.service.account.AccountService;
import com.gamewin.weixin.service.account.ApplyThreeAdminService;
import com.gamewin.weixin.service.account.ShiroDbRealm.ShiroUser;
import com.google.common.collect.Maps;

/**
 * ApplyThreeAdmin管理的Controller, 使用Restful风格的Urls:
 * 
 * List page : GET /applyThreeAdmin/ Create page : GET /applyThreeAdmin/create Create
 * action : POST /applyThreeAdmin/create Update page : GET /applyThreeAdmin/update/{id}
 * Update action : POST /applyThreeAdmin/update Delete action : GET
 * /applyThreeAdmin/delete/{id}
 * 
 * @author calvin
 */
@Controller
@RequestMapping(value = "/ApplyThreeAdmin")
public class ApplyThreeAdminController {

	@Autowired
	private AccountService accountService;
	private static final String PAGE_SIZE = "10";

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("title", "标题");
	}

	@Autowired
	private ApplyThreeAdminService applyThreeAdminService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Long userId = getCurrentUserId();
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		Page<ApplyThreeAdmin> applyThreeAdmins =null;
		if ("admin".equals(user.getRoles())) {
			applyThreeAdmins = applyThreeAdminService.getUserApplyThreeAdminAuditAdmin(userId, searchParams, pageNumber, pageSize,sortType);
		} else {
			applyThreeAdmins = applyThreeAdminService.getUserApplyThreeAdminAudit(userId, searchParams, pageNumber, pageSize,sortType);
		}
		 

		model.addAttribute("applyThreeAdmins", applyThreeAdmins);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "audit/applyThreeAdminList";
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model,RedirectAttributes redirectAttributes) {
		
		ApplyThreeAdmin applyThreeAdmin=applyThreeAdminService.getApplyThreeAdminByUser(getCurrentUserId());
		if(applyThreeAdmin!=null)
		{
			if("submit".equals(applyThreeAdmin.getStatus()))
			{
				redirectAttributes.addFlashAttribute("applyThreeAdmin", applyThreeAdmin);
				redirectAttributes.addFlashAttribute("message", "您的分销商申请正在审批,请等待审批完成.");
				return "redirect:/ApplyThreeAdmin/applyThreeAdminView";
			}
			else if("reject".equals(applyThreeAdmin.getStatus()))
			{
				redirectAttributes.addFlashAttribute("applyThreeAdmin", applyThreeAdmin);
				redirectAttributes.addFlashAttribute("message", "您的分销商申请已被拒绝!");
				return "redirect:/ApplyThreeAdmin/applyThreeAdminView";
			}else if("pass".equals(applyThreeAdmin.getStatus()))
			{
				redirectAttributes.addFlashAttribute("applyThreeAdmin", applyThreeAdmin);
				redirectAttributes.addFlashAttribute("message", "您的分销商申请已通过!");
				return "redirect:/ApplyThreeAdmin/applyThreeAdminView";
			}else
			{
				redirectAttributes.addFlashAttribute("applyThreeAdmin", applyThreeAdmin);
				return "redirect:/ApplyThreeAdmin/applyThreeAdminView";
			}
		}else
		{ 
			List<UserDto> userdto=accountService.getUserByTwoUpAdminUserlist();
			model.addAttribute("userdto", userdto);
			model.addAttribute("applyThreeAdmin", new ApplyThreeAdmin());
			model.addAttribute("action", "create");
			return "applyAdmin/applyThreeAdminFrom";
		}

	}
	@RequestMapping(value = "auditView/{id}", method = RequestMethod.GET)
	public String auditView(@PathVariable("id") Long id,Model model,RedirectAttributes redirectAttributes,ServletRequest request) {
  
		ApplyThreeAdmin applyThreeAdmin=applyThreeAdminService.getApplyThreeAdmin(id);
		if(applyThreeAdmin!=null)
		{ 
			model.addAttribute("applyThreeAdmin",applyThreeAdmin);
			return "audit/applyThreeAdminView";
		}else
		{ 
			redirectAttributes.addFlashAttribute("message", "无法找到此申请.");
			return "redirect:/ApplyThreeAdmin";
		}

	}
	
	/**
	 * 审批 
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresRoles(value = { "admin", "TwoAdmin", "ThreeAdmin" }, logical = Logical.OR)
	@RequestMapping(value = "auditPass" , method = RequestMethod.POST)
	public String auditPass(Model model,RedirectAttributes redirectAttributes,ServletRequest request,ApplyThreeAdmin applyThreeAdmin) {
		 
		ApplyThreeAdmin applyThreeAdminold=applyThreeAdminService.getApplyThreeAdmin(applyThreeAdmin.getId());
		applyThreeAdminold.setStatus(applyThreeAdmin.getStatus());
		applyThreeAdminService.saveApplyThreeAdmin(applyThreeAdminold);
		
		if("pass".equals(applyThreeAdmin.getStatus()))
		{
			ApplyThreeAdmin applyThree =applyThreeAdminService.getApplyThreeAdmin(applyThreeAdmin.getId());
			User applyUser=accountService.getUser(applyThree.getUser().getId());
			applyUser.setRoles("ThreeAdmin");
			accountService.updateUser(applyUser);
			redirectAttributes.addFlashAttribute("message", "审批成功,"+applyThree.getUserName()+",已成为分销商");
		}else
		{
			redirectAttributes.addFlashAttribute("message", "已拒绝");
		}
	 
		return "redirect:/ApplyThreeAdmin";
	}
	
	 
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid ApplyThreeAdmin newApplyThreeAdmin, RedirectAttributes redirectAttributes,ServletRequest request) {
		User user = new User(getCurrentUserId());
		newApplyThreeAdmin.setUser(user);
		newApplyThreeAdmin.setIsdelete(0);
		newApplyThreeAdmin.setStatus("submit");
		
 	
		String upuserId =request.getParameter("upuserId");
		if(!StringUtils.isEmpty(upuserId))
		{
			User upuser=  accountService.getUser(Long.parseLong(upuserId));
			if(upuser!=null && "TwoAdmin".equals(upuser.getRoles()))
			{  
				newApplyThreeAdmin.setUpuser(upuser);
				applyThreeAdminService.saveApplyThreeAdmin(newApplyThreeAdmin);
				redirectAttributes.addFlashAttribute("applyThreeAdmin", newApplyThreeAdmin);
				redirectAttributes.addFlashAttribute("message", "申请中,待上级分销商审批");
				return "redirect:/ApplyThreeAdmin/applyThreeAdminView";
			}
			else
			{
				applyThreeAdminService.saveApplyThreeAdmin(newApplyThreeAdmin);
				redirectAttributes.addFlashAttribute("applyThreeAdmin", newApplyThreeAdmin);
				redirectAttributes.addFlashAttribute("message", "申请中,待总经销商审批");
				return "redirect:/ApplyThreeAdmin/applyThreeAdminView";
			}
		}else { 
			applyThreeAdminService.saveApplyThreeAdmin(newApplyThreeAdmin);
			redirectAttributes.addFlashAttribute("applyThreeAdmin", newApplyThreeAdmin);
			redirectAttributes.addFlashAttribute("message", "申请中,待总经销商审批");
			return "redirect:/ApplyThreeAdmin/applyThreeAdminView";
		}
		
	
	} 
	@RequestMapping(value = "applyThreeAdminView", method = RequestMethod.GET)
	public String applyThreeAdminView(Model model) { 
		return "applyAdmin/applyThreeAdminView";
	}
	
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model,RedirectAttributes redirectAttributes) {
		ApplyThreeAdmin applyThreeAdmin=applyThreeAdminService.getApplyThreeAdmin(id);
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		if(applyThreeAdmin!=null && user.id.equals(applyThreeAdmin.getUser().getId()))
		{
			model.addAttribute("applyThreeAdmin", applyThreeAdminService.getApplyThreeAdmin(id));
			model.addAttribute("action", "update");
			List<UserDto> userdto=accountService.getUserByTwoUpAdminUserlist();
			model.addAttribute("userdto", userdto);
			return "applyAdmin/applyThreeAdminFrom";
		}else
		{
			redirectAttributes.addFlashAttribute("message", "非法操作");
			return "redirect:/ApplyThreeAdmin/applyThreeAdminView";
		}
		

		
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("applyThreeAdmin") ApplyThreeAdmin newApplyThreeAdmin,
			RedirectAttributes redirectAttributes, ServletRequest request) { 
 
		User user = new User(getCurrentUserId());
		newApplyThreeAdmin.setUser(user);
		newApplyThreeAdmin.setIsdelete(0);
		newApplyThreeAdmin.setStatus("submit");
		
 	
		String upuserId =request.getParameter("upuserId");
		if(!StringUtils.isEmpty(upuserId))
		{
			User upuser=  accountService.getUser(Long.parseLong(upuserId));
			if(upuser!=null && "TwoAdmin".equals(upuser.getRoles()))
			{  
				newApplyThreeAdmin.setUpuser(upuser);
				applyThreeAdminService.saveApplyThreeAdmin(newApplyThreeAdmin);
				redirectAttributes.addFlashAttribute("applyThreeAdmin", newApplyThreeAdmin);
				redirectAttributes.addFlashAttribute("message", "重新申请中,待上级分销商审批");
				return "redirect:/ApplyThreeAdmin/applyThreeAdminView";
			}
			else
			{
				applyThreeAdminService.saveApplyThreeAdmin(newApplyThreeAdmin);
				redirectAttributes.addFlashAttribute("applyThreeAdmin", newApplyThreeAdmin);
				redirectAttributes.addFlashAttribute("message", "重新申请中,待总经销商审批");
				return "redirect:/ApplyThreeAdmin/applyThreeAdminView";
			}
		}else { 
			applyThreeAdminService.saveApplyThreeAdmin(newApplyThreeAdmin);
			redirectAttributes.addFlashAttribute("applyThreeAdmin", newApplyThreeAdmin);
			redirectAttributes.addFlashAttribute("message", "重新申请中,待总经销商审批");
			return "redirect:/ApplyThreeAdmin/applyThreeAdminView";
		}
		 
		
	}

 
 
	/**
	 * 取出Shiro中的当前用户Id.
	 */
	private Long getCurrentUserId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.id;
	}

	
	/**
	 * Ajax请求校验是否是二级经销商
	 */
	@RequestMapping(value = "checkUpuserName")
	@ResponseBody
	public String checkUpuserName(@RequestParam("upuserName") String upuserName) {
		User upuser=  accountService.findUserByLoginName(upuserName);
		if(upuser!=null && "TwoAdmin".equals(upuser.getRoles()))
		{  
			return "true";
		}
		else
		{
			return "false";
		}
	 
	}
}
