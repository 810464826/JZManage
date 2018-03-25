/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cancel.remotecancel.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 异地核销Entity
 * @author tanghaobo
 * @version 2017-06-18
 */
public class RemoteCancel extends DataEntity<RemoteCancel> {
	
	private static final long serialVersionUID = 1L;
	private String activityName;		// 活动名称
	private String activityPrizesAddress;		// 活动所属区域
	private String activityPrizesId;		// 活动申请人
	private String activityId;		// 活动ID
	private String cancelUser;		// 核销人
	private String cancelAddress;		// 核销经销商区域
	private String goodsNumber;		// 运单编号
	private Long cancelNumber;		// 核销数量
	private String prizeName;		// 奖品名称
	private String cancelUserName;   //核销人名字
	private String cancelUserPhone;  //核销人电话
	private String createTime;		// 时间
	private String activityPrizesOffice;		// 活动所属经销商
	private String cancelOffice;		// 核销经销商
	private String cancelOpenid;		// 核销人openid
	private String checkState;		// 对账
	private String checkUser;		// 对账人
	
	private String startTime;
	private String endTime;
	
	
	/**
	 * @return the cancelUserName
	 */
	public String getCancelUserName() {
		return cancelUserName;
	}

	/**
	 * @param cancelUserName the cancelUserName to set
	 */
	public void setCancelUserName(String cancelUserName) {
		this.cancelUserName = cancelUserName;
	}

	/**
	 * @return the cancelUserPhone
	 */
	public String getCancelUserPhone() {
		return cancelUserPhone;
	}

	/**
	 * @param cancelUserPhone the cancelUserPhone to set
	 */
	public void setCancelUserPhone(String cancelUserPhone) {
		this.cancelUserPhone = cancelUserPhone;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public RemoteCancel() {
		super();
	}

	public RemoteCancel(String id){
		super(id);
	}

	@Length(min=0, max=255, message="活动名称长度必须介于 0 和 255 之间")
	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Length(min=0, max=255, message="活动所属区域长度必须介于 0 和 255 之间")
	public String getActivityPrizesAddress() {
		return activityPrizesAddress;
	}

	public void setActivityPrizesAddress(String activityPrizesAddress) {
		this.activityPrizesAddress = activityPrizesAddress;
	}
	
	@Length(min=0, max=255, message="活动申请人长度必须介于 0 和 255 之间")
	public String getActivityPrizesId() {
		return activityPrizesId;
	}

	public void setActivityPrizesId(String activityPrizesId) {
		this.activityPrizesId = activityPrizesId;
	}
	
	@Length(min=0, max=255, message="活动ID长度必须介于 0 和 255 之间")
	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	
	@Length(min=0, max=255, message="核销人长度必须介于 0 和 255 之间")
	public String getCancelUser() {
		return cancelUser;
	}

	public void setCancelUser(String cancelUser) {
		this.cancelUser = cancelUser;
	}
	
	@Length(min=0, max=255, message="核销经销商区域长度必须介于 0 和 255 之间")
	public String getCancelAddress() {
		return cancelAddress;
	}

	public void setCancelAddress(String cancelAddress) {
		this.cancelAddress = cancelAddress;
	}
	
	@Length(min=0, max=255, message="运单编号长度必须介于 0 和 255 之间")
	public String getGoodsNumber() {
		return goodsNumber;
	}

	public void setGoodsNumber(String goodsNumber) {
		this.goodsNumber = goodsNumber;
	}
	
	public Long getCancelNumber() {
		return cancelNumber;
	}

	public void setCancelNumber(Long cancelNumber) {
		this.cancelNumber = cancelNumber;
	}
	
	@Length(min=0, max=255, message="奖品名称长度必须介于 0 和 255 之间")
	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}
	
	@Length(min=0, max=255, message="活动所属经销商长度必须介于 0 和 255 之间")
	public String getActivityPrizesOffice() {
		return activityPrizesOffice;
	}

	public void setActivityPrizesOffice(String activityPrizesOffice) {
		this.activityPrizesOffice = activityPrizesOffice;
	}
	
	@Length(min=0, max=255, message="核销经销商长度必须介于 0 和 255 之间")
	public String getCancelOffice() {
		return cancelOffice;
	}

	public void setCancelOffice(String cancelOffice) {
		this.cancelOffice = cancelOffice;
	}
	
	@Length(min=0, max=255, message="核销人openid长度必须介于 0 和 255 之间")
	public String getCancelOpenid() {
		return cancelOpenid;
	}

	public void setCancelOpenid(String cancelOpenid) {
		this.cancelOpenid = cancelOpenid;
	}
	
	@Length(min=0, max=255, message="对账长度必须介于 0 和 255 之间")
	public String getCheckState() {
		return checkState;
	}

	public void setCheckState(String checkState) {
		this.checkState = checkState;
	}
	
	@Length(min=0, max=255, message="对账人长度必须介于 0 和 255 之间")
	public String getCheckUser() {
		return checkUser;
	}

	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}
	
}