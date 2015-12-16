package com.baseandroid;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public abstract class BaseActivity extends Activity {
	protected Context context;
	protected Activity activity;
	protected LayoutInflater inflater;
	protected SharedPreferences sp;
	protected ActionBar actionBar;
	protected View actionBarView;
	protected FragmentManager fragmentManager;
	protected ImageButton leftBtn;
	protected ImageButton rightBtn;
	protected TextView rightTextBtn;
	protected TextView actionBarTitle;
	protected RelativeLayout rightLayout;
	private NetWorkBroadcast netWorkBroadcast;
	private TextView netWorkInfo;
	protected Bundle savedInstanceState;
	private View actionbarView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		this.savedInstanceState = savedInstanceState;
		if (isFullScreen()) {
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}
		init();
		addContentView();
		if(!isFullScreen() && !isHideActonBar()){
			initActonBar();
		}if(isHideActonBar() && !isFullScreen()){
			actionbarView.setVisibility(View.GONE);
		}
		initViews();
	}

	protected abstract void initViews();
	
	protected void init() {
		context = this.getApplicationContext();
		activity = this;
		actionbarView = this.findViewById(R.id.action_bar);
		inflater = LayoutInflater.from(context);
		sp = this.getSharedPreferences("sp", MODE_PRIVATE);
		fragmentManager = this.getFragmentManager();
	}

	private void initNetWorkBraodcast() {
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
		netWorkBroadcast = new NetWorkBroadcast();
		registerReceiver(netWorkBroadcast, filter);
	}

	protected abstract int getContentView();
	
	protected abstract String setActionBarTitle();

	private void addContentView() {
		if(isAttachToLayout()){
			this.setContentView(R.layout.base_activity);
			LinearLayout layout = (LinearLayout) this.findViewById(R.id.base_framelayout);
			View view = inflater.inflate(getContentView(), layout,false);		
			layout.addView(view,new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT));
		}else{
			this.setContentView(getContentView());
		}
	}
	
	protected boolean isAttachToLayout(){
		return true;
	}


	public void initActonBar() {

	}

	protected boolean isFullScreen() {
		return false;
	}
	
	protected void gotoActivity(Intent intent){
		startActivity(intent);
		startEffect();
	}

	// 显示后退按钮
	protected void showBackBtn() {
		leftBtn.setVisibility(View.VISIBLE);
		leftBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
				backEffect();
			}
		});
	}
	
	protected void backEffect(){
		overridePendingTransition(R.anim.slide_right_out, R.anim.slide_right_in);		
	}
	protected void startEffect(){
		overridePendingTransition(R.anim.slide_left_in,R.anim.slide_left_out);
	}
	
	class NetWorkBroadcast extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			netWorkInfo = (TextView) findViewById(R.id.net_work_info);
			ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE); 
			NetworkInfo info = cm.getActiveNetworkInfo();
			if(info == null){
				netWorkInfo.setVisibility(View.VISIBLE);
				netWorkInfo.setOnClickListener(goToNetWorkSetting);
			}else{
				netWorkInfo.setVisibility(View.GONE);
			}			
		}		
	}
	
	private OnClickListener goToNetWorkSetting = new OnClickListener() {		
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
			activity.startActivityForResult( intent , 0);
			overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
		}
	};
	
	@Override
	protected void onPause() {
		if(isShowNetInfo())
			unregisterReceiver(netWorkBroadcast);
		super.onPause();
	};
	
	@Override
	protected void onResume() {
		if(isShowNetInfo())
			initNetWorkBraodcast();
		super.onResume();
	}
	

	
	protected boolean isShowNetInfo() {
		return false;
	}
	
	protected boolean isHideActonBar(){
		return false;
	}
	
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			onKeyBack();
			return false;
		}else if (keyCode == KeyEvent.KEYCODE_MENU) {
			onKeyMenu();
			return false;
		} else if (keyCode == KeyEvent.KEYCODE_HOME) {
			onKeyHome();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	protected void onKeyBack(){
		finish();
		backEffect();
	}
	protected void onKeyHome(){}
	protected void onKeyMenu(){}

	public void setActionBarTitle(String actionBarTitle) {
		this.actionBarTitle.setText(actionBarTitle);
	}
	
	public ImageButton getRightBtn() {
		return rightBtn;
	}

	public TextView getRightTextBtn() {
		return rightTextBtn;
	}

	public void setRightTextBtn(Button rightTextBtn) {
		this.rightTextBtn = rightTextBtn;
	}
}
