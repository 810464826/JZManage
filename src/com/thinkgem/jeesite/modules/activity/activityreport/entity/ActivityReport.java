/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.activity.activityreport.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 活动报备Entity
 * @author tanghaobo
 * @version 2017-03-16
 */
public class ActivityReport extends DataEntity<ActivityReport> {
	
	private static final long serialVersionUID = 1L;
	private String activityName;		// 活动名称
	private String activityAddress;		// 活动地址
	private Date createTime;		// 创建时间
	private Date updateTime;		// 修改时间
	private String applyUser;		// 申请人
	private String examineUser;		// 最后审核人
	private String state;		// 状态
	private String applyofficeid; //申请人的公司id
	
	/**
	 * @return the applyofficeid
	 */
	public String getApplyofficeid() {
		return applyofficeid;
	}

	/**
	 * @param applyofficeid the applyofficeid to set
	 */
	public void setApplyofficeid(String applyofficeid) {
		this.applyofficeid = applyofficeid;
	}

	public ActivityReport() {
		super();
	}

	public ActivityReport(String id){
		super(id);
	}

	@Length(min=1, max=255, message="活动名称长度必须介于 1 和 255 之间")
	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	
	@Length(min=1, max=255, message="活动地址长度必须介于 1 和 255 之间")
	public String getActivityAddress() {
		return activityAddress;
	}

	public void setActivityAddress(String activityAddress) {
		this.activityAddress = activityAddress;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="创建时间不能为空")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="修改时间不能为空")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@Length(min=1, max=255, message="申请人长度必须介于 1 和 255 之间")
	public String getApplyUser() {
		return applyUser;
	}

	public void setApplyUser(String applyUser) {
		this.applyUser = applyUser;
	}
	
	@Length(min=0, max=255, message="最后审核人长度必须介于 0 和 255 之间")
	public String getExamineUser() {
		return examineUser;
	}

	public void setExamineUser(String examineUser) {
		this.examineUser = examineUser;
	}
	
	@Length(min=0, max=11, message="状态长度必须介于 0 和 11 之间")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}