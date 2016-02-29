package com.egceo.app.myfarm.home.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baseandroid.BaseActivity;
import com.baseandroid.util.CommonUtil;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.egceo.app.myfarm.R;
import com.egceo.app.myfarm.city.activity.CityActivity;
import com.egceo.app.myfarm.db.CodeDao;
import com.egceo.app.myfarm.db.DBHelper;
import com.egceo.app.myfarm.entity.Code;
import com.egceo.app.myfarm.entity.Resource;
import com.egceo.app.myfarm.entity.TransferObject;
import com.egceo.app.myfarm.home.fragment.JingXuanFragment;
import com.egceo.app.myfarm.home.fragment.QiangGouFragment;
import com.egceo.app.myfarm.home.fragment.TuiJianFragment;
import com.egceo.app.myfarm.home.fragment.ZhouBianFragment;
import com.egceo.app.myfarm.http.API;
import com.egceo.app.myfarm.http.AppHttpResListener;
import com.egceo.app.myfarm.http.AppRequest;
import com.egceo.app.myfarm.search.activity.SearchActivity;
import com.egceo.app.myfarm.user.activity.UserActivity;
import com.egceo.app.myfarm.util.AppUtil;
import com.egceo.app.myfarm.util.NetworkImageHolderView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.ogaclejapan.smarttablayout.utils.v13.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v13.FragmentPagerItems;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import github.chenupt.dragtoplayout.DragTopLayout;

public class MainActivityNew extends BaseActivity {
    private DisplayImageOptions options;
    private TextView area;
    private DragTopLayout dragTopLayout;
    private ViewPager contentViewPager;
    private ConvenientBanner banner;
    private View searchBtn;
    private View loginBtn;
    private View userBtn;
    private List<Resource> resources;
    private FragmentPagerItemAdapter adapter;
    private RadioGroup radioButtonGroup;
    private CodeDao codeDao;
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
        final Code sysCode = codeDao.load(2l);
        Code code = codeDao.load(1l);
        if(sysCode != null && code != null && code.getCodetype().equals(AppUtil.CODE_TYPE_AUTO)){
            if(!code.getCodeDesc().equals(sysCode.getCodeDesc())){
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
                                code.setCodeName(sysCode.getCodeName());
                                code.setCodeDesc(sysCode.getCodeDesc());
                                changeCity(code);
                                dialogInterface.dismiss();
                            }
                        })
                        .setMessage("您现在的位置是"+sysCode.getCodeDesc()+"是否切换?")
                        .setTitle("提示").show();
            }
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
        banner.setPageIndicator(new int[]{R.mipmap.banner_i,R.mipmap.banner_i_s});
    }

    private void loadDataFromServer() {
        String url = API.URL + API.API_URL.FARM_TOPIC_LIST;
        TransferObject data = AppUtil.getHttpData(context);
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
                Intent intent1 = new Intent(MainActivityNew.this, UserActivity.class);
                startActivity(intent1);
            }
        });
    }

    private void initClick() {
        contentViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                EventBus.getDefault().post(position);
                ((RadioButton)radioButtonGroup.getChildAt(position)).setChecked(true);
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
        radioButtonGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.jingxuan_btn){
                    contentViewPager.setCurrentItem(0);
                }else if(checkedId == R.id.qianggou_btn){
                    contentViewPager.setCurrentItem(1);
                }else if(checkedId == R.id.tuijian_btn){
                    contentViewPager.setCurrentItem(2);
                }else if(checkedId == R.id.zhoubian_btn){
                    contentViewPager.setCurrentItem(3);
                }
            }
        });
        ((RadioButton)radioButtonGroup.getChildAt(0)).setChecked(true);
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
                .add("", TuiJianFragment.class)
                .add("", ZhouBianFragment.class)
                .create());
        contentViewPager.setAdapter(adapter);
    }

    public void onEvent(Boolean flag) {
        dragTopLayout.setTouchMode(flag);
    }

    private void initData() {
        codeDao = DBHelper.getDaoSession(context).getCodeDao();
        options = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED).build();
        Code code = codeDao.load(1l);
        String city = "";
        if(code != null) {
            city = CommonUtil.toDBC(code.getCodeDesc());
        }else{
            city = CommonUtil.toDBC(AppUtil.DEFAULT_CITY_NAME);
        }
        if(!"".equals(city))
            area.setText(city.substring(0,2));
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
                    Intent intent1 = new Intent(MainActivityNew.this, UserActivity.class);
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
        code.setCodeId(1l);
        code.setCodetype(AppUtil.CODE_TYPE_AUTO);
        codeDao.update(code);
        Intent intent = new Intent(context, MainActivityNew.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void findViews() {
        contentViewPager = (ViewPager) this.findViewById(R.id.content_view_pager);
        dragTopLayout = (DragTopLayout) this.findViewById(R.id.drag_layout);
        banner = (ConvenientBanner) this.findViewById(R.id.convenient_banner);
        searchBtn = this.findViewById(R.id.search_btn);
        loginBtn = this.findViewById(R.id.user_login);
        userBtn = this.findViewById(R.id.user_btn);
        area = (TextView) this.findViewById(R.id.area);
        radioButtonGroup = (RadioGroup) this.findViewById(R.id.bottom_radio_group);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main_new;
    }

    @Override
    protected String setActionBarTitle() {
        return "";
    }

}
