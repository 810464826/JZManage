/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.reportform.provincescount.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.reportform.provincescount.entity.ProvincesCount;

/**
 * 跨省份扫码统计DAO接口
 * @author tanghaobo
 * @version 2017-07-06
 */
@MyBatisDao
public interface ProvincesCountDao extends CrudDao<ProvincesCount> {
	
}