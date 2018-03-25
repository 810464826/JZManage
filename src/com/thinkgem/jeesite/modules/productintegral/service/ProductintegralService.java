/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.productintegral.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.productintegral.entity.Productintegral;
import com.thinkgem.jeesite.modules.productintegral.dao.ProductintegralDao;

/**
 * 产品积分关系Service
 * @author cxb
 * @version 2017-10-30
 */
@Service
@Transactional(readOnly = true)
public class ProductintegralService extends CrudService<ProductintegralDao, Productintegral> {
	@Autowired
	ProductintegralDao dao;
	
	public Productintegral get(String id) {
		return super.get(id);
	}
	
	public List<Productintegral> findList(Productintegral productintegral) {
		return super.findList(productintegral);
	}
	
	public Page<Productintegral> findPage(Page<Productintegral> page, Productintegral productintegral) {
		return super.findPage(page, productintegral);
	}
	
	@Transactional(readOnly = false)
	public void save(Productintegral productintegral) {
		super.save(productintegral);
	}
	
	@Transactional(readOnly = false)
	public void delete(Productintegral productintegral) {
		super.delete(productintegral);
	}
	
	public Productintegral findProductByCondition(Productintegral productintegral){
		return dao.findProductByCondition(productintegral);
	}
	
	
}