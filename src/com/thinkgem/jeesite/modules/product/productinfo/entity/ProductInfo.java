/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.product.productinfo.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 产品管理Entity
 * @author tanghaobo
 * @version 2017-03-17
 */
public class ProductInfo extends DataEntity<ProductInfo> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 产品名称
	private String number;		// 产品编号
	private String imgid;		// 图片
	private String type;		// 产品类型1：卡券商品  2：积分标识  3:物流商品
	private Date createTime;		// 创建时间
	private Date updateTime;		// 修改时间
	private Double price;		// 产品价格
	private String unit;		// 产品单位
	private String alcoholic;		// 酒精度数
	private String productsize;		// 规格
	private String capacity;		// 净含量
	private String description;		// 产品描述
	private String cardId;		// 微信卡券
	private String remark;		// 备注
	private String status;		// 状态 1存在  2不存在
	
	public ProductInfo() {
		super();
	}

	public ProductInfo(String id){
		super(id);
	}

	@NotNull(message="产品编号")
	@ExcelField(title="产品编号", align=2, sort=30)
	public String getNumber() {
		return number;
	}
	
	@NotNull(message="产品名称")
	@ExcelField(title="产品名称", align=2, sort=35)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getImgid() {
		return imgid;
	}

	public void setImgid(String imgid) {
		this.imgid = imgid;
	}
	
	@NotNull(message="产品类型")
	@ExcelField(title="产品类型", align=2, sort=40)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@NotNull(message="产品价格")
	@ExcelField(title="产品价格", align=2, sort=45)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	@NotNull(message="产品单位(选填)")
	@ExcelField(title="产品单位(选填)", align=2, sort=50)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@NotNull(message="酒精度数(选填)")
	@ExcelField(title="酒精度数(选填)", align=2, sort=55)
	public String getAlcoholic() {
		return alcoholic;
	}

	public void setAlcoholic(String alcoholic) {
		this.alcoholic = alcoholic;
	}
	
	@NotNull(message="规格(选填)")
	@ExcelField(title="规格(选填)", align=2, sort=60)
	public String getProductsize() {
		return productsize;
	}

	public void setProductsize(String productsize) {
		this.productsize = productsize;
	}
	
	@NotNull(message="净含量(选填)")
	@ExcelField(title="净含量(选填)", align=2, sort=65)
	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}
	
	@NotNull(message="产品描述(选填)")
	@ExcelField(title="产品描述(选填)", align=2, sort=70)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	
	@NotNull(message="状态(必填)")
	@ExcelField(title="状态(必填)", align=2, sort=75)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@NotNull(message="备注(选填)")
	@ExcelField(title="备注(选填)", align=2, sort=80)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}