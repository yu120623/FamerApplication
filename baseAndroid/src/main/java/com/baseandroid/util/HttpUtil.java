package com.baseandroid.util;


@Deprecated
public class HttpUtil {
	
//	public static void post(AsyncHttpClient client,String url,HttpResponseListener httpResponseListener){
//		post(client,url,httpResponseListener,null);
//	}
//	
//	public static void get(AsyncHttpClient client,String url,HttpResponseListener httpResponseListener){
//		get(client,url,httpResponseListener,null);
//	}
//	
//	public static void post(AsyncHttpClient client,String url){
//		post(client,url,null,null);
//	}
//	
//	public static void get(AsyncHttpClient client,String url){
//		get(client,url,null,null);
//	}
//	
//	public static void post(AsyncHttpClient client,String url,final HttpResponseListener httpResponseListener,RequestParams params){
//		client.post(url, params, new AsyncHttpResponseHandler() {			
//			@Override
//			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//				String result = new String(responseBody);
//				if(httpResponseListener != null)
//					httpResponseListener.onSuccess(statusCode,result);
//			}			
//			@Override
//			public void onFailure(int statusCode, Header[] headers,
//					byte[] responseBody, Throwable error) {
//				String result = null;
//				if(responseBody != null)
//					result = new String(responseBody);
//				if(httpResponseListener != null)
//					httpResponseListener.onFailure(statusCode, error, result);
//			}
//		});
//	}
//	
//	public static void get(AsyncHttpClient client,String url,final HttpResponseListener httpResponseListener,RequestParams params){
//		client.get(url, params, new AsyncHttpResponseHandler() {			
//			@Override
//			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//				String result = new String(responseBody);
//				if(httpResponseListener != null)
//					httpResponseListener.onSuccess(statusCode,result);
//			}			
//			@Override
//			public void onFailure(int statusCode, Header[] headers,
//					byte[] responseBody, Throwable error) {
//				String result = new String(responseBody);
//				if(httpResponseListener != null)
//					httpResponseListener.onFailure(statusCode, error, result);
//			}
//		});
//	}
}
