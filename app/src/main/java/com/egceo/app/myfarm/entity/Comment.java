package com.egceo.app.myfarm.entity;

import java.util.Date;

public class Comment implements java.io.Serializable{
	private Integer commentId;
	private String commentContent;
	private String commnetType;
	private Integer commentScore;
	private Integer commenter;
	private String commentStatus;
	private Integer referenceObjectId;
	private String isDeleted;
	private String createdBy;
	private Date createdTime;
	private String updatedBy;
	private Date updatedtime;
	
	public Comment() {
		
	}
	public Comment(String commentContent, String commnetType, Integer commentScore,
			Integer commenter, String commentStatus, Integer referenceObjectId,
			String isDeleted, String createdBy, Date createdTime,
			String updatedBy, Date updatedtime) {
		this.commentContent = commentContent;
		this.commnetType = commnetType;
		this.commentScore = commentScore;
		this.commenter = commenter;
		this.commentStatus = commentStatus;
		this.referenceObjectId = referenceObjectId;
		this.isDeleted = isDeleted;
		this.createdBy = createdBy;
		this.createdTime = createdTime;
		this.updatedBy = updatedBy;
		this.updatedtime = updatedtime;
	}

	public Integer getCommentId() {
		return commentId;
	}
	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public String getCommnetType() {
		return commnetType;
	}
	public void setCommnetType(String commnetType) {
		this.commnetType = commnetType;
	}

	public Integer getCommentScore() {
		return commentScore;
	}
	public void setCommentScore(Integer commentScore) {
		this.commentScore = commentScore;
	}

	public Integer getCommenter() {
		return commenter;
	}
	public void setCommenter(Integer commenter) {
		this.commenter = commenter;
	}

	public String getCommentStatus() {
		return commentStatus;
	}
	public void setCommentStatus(String commentStatus) {
		this.commentStatus = commentStatus;
	}

	public Integer getReferenceObjectId() {
		return referenceObjectId;
	}
	public void setReferenceObjectId(Integer referenceObjectId) {
		this.referenceObjectId = referenceObjectId;
	}
	public String getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedtime() {
		return updatedtime;
	}
	public void setUpdatedtime(Date updatedtime) {
		this.updatedtime = updatedtime;
	}
	
	

}
