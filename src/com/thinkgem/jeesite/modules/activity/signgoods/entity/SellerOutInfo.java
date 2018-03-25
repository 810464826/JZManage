package com.thinkgem.jeesite.modules.activity.signgoods.entity;

import java.util.Date;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 发货信息表
 * @author 小石潭记丶
 *
 * 2017年8月6日
 */
public class SellerOutInfo extends DataEntity<SellerOutInfo>{

	private static final long serialVersionUID = 1L;
	//记录ID
	private String recordId;
	//操作员ID
	private Number userId;
	//仓库ID
	private Number storeId;
	//产品ID
	private Number productId;
	//经销商ID
	private Number sellerId;
	//出库单号
	private String outNo;
	//产品名称
	private String productName;
	//出库类型
	private String outType;
	//经销商名称
	private String sellerName;
	//盒码
	private String packCode;
	//箱码
	private String boxcode;
	//箱数
	private String boxcount;
	//瓶码
	private String bottleCode;
	//规格
	private String spec;
	//度数
	private Number degree;
	//容量
	private Number volume;	
	//出库时间
	private Date outDate;	
	//物流单号
	private String logisNum;
	//车牌号
	private String plateNum;
	//接收商家编码
	private String recsellerCode;
	//接收商家名称
	private String recsellerName;
	//操作员名称
	private String userName;
	//接收商家地址
	private String recsellerArea;
	//物流公司id
	private Number logisticId;
	//联系人
	private String contacts;
	//类型（香型）
	private String productType;
	//物流公司名称
	private String logisticName;
	//0 正常  1 作废
	private Number status;
	//品牌ID
	private Number brandsId;
	//品牌名称
	private String brandsName;
	//物流号
	private String logisticNo;
	//备注
	private String remark;
	//票据号
	private String billNumber;
	//扫码
	private Number wlySign;
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	public Number getUserId() {
		return userId;
	}
	public void setUserId(Number userId) {
		this.userId = userId;
	}
	public Number getStoreId() {
		return storeId;
	}
	public void setStoreId(Number storeId) {
		this.storeId = storeId;
	}
	public Number getProductId() {
		return productId;
	}
	public void setProductId(Number productId) {
		this.productId = productId;
	}
	public Number getSellerId() {
		return sellerId;
	}
	public void setSellerId(Number sellerId) {
		this.sellerId = sellerId;
	}
	public String getOutNo() {
		return outNo;
	}
	public void setOutNo(String outNo) {
		this.outNo = outNo;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getOutType() {
		return outType;
	}
	public void setOutType(String outType) {
		this.outType = outType;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public String getPackCode() {
		return packCode;
	}
	public void setPackCode(String packCode) {
		this.packCode = packCode;
	}
	public String getBoxCode() {
		return boxcode;
	}
	public void setBoxCode(String boxcode) {
		this.boxcode = boxcode;
	}
	public String getBoxCount() {
		return boxcount;
	}
	public void setBoxCount(String boxcount) {
		this.boxcount = boxcount;
	}
	public String getBottleCode() {
		return bottleCode;
	}
	public void setBottleCode(String bottleCode) {
		this.bottleCode = bottleCode;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public Number getDegree() {
		return degree;
	}
	public void setDegree(Number degree) {
		this.degree = degree;
	}
	public Number getVolume() {
		return volume;
	}
	public void setVolume(Number volume) {
		this.volume = volume;
	}
	public Date getOutDate() {
		return outDate;
	}
	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}
	public String getLogisNum() {
		return logisNum;
	}
	public void setLogisNum(String logisNum) {
		this.logisNum = logisNum;
	}
	public String getPlateNum() {
		return plateNum;
	}
	public void setPlateNum(String plateNum) {
		this.plateNum = plateNum;
	}
	public String getRecsellerCode() {
		return recsellerCode;
	}
	public void setRecsellerCode(String recsellerCode) {
		this.recsellerCode = recsellerCode;
	}
	public String getRecsellerName() {
		return recsellerName;
	}
	public void setRecsellerName(String recsellerName) {
		this.recsellerName = recsellerName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRecsellerArea() {
		return recsellerArea;
	}
	public void setRecsellerArea(String recsellerArea) {
		this.recsellerArea = recsellerArea;
	}
	public Number getLogisticId() {
		return logisticId;
	}
	public void setLogisticId(Number logisticId) {
		this.logisticId = logisticId;
	}
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getLogisticName() {
		return logisticName;
	}
	public void setLogisticName(String logisticName) {
		this.logisticName = logisticName;
	}
	public Number getStatus() {
		return status;
	}
	public void setStatus(Number status) {
		this.status = status;
	}
	public Number getBrandsId() {
		return brandsId;
	}
	public void setBrandsId(Number brandsId) {
		this.brandsId = brandsId;
	}
	public String getBrandsName() {
		return brandsName;
	}
	public void setBrandsName(String brandsName) {
		this.brandsName = brandsName;
	}
	public String getLogisticNo() {
		return logisticNo;
	}
	public void setLogisticNo(String logisticNo) {
		this.logisticNo = logisticNo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getBillNumber() {
		return billNumber;
	}
	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}
	public Number getWlySign() {
		return wlySign;
	}
	public void setWlySign(Number wlySign) {
		this.wlySign = wlySign;
	}
	
}
