package com.baseandroid.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

public class InScrollViewGridView extends GridView {
	public InScrollViewGridView(Context context) {
		super(context);
	}

	public InScrollViewGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public InScrollViewGridView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

	  public boolean dispatchTouchEvent(MotionEvent ev){
	    	if(ev.getAction() == MotionEvent.ACTION_MOVE){
	    		 return true;  //��ֹ����
	    	}
	    	return super.dispatchTouchEvent(ev);
	    }
}
