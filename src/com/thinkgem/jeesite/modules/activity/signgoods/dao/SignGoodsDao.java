/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.activity.signgoods.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.activity.signgoods.entity.SignGoods;

/**
 * 扫描发货DAO接口
 * @author tanghaobo
 * @version 2017-04-27
 */
@MyBatisDao
public interface SignGoodsDao extends CrudDao<SignGoods> {
	List<SignGoods> getSignGoods(@Param("id")String id);
	List<SignGoods> getSignGoodsAll();
	public void updateSign(SignGoods signGoods);
	List<SignGoods> SignGoodslist(@Param("id")String id);
	List<SignGoods> SignGoodslistF(@Param("id")String id);
	List<SignGoods> getSignGoodsByOutNoAndState(String recordId);
	//根据活动id查询signGoods信息
	List<SignGoods> getSignGoodsByActivityId(String activityReportId);
}