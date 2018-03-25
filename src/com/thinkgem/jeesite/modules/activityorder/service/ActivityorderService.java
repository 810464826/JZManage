/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.activityorder.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.activityorder.entity.Activityorder;
import com.thinkgem.jeesite.modules.activityorder.dao.ActivityorderDao;

/**
 * 活动和运单Service
 * @author 陈小兵
 * @version 2017-08-07
 */
@Service
@Transactional(readOnly = true)
public class ActivityorderService extends CrudService<ActivityorderDao, Activityorder> {
	@Autowired
	ActivityorderDao activityorderDao;
	public Activityorder get(String id) {
		return super.get(id);
	}
	
	public List<Activityorder> findList(Activityorder activityorder) {
		return super.findList(activityorder);
	}
	
	public Page<Activityorder> findPage(Page<Activityorder> page, Activityorder activityorder) {
		return super.findPage(page, activityorder);
	}
	
	@Transactional(readOnly = false)
	public void save(Activityorder activityorder) {
		super.save(activityorder);
	}
	
	@Transactional(readOnly = false)
	public void delete(Activityorder activityorder) {
		super.delete(activityorder);
	}
	
	/**
	 * 刪除一定要根据活动id来删除这条记录
	 * @param activityid
	 */
	public void deleteActivity(String activityid){
		activityorderDao.deleteActivity(activityid);
	}
	
	public List<Activityorder> findActivityOrder(String activityid){
		return activityorderDao.findActivityOrder(activityid);
	}
	
	public List<Activityorder> findActivityOrderRecordIdByState(String activityid){
		return activityorderDao.findActivityOrderRecordIdByState(activityid);
	}
}