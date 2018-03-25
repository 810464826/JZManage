/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cancel.localcancel.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 核销统计Entity
 * @author tanghaobo
 * @version 2017-07-06
 */
public class LocalCancel extends DataEntity<LocalCancel> {
	
	private static final long serialVersionUID = 1L;
	private String activityName;		// 活动名称
	private String activityPrizesAddress;		// 活动所属地址
	private String activityPrizesId;		// 活动所属经销商
	private String activityId;		// 活动ID
	private String cancelUser;		// 核销人
	private String cancelAddress;		// 核销人所属地址
	private String prizeName;		// 奖品名称
	private String prizeId;
	private String cancelNumber;		// 核销数量
	private String activityPrizesOffice;		// 活动经销商
	private String cancelOffice;		// 核销人经销商
	private String cancelOpenid;		// 核销人openid
	private String checkState;		// 对账  0未对账  1已对账
	private String checkUser;		// 对账人
	private String existence;		// 对账人
	
	private String cancelUserName;   //核销人名字
	private String cancelUserPhone;  //核销人电话
	
	private String createTime;
	private String startTime;
	private String endTime;
	private String activityAddress;
	private String cancelAddressName;
	
	
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

	/**
	 * @return the activityAddress
	 */
	public String getActivityAddress() {
		return activityAddress;
	}

	/**
	 * @param activityAddress the activityAddress to set
	 */
	public void setActivityAddress(String activityAddress) {
		this.activityAddress = activityAddress;
	}

	/**
	 * @return the cancelAddressName
	 */
	public String getCancelAddressName() {
		return cancelAddressName;
	}

	/**
	 * @param cancelAddressName the cancelAddressName to set
	 */
	public void setCancelAddressName(String cancelAddressName) {
		this.cancelAddressName = cancelAddressName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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
	
	public LocalCancel() {
		super();
	}

	public LocalCancel(String id){
		super(id);
	}

	@Length(min=0, max=255, message="活动名称长度必须介于 0 和 255 之间")
	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	
	@Length(min=0, max=255, message="活动所属地址长度必须介于 0 和 255 之间")
	public String getActivityPrizesAddress() {
		return activityPrizesAddress;
	}

	public void setActivityPrizesAddress(String activityPrizesAddress) {
		this.activityPrizesAddress = activityPrizesAddress;
	}
	
	public String getPrizeId() {
		return prizeId;
	}

	public void setPrizeId(String prizeId) {
		this.prizeId = prizeId;
	}
	public String getExistence() {
		return existence;
	}

	public void setExistence(String existence) {
		this.existence = existence;
	}

	@Length(min=0, max=255, message="活动所属经销商长度必须介于 0 和 255 之间")
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
	
	@Length(min=0, max=255, message="核销人所属地址长度必须介于 0 和 255 之间")
	public String getCancelAddress() {
		return cancelAddress;
	}

	public void setCancelAddress(String cancelAddress) {
		this.cancelAddress = cancelAddress;
	}
	
	@Length(min=0, max=255, message="奖品名称长度必须介于 0 和 255 之间")
	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}
	
	@Length(min=0, max=255, message="核销数量长度必须介于 0 和 255 之间")
	public String getCancelNumber() {
		return cancelNumber;
	}

	public void setCancelNumber(String cancelNumber) {
		this.cancelNumber = cancelNumber;
	}
	
	@Length(min=0, max=255, message="活动经销商长度必须介于 0 和 255 之间")
	public String getActivityPrizesOffice() {
		return activityPrizesOffice;
	}

	public void setActivityPrizesOffice(String activityPrizesOffice) {
		this.activityPrizesOffice = activityPrizesOffice;
	}
	
	@Length(min=0, max=255, message="核销人经销商长度必须介于 0 和 255 之间")
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
	
	@Length(min=0, max=255, message="对账  0未对账  1已对账长度必须介于 0 和 255 之间")
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