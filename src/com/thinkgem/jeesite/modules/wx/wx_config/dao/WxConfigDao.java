/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wx.wx_config.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.wx.wx_config.entity.WxConfig;

/**
 * 调用微信需要的一些配置参数DAO接口
 * @author tanghaobo
 * @version 2017-03-16
 */
@MyBatisDao
public interface WxConfigDao extends CrudDao<WxConfig> {
	public WxConfig getWxconfigByName(String name);
}