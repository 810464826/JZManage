/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wx.wx_config.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 调用微信需要的一些配置参数Entity
 * @author tanghaobo
 * @version 2017-03-16
 */
public class WxConfig extends DataEntity<WxConfig> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String value;		// 值
	
	public WxConfig() {
		super();
	}

	public WxConfig(String id){
		super(id);
	}

	@Length(min=1, max=255, message="名称长度必须介于 1 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=255, message="值长度必须介于 1 和 255 之间")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}