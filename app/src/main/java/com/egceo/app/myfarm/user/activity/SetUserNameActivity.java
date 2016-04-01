package com.egceo.app.myfarm.user.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.baseandroid.BaseActivity;
import com.baseandroid.util.CommonUtil;
import com.egceo.app.myfarm.R;
import com.egceo.app.myfarm.db.DBHelper;
import com.egceo.app.myfarm.db.UserProfileDao;
import com.egceo.app.myfarm.entity.TransferObject;
import com.egceo.app.myfarm.entity.UserProfile;
import com.egceo.app.myfarm.http.API;
import com.egceo.app.myfarm.http.AppHttpResListener;
import com.egceo.app.myfarm.http.AppRequest;
import com.egceo.app.myfarm.util.AppUtil;

/**
 * Created by FreeMason on 2016/2/24.
 */
public class SetUserNameActivity extends BaseActivity {
    private EditText userNameEditText;
    private Button save;
    private UserProfileDao userProfileDao;
    private UserProfile userProfile;

    @Override
    protected void initViews() {
        userProfile = (UserProfile) this.getIntent().getSerializableExtra("userProfile");
        userProfileDao = DBHelper.getUserDaoSession(context, userProfile.getUserAliasId()).getUserProfileDao();
        userNameEditText = (EditText) this.findViewById(R.id.user_name);
        userNameEditText.setText(userProfile.getUserName());
        save = (Button) this.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(userNameEditText.getText())){
                    CommonUtil.showMessage(context,getString(R.string.plz_enter_username));
                    return;
                }
                CommonUtil.showSimpleProgressDialog(getString(R.string.changing_name),activity);
                String url = API.URL + API.API_URL.USER_EDIT;
                TransferObject data = AppUtil.getHttpData(context);
                UserProfile userProfile = new UserProfile();
                userProfile.setUserAliasId(data.getUserAliasId());
                userProfile.setUserName(userNameEditText.getText().toString());
                data.setUserProfile(userProfile);
                AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
                    @Override
                    public void onSuccess(TransferObject data) {
                        SetUserNameActivity.this.userProfile.setUserName(userNameEditText.getText().toString());
                        userProfileDao.update(SetUserNameActivity.this.userProfile);
                        CommonUtil.showMessage(context,getString(R.string.change_success));
                        setResult(RESULT_OK);
                        finish();
                    }
                },data);
                request.execute();
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_set_user_name;
    }

    @Override
    protected String setActionBarTitle() {
        return getString(R.string.me);
    }
}
