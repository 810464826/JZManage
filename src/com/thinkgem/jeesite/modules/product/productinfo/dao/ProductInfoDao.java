/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.product.productinfo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.product.productinfo.entity.ProductInfo;

/**
 * 产品管理DAO接口
 * @author tanghaobo
 * @version 2017-03-17
 */
@MyBatisDao
public interface ProductInfoDao extends CrudDao<ProductInfo> {
	List<ProductInfo> getProductBytype(@Param("type")String type);
	List<ProductInfo> getProductByNumber(String number);
	public void savePro(ProductInfo productInfo);
}