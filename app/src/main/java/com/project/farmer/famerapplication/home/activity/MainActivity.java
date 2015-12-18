package com.project.farmer.famerapplication.home.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.baseandroid.BaseActivity;
import com.baseandroid.util.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.ogaclejapan.smarttablayout.utils.v13.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v13.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v13.FragmentPagerItems;
import com.project.farmer.famerapplication.R;
import com.project.farmer.famerapplication.home.fragment.JingXuanFragment;
import com.project.farmer.famerapplication.home.fragment.QiangGouFragment;
import com.project.farmer.famerapplication.home.fragment.TuiJianFragment;
import com.project.farmer.famerapplication.home.fragment.ZhouBianFragment;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
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
    private ViewPager contentViewPager;
    @Override
    protected void initViews() {
        findViews();
        initData();
        initFragments();
    }

    private void initFragments() {
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getFragmentManager(), FragmentPagerItems.with(this)
                .add("", JingXuanFragment.class)
                .add("", QiangGouFragment.class)
                .add("", ZhouBianFragment.class)
                .add("", TuiJianFragment.class)
                .create());
        contentViewPager.setAdapter(adapter);
    }

    public void onEvent(Boolean flag){
        dragTopLayout.setTouchMode(flag);
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
                dragTopLayout.setCollapseOffset(jingxuan.getWidth()+10);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    private void findViews() {
        imageView = (ImageView) this.findViewById(R.id.image_view);
        jingxuan = (RelativeLayout) this.findViewById(R.id.jingxuan);
        qianggou = (RelativeLayout) this.findViewById(R.id.qianggou);
        zhoubian = (RelativeLayout) this.findViewById(R.id.zhoubian);
        tuijian = (RelativeLayout) this.findViewById(R.id.tuijian);
        postionLayout = (RelativeLayout) this.findViewById(R.id.postion_container);
        contentViewPager = (ViewPager) this.findViewById(R.id.content_view_pager);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
