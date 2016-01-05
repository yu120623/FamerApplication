package com.project.farmer.famerapplication.http;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.baseandroid.util.Json;
import com.project.farmer.famerapplication.entity.TransferObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/22.
 */
public class PostRequest extends Request<TransferObject> {
    private TransferObject data;
    private Response.Listener<TransferObject> mListener;
    private Response.ErrorListener errorListener;
    private byte[] file;
    public PostRequest(String url, Response.Listener<TransferObject> listener,
                       Response.ErrorListener errorListener, TransferObject data) {
        super(Method.POST, url, errorListener);
        mListener = listener;
        this.data = data;
        this.errorListener = errorListener;
        setShouldCache(false);

    }

    public PostRequest(String url, Response.Listener<TransferObject> listener,
                       Response.ErrorListener errorListener, TransferObject data, byte[] file) {
        super(Method.POST, url, errorListener);
        mListener = listener;
        this.data = data;
        this.errorListener = errorListener;
        this.file = file;
        setShouldCache(false);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String,String> param = new HashMap<String,String>();
        param.put(API.PARAM_STR, Json.get().toString(data));
        return param;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        if(file != null)
            return file;
        return super.getBody();
    }


    @Override
    protected Response<TransferObject> parseNetworkResponse(
            NetworkResponse response) {
        String json = null;
        try {
            json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Log.i("++++++++json++++",json);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Response<TransferObject> res = Response.success(Json.get().toObject(json,TransferObject.class),HttpHeaderParser.parseCacheHeaders(response));
        return res;
    }

    @Override
    protected void deliverResponse(TransferObject data) {
        mListener.onResponse(data);
    }


    @Override
    public void deliverError(VolleyError error) {
        String msg = error.getMessage();
        errorListener.onErrorResponse(error);
    }
}
