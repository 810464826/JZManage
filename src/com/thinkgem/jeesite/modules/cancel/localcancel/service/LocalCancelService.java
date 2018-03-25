/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cancel.localcancel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.cancel.localcancel.entity.LocalCancel;
import com.thinkgem.jeesite.modules.cancel.localcancel.dao.LocalCancelDao;

/**
 * 核销统计Service
 * @author tanghaobo
 * @version 2017-07-06
 */
@Service
@Transactional(readOnly = true)
public class LocalCancelService extends CrudService<LocalCancelDao, LocalCancel> {

	@Autowired
	LocalCancelDao dao;
	
	public LocalCancel get(String id) {
		return super.get(id);
	}
	
	public List<LocalCancel> findList(LocalCancel localCancel) {
		return super.findList(localCancel);
	}
	
	public Page<LocalCancel> findPage(Page<LocalCancel> page, LocalCancel localCancel) {
		return super.findPage(page, localCancel);
	}
	
	@Transactional(readOnly = false)
	public void save(LocalCancel localCancel) {
		super.save(localCancel);
	}
	
	@Transactional(readOnly = false)
	public void delete(LocalCancel localCancel) {
		super.delete(localCancel);
	}
	
	public List<LocalCancel> QueryLocalCancelUser(String officeId) {
		return dao.QueryLocalCancelUser(officeId);
	}
	
	public List<LocalCancel> QueryLocalByCancelUser(String cancleUser) {
		return dao.QueryLocalByCancelUser(cancleUser);
	}
	public List<LocalCancel> findListByState(LocalCancel localCancel){
		return dao.findListByState(localCancel);
	}
}