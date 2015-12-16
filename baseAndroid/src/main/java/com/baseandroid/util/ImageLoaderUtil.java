package com.baseandroid.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

public class ImageLoaderUtil {
	private static ImageLoaderUtil loaderUtil;
	private static ImageLoader imageLoader;
	private static DisplayImageOptions options;
	private static ImageLoadingListener imageLoadingListener;
	private ImageLoaderUtil() {
	}

	public static ImageLoaderUtil getInstance() {
		if (loaderUtil == null) {
			loaderUtil = new ImageLoaderUtil();
			imageLoader = ImageLoader.getInstance();
			options = new DisplayImageOptions.Builder()
//			.showStubImage(R.drawable.placeholder)		
//			.showImageForEmptyUri(R.drawable.ic_empty)	
//			.showImageOnFail(R.drawable.ic_error)	
			.bitmapConfig(Config.RGB_565)
			.cacheInMemory(true)	
			.cacheOnDisk(true)
			.build();
		}
		if(imageLoadingListener == null){
			imageLoadingListener = new ImageLoadingListener() {
				
				@Override
				public void onLoadingStarted(String arg0, View arg1) {
					
				}
				
				@Override
				public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
					if(arg2.getType() == FailReason.FailType.OUT_OF_MEMORY){
						imageLoader.clearMemoryCache();
					}
				}
				
				@Override
				public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
					
				}
				
				@Override
				public void onLoadingCancelled(String arg0, View arg1) {
					
				}
			}; 
		}
		return loaderUtil;
	}
	
	public void displayImg(ImageView img, String url){
		imageLoader.displayImage(url,img,options,imageLoadingListener);
	}

	
	public Bitmap loadImageToBitmapFromSDCard(String uri){
		return imageLoader.loadImageSync(uri);		
	}
	
	public void displayImg(ImageView img, String url,DisplayImageOptions options){
		imageLoader.displayImage(url,img,options,imageLoadingListener);
	}
	
	public void displayImg(ImageView img, String url,DisplayImageOptions options,ImageLoadingListener imageLoadingListener){
		imageLoader.displayImage(url,img,options,imageLoadingListener);
	}
	
	public void displayImg(ImageView img, String url,ImageLoadingListener imageLoadingListener){
		imageLoader.displayImage(url,img,imageLoadingListener);
	}
	
	public void displayImg(ImageView img, String url,ImageLoadingProgressListener imageLoadingProgressListener){
		imageLoader.displayImage(url, img, options, null, imageLoadingProgressListener);
	}
}
