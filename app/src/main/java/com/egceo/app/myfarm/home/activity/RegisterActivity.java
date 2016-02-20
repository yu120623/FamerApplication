package com.egceo.app.myfarm.home.activity;

import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
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
    }

    private void sendSms() {
        String phoneText = phone.getText().toString();
        if(phoneText.length() != 11){
            CommonUtil.showMessage(context,getString(R.string.pls_enter_phone_number));
            return;
        }
        android.os.Message msg = new android.os.Message();
        msg.obj = getCode;
        getCodeBtnHandler.setNextTime(AppUtil.SMS_TIME);
        getCodeBtnHandler.sendMessage(msg);
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
        sms.setSmsId(sp.getString(AppUtil.REG_SMS_ID,""));
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
        if(phoneText.length() != 11){
            CommonUtil.showMessage(context,getString(R.string.pls_enter_phone_number));
            return false;
        }
        if(TextUtils.isEmpty(passwordText)){
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
