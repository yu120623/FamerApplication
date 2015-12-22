package com.project.farmer.famerapplication.entity;


import java.util.Date;

public class FarmItems implements java.io.Serializable {

	private Integer farmItemId;
	private String farmItemAliasId;
	private int farmId;
	private String farmItemName;
	private String farmItemType;
	private String farmItemDesc;
	private Date createdTime;
	private String createdBy;
	private Date updatedTime;
	private String updatedBy;

	public FarmItems() {
	}

	public FarmItems(String farmItemAliasId, int farmId, Date createdTime,
			String createdBy, Date updatedTime, String updatedBy) {
		this.farmItemAliasId = farmItemAliasId;
		this.farmId = farmId;
		this.createdTime = createdTime;
		this.createdBy = createdBy;
		this.updatedTime = updatedTime;
		this.updatedBy = updatedBy;
	}

	public FarmItems(String farmItemAliasId, int farmId, String farmItemName,
			String farmItemType, String farmItemDesc, Date createdTime,
			String createdBy, Date updatedTime, String updatedBy) {
		this.farmItemAliasId = farmItemAliasId;
		this.farmId = farmId;
		this.farmItemName = farmItemName;
		this.farmItemType = farmItemType;
		this.farmItemDesc = farmItemDesc;
		this.createdTime = createdTime;
		this.createdBy = createdBy;
		this.updatedTime = updatedTime;
		this.updatedBy = updatedBy;
	}

	public Integer getFarmItemId() {
		return this.farmItemId;
	}

	public void setFarmItemId(Integer farmItemId) {
		this.farmItemId = farmItemId;
	}

	public String getFarmItemAliasId() {
		return this.farmItemAliasId;
	}

	public void setFarmItemAliasId(String farmItemAliasId) {
		this.farmItemAliasId = farmItemAliasId;
	}

	public int getFarmId() {
		return this.farmId;
	}

	public void setFarmId(int farmId) {
		this.farmId = farmId;
	}

	public String getFarmItemName() {
		return this.farmItemName;
	}

	public void setFarmItemName(String farmItemName) {
		this.farmItemName = farmItemName;
	}

	public String getFarmItemType() {
		return this.farmItemType;
	}

	public void setFarmItemType(String farmItemType) {
		this.farmItemType = farmItemType;
	}

	public String getFarmItemDesc() {
		return this.farmItemDesc;
	}

	public void setFarmItemDesc(String farmItemDesc) {
		this.farmItemDesc = farmItemDesc;
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
