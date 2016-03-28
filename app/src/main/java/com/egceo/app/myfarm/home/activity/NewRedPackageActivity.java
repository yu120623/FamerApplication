package com.egceo.app.myfarm.home.activity;

import android.app.Service;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baseandroid.BaseActivity;
import com.baseandroid.util.ImageLoaderUtil;
import com.egceo.app.myfarm.R;
import com.egceo.app.myfarm.entity.ActivityModel;
import com.egceo.app.myfarm.entity.TransferObject;
import com.egceo.app.myfarm.http.API;
import com.egceo.app.myfarm.http.AppHttpResListener;
import com.egceo.app.myfarm.http.AppRequest;
import com.egceo.app.myfarm.util.AppUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NewRedPackageActivity extends BaseActivity implements SensorEventListener {
    private ImageView redPackageBg;
    private ImageView redPackageBtn;
    private View packageInfo;
    private View packageSuccess;
    private View packageFailed;
    private TextView packageSuccessDesc;
    private TextView packageFailedDesc;
    private Set<String> ids;
    private int index = 0;
    private SensorManager sensorManager;
    private List<ActivityModel> activityModels;
    private Vibrator vibrator;
    private long min = 0;
    private boolean isEnableSensor = true;
    private TransferObject data;
    private ActivityModel activityModel;
    private boolean waitReload = false;

    @Override
    protected void initViews() {
        findViews();
        initData();
    }

    private void initData() {
        activityModels = new ArrayList<>();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
        ids = sp.getStringSet(AppUtil.SP_IBEACON_IDS, new HashSet<String>());
    }

    private void findViews() {
        redPackageBg = (ImageView) this.findViewById(R.id.red_package_bg);
        redPackageBtn  = (ImageView) this.findViewById(R.id.red_package_btn);
        packageInfo = this.findViewById(R.id.package_info);
        packageSuccess = this.findViewById(R.id.package_success);
        packageFailed = this.findViewById(R.id.package_failed);
        packageSuccessDesc = (TextView) this.findViewById(R.id.package_success_desc);
        packageFailedDesc = (TextView) this.findViewById(R.id.package_failed_desc);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_red_package;
    }

    @Override
    protected String setActionBarTitle() {
        return "红包";
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(!isEnableSensor)return;
        int sensorType = event.sensor.getType();
        float[] values = event.values;
        if (sensorType == Sensor.TYPE_ACCELEROMETER) {
            if ((Math.abs(values[0]) > 17 || Math.abs(values[1]) > 17 || Math
                    .abs(values[2]) > 17)) {
                if(System.currentTimeMillis() - min < 800) {
                    min = System.currentTimeMillis();
                    return;
                }
                AnimationDrawable animationDrawable = (AnimationDrawable) redPackageBtn.getDrawable();
                animationDrawable.start();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
