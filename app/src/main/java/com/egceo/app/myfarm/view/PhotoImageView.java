package com.egceo.app.myfarm.view;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.baseandroid.util.CommonUtil;
import com.baseandroid.util.ImageLoaderUtil;
import com.egceo.app.myfarm.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;


//显示图片，能够放大缩小.网络图片显示加载进度条
public class PhotoImageView extends RelativeLayout {
	private NumberProgressBar downloadImgProgressBar;
	private LayoutInflater inflater;
	private PhotoView imageView;
	private PhotoViewAttacher mAttacher;
	private int rotation = 0;
	public PhotoImageView(Context context) {
		super(context);
		init();
		initView();
	}

	public PhotoImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		initView();
	}

	public PhotoImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
		initView();
	}
	
	public void init(){
		inflater = LayoutInflater.from(getContext());
		setBackgroundColor(Color.BLACK);
	}

	private void initView() {
		addImageView();
		addProgressBar();
		//addRotationBtn();
	}



	private void addImageView() {		
		imageView = new PhotoView(getContext());
		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT); 
		imageView.setLayoutParams(layoutParams);
		addView(imageView);
	}

	private void addProgressBar() {
		downloadImgProgressBar = (NumberProgressBar) inflater.inflate(R.layout.download_img_progress_bar, null,false);
		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT); 
		layoutParams.setMargins(20, 20, 20, 0);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		downloadImgProgressBar.setLayoutParams(layoutParams);	
		addView(downloadImgProgressBar);
	}
	
	public void hideProgressBar(){
		downloadImgProgressBar.setVisibility(View.GONE);
	}

	public ImageView getImageView() {
		return imageView;
	}
	
	public void setProgress(int progress){
		downloadImgProgressBar.setProgress(progress);
	}

	public void loadImage(String url){
		if(DiskCacheUtils.findInCache(url, ImageLoader.getInstance().getDiskCache()) != null){
			hideProgressBar();
		}
		ImageLoaderUtil.getInstance().displayImg(imageView, url,new ImageLoadingProgressListener() {			
			@Override
			public void onProgressUpdate(String imageUri, View view, int current,int total) {
				int progress = CommonUtil.getProgress(current, total);
				setProgress(progress);
				if(progress >= 100)
					hideProgressBar();
			}
		});
	}
	
}
