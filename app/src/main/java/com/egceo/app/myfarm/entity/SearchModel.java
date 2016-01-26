package com.egceo.app.myfarm.entity;

import java.util.Date;

public class SearchModel {
    private String searchId;
    private String searchType;
    private String searchTitle;
    private String searchDesc;
    private Date creatyTime;

    public Date getCreatyTime() {
        return creatyTime;
    }

    public void setCreatyTime(Date creatyTime) {
        this.creatyTime = creatyTime;
    }

    public String getSearchId() {
        return searchId;
    }

    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getSearchTitle() {
        return searchTitle;
    }

    public void setSearchTitle(String searchTitle) {
        this.searchTitle = searchTitle;
    }

    public String getSearchDesc() {
        return searchDesc;
    }

    public void setSearchDesc(String searchDesc) {
        this.searchDesc = searchDesc;
    }

}
