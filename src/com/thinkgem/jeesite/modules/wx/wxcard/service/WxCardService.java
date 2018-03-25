/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wx.wxcard.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.wx.wxcard.entity.WxCard;
import com.thinkgem.jeesite.modules.wx.wxcard.dao.WxCardDao;

/**
 * 微信卡券Service
 * @author tanghaobo
 * @version 2017-03-23
 */
@Service
@Transactional(readOnly = true)
public class WxCardService extends CrudService<WxCardDao, WxCard> {

	public WxCard get(String id) {
		return super.get(id);
	}
	
	public List<WxCard> findList(WxCard wxCard) {
		return super.findList(wxCard);
	}
	
	public Page<WxCard> findPage(Page<WxCard> page, WxCard wxCard) {
		return super.findPage(page, wxCard);
	}
	
	@Transactional(readOnly = false)
	public void save(WxCard wxCard) {
		super.save(wxCard);
	}
	
	@Transactional(readOnly = false)
	public void delete(WxCard wxCard) {
		super.delete(wxCard);
	}
	
	@Transactional(readOnly = false)
	public WxCard getCardByCardId(String cardid) {
		return dao.getCardByCardId(cardid);
	}
}