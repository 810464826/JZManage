/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.TreeService;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 机构Service
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class OfficeService extends TreeService<OfficeDao, Office> {
	
	@Autowired
	OfficeDao dao;  
	
	public Office getOfficeByName(String name){
		return dao.getOfficeByName(name);
	}
	
	public Office getOfficeByParentId(String parentId){
		return dao.getOfficeByParentId(parentId);
	}
	
	public List<Office> findAll(){
		return UserUtils.getOfficeList();
	}

	public List<Office> findList(Boolean isAll){
		if (isAll != null && isAll){
			return UserUtils.getOfficeAllList();
		}else{
			return UserUtils.getOfficeList();
		}
	}
	
	@Transactional(readOnly = true)
	public List<Office> findList(Office office){
		if(office != null){
			office.setParentIds(office.getParentIds()+"%");
			return dao.findByParentIdsLike(office);
		}
		return  new ArrayList<Office>();
	}
	
	@Transactional(readOnly = false)
	public void save(Office office) {
		super.save(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(Office office) {
		super.delete(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}
	
	public List<Office> officeByParent(String parent){
		return dao.officeByParent(parent);
	}
	
	public Office getOfficeById(String id){
		return dao.getOfficeById(id);
	}
	
	public List<Office> getStoreByType(@Param("name")String name,@Param("type")String type,@Param("coretype")String coretype,@Param("startLine")int startLine,@Param("endLine")int endLine){
		return dao.getStoreByType(name,type,coretype,startLine,endLine);
	}
	
	public List<Office> getStoreByOfficeId(@Param("officeid")String officeid,@Param("name")String name,@Param("type")String type,@Param("coretype")String coretype,@Param("startLine")int startLine,@Param("endLine")int endLine){
		return dao.getStoreByOfficeId(officeid,name,type,coretype,startLine,endLine);
	}
	
	public List<Office> getNoParentStoreByOfficeId(@Param("officeid")String officeid,@Param("name")String name,@Param("type")String type,@Param("coretype")String coretype,@Param("startLine")int startLine,@Param("endLine")int endLine){
		return dao.getNoParentStoreByOfficeId(officeid,name,type,coretype,startLine,endLine);
	}
	
	public List<Office> getStoreByOfficeId1(String officeid){
		return dao.getStoreByOfficeId1(officeid);
	}
	
	public List<Office> getOfficeByNameAndRole(String name,String roletype){
		return dao.getOfficeByNameAndRole(name,roletype);
	}
	
	public List<Office> getOfficeByNameAndType(String name,String type,String coretype){
		return dao.getOfficeByNameAndType(name,type,coretype);
	}
	
	public List<Office> getOfficeByRoleType(String roletype){
		return dao.getOfficeByRoleType(roletype);
	}
	
	public List<Office> getOfficeByOfficeId(String officeid){
		return dao.getOfficeByOfficeId(officeid);
	}
	
	public List<Office> getOfficeByOfficeId1(String officeid){
		return dao.getOfficeByOfficeId1(officeid);
	}
	
	public List<Office> getAllOffice(){
		return dao.getAllOffice();
	}
	
	public void deleteOfficeById(String id){
		dao.deleteOfficeById(id);
	}
	
	//门店数量
	public int getStoreByTypeCount(@Param("name")String name,@Param("type")String type,@Param("coretype")String coretype){
		return dao.getStoreByTypeCount(name,type,coretype);
	}
	
	public int getStoreByParentOfficeIdCount(@Param("officeid")String officeid,@Param("name")String name,@Param("type")String type,@Param("coretype")String coretype){
		return dao.getStoreByParentOfficeIdCount(officeid, name, type, coretype);
	}
	public int getStoreByNoParentOfficeIdCount(@Param("officeid")String officeid,@Param("name")String name,@Param("type")String type,@Param("coretype")String coretype){
		return dao.getStoreByNoParentOfficeIdCount(officeid, name, type, coretype);
	}
	//渠道商
	public List<Office> getOfficeList(@Param("name")String name,@Param("roletype")String roletype,@Param("startLine")int startLine,@Param("endLine")int endLine){
		return dao.getOfficeList(name, roletype, startLine, endLine);
	}
	public int getOfficeListCount(@Param("name")String name,@Param("roletype")String roletype){
		return dao.getOfficeListCount(name, roletype);
	}
	//渠道商 有节点的分页
	public List<Office> getParentOfficeByOfficeId(@Param("officeid")String officeid,@Param("name")String name,@Param("roletype")String roletype,@Param("startLine")int startLine,@Param("endLine")int endLine){
		return dao.getParentOfficeByOfficeId(officeid, name, roletype, startLine, endLine);
	}
	//有节点的渠道商的数量
	public int getOfficeParentOfficeIdCount(@Param("officeid")String officeid,@Param("name")String name,@Param("roletype")String roletype){
		return dao.getOfficeParentOfficeIdCount(officeid, name, roletype);
	}
	//渠道商 没有节点的分页
	public List<Office> getNoParentOfficeByOfficeId(@Param("officeid")String officeid,@Param("name")String name,@Param("roletype")String roletype,@Param("startLine")int startLine,@Param("endLine")int endLine){
		return dao.getNoParentOfficeByOfficeId(officeid, name, roletype, startLine, endLine);
	}
	//数量 没有节点的渠道商
	public int getOfficeNoParentOfficeIdCount(@Param("officeid")String officeid,@Param("name")String name,@Param("roletype")String roletype){
		return dao.getOfficeNoParentOfficeIdCount(officeid, name, roletype);
	}
}
