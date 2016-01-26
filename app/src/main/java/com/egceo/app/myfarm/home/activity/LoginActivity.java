package com.egceo.app.myfarm.home.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.baseandroid.BaseActivity;
import com.egceo.app.myfarm.R;
import com.egceo.app.myfarm.user.activity.UserActivity;

/**
 * Created by gseoa on 2016/1/12.
 */
public class LoginActivity extends BaseActivity {
    private TextView registerBtn;
    private TextView loginBtn;

    @Override
    protected void initViews() {
        findViews();
        initData();
        initClick();
    }

    private void findViews() {
        registerBtn = (TextView) this.findViewById(R.id.register_user_btn);
        loginBtn = (TextView) this.findViewById(R.id.login_btn);
    }

    private void initData() {
    }

    private void initClick() {
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                intent.putExtra("in", true);
                startActivity(intent);
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected String setActionBarTitle() {
        return "登录";
    }
}
