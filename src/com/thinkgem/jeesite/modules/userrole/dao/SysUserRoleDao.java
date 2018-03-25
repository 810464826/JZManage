/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.userrole.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.userrole.entity.SysUserRole;

/**
 * 人员角色关系DAO接口
 * @author 陈小兵
 * @version 2017-08-06
 */
@MyBatisDao
public interface SysUserRoleDao extends CrudDao<SysUserRole> {
	
}