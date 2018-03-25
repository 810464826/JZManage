/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.reportform.signinfo.dao;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.reportform.signinfo.entity.SignInfo;

/**
 * 扫码中奖统计DAO接口
 * @author tanghaobo
 * @version 2017-06-23
 */
@MyBatisDao
public interface SignInfoDao extends CrudDao<SignInfo> {
	List<Map<String,Object>> findExport(SignInfo signInfo);
}