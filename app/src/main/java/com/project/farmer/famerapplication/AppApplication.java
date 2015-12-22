package com.project.farmer.famerapplication;

import com.baseandroid.BaseApplication;
import com.project.farmer.famerapplication.http.NormalQueue;

/**
 * Created by Administrator on 2015/12/16.
 */
public class AppApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        NormalQueue.init(this);
    }
}
