package com.project.farmer.famerapplication.entity;


import java.util.Date;

public class AaSubjectLogin implements java.io.Serializable {

	private Integer subjectLoginId;
	private int subjectId;
	private String loginName;
	private String loginPassword;
	private Integer attempMade;
	private Date passwordChangeDate;
	private Date createdTime;
	private String createdBy;
	private Date updatedTime;
	private String updatedBy;

	public AaSubjectLogin() {
	}

	public AaSubjectLogin(int subjectId, String loginName,
			String loginPassword, Date createdTime, String createdBy,
			Date updatedTime, String updatedBy) {
		this.subjectId = subjectId;
		this.loginName = loginName;
		this.loginPassword = loginPassword;
		this.createdTime = createdTime;
		this.createdBy = createdBy;
		this.updatedTime = updatedTime;
		this.updatedBy = updatedBy;
	}

	public AaSubjectLogin(int subjectId, String loginName,
			String loginPassword, Integer attempMade, Date passwordChangeDate,
			Date createdTime, String createdBy, Date updatedTime,
			String updatedBy) {
		this.subjectId = subjectId;
		this.loginName = loginName;
		this.loginPassword = loginPassword;
		this.attempMade = attempMade;
		this.passwordChangeDate = passwordChangeDate;
		this.createdTime = createdTime;
		this.createdBy = createdBy;
		this.updatedTime = updatedTime;
		this.updatedBy = updatedBy;
	}

	public Integer getSubjectLoginId() {
		return this.subjectLoginId;
	}

	public void setSubjectLoginId(Integer subjectLoginId) {
		this.subjectLoginId = subjectLoginId;
	}

	public int getSubjectId() {
		return this.subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPassword() {
		return this.loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public Integer getAttempMade() {
		return this.attempMade;
	}

	public void setAttempMade(Integer attempMade) {
		this.attempMade = attempMade;
	}

	public Date getPasswordChangeDate() {
		return this.passwordChangeDate;
	}

	public void setPasswordChangeDate(Date passwordChangeDate) {
		this.passwordChangeDate = passwordChangeDate;
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
