package com.egceo.app.myfarm.entity;

import java.util.Date;

public class Contact implements java.io.Serializable{
	private Integer contactId;
	private String connectName;
	private String connectPhone;
	private Date createdTime;
	private String createdBy;
	private Date updatedTime;
	private String updatedBy;
	
	public Contact() {
	}
	
	public Contact(String connectName, String connectPhone, Date createdTime,
			String createdBy, Date updatedTime, String updatedBy) {
		this.connectName = connectName;
		this.connectPhone = connectPhone;
		this.createdTime = createdTime;
		this.createdBy = createdBy;
		this.updatedTime = updatedTime;
		this.updatedBy = updatedBy;
	}

	public Integer getContactId() {
		return contactId;
	}
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}

	public String getConnectName() {
		return connectName;
	}
	public void setConnectName(String connectName) {
		this.connectName = connectName;
	}

	public String getConnectPhone() {
		return connectPhone;
	}
	public void setConnectPhone(String connectPhone) {
		this.connectPhone = connectPhone;
	}

	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	
	
	
}
