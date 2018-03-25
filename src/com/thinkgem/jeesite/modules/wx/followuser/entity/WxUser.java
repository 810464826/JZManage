/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wx.followuser.entity;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 微信公众号上的关注用户Entity
 * @author tanghaobo
 * @version 2017-03-13
 */
public class WxUser extends DataEntity<WxUser> {
	
	private static final long serialVersionUID = 1L;
	private String nickname;		// 微信名称
	private String sex;		// 性别
	private String country;		// 国籍
	private String province;		// 省
	private String city;		// 市
	private String headimgurl;		// 头像地址
	private String openid;		// 用户微信ID
	private String phoneNumber;		// 手机号码
	private Date follotime;		// 关注时间
	
	public WxUser() {
		super();
	}

	public WxUser(String id){
		super(id);
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getFollotime() {
		return follotime;
	}

	public void setFollotime(Date follotime) {
		this.follotime = follotime;
	}
	
}