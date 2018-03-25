/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.prize.integralprize.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.prize.integralprize.entity.IntegralPrize;
import com.thinkgem.jeesite.modules.prize.integralprize.dao.IntegralPrizeDao;

/**
 * 积分兑换Service
 * @author tanghaobo
 * @version 2017-07-05
 */
@Service
@Transactional(readOnly = true)
public class IntegralPrizeService extends CrudService<IntegralPrizeDao, IntegralPrize> {

	public IntegralPrize get(String id) {
		return super.get(id);
	}
	
	public List<IntegralPrize> findList(IntegralPrize integralPrize) {
		return super.findList(integralPrize);
	}
	
	public Page<IntegralPrize> findPage(Page<IntegralPrize> page, IntegralPrize integralPrize) {
		return super.findPage(page, integralPrize);
	}
	
	@Transactional(readOnly = false)
	public void save(IntegralPrize integralPrize) {
		super.save(integralPrize);
	}
	
	@Transactional(readOnly = false)
	public void delete(IntegralPrize integralPrize) {
		super.delete(integralPrize);
	}
	
}