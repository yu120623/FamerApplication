package com.egceo.app.myfarm.farm.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baseandroid.BaseActivity;
import com.baseandroid.util.CommonUtil;
import com.baseandroid.view.HackyViewPager;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.egceo.app.myfarm.entity.FarmQuickPayModel;
import com.egceo.app.myfarm.home.activity.LoginActivity;
import com.egceo.app.myfarm.listener.OnFavouriteClick;
import com.egceo.app.myfarm.util.AppUtil;
import com.egceo.app.myfarm.view.FavouriteBtn;
import com.nineoldandroids.view.ViewHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v13.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v13.FragmentPagerItems;
import com.egceo.app.myfarm.R;
import com.egceo.app.myfarm.entity.FarmModel;
import com.egceo.app.myfarm.entity.TransferObject;
import com.egceo.app.myfarm.farm.fragment.FarmCommentFragment;
import com.egceo.app.myfarm.farm.fragment.FarmDescFragment;
import com.egceo.app.myfarm.farm.fragment.FarmMapFragment;
import com.egceo.app.myfarm.farmset.activity.FarmSetActivity;
import com.egceo.app.myfarm.http.API;
import com.egceo.app.myfarm.http.AppHttpResListener;
import com.egceo.app.myfarm.http.AppRequest;
import com.egceo.app.myfarm.util.NetworkImageHolderView;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import de.greenrobot.event.EventBus;
import github.chenupt.dragtoplayout.DragTopLayout;

public class FarmDetailActivity extends BaseActivity {
    private DisplayImageOptions options;
    private DragTopLayout dragTopLayout;
    private HackyViewPager contentViewPager;
    private ConvenientBanner banner;
    private SmartTabLayout smartTabLayout;
    private List<String> bannerUrls;
    private ImageView progress;
    private AnimationDrawable progressDrawable;
    private View contentView;
    private View actionBar;
    private View actionBarBg;
    private Button viewFarmSet;
    private ImageView backBtn;
    private ImageView shareBtn;
    private FavouriteBtn favouriteBtn;
    private FarmModel farmModel;
    private TextView title;
    private Button fastPay;
    private View view1;
    private View quickPayView;
    private TextView quickPayText;
    private LinearLayout tagsContainer;
    private String shareImgUrl;
    @Override
    protected void initViews() {
        showProgress();
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
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
        viewFarmSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FarmSetActivity.class);
                intent.putExtra("title",farmModel.getFarmName());
                intent.putExtra("farmAliasId",farmModel.getFarmAliasId());
                startActivity(intent);
            }
        });
        contentViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                if(position == 2){
                    contentViewPager.setLocked(true);
                    dragTopLayout.closeTopView(true);
                }else{
                    contentViewPager.setLocked(false);
                }
                EventBus.getDefault().post(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        favouriteBtn.setTag(R.id.favourite_id,farmModel.getFarmAliasId());
        favouriteBtn.setTag(R.id.favourite_type,OnFavouriteClick.FARM);
        fastPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!AppUtil.checkIsLogin(context)){
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                Intent intent = new Intent(activity,QuickPayActivity.class);
                intent.putExtra("farmModel",farmModel);
                startActivity(intent);
            }
        });
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnekeyShare oks = new OnekeyShare();
                oks.disableSSOWhenAuthorize();
                oks.setTitle(farmModel.getFarmName());
                oks.setImageUrl(shareImgUrl);
                oks.setTitleUrl("http://w.mycff.com/Wechat/Farm/content/id/"+farmModel.getFarmAliasId()+"/abc/1");
                oks.setText(getString(R.string.share_content));
                oks.setUrl("http://w.mycff.com/Wechat/Farm/content/id/"+farmModel.getFarmAliasId()+"/abc/1");
                oks.setComment(getString(R.string.share_content));
                oks.setSite(getString(R.string.app_name));
                oks.setSiteUrl("http://w.mycff.com/Wechat/Farm/content/id/"+farmModel.getFarmAliasId()+"/abc/1");
                oks.show(activity);
            }
        });
    }

    private void setActionBarIcon(boolean flag){
        backBtn.setSelected(flag);
        favouriteBtn.setSelected(flag);
        shareBtn.setSelected(flag);
        if(flag){
            title.setVisibility(View.VISIBLE);
        }else{
            title.setVisibility(View.GONE);
        }
    }

    private void initBanner() {
        final NetworkImageHolderView netWorkImageHolderView = new NetworkImageHolderView(AppUtil.INNER_BANNER_IMG_SIZE);
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
        banner.setPageIndicator(new int[]{R.mipmap.banner_i,R.mipmap.banner_i_s});
    }

    @Override
    public void initActonBar() {
        super.initActonBar();
        this.actionbarView.setVisibility(View.GONE);

    }

    private void initFragments() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("farmModel",farmModel);
        bundle.putBoolean("farmSetExist",view1.getVisibility() == View.VISIBLE);
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getFragmentManager(), FragmentPagerItems.with(this)
                .add(R.string.jieshao, FarmDescFragment.class,bundle)
                .add(R.string.pingjia, FarmCommentFragment.class,bundle)
                .add(R.string.map, FarmMapFragment.class,bundle)
                .create());
        contentViewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(contentViewPager);
    }

    public void onEvent(Boolean flag){
        dragTopLayout.setTouchMode(flag);
    }


    private void initData() {
        ShareSDK.initSDK(this);
        farmModel = (FarmModel) this.getIntent().getSerializableExtra("farmModel");
        shareImgUrl = farmModel.getResourcePath() + AppUtil.FARM_FACE;
        options = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageForEmptyUri(R.mipmap.banner640)
                .showImageOnFail(R.mipmap.banner640)
                .showImageOnLoading(R.mipmap.banner640)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED).build();
        progressDrawable = (AnimationDrawable) progress.getDrawable();
        progressDrawable.start();
        dragTopLayout.setCollapseOffset((int)getResources().getDimension(android.R.dimen.app_icon_size));
        title.setText(farmModel.getFarmName());
        dragTopLayout.setOverDrag(false);
        loadDataFromServer();
    }

    //获取专题详细
    private void loadDataFromServer() {
        String url = API.URL + API.API_URL.FARM_INFO;
        TransferObject data = AppUtil.getHttpData(context);
        data.setFarmAliasId(farmModel.getFarmAliasId());
        AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                if(data.getFarmSetIsExistence().equals("0")){
                    view1.setVisibility(View.GONE);
                }
                farmModel = data.getFarmModel();
                if(!farmModel.getQuickPay().equalsIgnoreCase("Y")){
                    quickPayView.setVisibility(View.GONE);
                }
                quickPayText.setText(farmModel.getFarmIntruDesc());
                farmModel.setFarmAliasId(data.getFarmAliasId());
                if(farmModel.getCollectStatus().equals("1")){
                    favouriteBtn.setChecked(true);
                }
                favouriteBtn.setOnCheckedChangeListener(new OnFavouriteClick(activity));
                setUrlBanners(farmModel);
                initBanner();
                initTags();
                initFragments();
                progressDrawable.stop();
                progress.setVisibility(View.GONE);
                contentView.setVisibility(View.VISIBLE);
                actionBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onEnd() {
                super.onEnd();
                hideProgress();
            }
        },data);
        request.execute();
    }

    private void initTags() {
        if (null == farmModel.getFarmTags() || farmModel.getFarmTags().size() <= 0) return;
        for (int i = 0; i < farmModel.getFarmTags().size(); i++) {
            inflater.inflate(R.layout.text, tagsContainer, true);
            final TextView tag = (TextView) tagsContainer.getChildAt(tagsContainer.getChildCount() - 1);
            tag.setText(farmModel.getFarmTags().get(i));
        }
    }

    public void setUrlBanners(FarmModel farmModel) {
        bannerUrls = new ArrayList<String>();
        for(int i = 0;i < farmModel.getBaResources().size();i++){
            bannerUrls.add(farmModel.getBaResources().get(i).getResourceLocation());
        }
    }

    private void findViews() {
        contentViewPager = (HackyViewPager) this.findViewById(R.id.details_content_view_pager);
        dragTopLayout = (DragTopLayout) this.findViewById(R.id.details_drag_layout);
        banner = (ConvenientBanner) this.findViewById(R.id.details_convenient_banner);
        smartTabLayout= (SmartTabLayout) this.findViewById(R.id.viewpagertab);
        progress = (ImageView) this.findViewById(R.id.progress);
        contentView = this.findViewById(R.id.topic_content);
        actionBar = this.findViewById(R.id.top_info_acion_bar);
        actionBarBg = this.findViewById(R.id.top_info_acion_bar_bg);
        backBtn = (ImageView) this.findViewById(R.id.topic_back_btn);
        shareBtn = (ImageView) this.findViewById(R.id.share_btn);
        favouriteBtn = (FavouriteBtn) this.findViewById(R.id.favourite_btn);
        viewFarmSet = (Button) this.findViewById(R.id.view_farm_set);
        fastPay = (Button) this.findViewById(R.id.fast_pay);
        title = (TextView) this.findViewById(R.id.title);
        view1 = this.findViewById(R.id.view1);
        quickPayView = this.findViewById(R.id.quick_pay_layout);
        tagsContainer = (LinearLayout) this.findViewById(R.id.tags_container);
        quickPayText = (TextView) this.findViewById(R.id.quick_pay_text);
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

    @Override
    protected int getContentView() {
        return R.layout.activity_farm_detail;
    }

    @Override
    protected String setActionBarTitle() {
        return "";
    }
}
