package com.egceo.app.myfarm.entity;

import java.util.Date;

public class Order implements java.io.Serializable {

	private Integer orderId;
	private String orderAliasId;
	private String orderType;
	private String orderSn;
	private String orderStatus;
	private int userId;
	private int referenceObjeceId;
	private String isDeleted;
	private Date createdTime;
	private String createdBy;
	private Date updatedTime;
	private String updatedBy;

	public Order() {
	}

	

	public Order(String orderAliasId, String orderType, String orderSn,
			String orderStatus, int userId, int referenceObjeceId,
			String isDeleted, Date createdTime, String createdBy,
			Date updatedTime, String updatedBy) {
		this.orderAliasId = orderAliasId;
		this.orderType = orderType;
		this.orderSn = orderSn;
		this.orderStatus = orderStatus;
		this.userId = userId;
		this.referenceObjeceId = referenceObjeceId;
		this.isDeleted = isDeleted;
		this.createdTime = createdTime;
		this.createdBy = createdBy;
		this.updatedTime = updatedTime;
		this.updatedBy = updatedBy;
	}



	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getOrderSn() {
		return this.orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public String getOrderStatus() {
		return this.orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedTime() {
		return this.updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}


	public String getOrderAliasId() {
		return orderAliasId;
	}



	public void setOrderAliasId(String orderAliasId) {
		this.orderAliasId = orderAliasId;
	}


	public String getOrderType() {
		return orderType;
	}



	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}


	public int getReferenceObjeceId() {
		return referenceObjeceId;
	}



	public void setReferenceObjeceId(int referenceObjeceId) {
		this.referenceObjeceId = referenceObjeceId;
	}


	public String getIsDeleted() {
		return isDeleted;
	}



	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

}
