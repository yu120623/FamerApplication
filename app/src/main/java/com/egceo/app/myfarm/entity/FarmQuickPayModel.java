package com.egceo.app.myfarm.entity;

import java.io.Serializable;

/**
 * Created by FreeMason on 2016/2/20.
 */
public class FarmQuickPayModel implements Serializable{
    private String farmAliasId;
    private String userAliasId;
    private String quickPaySn;
    private String price;

    public String getFarmAliasId() {
        return farmAliasId;
    }

    public void setFarmAliasId(String farmAliasId) {
        this.farmAliasId = farmAliasId;
    }

    public String getUserAliasId() {
        return userAliasId;
    }

    public void setUserAliasId(String userAliasId) {
        this.userAliasId = userAliasId;
    }

    public String getQuickPaySn() {
        return quickPaySn;
    }

    public void setQuickPaySn(String quickPaySn) {
        this.quickPaySn = quickPaySn;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
