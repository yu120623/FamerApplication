package com.egceo.app.myfarm;

import android.content.Intent;

import com.baseandroid.BaseApplication;
import com.egceo.app.myfarm.http.NormalQueue;
import com.egceo.app.myfarm.services.LocationService;
import com.egceo.app.myfarm.services.UploadService;
import com.zxinsight.MWConfiguration;
import com.zxinsight.MagicWindowSDK;

import cn.jpush.android.api.JPushInterface;

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
//        Intent ibeaconService = new Intent(this, IbeaconService.class);
//        this.startService(ibeaconService);
        JPushInterface.init(this);
//        MWConfiguration config = new MWConfiguration(this);
//        config.setChannel("yiqiwan")
//                //开启Debug模式，显示Log，release时注意关闭
//                .setDebugModel(true)
//                        //带有Fragment的页面。具体查看2.2.2
//                . setPageTrackWithFragment(true)
//                        //设置分享方式，如果之前有集成sharesdk，可在此开启
//                . setSharePlatform(MWConfiguration.ORIGINAL);
        //MagicWindowSDK.initSDK(config);
    }
}
