/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cancel.remotecancel.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.cancel.remotecancel.entity.RemoteCancel;
import com.thinkgem.jeesite.modules.cancel.remotecancel.dao.RemoteCancelDao;

/**
 * 异地核销Service
 * @author tanghaobo
 * @version 2017-06-18
 */
@Service
@Transactional(readOnly = true)
public class RemoteCancelService extends CrudService<RemoteCancelDao, RemoteCancel> {

	public RemoteCancel get(String id) {
		return super.get(id);
	}
	
	public List<RemoteCancel> findList(RemoteCancel remoteCancel) {
		return super.findList(remoteCancel);
	}
	
	public Page<RemoteCancel> findPage(Page<RemoteCancel> page, RemoteCancel remoteCancel) {
		return super.findPage(page, remoteCancel);
	}
	
	@Transactional(readOnly = false)
	public void save(RemoteCancel remoteCancel) {
		super.save(remoteCancel);
	}
	
	@Transactional(readOnly = false)
	public void delete(RemoteCancel remoteCancel) {
		super.delete(remoteCancel);
	}
	
}