package com.egceo.app.myfarm.entity;

public class RefundRequestModel extends RefundRequest{
	private String ReasonsForRefusal;

	public String getReasonsForRefusal() {
		return ReasonsForRefusal;
	}

	public void setReasonsForRefusal(String reasonsForRefusal) {
		ReasonsForRefusal = reasonsForRefusal;
	}
}
