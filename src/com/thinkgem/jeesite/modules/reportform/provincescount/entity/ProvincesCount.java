/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.reportform.provincescount.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 跨省份扫码统计Entity
 * @author tanghaobo
 * @version 2017-07-06
 */
public class ProvincesCount extends DataEntity<ProvincesCount> {
	
	private static final long serialVersionUID = 1L;
	private String userName;		// 呢称
	private String phone;		// 电话号码
	private String country;		// 国家
	private String city;		// 城市
	private String province;		// 省
	private String scanTime;		// 扫码时间，只保留年月
	private String openid;		// openid
	private String activityPrizesName;		// 活动经销商名称
	private String activityPrizesAddress;		// 活动经销商所属省份
	private String activityPrizesId;		// 活动经销商ID
	private String activityId;		// 活动ID
	private String outProvinces;		// 是否属于外省  0属于本地 1不属于本地
	private String codeid;		// 所扫码ID
	private String scanNumber;		// 扫码次数
	private String district;		// 区
	private String road;		// 街道
	private String longitude;		// 经度
	private String latitude;		// 纬度
	
	public ProvincesCount() {
		super();
	}

	public ProvincesCount(String id){
		super(id);
	}

	@Length(min=0, max=255, message="呢称长度必须介于 0 和 255 之间")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Length(min=0, max=255, message="电话号码长度必须介于 0 和 255 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=255, message="国家长度必须介于 0 和 255 之间")
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	@Length(min=0, max=255, message="城市长度必须介于 0 和 255 之间")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Length(min=0, max=255, message="省长度必须介于 0 和 255 之间")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	@Length(min=0, max=255, message="扫码时间，只保留年月长度必须介于 0 和 255 之间")
	public String getScanTime() {
		return scanTime;
	}

	public void setScanTime(String scanTime) {
		this.scanTime = scanTime;
	}
	
	@Length(min=0, max=255, message="openid长度必须介于 0 和 255 之间")
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	@Length(min=0, max=255, message="活动经销商名称长度必须介于 0 和 255 之间")
	public String getActivityPrizesName() {
		return activityPrizesName;
	}

	public void setActivityPrizesName(String activityPrizesName) {
		this.activityPrizesName = activityPrizesName;
	}
	
	@Length(min=0, max=255, message="活动经销商所属省份长度必须介于 0 和 255 之间")
	public String getActivityPrizesAddress() {
		return activityPrizesAddress;
	}

	public void setActivityPrizesAddress(String activityPrizesAddress) {
		this.activityPrizesAddress = activityPrizesAddress;
	}
	
	@Length(min=0, max=255, message="活动经销商ID长度必须介于 0 和 255 之间")
	public String getActivityPrizesId() {
		return activityPrizesId;
	}

	public void setActivityPrizesId(String activityPrizesId) {
		this.activityPrizesId = activityPrizesId;
	}
	
	@Length(min=0, max=255, message="活动ID长度必须介于 0 和 255 之间")
	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	
	@Length(min=0, max=255, message="是否属于外省  0属于本地 1不属于本地长度必须介于 0 和 255 之间")
	public String getOutProvinces() {
		return outProvinces;
	}

	public void setOutProvinces(String outProvinces) {
		this.outProvinces = outProvinces;
	}
	
	@Length(min=0, max=255, message="所扫码ID长度必须介于 0 和 255 之间")
	public String getCodeid() {
		return codeid;
	}

	public void setCodeid(String codeid) {
		this.codeid = codeid;
	}
	
	@Length(min=0, max=255, message="扫码次数长度必须介于 0 和 255 之间")
	public String getScanNumber() {
		return scanNumber;
	}

	public void setScanNumber(String scanNumber) {
		this.scanNumber = scanNumber;
	}
	
	@Length(min=0, max=255, message="区长度必须介于 0 和 255 之间")
	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}
	
	@Length(min=0, max=255, message="街道长度必须介于 0 和 255 之间")
	public String getRoad() {
		return road;
	}

	public void setRoad(String road) {
		this.road = road;
	}
	
	@Length(min=0, max=255, message="经度长度必须介于 0 和 255 之间")
	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	@Length(min=0, max=255, message="纬度长度必须介于 0 和 255 之间")
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
}