package com.egceo.app.myfarm.entity;

/**
 * Created by FreeMason on 2016/1/29.
 */
public class SMSObject {
    private String smsId;
    private String smsVerificationCode;

    public String getSmsId() {
        return smsId;
    }

    public void setSmsId(String smsId) {
        this.smsId = smsId;
    }

    public String getSmsVerificationCode() {
        return smsVerificationCode;
    }

    public void setSmsVerificationCode(String smsVerificationCode) {
        this.smsVerificationCode = smsVerificationCode;
    }
}
