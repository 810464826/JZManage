/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.logistics.logisticsdriver.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.logistics.logisticsdriver.entity.LogisticsDriver;

/**
 * 物流司机DAO接口
 * @author tanghaobo
 * @version 2017-03-31
 */
@MyBatisDao
public interface LogisticsDriverDao extends CrudDao<LogisticsDriver> {
	
}