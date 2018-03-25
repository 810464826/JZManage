/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.logistics.goodsquery.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.logistics.goodsquery.entity.GoodsQuery;
import com.thinkgem.jeesite.modules.logistics.goodsquery.dao.GoodsQueryDao;

/**
 * 发货查询Service
 * @author tanghaobo
 * @version 2017-03-31
 */
@Service
@Transactional(readOnly = true)
public class GoodsQueryService extends CrudService<GoodsQueryDao, GoodsQuery> {

	public GoodsQuery get(String id) {
		return super.get(id);
	}
	
	public List<GoodsQuery> findList(GoodsQuery goodsQuery) {
		return super.findList(goodsQuery);
	}
	
	public Page<GoodsQuery> findPage(Page<GoodsQuery> page, GoodsQuery goodsQuery) {
		return super.findPage(page, goodsQuery);
	}
	
	@Transactional(readOnly = false)
	public void save(GoodsQuery goodsQuery) {
		super.save(goodsQuery);
	}
	
	@Transactional(readOnly = false)
	public void delete(GoodsQuery goodsQuery) {
		super.delete(goodsQuery);
	}
	
}