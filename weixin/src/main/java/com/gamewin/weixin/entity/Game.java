package com.gamewin.weixin.entity;

import java.beans.Transient;
import java.util.Date;

@Entity
@Table(name = "wx_game")
public class Game extends IdEntity {
	private String gameName;
	private String gameMessage;
	private Long maximum;
	private String xuhao;
	private String status;
	private Integer isdelete;
	private Integer totalCount;
	private Integer postedCount;
	private Integer surplusCount;
	private Date createDate;
	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

 
	public String getXuhao() {
		return xuhao;
	}

	public void setXuhao(String xuhao) {
		this.xuhao = xuhao;
	}

	public Game() {
	}

	public Game(Long id) {
		this.id = id;
	}

	@Transient
	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	@Transient
	public Integer getPostedCount() {
		return postedCount;
	}

	public void setPostedCount(Integer postedCount) {
		this.postedCount = postedCount;
	}

	@Transient
	public Integer getSurplusCount() {
		return surplusCount;
	}

	public void setSurplusCount(Integer surplusCount) {
		this.surplusCount = surplusCount;
	}

	public Integer getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(Integer isdelete) {
		this.isdelete = isdelete;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getGameMessage() {
		return gameMessage;
	}

	public void setGameMessage(String gameMessage) {
		this.gameMessage = gameMessage;
	}

	public Long getMaximum() {
		return maximum;
	}

	public void setMaximum(Long maximum) {
		this.maximum = maximum;
	}

}
