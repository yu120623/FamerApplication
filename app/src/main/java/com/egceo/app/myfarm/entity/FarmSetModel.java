package com.egceo.app.myfarm.entity;

import java.util.Date;
import java.util.List;

public class FarmSetModel extends FarmSet{
	private List<ResourseModel> baResourceModels;
	private List<ResourseModel> deResourceModels;
	private List<String> tags;
	private List<FarmItemsModel> farmItemsModels;
	private Double minPrice;
	private Double conPrice;
	private Date nowTime;
	private Date endTime;
	private Date beginTime;
	private Double fund;
	private String collectStatus;
	
	public List<ResourseModel> getBaResourceModels() {
		return baResourceModels;
	}
	public void setBaResourceModels(List<ResourseModel> baResourceModels) {
		this.baResourceModels = baResourceModels;
	}
	public List<ResourseModel> getDeResourceModels() {
		return deResourceModels;
	}
	public void setDeResourceModels(List<ResourseModel> deResourceModels) {
		this.deResourceModels = deResourceModels;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public Double getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(Double minPrice) {
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

	public Date getNowTime() {
		return nowTime;
	}

	public void setNowTime(Date nowTime) {
		this.nowTime = nowTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Double getFund() {
		return fund;
	}

	public void setFund(Double fund) {
		this.fund = fund;
	}

	public String getCollectStatus() {
		return collectStatus;
	}

	public void setCollectStatus(String collectStatus) {
		this.collectStatus = collectStatus;
	}

	public Double getConPrice() {
		return conPrice;
	}

	public void setConPrice(Double conPrice) {
		this.conPrice = conPrice;
	}
}
