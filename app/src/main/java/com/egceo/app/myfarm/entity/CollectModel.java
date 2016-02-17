package com.egceo.app.myfarm.entity;

import java.util.Date;

public class CollectModel {
	private String status;
	private String title;
	private Date collectDate;
	private String collectAliasId;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getCollectDate() {
		return collectDate;
	}
	public void setCollectDate(Date collectDate) {
		this.collectDate = collectDate;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	private String desc;

	public String getCollectAliasId() {
		return collectAliasId;
	}

	public void setCollectAliasId(String collectAliasId) {
		this.collectAliasId = collectAliasId;
	}
}
