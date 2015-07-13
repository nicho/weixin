/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.gamewin.weixin.web.account;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gamewin.weixin.entity.ActivationCode;
import com.gamewin.weixin.entity.ApplyThreeAdmin;
import com.gamewin.weixin.entity.User;
import com.gamewin.weixin.model.UserDto;
import com.gamewin.weixin.service.account.AccountService;
import com.gamewin.weixin.service.task.ActivationCodeService;

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
	@Autowired
	private ActivationCodeService activationCodeService;

	@RequestMapping(method = RequestMethod.GET)
	public String registerForm(Model model) {
		List<UserDto> userdto = accountService.getUserByUpAdminUserlist();
		model.addAttribute("userdto", userdto);
		return "account/register";
	}

	@RequestMapping(value = "code/{id}")
	public String registerFormByCode(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {

		List<ActivationCode> codeList = activationCodeService.getActivationCodeByCode(id + "");
		if (codeList != null && codeList.size() > 0) {
			ActivationCode code = codeList.get(0);
			if ("CREATEUSER".equals(code.getActivationCodeType())) {
				model.addAttribute("activationCode", id);
				return "account/registerByUserCode";
			}else if ("CREATETHREEADMIN".equals(code.getActivationCodeType())) {
				model.addAttribute("activationCode", id);
				return "account/registerByAdminCode";
			}else
			{
				redirectAttributes.addFlashAttribute("message", "激活码类型异常!");
				return "redirect:/register";
			}
		} else {
			redirectAttributes.addFlashAttribute("message", "激活码不存在!");
			return "redirect:/register";
		}

	}

	@RequestMapping(value = "registerByUserCode",method = RequestMethod.POST)
	public String registerByUserCode(@Valid User user, RedirectAttributes redirectAttributes, ServletRequest request) {
		user.setIsdelete(0);

		String activationCode = request.getParameter("activationCode");
		if (!StringUtils.isEmpty(activationCode)) {
			List<ActivationCode> codeList = activationCodeService.getActivationCodeByCode(activationCode);
			if (codeList != null && codeList.size() > 0) {
				ActivationCode code = codeList.get(0);
				if (!"CREATEUSER".equals(code.getActivationCodeType())) {
					redirectAttributes.addFlashAttribute("message", "注册失败,激活码类型不符");
					return "redirect:/register";
				}
				
				if ("disabled".equals(code.getStatus())) {
					user.setUpuser(code.getUser());
					user.setStatus("Audit");
					accountService.registerUser(user);
					
					redirectAttributes.addFlashAttribute("message", "激活码已失效,注册申请已提交,待审核.");
					return "redirect:/login";
				} else{
					user.setUpuser(code.getUser());
					user.setStatus("enabled");
					accountService.registerUser(user);

					redirectAttributes.addFlashAttribute("message", "注册成功.");
					return "redirect:/login";
				}
			} else {
				redirectAttributes.addFlashAttribute("message", "注册失败,激活码不存在!");
				return "redirect:/register";
			}

		} else {
			redirectAttributes.addFlashAttribute("message", "注册失败,激活码不存在!");
			return "redirect:/register";
		}

	}
	@RequestMapping(value = "registerByAdminCode",method = RequestMethod.POST)
	public String registerByAdminCode(@Valid User user, RedirectAttributes redirectAttributes, ServletRequest request,String userName,String ssnumber,String address,String description) {
		user.setIsdelete(0);

		String activationCode = request.getParameter("activationCode");
		if (!StringUtils.isEmpty(activationCode)) {
			List<ActivationCode> codeList = activationCodeService.getActivationCodeByCode(activationCode);
			if (codeList != null && codeList.size() > 0) {
				ActivationCode code = codeList.get(0);
				if (!"CREATETHREEADMIN".equals(code.getActivationCodeType())) {
					redirectAttributes.addFlashAttribute("message", "注册失败,激活码类型不符");
					return "redirect:/register";
				}
				
				if ("disabled".equals(code.getStatus())) {
					user.setUpuser(code.getUser());
					user.setStatus("Audit");
					accountService.registerAdminUser(user);
					
					ApplyThreeAdmin newApplyThreeAdmin=new ApplyThreeAdmin();
					newApplyThreeAdmin.setUser(user);
					newApplyThreeAdmin.setIsdelete(0);
					newApplyThreeAdmin.setStatus("submit");
					newApplyThreeAdmin.setUpuser(code.getUser());
					newApplyThreeAdmin.setUserName(userName);
					newApplyThreeAdmin.setSsnumber(ssnumber);
					newApplyThreeAdmin.setAddress(address);
					newApplyThreeAdmin.setDescription(description);
					newApplyThreeAdmin.setCreateDate(new Date());
					newApplyThreeAdmin.setApprovalOpinion("激活码'"+activationCode+"'激活,激活码已失效待审核");
				 
	 
					redirectAttributes.addFlashAttribute("message", "激活码已失效,注册申请已提交,待审核.");
					return "redirect:/login";
				} else{
					user.setUpuser(code.getUser());
					user.setStatus("enabled");
					accountService.registerAdminUser(user);
					
					ApplyThreeAdmin newApplyThreeAdmin=new ApplyThreeAdmin();
					newApplyThreeAdmin.setUser(user);
					newApplyThreeAdmin.setIsdelete(0);
					newApplyThreeAdmin.setStatus("pass");
					newApplyThreeAdmin.setUpuser(code.getUser());
					newApplyThreeAdmin.setUserName(userName);
					newApplyThreeAdmin.setSsnumber(ssnumber);
					newApplyThreeAdmin.setAddress(address);
					newApplyThreeAdmin.setDescription(description);
					newApplyThreeAdmin.setCreateDate(new Date());
					newApplyThreeAdmin.setApprovalOpinion("激活码'"+activationCode+"'激活成功");
					 
					redirectAttributes.addFlashAttribute("message", "注册成功.您已成为三级分销商");
					return "redirect:/login";
				}
			} else {
				redirectAttributes.addFlashAttribute("message", "注册失败,激活码不存在!");
				return "redirect:/register";
			}

		} else {
			redirectAttributes.addFlashAttribute("message", "注册失败,激活码不存在!");
			return "redirect:/register";
		}

	}
	@RequestMapping(method = RequestMethod.POST)
	public String register(@Valid User user, RedirectAttributes redirectAttributes, ServletRequest request) {
		user.setIsdelete(0);

		String upuserId = request.getParameter("upuserId");
		if (!StringUtils.isEmpty(upuserId)) {
			User upuser = accountService.getUser(Long.parseLong(upuserId));
			if (upuser != null && ("TwoAdmin".equals(upuser.getRoles()) || "ThreeAdmin".equals(upuser.getRoles()))) {
				String activationCode = request.getParameter("activationCode");
				if (!StringUtils.isEmpty(activationCode)) {
					List<ActivationCode> codeList = activationCodeService.getActivationCodeByCode(activationCode);
					if (codeList != null && codeList.size() > 0) {
						ActivationCode code = codeList.get(0);
						if ("disabled".equals(code.getStatus())) {
							redirectAttributes.addFlashAttribute("message", "注册失败,激活码已失效");
							return "redirect:/register";
						}
						if (!"N".equals(code.getStatus())) {
							redirectAttributes.addFlashAttribute("message", "注册失败,激活码已使用");
							return "redirect:/register";
						} else if (!upuserId.equals(code.getUser().getId())) {
							redirectAttributes.addFlashAttribute("message", "注册失败,激活码和上级分销商不匹配");
							return "redirect:/register";
						} else if (!"CREATEUSER".equals(code.getActivationCodeType())) {
							redirectAttributes.addFlashAttribute("message", "注册失败,激活码类型不符");
							return "redirect:/register";
						} else {
							user.setUpuser(upuser);
							user.setStatus("enabled");
							accountService.registerUser(user);

							/*
							 * code.setStatus("Y"); code.setActivationDate(new
							 * Date()); code.setActivationUser(user);
							 * activationCodeService.saveActivationCode(code);
							 */

							redirectAttributes.addFlashAttribute("message", "注册成功.");
							return "redirect:/login";
						}

					} else {
						redirectAttributes.addFlashAttribute("message", "注册失败,激活码不存在");
						return "redirect:/register";
					}

				} else {
					user.setUpuser(upuser);
					user.setStatus("Audit");
					accountService.registerUser(user);
					redirectAttributes.addFlashAttribute("message", "注册申请已提交,待审核.");
					return "redirect:/login";
				}

			} else {
				user.setStatus("Audit");
				accountService.registerUser(user);
				redirectAttributes.addFlashAttribute("message", "注册申请已提交,待审核.");
				return "redirect:/login";
			}
		} else {
			user.setStatus("Audit");
			accountService.registerUser(user);
			redirectAttributes.addFlashAttribute("message", "注册申请已提交,待审核.");
			return "redirect:/login";
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
		User upuser = accountService.findUserByLoginName(upuserName);
		if (upuser != null && ("TwoAdmin".equals(upuser.getRoles()) || "ThreeAdmin".equals(upuser.getRoles()))) {
			return "true";
		} else {
			return "false";
		}

	}
}
