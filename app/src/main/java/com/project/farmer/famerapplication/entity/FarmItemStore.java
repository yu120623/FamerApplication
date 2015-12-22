package com.project.farmer.famerapplication.entity;

// Generated 21-ʮ����-15 ���� 12:31 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

public class FarmItemStore implements java.io.Serializable {

	private Integer farmItemStoreId;
	private int farmItemId;
	private int farmItemAvailable;
	private Date farmItemDate;
	private Date createdTime;
	private String createdBy;
	private Date updatedTime;
	private String updatedBy;

	public FarmItemStore() {
	}

	public FarmItemStore(int farmItemId, int farmItemAvailable,
			Date farmItemDate, Date createdTime, String createdBy,
			Date updatedTime, String updatedBy) {
		this.farmItemId = farmItemId;
		this.farmItemAvailable = farmItemAvailable;
		this.farmItemDate = farmItemDate;
		this.createdTime = createdTime;
		this.createdBy = createdBy;
		this.updatedTime = updatedTime;
		this.updatedBy = updatedBy;
	}

	public Integer getFarmItemStoreId() {
		return this.farmItemStoreId;
	}

	public void setFarmItemStoreId(Integer farmItemStoreId) {
		this.farmItemStoreId = farmItemStoreId;
	}

	public int getFarmItemId() {
		return this.farmItemId;
	}

	public void setFarmItemId(int farmItemId) {
		this.farmItemId = farmItemId;
	}

	public int getFarmItemAvailable() {
		return this.farmItemAvailable;
	}

	public void setFarmItemAvailable(int farmItemAvailable) {
		this.farmItemAvailable = farmItemAvailable;
	}

	public Date getFarmItemDate() {
		return this.farmItemDate;
	}

	public void setFarmItemDate(Date farmItemDate) {
		this.farmItemDate = farmItemDate;
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

}
