package com.egceo.app.myfarm.home.activity;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baseandroid.BaseActivity;
import com.baseandroid.util.CommonUtil;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.egceo.app.myfarm.entity.Code;
import com.egceo.app.myfarm.entity.FarmTopicModel;
import com.egceo.app.myfarm.entity.Resource;
import com.egceo.app.myfarm.entity.TransferObject;
import com.egceo.app.myfarm.http.API;
import com.egceo.app.myfarm.http.AppHttpResListener;
import com.egceo.app.myfarm.http.AppRequest;
import com.html.HtmlActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.ogaclejapan.smarttablayout.utils.v13.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v13.FragmentPagerItems;
import com.egceo.app.myfarm.R;
import com.egceo.app.myfarm.city.activity.CityActivity;
import com.egceo.app.myfarm.home.fragment.JingXuanFragment;
import com.egceo.app.myfarm.home.fragment.QiangGouFragment;
import com.egceo.app.myfarm.home.fragment.TuiJianFragment;
import com.egceo.app.myfarm.home.fragment.ZhouBianFragment;
import com.egceo.app.myfarm.search.activity.SearchActivity;
import com.egceo.app.myfarm.user.activity.UserActivity;
import com.egceo.app.myfarm.util.AppUtil;
import com.egceo.app.myfarm.util.NetworkImageHolderView;

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
    private View loginBtn;
    private List<RelativeLayout> buttons;
    private View userBtn;
    private List<Resource> resources;
    private FragmentPagerItemAdapter adapter;
    @Override
    protected void initViews() {
        findViews();
        initData();
        initFragments();
        initClick();
        initBannerSize();
        loadDataFromServer();
        checkLocationChange();
    }

    private void checkLocationChange() {
        String code = sp.getString(AppUtil.SP_CITY_CODE,AppUtil.DEFAULT_CITY_CODE);
        final String newCode = sp.getString(AppUtil.SP_NEW_CITY_CODE,"");
        final String newCity = sp.getString(AppUtil.SP_NEW_CITY_NAME,"");
        if(!"".equals(newCode) && !code.equals(newCode)){
            new AlertDialog.Builder(activity)
            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            })
            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Code code = new Code();
                    code.setCodeName(newCode);
                    code.setCodeDesc(newCity);
                    changeCity(code);
                    dialogInterface.dismiss();
                }
            })
            .setMessage("您现在的位置是"+newCity+"是否切换?")
            .setTitle("提示").show();
        }
    }

    private void initBannerSize() {
        banner.startTurning(3000);
        int screenWith = CommonUtil.getScreenWith(getWindowManager());
        double scale = screenWith / (444 * 1.0);
        banner.getLayoutParams().height = (int) (200 * scale);
    }

    private void initBanner(List<String> url) {
        banner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView(activity,resources,AppUtil.HOME_BANNER_IMG_SIZE);
            }
        }, url);
    }

    private void loadDataFromServer() {
        String url = API.URL + API.API_URL.FARM_TOPIC_LIST;
        TransferObject data = AppUtil.getHttpData(context);
        data.setCityCode(sp.getString(AppUtil.SP_CITY_CODE,""));
        AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                resources = data.getResources();
                List<String> urls = new ArrayList<>();
                for(Resource resource : resources){
                    urls.add(resource.getResourceLocation());
                }
                initBanner(urls);
            }
            @Override
            public void onEnd() {
            }
        },data);
        request.execute();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        loginBtn.setVisibility(View.GONE);
        userBtn.setVisibility(View.VISIBLE);
        userBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, UserActivity.class);
                startActivity(intent1);
            }
        });
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
        contentViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CityActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void initActonBar() {
        actionbarView = (RelativeLayout) this.findViewById(R.id.action_bar_view);
        inflater.inflate(R.layout.home_action_bar, actionbarView, true);
    }

    private void initFragments() {
        adapter = new FragmentPagerItemAdapter(
                getFragmentManager(), FragmentPagerItems.with(this)
                .add("", JingXuanFragment.class)
                .add("", QiangGouFragment.class)
                .add("", ZhouBianFragment.class)
                .add("", TuiJianFragment.class)
                .create());
        contentViewPager.setAdapter(adapter);
    }

    public void onEvent(Boolean flag) {
        dragTopLayout.setTouchMode(flag);
    }

    private void setArrVisible(int index) {
        arrContent.getChildAt(index).setVisibility(View.VISIBLE);
        buttons.get(index).setSelected(true);
        for (int i = 0; i < arrContent.getChildCount(); i++) {
            if (index == i) {
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
                postionLayout.getLayoutParams().height = jingxuan.getWidth() / 2;
                dragTopLayout.setCollapseOffset(jingxuan.getWidth() + arrContent.getHeight() + 10);
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
        String city = CommonUtil.toDBC(sp.getString(AppUtil.SP_CITY_NAME, AppUtil.DEFAULT_CITY_NAME));
        if(!"".equals(city))
            area.setText(city.substring(0,2));
        setArrVisible(0);
        dragTopLayout.setOverDrag(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        if (!AppUtil.checkIsLogin(context)) {
            loginBtn.setVisibility(View.VISIBLE);
            userBtn.setVisibility(View.GONE);
            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            loginBtn.setVisibility(View.GONE);
            userBtn.setVisibility(View.VISIBLE);
            userBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(MainActivity.this, UserActivity.class);
                    startActivity(intent1);
                }
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    private void changeCity(Code code){
        sp.edit().putString(AppUtil.SP_CITY_CODE,code.getCodeName()).commit();
        sp.edit().putString(AppUtil.SP_CITY_NAME,code.getCodeDesc()).commit();
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
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
        loginBtn = this.findViewById(R.id.user_login);
        userBtn = this.findViewById(R.id.user_btn);
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

}
