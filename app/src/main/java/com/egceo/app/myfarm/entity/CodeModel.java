package com.egceo.app.myfarm.entity;

import java.util.List;

public class CodeModel extends Code{
	private String resourcePath;
	private String codeContent;
	private String cityName;
	private String cityCode;
	private List<Code> codeRecommend;
	private List<Code> codeAll;
	
	public List<Code> getCodeRecommend() {
		return codeRecommend;
	}
	public void setCodeRecommend(List<Code> codeRecommend) {
		this.codeRecommend = codeRecommend;
	}
	public List<Code> getCodeAll() {
		return codeAll;
	}
	public void setCodeAll(List<Code> codeAll) {
		this.codeAll = codeAll;
	}
	public String getResourcePath() {
		return resourcePath;
	}
	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}
	public String getCodeContent() {
		return codeContent;
	}
	public void setCodeContent(String codeContent) {
		this.codeContent = codeContent;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}


	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
}
