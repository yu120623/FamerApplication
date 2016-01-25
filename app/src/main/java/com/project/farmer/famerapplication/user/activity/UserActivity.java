package com.project.farmer.famerapplication.user.activity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baseandroid.BaseActivity;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v13.FragmentPagerItem;
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
                .add("订单", UserOrderFragment.class)
                .add("信息", UserMsgFragment.class)
                .add("收藏", UserFavoFragment.class)
                .add("银行卡", UserBankCardFragment.class)
                .create());
        smartTabLayout.setCustomTabView(new SimpleTabProvider());
        contentViewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(contentViewPager);
    }

    private class SimpleTabProvider implements SmartTabLayout.TabProvider {

        @Override
        public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
            View view = inflater.inflate(R.layout.tab_ord, container, false);
            ImageView tabIcon = (ImageView) view.findViewById(R.id.tab_icon);
            TextView tabTitle = (TextView) view.findViewById(R.id.tab_title);
            if (position == 0) {
                tabTitle.setText("订单");
                tabIcon.setImageResource(R.drawable.tab_item1);
            } else if (position == 1) {
                tabTitle.setText("信息");
                tabIcon.setImageResource(R.drawable.tab_item2);
            } else if (position == 2) {
                tabTitle.setText("收藏");
                tabIcon.setImageResource(R.drawable.tab_item3);
            } else if (position == 3) {
                tabTitle.setText("银行卡");
                tabIcon.setImageResource(R.drawable.tab_item4);
            }
            return view;
        }

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
