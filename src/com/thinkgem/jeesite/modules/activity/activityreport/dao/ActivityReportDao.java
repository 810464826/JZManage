/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.activity.activityreport.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.activity.activityreport.entity.ActivityReport;

/**
 * 活动报备DAO接口
 * @author tanghaobo
 * @version 2017-03-16
 */
@MyBatisDao
public interface ActivityReportDao extends CrudDao<ActivityReport> {
	public List<ActivityReport> findListByOfficeId(String officeid);
	public List<ActivityReport> findListByApplyUser(String userid);
}