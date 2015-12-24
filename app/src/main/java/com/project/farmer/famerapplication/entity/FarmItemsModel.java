package com.project.farmer.famerapplication.entity;

public class FarmItemsModel extends FarmItems{
	private Integer FarmItemsprice;
	private String FarmName;
	private Integer price;
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
}
