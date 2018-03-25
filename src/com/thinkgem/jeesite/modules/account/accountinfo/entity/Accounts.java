/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.account.accountinfo.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 对账管理Entity
 * @author tanghaobo
 * @version 2017-06-27
 */
public class Accounts extends DataEntity<Accounts> {
	
	private static final long serialVersionUID = 1L;
	private String accountsUserId;		// 对账发起人
	private String accountsDistributorId;		// 对账发起经销商
	private String accountsConfirmId;		// 确认对账人
	private String accountsConfirmDistributorI;		// 确认对账经销商
	private Date accountsTime;		// 对账时间
	private String state;		// 状态
	private String activityId;		// 活动ID
	private String prizeId;		// 奖品ID
	private String prizeName;		// 奖品名称
	private String cancelNumber;		// 核销数量
	private String cancelId;		// 核销记录ID
	private String accountsSubordinate;		// 向下级对账记录
	private String spareOne;		// 备用一
	private String spareTwo;		// 备用二
	private String selecttime;		// 添加对账时 选择的时间
	private String salesman;		// 对账的业务员D
	private String salesname;       //对账业务员名称
	private Date startTime;
	private Date endTime;
	
	/**
	 * @return the salesname
	 */
	public String getSalesname() {
		return salesname;
	}

	/**
	 * @param salesname the salesname to set
	 */
	public void setSalesname(String salesname) {
		this.salesname = salesname;
	}

	/**
	 * @return the selecttime
	 */
	public String getSelecttime() {
		return selecttime;
	}

	/**
	 * @param selecttime the selecttime to set
	 */
	public void setSelecttime(String selecttime) {
		this.selecttime = selecttime;
	}

	/**
	 * @return the salesman
	 */
	public String getSalesman() {
		return salesman;
	}

	/**
	 * @param salesman the salesman to set
	 */
	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Accounts() {
		super();
	}

	public Accounts(String id){
		super(id);
	}

	@Length(min=0, max=255, message="对账发起人长度必须介于 0 和 255 之间")
	public String getAccountsUserId() {
		return accountsUserId;
	}

	public void setAccountsUserId(String accountsUserId) {
		this.accountsUserId = accountsUserId;
	}
	
	@Length(min=0, max=255, message="对账发起经销商长度必须介于 0 和 255 之间")
	public String getAccountsDistributorId() {
		return accountsDistributorId;
	}

	public void setAccountsDistributorId(String accountsDistributorId) {
		this.accountsDistributorId = accountsDistributorId;
	}
	
	@Length(min=0, max=255, message="确认对账人长度必须介于 0 和 255 之间")
	public String getAccountsConfirmId() {
		return accountsConfirmId;
	}

	public void setAccountsConfirmId(String accountsConfirmId) {
		this.accountsConfirmId = accountsConfirmId;
	}
	
	@Length(min=0, max=255, message="确认对账经销商长度必须介于 0 和 255 之间")
	public String getAccountsConfirmDistributorI() {
		return accountsConfirmDistributorI;
	}

	public void setAccountsConfirmDistributorI(String accountsConfirmDistributorI) {
		this.accountsConfirmDistributorI = accountsConfirmDistributorI;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAccountsTime() {
		return accountsTime;
	}

	public void setAccountsTime(Date accountsTime) {
		this.accountsTime = accountsTime;
	}
	
	@Length(min=0, max=255, message="状态长度必须介于 0 和 255 之间")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	@Length(min=0, max=64, message="活动ID长度必须介于 0 和 64 之间")
	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	
	@Length(min=0, max=64, message="奖品ID长度必须介于 0 和 64 之间")
	public String getPrizeId() {
		return prizeId;
	}

	public void setPrizeId(String prizeId) {
		this.prizeId = prizeId;
	}
	
	@Length(min=0, max=64, message="奖品名称长度必须介于 0 和 64 之间")
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
	
	@Length(min=0, max=64, message="核销记录ID长度必须介于 0 和 64 之间")
	public String getCancelId() {
		return cancelId;
	}

	public void setCancelId(String cancelId) {
		this.cancelId = cancelId;
	}
	
	@Length(min=0, max=255, message="向下级对账记录长度必须介于 0 和 255 之间")
	public String getAccountsSubordinate() {
		return accountsSubordinate;
	}

	public void setAccountsSubordinate(String accountsSubordinate) {
		this.accountsSubordinate = accountsSubordinate;
	}
	
	@Length(min=0, max=255, message="备用一长度必须介于 0 和 255 之间")
	public String getSpareOne() {
		return spareOne;
	}

	public void setSpareOne(String spareOne) {
		this.spareOne = spareOne;
	}
	
	@Length(min=0, max=255, message="备用二长度必须介于 0 和 255 之间")
	public String getSpareTwo() {
		return spareTwo;
	}

	public void setSpareTwo(String spareTwo) {
		this.spareTwo = spareTwo;
	}
	
}