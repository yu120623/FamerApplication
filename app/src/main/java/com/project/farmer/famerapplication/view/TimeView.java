package com.project.farmer.famerapplication.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.farmer.famerapplication.R;

public class TimeView extends RelativeLayout {
    private Context context;
    private TextView hour;//小时
    private TextView minute;//分钟
    private TextView seconds;//秒
    private Long ms;//总毫秒数
    private boolean isStop = false;
    TimeHandler handler;

    public TimeView(Context context) {
        super(context);
        init(context);
    }

    public TimeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TimeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.layout_time,this,true);
        hour = (TextView) this.findViewById(R.id.hour);
        minute = (TextView) this.findViewById(R.id.minute);
        seconds = (TextView) this.findViewById(R.id.seconds);
    }

    public void start(Long ms){
        this.ms = ms;
        if(ms < 0){
            this.ms = 0l;
        }
        updateTime();
        if(handler != null){
            handler.setStopTimeHanlder(true);
        }
        handler = new TimeHandler();
        handler.sendEmptyMessageDelayed(0,1000);
    }

    private class TimeHandler extends Handler{
        private boolean stopTimeHanlder = false;
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1) {
                Log.i("","");
                return;
            }
            if(stopTimeHanlder)return;
            ms = ms - 1000l;
            if(ms < 0l) {
                ms = 0l;
                return;
            }
            updateTime();
            handler.sendEmptyMessageDelayed(0,1000);
        }

        public void setStopTimeHanlder(boolean stopTimeHanlder) {
            this.stopTimeHanlder = stopTimeHanlder;
        }
    }

    private void updateTime() {
        int mHour = (int)(ms / (1000 * 60 * 60));
        int mMinute = (int)((ms % (1000 * 60 * 60)) / (1000 * 60));
        int mSecond = (int)((ms % (1000 * 60)) / 1000);
        hour.setText(formatTime(mHour));
        minute.setText(formatTime(mMinute));
        seconds.setText(formatTime(mSecond));
    }


    private String formatTime(int time){
        if(time < 10){
            return "0"+time;
        }
        return time+"";
    }
}
