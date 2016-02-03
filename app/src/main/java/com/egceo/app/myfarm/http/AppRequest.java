package com.egceo.app.myfarm.http;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.egceo.app.myfarm.entity.Error;
import com.egceo.app.myfarm.entity.TransferObject;
import com.egceo.app.myfarm.util.AppUtil;

/**
 * Created by Administrator on 2015/12/22.
 */
public class AppRequest {

    private TransferObject data;
    private AppHttpResListener responseListener;
    private String url;
    private byte[] file = null;
    private Context context;
    public AppRequest(Context context,String url, AppHttpResListener responseListener, TransferObject data) {
        this.data = data;
        this.url = url;
        this.context = context;
        this.responseListener = responseListener;
        responseListener.setContext(context);
    }

    public AppRequest(Context context,String url, AppHttpResListener responseListener, TransferObject data, byte[] file) {
        this.data = data;
        this.url = url;
        this.context = context;
        this.responseListener = responseListener;
        responseListener.setContext(context);
        this.file = file;
    }

    public AppRequest(Context context, String url, AppHttpResListener responseListener) {
        this.url = url;
        this.context = context;
        this.responseListener = responseListener;
        responseListener.setContext(context);
    }

    public void execute(){
        PostRequest postRequest = new PostRequest(url, new Response.Listener<TransferObject>() {
            @Override
            public void onResponse(TransferObject data) {
                if(data.getMessage() == null || !AppUtil.RES_STATUS.STATUS_OK.equals(data.getMessage().getStatus())){
                    Error e = new Error();
                    e.setErrorMsg(data.getMessage().getMessage());
                    e.setErrorType(Error.API_ERROR);
                    responseListener.onFailed(e);
                    responseListener.onEnd();
                }else {
                    responseListener.onSuccess(data);
                    responseListener.onEnd();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Error e = new Error();
                e.setErrorMsg(error.getMessage());
                e.setErrorType(Error.SERVER_ERROR);
                responseListener.onFailed(e);
                responseListener.onEnd();
            }
        }, data,file);
        NormalQueue.getInstance().addToQueue(postRequest);
    }
}


