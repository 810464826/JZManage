/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.salesman.entity;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 业务员Entity
 * @author cxb
 * @version 2017-09-04
 */
public class Salesman extends DataEntity<Salesman> {
	
	private static final long serialVersionUID = 1L;
	private String officeid;		// 所属店的id
	private String officename;		// 所属店的名称
	private String name;		// 业务员名称
	private String phone;		// 业务员联系电话
	private String remakes;		// 备注
	private String manangeofficeid;		// 管理公司id
	private String manangeofficename;		// 管理公司名称
	private String userId;   //用户id
	
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Salesman() {
		super();
	}

	public Salesman(String id){
		super(id);
	}
	
	public String getManangeofficeid() {
		return manangeofficeid;
	}

	public void setManangeofficeid(String manangeofficeid) {
		this.manangeofficeid = manangeofficeid;
	}

	public String getManangeofficename() {
		return manangeofficename;
	}

	public void setManangeofficename(String manangeofficename) {
		this.manangeofficename = manangeofficename;
	}

	//	@JsonIgnore
//	@NotNull(message="所属店的id")
//	@ExcelField(title="所属店的id", align=2, sort=30)
	public String getOfficeid() {
		return officeid;
	}

	public void setOfficeid(String officeid) {
		this.officeid = officeid;
	}
	
	@JsonIgnore
	@NotNull(message="所属店的名称")
	@ExcelField(title="所属店的名称", align=2, sort=35)
	public String getOfficename() {
		return officename;
	}

	public void setOfficename(String officename) {
		this.officename = officename;
	}
	
	@JsonIgnore
	@NotNull(message="业务员名称")
	@ExcelField(title="业务员名称", align=2, sort=40)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JsonIgnore
	@NotNull(message="业务员联系电话")
	@ExcelField(title="业务员联系电话", align=2, sort=45)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@JsonIgnore
	@NotNull(message="备注(选填)")
	@ExcelField(title="备注(选填)", align=2, sort=50)
	public String getRemakes() {
		return remakes;
	}

	public void setRemakes(String remakes) {
		this.remakes = remakes;
	}
	
}