/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.gamewin.weixin.web.api;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gamewin.weixin.entity.Task;
import com.gamewin.weixin.entity.User;
import com.gamewin.weixin.service.account.ShiroDbRealm.ShiroUser;
import com.gamewin.weixin.service.task.TaskService;
import com.gamewin.weixin.util.MobileHttpClient;

@Controller
@RequestMapping(value = "/index")
public class IndexController {
	@Autowired
	private TaskService taskService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model,HttpServletRequest request) {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		Task task = taskService.getTaskByUser(user.id);
		if (task != null) {
			model.addAttribute("task", task);
			model.addAttribute("action", "update");
			return "task/taskForm";
		} else {
			task=new Task();
			User userx = new User(user.id);
			task.setTitle(user.getName()+"的二维码");
			task.setUser(userx);

		
			String imageUrl=user.id+".jpg";
			String AccessToken=taskService.getAccessToken();
			System.out.println(AccessToken);
			String ticket;
			try {
				ticket = MobileHttpClient.getJsapi_ticket(AccessToken,user.id);
				System.out.println(ticket);
				String url=request.getServletContext().getRealPath("/")+"\\image\\"+imageUrl;
				MobileHttpClient.getticketImage(URLEncoder.encode(ticket,"UTF-8"),url);
				
			} catch (Exception e) { 
				e.printStackTrace();
			}
			task.setImageUrl(imageUrl);
			taskService.saveTask(task);
			
			model.addAttribute("task", task);
			model.addAttribute("action", "update");
			return "task/taskForm";
		}
	}
}
