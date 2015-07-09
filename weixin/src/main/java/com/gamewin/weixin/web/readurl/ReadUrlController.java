/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.gamewin.weixin.web.readurl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gamewin.weixin.entity.HistoryUrl;
import com.gamewin.weixin.entity.ManageQRcode;
import com.gamewin.weixin.service.account.AccountService;
import com.gamewin.weixin.service.task.ManageQRcodeService;
import com.gamewin.weixin.service.task.ManageTaskService;
import com.gamewin.weixin.service.weixinUser.WeiXinUserService;

@Controller
@RequestMapping(value = "/readurl")
public class ReadUrlController {

	@Autowired
	private WeiXinUserService weiXinUserService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private ManageQRcodeService manageQRcodeService;
	@Autowired
	private ManageTaskService manageTaskService;
	@RequestMapping(value = "redirectUrl", method = { RequestMethod.GET, RequestMethod.POST })
	public String readUrl(HttpServletRequest request, HttpServletResponse response, Model model,String taskid,String qrcodeId) {
		String userIp=getIpAddr(request); 
		if(!StringUtils.isEmpty(qrcodeId))
		{
			ManageQRcode manageQRcode=manageQRcodeService.getManageQRcode(Long.parseLong(qrcodeId));
			if(manageQRcode.getTask().getId().equals(Long.parseLong(taskid)))
			{
				Integer count=manageQRcodeService.selectHistoryUrlByuserIpAndqrcodeId(userIp, manageQRcode.getId());
				if(count==0)
				{
					if("Y".equals(manageQRcode.getQrState()))
					{
						manageQRcode.setQrSubscribeCount(manageQRcode.getQrSubscribeCount()+1);
						manageQRcode.setQrSubscribeAdminCount(manageQRcode.getQrSubscribeAdminCount()+1);
						manageQRcodeService.saveManageQRcode(manageQRcode);
					}else
					{
						manageQRcode.setQrSubscribeAdminCount(manageQRcode.getQrSubscribeAdminCount()+1);
						manageQRcodeService.saveManageQRcode(manageQRcode);
					} 
					 
				}
				HistoryUrl entity=new HistoryUrl();
				entity.setCreateDate(new Date());
				entity.setQrcodeId(manageQRcode.getId());
				entity.setTaskId(manageQRcode.getTask().getId());
				entity.setUserIp(userIp);
				manageQRcodeService.saveHistoryUrl(entity);
				
				model.addAttribute("redirectUrl",manageQRcode.getQrUrl());
				return "readUrl/redirectUrl";
			}
			
		}
		return null; 
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
