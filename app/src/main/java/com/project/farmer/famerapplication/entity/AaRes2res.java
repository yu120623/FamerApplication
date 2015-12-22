package com.project.farmer.famerapplication.entity;


import java.util.Date;


public class AaRes2res implements java.io.Serializable {

	private Integer res2resId;
	private int originalResourceId;
	private int targetResourceId;
	private String type;
	private Date createdTime;
	private String createdBy;
	private Date updatedTime;
	private String updatedBy;

	public AaRes2res() {
	}

	public AaRes2res(int originalResourceId, int targetResourceId, String type,
			Date createdTime, String createdBy, Date updatedTime,
			String updatedBy) {
		this.originalResourceId = originalResourceId;
		this.targetResourceId = targetResourceId;
		this.type = type;
		this.createdTime = createdTime;
		this.createdBy = createdBy;
		this.updatedTime = updatedTime;
		this.updatedBy = updatedBy;
	}

	public Integer getRes2resId() {
		return this.res2resId;
	}

	public void setRes2resId(Integer res2resId) {
		this.res2resId = res2resId;
	}

	public int getOriginalResourceId() {
		return this.originalResourceId;
	}

	public void setOriginalResourceId(int originalResourceId) {
		this.originalResourceId = originalResourceId;
	}

	public int getTargetResourceId() {
		return this.targetResourceId;
	}

	public void setTargetResourceId(int targetResourceId) {
		this.targetResourceId = targetResourceId;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
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

}
