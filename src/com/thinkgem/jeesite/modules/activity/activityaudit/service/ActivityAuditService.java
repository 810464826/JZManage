/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.activity.activityaudit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.activity.activityaudit.entity.ActivityAudit;
import com.thinkgem.jeesite.modules.activity.activityaudit.dao.ActivityAuditDao;

/**
 * 活动审核Service
 * @author tanghaobo
 * @version 2017-03-24
 */
@Service
@Transactional(readOnly = true)
public class ActivityAuditService extends CrudService<ActivityAuditDao, ActivityAudit> {

	@Autowired
	ActivityAuditDao activityAuditDao;
	
	public ActivityAudit get(String id) {
		return super.get(id);
	}
	
	public List<ActivityAudit> findList(ActivityAudit activityAudit) {
		return super.findList(activityAudit);
	}
	
	public Page<ActivityAudit> findPage(Page<ActivityAudit> page, ActivityAudit activityAudit) {
		return super.findPage(page, activityAudit);
	}
	
	@Transactional(readOnly = false)
	public void save(ActivityAudit activityAudit) {
		super.save(activityAudit);
	}
	
	@Transactional(readOnly = false)
	public void delete(ActivityAudit activityAudit) {
		super.delete(activityAudit);
	}
	public void updateState(ActivityAudit activityAudit) {
		activityAuditDao.updateState(activityAudit);
	}
	
	public List<ActivityAudit> getAllNoMe(String userName){
		return activityAuditDao.getAllNoMe(userName);
	}
}