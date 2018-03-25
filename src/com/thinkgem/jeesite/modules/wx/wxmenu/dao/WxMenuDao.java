/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wx.wxmenu.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.wx.wxmenu.entity.WxMenu;

/**
 * 微信自定义菜单DAO接口
 * @author tanghaobo
 * @version 2017-03-20
 */
@MyBatisDao
public interface WxMenuDao extends CrudDao<WxMenu> {
	
}