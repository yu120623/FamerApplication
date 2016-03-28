package com.egceo.app.myfarm.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.baseandroid.util.ImageLoaderUtil;
import com.bigkoo.convenientbanner.holder.Holder;
import com.egceo.app.myfarm.entity.FarmModel;
import com.egceo.app.myfarm.entity.FarmTopic;
import com.egceo.app.myfarm.entity.FarmTopicModel;
import com.egceo.app.myfarm.entity.Resource;
import com.egceo.app.myfarm.farm.activity.FarmDetailActivity;
import com.egceo.app.myfarm.home.activity.LoginActivity;
import com.egceo.app.myfarm.home.activity.NewRedPackageActivity;
import com.egceo.app.myfarm.home.activity.RedPackageActivity;
import com.egceo.app.myfarm.topic.activity.TimingTopicDetailsActivity;
import com.egceo.app.myfarm.topic.activity.TopicDetailsActivity;
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
    public View createView(final Context context) {
        //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
        if(options == null) {
            options = new DisplayImageOptions.Builder()
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .showImageForEmptyUri(R.mipmap.default_banner_img)
                    .showImageOnFail(R.mipmap.default_banner_img)
                    .showImageOnLoading(R.mipmap.default_banner_img)
                    .imageScaleType(ImageScaleType.EXACTLY_STRETCHED).build();
        }
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getTag() == null) return;
                String url = (String) view.getTag();
                if (url.startsWith("http")) {
                    Intent intent = new Intent(activity, HtmlActivity.class);
                    intent.putExtra("url", (String) view.getTag());
                    activity.startActivity(intent);
                } else if (url.startsWith("farm")) {
                    Intent intent = new Intent(activity, FarmDetailActivity.class);
                    FarmModel farmModel = new FarmModel();
                    farmModel.setFarmAliasId(url.substring(url.indexOf(":") + 1));
                    intent.putExtra("farmModel", farmModel);
                    activity.startActivity(intent);
                } else if (url.startsWith("topic")) {
                    Intent intent = new Intent(activity, TopicDetailsActivity.class);
                    FarmTopicModel farmTopic = new FarmTopicModel();
                    farmTopic.setFarmTopicAliasId(url.substring(url.indexOf(":") + 1));
                    intent.putExtra("farmTopic", farmTopic);
                    activity.startActivity(intent);
                } else if (url.startsWith("buying")) {
                    Intent intent = new Intent(activity, TimingTopicDetailsActivity.class);
                    FarmTopicModel farmTopic = new FarmTopicModel();
                    farmTopic.setFarmTopicAliasId(url.substring(url.indexOf(":") + 1));
                    intent.putExtra("farmTopic", farmTopic);
                    activity.startActivity(intent);
                } else if (url.startsWith("shake")) {
                    if (!AppUtil.checkIsLogin(activity)) {
                        Intent intent = new Intent(activity, LoginActivity.class);
                        activity.startActivity(intent);
                    } else {
                        Intent intent = new Intent(activity, NewRedPackageActivity.class);
                        intent.putExtra("id",url.substring(url.indexOf(":") + 1));
                        activity.startActivity(intent);
                    }
                }
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

    /*public List<ImageView> getImageViews() {
        return imageViews;
    }*/
}
