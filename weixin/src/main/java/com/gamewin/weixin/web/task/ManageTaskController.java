/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.gamewin.weixin.web.task;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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

import com.gamewin.weixin.entity.ManageTask;
import com.gamewin.weixin.entity.User;
import com.gamewin.weixin.entity.ViewRange;
import com.gamewin.weixin.service.account.ShiroDbRealm.ShiroUser;
import com.gamewin.weixin.service.task.ManageTaskService;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;

/**
 * ManageTask管理的Controller, 使用Restful风格的Urls:
 * 
 * List page : GET /manageTask/ Create page : GET /manageTask/create Create
 * action : POST /manageTask/create Update page : GET /manageTask/update/{id}
 * Update action : POST /manageTask/update Delete action : GET
 * /manageTask/delete/{id}
 * 
 * @author calvin
 */
@Controller
@RequestMapping(value = "/manageTask")
public class ManageTaskController {
	 
	private static final String PAGE_SIZE = "10";

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("title", "标题");
	}

	@Autowired
	private ManageTaskService manageTaskService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Long userId = getCurrentUserId();

		Page<ManageTask> manageTasks = manageTaskService.getUserManageTask(userId, searchParams, pageNumber, pageSize,
				sortType);

		model.addAttribute("manageTasks", manageTasks);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "manageTask/manageTaskList";
	}
	
	
	@RequestMapping(value = "myTask" ,method = RequestMethod.GET)
	public String myTask(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Long userId = getCurrentUserId();

		List<ManageTask> manageTasks = manageTaskService.getUserMyManageTask(userId, searchParams, pageNumber, pageSize,
				sortType);
		PageInfo<ManageTask> page = new PageInfo<ManageTask>(manageTasks);
	  	model.addAttribute("page", page);
		model.addAttribute("manageTasks", manageTasks);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "myTask/myTaskList";
	}
	
	
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("manageTask", new ManageTask());
		model.addAttribute("action", "create");
		return "manageTask/manageTaskForm";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid ManageTask newManageTask, RedirectAttributes redirectAttributes,ServletRequest request) {
		User user = new User(getCurrentUserId());
		newManageTask.setUser(user);
		newManageTask.setIsdelete(0);
		newManageTask.setState("Y");
		manageTaskService.saveManageTask(newManageTask);
		
		if("newManageTask".equals(newManageTask.getViewrangeType()))
		{
			//设置可见范围
			String viewrangeUsers=request.getParameter("viewrangeUsers");
			if(!StringUtils.isEmpty(viewrangeUsers))
			{
				String [] viewrangeUserArray=viewrangeUsers.split(",");
				for (int i = 0; i < viewrangeUserArray.length; i++) {
					Long userId=Long.parseLong(viewrangeUserArray[i]);  
					User user_vr = new User(userId);
					ViewRange vr=new ViewRange();
					vr.setCreateDate(new Date());
					vr.setIsdelete(0);
					vr.setTask(newManageTask);
					vr.setUser(user_vr);
					manageTaskService.saveViewRange(vr);
				}
			}
		}
		  
		
		redirectAttributes.addFlashAttribute("message", "创建任务成功");
		return "redirect:/manageTask/";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("task", manageTaskService.getManageTask(id));
		model.addAttribute("action", "update");
		return "manageTask/manageTaskForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("manageTask") ManageTask manageTask,
			RedirectAttributes redirectAttributes, ServletRequest request) {
		String createDateStr = request.getParameter("createDateStr");
		String endDateStr = request.getParameter("endDateStr");

		try {
			manageTask.setCreateDate(DateUtils.parseDate(createDateStr, "yyyy-MM-dd"));
			manageTask.setEndDate(DateUtils.parseDate(endDateStr, "yyyy-MM-dd"));
			manageTaskService.saveManageTask(manageTask);
			redirectAttributes.addFlashAttribute("message", "更新任务成功");
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "更新任务失败");
			return "redirect:/manageTask/";
		}
		return "redirect:/manageTask/";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		ManageTask entity = manageTaskService.getManageTask(id);
		entity.setIsdelete(1);
		entity.setState("N");
		manageTaskService.saveManageTask(entity);
		redirectAttributes.addFlashAttribute("message", "删除任务成功");
		return "redirect:/manageTask/";
	}
	@RequestMapping(value = "disabled/{id}")
	public String disabled(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		ManageTask entity = manageTaskService.getManageTask(id);
		entity.setState("N");
		manageTaskService.saveManageTask(entity);
		redirectAttributes.addFlashAttribute("message", "失效任务成功");
		return "redirect:/manageTask/";
	}
	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2
	 * Preparable二次部分绑定的效果,先根据form的id从数据库查出ManageTask对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getManageTask(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("manageTask", manageTaskService.getManageTask(id));
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
