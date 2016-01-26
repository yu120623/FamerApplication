package com.egceo.app.myfarm.user.fragment;

import android.support.v4.view.ViewPager;

import com.baseandroid.BaseFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v13.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v13.FragmentPagerItems;
import com.egceo.app.myfarm.R;
import com.egceo.app.myfarm.user.orderfragment.ConsumedFragment;
import com.egceo.app.myfarm.user.orderfragment.PaidFragment;
import com.egceo.app.myfarm.user.orderfragment.ReFundFragment;
import com.egceo.app.myfarm.user.orderfragment.UnConsumedFragment;
import com.egceo.app.myfarm.user.orderfragment.UnPaidFragment;

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
                .add(R.string.no_pay, UnPaidFragment.class)
                .add(R.string.paid, PaidFragment.class)
                .add(R.string.wait_consumption, UnConsumedFragment.class)
                .add(R.string.already_consumption, ConsumedFragment.class)
                .add(R.string.back_order, ReFundFragment.class)
                .create());
        contentViewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(contentViewPager);
    }

    @Override
    protected int getContentView() {
        return R.layout.frag_user_order;
    }

}
