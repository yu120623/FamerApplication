package com.project.farmer.famerapplication.ptr;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;


import com.egceo.app.myfarm.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

public class CustomUIHandler extends ImageView implements PtrUIHandler {
    private AnimationDrawable drawable;
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
        setImageDrawable(drawable);
        setLayoutParams(new PtrFrameLayout.LayoutParams(300,100));
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
