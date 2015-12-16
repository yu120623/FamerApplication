package com.baseandroid.listener;

public interface HttpResponseListener {
	public void onSuccess(int responseCode, String response);
	
	public void onFailure(int responseCode,Throwable error,String response);
}
