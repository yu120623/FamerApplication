package com.egceo.app.myfarm.entity;

import java.util.Date;

public class Code implements java.io.Serializable{

	private long codeId;
	private String codeName;
	private String codeDesc;
	private String codetype;
	private Integer parentId;
	private String createdBy;
	private Date createdTime;
	private String updatedBy;
	private Date updatedTime;
	private boolean isCurrent;
	public Code() {

	}
	public Code(Long codeId, String codeName, String codeDesc, String codetype, String createdBy, String updatedBy, java.util.Date updatedTime, java.util.Date createdTime, Boolean isCurrent) {
		this.codeId = codeId;
		this.codeName = codeName;
		this.codeDesc = codeDesc;
		this.codetype = codetype;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.updatedTime = updatedTime;
		this.createdTime = createdTime;
		this.isCurrent = isCurrent;
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


	public long getCodeId() {
		return codeId;
	}
	public void setCodeId(long codeId) {
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


	public boolean getIsCurrent() {
		return isCurrent;
	}

	public void setIsCurrent(boolean current) {
		isCurrent = current;
	}
}
