/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.reportform.signinfo.entity;

import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 扫码中奖统计Entity
 * @author tanghaobo
 * @version 2017-06-23
 */
public class SignInfo extends DataEntity<SignInfo> {
	
	private static final long serialVersionUID = 1L;
	private String winningWay;		// 中奖方式 1扫码中奖 2转盘中奖 3商城兑换
	private String prizeName;		// 奖品名称
	private Date winningTime;		// 中奖时间
	private String prizeType;		// 奖品类型 1积分奖品  2卡券奖品 3物流奖品 4卡券物流奖品
	private String activityId;		// 活动ID
	private String activityName;		// 活动名称
	private String userName;		// 经销商名称
	private String activityAddress;		// 活动地区
	private String number;		// 统计
	
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
	public String getWinningWay() {
		return winningWay;
	}
	public void setWinningWay(String winningWay) {
		this.winningWay = winningWay;
	}
	public String getPrizeName() {
		return prizeName;
	}
	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}
	public Date getWinningTime() {
		return winningTime;
	}
	public void setWinningTime(Date winningTime) {
		this.winningTime = winningTime;
	}
	public String getPrizeType() {
		return prizeType;
	}
	public void setPrizeType(String prizeType) {
		this.prizeType = prizeType;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getActivityAddress() {
		return activityAddress;
	}
	public void setActivityAddress(String activityAddress) {
		this.activityAddress = activityAddress;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	
}