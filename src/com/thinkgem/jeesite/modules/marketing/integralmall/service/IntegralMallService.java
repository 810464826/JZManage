/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.integralmall.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.marketing.integralmall.entity.IntegralMall;
import com.thinkgem.jeesite.modules.marketing.integralmall.dao.IntegralMallDao;

/**
 * 积分商城Service
 * @author tanghaobo
 * @version 2017-03-21
 */
@Service
@Transactional(readOnly = true)
public class IntegralMallService extends CrudService<IntegralMallDao, IntegralMall> {

	public IntegralMall get(String id) {
		return super.get(id);
	}
	
	public List<IntegralMall> findList(IntegralMall integralMall) {
		return super.findList(integralMall);
	}
	
	public Page<IntegralMall> findPage(Page<IntegralMall> page, IntegralMall integralMall) {
		return super.findPage(page, integralMall);
	}
	
	@Transactional(readOnly = false)
	public void save(IntegralMall integralMall) {
		super.save(integralMall);
	}
	
	@Transactional(readOnly = false)
	public void delete(IntegralMall integralMall) {
		super.delete(integralMall);
	}
	
}