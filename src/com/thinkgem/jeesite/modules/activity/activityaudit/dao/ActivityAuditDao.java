/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.activity.activityaudit.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.activity.activityaudit.entity.ActivityAudit;

/**
 * 活动审核DAO接口
 * @author tanghaobo
 * @version 2017-03-24
 */
@MyBatisDao
public interface ActivityAuditDao extends CrudDao<ActivityAudit> {
	void updateState(ActivityAudit activityAudit);
	public List<ActivityAudit> getAllNoMe(@Param("userName")String userName);
}