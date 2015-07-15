package com.gamewin.weixin.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "wx_game_code")
public class GameCode extends IdEntity {
	private String code;
	private Game game;
	private String wxuserId;
	private String status;
	private Integer isdelete;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	// JPA 基于USER_ID列的多对一关系定义
	@ManyToOne
	@JoinColumn(name = "game_id")
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public String getWxuserId() {
		return wxuserId;
	}

	public void setWxuserId(String wxuserId) {
		this.wxuserId = wxuserId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(Integer isdelete) {
		this.isdelete = isdelete;
	}

}
