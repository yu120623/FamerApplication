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
import com.egceo.app.myfarm.db.CodeDao;
import com.egceo.app.myfarm.db.DBHelper;
import com.egceo.app.myfarm.entity.Code;
import com.egceo.app.myfarm.util.AppUtil;

import java.util.List;

/**
 * Created by Administrator on 2015/12/29.
 */
public class LocationService extends Service {
    private Context context;
    private SharedPreferences sp;
    private AMapLocationClient mLocationClient;
    private CodeDao codeDao;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        context = getApplicationContext();
        codeDao = DBHelper.getDaoSession(context).getCodeDao();
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
                Code code = codeDao.load(1l);
                if(code == null){
                    code = new Code();
                    code.setCodeId(1l);
                    code.setCodeDesc(aMapLocation.getCity());
                    code.setCodeName(aMapLocation.getCityCode());
                    code.setCodetype(AppUtil.CODE_TYPE_AUTO);
                    codeDao.insert(code);
                }
                code = new Code();
                code.setCodeId(2l);
                code.setCodetype(AppUtil.CODE_TYEP_C_S);
                code.setCodeDesc(aMapLocation.getCity());
                code.setCodeName(aMapLocation.getCityCode());
                codeDao.insertOrReplace(code);
                sp.edit().putFloat(AppUtil.SP_LAT, Double.valueOf(aMapLocation.getLatitude()).floatValue()).commit();
                sp.edit().putFloat(AppUtil.SP_LOG, Double.valueOf(aMapLocation.getLongitude()).floatValue()).commit();
            }
        }
    }
}
