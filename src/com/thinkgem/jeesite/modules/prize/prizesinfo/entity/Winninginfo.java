/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.prize.prizesinfo.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 中奖记录Entity
 * @author tanghaobo
 * @version 2017-06-15
 */
public class Winninginfo extends DataEntity<Winninginfo> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 呢称
	private String winningWay;		// 中奖方式 1扫码中奖 2转盘中奖 3商城兑换
	private String prizeName;		// 奖品名称
	private String expressNumber;		// 快递单号
	private String express;		// 快递公司
	private String collectAddress;		// 收获地址
	private String collectUser;		// 收货人
	private Long collectPhone;		// collect_phone
	private Date deliveryTime;		// 发货时间
	private Date winningTime;		// 中奖时间
	private String distributionStatus;		// 配送状态  1未配送 2已配送 3已领取 4未使用 5已使用 6已赠送 7赠送中  8已删除
	private String openid;		// openid
	private String grainGram;		// 粮食克数
	private String prizeStatus;		// 奖品状态  1已使用  2未使用
	private String prizeType;		// 奖品类型 1积分奖品  2卡券奖品 3物流奖品 4卡券物流奖品
	private String codeId;		// 所扫码的ID
	private String code;		// 微信生成的唯一ID
	private String cardid;		// 卡券ID
	private String addressStatus;		// 地址状态  0 未填写  1已填写
	private String activityId;		
	private String activityUserName;		
	private String activityArea;		
	private Date startTime;
	private Date endTime;
	
	/**
	 * @return the activityId
	 */
	public String getActivityId() {
		return activityId;
	}

	/**
	 * @param activityId the activityId to set
	 */
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	/**
	 * @return the activityUserName
	 */
	public String getActivityUserName() {
		return activityUserName;
	}

	/**
	 * @param activityUserName the activityUserName to set
	 */
	public void setActivityUserName(String activityUserName) {
		this.activityUserName = activityUserName;
	}

	/**
	 * @return the activityArea
	 */
	public String getActivityArea() {
		return activityArea;
	}

	/**
	 * @param activityArea the activityArea to set
	 */
	public void setActivityArea(String activityArea) {
		this.activityArea = activityArea;
	}

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

	public Winninginfo() {
		super();
	}

	public Winninginfo(String id){
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
	
	@Length(min=0, max=64, message="快递单号长度必须介于 0 和 64 之间")
	public String getExpressNumber() {
		return expressNumber;
	}

	public void setExpressNumber(String expressNumber) {
		this.expressNumber = expressNumber;
	}
	
	@Length(min=0, max=64, message="快递公司长度必须介于 0 和 64 之间")
	public String getExpress() {
		return express;
	}

	public void setExpress(String express) {
		this.express = express;
	}
	
	@Length(min=0, max=255, message="收获地址长度必须介于 0 和 255 之间")
	public String getCollectAddress() {
		return collectAddress;
	}

	public void setCollectAddress(String collectAddress) {
		this.collectAddress = collectAddress;
	}
	
	@Length(min=0, max=255, message="收货人长度必须介于 0 和 255 之间")
	public String getCollectUser() {
		return collectUser;
	}

	public void setCollectUser(String collectUser) {
		this.collectUser = collectUser;
	}
	
	public Long getCollectPhone() {
		return collectPhone;
	}

	public void setCollectPhone(Long collectPhone) {
		this.collectPhone = collectPhone;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getWinningTime() {
		return winningTime;
	}

	public void setWinningTime(Date winningTime) {
		this.winningTime = winningTime;
	}
	
	@Length(min=0, max=255, message="配送状态  1未配送 2已配送 3已领取 4未使用 5已使用 6已赠送 7赠送中  8已删除长度必须介于 0 和 255 之间")
	public String getDistributionStatus() {
		return distributionStatus;
	}

	public void setDistributionStatus(String distributionStatus) {
		this.distributionStatus = distributionStatus;
	}
	
	@Length(min=0, max=64, message="openid长度必须介于 0 和 64 之间")
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	@Length(min=0, max=255, message="粮食克数长度必须介于 0 和 255 之间")
	public String getGrainGram() {
		return grainGram;
	}

	public void setGrainGram(String grainGram) {
		this.grainGram = grainGram;
	}
	
	@Length(min=0, max=255, message="奖品状态  1已使用  2未使用长度必须介于 0 和 255 之间")
	public String getPrizeStatus() {
		return prizeStatus;
	}

	public void setPrizeStatus(String prizeStatus) {
		this.prizeStatus = prizeStatus;
	}
	
	@Length(min=0, max=255, message="奖品类型 1积分奖品  2卡券奖品 3物流奖品 4卡券物流奖品长度必须介于 0 和 255 之间")
	public String getPrizeType() {
		return prizeType;
	}

	public void setPrizeType(String prizeType) {
		this.prizeType = prizeType;
	}
	
	@Length(min=0, max=255, message="所扫码的ID长度必须介于 0 和 255 之间")
	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}
	
	@Length(min=0, max=255, message="微信生成的唯一ID长度必须介于 0 和 255 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=0, max=255, message="卡券ID长度必须介于 0 和 255 之间")
	public String getCardid() {
		return cardid;
	}

	public void setCardid(String cardid) {
		this.cardid = cardid;
	}
	
	@Length(min=0, max=255, message="地址状态  0 未填写  1已填写长度必须介于 0 和 255 之间")
	public String getAddressStatus() {
		return addressStatus;
	}

	public void setAddressStatus(String addressStatus) {
		this.addressStatus = addressStatus;
	}
	
}