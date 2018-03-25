/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.activity.signgoods.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.activity.signgoods.entity.SellerOutInfo;
import com.thinkgem.jeesite.modules.activity.signgoods.dao.SellerOutInfoDao;

/**
 * 扫描发货Service
 * @author tanghaobo
 * @version 2017-04-27
 */
@Service
@Transactional(readOnly = true)
public class SellerOutInfoService extends CrudService<SellerOutInfoDao, SellerOutInfo> {

	@Autowired
	SellerOutInfoDao sellerOutInfoDao;
	
	public List<SellerOutInfo> getRecsellercodeByOutNumber(String phone,int startLine,int endLine){
		return sellerOutInfoDao.getRecsellercodeByOutNumber(phone,startLine,endLine);
	}
	public SellerOutInfo getOrderByRecordId(String recordId){
		return sellerOutInfoDao.getOrderByRecordId(recordId);
	}
	public List<SellerOutInfo> getRecsellercodeAdmin(int startLine,int endLine){
		return sellerOutInfoDao.getRecsellercodeAdmin(startLine,endLine);
	}
	/**
	 *不是管理员 普通用户的查询
	 */
	public List<SellerOutInfo> getSellerOutInfoByConditionByPhone(String outNo,String sellerName,String startTime,String endTime,String phone,int startLine,int endLine){
		return sellerOutInfoDao.getSellerOutInfoByConditionByPhone(outNo, sellerName, startTime, endTime,phone,startLine,endLine);
	}
	
	/**
	 *管理员不需要手机号码查询
	 */
	public List<SellerOutInfo> getSellerOutInfoByCondition(String outNo,String sellerName,String startTime,String endTime,int startLine,int endLine){
		return sellerOutInfoDao.getSellerOutInfoByCondition(outNo, sellerName, startTime, endTime,startLine,endLine);
	}
	
	public List<SellerOutInfo> getSellerOutInfoByCondition(){
		return sellerOutInfoDao.getSellerOutInfoByCondition();
	}
	@Transactional(readOnly = false)
	public void updateSellerInfoOn(String recordId){
		sellerOutInfoDao.updateSellerInfoOn(recordId);
	}
	@Transactional(readOnly = false)
	public void updateSellerInfoOut(String recordId){
		sellerOutInfoDao.updateSellerInfoOut(recordId);
	}
	/**
	 * 获取运单页数 查询的时候 普通用户
	 */
	public int getSellInfoCountByPhoneSearch(@Param("outNo")String outNo,@Param("phone")String phone,@Param("sellerName")String sellerName,@Param("startTime")String startTime,@Param("endTime")String endTime){
		return sellerOutInfoDao.getSellInfoCountByPhoneSearch(outNo,phone,sellerName,startTime,endTime);
	}
	/**
	 * 获取运单页数 查询的时候 管理员 不需要手机号码
	 */
	public int getAllSellInfoCountSearch(@Param("outNo")String outNo,@Param("sellerName")String sellerName,@Param("startTime")String startTime,@Param("endTime")String endTime){
		return sellerOutInfoDao.getAllSellInfoCountSearch(outNo,sellerName,startTime,endTime);
	}
	
	/**
	 * 获取运单页数 进入的时候直接查 普通用户  需要手机
	 */
	public int getSellInfoCountByPhone(@Param("phone")String phone){
		return sellerOutInfoDao.getSellInfoCountByPhone(phone);
	}
	/**
	 * 获取运单页数 进入的时候直接查 管理员不需要手机号码
	 */
	public int getAllSellInfoCount(){
		return sellerOutInfoDao.getAllSellInfoCount();
	}
	
	/**
	 * 通过产品名称查询运单号  管理员
	 */
	public List<SellerOutInfo> getSellerOutInfoByProductName(@Param("productname")String productname,@Param("startLine")int startLine,@Param("endLine")int endLine){
		return sellerOutInfoDao.getSellerOutInfoByProductName(productname,startLine,endLine);
	}
	
	/**
	 * 通过产品名称查询运单号  普通登录者
	 */
	public List<SellerOutInfo> getSellerOutInfoByProductNamePhone(@Param("phone")String phone,@Param("productname")String productname,@Param("startLine")int startLine,@Param("endLine")int endLine){
		return sellerOutInfoDao.getSellerOutInfoByProductNamePhone(phone,productname,startLine,endLine);
	}
	
	/**
	 * 这里是获取总数据条数的  管理员
	 */
	public int getSellerCountByProductName(@Param("productname")String productname){
		return sellerOutInfoDao.getSellerCountByProductName(productname);
	}
	
	/**
	 * 这里是获取总数据条数的  普通登录者
	 */
	public int getSellerCountByProductNamePhone(@Param("productName")String productname,@Param("phone")String phone){
		return sellerOutInfoDao.getSellerCountByProductNamePhone(productname,phone);
	}
}