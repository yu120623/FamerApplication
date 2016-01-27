package com.egceo.app.myfarm.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.egceo.app.myfarm.util.AppUtil;

/**
 * Created by Administrator on 2015/12/29.
 */
public class LocationService extends Service {
    private Context context;
    private SharedPreferences sp;
    private AMapLocationClient mLocationClient;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        context = getApplicationContext();
        sp = this.getSharedPreferences("sp", MODE_PRIVATE);
        mLocationClient = new AMapLocationClient(context);
        LocationModeSource locationModeSource = new LocationModeSource();
        mLocationClient.setLocationListener(locationModeSource);
        mLocationClient.startLocation();
        return super.onStartCommand(intent, flags, startId);
    }


    public class LocationModeSource implements AMapLocationListener {

        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            Log.i("11111111111111111111", "onLocationChanged: "+aMapLocation.getErrorInfo());
            if(aMapLocation.getErrorCode() == 0) {
                mLocationClient.stopLocation();
                if(!"".equals(sp.getString(AppUtil.SP_CITY_CODE,""))){
                    sp.edit().putString(AppUtil.SP_NEW_CITY_CODE, aMapLocation.getCityCode()).commit();
                    sp.edit().putString(AppUtil.SP_NEW_CITY_NAME, aMapLocation.getCity()).commit();
                }else {
                    sp.edit().putString(AppUtil.SP_CITY_CODE, aMapLocation.getCityCode()).commit();
                    sp.edit().putString(AppUtil.SP_CITY_NAME, aMapLocation.getCity()).commit();
                }
                sp.edit().putFloat(AppUtil.SP_LAT, Double.valueOf(aMapLocation.getLatitude()).floatValue()).commit();
                sp.edit().putFloat(AppUtil.SP_LOG, Double.valueOf(aMapLocation.getLongitude()).floatValue()).commit();
            }
        }
    }
}