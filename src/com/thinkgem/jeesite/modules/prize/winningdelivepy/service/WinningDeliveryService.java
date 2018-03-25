/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.prize.winningdelivepy.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.prize.winningdelivepy.entity.WinningDelivery;
import com.thinkgem.jeesite.modules.prize.winningdelivepy.dao.WinningDeliveryDao;

/**
 * 奖品派送Service
 * @author tanghaobo
 * @version 2017-03-31
 */
@Service
@Transactional(readOnly = true)
public class WinningDeliveryService extends CrudService<WinningDeliveryDao, WinningDelivery> {

	public WinningDelivery get(String id) {
		return super.get(id);
	}
	
	public List<WinningDelivery> findList(WinningDelivery winningDelivery) {
		return super.findList(winningDelivery);
	}
	
	public Page<WinningDelivery> findPage(Page<WinningDelivery> page, WinningDelivery winningDelivery) {
		return super.findPage(page, winningDelivery);
	}
	
	@Transactional(readOnly = false)
	public void save(WinningDelivery winningDelivery) {
		super.save(winningDelivery);
	}
	
	@Transactional(readOnly = false)
	public void delete(WinningDelivery winningDelivery) {
		super.delete(winningDelivery);
	}
	
}