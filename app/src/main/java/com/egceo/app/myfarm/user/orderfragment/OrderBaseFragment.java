package com.egceo.app.myfarm.user.orderfragment;

import android.view.View;

import com.baseandroid.BaseFragment;
import com.egceo.app.myfarm.R;

/**
 * Created by FreeMason on 2016/4/14.
 */
public abstract class OrderBaseFragment extends BaseFragment {
    protected void showNothing() {
        showRetryView();
        retryText.setText(R.string.no_order);
        retryButton.setVisibility(View.GONE);
        retryImg.setImageResource(R.mipmap.no_data);
    }
}
