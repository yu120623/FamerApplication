package com.egceo.app.myfarm.entity;

// Generated 21-ʮ����-15 ���� 12:31 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

public class Resource implements java.io.Serializable {

	private Integer resourceId;
	private String resourceName;
	private String resourceType;
	private String resourceLocation;
	private String resourceProperty;
	private Integer referrenceObjectId;
	private String isDeleted;
	private Date createdTime;
	private String createdBy;
	private Date updatedTime;
	private String updatedBy;

	public Resource() {
	}

	public Resource(String resourceName, String resourceLocation,
			Date createdTime, String createdBy, Date updatedTime,
			String updatedBy) {
		this.resourceName = resourceName;
		this.resourceLocation = resourceLocation;
		this.createdTime = createdTime;
		this.createdBy = createdBy;
		this.updatedTime = updatedTime;
		this.updatedBy = updatedBy;
	}

	

	

	public Resource(String resourceName, String resourceType,
			String resourceLocation, String resourceProperty,
			Integer referrenceObjectId, String isDeleted, Date createdTime,
			String createdBy, Date updatedTime, String updatedBy) {
		this.resourceName = resourceName;
		this.resourceType = resourceType;
		this.resourceLocation = resourceLocation;
		this.resourceProperty = resourceProperty;
		this.referrenceObjectId = referrenceObjectId;
		this.isDeleted = isDeleted;
		this.createdTime = createdTime;
		this.createdBy = createdBy;
		this.updatedTime = updatedTime;
		this.updatedBy = updatedBy;
	}

	public Integer getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceName() {
		return this.resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getResourceType() {
		return this.resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getResourceLocation() {
		return this.resourceLocation;
	}

	public void setResourceLocation(String resourceLocation) {
		this.resourceLocation = resourceLocation;
	}
	public String getResourceProperty() {
		return this.resourceProperty;
	}

	public void setResourceProperty(String resourceProperty) {
		this.resourceProperty = resourceProperty;
	}

	public Integer getReferrenceObjectId() {
		return this.referrenceObjectId;
	}

	public void setReferrenceObjectId(Integer referrenceObjectId) {
		this.referrenceObjectId = referrenceObjectId;
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

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

}
