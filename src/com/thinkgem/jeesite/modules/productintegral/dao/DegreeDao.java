/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.productintegral.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.productintegral.entity.Degree;

/**
 * 度数DAO接口
 * @author cxb
 * @version 2017-10-30
 */
@MyBatisDao
public interface DegreeDao extends CrudDao<Degree> {
	public List<Degree> getAllDegree();
}