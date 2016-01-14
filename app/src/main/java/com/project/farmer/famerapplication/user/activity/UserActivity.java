package com.project.farmer.famerapplication.user.activity;

import android.support.v4.view.ViewPager;

import com.baseandroid.BaseActivity;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v13.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v13.FragmentPagerItems;
import com.project.farmer.famerapplication.R;
import com.project.farmer.famerapplication.user.fragment.UserBankCardFragment;
import com.project.farmer.famerapplication.user.fragment.UserFavoFragment;
import com.project.farmer.famerapplication.user.fragment.UserMsgFragment;
import com.project.farmer.famerapplication.user.fragment.UserOrderFragment;

/**
 * Created by gseoa on 2016/1/14.
 */
public class UserActivity extends BaseActivity {
    private ViewPager contentViewPager;
    private SmartTabLayout smartTabLayout;

    @Override
    protected void initViews() {
        findViews();
        initFragments();
    }

    private void findViews() {
        contentViewPager = (ViewPager) this.findViewById(R.id.details_content_view_pager1);
        smartTabLayout = (SmartTabLayout) this.findViewById(R.id.viewpagertab1);
    }

    private void initData() {
    }

    private void initFragments() {
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getFragmentManager(), FragmentPagerItems.with(this)
                .add("我的订单", UserOrderFragment.class)
                .add("我的信息", UserMsgFragment.class)
                .add("我的收藏", UserFavoFragment.class)
                .add("我的银行卡", UserBankCardFragment.class)
                .create());
        contentViewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(contentViewPager);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_user;
    }

    @Override
    protected String setActionBarTitle() {
        return "我的";
    }
}
