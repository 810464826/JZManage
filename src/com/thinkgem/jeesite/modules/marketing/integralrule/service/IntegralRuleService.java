/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.integralrule.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.marketing.integralrule.entity.IntegralRule;
import com.thinkgem.jeesite.modules.marketing.integralrule.dao.IntegralRuleDao;

/**
 * 积分规则Service
 * @author tanghaobo
 * @version 2017-03-16
 */
@Service
@Transactional(readOnly = true)
public class IntegralRuleService extends CrudService<IntegralRuleDao, IntegralRule> {

	public IntegralRule get(String id) {
		return super.get(id);
	}
	
	public List<IntegralRule> findList(IntegralRule integralRule) {
		return super.findList(integralRule);
	}
	
	public Page<IntegralRule> findPage(Page<IntegralRule> page, IntegralRule integralRule) {
		return super.findPage(page, integralRule);
	}
	
	@Transactional(readOnly = false)
	public void save(IntegralRule integralRule) {
		super.save(integralRule);
	}
	
	@Transactional(readOnly = false)
	public void delete(IntegralRule integralRule) {
		super.delete(integralRule);
	}
	
}