/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.prize.integralprize.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.prize.integralprize.entity.IntegralPrize;

/**
 * 积分兑换DAO接口
 * @author tanghaobo
 * @version 2017-07-05
 */
@MyBatisDao
public interface IntegralPrizeDao extends CrudDao<IntegralPrize> {
	
}