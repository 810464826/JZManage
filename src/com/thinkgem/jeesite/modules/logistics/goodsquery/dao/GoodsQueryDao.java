/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.logistics.goodsquery.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.logistics.goodsquery.entity.GoodsQuery;

/**
 * 发货查询DAO接口
 * @author tanghaobo
 * @version 2017-03-31
 */
@MyBatisDao
public interface GoodsQueryDao extends CrudDao<GoodsQuery> {
	
}