package com.egceo.app.myfarm;

import android.content.Intent;

import com.baseandroid.BaseApplication;
import com.egceo.app.myfarm.http.NormalQueue;
import com.egceo.app.myfarm.services.LocationService;

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
        /*
        PlatformConfig.setWeixin("wx89f5f3a4bf3c8ea6", "24884fea557311fb9d7196c93718c840");
        //微信 appid appsecret
        PlatformConfig.setSinaWeibo("3038081932","e131ed82dee48cd90cdcf2c0ce9282f7");
        //新浪微博 appkey appsecret
        PlatformConfig.setQQZone("1104803292", "Aqx4nyhStRdPASV7");*/
        // QQ和Qzone appid appkey
    }
}
