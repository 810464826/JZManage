package com.thinkgem.jeesite.modules.activity.signgoods.entity;

import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 系统登陆用户
 * @author 小石潭记丶
 *
 * 2017年8月6日
 */
public class SystemUserDb extends DataEntity<SystemUserDb>{

	private static final long serialVersionUID = 1L;
	//操作员ID
	private Number userId;
	//角色ID
	private Number roleId;
	//操作员_操作员ID
	private Number tbUserId;
	//操作员编码
	private String userCode;
	//操作员名称
	private String userName;
	//密码
	private String passWord;
	//更新时间
	private Date inputDate;
	//状态 0启用 1禁止
	private Number status;
	//用户类型 1管理员  2普通用户
	private Number userType;
	//操作员类别 1平台公司 2厂商 3经销商
	private Number targetType;
	//type=3 经销商ID
	private Number targetId;
	//平台权限 1web端 2pc端 3PDA，多个平台用逗号分隔
	private String platformRights;
	//部门ID
	private Number departmentId;
	//登陆手机号码
	private String phone;
	public Number getUserId() {
		return userId;
	}
	public void setUserId(Number userId) {
		this.userId = userId;
	}
	public Number getRoleId() {
		return roleId;
	}
	public void setRoleId(Number roleId) {
		this.roleId = roleId;
	}
	public Number getTbUserId() {
		return tbUserId;
	}
	public void setTbUserId(Number tbUserId) {
		this.tbUserId = tbUserId;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public Date getInputDate() {
		return inputDate;
	}
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}
	public Number getStatus() {
		return status;
	}
	public void setStatus(Number status) {
		this.status = status;
	}
	public Number getUserType() {
		return userType;
	}
	public void setUserType(Number userType) {
		this.userType = userType;
	}
	public Number getTargetType() {
		return targetType;
	}
	public void setTargetType(Number targetType) {
		this.targetType = targetType;
	}
	public Number getTargetId() {
		return targetId;
	}
	public void setTargetId(Number targetId) {
		this.targetId = targetId;
	}
	public String getPlatformRights() {
		return platformRights;
	}
	public void setPlatformRights(String platformRights) {
		this.platformRights = platformRights;
	}
	public Number getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Number departmentId) {
		this.departmentId = departmentId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
