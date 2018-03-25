/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wx.wxmenu.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.wx.wxmenu.entity.WxMenu;
import com.thinkgem.jeesite.modules.wx.wxmenu.dao.WxMenuDao;

/**
 * 微信自定义菜单Service
 * @author tanghaobo
 * @version 2017-03-20
 */
@Service
@Transactional(readOnly = true)
public class WxMenuService extends CrudService<WxMenuDao, WxMenu> {

	public WxMenu get(String id) {
		return super.get(id);
	}
	
	public List<WxMenu> findList(WxMenu wxMenu) {
		return super.findList(wxMenu);
	}
	
	public Page<WxMenu> findPage(Page<WxMenu> page, WxMenu wxMenu) {
		return super.findPage(page, wxMenu);
	}
	
	@Transactional(readOnly = false)
	public void save(WxMenu wxMenu) {
		super.save(wxMenu);
	}
	
	@Transactional(readOnly = false)
	public void delete(WxMenu wxMenu) {
		super.delete(wxMenu);
	}
	
}