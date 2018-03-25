/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.activity.revokeaudit.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.activity.revokeaudit.entity.RevokeAudit;
import com.thinkgem.jeesite.modules.activity.revokeaudit.dao.RevokeAuditDao;

/**
 * 撤销审核Service
 * @author tanghaobo
 * @version 2017-03-24
 */
@Service
@Transactional(readOnly = true)
public class RevokeAuditService extends CrudService<RevokeAuditDao, RevokeAudit> {

	public RevokeAudit get(String id) {
		return super.get(id);
	}
	
	public List<RevokeAudit> findList(RevokeAudit revokeAudit) {
		return super.findList(revokeAudit);
	}
	
	public Page<RevokeAudit> findPage(Page<RevokeAudit> page, RevokeAudit revokeAudit) {
		return super.findPage(page, revokeAudit);
	}
	
	@Transactional(readOnly = false)
	public void save(RevokeAudit revokeAudit) {
		super.save(revokeAudit);
	}
	
	@Transactional(readOnly = false)
	public void delete(RevokeAudit revokeAudit) {
		super.delete(revokeAudit);
	}
	
}