package com.egceo.app.myfarm.home.activity;

import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.baseandroid.BaseActivity;
import com.baseandroid.util.CommonUtil;
import com.egceo.app.myfarm.R;
import com.egceo.app.myfarm.entity.SMSObject;
import com.egceo.app.myfarm.entity.TransferObject;
import com.egceo.app.myfarm.entity.UserProfile;
import com.egceo.app.myfarm.http.API;
import com.egceo.app.myfarm.http.AppHttpResListener;
import com.egceo.app.myfarm.http.AppRequest;
import com.egceo.app.myfarm.util.AppUtil;
import com.egceo.app.myfarm.util.GetCodeBtnHandler;

/**
 * Created by FreeMason on 2016/2/18.
 */
public class ForgetPwdActivity extends BaseActivity {
    private EditText pwdEditText;
    private EditText phoneEditText;
    private EditText code;
    private TextView changePwd;
    private TextView getCode;
    private CheckBox viewPwd;
    private GetCodeBtnHandler getCodeBtnHandler;
    @Override
    protected void initViews() {
        findViews();
        initData();
        initClick();
    }

    private void initClick() {
        getCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSms();
            }
        });
        changePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validate())return;
                changePwd();
            }
        });
        viewPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    pwdEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                else
                    pwdEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });
    }

    private void changePwd() {
        String url = API.URL + API.API_URL.FORGET_PWD;
        CommonUtil.showSimpleProgressDialog(getString(R.string.forget_pwd_plz_wait),activity);
        String phoneText = phoneEditText.getText().toString();
        String passwordText = pwdEditText.getText().toString();
        String codeText = code.getText().toString();
        TransferObject data = AppUtil.getHttpData(context);
        UserProfile user = new UserProfile();
        user.setUserBindingPhone(phoneText);
        user.setUserPassword(passwordText);
        SMSObject sms = new SMSObject();
        sms.setSmsId(sp.getString(AppUtil.PWD_SMS_ID,""));
        sms.setSmsVerificationCode(codeText);
        data.setSmsObject(sms);
        data.setUserProfile(user);
        AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                if(data.getMessage().getStatus().equals(AppUtil.RES_STATUS.STATUS_OK)){
                    CommonUtil.showMessage(context,getString(R.string.forget_pwd_success));
                    finish();;
                }
            }
        },data);
        request.execute();
    }

    private void initData() {
        if(("changePwd").equals(getIntent().getStringExtra("type"))){
            changePwd.setText("立即修改");
        }
        getCodeBtnHandler = new GetCodeBtnHandler(AppUtil.PWD_SMS_TIME,sp,context);
        android.os.Message msg = new android.os.Message();
        msg.obj = getCode;
        getCodeBtnHandler.sendMessage(msg);
    }

    private void sendSms() {
        String phoneText = phoneEditText.getText().toString();
        if(phoneText.length() != 11){
            CommonUtil.showMessage(context,getString(R.string.pls_enter_phone_number));
            return;
        }
        getCode.setEnabled(false);
        String url = API.URL + API.API_URL.SEND_SMS;
        TransferObject data = AppUtil.getHttpData(context);
        UserProfile user = new UserProfile();
        user.setUserBindingPhone(phoneText);
        data.setUserProfile(user);
        data.setType("UF");
        AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                SMSObject sms = data.getSmsObject();
                sp.edit().putString(AppUtil.PWD_SMS_ID,sms.getSmsId()).commit();
                android.os.Message msg = new android.os.Message();
                msg.obj = getCode;
                getCodeBtnHandler.setNextTime(AppUtil.SMS_TIME);
                getCodeBtnHandler.sendMessage(msg);
            }

            @Override
            public void onEnd() {
                super.onEnd();
                getCode.setEnabled(true);
            }
        },data);
        request.execute();
    }

    private boolean validate() {
        String phoneText = phoneEditText.getText().toString();
        String passwordText = pwdEditText.getText().toString();
        String codeText = code.getText().toString();
        if(phoneText.length() != 11){
            CommonUtil.showMessage(context,getString(R.string.pls_enter_phone_number));
            return false;
        }
        if(passwordText.length() < 6 || passwordText.length() > 20){
            CommonUtil.showMessage(context,getString(R.string.pls_enter_password));
            return false;
        }
        if(codeText.length() != 6){
            CommonUtil.showMessage(context,getString(R.string.pls_enter_code));
            return false;
        }
        return true;
    }

    private void findViews() {
        pwdEditText = (EditText) this.findViewById(R.id.password);
        phoneEditText = (EditText) this.findViewById(R.id.phone);
        code = (EditText) this.findViewById(R.id.code);
        getCode = (TextView) this.findViewById(R.id.get_code);
        changePwd = (TextView) this.findViewById(R.id.change_pwd_btn);
        viewPwd = (CheckBox) this.findViewById(R.id.view_pwd);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            CommonUtil.hideKeyBoard(activity);
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_forget_pwd;
    }

    @Override
    protected String setActionBarTitle() {
        if("changePwd".equals(getIntent().getStringExtra("type"))){
            return getString(R.string.change_pwd_title);
        }
        return getString(R.string.forget_pwd_title);
    }
}
