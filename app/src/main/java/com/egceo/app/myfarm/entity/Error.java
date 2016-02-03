package com.egceo.app.myfarm.entity;

/**
 * Created by FreeMason on 2016/2/3.
 */
public class Error{

    public static final String SERVER_ERROR = "server_error";
    public static final String API_ERROR = "api_error";

    private String errorType;
    private String errorMsg;

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
