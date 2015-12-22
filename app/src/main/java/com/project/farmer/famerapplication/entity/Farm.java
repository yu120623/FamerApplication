package com.project.farmer.famerapplication.entity;

// Generated 21-ʮ����-15 ���� 12:31 by Hibernate Tools 3.4.0.CR1

import java.util.Date;


public class Farm implements java.io.Serializable {

	private Integer farmId;
	private String farmAliasId;
	private String farmName;
	private String farmOwnerId;
	private String farmDesc;
	private String farmAddress;
	private String farmProvince;
	private String farmCity;
	private String farmArea;
	private String farmCode;
	private String farmLatitude;
	private String farmLongitude;
	private String farmLevel;
	private String farmInCharge;
	private Integer farmInChargePhone;
	private String farmStatus;
	private Date createdTime;
	private String createdBy;
	private Date updatedTime;
	private String updatedBy;

	public Farm() {
	}

	public Farm(String farmAliasId, String farmName, Date createdTime,
			String createdBy, Date updatedTime, String updatedBy) {
		this.farmAliasId = farmAliasId;
		this.farmName = farmName;
		this.createdTime = createdTime;
		this.createdBy = createdBy;
		this.updatedTime = updatedTime;
		this.updatedBy = updatedBy;
	}

	public Farm(String farmAliasId, String farmName, String farmOwnerId,
			String farmDesc, String farmAddress, String farmProvince,
			String farmCity, String farmArea, String farmCode,
			String farmLatitude, String farmLongitude, String farmLevel,
			String farmInCharge, Integer farmInChargePhone, String farmStatus,
			Date createdTime, String createdBy, Date updatedTime,
			String updatedBy) {
		this.farmAliasId = farmAliasId;
		this.farmName = farmName;
		this.farmOwnerId = farmOwnerId;
		this.farmDesc = farmDesc;
		this.farmAddress = farmAddress;
		this.farmProvince = farmProvince;
		this.farmCity = farmCity;
		this.farmArea = farmArea;
		this.farmCode = farmCode;
		this.farmLatitude = farmLatitude;
		this.farmLongitude = farmLongitude;
		this.farmLevel = farmLevel;
		this.farmInCharge = farmInCharge;
		this.farmInChargePhone = farmInChargePhone;
		this.farmStatus = farmStatus;
		this.createdTime = createdTime;
		this.createdBy = createdBy;
		this.updatedTime = updatedTime;
		this.updatedBy = updatedBy;
	}

	public Integer getFarmId() {
		return this.farmId;
	}

	public void setFarmId(Integer farmId) {
		this.farmId = farmId;
	}

	public String getFarmAliasId() {
		return this.farmAliasId;
	}

	public void setFarmAliasId(String farmAliasId) {
		this.farmAliasId = farmAliasId;
	}

	public String getFarmName() {
		return this.farmName;
	}

	public void setFarmName(String farmName) {
		this.farmName = farmName;
	}

	public String getFarmOwnerId() {
		return this.farmOwnerId;
	}

	public void setFarmOwnerId(String farmOwnerId) {
		this.farmOwnerId = farmOwnerId;
	}

	public String getFarmDesc() {
		return this.farmDesc;
	}

	public void setFarmDesc(String farmDesc) {
		this.farmDesc = farmDesc;
	}

	public String getFarmAddress() {
		return this.farmAddress;
	}

	public void setFarmAddress(String farmAddress) {
		this.farmAddress = farmAddress;
	}

	public String getFarmProvince() {
		return this.farmProvince;
	}

	public void setFarmProvince(String farmProvince) {
		this.farmProvince = farmProvince;
	}

	public String getFarmCity() {
		return this.farmCity;
	}

	public void setFarmCity(String farmCity) {
		this.farmCity = farmCity;
	}

	public String getFarmArea() {
		return this.farmArea;
	}

	public void setFarmArea(String farmArea) {
		this.farmArea = farmArea;
	}

	public String getFarmCode() {
		return this.farmCode;
	}

	public void setFarmCode(String farmCode) {
		this.farmCode = farmCode;
	}

	public String getFarmLatitude() {
		return this.farmLatitude;
	}

	public void setFarmLatitude(String farmLatitude) {
		this.farmLatitude = farmLatitude;
	}

	public String getFarmLongitude() {
		return this.farmLongitude;
	}

	public void setFarmLongitude(String farmLongitude) {
		this.farmLongitude = farmLongitude;
	}

	public String getFarmLevel() {
		return this.farmLevel;
	}

	public void setFarmLevel(String farmLevel) {
		this.farmLevel = farmLevel;
	}

	public String getFarmInCharge() {
		return this.farmInCharge;
	}

	public void setFarmInCharge(String farmInCharge) {
		this.farmInCharge = farmInCharge;
	}

	public Integer getFarmInChargePhone() {
		return this.farmInChargePhone;
	}

	public void setFarmInChargePhone(Integer farmInChargePhone) {
		this.farmInChargePhone = farmInChargePhone;
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

}
