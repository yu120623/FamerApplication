package com.egceo.app.myfarm.home.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.baseandroid.BaseActivity;
import com.baseandroid.util.CommonUtil;
import com.egceo.app.myfarm.R;
import com.egceo.app.myfarm.entity.AaSubjectLogin;
import com.egceo.app.myfarm.entity.TransferObject;
import com.egceo.app.myfarm.http.API;
import com.egceo.app.myfarm.http.AppHttpResListener;
import com.egceo.app.myfarm.http.AppRequest;
import com.egceo.app.myfarm.user.activity.UserActivity;
import com.egceo.app.myfarm.util.AppUtil;

public class LoginActivity extends BaseActivity {
    private EditText phone;
    private EditText password;
    private TextView registerBtn;
    private TextView loginBtn;

    @Override
    protected void initViews() {
        findViews();
        initData();
        initClick();
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
                login();
            }
        });
    }

    private void login() {
        CommonUtil.showSimpleProgressDialog(getString(R.string.login_now),activity);
        String url = API.URL + API.API_URL.LOGIN;
        String phoneText = phone.getText().toString();
        String passwordText = password.getText().toString();
        TransferObject data = AppUtil.getHttpData(context);
        AaSubjectLogin login = new AaSubjectLogin();
        login.setLoginName(phoneText);
        login.setLoginPassword(passwordText);
        data.setAaSubjectLogin(login);
        AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                if(data.getMessage().getStatus().equals(AppUtil.RES_STATUS.STATUS_OK)) {
                    sp.edit().putString(AppUtil.L_N,data.getUserProfile().getUserAliasId()).commit();
                    /*Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                    intent.putExtra("in", true);
                    startActivity(intent);*/
                    finish();
                }
            }
        },data);
        request.execute();
    }

    private void findViews() {
        registerBtn = (TextView) this.findViewById(R.id.register_user_btn);
        loginBtn = (TextView) this.findViewById(R.id.login_btn);
        phone = (EditText) this.findViewById(R.id.phone);
        password = (EditText) this.findViewById(R.id.password);
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
