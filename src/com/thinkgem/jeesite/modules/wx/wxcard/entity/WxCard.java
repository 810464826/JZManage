/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wx.wxcard.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 微信卡券Entity
 * @author tanghaobo
 * @version 2017-03-23
 */
public class WxCard extends DataEntity<WxCard> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 卡券名称
	private String cardId;		// 卡券ID
	private String cardType;		// 卡券类型
	
	public WxCard() {
		super();
	}

	public WxCard(String id){
		super(id);
	}

	@Length(min=1, max=255, message="卡券名称长度必须介于 1 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=255, message="卡券ID长度必须介于 1 和 255 之间")
	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	
	@Length(min=0, max=255, message="卡券类型长度必须介于 0 和 255 之间")
	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	
}