package com.egceo.app.myfarm.entity;

import java.util.Date;

public class FarmTopicModel extends FarmTopic{
	private String resourcePath;
	
	private Date nowTime;

	private String tagName;

	
	
	public Date getNowTime() {
		return nowTime;
	}

	public void setNowTime(Date nowTime) {
		this.nowTime = nowTime;
	}

	public String getResourcePath() {
		return resourcePath;
	}

	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}
	

	public FarmTopicModel(){
		
	}
	
	public FarmTopicModel(FarmTopic farmTopic){
		this.setCreatedBy(farmTopic.getCreatedBy());
		this.setCreatedTime(farmTopic.getCreatedTime());
		this.setFarmTopicDesc(farmTopic.getFarmTopicDesc());
		this.setFarmTopicEndTime(farmTopic.getFarmTopicEndTime());
		this.setFarmTopicId(farmTopic.getFarmTopicId());
		this.setFarmTopicName(farmTopic.getFarmTopicName());
		this.setFarmTopicRecomReason(farmTopic.getFarmTopicRecomReason());
		this.setFarmTopicType(farmTopic.getFarmTopicType());
		this.setUpdatdBy(farmTopic.getUpdatdBy());
		this.setUpdatedTime(farmTopic.getUpdatedTime());
		this.setFarmTopicAliasId(farmTopic.getFarmTopicAliasId());
		this.setFarmTopicAreaTag(farmTopic.getFarmTopicAreaTag());
		this.setFarmTopicCityCode(farmTopic.getFarmTopicCityCode());
		this.setFarmTopicOrderbyId(farmTopic.getFarmTopicOrderbyId());
		this.setFarmTopicBeginTime(farmTopic.getFarmTopicBeginTime());
		this.setIsDeleted(farmTopic.getIsDeleted());
		this.setFarmTopicStatus(farmTopic.getFarmTopicStatus());
	}
	public FarmTopic getFarmTopic(){
		FarmTopic farmTopic = new FarmTopic();
		farmTopic.setCreatedBy(this.getCreatedBy());
		farmTopic.setCreatedTime(this.getCreatedTime());
		farmTopic.setFarmTopicDesc(this.getFarmTopicDesc());
		farmTopic.setFarmTopicEndTime(this.getFarmTopicEndTime());
		farmTopic.setFarmTopicId(this.getFarmTopicId());
		farmTopic.setFarmTopicName(this.getFarmTopicName());
		farmTopic.setFarmTopicRecomReason(this.getFarmTopicRecomReason());
		farmTopic.setFarmTopicType(this.getFarmTopicType());
		farmTopic.setUpdatdBy(this.getUpdatdBy());
		farmTopic.setUpdatedTime(this.getUpdatedTime());
		farmTopic.setFarmTopicAliasId(this.getFarmTopicAliasId());
		farmTopic.setFarmTopicAreaTag(this.getFarmTopicAreaTag());
		farmTopic.setFarmTopicCityCode(this.getFarmTopicCityCode());
		farmTopic.setFarmTopicOrderbyId(this.getFarmTopicOrderbyId());
		farmTopic.setFarmTopicBeginTime(this.getFarmTopicBeginTime());
		farmTopic.setIsDeleted(this.getIsDeleted());
		farmTopic.setFarmTopicStatus(this.getFarmTopicStatus());
		return farmTopic;
	}


	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}


}
