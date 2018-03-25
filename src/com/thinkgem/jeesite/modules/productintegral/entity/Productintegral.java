/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.productintegral.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 产品积分关系Entity
 * @author cxb
 * @version 2017-10-30
 */
public class Productintegral extends DataEntity<Productintegral> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 产品名称
	private String volume;		// 容量
	private String degree;		// 度数
	private String spec;		// 规格
	private String integreal;		// 积分
	/*private String spareone;		// 备用字段一
	private String sparetwo;		// 备用字段二
*/	
	public Productintegral() {
		super();
	}

	public Productintegral(String id){
		super(id);
	}

	@Length(min=0, max=64, message="产品名称长度必须介于 0 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=64, message="容量长度必须介于 0 和 64 之间")
	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}
	
	@Length(min=0, max=64, message="度数长度必须介于 0 和 64 之间")
	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}
	
	@Length(min=0, max=64, message="规格长度必须介于 0 和 64 之间")
	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}
	
	@Length(min=0, max=64, message="积分长度必须介于 0 和 64 之间")
	public String getIntegreal() {
		return integreal;
	}

	public void setIntegreal(String integreal) {
		this.integreal = integreal;
	}
	
	/*@Length(min=0, max=64, message="备用字段一长度必须介于 0 和 64 之间")
	public String getSpareone() {
		return spareone;
	}

	public void setSpareone(String spareone) {
		this.spareone = spareone;
	}
	
	@Length(min=0, max=64, message="备用字段二长度必须介于 0 和 64 之间")
	public String getSparetwo() {
		return sparetwo;
	}

	public void setSparetwo(String sparetwo) {
		this.sparetwo = sparetwo;
	}*/
	
}