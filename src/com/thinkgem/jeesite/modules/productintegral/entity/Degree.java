package com.thinkgem.jeesite.modules.productintegral.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * @author ChenXb
 * 度数
 * 2017年10月30日
 */
public class Degree extends DataEntity<Degree>{
	private static final long serialVersionUID = -4304847405526850884L;
	private String degreeId;
	private String degree;
	
	/**
	 * @return the degreeId
	 */
	public String getDegreeId() {
		return degreeId;
	}
	/**
	 * @param degreeId the degreeId to set
	 */
	public void setDegreeId(String degreeId) {
		this.degreeId = degreeId;
	}
	/**
	 * @return the degree
	 */
	public String getDegree() {
		return degree;
	}
	/**
	 * @param degree the degree to set
	 */
	public void setDegree(String degree) {
		this.degree = degree;
	}
	
}
