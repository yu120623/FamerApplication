package com.egceo.app.myfarm.home.activity;

import android.app.Service;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Message;
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
    private View packageEnd;
    private TextView yaoAgain;
    private TextView packageSuccessDesc;
    private TextView packageFailedDesc;
    private ImageView yaoyao;
    private ImageView yaoyao2;
    private SensorManager sensorManager;
    private List<ActivityModel> activityModels;
    private Vibrator vibrator;
    private boolean isEnableSensor = true;
    private TransferObject data;
    private ActivityModel activityModel;
    private int index = 0;
    private MediaPlayer mediaPlayer1;//鸡蛋碎裂声音
    private MediaPlayer mediaPlayer2;//微信摇一摇声音
    private MediaPlayer mediaPlayer3;//有红包
    private MediaPlayer mediaPlayer4;//没红包
    private TextView packageSuccessMoney;

    private float LastX,LastY,LastZ;
    private AnimationDrawable animationDrawable;

    @Override
    protected void initViews() {
        showProgress();
        findViews();
        initData();
        loadRedPackage();
    }

    private void loadRedPackage() {
        isEnableSensor =false;
        String url = API.URL + API.API_URL.READ_PACKAGES;
        data = new TransferObject();
        data.setUUIDs(new ArrayList<String>());
        data.setType((String) getIntent().getSerializableExtra("id"));
        data.setLBSValue(AppUtil.getHttpData(context).getCityCode());
        AppRequest request  = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                activityModels = data.getActivityModels();
                if(activityModels == null || activityModels.size() <= 0){
                    showEndEvent();
                    return;
                }
                yaoyao.setVisibility(View.VISIBLE);
                yaoyao2.setVisibility(View.GONE);
                isEnableSensor = true;
                ImageLoaderUtil.getInstance().displayImg(redPackageBg,activityModels.get(0).getResource().getResourceLocation());
            }

            @Override
            public void onEnd() {
                super.onEnd();
                hideProgress();
            }
        },data);
        request.execute();
    }

    private void initData() {
        animationDrawable = (AnimationDrawable) redPackageBtn.getDrawable();
        animationDrawable.stop();
        activityModels = new ArrayList<>();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
        mediaPlayer1 = MediaPlayer.create(context,R.raw.egg);
        mediaPlayer2 = MediaPlayer.create(context,R.raw.wx_voice);
        mediaPlayer3 = MediaPlayer.create(context,R.raw.package_success);
        mediaPlayer4 = MediaPlayer.create(context,R.raw.package_failed);
    }

    private void findViews() {
        redPackageBg = (ImageView) this.findViewById(R.id.red_package_bg);
        redPackageBtn  = (ImageView) this.findViewById(R.id.red_package_btn);
        packageInfo = this.findViewById(R.id.package_info);
        packageSuccess = this.findViewById(R.id.package_success);
        packageFailed = this.findViewById(R.id.package_failed);
        packageSuccessDesc = (TextView) this.findViewById(R.id.package_success_desc);
        packageFailedDesc = (TextView) this.findViewById(R.id.package_failed_desc);
        packageEnd = this.findViewById(R.id.package_end);
        yaoAgain = (TextView) this.findViewById(R.id.yao_again);
        packageSuccessMoney = (TextView) this.findViewById(R.id.package_success_money);
        yaoyao = (ImageView) this.findViewById(R.id.yaoyao);
        yaoyao2 = (ImageView) this.findViewById(R.id.yaoyao2);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_red_package;
    }

    @Override
    protected String setActionBarTitle() {
        return "我的农庄特别活动";
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (!isEnableSensor) return;
        int sensorType = event.sensor.getType();
        float[] values = event.values;
        if (sensorType == Sensor.TYPE_ACCELEROMETER) {
            if ((Math.abs(values[0]) > 19 || Math.abs(values[1]) > 19 || Math
                    .abs(values[2]) > 19)) {
                //vibrator.vibrate(new long[]{80,300,80,300},-1);
                mediaPlayer2.start();
                handler.sendEmptyMessageDelayed(1,1500);
            }
        }
//        if (sensorType == Sensor.TYPE_ACCELEROMETER) {
//            float NowX=event.values[0];
//            float NowY=event.values[1];
//            float NowZ=event.values[2];
//            //计算x,y,z变化量
//            float DeltaX=NowX-LastX;
//            float DeltaY=NowY-LastY;
//            float DeltaZ=NowZ-LastZ;
//            //赋值
//            LastX=NowX;
//            LastY=NowY;
//            LastZ=NowZ;
//            double NowSpeed = Math.sqrt(DeltaX * DeltaX + DeltaY * DeltaY + DeltaZ * DeltaZ)/50 * 10000;
//            if(NowSpeed >= 3000){
//                mediaPlayer2.start();
//                handler.sendEmptyMessageDelayed(1,1500);
//            }
//        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void qiangPackage() {
        isEnableSensor = false;
        String url = API.URL + API.API_URL.GET_PACKAGES;
        TransferObject data = AppUtil.getHttpData(context);
        data.setActivityId(activityModels.get(0).getId());
        AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                activityModel = data.getActivityModel();
                packageSuccessMoney.setText(activityModel.getRedPackagdMoney()+"");
                openPackage();
            }
        },data);
        request.execute();
    }

    private void openPackage() {
        redPackageBtn.setVisibility(View.GONE);
        packageInfo.setVisibility(View.VISIBLE);
        if(activityModel.getIsOk().equals("1")){
            mediaPlayer3.start();
            packageSuccess.setVisibility(View.VISIBLE);
            yaoAgain.setVisibility(View.VISIBLE);
            yaoAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    redPackageBtn.setVisibility(View.VISIBLE);
                    activityModel = null;
                    activityModels = null;
                    packageSuccess.setVisibility(View.GONE);
                    yaoAgain.setVisibility(View.GONE);
                    packageInfo.setVisibility(View.GONE);
                    index = 0;
                    loadRedPackage();
                }
            });
            packageFailed.setVisibility(View.GONE);
            packageSuccessDesc.setText(activityModel.getPromptStr());
        }else{
            mediaPlayer4.start();
            packageSuccess.setVisibility(View.GONE);
            packageFailed.setVisibility(View.VISIBLE);
            packageFailedDesc.setText("您的次数已用完");
        }
        yaoyao.setVisibility(View.GONE);
        yaoyao2.setVisibility(View.GONE);
        isEnableSensor = false;
    }


    private void showEndEvent() {
        redPackageBtn.setVisibility(View.GONE);
        packageInfo.setVisibility(View.VISIBLE);
        packageEnd.setVisibility(View.VISIBLE);
        packageSuccess.setVisibility(View.GONE);
        packageFailed.setVisibility(View.GONE);
        isEnableSensor = false;
    }


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 0) {
                yaoyao.setVisibility(View.GONE);
                yaoyao2.setVisibility(View.VISIBLE);
                isEnableSensor = true;
                index = 1;
            }else if(msg.what == 1){
                if(index > 0){
                    qiangPackage();
                }else {

                    int duration = 0;
                    for(int i = 0 ;i < animationDrawable.getNumberOfFrames();i++){
                        duration += animationDrawable.getDuration(i);
                    }
                    animationDrawable.start();
                    mediaPlayer1.start();
                    handler.sendEmptyMessageDelayed(0,duration);
                    isEnableSensor = false;
                }
            }
        }
    };

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
