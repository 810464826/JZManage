/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.accountsapply.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 对账申请Entity
 * @author cxb
 * @version 2017-09-25
 */
public class Accountsapply extends DataEntity<Accountsapply> {
	
	private static final long serialVersionUID = 1L;
	private String applyoffice;		// 申请经销商
	private String applyusername;		// 申请人
	private String handleusername;		// 处理人
	private String handleoffice;		// 处理经销商
	private String state;		// 状态
	private String applytime;		// 申请时间
	private String handletime;		// handletime
	private String remarks;		// remarks 
	private String applyusercode;		// applyusercode 
	private String handleusercode;		// accountsusercode 
	
	public Accountsapply() {
		super();
	}

	/**
	 * @return the handleusercode
	 */
	public String getHandleusercode() {
		return handleusercode;
	}


	/**
	 * @param handleusercode the handleusercode to set
	 */
	public void setHandleusercode(String handleusercode) {
		this.handleusercode = handleusercode;
	}


	/**
	 * @return the applyusercode
	 */
	public String getApplyusercode() {
		return applyusercode;
	}

	/**
	 * @param applyusercode the applyusercode to set
	 */
	public void setApplyusercode(String applyusercode) {
		this.applyusercode = applyusercode;
	}

	public Accountsapply(String id){
		super(id);
	}

	public String getApplyoffice() {
		return applyoffice;
	}

	public void setApplyoffice(String applyoffice) {
		this.applyoffice = applyoffice;
	}
	
	public String getApplyusername() {
		return applyusername;
	}

	public void setApplyusername(String applyusername) {
		this.applyusername = applyusername;
	}
	
	public String getHandleusername() {
		return handleusername;
	}

	public void setHandleusername(String handleusername) {
		this.handleusername = handleusername;
	}
	
	public String getHandleoffice() {
		return handleoffice;
	}

	public void setHandleoffice(String handleoffice) {
		this.handleoffice = handleoffice;
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public String getApplytime() {
		return applytime;
	}

	public void setApplytime(String applytime) {
		this.applytime = applytime;
	}
	
	public String getHandletime() {
		return handletime;
	}

	public void setHandletime(String handletime) {
		this.handletime = handletime;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
}