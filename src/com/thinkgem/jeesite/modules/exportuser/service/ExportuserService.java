/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exportuser.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.exportuser.entity.Exportuser;
import com.thinkgem.jeesite.modules.exportuser.dao.ExportuserDao;

/**
 * 经销商Service
 * @author cxb
 * @version 2017-09-04
 */
@Service
@Transactional(readOnly = true)
public class ExportuserService extends CrudService<ExportuserDao, Exportuser> {

	@Autowired
	private ExportuserDao exportuserDao;
	
	public Exportuser get(String id) {
		return super.get(id);
	}
	
	public List<Exportuser> findList(Exportuser exportuser) {
		return super.findList(exportuser);
	}
	
	public Page<Exportuser> findPage(Page<Exportuser> page, Exportuser exportuser) {
		return super.findPage(page, exportuser);
	}
	
	@Transactional(readOnly = false)
	public void save(Exportuser exportuser) {
		super.save(exportuser);
	}
	
	@Transactional(readOnly = false)
	public void delete(Exportuser exportuser) {
		super.delete(exportuser);
	}
	
	public Page<Exportuser> findExportUser(Page<Exportuser> page, Exportuser user) {
		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
		user.getSqlMap().put("dsf", dataScopeFilter(user.getCurrentUser(), "o", "a"));
		// 设置分页参数
		user.setPage(page);
		// 执行分页查询
		page.setList(exportuserDao.findList(user));
		return page;
	}
	
	public void deleteExportuserByPhone(String phone){
		exportuserDao.deleteExportuserByPhone(phone);
	}
	
	public void deleteExportuserByUserId(String userid){
		exportuserDao.deleteExportuserByUserId(userid);
	}
	
	public Exportuser findUserByUserId(String userid){
		return exportuserDao.findUserByUserId(userid);
	}
}