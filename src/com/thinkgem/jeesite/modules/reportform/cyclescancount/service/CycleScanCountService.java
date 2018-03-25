/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.reportform.cyclescancount.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.reportform.cyclescancount.entity.CycleScanCount;
import com.thinkgem.jeesite.modules.reportform.cyclescancount.dao.CycleScanCountDao;

/**
 * 周期扫码次数统计Service
 * @author tanghaobo
 * @version 2017-07-06
 */
@Service
@Transactional(readOnly = true)
public class CycleScanCountService extends CrudService<CycleScanCountDao, CycleScanCount> {

	public CycleScanCount get(String id) {
		return super.get(id);
	}
	
	public List<CycleScanCount> findList(CycleScanCount cycleScanCount) {
		return super.findList(cycleScanCount);
	}
	
	public Page<CycleScanCount> findPage(Page<CycleScanCount> page, CycleScanCount cycleScanCount) {
		return super.findPage(page, cycleScanCount);
	}
	
	@Transactional(readOnly = false)
	public void save(CycleScanCount cycleScanCount) {
		super.save(cycleScanCount);
	}
	
	@Transactional(readOnly = false)
	public void delete(CycleScanCount cycleScanCount) {
		super.delete(cycleScanCount);
	}
	
}