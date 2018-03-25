/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.logistics.logisticstruck.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 物流车辆Entity
 * @author tanghaobo
 * @version 2017-03-31
 */
public class LogisticsTruck extends DataEntity<LogisticsTruck> {
	
	private static final long serialVersionUID = 1L;
	private String carNumber;		// 车牌号
	private Long load;		// 载重量
	private String carName;		// 汽车名称
	private String firmId;		// 物流公司ID
	
	public LogisticsTruck() {
		super();
	}

	public LogisticsTruck(String id){
		super(id);
	}

	@Length(min=0, max=255, message="车牌号长度必须介于 0 和 255 之间")
	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}
	
	public Long getLoad() {
		return load;
	}

	public void setLoad(Long load) {
		this.load = load;
	}
	
	@Length(min=0, max=64, message="汽车名称长度必须介于 0 和 64 之间")
	public String getCarName() {
		return carName;
	}

	public void setCarName(String carName) {
		this.carName = carName;
	}
	
	@Length(min=0, max=64, message="物流公司ID长度必须介于 0 和 64 之间")
	public String getFirmId() {
		return firmId;
	}

	public void setFirmId(String firmId) {
		this.firmId = firmId;
	}
	
}