package com.egceo.app.myfarm.entity;

// Generated 21-ʮ����-15 ���� 12:31 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

public class UserProfile implements java.io.Serializable {

	private Integer userId;
	private Integer userSubjectId;
	private String userOpenId;
	private String userAliasId;
	private String userName;
	private String userRealName;
	private String userPassword;
	private String userGender;
	private Date userDob;
	private String userPic;
	private String userProfession;
	private String userBindingPhone;
	private String userCity;
	private String userAddress;
	private String userInviteCodeAssigned;
	private String userInviteCodeUsed;
	private String userLevel;
	private Integer userPoints;
	private String userStatus;
	private Date createdTime;
	private String createdBy;
	private Date updatedTime;
	private String updatedBy;

	public UserProfile() {
	}

	public UserProfile(String userAliasId, String userBindingPhone,
			String userStatus, Date createdTime, String createdBy,
			Date updatedTime, String updatedBy) {
		this.userAliasId = userAliasId;
		this.userBindingPhone = userBindingPhone;
		this.userStatus = userStatus;
		this.createdTime = createdTime;
		this.createdBy = createdBy;
		this.updatedTime = updatedTime;
		this.updatedBy = updatedBy;
	}

	public UserProfile(Integer userSubjectId, String userOpenId,
			String userAliasId, String userName, String userRealName,
			String userPassword, String userGender, Date userDob,
			String userPic, String userProfession, String userBindingPhone,
			String userCity, String userAddress, String userInviteCodeAssigned,
			String userInviteCodeUsed, String userLevel, Integer userPoints,
			String userStatus, Date createdTime, String createdBy,
			Date updatedTime, String updatedBy) {
		this.userSubjectId = userSubjectId;
		this.userOpenId = userOpenId;
		this.userAliasId = userAliasId;
		this.userName = userName;
		this.userRealName = userRealName;
		this.userPassword = userPassword;
		this.userGender = userGender;
		this.userDob = userDob;
		this.userPic = userPic;
		this.userProfession = userProfession;
		this.userBindingPhone = userBindingPhone;
		this.userCity = userCity;
		this.userAddress = userAddress;
		this.userInviteCodeAssigned = userInviteCodeAssigned;
		this.userInviteCodeUsed = userInviteCodeUsed;
		this.userLevel = userLevel;
		this.userPoints = userPoints;
		this.userStatus = userStatus;
		this.createdTime = createdTime;
		this.createdBy = createdBy;
		this.updatedTime = updatedTime;
		this.updatedBy = updatedBy;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getUserSubjectId() {
		return this.userSubjectId;
	}

	public void setUserSubjectId(Integer userSubjectId) {
		this.userSubjectId = userSubjectId;
	}

	public String getUserOpenId() {
		return this.userOpenId;
	}

	public void setUserOpenId(String userOpenId) {
		this.userOpenId = userOpenId;
	}

	public String getUserAliasId() {
		return this.userAliasId;
	}

	public void setUserAliasId(String userAliasId) {
		this.userAliasId = userAliasId;
	}


	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserRealName() {
		return this.userRealName;
	}

	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}

	public String getUserPassword() {
		return this.userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserGender() {
		return this.userGender;
	}

	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

	public Date getUserDob() {
		return this.userDob;
	}

	public void setUserDob(Date userDob) {
		this.userDob = userDob;
	}

	public String getUserPic() {
		return this.userPic;
	}

	public void setUserPic(String userPic) {
		this.userPic = userPic;
	}

	public String getUserProfession() {
		return this.userProfession;
	}

	public void setUserProfession(String userProfession) {
		this.userProfession = userProfession;
	}

	public String getUserBindingPhone() {
		return this.userBindingPhone;
	}

	public void setUserBindingPhone(String userBindingPhone) {
		this.userBindingPhone = userBindingPhone;
	}

	public String getUserCity() {
		return this.userCity;
	}

	public void setUserCity(String userCity) {
		this.userCity = userCity;
	}

	public String getUserAddress() {
		return this.userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public String getUserInviteCodeAssigned() {
		return this.userInviteCodeAssigned;
	}

	public void setUserInviteCodeAssigned(String userInviteCodeAssigned) {
		this.userInviteCodeAssigned = userInviteCodeAssigned;
	}
	public String getUserInviteCodeUsed() {
		return this.userInviteCodeUsed;
	}

	public void setUserInviteCodeUsed(String userInviteCodeUsed) {
		this.userInviteCodeUsed = userInviteCodeUsed;
	}

	public String getUserLevel() {
		return this.userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

	public Integer getUserPoints() {
		return this.userPoints;
	}

	public void setUserPoints(Integer userPoints) {
		this.userPoints = userPoints;
	}

	public String getUserStatus() {
		return this.userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
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
