/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exportcompany.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.exportcompany.entity.Exportcompany;

/**
 * 终端门店DAO接口
 * @author cxb
 * @version 2017-09-04
 */
@MyBatisDao
public interface ExportcompanyDao extends CrudDao<Exportcompany> {
	Exportcompany findCopmpanyById(String officeid);
	public void deleteExportCompanyByOfficeId(String officeid);
	Exportcompany findCopmpanyByUserId(String userid);
	public void deleteExportCompanyByUserId(String userid);
}