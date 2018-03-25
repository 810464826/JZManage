/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exportuser.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import javax.validation.constraints.NotNull;
/**
 * 经销商Entity
 * @author cxb
 * @version 2017-09-04
 */
/**
 * @author ChenXb
 *
 * 2017年9月8日
 */
public class Exportuser extends DataEntity<Exportuser> {
	
	private static final long serialVersionUID = 1L;
	private String companyallname;		// 经销商全称
	private String companyaddress;		// 经销商区域
	private String caddress;		// 经销商地址
	private String name;		// 联系人姓名
	private String phone;		// 联系人手机号
	private String parentcompany;		// 上级经销商(选填)
	private String usertype;		// 经销商类型 0系统管理员 1平台商 2一级分销商 3二级分销商 4门店
	private String remakes;		// 备注
	private String userid;      //用户id
	
	public Exportuser() {
		super();
	}

	public Exportuser(String id){
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

	@JsonIgnore
	@NotNull(message="经销商全称")
	@ExcelField(title="经销商全称", align=2, sort=30)
	public String getCompanyallname() {
		return companyallname;
	}

	public void setCompanyallname(String companyallname) {
		this.companyallname = companyallname;
	}
	
	@JsonIgnore
	@NotNull(message="经销商区域")
	@ExcelField(title="经销商区域", align=2, sort=40)
	public String getCompanyaddress() {
		return companyaddress;
	}

	public void setCompanyaddress(String companyaddress) {
		this.companyaddress = companyaddress;
	}
	
	@JsonIgnore
	@NotNull(message="经销商地址")
	@ExcelField(title="经销商地址", align=2, sort=45)
	public String getCaddress() {
		return caddress;
	}

	public void setCaddress(String caddress) {
		this.caddress = caddress;
	}
	
	@JsonIgnore
	@NotNull(message="经销商类型")
	@ExcelField(title="经销商类型", align=2, sort=50)
	public String getUsertype() {
		return usertype;
	}
	@JsonIgnore
	@NotNull(message="联系人姓名")
	@ExcelField(title="联系人姓名", align=2, sort=60)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JsonIgnore
	@NotNull(message="联系人手机号")
	@ExcelField(title="联系人手机号", align=2, sort=65)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@JsonIgnore
	@ExcelField(title="上级经销商(选填)", align=2, sort=70)
	public String getParentcompany() {
		return parentcompany;
	}

	public void setParentcompany(String parentcompany) {
		this.parentcompany = parentcompany;
	}
	

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	
	@JsonIgnore
	@ExcelField(title="备注(选填)", align=2, sort=75)
	public String getRemakes() {
		return remakes;
	}

	public void setRemakes(String remakes) {
		this.remakes = remakes;
	}
	
}