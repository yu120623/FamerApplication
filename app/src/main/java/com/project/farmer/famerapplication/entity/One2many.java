package com.project.farmer.famerapplication.entity;


import java.util.Date;

public class One2many implements java.io.Serializable {

	private Integer one2manyId;
	private int oneKey;
	private int manyKey;
	private String one2manyType;
	private String desc;
	private Date createdTime;
	private String createdBy;
	private Date updatedTime;
	private String updatedBy;

	public One2many() {
	}

	public One2many(int oneKey, int manyKey, String one2manyType, String desc,
			Date createdTime, String createdBy, Date updatedTime,
			String updatedBy) {
		this.oneKey = oneKey;
		this.manyKey = manyKey;
		this.one2manyType = one2manyType;
		this.desc = desc;
		this.createdTime = createdTime;
		this.createdBy = createdBy;
		this.updatedTime = updatedTime;
		this.updatedBy = updatedBy;
	}

	public Integer getOne2manyId() {
		return this.one2manyId;
	}

	public void setOne2manyId(Integer one2manyId) {
		this.one2manyId = one2manyId;
	}

	public int getOneKey() {
		return this.oneKey;
	}

	public void setOneKey(int oneKey) {
		this.oneKey = oneKey;
	}

	public int getManyKey() {
		return this.manyKey;
	}

	public void setManyKey(int manyKey) {
		this.manyKey = manyKey;
	}

	public String getOne2manyType() {
		return this.one2manyType;
	}

	public void setOne2manyType(String one2manyType) {
		this.one2manyType = one2manyType;
	}

	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
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
