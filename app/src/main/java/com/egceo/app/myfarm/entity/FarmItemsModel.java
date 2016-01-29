package com.egceo.app.myfarm.entity;

import java.util.Date;
import java.util.List;

public class FarmItemsModel extends FarmItems{
	private Double FarmItemsprice;
	private String farmName;
	private Double price;
	private List<Resource> resources;
	private Double farmLatitude;
	private Double farmLongitude;
	private String farmAddress;
	private Double distance;
	private String status;
	private Date consumeTime;
	public Double getFarmItemsprice() {
		return FarmItemsprice;
	}
	public void setFarmItemsprice(Double farmItemsprice) {
		FarmItemsprice = farmItemsprice;
	}
	public String getFarmName() {
		return farmName;
	}
	public void setFarmName(String farmName) {
		farmName = farmName;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
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

	public Date getConsumeTime() {
		return consumeTime;
	}

	public void setConsumeTime(Date consumeTime) {
		this.consumeTime = consumeTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
