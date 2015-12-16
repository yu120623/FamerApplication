package com.baseandroid.listener;

import com.baseandroid.util.CommonUtil;

import android.view.View;
import android.view.View.OnClickListener;

public abstract class OnBanDoubleClickListener implements OnClickListener {

	@Override
	public void onClick(View v) {
		if(CommonUtil.isFastDoubleClick())
			return;
		onBanDoubleClick(v);
	}

	public abstract void onBanDoubleClick(View v);
}
