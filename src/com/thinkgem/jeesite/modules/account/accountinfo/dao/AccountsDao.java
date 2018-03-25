/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.account.accountinfo.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.account.accountinfo.entity.Accounts;

/**
 * 对账管理DAO接口
 * @author tanghaobo
 * @version 2017-06-27
 */
@MyBatisDao
public interface AccountsDao extends CrudDao<Accounts> {
}