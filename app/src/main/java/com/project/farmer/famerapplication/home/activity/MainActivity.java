package com.project.farmer.famerapplication.home.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.project.farmer.famerapplication.home.fragment.JingXuanFragment;
import com.project.farmer.famerapplication.home.fragment.QiangGouFragment;
import com.project.farmer.famerapplication.home.fragment.TuiJianFragment;
import com.project.farmer.famerapplication.home.fragment.ZhouBianFragment;
import com.project.farmer.famerapplication.search.activity.SearchActivity;
import com.project.farmer.famerapplication.util.NetworkImageHolderView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.greenrobot.event.EventBus;
import github.chenupt.dragtoplayout.DragTopLayout;

public class MainActivity extends BaseActivity {
    private DisplayImageOptions options;
    private TextView area;
    private RelativeLayout jingxuan;
    private RelativeLayout qianggou;
    private RelativeLayout zhoubian;
    private RelativeLayout tuijian;
    private LinearLayout arrContent;
    private RelativeLayout postionLayout;
    private DragTopLayout dragTopLayout;
    private ViewPager contentViewPager;
    private ConvenientBanner banner;
    private View searchBtn;
    private List<RelativeLayout> buttons;
    private String[] url = {"http://oss.mycff.com/images/000014.png","http://oss.mycff.com/images/000015.png"};
    @Override
    protected void initViews() {
        findViews();
        initData();
        initFragments();
        initBanner();
        initClick();
    }

    private void initBanner() {
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
    }

    private void initClick() {
        jingxuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentViewPager.setCurrentItem(0);
                setArrVisible(0);
            }
        });
        qianggou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentViewPager.setCurrentItem(1);
                setArrVisible(1);
            }
        });
        zhoubian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentViewPager.setCurrentItem(2);
                setArrVisible(2);
            }
        });
        tuijian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentViewPager.setCurrentItem(3);
                setArrVisible(3);
            }
        });
        contentViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                EventBus.getDefault().post(position);
                setArrVisible(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initActonBar() {
        actionbarView = (RelativeLayout) this.findViewById(R.id.action_bar_view);
        inflater.inflate(R.layout.home_action_bar,actionbarView,true);
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

    private void setArrVisible(int index){
        arrContent.getChildAt(index).setVisibility(View.VISIBLE);
        buttons.get(index).setSelected(true);
        for(int i = 0;i < arrContent.getChildCount();i++){
            if(index == i) {
                continue;
            }
            buttons.get(i).setSelected(false);
            arrContent.getChildAt(i).setVisibility(View.INVISIBLE);
        }
    }

    private void initData() {
        options = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED).build();
        jingxuan.post(new Runnable() {
            @Override
            public void run() {
                jingxuan.getLayoutParams().height = jingxuan.getWidth();
                qianggou.getLayoutParams().height = jingxuan.getWidth();
                zhoubian.getLayoutParams().height = jingxuan.getWidth();
                tuijian.getLayoutParams().height = jingxuan.getWidth();
                postionLayout.getLayoutParams().height = jingxuan.getWidth()/2;
                dragTopLayout.setCollapseOffset(jingxuan.getWidth()+arrContent.getHeight()+10);
                jingxuan.setVisibility(View.VISIBLE);
                qianggou.setVisibility(View.VISIBLE);
                zhoubian.setVisibility(View.VISIBLE);
                tuijian.setVisibility(View.VISIBLE);

            }
        });
        buttons = new ArrayList<>();
        buttons.add(jingxuan);
        buttons.add(qianggou);
        buttons.add(zhoubian);
        buttons.add(tuijian);
        area.setText(CommonUtil.toDBC("苏州"));
        setArrVisible(0);
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
        jingxuan = (RelativeLayout) this.findViewById(R.id.jingxuan);
        qianggou = (RelativeLayout) this.findViewById(R.id.qianggou);
        zhoubian = (RelativeLayout) this.findViewById(R.id.zhoubian);
        tuijian = (RelativeLayout) this.findViewById(R.id.tuijian);
        postionLayout = (RelativeLayout) this.findViewById(R.id.postion_container);
        contentViewPager = (ViewPager) this.findViewById(R.id.content_view_pager);
        dragTopLayout = (DragTopLayout) this.findViewById(R.id.drag_layout);
        arrContent = (LinearLayout) this.findViewById(R.id.arr_content);
        banner = (ConvenientBanner) this.findViewById(R.id.convenient_banner);
        searchBtn = this.findViewById(R.id.search_btn);
        area = (TextView) this.findViewById(R.id.area);
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
