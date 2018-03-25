/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.logistics.logisticsfirm.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.logistics.logisticsfirm.entity.LogisticsFirm;

/**
 * 物流信息DAO接口
 * @author tanghaobo
 * @version 2017-03-30
 */
@MyBatisDao
public interface LogisticsFirmDao extends CrudDao<LogisticsFirm> {
	
}