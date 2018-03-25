/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.prize.turntableraffle.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 转盘抽奖Entity
 * @author tanghaobo
 * @version 2017-03-31
 */
public class TurntableRaffle extends DataEntity<TurntableRaffle> {
	
	private static final long serialVersionUID = 1L;
	private String prizeName;		// 奖品Id
	private Long prizeNumber;		// 奖品总数
	private Long surplusNumber;		// 剩余数量
	private String prizeType;		// 奖品类型
	private Long winningNumber;		// 获奖数量
	private String winningProbability;		// 获奖概率
	private String newProbability;		// 当前获奖概率
	private String wxCardId;		// 微信卡券ID
	private Date createDate;		// 创建时间CREATE_DATE
	private String activityId;		// 创建时间ACTIVITY_ID
	private String name;		// 奖品名称
	private String turntable;
	
	public TurntableRaffle() {
		super();
	}

	public TurntableRaffle(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Length(min=1, max=255, message="奖品名称长度必须介于 1 和 255 之间")
	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}
	
	public Long getPrizeNumber() {
		return prizeNumber;
	}

	public void setPrizeNumber(Long prizeNumber) {
		this.prizeNumber = prizeNumber;
	}
	
	public Long getSurplusNumber() {
		return surplusNumber;
	}

	public void setSurplusNumber(Long surplusNumber) {
		this.surplusNumber = surplusNumber;
	}
	
	@Length(min=0, max=255, message="奖品类型长度必须介于 0 和 255 之间")
	public String getPrizeType() {
		return prizeType;
	}

	public void setPrizeType(String prizeType) {
		this.prizeType = prizeType;
	}
	
	public Long getWinningNumber() {
		return winningNumber;
	}

	public void setWinningNumber(Long winningNumber) {
		this.winningNumber = winningNumber;
	}
	
	@Length(min=0, max=255, message="获奖概率长度必须介于 0 和 255 之间")
	public String getWinningProbability() {
		return winningProbability;
	}

	public void setWinningProbability(String winningProbability) {
		this.winningProbability = winningProbability;
	}
	
	@Length(min=0, max=255, message="微信卡券ID长度必须介于 0 和 255 之间")
	public String getWxCardId() {
		return wxCardId;
	}

	public void setWxCardId(String wxCardId) {
		this.wxCardId = wxCardId;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getNewProbability() {
		return newProbability;
	}

	public void setNewProbability(String newProbability) {
		this.newProbability = newProbability;
	}

	public String getTurntable() {
		return turntable;
	}

	public void setTurntable(String turntable) {
		this.turntable = turntable;
	}
	
	
}