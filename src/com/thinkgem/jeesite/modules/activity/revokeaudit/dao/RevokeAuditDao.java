/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.activity.revokeaudit.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.activity.revokeaudit.entity.RevokeAudit;

/**
 * 撤销审核DAO接口
 * @author tanghaobo
 * @version 2017-03-24
 */
@MyBatisDao
public interface RevokeAuditDao extends CrudDao<RevokeAudit> {
	
}