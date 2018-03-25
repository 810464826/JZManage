/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.productintegral.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.productintegral.entity.Volume;
import com.thinkgem.jeesite.modules.productintegral.dao.VolumeDao;

/**
 * 容量Service
 * @author cxb
 * @version 2017-10-30
 */
@Service
@Transactional(readOnly = true)
public class VolumeService extends CrudService<VolumeDao, Volume> {
	@Autowired
	VolumeDao dao;
	
	public List<Volume> getAllVolume(){
		return dao.getAllVolume();
	}
}