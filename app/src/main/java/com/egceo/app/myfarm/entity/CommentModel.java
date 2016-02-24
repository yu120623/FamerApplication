package com.egceo.app.myfarm.entity;

import java.util.List;

public class CommentModel extends Comment{
	
	private String commentName;
	
	private List<Resource> resoursePath;

	public List<Resource> getResoursePath() {
		return resoursePath;
	}

	public void setResoursePath(List<Resource> resoursePath) {
		this.resoursePath = resoursePath;
	}

	public String getCommentName() {
		return commentName;
	}

	public void setCommentName(String commentName) {
		this.commentName = commentName;
	}

	public CommentModel() {
		
	}
	
	public CommentModel(Comment comment){
		this.setCommentContent(comment.getCommentContent());
		this.setCommenter(comment.getCommenter());
		this.setCommentId(comment.getCommentId());
		this.setCommentScore(comment.getCommentScore());
		this.setCommentStatus(comment.getCommentStatus());
		this.setCommnetType(comment.getCommnetType());
		this.setCreatedBy(comment.getCreatedBy());
		this.setCreatedTime(comment.getCreatedTime());
		this.setIsDeleted(comment.getIsDeleted());
		this.setReferenceObjectId(comment.getReferenceObjectId());
		this.setUpdatedBy(comment.getUpdatedBy());
		this.setUpdatedTime(comment.getUpdatedTime());
		
	}
	
	public Comment getComment(){
		Comment comment = new Comment();
		comment.setCommentContent(this.getCommentContent());
		comment.setCommenter(this.getCommenter());
		comment.setCommentId(this.getCommentId());
		comment.setCommentScore(this.getCommentScore());
		comment.setCommentStatus(this.getCommentStatus());
		comment.setCommnetType(this.getCommnetType());
		comment.setCreatedBy(this.getCreatedBy());
		comment.setCreatedTime(this.getCreatedTime());
		comment.setIsDeleted(this.getIsDeleted());
		comment.setReferenceObjectId(this.getReferenceObjectId());
		comment.setUpdatedBy(this.getUpdatedBy());
		comment.setUpdatedTime(this.getUpdatedTime());
		return comment;
	}
	
	
}
