package com.egceo.app.myfarm.entity;



public class ActivityModel extends Activity {

	private Resource resource;
	
	private Double redPackagdMoney;

	private String promptStr;
	
	private String isOk;
	
	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}


	public Double getRedPackagdMoney() {
		return redPackagdMoney;
	}

	public void setRedPackagdMoney(Double redPackagdMoney) {
		this.redPackagdMoney = redPackagdMoney;
	}

	public String getPromptStr() {
		return promptStr;
	}

	public void setPromptStr(String promptStr) {
		this.promptStr = promptStr;
	}

	public String getIsOk() {
		return isOk;
	}

	public void setIsOk(String isOk) {
		this.isOk = isOk;
	}
}
