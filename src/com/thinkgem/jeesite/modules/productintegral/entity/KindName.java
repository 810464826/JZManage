package com.thinkgem.jeesite.modules.productintegral.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * @author ChenXb
 * 品名
 * 2017年10月30日
 */
public class KindName extends DataEntity<KindName>{
	private static final long serialVersionUID = 5328978039287962523L;
	private String kindNameId;
	private String kindName;
	
	/**
	 * @return the kindNameId
	 */
	public String getKindNameId() {
		return kindNameId;
	}
	/**
	 * @param kindNameId the kindNameId to set
	 */
	public void setKindNameId(String kindNameId) {
		this.kindNameId = kindNameId;
	}
	/**
	 * @return the kindName
	 */
	public String getKindName() {
		return kindName;
	}
	/**
	 * @param kindName the kindName to set
	 */
	public void setKindName(String kindName) {
		this.kindName = kindName;
	}
	
}
