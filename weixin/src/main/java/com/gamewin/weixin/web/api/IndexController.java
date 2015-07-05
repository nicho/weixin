/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.gamewin.weixin.web.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gamewin.weixin.service.task.TaskService;

@Controller
@RequestMapping(value = "/index")
public class IndexController {
	@Autowired
	private TaskService taskService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model,HttpServletRequest request) { 
			return "index/index"; 
	}
}
