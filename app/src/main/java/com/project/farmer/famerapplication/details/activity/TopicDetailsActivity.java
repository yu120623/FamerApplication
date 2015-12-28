package com.project.farmer.famerapplication.details.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.baseandroid.BaseActivity;
import com.baseandroid.util.CommonUtil;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.nineoldandroids.view.ViewHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v13.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v13.FragmentPagerItems;
import com.project.farmer.famerapplication.R;
import com.project.farmer.famerapplication.details.fragment.JieShaoFragment;
import com.project.farmer.famerapplication.details.fragment.PingJiaFragment;
import com.project.farmer.famerapplication.details.fragment.XuZhiFragment;
import com.project.farmer.famerapplication.entity.FarmSetModel;
import com.project.farmer.famerapplication.entity.FarmTopicModel;
import com.project.farmer.famerapplication.entity.TransferObject;
import com.project.farmer.famerapplication.http.API;
import com.project.farmer.famerapplication.http.AppHttpResListener;
import com.project.farmer.famerapplication.http.AppRequest;
import com.project.farmer.famerapplication.util.AppUtil;
import com.project.farmer.famerapplication.util.NetworkImageHolderView;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import github.chenupt.dragtoplayout.DragTopLayout;

public class TopicDetailsActivity extends BaseActivity {
    private DisplayImageOptions options;
    private DragTopLayout dragTopLayout;
    private ViewPager contentViewPager;
    private ConvenientBanner banner;
    private SmartTabLayout smartTabLayout;
    private FarmTopicModel farmTopicModel;
    private List<String> bannerUrls;
    private ImageView progress;
    private AnimationDrawable progressDrawable;
    private View contentView;
    private FarmSetModel farmSetModels;
    private View actionBar;
    private View actionBarBg;
    private ImageView backBtn;
    private ImageView shareBtn;
    private ImageView favouriteBtn;

    @Override
    protected void initViews() {
        findViews();
        initData();
        initClick();
    }

    private void initClick() {
        dragTopLayout.listener(new DragTopLayout.PanelListener() {
            @Override
            public void onPanelStateChanged(DragTopLayout.PanelState panelState) {
                if(panelState == DragTopLayout.PanelState.COLLAPSED){
                    setActionBarIcon(true);
                }else if(panelState == DragTopLayout.PanelState.EXPANDED){
                    setActionBarIcon(false);
                }
            }

            @Override
            public void onSliding(float ratio) {
                ViewHelper.setAlpha(actionBarBg,1-ratio);
            }

            @Override
            public void onRefresh() {

            }
        });
    }

    private void setActionBarIcon(boolean flag){
        backBtn.setSelected(flag);
        favouriteBtn.setSelected(flag);
        shareBtn.setSelected(flag);

    }

    private void initBanner() {
        final NetworkImageHolderView netWorkImageHolderView = new NetworkImageHolderView();
        netWorkImageHolderView.setImageOptions(options);
        banner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return netWorkImageHolderView;
            }
        }, bannerUrls);
        banner.startTurning(3000);
        int screenWith = CommonUtil.getScreenWith(getWindowManager());
        double scale = screenWith/ (640*1.0);
        banner.getLayoutParams().height = (int)(400*scale);
    }

    @Override
    public void initActonBar() {
        super.initActonBar();
        this.actionbarView.setVisibility(View.GONE);
    }

    private void initFragments() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("farmSet",farmSetModels);
        Bundle bundle2 = new Bundle();
        bundle2.putSerializable("farmTopic",farmTopicModel);
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getFragmentManager(), FragmentPagerItems.with(this)
                .add(R.string.jieshao, JieShaoFragment.class,bundle)
                .add(R.string.pingjia, PingJiaFragment.class,bundle2)
                .add(R.string.xuzhi, XuZhiFragment.class,bundle2)
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
                .showImageForEmptyUri(R.mipmap.default_banner_img)
                .showImageOnFail(R.mipmap.default_banner_img)
                .showImageOnLoading(R.mipmap.default_banner_img)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED).build();
        farmTopicModel = (FarmTopicModel) this.getIntent().getSerializableExtra("farmTopic");
        progressDrawable = (AnimationDrawable) progress.getDrawable();
        progressDrawable.start();
        dragTopLayout.setCollapseOffset((int)getResources().getDimension(android.R.dimen.app_icon_size));
        loadDataFromServer();
    }


    //获取专题详细
    private void loadDataFromServer() {
        String url = API.URL + API.API_URL.FARM_TOPIC_INFO;
        TransferObject data = AppUtil.getHttpData(context);
        data.setFarmTopicAliasId(farmTopicModel.getFarmTopicAliasId());
        data.setPageNumber(0);
        AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                farmSetModels = data.getFarmSetModel();
                setUrlBanners(farmSetModels);
                initBanner();
                initFragments();
                progressDrawable.stop();
                progress.setVisibility(View.GONE);
                contentView.setVisibility(View.VISIBLE);
                actionBar.setVisibility(View.VISIBLE);
            }
        },data);
        request.execute();
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
        contentViewPager = (ViewPager) this.findViewById(R.id.details_content_view_pager);
        dragTopLayout = (DragTopLayout) this.findViewById(R.id.details_drag_layout);
        banner = (ConvenientBanner) this.findViewById(R.id.details_convenient_banner);
        smartTabLayout= (SmartTabLayout) this.findViewById(R.id.viewpagertab);
        progress = (ImageView) this.findViewById(R.id.progress);
        contentView = this.findViewById(R.id.topic_content);
        actionBar = this.findViewById(R.id.top_info_acion_bar);
        actionBarBg = this.findViewById(R.id.top_info_acion_bar_bg);
        backBtn = (ImageView) this.findViewById(R.id.topic_back_btn);
        shareBtn = (ImageView) this.findViewById(R.id.share_btn);
        favouriteBtn = (ImageView) this.findViewById(R.id.favourite_btn);
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

    public void setUrlBanners(FarmSetModel farmSetModel) {
        bannerUrls = new ArrayList<String>();
        for(int i = 0;i < farmSetModel.getBaResources().size();i++){
            bannerUrls.add(farmSetModel.getBaResources().get(i).getResourceLocation());
        }
    }
}
