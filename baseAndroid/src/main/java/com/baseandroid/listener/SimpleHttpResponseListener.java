package com.baseandroid.listener;

import android.content.Context;

import com.baseandroid.R;
import com.baseandroid.util.CommonUtil;

public abstract class SimpleHttpResponseListener implements HttpResponseListener {
	private Context context;
	public SimpleHttpResponseListener(Context context) {
		this.context = context;
	}
	
	@Override
	public void onFailure(int responseCode, Throwable error, String response) {
		CommonUtil.showMessage(context, R.string.server_error_default_msg);
	}

}
