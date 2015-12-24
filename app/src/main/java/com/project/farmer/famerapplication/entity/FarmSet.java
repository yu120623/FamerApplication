package com.project.farmer.famerapplication.entity;


import java.util.Date;

public class FarmSet implements java.io.Serializable {

	private Integer farmSetId;
	private String farmSetAliasId;
	private String farmSetName;
	private String farmSetDesc;
	private String farmSetRecomReason;
	private String farmSetType;
	private String farmStatus;
	private String isDeleted;
	private Date createdTime;
	private String createdBy;
	private Date updatedTime;
	private String updatedBy;

	public FarmSet() {
	}

	public FarmSet(String farmSetAliasId, Date createdTime, String createdBy,
			Date updatedTime, String updatedBy) {
		this.farmSetAliasId = farmSetAliasId;
		this.createdTime = createdTime;
		this.createdBy = createdBy;
		this.updatedTime = updatedTime;
		this.updatedBy = updatedBy;
	}

	

	

	public FarmSet(String farmSetAliasId, String farmSetName,
			String farmSetDesc, String farmSetRecomReason, String farmSetType,
			String farmStatus, String isDeleted, Date createdTime,
			String createdBy, Date updatedTime, String updatedBy) {
		this.farmSetAliasId = farmSetAliasId;
		this.farmSetName = farmSetName;
		this.farmSetDesc = farmSetDesc;
		this.farmSetRecomReason = farmSetRecomReason;
		this.farmSetType = farmSetType;
		this.farmStatus = farmStatus;
		this.isDeleted = isDeleted;
		this.createdTime = createdTime;
		this.createdBy = createdBy;
		this.updatedTime = updatedTime;
		this.updatedBy = updatedBy;
	}

	public Integer getFarmSetId() {
		return this.farmSetId;
	}

	public void setFarmSetId(Integer farmSetId) {
		this.farmSetId = farmSetId;
	}

	public String getFarmSetAliasId() {
		return this.farmSetAliasId;
	}

	public void setFarmSetAliasId(String farmSetAliasId) {
		this.farmSetAliasId = farmSetAliasId;
	}

	public String getFarmSetName() {
		return this.farmSetName;
	}

	public void setFarmSetName(String farmSetName) {
		this.farmSetName = farmSetName;
	}

	public String getFarmSetDesc() {
		return this.farmSetDesc;
	}

	public void setFarmSetDesc(String farmSetDesc) {
		this.farmSetDesc = farmSetDesc;
	}

	public String getFarmSetRecomReason() {
		return this.farmSetRecomReason;
	}

	public void setFarmSetRecomReason(String farmSetRecomReason) {
		this.farmSetRecomReason = farmSetRecomReason;
	}

	public String getFarmSetType() {
		return this.farmSetType;
	}

	public void setFarmSetType(String farmSetType) {
		this.farmSetType = farmSetType;
	}

	public String getFarmStatus() {
		return this.farmStatus;
	}

	public void setFarmStatus(String farmStatus) {
		this.farmStatus = farmStatus;
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
