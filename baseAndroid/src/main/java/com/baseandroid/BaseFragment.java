package com.baseandroid;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public abstract class BaseFragment extends Fragment {
	protected Context context;
	protected BaseActivity activity;
	protected LayoutInflater inflater;
	protected SharedPreferences sp;
	protected RelativeLayout view;//缓存Fragment view
	protected View contentView;
	private ImageView progress;
	private AnimationDrawable progressDrawable;
	private View progressView;
	private View retryView;
	protected ImageView retryImg;
	protected TextView retryText;
	private TextView progressText;
	protected Button retryButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.inflater = inflater;
		if(view == null || !isCacheView()){
			//view = inflater.inflate(getContentView(), container,false);
			view = (RelativeLayout) inflater.inflate(R.layout.base_fragment, container,false);
			contentView = inflater.inflate(getContentView(), container,false);
			view.addView(contentView,new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT));
			init();
			initViews();		
		}
		ViewGroup parent = (ViewGroup)view.getParent();  
        if (parent != null) {  
            parent.removeView(view);  
        }   
		return view;
	}
	
	protected void init() {
		context = this.getActivity().getApplicationContext();
		activity = (BaseActivity) this.getActivity();
		sp = context.getSharedPreferences("sp", Activity.MODE_PRIVATE);
		progress = (ImageView) this.findViewById(R.id.progress);
		progressDrawable = (AnimationDrawable) progress.getDrawable();
		progressView = this.findViewById(R.id.progress_view);
		progressText = (TextView) this.findViewById(R.id.progress_text);
		retryView = this.findViewById(R.id.retry_view);
		retryButton = (Button) this.findViewById(R.id.retry);
		retryButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				retry();
			}
		});
		retryImg = (ImageView) this.findViewById(R.id.retry_img);
		retryText = (TextView) this.findViewById(R.id.retry_text);
	}

	protected void retry() {

	}

	protected void showProgress(){
		progressView.setVisibility(View.VISIBLE);
		progressDrawable.start();
		contentView.setVisibility(View.GONE);
	}

	protected void hideProgress(){
		progressView.setVisibility(View.GONE);
		progressDrawable.stop();
		contentView.setVisibility(View.VISIBLE);
	}

	protected void hideRetryView(){
		retryView.setVisibility(View.INVISIBLE);
		contentView.setVisibility(View.VISIBLE);
	}

	protected void showRetryView(){
		retryView.setVisibility(View.VISIBLE);
		contentView.setVisibility(View.GONE);
	}

	protected abstract void initViews();
	protected abstract int getContentView();
	
	
	protected View findViewById(int id){
		return view.findViewById(id);
	}
	protected String getStringById(int id) {
		return this.getResources().getString(id);
	}
	
	protected boolean isCacheView(){
		return true;
	}
	
	protected void gotoActivity(Intent intent){
		startActivity(intent);
		startEffect();
	}
	
	protected void startEffect(){
		getActivity().overridePendingTransition(R.anim.slide_left_in,R.anim.slide_left_out);
	}
}
