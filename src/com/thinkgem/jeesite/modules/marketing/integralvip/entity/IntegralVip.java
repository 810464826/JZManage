/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.integralvip.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 会员积分Entity
 * @author tanghaobo
 * @version 2017-03-27
 */
public class IntegralVip extends DataEntity<IntegralVip> {
	
	private static final long serialVersionUID = 1L;
	private String openid;		// openid
	private String luckyDraw;		// 抽奖人
	private String phone;		// 电话号码
	private String wheat;		// 小麦积分
	private String rice;		// 大米积分
	private String corn;		// 玉米积分
	private String sorghum;		// 高粱积分
	private String glutinousrice;		// 糯米积分
	private String allIntegral;		// 全部积分
	private String luckyNumber;		// 抽奖次数
	private Date createTime;		// 创建时间
	private Date updateTime;		// 修改时间
	
	private String smallFen;    //总积分较小的分数
	private String bigFen;      //总积分较大的分数
	
	
	public String getSmallFen() {
		return smallFen;
	}

	public void setSmallFen(String smallFen) {
		this.smallFen = smallFen;
	}

	public String getBigFen() {
		return bigFen;
	}

	public void setBigFen(String bigFen) {
		this.bigFen = bigFen;
	}

	public IntegralVip() {
		super();
	}

	public IntegralVip(String id){
		super(id);
	}

	@Length(min=1, max=255, message="openid长度必须介于 1 和 255 之间")
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	@Length(min=1, max=255, message="抽奖人长度必须介于 1 和 255 之间")
	public String getLuckyDraw() {
		return luckyDraw;
	}

	public void setLuckyDraw(String luckyDraw) {
		this.luckyDraw = luckyDraw;
	}
	
	@Length(min=1, max=11, message="电话号码长度必须介于 1 和 11 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=11, message="小麦积分长度必须介于 0 和 11 之间")
	public String getWheat() {
		return wheat;
	}

	public void setWheat(String wheat) {
		this.wheat = wheat;
	}
	
	@Length(min=0, max=11, message="大米积分长度必须介于 0 和 11 之间")
	public String getRice() {
		return rice;
	}

	public void setRice(String rice) {
		this.rice = rice;
	}
	
	@Length(min=0, max=11, message="玉米积分长度必须介于 0 和 11 之间")
	public String getCorn() {
		return corn;
	}

	public void setCorn(String corn) {
		this.corn = corn;
	}
	
	@Length(min=0, max=11, message="高粱积分长度必须介于 0 和 11 之间")
	public String getSorghum() {
		return sorghum;
	}

	public void setSorghum(String sorghum) {
		this.sorghum = sorghum;
	}
	
	@Length(min=0, max=11, message="糯米积分长度必须介于 0 和 11 之间")
	public String getGlutinousrice() {
		return glutinousrice;
	}

	public void setGlutinousrice(String glutinousrice) {
		this.glutinousrice = glutinousrice;
	}
	
	@Length(min=0, max=11, message="全部积分长度必须介于 0 和 11 之间")
	public String getAllIntegral() {
		return allIntegral;
	}

	public void setAllIntegral(String allIntegral) {
		this.allIntegral = allIntegral;
	}
	
	@Length(min=0, max=11, message="抽奖次数长度必须介于 0 和 11 之间")
	public String getLuckyNumber() {
		return luckyNumber;
	}

	public void setLuckyNumber(String luckyNumber) {
		this.luckyNumber = luckyNumber;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}