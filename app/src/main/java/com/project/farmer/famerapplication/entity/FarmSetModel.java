package com.project.farmer.famerapplication.entity;

import java.util.List;

public class FarmSetModel extends FarmSet{
	private List<Resource> baResources;
	private List<Resource> deResources;
	private List<String> tags;
	private List<FarmItemsModel> farmItemsModels;
	private int norPrice;
	private int minPrice;
	public List<Resource> getBaResources() {
		return baResources;
	}
	public void setBaResources(List<Resource> baResources) {
		this.baResources = baResources;
	}
	public List<Resource> getDeResources() {
		return deResources;
	}
	public void setDeResources(List<Resource> deResources) {
		this.deResources = deResources;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public int getNorPrice() {
		return norPrice;
	}
	public void setNorPrice(int norPrice) {
		this.norPrice = norPrice;
	}
	public int getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(int minPrice) {
		this.minPrice = minPrice;
	}
	public FarmSetModel(){
		
	}
	
	public FarmSetModel(FarmSet farmSet){
		this.setCreatedBy(farmSet.getCreatedBy());
		this.setCreatedTime(farmSet.getCreatedTime());
		this.setFarmSetAliasId(farmSet.getFarmSetAliasId());
		this.setFarmSetDesc(farmSet.getFarmSetDesc());
		this.setFarmSetId(farmSet.getFarmSetId());
		this.setFarmSetName(farmSet.getFarmSetName());
		this.setFarmSetRecomReason(farmSet.getFarmSetRecomReason());
		this.setFarmSetType(farmSet.getFarmSetType());
		this.setFarmStatus(farmSet.getFarmStatus());
		this.setUpdatedBy(farmSet.getUpdatedBy());
		this.setUpdatedTime(farmSet.getUpdatedTime());
		this.setIsDeleted(farmSet.getIsDeleted());
	}
	
	public FarmSet gerFarmSet(){
		FarmSet farmSet = new FarmSet();
		farmSet.setCreatedBy(this.getCreatedBy());
		farmSet.setCreatedTime(this.getCreatedTime());
		farmSet.setFarmSetAliasId(this.getFarmSetAliasId());
		farmSet.setFarmSetDesc(this.getFarmSetDesc());
		farmSet.setFarmSetId(this.getFarmSetId());
		farmSet.setFarmSetName(this.getFarmSetName());
		farmSet.setFarmSetRecomReason(this.getFarmSetRecomReason());
		farmSet.setFarmSetType(this.getFarmSetType());
		farmSet.setFarmStatus(this.getFarmStatus());
		farmSet.setUpdatedBy(this.getUpdatedBy());
		farmSet.setUpdatedTime(this.getUpdatedTime());
		farmSet.setIsDeleted(this.getIsDeleted());
		return farmSet;
	}
	public List<FarmItemsModel> getFarmItemsModels() {
		return farmItemsModels;
	}
	public void setFarmItemsModels(List<FarmItemsModel> farmItemsModels) {
		this.farmItemsModels = farmItemsModels;
	}
	
}
