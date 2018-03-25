/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wx.wxcard.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.wx.wxcard.entity.WxCard;

/**
 * 微信卡券DAO接口
 * @author tanghaobo
 * @version 2017-03-23
 */
@MyBatisDao
public interface WxCardDao extends CrudDao<WxCard> {
	WxCard getCardByCardId(String cardid);
}