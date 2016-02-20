package com.egceo.app.myfarm.user.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baseandroid.BaseActivity;
import com.baseandroid.util.CommonUtil;
import com.egceo.app.myfarm.R;
import com.egceo.app.myfarm.home.activity.MainActivity;
import com.egceo.app.myfarm.util.AppUtil;

/**
 * Created by FreeMason on 2016/2/18.
 */
public class UserSettingActivity extends BaseActivity{
    private TextView loginOutBtn;
    @Override
    protected void initViews() {
        findViews();
        initClick();
    }

    private void initClick() {
        loginOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtil.showSimpleProgressDialog("正在安全退出,请稍后",activity);
                sp.edit().remove(AppUtil.L_N).commit();
                Intent intent = new Intent(context, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                CommonUtil.dismissSimpleProgressDialog();
                startActivity(intent);
                finish();
            }
        });
    }

    private void findViews() {
        loginOutBtn = (TextView) this.findViewById(R.id.login_out_btn);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_user_setting;
    }

    @Override
    protected String setActionBarTitle() {
        return getString(R.string.setting);
    }
}
