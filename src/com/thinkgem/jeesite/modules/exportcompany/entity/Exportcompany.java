/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exportcompany.entity;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 终端门店Entity
 * @author cxb
 * @version 2017-09-04
 */
public class Exportcompany extends DataEntity<Exportcompany> {
	
	private static final long serialVersionUID = 1L;
	private String companyallname;		// 店铺全称
	private String companyaddress;		// 所属区域
	private String caddress;		// 地址
	private String companytype;		// 店铺类型
	private String coretype;		// 核心类型 1核心店 2非核心店
	private String name;		// 联系人名称
	private String phone;		// 联系人手机号
	private String parentcompany;		// 上级经销商
	private String salesmanname;		// 业务员名称
	private String salesmanphone;		// 业务员手机号
	private String remakes;		// 备注
	private String officeid;    //公司id
	private String userid;    //userid
	
	public Exportcompany() {
		super();
	}

	public Exportcompany(String id){
		super(id);
	}
	
	/**
	 * @return the userid
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * @param userid the userid to set
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}

	/**
	 * @return the officeid
	 */
	public String getOfficeid() {
		return officeid;
	}

	/**
	 * @param officeid the officeid to set
	 */
	public void setOfficeid(String officeid) {
		this.officeid = officeid;
	}

	@JsonIgnore
	@NotNull(message="店铺全称")
	@ExcelField(title="店铺全称", align=2, sort=30)
	public String getCompanyallname() {
		return companyallname;
	}

	public void setCompanyallname(String companyallname) {
		this.companyallname = companyallname;
	}
	
	@JsonIgnore
	@NotNull(message="所属区域")
	@ExcelField(title="所属区域", align=2, sort=35)
	public String getCompanyaddress() {
		return companyaddress;
	}

	public void setCompanyaddress(String companyaddress) {
		this.companyaddress = companyaddress;
	}
	
	@JsonIgnore
	@NotNull(message="地址")
	@ExcelField(title="地址", align=2, sort=40)
	public String getCaddress() {
		return caddress;
	}

	public void setCaddress(String caddress) {
		this.caddress = caddress;
	}
	
	@JsonIgnore
	@NotNull(message="店铺类型")
	@ExcelField(title="店铺类型", align=2, sort=45)
	public String getCompanytype() {
		return companytype;
	}

	public void setCompanytype(String companytype) {
		this.companytype = companytype;
	}
	
	@JsonIgnore
	@NotNull(message="核心类型")
	@ExcelField(title="核心类型", align=2, sort=50)
	public String getCoretype() {
		return coretype;
	}

	public void setCoretype(String coretype) {
		this.coretype = coretype;
	}
	
	@JsonIgnore
	@NotNull(message="联系人名称")
	@ExcelField(title="联系人名称", align=2, sort=55)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JsonIgnore
	@NotNull(message="联系人手机号")
	@ExcelField(title="联系人手机号", align=2, sort=60)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@JsonIgnore
	@NotNull(message="上级经销商(选填)")
	@ExcelField(title="上级经销商(选填)", align=2, sort=65)
	public String getParentcompany() {
		return parentcompany;
	}

	public void setParentcompany(String parentcompany) {
		this.parentcompany = parentcompany;
	}
	
	@JsonIgnore
	@NotNull(message="业务员名称")
	@ExcelField(title="业务员名称", align=2, sort=70)
	public String getSalesmanname() {
		return salesmanname;
	}

	public void setSalesmanname(String salesmanname) {
		this.salesmanname = salesmanname;
	}
	
	@JsonIgnore
	@NotNull(message="业务员手机号")
	@ExcelField(title="业务员手机号", align=2, sort=75)
	public String getSalesmanphone() {
		return salesmanphone;
	}

	public void setSalesmanphone(String salesmanphone) {
		this.salesmanphone = salesmanphone;
	}
	
	@JsonIgnore
	@NotNull(message="备注(选填)")
	@ExcelField(title="备注(选填)", align=2, sort=80)
	public String getRemakes() {
		return remakes;
	}

	public void setRemakes(String remakes) {
		this.remakes = remakes;
	}
	
}