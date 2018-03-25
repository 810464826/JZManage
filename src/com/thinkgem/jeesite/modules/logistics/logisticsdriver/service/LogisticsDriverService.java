/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.logistics.logisticsdriver.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.logistics.logisticsdriver.entity.LogisticsDriver;
import com.thinkgem.jeesite.modules.logistics.logisticsdriver.dao.LogisticsDriverDao;

/**
 * 物流司机Service
 * @author tanghaobo
 * @version 2017-03-31
 */
@Service
@Transactional(readOnly = true)
public class LogisticsDriverService extends CrudService<LogisticsDriverDao, LogisticsDriver> {

	public LogisticsDriver get(String id) {
		return super.get(id);
	}
	
	public List<LogisticsDriver> findList(LogisticsDriver logisticsDriver) {
		return super.findList(logisticsDriver);
	}
	
	public Page<LogisticsDriver> findPage(Page<LogisticsDriver> page, LogisticsDriver logisticsDriver) {
		return super.findPage(page, logisticsDriver);
	}
	
	@Transactional(readOnly = false)
	public void save(LogisticsDriver logisticsDriver) {
		super.save(logisticsDriver);
	}
	
	@Transactional(readOnly = false)
	public void delete(LogisticsDriver logisticsDriver) {
		super.delete(logisticsDriver);
	}
	
}