/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.account.accountinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.account.accountinfo.entity.Accounts;
import com.thinkgem.jeesite.modules.account.accountinfo.dao.AccountsDao;

/**
 * 对账管理Service
 * @author tanghaobo
 * @version 2017-06-27
 */
@Service
@Transactional(readOnly = true)
public class AccountsService extends CrudService<AccountsDao, Accounts> {
	@Autowired
	AccountsDao dao;
	
	public Accounts get(String id) {
		return super.get(id);
	}
	
	public List<Accounts> findList(Accounts accounts) {
		return super.findList(accounts);
	}
	
	public Page<Accounts> findPage(Page<Accounts> page, Accounts accounts) {
		return super.findPage(page, accounts);
	}
	
	@Transactional(readOnly = false)
	public void save(Accounts accounts) {
		super.save(accounts);
	}
	
	@Transactional(readOnly = false)
	public void delete(Accounts accounts) {
		super.delete(accounts);
	}
}