/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.prize.prizesinfo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.prize.prizesinfo.entity.Winninginfo;
import com.thinkgem.jeesite.modules.prize.prizesinfo.dao.WinninginfoDao;

/**
 * 中奖记录Service
 * @author tanghaobo
 * @version 2017-06-15
 */
@Service
@Transactional(readOnly = true)
public class WinninginfoService extends CrudService<WinninginfoDao, Winninginfo> {

	public Winninginfo get(String id) {
		return super.get(id);
	}
	
	public List<Winninginfo> findList(Winninginfo winninginfo) {
		return super.findList(winninginfo);
	}
	
	public Page<Winninginfo> findPage(Page<Winninginfo> page, Winninginfo winninginfo) {
		return super.findPage(page, winninginfo);
	}
	
	@Transactional(readOnly = false)
	public void save(Winninginfo winninginfo) {
		super.save(winninginfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(Winninginfo winninginfo) {
		super.delete(winninginfo);
	}
	
}