/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.gamewin.weixin.web.account;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gamewin.weixin.entity.User;
import com.gamewin.weixin.service.account.AccountService;

/**
 * 用户注册的Controller.
 * 
 * @author calvin
 */
@Controller
@RequestMapping(value = "/register")
public class RegisterController {

	@Autowired
	private AccountService accountService;

	@RequestMapping(method = RequestMethod.GET)
	public String registerForm() {
		return "account/register";
	}
 
	
	@RequestMapping(method = RequestMethod.POST)
	public String register(@Valid User user, RedirectAttributes redirectAttributes,ServletRequest request) {
		user.setIsdelete(0);
		
		
		String upuserName =request.getParameter("upuserName");
		User upuser=  accountService.findUserByLoginName(upuserName);
		if(upuser!=null && ("TwoAdmin".equals(upuser.getRoles()) || "ThreeAdmin".equals(upuser.getRoles())))
		{
			user.setUpuser(upuser);
			user.setStatus("Audit"); 
			accountService.registerUser(user); 
			redirectAttributes.addFlashAttribute("message", "注册申请已提交,待审核.");
			return "redirect:/login";
		}
		else
		{
			redirectAttributes.addFlashAttribute("message", "注册失败,上级分销商不存在");
			return "redirect:/register";
		}

	}

	/**
	 * Ajax请求校验loginName是否唯一。
	 */
	@RequestMapping(value = "checkLoginName")
	@ResponseBody
	public String checkLoginName(@RequestParam("loginName") String loginName) {
		if (accountService.findUserByLoginName(loginName) == null) {
			return "true";
		} else {
			return "false";
		}
	}
	
	/**
	 * Ajax请求校验是否是二级经销商
	 */
	@RequestMapping(value = "checkUpuserName")
	@ResponseBody
	public String checkUpuserName(@RequestParam("upuserName") String upuserName) {
		User upuser=  accountService.findUserByLoginName(upuserName);
		if(upuser!=null && ("TwoAdmin".equals(upuser.getRoles()) || "ThreeAdmin".equals(upuser.getRoles())))
		{  
			return "true";
		}
		else
		{
			return "false";
		}
	 
	}
}
