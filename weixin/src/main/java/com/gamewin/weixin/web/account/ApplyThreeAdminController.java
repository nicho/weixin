/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.gamewin.weixin.web.account;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;

import com.gamewin.weixin.entity.ApplyThreeAdmin;
import com.gamewin.weixin.entity.User;
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

		Page<ApplyThreeAdmin> applyThreeAdmins = applyThreeAdminService.getUserApplyThreeAdmin(userId, searchParams, pageNumber, pageSize,
				sortType);

		model.addAttribute("applyThreeAdmins", applyThreeAdmins);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "applyThreeAdmin/applyThreeAdminList";
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model,RedirectAttributes redirectAttributes) {
		
		ApplyThreeAdmin applyThreeAdmin=applyThreeAdminService.getApplyThreeAdminByUser(getCurrentUserId());
		if(applyThreeAdmin!=null)
		{
			redirectAttributes.addFlashAttribute("applyThreeAdmin", applyThreeAdmin);
			redirectAttributes.addFlashAttribute("message", "您的分销商申请正在审批,请等待审批完成.");
			return "redirect:/ApplyThreeAdmin/applyThreeAdminView";
		}else
		{
			
			model.addAttribute("applyThreeAdmin", new ApplyThreeAdmin());
			model.addAttribute("action", "create");
			return "applyAdmin/applyThreeAdminFrom";
		}

	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid ApplyThreeAdmin newApplyThreeAdmin, RedirectAttributes redirectAttributes,ServletRequest request) {
		User user = new User(getCurrentUserId());
		newApplyThreeAdmin.setUser(user);
		newApplyThreeAdmin.setIsdelete(0);
		newApplyThreeAdmin.setStatus("submit");
		String upuserName =request.getParameter("upuserName");
		User upuser=  accountService.findUserByLoginName(upuserName);
		if(upuser!=null && "TwoAdmin".equals(upuser.getRoles()))
		{  
			newApplyThreeAdmin.setUpuser(upuser);
			applyThreeAdminService.saveApplyThreeAdmin(newApplyThreeAdmin);
			redirectAttributes.addFlashAttribute("message", "申请任务成功,待上级分销商审批");
			return "redirect:/ApplyThreeAdmin/applyThreeAdminView";
		}
		else
		{
			redirectAttributes.addFlashAttribute("message", "申请任务失败,上级分销商不存在");
			return "redirect:/ApplyThreeAdmin/applyThreeAdminView";
		}
	
	} 
	@RequestMapping(value = "applyThreeAdminView", method = RequestMethod.GET)
	public String applyThreeAdminView(Model model) { 
		return "applyAdmin/applyThreeAdminView";
	}
	
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("task", applyThreeAdminService.getApplyThreeAdmin(id));
		model.addAttribute("action", "update");
		return "applyThreeAdmin/applyThreeAdminForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("applyThreeAdmin") ApplyThreeAdmin applyThreeAdmin,
			RedirectAttributes redirectAttributes, ServletRequest request) { 

		try { 
			applyThreeAdminService.saveApplyThreeAdmin(applyThreeAdmin);
			redirectAttributes.addFlashAttribute("message", "更新任务成功");
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "更新任务失败");
			return "redirect:/applyThreeAdmin/";
		}
		return "redirect:/applyThreeAdmin/";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		ApplyThreeAdmin entity = applyThreeAdminService.getApplyThreeAdmin(id);
		entity.setIsdelete(1);
		applyThreeAdminService.saveApplyThreeAdmin(entity);
		redirectAttributes.addFlashAttribute("message", "删除任务成功");
		return "redirect:/applyThreeAdmin/";
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2
	 * Preparable二次部分绑定的效果,先根据form的id从数据库查出ApplyThreeAdmin对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getApplyThreeAdmin(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("applyThreeAdmin", applyThreeAdminService.getApplyThreeAdmin(id));
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
