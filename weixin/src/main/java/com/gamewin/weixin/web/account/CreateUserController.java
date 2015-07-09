/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.gamewin.weixin.web.account;

import java.util.List;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gamewin.weixin.entity.User;
import com.gamewin.weixin.model.UserDto;
import com.gamewin.weixin.service.account.AccountService;

/**
 * 用户注册的Controller.
 * 
 * @author calvin
 */
@Controller
@RequestMapping(value = "/admin/cteateUser")
public class CreateUserController {

	@Autowired
	private AccountService accountService;
	@RequiresRoles("admin")
	@RequestMapping(method = RequestMethod.GET)
	public String registerForm( Model model) {
		List<UserDto> userdto=accountService.getUserByUpAdminUserlist();
		model.addAttribute("userdto", userdto);
		return "account/createUserFrom";
	}
	@RequiresRoles("admin")
	@RequestMapping(method = RequestMethod.POST)
	public String register(@Valid User user, RedirectAttributes redirectAttributes) {
		user.setIsdelete(0);
		accountService.createUser(user); 
		redirectAttributes.addFlashAttribute("message", "添加用户" + user.getLoginName() + "成功");
		return "redirect:/admin/user";
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
}
