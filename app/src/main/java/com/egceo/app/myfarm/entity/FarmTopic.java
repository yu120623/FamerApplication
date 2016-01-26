package com.egceo.app.myfarm.entity;

// Generated 21-ʮ����-15 ���� 12:31 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

public class FarmTopic implements java.io.Serializable {

	private Integer farmTopicId;
	private String farmTopicAliasId;
	private Integer farmTopicAreaTag;
	private Integer farmTopicCityCode;
	private String farmTopicOrderbyId;
	private String farmTopicName;
	private String farmTopicDesc;
	private String farmTopicRecomReason;
	private String farmTopicType;
	private Date farmTopicBeginTime;
	private Date farmTopicEndTime;
	private String isDeleted;
	private String farmTopicStatus;
	private Date createdTime;
	private String createdBy;
	private Date updatedTime;
	private String updatdBy;

	public FarmTopic() {
	}

	public FarmTopic( String farmTopicName,
			String farmTopicType, Date createdTime, String createdBy,
			Date updatedTime, String updatdBy) {
		this.farmTopicName = farmTopicName;
		this.farmTopicType = farmTopicType;
		this.createdTime = createdTime;
		this.createdBy = createdBy;
		this.updatedTime = updatedTime;
		this.updatdBy = updatdBy;
	}

	
	
	

	public FarmTopic(String farmTopicAliasId, Integer farmTopicAreaTag,
			Integer farmTopicCityCode, String farmTopicOrderbyId,
			String farmTopicName, String farmTopicDesc,
			String farmTopicRecomReason, String farmTopicType,
			Date farmTopicBeginTime, Date farmTopicEndTime, String isDeleted,
			String farmTopicStatus, Date createdTime, String createdBy,
			Date updatedTime, String updatdBy) {
		this.farmTopicAliasId = farmTopicAliasId;
		this.farmTopicAreaTag = farmTopicAreaTag;
		this.farmTopicCityCode = farmTopicCityCode;
		this.farmTopicOrderbyId = farmTopicOrderbyId;
		this.farmTopicName = farmTopicName;
		this.farmTopicDesc = farmTopicDesc;
		this.farmTopicRecomReason = farmTopicRecomReason;
		this.farmTopicType = farmTopicType;
		this.farmTopicBeginTime = farmTopicBeginTime;
		this.farmTopicEndTime = farmTopicEndTime;
		this.isDeleted = isDeleted;
		this.farmTopicStatus = farmTopicStatus;
		this.createdTime = createdTime;
		this.createdBy = createdBy;
		this.updatedTime = updatedTime;
		this.updatdBy = updatdBy;
	}

	public Integer getFarmTopicId() {
		return this.farmTopicId;
	}

	public void setFarmTopicId(Integer farmTopicId) {
		this.farmTopicId = farmTopicId;
	}

	public String getFarmTopicName() {
		return this.farmTopicName;
	}

	public void setFarmTopicName(String farmTopicName) {
		this.farmTopicName = farmTopicName;
	}

	public String getFarmTopicDesc() {
		return this.farmTopicDesc;
	}

	public void setFarmTopicDesc(String farmTopicDesc) {
		this.farmTopicDesc = farmTopicDesc;
	}

	public String getFarmTopicRecomReason() {
		return this.farmTopicRecomReason;
	}

	public void setFarmTopicRecomReason(String farmTopicRecomReason) {
		this.farmTopicRecomReason = farmTopicRecomReason;
	}
	public String getFarmTopicType() {
		return this.farmTopicType;
	}

	public void setFarmTopicType(String farmTopicType) {
		this.farmTopicType = farmTopicType;
	}

	public Date getFarmTopicEndTime() {
		return this.farmTopicEndTime;
	}

	public void setFarmTopicEndTime(Date farmTopicEndTime) {
		this.farmTopicEndTime = farmTopicEndTime;
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

	public String getUpdatdBy() {
		return this.updatdBy;
	}

	public void setUpdatdBy(String updatdBy) {
		this.updatdBy = updatdBy;
	}

	public String getFarmTopicAliasId() {
		return farmTopicAliasId;
	}

	public void setFarmTopicAliasId(String farmTopicAliasId) {
		this.farmTopicAliasId = farmTopicAliasId;
	}

	public Integer getFarmTopicAreaTag() {
		return farmTopicAreaTag;
	}

	public void setFarmTopicAreaTag(Integer farmTopicAreaTag) {
		this.farmTopicAreaTag = farmTopicAreaTag;
	}

	public Integer getFarmTopicCityCode() {
		return farmTopicCityCode;
	}

	public void setFarmTopicCityCode(Integer farmTopicCityCode) {
		this.farmTopicCityCode = farmTopicCityCode;
	}

	public String getFarmTopicOrderbyId() {
		return farmTopicOrderbyId;
	}

	public void setFarmTopicOrderbyId(String farmTopicOrderbyId) {
		this.farmTopicOrderbyId = farmTopicOrderbyId;
	}

	public Date getFarmTopicBeginTime() {
		return farmTopicBeginTime;
	}

	public void setFarmTopicBeginTime(Date farmTopicBeginTime) {
		this.farmTopicBeginTime = farmTopicBeginTime;
	}
	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getFarmTopicStatus() {
		return farmTopicStatus;
	}

	public void setFarmTopicStatus(String farmTopicStatus) {
		this.farmTopicStatus = farmTopicStatus;
	}
	
	
	
}
