/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.gamewin.weixin.web.readurl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gamewin.weixin.entity.User;
import com.gamewin.weixin.service.account.AccountService;
import com.gamewin.weixin.service.account.ShiroDbRealm.ShiroUser;
import com.gamewin.weixin.util.MobileHttpClient;

@Controller
@RequestMapping(value = "/weixinUser")
public class WeiXinUserController {

	@Autowired
	private AccountService accountService;

	@RequestMapping(value = "bindUserOpenId", method = { RequestMethod.GET, RequestMethod.POST })
	public String bindUserOpenId(HttpServletRequest request, HttpServletResponse response, Model model) {
		String code = request.getParameter("code");
		String grant_type = request.getParameter("grant_type");
		model.addAttribute("code", code);
		model.addAttribute("grant_type", grant_type);
		return "weiXinUser/bindUserOpenIdFrom";
	}

	@RequestMapping(value = "updateBindUserOpenId", method = { RequestMethod.GET, RequestMethod.POST })
	public String updateBindUserOpenId(HttpServletRequest request, HttpServletResponse response, Model model) {
		String code = request.getParameter("code");
		// String grant_type = request.getParameter("grant_type");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		try {
			Subject subject = SecurityUtils.getSubject();
			subject.login(new UsernamePasswordToken(username, password, false));
			if (subject.isAuthenticated()) {
				if (!StringUtils.isEmpty(code)) {

					String openId = MobileHttpClient.getUserOpenIdByCode(code);
					if (!StringUtils.isEmpty(openId)) {
						ShiroUser user = (ShiroUser) subject.getPrincipal();
						User usr = accountService.getUser(user.id);
						usr.setWeixinOpenid(openId);
						accountService.updateUser(usr);
						model.addAttribute("message", "认证成功!");
					} else {
						model.addAttribute("message", "认证失效,请重新认证");
					}

				}
			} else {
				model.addAttribute("message", "用户名/密码错误");
			}
		} catch (Exception e) {
			model.addAttribute("message", "认证失效,请重新认证");
		}
		return "weiXinUser/bindUserOpenIdView";
	}
}
