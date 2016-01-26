package com.egceo.app.myfarm.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.egceo.app.myfarm.R;

import java.util.Date;

public class QiangGouBtnView extends LinearLayout{
    private LayoutInflater inflater;
    private TimeView timeView;
    private TextView qianggouBtn;
    public QiangGouBtnView(Context context) {
        super(context);
        init(context);
    }

    public QiangGouBtnView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public QiangGouBtnView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.layout_qianggou_btn,this,true);
        findViews();
    }

    private void findViews() {
        timeView = (TimeView) this.findViewById(R.id.time);
        qianggouBtn = (TextView) this.findViewById(R.id.qianggou_btn);
    }

    public void setTime(long now,long startTime,long endTime){
        if(now < startTime){
            setNotStart();
            timeView.setTimeText(new Date(startTime));
            timeView.setOnTimeEnd(null);
        }else{
            if(endTime < now){
                setEnd();
                timeView.reset();
                timeView.setOnTimeEnd(null);
            }else{
                setStarting();
                timeView.start(endTime - now);
                timeView.setOnTimeEnd(new TimeView.OnTimeEndListener() {
                    @Override
                    public void onEnd() {
                        setEnd();
                        timeView.reset();
                        timeView.setOnTimeEnd(null);
                    }
                });
            }
        }
    }

    //即将开始
    public void setNotStart(){
        timeView.setBackgroundResource(R.drawable.not_start_qianggou_time_bg);
        qianggouBtn.setBackgroundResource(R.drawable.not_start_qianggou_time_btn_bg);
        qianggouBtn.setText(R.string.not_start_qianggou);
    }


    //抢购中
    public void setStarting(){
        timeView.setBackgroundResource(R.drawable.starting_qianggou_time_bg);
        qianggouBtn.setBackgroundResource(R.drawable.starting_qianggou_time_btn_bg);
        qianggouBtn.setText(R.string.startring_qianggou);
    }

    //已经结束
    public void setEnd(){
        timeView.setBackgroundResource(R.drawable.end_qianggou_time_bg);
        qianggouBtn.setBackgroundResource(R.drawable.end_qianggou_time_btn_bg);
        qianggouBtn.setText(R.string.end_qianggou);
    }
}
