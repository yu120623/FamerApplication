package com.project.farmer.famerapplication.user.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.baseandroid.BaseFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v13.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v13.FragmentPagerItems;
import com.project.farmer.famerapplication.R;
import com.project.farmer.famerapplication.home.fragment.JingXuanFragment;
import com.project.farmer.famerapplication.user.orderfragment.ConsumedFragment;
import com.project.farmer.famerapplication.user.orderfragment.PaidFragment;
import com.project.farmer.famerapplication.user.orderfragment.ReFundFragment;
import com.project.farmer.famerapplication.user.orderfragment.UnConsumedFragment;
import com.project.farmer.famerapplication.user.orderfragment.UnPaidFragment;

/**
 * Created by gseoa on 2016/1/14.
 */
public class UserOrderFragment extends BaseFragment {
    private ViewPager contentViewPager;
    private SmartTabLayout smartTabLayout;

    @Override
    protected void initViews() {
        findViews();
        initData();
        initFragments();
    }

    private void initData() {
    }

    private void findViews() {
        contentViewPager = (ViewPager) this.findViewById(R.id.details_content_view_pager2);
        smartTabLayout = (SmartTabLayout) this.findViewById(R.id.viewpagertab2);
    }

    private void initFragments() {
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getFragmentManager(), FragmentPagerItems.with(context)
                .add("未付款", UnPaidFragment.class)
                .add("已付款", PaidFragment.class)
                .add("待消费", UnConsumedFragment.class)
                .add("已消费", ConsumedFragment.class)
                .add("退单区", ReFundFragment.class)
                .create());
        contentViewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(contentViewPager);
    }

    @Override
    protected int getContentView() {
        return R.layout.frag_user_order;
    }

}
