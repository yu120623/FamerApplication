package com.baseandroid;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {
	protected Context context;
	protected BaseActivity activity;
	protected LayoutInflater inflater;
	protected SharedPreferences sp;
	protected View view;//缓存Fragment view
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.inflater = inflater;
		if(view == null || !isCacheView()){
			view = inflater.inflate(getContentView(), container,false);	
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
