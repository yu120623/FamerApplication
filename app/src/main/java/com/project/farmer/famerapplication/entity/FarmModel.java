package com.project.farmer.famerapplication.entity;

public class FarmModel extends Farm{
	private double distance;
	private String resoursePath;
	private String codeName;
	
	public String getCodeName() {
		return codeName;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public String getResoursePath() {
		return resoursePath;
	}
	public void setResoursePath(String resoursePath) {
		this.resoursePath = resoursePath;
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
		return farm;
	}
}


