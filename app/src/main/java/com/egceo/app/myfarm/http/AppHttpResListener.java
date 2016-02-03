package com.egceo.app.myfarm.http;

import android.content.Context;

import com.android.volley.VolleyError;
import com.baseandroid.util.CommonUtil;
import com.egceo.app.myfarm.entity.Error;
import com.egceo.app.myfarm.entity.TransferObject;

/**
 * Created by Administrator on 2015/12/22.
 */
public abstract class AppHttpResListener {
    private Context context;
    public abstract void onSuccess(TransferObject data);
    public void onFailed(Error error){
        if(context != null || error != null){
            CommonUtil.showMessage(context,error.getErrorMsg());
        }
    }
    public void onEnd(){
        CommonUtil.dismissSimpleProgressDialog();
    }

    public void setContext(Context context) {
        this.context = context;
    }
}

