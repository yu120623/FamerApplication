package com.project.farmer.famerapplication.entity;

// Generated 21-ʮ����-15 ���� 12:31 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

public class FarmSetStore implements java.io.Serializable {

	private Integer farmSetStoreId;
	private int farmSetId;
	private int farmSetAvailable;
	private Date farmSetDate;
	private Date createdTime;
	private String createdBy;
	private Date updatedTime;
	private String updatedBy;

	public FarmSetStore() {
	}

	public FarmSetStore(int farmSetId, int farmSetAvailable, Date farmSetDate,
			Date createdTime, String createdBy, Date updatedTime,
			String updatedBy) {
		this.farmSetId = farmSetId;
		this.farmSetAvailable = farmSetAvailable;
		this.farmSetDate = farmSetDate;
		this.createdTime = createdTime;
		this.createdBy = createdBy;
		this.updatedTime = updatedTime;
		this.updatedBy = updatedBy;
	}

	public Integer getFarmSetStoreId() {
		return this.farmSetStoreId;
	}

	public void setFarmSetStoreId(Integer farmSetStoreId) {
		this.farmSetStoreId = farmSetStoreId;
	}

	public int getFarmSetId() {
		return this.farmSetId;
	}

	public void setFarmSetId(int farmSetId) {
		this.farmSetId = farmSetId;
	}

	public int getFarmSetAvailable() {
		return this.farmSetAvailable;
	}

	public void setFarmSetAvailable(int farmSetAvailable) {
		this.farmSetAvailable = farmSetAvailable;
	}

	public Date getFarmSetDate() {
		return this.farmSetDate;
	}

	public void setFarmSetDate(Date farmSetDate) {
		this.farmSetDate = farmSetDate;
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
