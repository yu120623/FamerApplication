package com.egceo.app.myfarm.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.egceo.app.myfarm.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

public class CustomUIHandler extends LinearLayout implements PtrUIHandler {
    private AnimationDrawable drawable;
    private ImageView img;
    public CustomUIHandler(Context context) {
        super(context);
        init();
    }

    public CustomUIHandler(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomUIHandler(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        drawable = (AnimationDrawable) this.getResources().getDrawable(R.drawable.loading);
        img = new ImageView(getContext());
        img.setImageDrawable(drawable);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int)getResources().getDimension(R.dimen.ptr_loding_width),(int)getResources().getDimension(R.dimen.ptr_loding_height));
        img.setLayoutParams(params);
        addView(img);
    }


    @Override
    public void onUIReset(PtrFrameLayout frame) {
        drawable.stop();
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        drawable.start();
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {

    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        drawable.start();
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

    }
}
