package com.egceo.app.myfarm.entity;

/**
 * Created by FreeMason on 2016/2/28.
 */
public class WalletModel {
    private String userId;
    private String ecValue;
    private Double lvValue;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEcValue() {
        return ecValue;
    }

    public void setEcValue(String ecValue) {
        this.ecValue = ecValue;
    }

    public Double getLvValue() {
        return lvValue;
    }

    public void setLvValue(Double lvValue) {
        this.lvValue = lvValue;
    }
}
