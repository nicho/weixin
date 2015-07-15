/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.gamewin.weixin.web.game;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

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
import org.springside.modules.web.Servlets;

import com.gamewin.weixin.entity.Game;
import com.gamewin.weixin.service.game.GameService;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;

@Controller
@RequestMapping(value = "/game")
public class GameController {

	private static final String PAGE_SIZE = "10";

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("title", "标题");
	}

	@Autowired
	private GameService gameService;

	@RequiresRoles("admin")
	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model, ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");

		List<Game> games = gameService.getGamelist(searchParams, pageNumber, pageSize, sortType);
		PageInfo<Game> page = new PageInfo<Game>(games);
		model.addAttribute("page", page);
		model.addAttribute("games", games);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "game/gameList";
	}

	@RequiresRoles("admin")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("game", new Game());
		model.addAttribute("action", "create");
		return "game/gameForm";
	}

	@RequiresRoles("admin")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid Game newGame, RedirectAttributes redirectAttributes, ServletRequest request) { 
		newGame.setStatus("Y");
		newGame.setCreateDate(new Date());
		newGame.setIsdelete(0);
		gameService.saveGame(newGame);
		redirectAttributes.addFlashAttribute("message", "创建游戏成功");
		return "redirect:/game/";
	}

 

	@RequiresRoles("admin")
	@RequestMapping(value = "disabled/{id}")
	public String disabled(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		Game game = gameService.getGame(id);
		game.setStatus("N");
		gameService.saveGame(game);
		redirectAttributes.addFlashAttribute("message", "失效游戏'" + game.getGameName()+ "'成功");
		return "redirect:/game/";
	}

	
	@RequiresRoles("admin")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("task", gameService.getGame(id)); 
		model.addAttribute("action", "update");
		return "game/gameForm";
	}
	
	@RequiresRoles("admin")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("game") Game game, RedirectAttributes redirectAttributes,ServletRequest request) {
		game.setStatus("Y");
		game.setCreateDate(new Date());
		game.setIsdelete(0);
		gameService.saveGame(game);
		redirectAttributes.addFlashAttribute("message", "更新游戏" + game.getGameName() + "成功");
		return "redirect:/game/";
	}
}
