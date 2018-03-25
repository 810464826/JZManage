/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.prize.winningdelivepy.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 奖品派送Entity
 * @author tanghaobo
 * @version 2017-03-31
 */
public class WinningDelivery extends DataEntity<WinningDelivery> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 呢称
	private String winningWay;		// 中奖方式
	private String prizeName;		// 奖品名称
	private String expressNumber;		// 快递单号
	private String express;		// 快递公司
	private String collectAddress;		// 收获地址
	private String collectUser;		// 收货人
	private Long collectPhone;		// 收货电话
	private Date deliveryTime;		// 发货时间
	private Date winningTime;		// 中奖时间
	private String distributionStatus;		// 配送状态
	private String openid;		// openid

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

	public WinningDelivery() {
		super();
	}

	public WinningDelivery(String id){
		super(id);
	}

	@Length(min=1, max=255, message="呢称长度必须介于 1 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=255, message="中奖方式长度必须介于 1 和 255 之间")
	public String getWinningWay() {
		return winningWay;
	}

	public void setWinningWay(String winningWay) {
		this.winningWay = winningWay;
	}
	
	@Length(min=1, max=255, message="奖品名称长度必须介于 1 和 255 之间")
	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}
	
	@Length(min=1, max=64, message="快递单号长度必须介于 1 和 64 之间")
	public String getExpressNumber() {
		return expressNumber;
	}

	public void setExpressNumber(String expressNumber) {
		this.expressNumber = expressNumber;
	}
	
	@Length(min=1, max=64, message="快递公司长度必须介于 1 和 64 之间")
	public String getExpress() {
		return express;
	}

	public void setExpress(String express) {
		this.express = express;
	}
	
	@Length(min=1, max=255, message="收获地址长度必须介于 1 和 255 之间")
	public String getCollectAddress() {
		return collectAddress;
	}

	public void setCollectAddress(String collectAddress) {
		this.collectAddress = collectAddress;
	}
	
	@Length(min=1, max=255, message="收货人长度必须介于 1 和 255 之间")
	public String getCollectUser() {
		return collectUser;
	}

	public void setCollectUser(String collectUser) {
		this.collectUser = collectUser;
	}
	
	@NotNull(message="收货电话不能为空")
	public Long getCollectPhone() {
		return collectPhone;
	}

	public void setCollectPhone(Long collectPhone) {
		this.collectPhone = collectPhone;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="发货时间不能为空")
	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="中奖时间不能为空")
	public Date getWinningTime() {
		return winningTime;
	}

	public void setWinningTime(Date winningTime) {
		this.winningTime = winningTime;
	}
	
	@Length(min=1, max=255, message="配送状态长度必须介于 1 和 255 之间")
	public String getDistributionStatus() {
		return distributionStatus;
	}

	public void setDistributionStatus(String distributionStatus) {
		this.distributionStatus = distributionStatus;
	}
	
	@Length(min=1, max=64, message="openid长度必须介于 1 和 64 之间")
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
}