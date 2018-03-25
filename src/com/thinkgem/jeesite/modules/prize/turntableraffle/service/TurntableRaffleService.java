/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.prize.turntableraffle.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.prize.turntableraffle.entity.TurntableRaffle;
import com.thinkgem.jeesite.modules.prize.turntableraffle.dao.TurntableRaffleDao;

/**
 * 转盘抽奖Service
 * @author tanghaobo
 * @version 2017-03-31
 */
@Service
@Transactional(readOnly = true)
public class TurntableRaffleService extends CrudService<TurntableRaffleDao, TurntableRaffle> {
	
	@Autowired
	TurntableRaffleDao turndao;
	
	public TurntableRaffle get(String id) {
		return super.get(id);
	}
	
	public List<TurntableRaffle> findList(TurntableRaffle turntableRaffle) {
		return super.findList(turntableRaffle);
	}
	
	public Page<TurntableRaffle> findPage(Page<TurntableRaffle> page, TurntableRaffle turntableRaffle) {
		return super.findPage(page, turntableRaffle);
	}
	
	@Transactional(readOnly = false)
	public void save(TurntableRaffle turntableRaffle) {
		super.save(turntableRaffle);
	}
	
	@Transactional(readOnly = false)
	public void delete(TurntableRaffle turntableRaffle) {
		super.delete(turntableRaffle);
	}
	
	public List<TurntableRaffle> TurntableRaffleList(String id) {
		return turndao.TurntableRaffleList(id);
	}
}