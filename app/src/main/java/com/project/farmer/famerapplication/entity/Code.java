package com.project.farmer.famerapplication.entity;

public class Code implements java.io.Serializable{

	private Integer codeId;
	private String codeName;
	private Integer parentId;
	private String number;
	public Code() {
		
	}
	public Code(Integer codeId, String codeName, Integer parentId, String number) {
		this.codeId = codeId;
		this.codeName = codeName;
		this.parentId = parentId;
		this.number = number;
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

	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	
	
}
