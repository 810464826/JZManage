/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.activity.signgoods.dao;


import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.activity.signgoods.entity.SystemUserDb;

/**
 * 用户DAO接口
 * @author tanghaobo
 * @version 2017-04-27
 */
@MyBatisDao
public interface SystemUserDbDao extends CrudDao<SystemUserDb> {
	public SystemUserDb getUserByPhone(String phone);
}