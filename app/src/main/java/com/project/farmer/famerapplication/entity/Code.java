package com.project.farmer.famerapplication.entity;

import java.util.Date;

public class Code implements java.io.Serializable{

	private Integer codeId;
	private String codeName;
	private String codeDesc;
	private String codetype;
	private Integer parentId;
	private String createdBy;
	private Date createdTime;
	private String updatedBy;
	private Date updatedTime;
	public Code() {

	}
	

	public Code(String codeName, String codeDesc, String codetype,
			Integer parentId, String createdBy, Date createdTime,
			String updatedBy, Date updatedTime) {
	
		this.codeName = codeName;
		this.codeDesc = codeDesc;
		this.codetype = codetype;
		this.parentId = parentId;
		this.createdBy = createdBy;
		this.createdTime = createdTime;
		this.updatedBy = updatedBy;
		this.updatedTime = updatedTime;
	}


	public Integer getCodeId() {
		return codeId;
	}
	public void setCodeId(Integer codeId) {
		this.codeId = codeId;
	}

	public String getCodeName() {
		return codeName;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getCodeDesc() {
		return codeDesc;
	}


	public void setCodeDesc(String codeDesc) {
		this.codeDesc = codeDesc;
	}

	public String getCodetype() {
		return codetype;
	}


	public void setCodetype(String codetype) {
		this.codetype = codetype;
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

	public Date getUpdatedTime() {
		return updatedTime;
	}


	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}
	
	
	
	
	
}
