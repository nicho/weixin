/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.gamewin.weixin.service.game;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gamewin.weixin.entity.Game;
import com.gamewin.weixin.entity.GameCode;
import com.gamewin.weixin.model.GameCodeDto;
import com.gamewin.weixin.mybatis.GameCodeMybatisDao;
import com.gamewin.weixin.mybatis.GameMybatisDao;
import com.gamewin.weixin.repository.GameCodeDao;
import com.gamewin.weixin.repository.GameDao;
import com.github.pagehelper.PageHelper;

// Spring Bean的标识.
@Component
// 类中所有public函数都纳入事务管理的标识.
@Transactional
public class GameService {
	@Autowired
	private GameDao gameDao;
	@Autowired
	private GameCodeDao gameCodeDao;
	@Autowired
	private GameMybatisDao gameMybatisDao;
	@Autowired
	private GameCodeMybatisDao gameCodeMybatisDao;

	public Long updateMyGameCode(Long id, String wxuserId) {
		return gameCodeMybatisDao.updateMyGameCode(id, wxuserId);
	}

	public GameCode getMyGameCode() {
		return gameCodeMybatisDao.getMyGameCode();
	}

	public Long getMyGameCodeCount(Long gameid, String wxuserId) {
		return gameCodeMybatisDao.getMyGameCodeCount(gameid, wxuserId);
	}

	public Game findGameByNameOrXuhao(String key) {
		return gameMybatisDao.finGameByNameOrXuhao(key);
	}

	public Game getGame(Long id) {
		return gameDao.findOne(id);
	}

	public GameCode getGameCode(Long id) {
		return gameCodeDao.findOne(id);
	}

	public void saveGame(Game entity) {
		gameDao.save(entity);
	}

	public void saveGameCode(GameCode entity) {
		gameCodeDao.save(entity);
	}

	public List<Game> getGamelist(Map<String, Object> searchParams, int pageNumber, int pageSize, String sortType) {
		PageHelper.startPage(pageNumber, pageSize);
		return gameMybatisDao.getGamelist();
	}

	public List<GameCode> getGameCodelist(Long id, Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageHelper.startPage(pageNumber, pageSize);
		return gameCodeMybatisDao.getGameCodelistByGame(id);
	}

	public List<Game> getEffectiveGamelist() {
		return gameMybatisDao.getEffectiveGamelist();
	}

	public GameCodeDto getGameCode(String gamekey, String wxuserId) {
		// gameCodeMybatisDao.getGameCode(wxuserId);
		return null;
	}
}
