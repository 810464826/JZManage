/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wx.wx_config.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.wx.wx_config.entity.WxConfig;
import com.thinkgem.jeesite.modules.wx.wx_config.dao.WxConfigDao;

/**
 * 调用微信需要的一些配置参数Service
 * @author tanghaobo
 * @version 2017-03-16
 */
@Service
@Transactional(readOnly = true)
public class WxConfigService extends CrudService<WxConfigDao, WxConfig> {

	@Autowired
	WxConfigDao wxdao;
	
	public WxConfig get(String id) {
		return super.get(id);
	}
	
	public List<WxConfig> findList(WxConfig wxConfig) {
		return super.findList(wxConfig);
	}
	
	public Page<WxConfig> findPage(Page<WxConfig> page, WxConfig wxConfig) {
		return super.findPage(page, wxConfig);
	}
	
	@Transactional(readOnly = false)
	public void save(WxConfig wxConfig) {
		super.save(wxConfig);
	}
	
	@Transactional(readOnly = false)
	public void delete(WxConfig wxConfig) {
		super.delete(wxConfig);
	}
	
	public WxConfig getWxconfigByName(String name) {
		return wxdao.getWxconfigByName(name);
	}
}