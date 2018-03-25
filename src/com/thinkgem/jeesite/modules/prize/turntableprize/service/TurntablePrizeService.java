/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.prize.turntableprize.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.prize.turntableprize.entity.TurntablePrize;
import com.thinkgem.jeesite.modules.prize.turntableprize.dao.TurntablePrizeDao;

/**
 * 转盘中奖记录Service
 * @author tanghaobo
 * @version 2017-07-05
 */
@Service
@Transactional(readOnly = true)
public class TurntablePrizeService extends CrudService<TurntablePrizeDao, TurntablePrize> {

	public TurntablePrize get(String id) {
		return super.get(id);
	}
	
	public List<TurntablePrize> findList(TurntablePrize turntablePrize) {
		return super.findList(turntablePrize);
	}
	
	public Page<TurntablePrize> findPage(Page<TurntablePrize> page, TurntablePrize turntablePrize) {
		return super.findPage(page, turntablePrize);
	}
	
	@Transactional(readOnly = false)
	public void save(TurntablePrize turntablePrize) {
		super.save(turntablePrize);
	}
	
	@Transactional(readOnly = false)
	public void delete(TurntablePrize turntablePrize) {
		super.delete(turntablePrize);
	}
	
}