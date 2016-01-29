package com.egceo.app.myfarm.entity;

// Generated 06-һ��-16 ���� 02:19 by Hibernate Tools 3.4.0.CR1

import java.util.Date;


public class RefundRequest implements java.io.Serializable {

	private Integer refundId;
	private int orderId;
	private String refundDesc;
	private Date refundRequestTime;
	private Date refundConfirmTime;
	private Date refundCompletedTime;
	private String personInCharge;
	private Date createdTime;
	private String createdBy;
	private Date updatedTime;
	private String updatedBy;

	private String status;
	public RefundRequest() {
	}

	public RefundRequest(int orderId, String refundDesc, String status,
			String personInCharge, Date createdTime, String createdBy,
			Date updatedTime, String updatedBy) {
		this.orderId = orderId;
		this.refundDesc = refundDesc;
		this.status = status;
		this.personInCharge = personInCharge;
		this.createdTime = createdTime;
		this.createdBy = createdBy;
		this.updatedTime = updatedTime;
		this.updatedBy = updatedBy;
	}

	public RefundRequest(int orderId, String refundDesc, String status,
			Date refundRequestTime, Date refundConfirmTime,
			Date refundCompletedTime, String personInCharge, Date createdTime,
			String createdBy, Date updatedTime, String updatedBy) {
		this.orderId = orderId;
		this.refundDesc = refundDesc;
		this.status = status;
		this.refundRequestTime = refundRequestTime;
		this.refundConfirmTime = refundConfirmTime;
		this.refundCompletedTime = refundCompletedTime;
		this.personInCharge = personInCharge;
		this.createdTime = createdTime;
		this.createdBy = createdBy;
		this.updatedTime = updatedTime;
		this.updatedBy = updatedBy;
	}

	public Integer getRefundId() {
		return this.refundId;
	}

	public void setRefundId(Integer refundId) {
		this.refundId = refundId;
	}

	public int getOrderId() {
		return this.orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getRefundDesc() {
		return this.refundDesc;
	}

	public void setRefundDesc(String refundDesc) {
		this.refundDesc = refundDesc;
	}



	public Date getRefundRequestTime() {
		return this.refundRequestTime;
	}

	public void setRefundRequestTime(Date refundRequestTime) {
		this.refundRequestTime = refundRequestTime;
	}

	public Date getRefundConfirmTime() {
		return this.refundConfirmTime;
	}

	public void setRefundConfirmTime(Date refundConfirmTime) {
		this.refundConfirmTime = refundConfirmTime;
	}

	public Date getRefundCompletedTime() {
		return this.refundCompletedTime;
	}

	public void setRefundCompletedTime(Date refundCompletedTime) {
		this.refundCompletedTime = refundCompletedTime;
	}

	public String getPersonInCharge() {
		return this.personInCharge;
	}

	public void setPersonInCharge(String personInCharge) {
		this.personInCharge = personInCharge;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
