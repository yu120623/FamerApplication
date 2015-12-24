package com.project.farmer.famerapplication.entity;

// Generated 21-ʮ����-15 ���� 12:31 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

public class Price implements java.io.Serializable {

	private Integer priceId;
	private String priceType;
	private int referenceObjectId;
	private Date priceDate;
	private long price;
	private Date createdTime;
	private String cretedBy;
	private Date updatedTime;
	private String updatedBy;

	public Price() {
	}

	public Price(String priceType, int referenceObjectId, Date priceDate,
			long price, Date createdTime, String cretedBy, Date updatedTime,
			String updatedBy) {
		this.priceType = priceType;
		this.referenceObjectId = referenceObjectId;
		this.priceDate = priceDate;
		this.price = price;
		this.createdTime = createdTime;
		this.cretedBy = cretedBy;
		this.updatedTime = updatedTime;
		this.updatedBy = updatedBy;
	}

	public Integer getPriceId() {
		return this.priceId;
	}

	public void setPriceId(Integer priceId) {
		this.priceId = priceId;
	}

	public String getPriceType() {
		return this.priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public int getReferenceObjectId() {
		return this.referenceObjectId;
	}

	public void setReferenceObjectId(int referenceObjectId) {
		this.referenceObjectId = referenceObjectId;
	}

	public Date getPriceDate() {
		return this.priceDate;
	}

	public void setPriceDate(Date priceDate) {
		this.priceDate = priceDate;
	}

	public long getPrice() {
		return this.price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public Date getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getCretedBy() {
		return this.cretedBy;
	}

	public void setCretedBy(String cretedBy) {
		this.cretedBy = cretedBy;
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

}
