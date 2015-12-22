package com.project.farmer.famerapplication.entity;

// Generated 21-ʮ����-15 ���� 12:31 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

public class AaSubject implements java.io.Serializable {

	private Integer subjectId;
	private String subjectAccount;
	private String subjectName;
	private Date subjectAccountExpiredDate;
	private Date lastLoginDate;
	private String lastLoginIp;
	private Date createdTime;
	private String createdBy;
	private Date updatedTime;
	private String updatedBy;

	public AaSubject() {
	}

	public AaSubject(String subjectAccount, Date subjectAccountExpiredDate,
			Date lastLoginDate, Date createdTime, String createdBy,
			Date updatedTime, String updatedBy) {
		this.subjectAccount = subjectAccount;
		this.subjectAccountExpiredDate = subjectAccountExpiredDate;
		this.lastLoginDate = lastLoginDate;
		this.createdTime = createdTime;
		this.createdBy = createdBy;
		this.updatedTime = updatedTime;
		this.updatedBy = updatedBy;
	}

	public AaSubject(String subjectAccount, String subjectName,
			Date subjectAccountExpiredDate, Date lastLoginDate,
			String lastLoginIp, Date createdTime, String createdBy,
			Date updatedTime, String updatedBy) {
		this.subjectAccount = subjectAccount;
		this.subjectName = subjectName;
		this.subjectAccountExpiredDate = subjectAccountExpiredDate;
		this.lastLoginDate = lastLoginDate;
		this.lastLoginIp = lastLoginIp;
		this.createdTime = createdTime;
		this.createdBy = createdBy;
		this.updatedTime = updatedTime;
		this.updatedBy = updatedBy;
	}

	public Integer getSubjectId() {
		return this.subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectAccount() {
		return this.subjectAccount;
	}

	public void setSubjectAccount(String subjectAccount) {
		this.subjectAccount = subjectAccount;
	}

	public String getSubjectName() {
		return this.subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public Date getSubjectAccountExpiredDate() {
		return this.subjectAccountExpiredDate;
	}

	public void setSubjectAccountExpiredDate(Date subjectAccountExpiredDate) {
		this.subjectAccountExpiredDate = subjectAccountExpiredDate;
	}

	public Date getLastLoginDate() {
		return this.lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getLastLoginIp() {
		return this.lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
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
