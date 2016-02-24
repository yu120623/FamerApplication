/*
 * Copyright (C) 2014 pengjianbo(pengjianbosoft@gmail.com), Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.egceo.app.myfarm.util;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.egceo.app.myfarm.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;


/**
 * Desction:
 * Author:pengjianbo
 * Date:15/10/10 下午5:52
 */
public class GalleryImageLoader implements galleryfinal.ImageLoader {

    @Override
    public void displayImage(final ImageView imageView, String url) {
        ImageSize imageSize = new ImageSize(200,200);
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(false)
                .showImageOnLoading(R.mipmap.ic_gf_default_photo)
                .showImageForEmptyUri(R.mipmap.ic_gf_default_photo)
                .showImageOnFail(R.mipmap.ic_gf_default_photo)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
//        ImageLoader.getInstance().loadImage(url, imageSize, options, new SimpleImageLoadingListener() {
//			@Override
//			public void onLoadingComplete(String imageUri, View view,
//					Bitmap loadedImage) {
//				imageView.setImageBitmap(loadedImage);
//			}
//		});
        ImageLoader.getInstance().displayImage(url, new ImageViewAware(imageView), options,imageSize, null, null);
        //ImageLoader.getInstance().displayImage(url, new ImageViewAware(imageView), options, null, null);
    }
}
