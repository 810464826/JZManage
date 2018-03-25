/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.reportform.signinfo.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.reportform.signinfo.entity.SignInfo;
import com.thinkgem.jeesite.modules.reportform.signinfo.dao.SignInfoDao;

/**
 * 扫码中奖统计Service
 * @author tanghaobo
 * @version 2017-06-23
 */
@Service
@Transactional(readOnly = true)
public class SignInfoService extends CrudService<SignInfoDao, SignInfo> {
	
	public SignInfo get(String id) {
		return super.get(id);
	}
	
	public List<SignInfo> findList(SignInfo signInfo) {
		return super.findList(signInfo);
	}
	
	public Page<SignInfo> findPage(Page<SignInfo> page, SignInfo signInfo) {
		return super.findPage(page, signInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(SignInfo signInfo) {
		super.save(signInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(SignInfo signInfo) {
		super.delete(signInfo);
	}
	
	public List<Map<String,Object>> findExport(SignInfo signInfo){
		return super.dao.findExport(signInfo);
	}
	
}