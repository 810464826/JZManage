/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wx.followuser.dao;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.wx.followuser.entity.WxUser;

/**
 * 微信公众号上的关注用户DAO接口
 * @author tanghaobo
 * @version 2017-03-13
 */
@MyBatisDao
public interface WxUserDao extends CrudDao<WxUser> {
	void saveByOpenid(@Param("id")String id,@Param("openid")String openid);
	void upUser(WxUser user);
	WxUser getUserByOpenid(@Param("openid")String openid);
	void empty();
}