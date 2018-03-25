/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exportuser.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.exportuser.entity.Exportuser;

/**
 * 经销商DAO接口
 * @author cxb
 * @version 2017-09-04
 */
@MyBatisDao
public interface ExportuserDao extends CrudDao<Exportuser> {
	public void deleteExportuserByPhone(String phone);
	public Exportuser findUserByUserId(String userid);
	public void deleteExportuserByUserId(String userid);
}