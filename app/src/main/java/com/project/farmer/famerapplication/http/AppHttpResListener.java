package com.project.farmer.famerapplication.http;

import android.content.Context;

import com.android.volley.VolleyError;
import com.baseandroid.util.CommonUtil;
import com.project.farmer.famerapplication.entity.TransferObject;

/**
 * Created by Administrator on 2015/12/22.
 */
public abstract class AppHttpResListener {
    private Context context;
    public abstract void onSuccess(TransferObject data);
    public void onFailed(VolleyError error){
        if(context != null){
            CommonUtil.showMessage(context,error.toString());
        }
    }
    public void onEnd(){

    }

    public void setContext(Context context) {
        this.context = context;
    }
}

