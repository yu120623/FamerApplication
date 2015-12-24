package com.project.farmer.famerapplication.entity;

import java.io.Serializable;
import java.util.List;


public class TransferObject implements Serializable {
	private Integer pageNumber;

	private List<FarmTopicModel> farmTopicModels;
	
	private List<FarmModel> farmModels;

	
	private FarmSetModel farmSetModel;
	
	private List<FarmSetModel> farmSetModels;
	
	private String farmTopicAliasId;
	
	private String commentCount;
	
	private List<CommentModel> commentModels;
	
	private String favorableRate;
	
	private String score;
	
	private List<FarmStatement> farmStatements;
	
	public FarmSetModel getFarmSetModel() {
		return farmSetModel;
	}

	public void setFarmSetModel(FarmSetModel farmSetModel) {
		this.farmSetModel = farmSetModel;
	}

	public List<FarmSetModel> getFarmSetModels() {
		return farmSetModels;
	}

	public void setFarmSetModels(List<FarmSetModel> farmSetModels) {
		this.farmSetModels = farmSetModels;
	}

	public String getFarmTopicAliasId() {
		return farmTopicAliasId;
	}

	public void setFarmTopicAliasId(String farmTopicAliasId) {
		this.farmTopicAliasId = farmTopicAliasId;
	}

	public List<FarmModel> getFarmModels() {
		return farmModels;
	}

	public void setFarmModels(List<FarmModel> farmModels) {
		this.farmModels= farmModels;
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


	public String getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(String commentCount) {
		this.commentCount = commentCount;
	}

	public List<CommentModel> getCommentModels() {
		return commentModels;
	}

	public void setCommentModels(List<CommentModel> commentModels) {
		this.commentModels = commentModels;
	}
	
	public String getFavorableRate() {
		return favorableRate;
	}

	public void setFavorableRate(String favorableRate) {
		this.favorableRate = favorableRate;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public List<FarmStatement> getFarmStatements() {
		return farmStatements;
	}

	public void setFarmStatements(List<FarmStatement> farmStatements) {
		this.farmStatements = farmStatements;
	}
	   
}
