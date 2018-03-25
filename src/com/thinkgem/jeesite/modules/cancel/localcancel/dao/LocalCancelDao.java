/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cancel.localcancel.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.cancel.localcancel.entity.LocalCancel;

/**
 * 核销统计DAO接口
 * @author tanghaobo
 * @version 2017-07-06
 */
@MyBatisDao
public interface LocalCancelDao extends CrudDao<LocalCancel> {
	List<LocalCancel> QueryLocalCancelUser(String officeId);
	List<LocalCancel> QueryLocalByCancelUser(String cancleUser);
	List<LocalCancel> findListByState(LocalCancel localCancel);
}