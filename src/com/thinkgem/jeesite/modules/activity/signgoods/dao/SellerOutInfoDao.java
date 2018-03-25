/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.activity.signgoods.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.activity.signgoods.entity.SellerOutInfo;

/**
 * 扫描发货DAO接口
 * @author tanghaobo
 * @version 2017-04-27
 */
@MyBatisDao
public interface SellerOutInfoDao extends CrudDao<SellerOutInfo> {
	List<SellerOutInfo> getRecsellercodeByOutNumber(@Param("phone")String phone,@Param("startLine")int startLine,@Param("endLine")int endLine);
	SellerOutInfo getOrderByRecordId(String recordId);
	List<SellerOutInfo> getRecsellercodeAdmin(@Param("startLine")int startLine,@Param("endLine")int endLine);
	//<!--普通用户的查询 根据手机号码查自己的  -->
	List<SellerOutInfo> getSellerOutInfoByConditionByPhone(@Param("outNo")String outNo,@Param("sellerName")String sellerName,@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("phone")String phone,@Param("startLine")int startLine,@Param("endLine")int endLine);
	//<!--管理员查询不需要电话号码  管理员查询1-->
	List<SellerOutInfo> getSellerOutInfoByCondition(@Param("outNo")String outNo,@Param("sellerName")String sellerName,@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("startLine")int startLine,@Param("endLine")int endLine);
	List<SellerOutInfo> getSellerOutInfoByCondition();
	public int updateSellerInfoOn(String recordId);
	public int updateSellerInfoOut(String recordId);
	//<!--获取所有的页数   当前登录人 这里是查询的时候   查询带-->
	public int getSellInfoCountByPhoneSearch(@Param("outNo")String outNo,@Param("phone")String phone,@Param("sellerName")String sellerName,@Param("startTime")String startTime,@Param("endTime")String endTime);
	//<!--获取所有的页数  这里是查询的时候    所有跟查询有关的都有1-->
	public int getAllSellInfoCountSearch(@Param("outNo")String outNo,@Param("sellerName")String sellerName,@Param("startTime")String startTime,@Param("endTime")String endTime);
	//<!--获取所有的页数   当前登录人  -->
	public int getSellInfoCountByPhone(@Param("phone")String phone);
	//<!--获取所有的页数  不是查询时候getAllSellInfoCount-->
	public int getAllSellInfoCount();
	
	/**
	 * 通过产品名称查询运单号  管理员
	 */
	List<SellerOutInfo> getSellerOutInfoByProductName(@Param("productname")String productname,@Param("startLine")int startLine,@Param("endLine")int endLine);
	/**
	 * 通过产品名称查询运单号  普通登陆者
	 */
	List<SellerOutInfo> getSellerOutInfoByProductNamePhone(@Param("phone")String phone,@Param("productname")String productname,@Param("startLine")int startLine,@Param("endLine")int endLine);
	/**
	 * 这里是获取总数据条数的  管理员
	 */
	public int getSellerCountByProductName(@Param("productname")String productname);
	
	/**
	 * 这里是获取总数据条数的  普通登录者
	 */
	public int getSellerCountByProductNamePhone(@Param("productname")String productname,@Param("phone")String phone);
}