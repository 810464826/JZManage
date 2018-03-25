/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.prize.turntableprize.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 转盘中奖记录Entity
 * @author tanghaobo
 * @version 2017-07-05
 */
public class TurntablePrize extends DataEntity<TurntablePrize> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 呢称
	private String winningWay;		// 中奖方式 1扫码中奖 2转盘中奖 3商城兑换
	private String prizeName;		// 奖品名称
	private Date winningTime;		// 中奖时间
	private String openid;		// openid
	private String prizeType;		// 奖品类型 1积分奖品  2卡券奖品 3物流奖品 4卡券物流奖品
	private String number;
	
	private Date startTime;
	private Date endTime;
	
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public TurntablePrize() {
		super();
	}

	public TurntablePrize(String id){
		super(id);
	}

	@Length(min=0, max=255, message="呢称长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="中奖方式 1扫码中奖 2转盘中奖 3商城兑换长度必须介于 0 和 255 之间")
	public String getWinningWay() {
		return winningWay;
	}

	public void setWinningWay(String winningWay) {
		this.winningWay = winningWay;
	}
	
	@Length(min=0, max=255, message="奖品名称长度必须介于 0 和 255 之间")
	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getWinningTime() {
		return winningTime;
	}

	public void setWinningTime(Date winningTime) {
		this.winningTime = winningTime;
	}
	
	
	@Length(min=0, max=64, message="openid长度必须介于 0 和 64 之间")
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	
	@Length(min=0, max=255, message="奖品类型 1积分奖品  2卡券奖品 3物流奖品 4卡券物流奖品长度必须介于 0 和 255 之间")
	public String getPrizeType() {
		return prizeType;
	}

	public void setPrizeType(String prizeType) {
		this.prizeType = prizeType;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	
}