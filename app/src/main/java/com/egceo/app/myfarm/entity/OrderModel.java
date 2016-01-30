package com.egceo.app.myfarm.entity;

import java.util.Date;
import java.util.List;

public class OrderModel extends Order{
	
	private Date journeyTime;
	
	private Double ordePrice;
	
	private Long fund;
	
	private String farmSetName;
	
	private Integer copies;
	
	private Double price;
	
	private List<FarmItemsModel> farmItemsModels;
	
	private Contact contact;
	
	private String status;
	
	private String farmSetDesc;
	
	private Date recordTime;
	
	private FarmSetModel farmSetModel;
	
	private RefundRequestModel refundRequestModel;

	public FarmSetModel getFarmSetModel() {
		return farmSetModel;
	}



	public void setFarmSetModel(FarmSetModel farmSetModel) {
		this.farmSetModel = farmSetModel;
	}



	public OrderModel() {
		
	}
	
	

	public OrderModel(Order order) {
	//	this.setOrderId(order.getOrderId());
		this.setOrderAliasId (order.getOrderAliasId());
		this.setOrderType (order.getOrderType());
		this.setOrderSn (order.getOrderSn());
		this.setOrderStatus (order.getOrderStatus());
		this.setUserId (order.getUserId());
		this.setReferenceObjeceId (order.getReferenceObjeceId());
		this.setIsDeleted (order.getIsDeleted());
		this.setCreatedTime (order.getCreatedTime());
		this.setCreatedBy (order.getCreatedBy());
		this.setUpdatedTime (order.getUpdatedTime());
		this.setUpdatedBy (order.getUpdatedBy());
	}



	public Date getJourneyTime() {
		return journeyTime;
	}

	public void setJourneyTime(Date journeyTime) {
		this.journeyTime = journeyTime;
	}

	public Double getOrdePrice() {
		return ordePrice;
	}

	public void setOrdePrice(Double integer) {
		this.ordePrice = integer;
	}

	public Long getFund() {
		return fund;
	}

	public void setFund(Long fund) {
		this.fund = fund;
	}



	public String getFarmSetName() {
		return farmSetName;
	}



	public void setFarmSetName(String farmSetName) {
		this.farmSetName = farmSetName;
	}



	public List<FarmItemsModel> getFarmItemsModels() {
		return farmItemsModels;
	}



	public void setFarmItemsModels(List<FarmItemsModel> farmItemsModels) {
		this.farmItemsModels = farmItemsModels;
	}



	public Integer getCopies() {
		return copies;
	}



	public void setCopies(Integer copies) {
		this.copies = copies;
	}



	public Double getPrice() {
		return price;
	}



	public void setPrice(Double price) {
		this.price = price;
	}



	public Contact getContact() {
		return contact;
	}



	public void setContact(Contact contact) {
		this.contact = contact;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String getFarmSetDesc() {
		return farmSetDesc;
	}



	public void setFarmSetDesc(String farmSetDesc) {
		this.farmSetDesc = farmSetDesc;
	}



	public Date getRecordTime() {
		return recordTime;
	}



	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}



	public RefundRequestModel getRefundRequestModel() {
		return refundRequestModel;
	}



	public void setRefundRequestModel(RefundRequestModel refundRequestModel) {
		this.refundRequestModel = refundRequestModel;
	}

}
