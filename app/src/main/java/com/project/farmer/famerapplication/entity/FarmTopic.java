package com.project.farmer.famerapplication.entity;

// Generated 21-ʮ����-15 ���� 12:31 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

public class FarmTopic implements java.io.Serializable {

	private Integer farmTopicId;
	private String farmTopicAreaCode;
	private String farmTopicName;
	private String farmTopicDesc;
	private String farmTopicRecomReason;
	private String farmTopicType;
	private Date farmTopicEndTime;
	private Date createdTime;
	private String createdBy;
	private Date updatedTime;
	private String updatdBy;

	public FarmTopic() {
	}

	public FarmTopic(String farmTopicAreaCode, String farmTopicName,
			String farmTopicType, Date createdTime, String createdBy,
			Date updatedTime, String updatdBy) {
		this.farmTopicAreaCode = farmTopicAreaCode;
		this.farmTopicName = farmTopicName;
		this.farmTopicType = farmTopicType;
		this.createdTime = createdTime;
		this.createdBy = createdBy;
		this.updatedTime = updatedTime;
		this.updatdBy = updatdBy;
	}

	public FarmTopic(String farmTopicAreaCode, String farmTopicName,
			String farmTopicDesc, String farmTopicRecomReason,
			String farmTopicType, Date farmTopicEndTime, Date createdTime,
			String createdBy, Date updatedTime, String updatdBy) {
		this.farmTopicAreaCode = farmTopicAreaCode;
		this.farmTopicName = farmTopicName;
		this.farmTopicDesc = farmTopicDesc;
		this.farmTopicRecomReason = farmTopicRecomReason;
		this.farmTopicType = farmTopicType;
		this.farmTopicEndTime = farmTopicEndTime;
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

	public String getFarmTopicAreaCode() {
		return this.farmTopicAreaCode;
	}

	public void setFarmTopicAreaCode(String farmTopicAreaCode) {
		this.farmTopicAreaCode = farmTopicAreaCode;
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

}
