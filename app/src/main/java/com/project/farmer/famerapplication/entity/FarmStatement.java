package com.project.farmer.famerapplication.entity;

// Generated 21-ʮ����-15 ���� 12:31 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

public class FarmStatement implements java.io.Serializable {

	private Integer farmStatementId;
	private String farmStatementDesc;
	private String farmStatementType;
	private Date createdTime;
	private String createdBy;
	private Date updatedTime;
	private String updatedBy;
	private String farmStatementTitle;

	public FarmStatement() {
	}

	public FarmStatement(String farmStatementDesc, String farmStatementType,
			Date createdTime, String createdBy, Date updatedTime,
			String updatedBy) {
		this.farmStatementDesc = farmStatementDesc;
		this.farmStatementType = farmStatementType;
		this.createdTime = createdTime;
		this.createdBy = createdBy;
		this.updatedTime = updatedTime;
		this.updatedBy = updatedBy;
	}

	public Integer getFarmStatementId() {
		return this.farmStatementId;
	}

	public void setFarmStatementId(Integer farmStatementId) {
		this.farmStatementId = farmStatementId;
	}

	public String getFarmStatementDesc() {
		return this.farmStatementDesc;
	}

	public void setFarmStatementDesc(String farmStatementDesc) {
		this.farmStatementDesc = farmStatementDesc;
	}

	public String getFarmStatementType() {
		return this.farmStatementType;
	}

	public void setFarmStatementType(String farmStatementType) {
		this.farmStatementType = farmStatementType;
	}

	public Date getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedTime() {
		return this.updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getFarmStatementTitle() {
		return farmStatementTitle;
	}

	public void setFarmStatementTitle(String farmStatementTitle) {
		this.farmStatementTitle = farmStatementTitle;
	}
}
