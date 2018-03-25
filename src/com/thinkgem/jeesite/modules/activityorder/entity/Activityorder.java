/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.activityorder.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 活动运单表Entity
 * @author 陈小兵
 * @version 2017-08-10
 */
public class Activityorder extends DataEntity<Activityorder> {
	
	private static final long serialVersionUID = 1L;
	private String activityid;		// activityid
	private String orderid;		// orderid
	private String state;		// state
	private String recordid;    //recordid
	
	public Activityorder() {
		super();
	}

	public Activityorder(String id){
		super(id);
	}

	@Length(min=1, max=64, message="activityid长度必须介于 1 和 64 之间")
	public String getActivityid() {
		return activityid;
	}

	public void setActivityid(String activityid) {
		this.activityid = activityid;
	}
	
	@Length(min=1, max=64, message="orderid长度必须介于 1 和 64 之间")
	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	
	@Length(min=1, max=64, message="state长度必须介于 1 和 64 之间")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	@Length(min=1, max=64, message="recordid长度必须介于 1 和 64 之间")
	public String getRecordid() {
		return recordid;
	}

	public void setRecordid(String recordid) {
		this.recordid = recordid;
	}
	
}