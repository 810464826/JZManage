/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wx.wxmenu.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 微信自定义菜单Entity
 * @author tanghaobo
 * @version 2017-03-20
 */
public class WxMenu extends DataEntity<WxMenu> {
	
	private static final long serialVersionUID = 1L;
	private String menuName;		// 菜单名称
	private String menuType;		// 菜单类型
	private String eventLink;		// 事件或链接
	private String generate;		// 是/否
	private String order;		// 顺序
	private WxMenu parent;		// 父级
	
	public WxMenu() {
		super();
	}

	public WxMenu(String id){
		super(id);
	}

	@Length(min=0, max=8, message="菜单名称长度必须介于 0 和 8 之间")
	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	
	@Length(min=0, max=10, message="菜单类型长度必须介于 0 和 10 之间")
	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}
	
	@Length(min=0, max=1000, message="事件或链接长度必须介于 0 和 1000 之间")
	public String getEventLink() {
		return eventLink;
	}

	public void setEventLink(String eventLink) {
		this.eventLink = eventLink;
	}
	
	@Length(min=0, max=1000, message="是/否长度必须介于 0 和 1000 之间")
	public String getGenerate() {
		return generate;
	}

	public void setGenerate(String generate) {
		this.generate = generate;
	}
	
	@Length(min=0, max=1000, message="顺序长度必须介于 0 和 1000 之间")
	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	
//	@JsonBackReference
	public WxMenu getParent() {
		return parent;
	}

	public void setParent(WxMenu parent) {
		this.parent = parent;
	}
	
}