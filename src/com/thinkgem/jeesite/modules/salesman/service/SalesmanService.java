/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.salesman.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.salesman.entity.Salesman;
import com.thinkgem.jeesite.modules.salesman.dao.SalesmanDao;

/**
 * 业务员Service
 * @author cxb
 * @version 2017-09-04
 */
@Service
@Transactional(readOnly = true)
public class SalesmanService extends CrudService<SalesmanDao, Salesman> {

	@Autowired
	SalesmanDao dao;
	public Salesman get(String id) {
		return super.get(id);
	}
	
	public List<Salesman> findList(Salesman salesman) {
		return super.findList(salesman);
	}
	
	public Page<Salesman> findPage(Page<Salesman> page, Salesman salesman) {
		return super.findPage(page, salesman);
	}
	
	@Transactional(readOnly = false)
	public void save(Salesman salesman) {
		super.save(salesman);
	}
	
	@Transactional(readOnly = false)
	public void delete(Salesman salesman) {
		super.delete(salesman);
	}
	
	public void deleteSalesManByOfficeId(String officeId){
		dao.deleteSalesManByOfficeId(officeId);
	}
	
	public List<Salesman> findSalesManByOfficeId(String officeId){
		return dao.findSalesManByOfficeId(officeId);
	}
	
	/*public List<Salesman> findSalesManByPhone(String phone,String name){
		return dao.findSalesManByPhone(phone,name);
	}*/
	
	public List<Salesman> findSalesManByPhone(String phone){
		return dao.findSalesManByPhone(phone);
	}
	
	public List<Salesman> findSalesManByPhoneManageOffice(String phone,String manangeofficeid){
		return dao.findSalesManByPhoneManageOffice(phone, manangeofficeid);
	}
}