package com.egceo.app.myfarm.db;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "SEND_COMMENT".
 */
public class SendComment {

    private Long commentId;
    private String commentContent;
    private String commnetType;
    private Float commentScore;
    private String orderSn;
    private Integer commenter;
    private String commentStatus;
    private Integer referenceObjectId;
    private String isDeleted;
    private String createdBy;
    private java.util.Date createdTime;
    private String updatedBy;
    private java.util.Date updatedTime;

    public SendComment() {
    }

    public SendComment(Long commentId) {
        this.commentId = commentId;
    }

    public SendComment(Long commentId, String commentContent, String commnetType, Float commentScore, String orderSn, Integer commenter, String commentStatus, Integer referenceObjectId, String isDeleted, String createdBy, java.util.Date createdTime, String updatedBy, java.util.Date updatedTime) {
        this.commentId = commentId;
        this.commentContent = commentContent;
        this.commnetType = commnetType;
        this.commentScore = commentScore;
        this.orderSn = orderSn;
        this.commenter = commenter;
        this.commentStatus = commentStatus;
        this.referenceObjectId = referenceObjectId;
        this.isDeleted = isDeleted;
        this.createdBy = createdBy;
        this.createdTime = createdTime;
        this.updatedBy = updatedBy;
        this.updatedTime = updatedTime;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
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

    public Float getCommentScore() {
        return commentScore;
    }

    public void setCommentScore(Float commentScore) {
        this.commentScore = commentScore;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
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

    public java.util.Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(java.util.Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public java.util.Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(java.util.Date updatedTime) {
        this.updatedTime = updatedTime;
    }

}
