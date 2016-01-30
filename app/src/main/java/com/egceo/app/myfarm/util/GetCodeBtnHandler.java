package com.egceo.app.myfarm.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.egceo.app.myfarm.R;


public class GetCodeBtnHandler extends Handler {
	private String key;
	private int time;
	private Context context;
	private SharedPreferences sp;
	public GetCodeBtnHandler(String key,SharedPreferences sp,Context context) {
		this.key = key;
		time = sp.getInt(key, 0);
		this.context = context;
		this.sp = sp;
	}
	@Override
	public void handleMessage(Message msg) {
		TextView textView = (TextView) msg.obj;
		time--;
		sp.edit().putInt(key, time).commit();
		if(time <= 0){
			textView.setText(R.string.get_code);
			textView.setClickable(true);
		}else{
			textView.setText(time+context.getResources().getString(R.string.get_code));
			textView.setClickable(false);
			Message message = new Message();
			message.obj = textView;
			sendMessageDelayed(message, 1000);
		}
	}
	
	public void resetDate(){
		time = sp.getInt(key, 0);
	}

	public void setNextTime(int time){
		this.time = time;
		sp.edit().putInt(key,time).commit();
	}
}
