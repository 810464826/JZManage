/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wx.followuser.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.wx.followuser.entity.WxUser;
import com.thinkgem.jeesite.modules.wx.followuser.dao.WxUserDao;

/**
 * 微信公众号上的关注用户Service
 * @author tanghaobo
 * @version 2017-03-13
 */
@Service("WxUserService")
@Transactional(readOnly = true)
public class WxUserService extends CrudService<WxUserDao, WxUser> {

	@Autowired
	WxUserDao wxdao;
	
	public WxUser get(String id) {
		return super.get(id);
	}
	
	public List<WxUser> findList(WxUser wxUser) {
		return super.findList(wxUser);
	}
	
	public Page<WxUser> findPage(Page<WxUser> page, WxUser wxUser) {
		return super.findPage(page, wxUser);
	}
	
	@Transactional(readOnly = false)
	public void save(WxUser wxUser) {
		super.save(wxUser);
	}
	
	@Transactional(readOnly = false)
	public void delete(WxUser wxUser) {
		super.delete(wxUser);
	}
	@Transactional(readOnly = false)
	public void saveByOpenid(WxUser user){
		wxdao.saveByOpenid(user.getId(),user.getOpenid());
	}
	
	@Transactional
	public void upUser(WxUser user){
		wxdao.upUser(user);
	}
	
	public WxUser getUserByOpenid(String openid) {
		return wxdao.getUserByOpenid(openid);
	}
	@Transactional
	public void empty(){
		wxdao.empty();
	}
}