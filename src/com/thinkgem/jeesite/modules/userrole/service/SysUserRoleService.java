/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.userrole.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.userrole.entity.SysUserRole;
import com.thinkgem.jeesite.modules.userrole.dao.SysUserRoleDao;

/**
 * 人员角色关系Service
 * @author 陈小兵
 * @version 2017-08-06
 */
@Service
@Transactional(readOnly = true)
public class SysUserRoleService extends CrudService<SysUserRoleDao, SysUserRole> {

	public SysUserRole get(String id) {
		return super.get(id);
	}
	
	public List<SysUserRole> findList(SysUserRole sysUserRole) {
		return super.findList(sysUserRole);
	}
	
	public Page<SysUserRole> findPage(Page<SysUserRole> page, SysUserRole sysUserRole) {
		return super.findPage(page, sysUserRole);
	}
	
	@Transactional(readOnly = false)
	public void save(SysUserRole sysUserRole) {
		super.save(sysUserRole);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysUserRole sysUserRole) {
		super.delete(sysUserRole);
	}
	
}