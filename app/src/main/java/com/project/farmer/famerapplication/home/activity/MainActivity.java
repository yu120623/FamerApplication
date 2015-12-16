package com.project.farmer.famerapplication.home.activity;

import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baseandroid.BaseActivity;
import com.baseandroid.postionviewpager.PostionViewPager;
import com.baseandroid.util.CommonUtil;
import com.baseandroid.util.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.project.farmer.famerapplication.R;

import github.chenupt.dragtoplayout.DragTopLayout;

public class MainActivity extends BaseActivity {
    private DisplayImageOptions options;
    private ImageView imageView;
    private String url = "http://img1.imgtn.bdimg.com/it/u=3654508348,624460089&fm=21&gp=0.jpg";
    private RelativeLayout jingxuan;
    private RelativeLayout qianggou;
    private RelativeLayout zhoubian;
    private RelativeLayout tuijian;
    private RelativeLayout postionLayout;
    private DragTopLayout dragTopLayout;
    @Override
    protected void initViews() {
        findViews();
        initData();
    }

    private void initData() {
        options = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED).build();
        ImageLoaderUtil.getInstance().displayImg(imageView,url,options);
        jingxuan.post(new Runnable() {
            @Override
            public void run() {
                jingxuan.getLayoutParams().height = jingxuan.getWidth();
                qianggou.getLayoutParams().height = jingxuan.getWidth();
                zhoubian.getLayoutParams().height = jingxuan.getWidth();
                tuijian.getLayoutParams().height = jingxuan.getWidth();
                postionLayout.getLayoutParams().height = jingxuan.getWidth()/2;
                dragTopLayout.setCollapseOffset(jingxuan.getWidth());
            }
        });
    }

    private void findViews() {
        imageView = (ImageView) this.findViewById(R.id.image_view);
        jingxuan = (RelativeLayout) this.findViewById(R.id.jingxuan);
        qianggou = (RelativeLayout) this.findViewById(R.id.qianggou);
        zhoubian = (RelativeLayout) this.findViewById(R.id.zhoubian);
        tuijian = (RelativeLayout) this.findViewById(R.id.tuijian);
        postionLayout = (RelativeLayout) this.findViewById(R.id.postion_container);
        dragTopLayout = (DragTopLayout) this.findViewById(R.id.drag_layout);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected String setActionBarTitle() {
        return "";
    }
}
