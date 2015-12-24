package com.project.farmer.famerapplication.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.baseandroid.util.ImageLoaderUtil;
import com.bigkoo.convenientbanner.holder.Holder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.project.farmer.famerapplication.R;

/**
 * Created by Sai on 15/8/4.
 * 网络图片加载例子
 */
public class NetworkImageHolderView implements Holder<String> {
    private ImageView imageView;
    private DisplayImageOptions options;
    @Override
    public View createView(Context context) {
        //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
        options = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageForEmptyUri(R.mipmap.default_banner_img)
                .showImageOnFail(R.mipmap.default_banner_img)
                .showImageOnLoading(R.mipmap.default_banner_img)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED).build();
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    public void setImageOptions(DisplayImageOptions options){
        this.options = options;
    }

    @Override
    public void UpdateUI(Context context,int position, String data) {
        ImageLoaderUtil.getInstance().displayImg(imageView,data,options);
    }
}
