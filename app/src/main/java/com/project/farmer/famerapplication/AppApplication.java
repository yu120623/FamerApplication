package com.project.farmer.famerapplication;

import android.content.Intent;

import com.baseandroid.BaseApplication;
import com.project.farmer.famerapplication.http.NormalQueue;
import com.project.farmer.famerapplication.services.LocationService;

/**
 * Created by Administrator on 2015/12/16.
 */
public class AppApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        NormalQueue.init(this);
        Intent intent = new Intent(this,LocationService.class);
        this.startService(intent);
    }
}
