package com.egceo.app.myfarm;

import android.content.Intent;

import com.baseandroid.BaseApplication;
import com.egceo.app.myfarm.http.NormalQueue;
import com.egceo.app.myfarm.services.LocationService;
import com.egceo.app.myfarm.services.UploadService;

/**
 * Created by Administrator on 2015/12/16.
 */
public class AppApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        NormalQueue.init(this);
        Intent loaction = new Intent(this,LocationService.class);
        this.startService(loaction);
        Intent upload = new Intent(this, UploadService.class);
        this.startService(upload);

    }
}
