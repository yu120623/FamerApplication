package com.baseandroid.postionviewpager;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.baseandroid.R;

public class PostionViewPager extends RelativeLayout{
	private Context context;
	private View view;
	private ViewPager viewPager;
	private PagerAdapter pagerAdapter;
	private List<ImageView> postions;
	private LayoutInflater inflater;
	private LinearLayout viewPagerPostionLayout;
	private Boolean flag = null;
	private float x1;
	private float x2;

	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			int index = viewPager.getCurrentItem();
			if(index + 1 >= pagerAdapter.getCount())
				viewPager.setCurrentItem(0);	
			else
				viewPager.setCurrentItem(index+1);	
			handler.sendEmptyMessageDelayed(0,3500);
		};
	};
	public PostionViewPager(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		init();
	}

	public PostionViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}

	public PostionViewPager(Context context) {
		super(context);
		this.context = context;
		init();
	}
	
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if(viewPager.getCurrentItem() == 0){
			if(ev.getAction() == MotionEvent.ACTION_DOWN){
				x1 = ev.getX(); 
			}else if(ev.getAction() == MotionEvent.ACTION_MOVE){
				x2 = ev.getX();
				if(flag != null){
					getParent().requestDisallowInterceptTouchEvent(flag); 
					return super.dispatchTouchEvent(ev);
				}
				if(x1 - x2 > 0){
					flag = true;
				}else{
					flag = false; 
				}
				getParent().requestDisallowInterceptTouchEvent(flag); 
			}else if(ev.getAction() == MotionEvent.ACTION_UP){
				flag = null;
			}		
		}else{
			getParent().requestDisallowInterceptTouchEvent(true); 
		}    	
		return super.dispatchTouchEvent(ev);
	}

	private void init() {
		inflater = LayoutInflater.from(context);
		view = inflater.inflate(R.layout.postion_view_pager, this,false);
		this.addView(view,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		viewPager = (ViewPager) view.findViewById(R.id.view_pager);
		viewPagerPostionLayout = (LinearLayout) view.findViewById(R.id.view_pager_postion_layout);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {			
			@Override
			public void onPageSelected(int arg0) {
				changeViewPagerPostion(arg0);
			}			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});	
		handler.sendEmptyMessageDelayed(0,2000);
	}
	
	private void changeViewPagerPostion(int index){
		for(int i = 0;i < postions.size();i++){
			postions.get(i).setImageResource(R.mipmap.white_yuan);
			if(i == index)
				postions.get(i).setImageResource(R.mipmap.yuan);
		}
	}
	
	public void setAdapter(PagerAdapter adapter){
		viewPager.setAdapter(adapter);
		this.pagerAdapter = adapter;
		postions = new ArrayList<ImageView>();
		viewPagerPostionLayout.removeAllViews();
		for(int i = 0;i< adapter.getCount();i++){
			ImageView postion = (ImageView) inflater.inflate(R.layout.view_pager_postion, viewPagerPostionLayout,false);	
			viewPagerPostionLayout.addView(postion);
			postions.add(postion);
		}
		changeViewPagerPostion(0);
	}
	
	public ViewPager getViewPager() {
		return viewPager;
	}
	
	public void notifyDataSetChanged(){
		pagerAdapter.notifyDataSetChanged();
		viewPagerPostionLayout.removeAllViews();
		postions.clear();
		for(int i = 0;i< pagerAdapter.getCount();i++){
			ImageView postion = (ImageView) inflater.inflate(R.layout.view_pager_postion, viewPagerPostionLayout,false);	
			viewPagerPostionLayout.addView(postion);
			postions.add(postion);
		}
		changeViewPagerPostion(0);
	}
	
//	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//	    int height = 0;
//	    for(int i = 0; i < getChildCount(); i++) {
//	      View child = getChildAt(i);
//	      child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//	      int h = child.getMeasuredHeight();
//	      if(h > height) height = h;
//	    }
//	    heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
//	    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//  	}
}
