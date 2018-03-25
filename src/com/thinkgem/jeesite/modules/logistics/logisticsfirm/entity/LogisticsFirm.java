/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.logistics.logisticsfirm.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 物流信息Entity
 * @author tanghaobo
 * @version 2017-03-30
 */
public class LogisticsFirm extends DataEntity<LogisticsFirm> {
	
	private static final long serialVersionUID = 1L;
	private String logisticsName;		// 公司名称
	private String logisticsCode;		// 公司代码
	
	public LogisticsFirm() {
		super();
	}

	public LogisticsFirm(String id){
		super(id);
	}

	@Length(min=1, max=255, message="公司名称长度必须介于 1 和 255 之间")
	public String getLogisticsName() {
		return logisticsName;
	}

	public void setLogisticsName(String logisticsName) {
		this.logisticsName = logisticsName;
	}
	
	@Length(min=0, max=255, message="公司代码长度必须介于 0 和 255 之间")
	public String getLogisticsCode() {
		return logisticsCode;
	}

	public void setLogisticsCode(String logisticsCode) {
		this.logisticsCode = logisticsCode;
	}
	
}