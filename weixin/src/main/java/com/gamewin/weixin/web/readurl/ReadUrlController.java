/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.gamewin.weixin.web.readurl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gamewin.weixin.service.account.AccountService;
import com.gamewin.weixin.service.weixinUser.WeiXinUserService;

@Controller
@RequestMapping(value = "/readurl")
public class ReadUrlController {

	@Autowired
	private WeiXinUserService weiXinUserService;
	@Autowired
	private AccountService accountService;

	@RequestMapping(value = "redirectUrl", method = { RequestMethod.GET, RequestMethod.POST })
	public String readUrl(HttpServletRequest request, HttpServletResponse response, Model model) {
		String userIp=getIpAddr(request);
		System.out.println(userIp);
		model.addAttribute("redirectUrl", "http://www.baidu.com");
		return "readUrl/redirectUrl";
	}
	
	//获得客户端真实IP地址的方法二：
	public String getIpAddr(HttpServletRequest request) {  
	    String ip = request.getHeader("x-forwarded-for");  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getRemoteAddr();  
	    }  
	    return ip;  
	}  
}
