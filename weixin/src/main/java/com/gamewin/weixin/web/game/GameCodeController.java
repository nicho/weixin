/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.gamewin.weixin.web.game;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;

import com.gamewin.weixin.entity.Game;
import com.gamewin.weixin.entity.GameCode;
import com.gamewin.weixin.service.game.GameService;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;

@Controller
@RequestMapping(value = "/gameCode")
public class GameCodeController {

	private static final String PAGE_SIZE = "10";

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("title", "标题");
	}

	@Autowired
	private GameService gameCodeService;

	@RequiresRoles("admin")
	@RequestMapping(value = "showGame/{id}",method = RequestMethod.GET)
	public String list(@PathVariable("id") Long id,@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model, ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");

		List<GameCode> gameCodes = gameCodeService.getGameCodelist(id,searchParams, pageNumber, pageSize, sortType);
		PageInfo<GameCode> page = new PageInfo<GameCode>(gameCodes);
		model.addAttribute("page", page);
		model.addAttribute("gameCodes", gameCodes);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "gameCode/gameCodeList";
	}

	@RequiresRoles("admin")
	@RequestMapping(value = "create/{id}", method = RequestMethod.GET)
	public String createForm(@PathVariable("id") Long id,Model model) {
		model.addAttribute("gameCode", new Game());
		model.addAttribute("action", "create");
		model.addAttribute("gameId", id);
		return "gameCode/gameCodeForm";
	}

	@RequiresRoles("admin")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid String codes,@Valid Long gameId, RedirectAttributes redirectAttributes, ServletRequest request) { 
		if(!StringUtils.isEmpty(codes))
		{
			String [] codeArr=codes.split(",");
			if(codeArr.length>0)
			{
				Game game=new Game(gameId);
				for (int i = 0; i < codeArr.length; i++) {
					GameCode newcode=new GameCode();
					newcode.setStatus("Y");
					newcode.setIsdelete(0);
					newcode.setCode(codeArr[i]);
					newcode.setGame(game);
					gameCodeService.saveGameCode(newcode);
				}
				redirectAttributes.addFlashAttribute("message", "创建游戏码成功,共"+codeArr.length+"个");
			}
		}
		 
		return "redirect:/gameCode/";
	}

 

	@RequiresRoles("admin")
	@RequestMapping(value = "disabled/{id}")
	public String disabled(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		Game gameCode = gameCodeService.getGame(id);
		gameCode.setStatus("N");
		gameCodeService.saveGame(gameCode);
		redirectAttributes.addFlashAttribute("message", "失效游戏码'" + gameCode.getGameName()+ "'成功");
		return "redirect:/gameCode/";
	}

	 
}
