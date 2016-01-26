package com.egceo.app.myfarm.entity;

import java.util.List;

public class ResourseModel extends Resource{

	private List<String> tagNames;

	public List<String> getTagNames() {
		return tagNames;
	}

	public void setTagNames(List<String> tagNames) {
		this.tagNames = tagNames;
	}

	public ResourseModel() {
	
	}

	public ResourseModel(Resource resource) {
		this.setResourceId(resource.getResourceId());
		this.setResourceName(resource.getResourceName())  ;
		this.setResourceType(resource.getResourceType()) ;
		this.setResourceLocation(resource.getResourceLocation())  ;
		this.setResourceProperty(resource.getResourceProperty()) ;
		this.setReferrenceObjectId(resource.getReferrenceObjectId()) ;
		this.setIsDeleted(resource.getIsDeleted()) ;
		this.setCreatedTime(resource.getCreatedTime()) ;
		this.setCreatedBy(resource.getCreatedBy()) ;
		this.setUpdatedTime(resource.getUpdatedTime()) ;
		this.setUpdatedBy(resource.getUpdatedBy()) ;
	}
	public Resource getResourse(){
		Resource resource = new Resource();
		resource.setResourceId(this.getResourceId());
		resource.setResourceName(this.getResourceName())  ;
		resource.setResourceType(this.getResourceType()) ;
		resource.setResourceLocation(this.getResourceLocation())  ;
		resource.setResourceProperty(this.getResourceProperty()) ;
		resource.setReferrenceObjectId(this.getReferrenceObjectId()) ;
		resource.setIsDeleted(this.getIsDeleted()) ;
		resource.setCreatedTime(this.getCreatedTime()) ;
		resource.setCreatedBy(this.getCreatedBy()) ;
		resource.setUpdatedTime(this.getUpdatedTime()) ;
		resource.setUpdatedBy(this.getUpdatedBy()) ;
		return resource;
	}
}
