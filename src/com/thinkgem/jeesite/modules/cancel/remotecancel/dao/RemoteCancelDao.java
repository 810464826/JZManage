/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cancel.remotecancel.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.cancel.remotecancel.entity.RemoteCancel;

/**
 * 异地核销DAO接口
 * @author tanghaobo
 * @version 2017-06-18
 */
@MyBatisDao
public interface RemoteCancelDao extends CrudDao<RemoteCancel> {
	
}