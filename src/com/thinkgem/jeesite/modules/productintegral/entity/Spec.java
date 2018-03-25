package com.thinkgem.jeesite.modules.productintegral.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * @author ChenXb
 * 规格
 * 2017年10月30日
 */
public class Spec extends DataEntity<Spec>{
	private static final long serialVersionUID = 1482666994712477021L;
	private String specId;
	private String spec;
	
	/**
	 * @return the specId
	 */
	public String getSpecId() {
		return specId;
	}
	/**
	 * @param specId the specId to set
	 */
	public void setSpecId(String specId) {
		this.specId = specId;
	}
	/**
	 * @return the spec
	 */
	public String getSpec() {
		return spec;
	}
	/**
	 * @param spec the spec to set
	 */
	public void setSpec(String spec) {
		this.spec = spec;
	}
	
}
