package com.project.farmer.famerapplication.entity;

// Generated 21-ʮ����-15 ���� 12:31 by Hibernate Tools 3.4.0.CR1

public class AaSubjet2res implements java.io.Serializable {

	private Integer id;
	private int subjectId;
	private int resourceId;

	public AaSubjet2res() {
	}

	public AaSubjet2res(int subjectId, int resourceId) {
		this.subjectId = subjectId;
		this.resourceId = resourceId;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getSubjectId() {
		return this.subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public int getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}

}
