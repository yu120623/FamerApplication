package com.project.farmer.famerapplication.entity;

public class FarmItemsModel extends FarmItems{
	private Integer FarmItemsprice;
	private String FarmName;
	private Integer price;
	private Resource resource;
	private Double farmLatitude;
	private Double farmLongitude;
	private String farmAddress;
	public Integer getFarmItemsprice() {
		return FarmItemsprice;
	}
	public void setFarmItemsprice(Integer farmItemsprice) {
		FarmItemsprice = farmItemsprice;
	}
	public String getFarmName() {
		return FarmName;
	}
	public void setFarmName(String farmName) {
		FarmName = farmName;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Resource getResource() {
		return resource;
	}
	public void setResource(Resource resource) {
		this.resource = resource;
	}
	public Double getFarmLatitude() {
		return farmLatitude;
	}
	public void setFarmLatitude(Double farmLatitude) {
		this.farmLatitude = farmLatitude;
	}
	public Double getFarmLongitude() {
		return farmLongitude;
	}
	public void setFarmLongitude(Double farmLongitude) {
		this.farmLongitude = farmLongitude;
	}
	public String getFarmAddress() {
		return farmAddress;
	}
	public void setFarmAddress(String farmAddress) {
		this.farmAddress = farmAddress;
	}
}
