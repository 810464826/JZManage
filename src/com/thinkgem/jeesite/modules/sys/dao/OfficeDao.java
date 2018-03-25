/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.TreeDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 机构DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {
	List<Office> officeByParent(@Param("parent")String parent);
	Office getOfficeByName(String name);
	Office getOfficeByParentId(String parentId);
	Office getOfficeById(String id);
	List<Office> getStoreByType(@Param("name")String name,@Param("type")String type,@Param("coretype")String coretype,@Param("startLine")int startLine,@Param("endLine")int endLine);
	//这里是因为点击的是不是父节点  或者子节点
	public List<Office> getStoreByOfficeId(@Param("officeid")String officeid,@Param("name")String name,@Param("type")String type,@Param("coretype")String coretype,@Param("startLine")int startLine,@Param("endLine")int endLine);
	//没有节点
	public List<Office> getNoParentStoreByOfficeId(@Param("officeid")String officeid,@Param("name")String name,@Param("type")String type,@Param("coretype")String coretype,@Param("startLine")int startLine,@Param("endLine")int endLine);
	public List<Office> getStoreByOfficeId1(String officeid);
	
	public List<Office> getOfficeByOfficeId(String officeid);
	public List<Office> getOfficeByOfficeId1(String officeid);
	//这里是查询的office
	public List<Office> getOfficeByNameAndRole(@Param("name")String name,@Param("roletype")String roletype);
	public List<Office> getOfficeByOfficeName(String name);
	public List<Office> getOfficeByRoleType(String roletype);
	//门店查询
	public List<Office> getOfficeByNameAndType(@Param("name")String name,@Param("type")String type,@Param("coretype")String coretype);
	
	public List<Office> getAllOffice();
	public void deleteOfficeById(String id);
	//门店数量
	public int getStoreByTypeCount(@Param("name")String name,@Param("type")String type,@Param("coretype")String coretype);
	//根据officeid查询门店
	public int getStoreByParentOfficeIdCount(@Param("officeid")String officeid,@Param("name")String name,@Param("type")String type,@Param("coretype")String coretype);
	public int getStoreByNoParentOfficeIdCount(@Param("officeid")String officeid,@Param("name")String name,@Param("type")String type,@Param("coretype")String coretype);
	//获取渠道商的信息   name,type,startLine,endLine
	public List<Office> getOfficeList(@Param("name")String name,@Param("roletype")String roletype,@Param("startLine")int startLine,@Param("endLine")int endLine);
	public int getOfficeListCount(@Param("name")String name,@Param("roletype")String roletype);
	//渠道商 有节点的分页
	public List<Office> getParentOfficeByOfficeId(@Param("officeid")String officeid,@Param("name")String name,@Param("roletype")String roletype,@Param("startLine")int startLine,@Param("endLine")int endLine);
	//数量 有节点的渠道商
	public int getOfficeParentOfficeIdCount(@Param("officeid")String officeid,@Param("name")String name,@Param("roletype")String roletype);
	//渠道商 没有节点的分页
	public List<Office> getNoParentOfficeByOfficeId(@Param("officeid")String officeid,@Param("name")String name,@Param("roletype")String roletype,@Param("startLine")int startLine,@Param("endLine")int endLine);
	//数量 没有节点的渠道商
	public int getOfficeNoParentOfficeIdCount(@Param("officeid")String officeid,@Param("name")String name,@Param("roletype")String roletype);
}
