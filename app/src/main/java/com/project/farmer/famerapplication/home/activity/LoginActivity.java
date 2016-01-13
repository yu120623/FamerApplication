package com.project.farmer.famerapplication.home.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.baseandroid.BaseActivity;
import com.project.farmer.famerapplication.R;

/**
 * Created by gseoa on 2016/1/12.
 */
public class LoginActivity extends BaseActivity {
    private TextView registerBtn;

    @Override
    protected void initViews() {
        findViews();
        initData();
        initClick();
    }

    private void findViews() {
        registerBtn = (TextView) this.findViewById(R.id.register_user_btn);
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
