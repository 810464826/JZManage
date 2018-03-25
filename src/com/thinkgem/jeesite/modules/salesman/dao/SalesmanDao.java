/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.salesman.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.salesman.entity.Salesman;

/**
 * 业务员DAO接口
 * @author cxb
 * @version 2017-09-04
 */
@MyBatisDao
public interface SalesmanDao extends CrudDao<Salesman> {
	public void deleteSalesManByOfficeId(String officeId);
	public List<Salesman> findSalesManByOfficeId(String officeId);
	/*public List<Salesman> findSalesManByPhone(@Param("phone")String phone,@Param("name")String name);*/
	public List<Salesman> findSalesManByPhone(@Param("phone")String phone);
	public List<Salesman> findSalesManByPhoneManageOffice(@Param("phone")String phone,@Param("manangeofficeid")String manangeofficeid);
}