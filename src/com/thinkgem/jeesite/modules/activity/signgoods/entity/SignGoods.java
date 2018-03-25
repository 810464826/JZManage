/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.activity.signgoods.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 扫描发货Entity
 * @author tanghaobo
 * @version 2017-04-27
 */
public class SignGoods extends DataEntity<SignGoods> {
	
	private static final long serialVersionUID = 1L;
	private String goodsNumber;		// 发货单号
	private String dealerName;		// 渠道商名称
	private String varieties;		// 品种名称
	private String totalBox;		// 总箱数
	private String createTime;		// 创建时间
	private String activityReportId;		// 活动报备ID
	private String disreibutorId;		// 经销商ID
	private String goodsAddress;		//发货地址
	private String state;		//0未删除  1已删除
	private String poductsize;		//规格，一箱多少瓶
	private String recordId;    //记录ID
	private String outTime;		// 发货时间
	private String startTime;   //发货开始时间查询
	private String endTime;     //发货结束时间查询
	
	/**
	 * @return the outTime
	 */
	public String getOutTime() {
		return outTime;
	}

	/**
	 * @param outTime the outTime to set
	 */
	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	/**
	 * @return the recordId
	 */
	public String getRecordId() {
		return recordId;
	}

	/**
	 * @param recordid the recordId to set
	 */
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getPoductsize() {
		return poductsize;
	}

	public void setPoductsize(String poductsize) {
		this.poductsize = poductsize;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public SignGoods() {
		super();
	}

	public SignGoods(String id){
		super(id);
	}

	public String getGoodsNumber() {
		return goodsNumber;
	}

	public void setGoodsNumber(String goodsNumber) {
		this.goodsNumber = goodsNumber;
	}
	
	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}
	
	public String getVarieties() {
		return varieties;
	}

	public void setVarieties(String varieties) {
		this.varieties = varieties;
	}
	
	public String getTotalBox() {
		return totalBox;
	}

	public void setTotalBox(String totalBox) {
		this.totalBox = totalBox;
	}
	
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	public String getActivityReportId() {
		return activityReportId;
	}

	public void setActivityReportId(String activityReportId) {
		this.activityReportId = activityReportId;
	}
	
	public String getDisreibutorId() {
		return disreibutorId;
	}

	public void setDisreibutorId(String disreibutorId) {
		this.disreibutorId = disreibutorId;
	}

	public String getGoodsAddress() {
		return goodsAddress;
	}

	public void setGoodsAddress(String goodsAddress) {
		this.goodsAddress = goodsAddress;
	}
	
}