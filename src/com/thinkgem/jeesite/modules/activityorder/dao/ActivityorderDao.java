/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.activityorder.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.activityorder.entity.Activityorder;

/**
 * 活动和运单DAO接口
 * @author 陈小兵
 * @version 2017-08-07
 */
@MyBatisDao
public interface ActivityorderDao extends CrudDao<Activityorder> {
	public void deleteActivity(String activityid);
	public List<Activityorder> findActivityOrder(String activityid);
	public List<Activityorder> findActivityOrderRecordIdByState(String activityid);
}