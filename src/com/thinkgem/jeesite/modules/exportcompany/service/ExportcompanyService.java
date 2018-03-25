/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exportcompany.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.exportcompany.entity.Exportcompany;
import com.thinkgem.jeesite.modules.exportcompany.dao.ExportcompanyDao;

/**
 * 终端门店Service
 * @author cxb
 * @version 2017-09-04
 */
@Service
@Transactional(readOnly = true)
public class ExportcompanyService extends CrudService<ExportcompanyDao, Exportcompany> {
	@Autowired
	private ExportcompanyDao dao;
	public Exportcompany get(String id) {
		return super.get(id);
	}
	
	public List<Exportcompany> findList(Exportcompany exportcompany) {
		return super.findList(exportcompany);
	}
	
	public Page<Exportcompany> findPage(Page<Exportcompany> page, Exportcompany exportcompany) {
		return super.findPage(page, exportcompany);
	}
	
	@Transactional(readOnly = false)
	public void save(Exportcompany exportcompany) {
		super.save(exportcompany);
	}
	
	@Transactional(readOnly = false)
	public void delete(Exportcompany exportcompany) {
		super.delete(exportcompany);
	}
	
	public Exportcompany findCopmpanyById(String officeid){
		return dao.findCopmpanyById(officeid);
	}
	public void deleteExportCompanyByOfficeId(String officeid){
		dao.deleteExportCompanyByOfficeId(officeid);
	}
	
	public void deleteExportCompanyByUserId(String userid){
		dao.deleteExportCompanyByUserId(userid);
	}
	
	public Exportcompany findCopmpanyByUserId(String userid){
		return dao.findCopmpanyByUserId(userid);
	}
	
}