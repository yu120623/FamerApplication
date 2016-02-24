package com.egceo.app.myfarm.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.baseandroid.util.ImageLoaderUtil;
import com.bigkoo.convenientbanner.holder.Holder;
import com.egceo.app.myfarm.entity.Resource;
import com.html.HtmlActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.egceo.app.myfarm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sai on 15/8/4.
 * 网络图片加载例子
 */
public class NetworkImageHolderView implements Holder<String> {
    private List<ImageView> imageViews = new ArrayList<>();
    private ImageView imageView;
    private DisplayImageOptions options;
    private Activity activity;
    private List<Resource> resources;
    private String imgSize;
    public NetworkImageHolderView(){

    }

    public NetworkImageHolderView(Activity activity, List<Resource> resources,String imgSize){
        this.activity = activity;
        this.resources = resources;
        this.imgSize = imgSize;
    }

    public NetworkImageHolderView(String imgSize){
        this.imgSize = imgSize;
    }

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
        imageViews.add(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getTag() == null)return;
                Intent intent = new Intent(activity, HtmlActivity.class);
                intent.putExtra("url",(String)view.getTag());
                activity.startActivity(intent);
            }
        });
        return imageView;
    }

    public void setImageOptions(DisplayImageOptions options){
        this.options = options;
    }

    @Override
    public void UpdateUI(Context context,int position, String data) {
        if(resources != null) {
            String url = resources.get(position).getResourceProperty();
            url = url.substring(url.indexOf(";") + 1, url.length());
            imageView.setTag(url);
        }
        ImageLoaderUtil.getInstance().displayImg(imageView,data+imgSize,options);
    }

    public List<ImageView> getImageViews() {
        return imageViews;
    }
}
