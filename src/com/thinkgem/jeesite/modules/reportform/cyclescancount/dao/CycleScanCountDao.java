/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.reportform.cyclescancount.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.reportform.cyclescancount.entity.CycleScanCount;

/**
 * 周期扫码次数统计DAO接口
 * @author tanghaobo
 * @version 2017-07-06
 */
@MyBatisDao
public interface CycleScanCountDao extends CrudDao<CycleScanCount> {
	
}