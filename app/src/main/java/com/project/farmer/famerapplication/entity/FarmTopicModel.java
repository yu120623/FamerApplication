package com.project.farmer.famerapplication.entity;

import java.util.Date;

public class FarmTopicModel extends FarmTopic{
	private String resourcePath;

	private String codeName;
	
	private Date nowTime;
	
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
	
	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public FarmTopicModel(){
		
	}
	
	public FarmTopicModel(FarmTopic farmTopic){
		this.setCreatedBy(farmTopic.getCreatedBy());
		this.setCreatedTime(farmTopic.getCreatedTime());
		this.setFarmTopicAreaCode(farmTopic.getFarmTopicAreaCode());
		this.setFarmTopicDesc(farmTopic.getFarmTopicDesc());
		this.setFarmTopicEndTime(farmTopic.getFarmTopicEndTime());
		this.setFarmTopicId(farmTopic.getFarmTopicId());
		this.setFarmTopicName(farmTopic.getFarmTopicName());
		this.setFarmTopicRecomReason(farmTopic.getFarmTopicRecomReason());
		this.setFarmTopicType(farmTopic.getFarmTopicType());
		this.setUpdatdBy(farmTopic.getUpdatdBy());
		this.setUpdatedTime(farmTopic.getUpdatedTime());
	}
	public FarmTopic getFarmTopic(){
		FarmTopic farmTopic = new FarmTopic();
		farmTopic.setCreatedBy(this.getCreatedBy());
		farmTopic.setCreatedTime(this.getCreatedTime());
		farmTopic.setFarmTopicAreaCode(this.getFarmTopicAreaCode());
		farmTopic.setFarmTopicDesc(this.getFarmTopicDesc());
		farmTopic.setFarmTopicEndTime(this.getFarmTopicEndTime());
		farmTopic.setFarmTopicId(this.getFarmTopicId());
		farmTopic.setFarmTopicName(this.getFarmTopicName());
		farmTopic.setFarmTopicRecomReason(this.getFarmTopicRecomReason());
		farmTopic.setFarmTopicType(this.getFarmTopicType());
		farmTopic.setUpdatdBy(this.getUpdatdBy());
		farmTopic.setUpdatedTime(this.getUpdatedTime());
		return farmTopic;
	}
	
}
