package com.project.farmer.famerapplication.details.activity;

import android.graphics.Bitmap;
import android.support.v4.view.ViewPager;
import android.widget.RelativeLayout;

import com.baseandroid.BaseActivity;
import com.baseandroid.util.CommonUtil;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v13.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v13.FragmentPagerItems;
import com.project.farmer.famerapplication.R;
import com.project.farmer.famerapplication.details.fragment.PingJiaFragment;
import com.project.farmer.famerapplication.details.fragment.XuZhiFragment;
import com.project.farmer.famerapplication.home.fragment.JingXuanFragment;
import com.project.farmer.famerapplication.util.NetworkImageHolderView;

import java.util.Arrays;

import de.greenrobot.event.EventBus;
import github.chenupt.dragtoplayout.DragTopLayout;

/**
 * Created by heoa on 2015/12/20.
 */
public class TopicDetailsActivity extends BaseActivity {
    private DisplayImageOptions options;
    private RelativeLayout jieshao;
    private RelativeLayout pingjia;
    private RelativeLayout xuzhi;
    private RelativeLayout postionLayout;
    private DragTopLayout dragTopLayout;
    private ViewPager contentViewPager;
    private ConvenientBanner banner;
    private SmartTabLayout smartTabLayout;
    private String[] url = {"http://oss.mycff.com/images/000014.png","http://oss.mycff.com/images/000015.png"};
    @Override
    protected void initViews() {
        findViews();
        initData();
        initFragments();
        initBanner();
    }

    private void initBanner() {
        //banner.getLayoutParams().height = CommonUtil.Px2Dp(context,1200);
        banner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, Arrays.asList(url));
        banner.startTurning(3000);
        int screenWith = CommonUtil.getScreenWith(getWindowManager());
        double scale = screenWith/ (444*1.0);
        banner.getLayoutParams().height = (int)(200*scale);
        //banner.getViewPager().setPageTransformer(true,new DepthPageTransformer());
    }

    @Override
    public void initActonBar() {
        super.initActonBar();
        actionbarView = (RelativeLayout) this.findViewById(R.id.action_bar_view);
        actionbarView.addView(inflater.inflate(R.layout.home_action_bar,null));
    }

    private void initFragments() {
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getFragmentManager(), FragmentPagerItems.with(this)
                .add("介绍", JingXuanFragment.class)
                .add("评价", PingJiaFragment.class)
                .add("须知", XuZhiFragment.class)
                .create());
        contentViewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(contentViewPager);
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
//        jieshao = (RelativeLayout) this.findViewById(R.id.details_jieshao);
//        pingjia = (RelativeLayout) this.findViewById(R.id.details_pingjia);
//        xuzhi = (RelativeLayout) this.findViewById(R.id.details_xuzhi);
//        postionLayout = (RelativeLayout) this.findViewById(R.id.details_postion_container);
        contentViewPager = (ViewPager) this.findViewById(R.id.details_content_view_pager);
        dragTopLayout = (DragTopLayout) this.findViewById(R.id.details_drag_layout);
        banner = (ConvenientBanner) this.findViewById(R.id.details_convenient_banner);
        smartTabLayout= (SmartTabLayout) this.findViewById(R.id.viewpagertab);
    }

    @Override
    protected int getContentView() {
        return R.layout.topic_details;
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
