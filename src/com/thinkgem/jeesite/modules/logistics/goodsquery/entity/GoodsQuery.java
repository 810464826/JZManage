/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.logistics.goodsquery.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 发货查询Entity
 * @author tanghaobo
 * @version 2017-03-31
 */
public class GoodsQuery extends DataEntity<GoodsQuery> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String prizeName;		// 奖品名称
	private String number;		// 编号
	private String dstributorlId;		// 经销商ID
	private String dstributorlName;		// 经销商名称
	private String dstributorlAddress;		// 地址
	private String driverName;		// 驾驶员名称
	private String driverPhone;		// 驾驶员电话
	private String goodsAddress;		// 发货地址
	private Date goodsTime;		// 发货时间
	private String driverId;		// 驾驶员ID
	
	public GoodsQuery() {
		super();
	}

	public GoodsQuery(String id){
		super(id);
	}

	@Length(min=1, max=255, message="名称长度必须介于 1 和 255 之间")
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
	
	@Length(min=1, max=255, message="编号长度必须介于 1 和 255 之间")
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	@Length(min=1, max=64, message="经销商ID长度必须介于 1 和 64 之间")
	public String getDstributorlId() {
		return dstributorlId;
	}

	public void setDstributorlId(String dstributorlId) {
		this.dstributorlId = dstributorlId;
	}
	
	@Length(min=1, max=255, message="经销商名称长度必须介于 1 和 255 之间")
	public String getDstributorlName() {
		return dstributorlName;
	}

	public void setDstributorlName(String dstributorlName) {
		this.dstributorlName = dstributorlName;
	}
	
	@Length(min=1, max=255, message="地址长度必须介于 1 和 255 之间")
	public String getDstributorlAddress() {
		return dstributorlAddress;
	}

	public void setDstributorlAddress(String dstributorlAddress) {
		this.dstributorlAddress = dstributorlAddress;
	}
	
	@Length(min=0, max=255, message="驾驶员名称长度必须介于 0 和 255 之间")
	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	
	@Length(min=0, max=255, message="驾驶员电话长度必须介于 0 和 255 之间")
	public String getDriverPhone() {
		return driverPhone;
	}

	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}
	
	@Length(min=1, max=255, message="发货地址长度必须介于 1 和 255 之间")
	public String getGoodsAddress() {
		return goodsAddress;
	}

	public void setGoodsAddress(String goodsAddress) {
		this.goodsAddress = goodsAddress;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getGoodsTime() {
		return goodsTime;
	}

	public void setGoodsTime(Date goodsTime) {
		this.goodsTime = goodsTime;
	}
	
	@Length(min=0, max=64, message="驾驶员ID长度必须介于 0 和 64 之间")
	public String getDriverId() {
		return driverId;
	}

	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}
	
}