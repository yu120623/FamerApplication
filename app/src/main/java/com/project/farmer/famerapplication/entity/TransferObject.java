package com.project.farmer.famerapplication.entity;

import java.io.Serializable;
import java.util.List;


public class TransferObject implements Serializable {
	private Integer pageNumber;

	private List<FarmTopicModel> farmTopicModels;
	
	private List<FarmModel> farmModels;

	private FarmModel farmModel;
	
	private String farmTopicAliasId;
	
	private FarmSetModel farmSetModel;
	
	private List<FarmSetModel> farmSetModels;
	
	private String farmSetAliasId;
	
	private String commentCount;
	
	private List<CommentModel> commentModels;
	
	private String favorableRate;
	
	private String score;
	
	private List<FarmStatementModel> farmStatementModels;
	
	private Double farmLatitude;
	
	private Double farmLongitude;
	
	private String type;

	private String farmAliasId;
	
	private String cityCode;
	
	private String cityCodeName;
	
	private List<CodeModel> codeModels;
	
	private List<Resource> resources;
	
	private CodeModel codeModel;
	
	public CodeModel getCodeModel() {
		return codeModel;
	}

	public void setCodeModel(CodeModel codeModel) {
		this.codeModel = codeModel;
	}

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

	public String getFarmSetAliasId() {
		return farmSetAliasId;
	}

	public void setFarmSetAliasId(String farmSetAliasId) {
		this.farmSetAliasId = farmSetAliasId;
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

	public List<FarmStatementModel> getFarmStatements() {
		return farmStatementModels;
	}

	public void setFarmStatements(List<FarmStatementModel> farmStatementModels) {
		this.farmStatementModels = farmStatementModels;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityCodeName() {
		return cityCodeName;
	}

	public void setCityCodeName(String cityCodeName) {
		this.cityCodeName = cityCodeName;
	}

	public List<FarmStatementModel> getFarmStatementModels() {
		return farmStatementModels;
	}

	public void setFarmStatementModels(List<FarmStatementModel> farmStatementModels) {
		this.farmStatementModels = farmStatementModels;
	}

	public List<CodeModel> getCodeModels() {
		return codeModels;
	}

	public void setCodeModels(List<CodeModel> codeModels) {
		this.codeModels = codeModels;
	}

	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

	public String getFarmAliasId() {
		return farmAliasId;
	}

	public void setFarmAliasId(String farmAliasId) {
		this.farmAliasId = farmAliasId;
	}

	public FarmModel getFarmModel() {
		return farmModel;
	}

	public void setFarmModel(FarmModel farmModel) {
		this.farmModel = farmModel;
	}
}
