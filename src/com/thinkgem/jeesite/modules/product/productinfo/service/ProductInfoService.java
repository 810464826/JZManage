/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.product.productinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.product.productinfo.entity.ProductInfo;
import com.thinkgem.jeesite.modules.product.productinfo.dao.ProductInfoDao;

/**
 * 产品管理Service
 * @author tanghaobo
 * @version 2017-03-17
 */
@Service
@Transactional(readOnly = true)
public class ProductInfoService extends CrudService<ProductInfoDao, ProductInfo> {
	
	@Autowired
	ProductInfoDao productInfoDao;

	public ProductInfo get(String id) {
		return super.get(id);
	}
	
	public List<ProductInfo> findList(ProductInfo productInfo) {
		return super.findList(productInfo);
	}
	
	public Page<ProductInfo> findPage(Page<ProductInfo> page, ProductInfo productInfo) {
		return super.findPage(page, productInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(ProductInfo productInfo) {
		super.save(productInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProductInfo productInfo) {
		super.delete(productInfo);
	}
	
	public List<ProductInfo> getProductBytype(String type) {
		return productInfoDao.getProductBytype(type);
	}
	
	public List<ProductInfo> getProductByNumber(String number){
		return productInfoDao.getProductByNumber(number);
	}
	
	public void savePro(ProductInfo productInfo){
		productInfoDao.savePro(productInfo);
	}
}