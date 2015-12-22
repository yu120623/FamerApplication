package com.project.farmer.famerapplication.entity;

import java.io.Serializable;
import java.util.List;


public class TransferObject implements Serializable {
	private Integer pageNumber;

	private List<FarmTopicModel> farmTopicModels;
	
	private List<FarmModel> farmModel;
	
	private Integer farmTopicId;
	
	public Integer getFarmTopicId() {
		return farmTopicId;
	}

	public void setFarmTopicId(Integer farmTopicId) {
		this.farmTopicId = farmTopicId;
	}

	public List<FarmModel> getFarmModel() {
		return farmModel;
	}

	public void setFarmModel(List<FarmModel> farmModel) {
		this.farmModel= farmModel;
	}

	public List<FarmTopicModel> getFarmTopicModels() {
		return farmTopicModels;
	}

	public void setFarmTopicModels(List<FarmTopicModel> farmTopicModels) {
		this.farmTopicModels = farmTopicModels;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	   
}
