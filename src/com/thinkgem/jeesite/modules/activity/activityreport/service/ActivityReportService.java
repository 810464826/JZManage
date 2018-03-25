/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.activity.activityreport.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.activity.activityreport.entity.ActivityReport;
import com.thinkgem.jeesite.modules.activity.activityreport.dao.ActivityReportDao;

/**
 * 活动报备Service
 * @author tanghaobo
 * @version 2017-03-16
 */
@Service
@Transactional(readOnly = true)
public class ActivityReportService extends CrudService<ActivityReportDao, ActivityReport> {
	@Autowired
	ActivityReportDao dao;
	public ActivityReport get(String id) {
		return super.get(id);
	}
	
	public List<ActivityReport> findList(ActivityReport activityReport) {
		return super.findList(activityReport);
	}
	
	public Page<ActivityReport> findPage(Page<ActivityReport> page, ActivityReport activityReport) {
		return super.findPage(page, activityReport);
	}
	
	@Transactional(readOnly = false)
	public void save(ActivityReport activityReport) {
		super.save(activityReport);
	}
	
	@Transactional(readOnly = false)
	public void delete(ActivityReport activityReport) {
		super.delete(activityReport);
	}
	
	public String saveBackId(ActivityReport activityReport) {
		return super.saveBackId(activityReport);
	}
	
	public List<ActivityReport> findListByOfficeId(String officeid){
		return dao.findListByOfficeId(officeid);
	}
	
	public List<ActivityReport> findListByApplyUser(String userid){
		return dao.findListByApplyUser(userid);
	}
}