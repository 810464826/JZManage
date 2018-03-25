/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.integralmall.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 积分商城Entity
 * @author tanghaobo
 * @version 2017-03-21
 */
public class IntegralMall extends DataEntity<IntegralMall> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 商品名称
	private String exchange;		// 积分兑换
	private String recommend;		// 是否推荐
	private String total;		// 总数
	private String remainingQuantity;		// 剩余数量
	private String prizeType;		// 奖品类型
	private String cardId;		// 卡券ID
	
	public IntegralMall() {
		super();
	}

	public IntegralMall(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}
	
	public String getRecommend() {
		return recommend;
	}

	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}
	
	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}
	
	public String getRemainingQuantity() {
		return remainingQuantity;
	}

	public void setRemainingQuantity(String remainingQuantity) {
		this.remainingQuantity = remainingQuantity;
	}
	
	public String getPrizeType() {
		return prizeType;
	}

	public void setPrizeType(String prizeType) {
		this.prizeType = prizeType;
	}
	
	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	
}