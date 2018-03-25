/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.integralrule.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.marketing.integralrule.entity.IntegralRule;

/**
 * 积分规则DAO接口
 * @author tanghaobo
 * @version 2017-03-16
 */
@MyBatisDao
public interface IntegralRuleDao extends CrudDao<IntegralRule> {
	
}