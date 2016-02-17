package com.html;

import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.baseandroid.BaseActivity;
import com.baseandroid.R;

public class HtmlActivity extends BaseActivity {
	WebView webView;
	ProgressBar progressBar;
	@Override
	protected void initViews() {
		webView = (WebView) this.findViewById(R.id.webview);
		progressBar = (ProgressBar) this.findViewById(R.id.progressBar);
		String url = getIntent().getStringExtra("url");
		String title = getIntent().getStringExtra("title");
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setAllowFileAccess(true);
		webView.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return super.shouldOverrideUrlLoading(view, url);
			}
		});
		webView.setWebChromeClient(new WebChromeClient(){
			@Override
			public boolean onJsAlert(WebView view, String url, String message,
					JsResult result) {
				return true;
			}
			
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				if(newProgress == 100){
					progressBar.setVisibility(View.GONE);
				}
			}
		});
		webView.loadUrl(url);
		showBackBtn();
		
	}

	@Override
	protected void onKeyBack() {
		if(webView.canGoBack())
			webView.goBack();
		else 
			finish();
	}

	@Override
	protected int getContentView() {
		return R.layout.act_html;
	}

	@Override
	protected String setActionBarTitle() {
		return "";
	}

}
