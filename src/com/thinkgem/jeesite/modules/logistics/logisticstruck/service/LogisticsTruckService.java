/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.logistics.logisticstruck.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.logistics.logisticstruck.entity.LogisticsTruck;
import com.thinkgem.jeesite.modules.logistics.logisticstruck.dao.LogisticsTruckDao;

/**
 * 物流车辆Service
 * @author tanghaobo
 * @version 2017-03-31
 */
@Service
@Transactional(readOnly = true)
public class LogisticsTruckService extends CrudService<LogisticsTruckDao, LogisticsTruck> {

	public LogisticsTruck get(String id) {
		return super.get(id);
	}
	
	public List<LogisticsTruck> findList(LogisticsTruck logisticsTruck) {
		return super.findList(logisticsTruck);
	}
	
	public Page<LogisticsTruck> findPage(Page<LogisticsTruck> page, LogisticsTruck logisticsTruck) {
		return super.findPage(page, logisticsTruck);
	}
	
	@Transactional(readOnly = false)
	public void save(LogisticsTruck logisticsTruck) {
		super.save(logisticsTruck);
	}
	
	@Transactional(readOnly = false)
	public void delete(LogisticsTruck logisticsTruck) {
		super.delete(logisticsTruck);
	}
	
}