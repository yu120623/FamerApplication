package com.egceo.app.myfarm.user.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.baseandroid.BaseActivity;
import com.baseandroid.util.CommonUtil;
import com.egceo.app.myfarm.R;
import com.egceo.app.myfarm.entity.TransferObject;
import com.egceo.app.myfarm.home.activity.ForgetPwdActivity;
import com.egceo.app.myfarm.home.activity.MainActivityNew;
import com.egceo.app.myfarm.http.API;
import com.egceo.app.myfarm.http.AppHttpResListener;
import com.egceo.app.myfarm.http.AppRequest;
import com.egceo.app.myfarm.util.AppUtil;

import java.util.Map;

/**
 * Created by FreeMason on 2016/2/18.
 */
public class UserSettingActivity extends BaseActivity {
    private TextView loginOutBtn;
    private View changeUserName;
    private View changePwd;
    private View clearCache;
    private View update;
    private View adboutUs;

    @Override
    protected void initViews() {
        findViews();
        initClick();
    }

    private void initClick() {
        loginOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtil.showSimpleProgressDialog("正在安全退出,请稍后", activity);
                sp.edit().remove(AppUtil.L_N).commit();
                Intent intent = new Intent(context, MainActivityNew.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                CommonUtil.dismissSimpleProgressDialog();
                startActivity(intent);
                finish();
            }
        });
        changeUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SetUserNameActivity.class);
                startActivity(intent);
            }
        });
        changePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ForgetPwdActivity.class);
                intent.putExtra("type", "changePwd");
                startActivity(intent);
            }
        });
        clearCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtil.showMessage(context, "清除成功");
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtil.showSimpleProgressDialog("正在检查更新",activity);
                String url = API.URL + API.API_URL.UPDATE;
                AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
                    @Override
                    public void onSuccess(TransferObject data) {
                        Map<String, Map<String, String>> map = data.getAppMap();
                        if (map.get("android_V") != null) {
                            sp.edit().putString(AppUtil.SP_VERSION, map.get("android_V").get("Version")).commit();
                            sp.edit().putString(AppUtil.SP_URL, map.get("android_V").get("URL")).commit();
                            sp.edit().putString(AppUtil.SP_IS_FORCED, map.get("android_V").get("Is_Forced")).commit();
                            float currentVserion = CommonUtil.getVersion(context);
                            float version = Float.parseFloat(sp.getString(AppUtil.SP_VERSION,"0"));
                            if(version > currentVserion) {
                                Intent intent = new Intent(context, MainActivityNew.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }else{
                                CommonUtil.showMessage(context,"已经是最新版本");
                            }
                        }else{
                            CommonUtil.showMessage(context,"已经是最新版本");
                        }
                    }
                }, AppUtil.getHttpData(context));
                request.execute();
            }
        });
        adboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,AboutUsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void findViews() {
        loginOutBtn = (TextView) this.findViewById(R.id.login_out_btn);
        changeUserName = this.findViewById(R.id.change_user_name);
        changePwd = this.findViewById(R.id.change_pwd);
        clearCache = this.findViewById(R.id.clear_cache);
        update = this.findViewById(R.id.update);
        adboutUs = this.findViewById(R.id.about_us);
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
