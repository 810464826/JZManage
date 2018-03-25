/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.prize.turntableraffle.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.prize.turntableraffle.entity.TurntableRaffle;

/**
 * 转盘抽奖DAO接口
 * @author tanghaobo
 * @version 2017-03-31
 */
@MyBatisDao
public interface TurntableRaffleDao extends CrudDao<TurntableRaffle> {
	List<TurntableRaffle> TurntableRaffleList(@Param("id")String id);
}