package com.project.farmer.famerapplication.entity;

import java.util.List;

public class FarmModel extends Farm{
	
	private String resourcePath;
	private List<String> farmTags;
	private String recommend;
	private String areaName;
	private float farmDistance;
	
	public float getFarmDistance() {
		return farmDistance;
	}

	public void setFarmDistance(float farmDistance) {
		this.farmDistance = farmDistance;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getResourcePath() {
		return resourcePath;
	}

	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}

	public List<String> getFarmTags() {
		return farmTags;
	}

	public void setFarmTags(List<String> farmTags) {
		this.farmTags = farmTags;
	}

	public String getRecommend() {
		return recommend;
	}

	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}

	public FarmModel(){
		
	}
	
	public FarmModel(Farm farm){
		this.setCreatedBy(farm.getCreatedBy());
		this.setCreatedTime(farm.getCreatedTime());
		this.setFarmAddress(farm.getFarmAddress());
		this.setFarmAliasId(farm.getFarmAliasId());
		this.setFarmArea(farm.getFarmArea());
		this.setFarmCity(farm.getFarmCity());
		this.setFarmCode(farm.getFarmCity());
		this.setFarmDesc(farm.getFarmDesc());
		this.setFarmId(farm.getFarmId());
		this.setFarmInCharge(farm.getFarmInCharge());
		this.setFarmInChargePhone(farm.getFarmInChargePhone());
		this.setFarmLatitude(farm.getFarmLatitude());
		this.setFarmLevel(farm.getFarmLevel());
		this.setFarmLongitude(farm.getFarmLongitude());
		this.setFarmName(farm.getFarmName());
		this.setFarmOwnerId(farm.getFarmOwnerId());
		this.setFarmProvince(farm.getFarmProvince());
		this.setFarmStatus(farm.getFarmStatus());
		this.setUpdatedBy(farm.getUpdatedBy());
		this.setUpdatedTime(farm.getUpdatedTime());
		this.setQuickPay(farm.getQuickPay());
		this.setIsDeleted(farm.getIsDeleted());
	}
	public Farm getFarm(){
		Farm farm = new Farm();
		farm.setCreatedBy(this.getCreatedBy());
		farm.setCreatedTime(this.getCreatedTime());
		farm.setFarmAddress(this.getFarmAddress());
		farm.setFarmAliasId(this.getFarmAliasId());
		farm.setFarmArea(this.getFarmArea());
		farm.setFarmCity(this.getFarmCity());
		farm.setFarmCode(this.getFarmCity());
		farm.setFarmDesc(this.getFarmDesc());
		farm.setFarmId(this.getFarmId());
		farm.setFarmInCharge(this.getFarmInCharge());
		farm.setFarmInChargePhone(this.getFarmInChargePhone());
		farm.setFarmLatitude(this.getFarmLatitude());
		farm.setFarmLevel(this.getFarmLevel());
		farm.setFarmLongitude(this.getFarmLongitude());
		farm.setFarmName(this.getFarmName());
		farm.setFarmOwnerId(this.getFarmOwnerId());
		farm.setFarmProvince(this.getFarmProvince());
		farm.setFarmStatus(this.getFarmStatus());
		farm.setUpdatedBy(this.getUpdatedBy());
		farm.setUpdatedTime(this.getUpdatedTime());
		farm.setQuickPay(this.getQuickPay());
		farm.setIsDeleted(this.getIsDeleted());
		return farm;
	}
}


