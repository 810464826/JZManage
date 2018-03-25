/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.reportform.provincescount.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.reportform.provincescount.entity.ProvincesCount;
import com.thinkgem.jeesite.modules.reportform.provincescount.dao.ProvincesCountDao;

/**
 * 跨省份扫码统计Service
 * @author tanghaobo
 * @version 2017-07-06
 */
@Service
@Transactional(readOnly = true)
public class ProvincesCountService extends CrudService<ProvincesCountDao, ProvincesCount> {

	public ProvincesCount get(String id) {
		return super.get(id);
	}
	
	public List<ProvincesCount> findList(ProvincesCount provincesCount) {
		return super.findList(provincesCount);
	}
	
	public Page<ProvincesCount> findPage(Page<ProvincesCount> page, ProvincesCount provincesCount) {
		return super.findPage(page, provincesCount);
	}
	
	@Transactional(readOnly = false)
	public void save(ProvincesCount provincesCount) {
		super.save(provincesCount);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProvincesCount provincesCount) {
		super.delete(provincesCount);
	}
	
}