/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.logistics.logisticsdriver.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 物流司机Entity
 * @author tanghaobo
 * @version 2017-03-31
 */
public class LogisticsDriver extends DataEntity<LogisticsDriver> {
	
	private static final long serialVersionUID = 1L;
	private String driverName;		// 司机名称
	private Long phone;		// 电话
	private String truckId;		// truck_id
	
	public LogisticsDriver() {
		super();
	}

	public LogisticsDriver(String id){
		super(id);
	}

	@Length(min=0, max=64, message="司机名称长度必须介于 0 和 64 之间")
	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	
	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=64, message="truck_id长度必须介于 0 和 64 之间")
	public String getTruckId() {
		return truckId;
	}

	public void setTruckId(String truckId) {
		this.truckId = truckId;
	}
	
}