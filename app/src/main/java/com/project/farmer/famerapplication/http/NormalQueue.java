package com.project.farmer.famerapplication.http;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Administrator on 2015/12/22.
 */
public class NormalQueue {
    private static RequestQueue queue;
    private static NormalQueue instance;
    private NormalQueue() {}
    public static void init(Application application){
        queue = Volley.newRequestQueue(application);
        instance = new NormalQueue();
    }

    public static NormalQueue getInstance(){
        return instance;
    }

    public Request addToQueue(Request request){
        return queue.add(request);
    }
}
