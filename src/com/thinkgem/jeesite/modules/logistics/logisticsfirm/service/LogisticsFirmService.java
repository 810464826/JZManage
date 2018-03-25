/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.logistics.logisticsfirm.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.logistics.logisticsfirm.entity.LogisticsFirm;
import com.thinkgem.jeesite.modules.logistics.logisticsfirm.dao.LogisticsFirmDao;

/**
 * 物流信息Service
 * @author tanghaobo
 * @version 2017-03-30
 */
@Service
@Transactional(readOnly = true)
public class LogisticsFirmService extends CrudService<LogisticsFirmDao, LogisticsFirm> {

	public LogisticsFirm get(String id) {
		return super.get(id);
	}
	
	public List<LogisticsFirm> findList(LogisticsFirm logisticsFirm) {
		return super.findList(logisticsFirm);
	}
	
	public Page<LogisticsFirm> findPage(Page<LogisticsFirm> page, LogisticsFirm logisticsFirm) {
		return super.findPage(page, logisticsFirm);
	}
	
	@Transactional(readOnly = false)
	public void save(LogisticsFirm logisticsFirm) {
		super.save(logisticsFirm);
	}
	
	@Transactional(readOnly = false)
	public void delete(LogisticsFirm logisticsFirm) {
		super.delete(logisticsFirm);
	}
	
}