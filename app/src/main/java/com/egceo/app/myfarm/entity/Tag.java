package com.egceo.app.myfarm.entity;

// Generated 21-ʮ����-15 ���� 12:31 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

public class Tag implements java.io.Serializable {

	private Integer tagId;
	private String tagName;
	private String tagDesc;
	private String isDeleted;
	private Date createdTime;
	private String createdBy;
	private Date updatedTime;
	private String updatedBy;

	public Tag() {
	}

	public Tag(String tagName, String tagDesc, String isDeleted,
			Date createdTime, String createdBy, Date updatedTime,
			String updatedBy) {
		this.tagName = tagName;
		this.tagDesc = tagDesc;
		this.isDeleted = isDeleted;
		this.createdTime = createdTime;
		this.createdBy = createdBy;
		this.updatedTime = updatedTime;
		this.updatedBy = updatedBy;
	}



	public Integer getTagId() {
		return this.tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	public String getTagName() {
		return this.tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getTagDesc() {
		return this.tagDesc;
	}

	public void setTagDesc(String tagDesc) {
		this.tagDesc = tagDesc;
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


	public String getIsDeleted() {
		return isDeleted;
	}



	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

}
