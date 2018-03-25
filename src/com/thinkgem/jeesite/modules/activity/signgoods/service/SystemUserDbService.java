/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.activity.signgoods.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.activity.signgoods.entity.SystemUserDb;
import com.thinkgem.jeesite.modules.activity.signgoods.dao.SystemUserDbDao;

/**
 * 用户Service
 * @author tanghaobo
 * @version 2017-04-27
 */
@Service
@Transactional(readOnly = true)
public class SystemUserDbService extends CrudService<SystemUserDbDao, SystemUserDb> {

	@Autowired
	SystemUserDbDao systemUserDbDao;
	
	public SystemUserDb getUserByPhone(String phone){
		return systemUserDbDao.getUserByPhone(phone);
	}
	
}