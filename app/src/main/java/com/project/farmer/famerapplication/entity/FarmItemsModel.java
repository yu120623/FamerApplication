package com.project.farmer.famerapplication.entity;

import java.util.List;

public class FarmItemsModel extends FarmItems{
	private Integer FarmItemsprice;
	private String farmName;
	private Integer price;
	private List<Resource> resources;
	private Double farmLatitude;
	private Double farmLongitude;
	private String farmAddress;
	private Double distance;
	public Integer getFarmItemsprice() {
		return FarmItemsprice;
	}
	public void setFarmItemsprice(Integer farmItemsprice) {
		FarmItemsprice = farmItemsprice;
	}
	public String getFarmName() {
		return farmName;
	}
	public void setFarmName(String farmName) {
		farmName = farmName;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
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

	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}
}
