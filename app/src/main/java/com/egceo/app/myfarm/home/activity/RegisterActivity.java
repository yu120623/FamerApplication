package com.egceo.app.myfarm.home.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.maps.model.Text;
import com.baseandroid.BaseActivity;
import com.baseandroid.util.CommonUtil;
import com.egceo.app.myfarm.R;
import com.egceo.app.myfarm.entity.Message;
import com.egceo.app.myfarm.entity.SMSObject;
import com.egceo.app.myfarm.entity.TransferObject;
import com.egceo.app.myfarm.entity.UserProfile;
import com.egceo.app.myfarm.http.API;
import com.egceo.app.myfarm.http.AppHttpResListener;
import com.egceo.app.myfarm.http.AppRequest;
import com.egceo.app.myfarm.util.AppUtil;
import com.egceo.app.myfarm.util.GetCodeBtnHandler;
import com.html.HtmlActivity;

/**
 * Created by gseoa on 2016/1/13.
 */
public class RegisterActivity extends BaseActivity {
    private EditText phone;
    private EditText password;
    private EditText code;
    private Button registerBtn;
    private TextView getCode;
    private GetCodeBtnHandler getCodeBtnHandler;
    private CheckBox checkBox;
    private TextView authDetailBtn;
    @Override
    protected void initViews() {
        findViews();
        initClick();
        initData();
    }

    private void initData() {
        getCodeBtnHandler = new GetCodeBtnHandler(AppUtil.REG_SMS_TIME,sp,context);
        android.os.Message msg = new android.os.Message();
        msg.obj = getCode;
        getCodeBtnHandler.sendMessage(msg);
    }

    private void initClick() {
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validate())return;
                register();
            }
        });
        getCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSms();
            }
        });
        authDetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HtmlActivity.class);
                intent.putExtra("url","http://w.mycff.com/Wechat/Index/news/id/7.htmll");
                startActivity(intent);
            }
        });
    }

    private void sendSms() {
        String phoneText = phone.getText().toString();
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
        data.setType("UR");
        AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                SMSObject sms = data.getSmsObject();
                sp.edit().putString(AppUtil.REG_SMS_ID,sms.getSmsId()).commit();
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

    private void register() {
        CommonUtil.showSimpleProgressDialog(getString(R.string.register_plz_wait),activity);
        String url = API.URL + API.API_URL.REGISTER_USER;
        String phoneText = phone.getText().toString();
        String passwordText = password.getText().toString();
        String codeText = code.getText().toString();
        TransferObject data = AppUtil.getHttpData(context);
        UserProfile user = new UserProfile();
        user.setUserBindingPhone(phoneText);
        user.setUserPassword(passwordText);
        SMSObject sms = new SMSObject();
        sms.setSmsId(sp.getString(AppUtil.REG_SMS_ID,"0"));
        sms.setSmsVerificationCode(codeText);
        data.setSmsObject(sms);
        data.setUserProfile(user);
        AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                if(data.getMessage().getStatus().equals(AppUtil.RES_STATUS.STATUS_OK)){
                    CommonUtil.showMessage(context,getString(R.string.register_success));
                    finish();
                }
            }
        },data);
        request.execute();
    }

    private boolean validate() {
        String phoneText = phone.getText().toString();
        String passwordText = password.getText().toString();
        String codeText = code.getText().toString();
        if(!checkBox.isChecked()){
            CommonUtil.showMessage(context,getString(R.string.agree_auth));
            return false;
        }
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
        phone = (EditText) this.findViewById(R.id.phone);
        password = (EditText) this.findViewById(R.id.password);
        code = (EditText) this.findViewById(R.id.code);
        registerBtn = (Button) this.findViewById(R.id.register_btn);
        getCode = (TextView) this.findViewById(R.id.get_code);
        checkBox = (CheckBox) this.findViewById(R.id.auth_btn);
        authDetailBtn = (TextView) this.findViewById(R.id.auth_detail_btn);
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
        return R.layout.activity_register;
    }

    @Override
    protected String setActionBarTitle() {
        return "注册";
    }
}
