package com.egceo.app.myfarm.user.activity;

import android.content.Context;
import android.media.Image;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baseandroid.BaseActivity;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v13.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v13.FragmentPagerItems;
import com.egceo.app.myfarm.R;
import com.egceo.app.myfarm.user.fragment.FundFragment;
import com.egceo.app.myfarm.user.fragment.UserFavoFragment;
import com.egceo.app.myfarm.user.fragment.UserMsgFragment;
import com.egceo.app.myfarm.user.fragment.UserOrderFragment;

/**
 * Created by gseoa on 2016/1/14.
 */
public class UserActivity extends BaseActivity {
    private ViewPager contentViewPager;
    private SmartTabLayout smartTabLayout;
    private ImageView arr1;
    private ImageView arr2;
    private ImageView arr3;
    private ImageView arr4;
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
                .add(R.string.fund, FundFragment.class)
                .add(R.string.order, UserOrderFragment.class)
                .add(R.string.info, UserMsgFragment.class)
                .add(R.string.favourite, UserFavoFragment.class)
                .create());
        smartTabLayout.setCustomTabView(new SimpleTabProvider());
        contentViewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(contentViewPager);
        contentViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                arr1.setVisibility(View.INVISIBLE);
                arr2.setVisibility(View.INVISIBLE);
                arr3.setVisibility(View.INVISIBLE);
                arr4.setVisibility(View.INVISIBLE);
                switch(position){
                    case 0:
                        arr1.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        arr2.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        arr3.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        arr4.setVisibility(View.VISIBLE);
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private class SimpleTabProvider implements SmartTabLayout.TabProvider {
        @Override
        public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
            View view = inflater.inflate(R.layout.tab_ord, container, false);
            ImageView tabIcon = (ImageView) view.findViewById(R.id.tab_icon);
            TextView tabTitle = (TextView) view.findViewById(R.id.tab_title);
            ImageView arr = (ImageView) view.findViewById(R.id.arr);
            if (position == 0) {
                arr1 = arr;
                arr1.setVisibility(View.VISIBLE);
                tabTitle.setText(R.string.fund);
                tabIcon.setImageResource(R.drawable.tab_item4);
            }else if (position == 1) {
                arr2 = arr;
                tabTitle.setText(R.string.order);
                tabIcon.setImageResource(R.drawable.tab_item1);
            }else if (position == 2) {
                arr3 = arr;
                tabTitle.setText(R.string.info);
                tabIcon.setImageResource(R.drawable.tab_item2);
            }else if (position == 3) {
                arr4 = arr;
                tabTitle.setText(R.string.favourite);
                tabIcon.setImageResource(R.drawable.tab_item3);
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
