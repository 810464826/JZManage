/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.accountsapply.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.accountsapply.entity.Accountsapply;
import com.thinkgem.jeesite.modules.accountsapply.dao.AccountsapplyDao;

/**
 * 对账申请Service
 * @author cxb
 * @version 2017-09-25
 */
@Service
@Transactional(readOnly = true)
public class AccountsapplyService extends CrudService<AccountsapplyDao, Accountsapply> {

	public Accountsapply get(String id) {
		return super.get(id);
	}
	
	public List<Accountsapply> findList(Accountsapply accountsapply) {
		return super.findList(accountsapply);
	}
	
	public Page<Accountsapply> findPage(Page<Accountsapply> page, Accountsapply accountsapply) {
		return super.findPage(page, accountsapply);
	}
	
	@Transactional(readOnly = false)
	public void save(Accountsapply accountsapply) {
		super.save(accountsapply);
	}
	
	@Transactional(readOnly = false)
	public void delete(Accountsapply accountsapply) {
		super.delete(accountsapply);
	}
	
}