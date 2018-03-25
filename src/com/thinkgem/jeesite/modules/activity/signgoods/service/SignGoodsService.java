/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.activity.signgoods.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.activity.signgoods.entity.SignGoods;
import com.thinkgem.jeesite.modules.activity.signgoods.dao.SignGoodsDao;

/**
 * 扫描发货Service
 * @author tanghaobo
 * @version 2017-04-27
 */
@Service
@Transactional(readOnly = true)
public class SignGoodsService extends CrudService<SignGoodsDao, SignGoods> {

	@Autowired
	SignGoodsDao signdao;
	
	public SignGoods get(String id) {
		return super.get(id);
	}
	
	public List<SignGoods> findList(SignGoods signGoods) {
		return super.findList(signGoods);
	}
	
	public Page<SignGoods> findPage(Page<SignGoods> page, SignGoods signGoods) {
		return super.findPage(page, signGoods);
	}
	
	@Transactional(readOnly = false)
	public void save(SignGoods signGoods) {
		super.save(signGoods);
	}
	
	@Transactional(readOnly = false)
	public void delete(SignGoods signGoods) {
		super.delete(signGoods);
	}
	
	public List<SignGoods> getSignGoods(String id) {
		return signdao.getSignGoods(id);
	}
	
	public List<SignGoods> getSignGoodsAll() {
		return signdao.getSignGoodsAll();
	}
	
	public void updateSign(SignGoods signGoods) {
		signdao.updateSign(signGoods);
	}
	public List<SignGoods> SignGoodslist(String id) {
		return signdao.SignGoodslist(id);
	}
	
	public List<SignGoods> SignGoodslistF(String id) {
		return signdao.SignGoodslistF(id);
	}
	
	public List<SignGoods> getSignGoodsByOutNoAndState(String recordId) {
		return signdao.getSignGoodsByOutNoAndState(recordId);
	}
	
	public List<SignGoods> getSignGoodsByActivityId(String activityReportId){
		return signdao.getSignGoodsByActivityId(activityReportId);
	}
}