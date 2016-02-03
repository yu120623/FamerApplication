package com.egceo.app.myfarm.entity;

import java.util.Date;


public class Sysinfo implements java.io.Serializable {

	private Integer sysinfoId;
	private String sysinfoType;
	private String sysinfoContent;
	private int userId;
	private Date createdTime;
	private String createdBy;
	private Date updatedTime;
	private String updatedBy;

	public Sysinfo() {
	}

	public Sysinfo(String sysinfoType, String sysinfoContent, int userId,
			Date createdTime, String createdBy, Date updatedTime,
			String updatedBy) {
		this.sysinfoType = sysinfoType;
		this.sysinfoContent = sysinfoContent;
		this.userId = userId;
		this.createdTime = createdTime;
		this.createdBy = createdBy;
		this.updatedTime = updatedTime;
		this.updatedBy = updatedBy;
	}

	public Integer getSysinfoId() {
		return this.sysinfoId;
	}

	public void setSysinfoId(Integer sysinfoId) {
		this.sysinfoId = sysinfoId;
	}

	public String getSysinfoType() {
		return this.sysinfoType;
	}

	public void setSysinfoType(String sysinfoType) {
		this.sysinfoType = sysinfoType;
	}

	public String getSysinfoContent() {
		return this.sysinfoContent;
	}

	public void setSysinfoContent(String sysinfoContent) {
		this.sysinfoContent = sysinfoContent;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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
