/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.integralvip.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.marketing.integralvip.entity.IntegralVip;
import com.thinkgem.jeesite.modules.marketing.integralvip.dao.IntegralVipDao;

/**
 * 会员积分Service
 * @author tanghaobo
 * @version 2017-03-27
 */
@Service
@Transactional(readOnly = true)
public class IntegralVipService extends CrudService<IntegralVipDao, IntegralVip> {
	@Autowired
	IntegralVipDao dao;

	public IntegralVip get(String id) {
		return super.get(id);
	}
	
	public List<IntegralVip> findList(IntegralVip integralVip) {
		return super.findList(integralVip);
	}
	
	public Page<IntegralVip> findPage(Page<IntegralVip> page, IntegralVip integralVip) {
		return super.findPage(page, integralVip);
	}
	
	@Transactional(readOnly = false)
	public void save(IntegralVip integralVip) {
		super.save(integralVip);
	}
	
	@Transactional(readOnly = false)
	public void delete(IntegralVip integralVip) {
		super.delete(integralVip);
	}
	
}