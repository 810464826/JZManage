/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.accountsapply.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.accountsapply.entity.Accountsapply;

/**
 * 对账申请DAO接口
 * @author cxb
 * @version 2017-09-25
 */
@MyBatisDao
public interface AccountsapplyDao extends CrudDao<Accountsapply> {
	
}