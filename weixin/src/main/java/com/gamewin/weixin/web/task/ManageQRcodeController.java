/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.gamewin.weixin.web.task;

import java.io.File;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.utils.Clock;
import org.springside.modules.web.Servlets;

import com.gamewin.weixin.entity.ManageQRcode;
import com.gamewin.weixin.entity.ManageTask;
import com.gamewin.weixin.entity.User;
import com.gamewin.weixin.model.QRcodeByHistory;
import com.gamewin.weixin.service.account.ShiroDbRealm.ShiroUser;
import com.gamewin.weixin.service.task.ManageQRcodeService;
import com.gamewin.weixin.service.task.ManageTaskService;
import com.gamewin.weixin.util.MobileContants;
import com.gamewin.weixin.util.MobileHttpClient;
import com.gamewin.weixin.web.util.QRCodeUtil;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;

/**
 * ManageQRcode管理的Controller, 使用Restful风格的Urls:
 * 
 * List page : GET /manageQRcode/ Create page : GET /manageQRcode/create Create
 * action : POST /manageQRcode/create Update page : GET /manageQRcode/update/{id}
 * Update action : POST /manageQRcode/update Delete action : GET
 * /manageQRcode/delete/{id}
 * 
 * @author calvin
 */
@Controller
@RequestMapping(value = "/manageQRcode")
public class ManageQRcodeController {
	 
	private static final String PAGE_SIZE = "10";
	private Clock clock = Clock.DEFAULT;
	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("title", "标题");
	}

	@Autowired
	private ManageQRcodeService manageQRcodeService;
	@Autowired
	private ManageTaskService manageTaskService; 
	
	
	@RequestMapping(value = "showMyTaskQRcodeHistroy/{id}",method = RequestMethod.GET)
	public String showMyTaskQRcodeHistroy(@PathVariable("id") Long id,@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		 
		List<QRcodeByHistory> qRcodeByHistory =null;
		ManageQRcode code=manageQRcodeService.getManageQRcode(id);
		if("WeixinGd".equals(code.getQrcodeType()) || "WeixinLs".equals(code.getQrcodeType()))
		{
			qRcodeByHistory=manageQRcodeService.getUserQRcodeByHistoryWeixin(id,getCurrentUserId(), searchParams, pageNumber, pageSize,sortType);			
		}
		else if("WeixinApk".equals(code.getQrcodeType()) || "WeixinOther".equals(code.getQrcodeType()))
		{
			qRcodeByHistory=manageQRcodeService.getUserQRcodeByHistoryUrl(id,getCurrentUserId(), searchParams, pageNumber, pageSize,sortType);			
		} 
		PageInfo<QRcodeByHistory> page = new PageInfo<QRcodeByHistory>(qRcodeByHistory);
	  	model.addAttribute("page", page);
	  	model.addAttribute("qRcodeByHistory", qRcodeByHistory); 
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		return "myTask/showMyTaskQRcodeHistroyList";
	}
	
	@RequestMapping(value = "showMyTaskQRcodeHistroyUp/{id}",method = RequestMethod.GET)
	public String showMyTaskQRcodeHistroyUp(@PathVariable("id") Long id,@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		 
		List<QRcodeByHistory> qRcodeByHistory =null;
		ManageQRcode code=manageQRcodeService.getManageQRcode(id);
		if("WeixinGd".equals(code.getQrcodeType()) || "WeixinLs".equals(code.getQrcodeType()))
		{
			qRcodeByHistory=manageQRcodeService.getUserQRcodeByHistoryWeixinUp(id,getCurrentUserId(), searchParams, pageNumber, pageSize,sortType);			
		}
		else if("WeixinApk".equals(code.getQrcodeType()) || "WeixinOther".equals(code.getQrcodeType()))
		{
			qRcodeByHistory=manageQRcodeService.getUserQRcodeByHistoryUrlUp(id,getCurrentUserId(), searchParams, pageNumber, pageSize,sortType);			
		} 
		PageInfo<QRcodeByHistory> page = new PageInfo<QRcodeByHistory>(qRcodeByHistory);
	  	model.addAttribute("page", page);
	  	model.addAttribute("qRcodeByHistory", qRcodeByHistory); 
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		return "myTask/showMyTaskQRcodeHistroyList";
	}
	
	@RequiresRoles(value = { "admin"}, logical = Logical.OR)
	@RequestMapping(value = "showTaskQRcode/{id}",method = RequestMethod.GET)
	public String showTaskQRcode(@PathVariable("id") Long id,@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
 

		List<ManageQRcode> manageQRcodes = manageQRcodeService.getUserManageQRcodeByTaskId(id, searchParams, pageNumber, pageSize,sortType);
		PageInfo<ManageQRcode> page = new PageInfo<ManageQRcode>(manageQRcodes);
	  	model.addAttribute("page", page);
	  	model.addAttribute("manageQRcodes", manageQRcodes); 
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "manageQRcode/manageQRcodeList";
	}

	@RequestMapping(value = "showMyTaskQRcode/{id}",method = RequestMethod.GET)
	public String showMyTaskQRcode(@PathVariable("id") Long id,@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		User user = new User(getCurrentUserId()); 
		//查询当前任务的类型
		ManageTask manageTask=manageTaskService.getManageTask(id);
		if("Y".equals(manageTask.getState()))
		{
			if("Y".equals(manageTask.getWeixinGd()))
			{
				Integer weixinGdCount=manageQRcodeService.getUserManageQRcodeByTaskIdAndQrType(id, "WeixinGd",shiroUser.id);
				if(weixinGdCount==0)
				{ 
					ManageQRcode newManageQRcode = new ManageQRcode();
					newManageQRcode.setUser(user);
					newManageQRcode.setIsdelete(0);
					newManageQRcode.setQrState("Y");
					newManageQRcode.setQrSubscribeCount(0);
					newManageQRcode.setQrSubscribeAdminCount(0);
					newManageQRcode.setTask(manageTask);
					newManageQRcode.setTitle(manageTask.getTitle()+"-微信固定-"+shiroUser.getName());
					newManageQRcode.setQrcodeType("WeixinGd");
					newManageQRcode.setCreateDate(new Date()); 
					manageQRcodeService.saveManageQRcode(newManageQRcode);
				}
			}
		    if("Y".equals(manageTask.getWeixinLs()))
			{
				Integer weixinGdCount=manageQRcodeService.getUserManageQRcodeByTaskIdAndQrType(id, "WeixinLs",shiroUser.id);
				if(weixinGdCount==0)
				{ 
					ManageQRcode newManageQRcode = new ManageQRcode();
					newManageQRcode.setUser(user);
					newManageQRcode.setIsdelete(0);
					newManageQRcode.setQrState("Y");
					newManageQRcode.setQrSubscribeCount(0);
					newManageQRcode.setQrSubscribeAdminCount(0);
					newManageQRcode.setTask(manageTask);
					newManageQRcode.setTitle(manageTask.getTitle()+"-微信临时-"+shiroUser.getName());
					newManageQRcode.setQrcodeType("WeixinLs");
					newManageQRcode.setCreateDate(new Date()); 
					manageQRcodeService.saveManageQRcode(newManageQRcode);
				}
			}
			if("Y".equals(manageTask.getWeixinApk()))
			{
				Integer weixinGdCount=manageQRcodeService.getUserManageQRcodeByTaskIdAndQrType(id, "WeixinApk",shiroUser.id);
				if(weixinGdCount==0)
				{ 
					ManageQRcode newManageQRcode = new ManageQRcode();
					newManageQRcode.setUser(user);
					newManageQRcode.setIsdelete(0);
					newManageQRcode.setQrState("Y");
					newManageQRcode.setQrSubscribeCount(0);
					newManageQRcode.setQrSubscribeAdminCount(0);
					newManageQRcode.setTask(manageTask);
					newManageQRcode.setTitle(manageTask.getTitle()+"-应用APK-"+shiroUser.getName());
					newManageQRcode.setQrcodeType("WeixinApk");
					newManageQRcode.setQrUrl(manageTask.getApkUrl());
					newManageQRcode.setCreateDate(new Date()); 
					manageQRcodeService.saveManageQRcode(newManageQRcode);
				}
			}
			if("Y".equals(manageTask.getWeixinOther()))
			{
				Integer weixinGdCount=manageQRcodeService.getUserManageQRcodeByTaskIdAndQrType(id, "WeixinOther",shiroUser.id);
				if(weixinGdCount==0)
				{ 
					ManageQRcode newManageQRcode = new ManageQRcode();
					newManageQRcode.setUser(user);
					newManageQRcode.setIsdelete(0);
					newManageQRcode.setQrState("Y");
					newManageQRcode.setQrSubscribeCount(0);
					newManageQRcode.setQrSubscribeAdminCount(0);
					newManageQRcode.setTask(manageTask);
					newManageQRcode.setTitle(manageTask.getTitle()+"-外部跳转-"+shiroUser.getName());
					newManageQRcode.setQrcodeType("WeixinOther");
					newManageQRcode.setQrUrl(manageTask.getOtherUrl());
					newManageQRcode.setCreateDate(new Date()); 
					manageQRcodeService.saveManageQRcode(newManageQRcode);
				}
			}
		}
		List<ManageQRcode> manageQRcodes = manageQRcodeService.getMyUserManageQRcodeByTaskId(id,shiroUser.id, searchParams, pageNumber, pageSize,sortType);
		
		
		PageInfo<ManageQRcode> page = new PageInfo<ManageQRcode>(manageQRcodes);
	  	model.addAttribute("page", page);
	  	model.addAttribute("manageQRcodes", manageQRcodes); 
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "manageQRcode/manageQRcodeList";
	} 
	@RequiresRoles(value = { "admin", "TwoAdmin", "ThreeAdmin" }, logical = Logical.OR)
	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		ManageQRcode entity = manageQRcodeService.getManageQRcode(id);
		entity.setIsdelete(1);
		entity.setQrState("N");
		manageQRcodeService.saveManageQRcode(entity);
		redirectAttributes.addFlashAttribute("message", "删除二维码成功");
		return "redirect:/manageQRcode/showTaskQRcode/"+entity.getTask().getId();
	}
	@RequiresRoles(value = { "admin", "TwoAdmin", "ThreeAdmin" }, logical = Logical.OR)
	@RequestMapping(value = "disabled/{id}")
	public String disabled(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		ManageQRcode entity = manageQRcodeService.getManageQRcode(id);
		entity.setQrState("N");
		manageQRcodeService.saveManageQRcode(entity);
		redirectAttributes.addFlashAttribute("message", "失效二维码成功");
		return "redirect:/manageQRcode/showTaskQRcode/"+entity.getTask().getId();
	}
	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2
	 * Preparable二次部分绑定的效果,先根据form的id从数据库查出ManageQRcode对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getManageQRcode(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("manageQRcode", manageQRcodeService.getManageQRcode(id));
		}
	}

	/**
	 * 取出Shiro中的当前用户Id.
	 */
	private Long getCurrentUserId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.id;
	}
	private void createQrCode(ManageQRcode entity,ServletRequest request,String filePath)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String nowDate=sdf.format(clock.getCurrentDate());
		
		String imageUrl = entity.getTask().getId() + "-" + entity.getQrcodeType() + "-" + entity.getId() + ".jpg";
		String url =  MobileContants.IMAGEURL+nowDate+"\\" + imageUrl; // 
		
		File file =new File(filePath+nowDate);    
		// 如果文件夹不存在则创建
		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
		} 
		try {
			if ("WeixinGd".equals(entity.getQrcodeType())) {
				String AccessToken = manageTaskService.getAccessToken(); 
				String ticket = MobileHttpClient.getJsapi_ticket_WeixinGd(AccessToken, entity.getId());
				MobileHttpClient.getticketImage(URLEncoder.encode(ticket, "UTF-8"), url);

			} else if ("WeixinLs".equals(entity.getQrcodeType())) {
				String AccessToken = manageTaskService.getAccessToken(); 
				String ticket = MobileHttpClient.getJsapi_ticket_WeixinLs(AccessToken, entity.getId());
				MobileHttpClient.getticketImage(URLEncoder.encode(ticket, "UTF-8"), url);
			} else if ("WeixinApk".equals(entity.getQrcodeType())) {
				QRCodeUtil.createEncode(MobileContants.YM+"/readurl/redirectUrl?taskid="+entity.getTask().getId()+"&qrcodeId="+entity.getId(), entity.getUser().getName(), filePath+nowDate, imageUrl);
			} else if ("WeixinOther".equals(entity.getQrcodeType())) {
				QRCodeUtil.createEncode(MobileContants.YM+"/readurl/redirectUrl?taskid="+entity.getTask().getId()+"&qrcodeId="+entity.getId(), entity.getUser().getName(), filePath+nowDate, imageUrl);
			}
		} catch (Exception e) { 
			e.printStackTrace();
		}
		
		entity.setImageUrl(nowDate+"\\"+imageUrl);
		manageQRcodeService.saveManageQRcode(entity);
	}
	@RequestMapping(value = "viewManageQRcode/{id}")
	public String viewManageQRcode(@PathVariable("id") Long id, RedirectAttributes redirectAttributes,Model model,ServletRequest request) {
		ManageQRcode entity = manageQRcodeService.getManageQRcode(id);
 
		if("Y".equals(entity.getQrState()))
		{  
			
			String filePath = MobileContants.IMAGEURL+"\\" ;
			if(!StringUtils.isEmpty(entity.getImageUrl()))
			{
				File file=new File(filePath+entity.getImageUrl());    
				if(!file.exists())
				{
					//原文件不存在,重新生成
					createQrCode(entity, request, filePath);
				}
			}else
			{ 
				createQrCode(entity, request, filePath);
			}
			
			
		} 
		model.addAttribute("HttpImageUrl",MobileContants.HTTPIMAGEURL);
		model.addAttribute("entity",entity);
		return "myTask/manageQRcodeView";
	}
}
