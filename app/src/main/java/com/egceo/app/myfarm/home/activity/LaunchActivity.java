package com.egceo.app.myfarm.home.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.baseandroid.BaseActivity;
import com.egceo.app.myfarm.R;
import com.egceo.app.myfarm.db.CodeDao;
import com.egceo.app.myfarm.db.DBHelper;
import com.egceo.app.myfarm.entity.Code;
import com.egceo.app.myfarm.util.AppUtil;

/**
 * Created by Administrator on 2015/12/29.
 */
public class LaunchActivity extends BaseActivity {
    private ImageView home;
    @Override
    protected void initViews() {
        if(sp.getBoolean(AppUtil.IS_FIRST_APP,true)){
            sp.edit().putBoolean(AppUtil.IS_FIRST_APP,false).commit();
        }
        home = (ImageView) this.findViewById(R.id.home);
        /*
        AnimationSet anim = (AnimationSet) AnimationUtils.loadAnimation(context,R.anim.launch_anim);
        home.startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(context,MainActivityNew.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });*/
        handler.sendEmptyMessageDelayed(0,3000);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Intent intent = new Intent(context,MainActivityNew.class);
            startActivity(intent);
            finish();
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.activity_launch;
    }

    @Override
    protected boolean isHideActonBar() {
        return true;
    }

    @Override
    protected String setActionBarTitle() {
        return "";
    }
}
