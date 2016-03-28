package com.egceo.app.myfarm.user.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.android.volley.VolleyError;
import com.baseandroid.BaseActivity;
import com.baseandroid.util.CommonUtil;
import com.baseandroid.util.ImageLoaderUtil;
import com.baseandroid.util.Json;
import com.egceo.app.myfarm.R;
import com.egceo.app.myfarm.db.DBHelper;
import com.egceo.app.myfarm.db.UserProfileDao;
import com.egceo.app.myfarm.entity.*;
import com.egceo.app.myfarm.home.activity.ForgetPwdActivity;
import com.egceo.app.myfarm.home.activity.MainActivityNew;
import com.egceo.app.myfarm.http.API;
import com.egceo.app.myfarm.http.AppHttpResListener;
import com.egceo.app.myfarm.http.AppRequest;
import com.egceo.app.myfarm.util.AppUtil;
import com.egceo.app.myfarm.util.GalleryImageLoader;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import galleryfinal.GalleryHelper;
import galleryfinal.model.PhotoInfo;

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
    private ImageView imageView;
    private View changeUserPic;
    private OSSClient oss;
    private UserProfile userProfile;
    private DisplayImageOptions options;
    private UserProfileDao userProfileDao;

    @Override
    protected void initViews() {
        findViews();
        initClick();
    }

    private void initClick() {
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider("1watviMQtPwgWYfY", "fVuW6rXCwSfNi5SkLYorUrBqGB2Qgn");
        oss = new OSSClient(getApplicationContext(), "http://oss-cn-hangzhou.aliyuncs.com", credentialProvider);
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
                Intent intent = new Intent(context, AboutUsActivity.class);
                startActivity(intent);
            }
        });
        changeUserPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GalleryHelper.openGalleryMuti(activity, 1, new GalleryImageLoader());
            }
        });
        String id = sp.getString(AppUtil.L_N, "");
        userProfileDao = DBHelper.getUserDaoSession(context, id).getUserProfileDao();
        userProfile = userProfileDao.load(id);
        if(userProfile != null){
            options = new DisplayImageOptions.Builder()
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .displayer(new RoundedBitmapDisplayer(CommonUtil.Dp2Px(context,100)))
                    .build();
            ImageLoaderUtil.getInstance().displayImg(imageView,userProfile.getUserPic(),options);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == GalleryHelper.GALLERY_RESULT_SUCCESS){
            if(requestCode == GalleryHelper.GALLERY_REQUEST_CODE){
                ArrayList<PhotoInfo> photos = (ArrayList<PhotoInfo>) data.getSerializableExtra(GalleryHelper.RESULT_LIST_DATA);
                if(photos != null && photos.size() > 0)
                    CommonUtil.gotoCropImg(Uri.fromFile(new File(photos.get(0).getPhotoPath())),activity);
            }
        }
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == 3){
                if(data.getExtras().get("data") == null){
                    CommonUtil.showMessage(context,"不支持该种裁剪方式，请更换后再试");
                    return;
                }
                CommonUtil.showSimpleProgressDialog("正在上传",activity,false);
                Bitmap pic = (Bitmap) data.getExtras().get("data");
                String name = System.currentTimeMillis()+"_android.jpg";
                final String imgUrl = "http://s.mycff.com/"+name;
                PutObjectRequest putObjectRequest = new PutObjectRequest("mygoto", name,CommonUtil.getBitmap2Bytes(pic));
                oss.asyncPutObject(putObjectRequest, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                    @Override
                    public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                        String url = API.URL + API.API_URL.USER_EDIT;
                        TransferObject data = AppUtil.getHttpData(context);
                        final UserProfile userProfile = new UserProfile();
                        userProfile.setUserAliasId(data.getUserAliasId());
                        userProfile.setUserPic(imgUrl);
                        if(UserSettingActivity.this.userProfile != null)
                            userProfile.setUserName(UserSettingActivity.this.userProfile.getUserName());
                        data.setUserProfile(userProfile);
                        AppRequest req = new AppRequest(context, url, new AppHttpResListener() {
                            @Override
                            public void onSuccess(TransferObject data) {
                                ImageLoaderUtil.getInstance().displayImg(imageView,imgUrl,options);
                                UserSettingActivity.this.userProfile.setUserPic(imgUrl);
                                userProfileDao.update(UserSettingActivity.this.userProfile);
                                CommonUtil.showMessage(context, getString(R.string.change_success));
                            }

                            @Override
                            public void onFailed(com.egceo.app.myfarm.entity.Error error) {
                                CommonUtil.showMessage(context, getString(R.string.upload_pic_failed));
                            }
                        },data);
                        req.execute();
                    }

                    @Override
                    public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                        CommonUtil.showMessage(context,getString(R.string.upload_pic_failed));
                        CommonUtil.dismissSimpleProgressDialog();
                    }
                });
            }
        }
    }

    private void findViews() {
        loginOutBtn = (TextView) this.findViewById(R.id.login_out_btn);
        changeUserName = this.findViewById(R.id.change_user_name);
        changePwd = this.findViewById(R.id.change_pwd);
        clearCache = this.findViewById(R.id.clear_cache);
        update = this.findViewById(R.id.update);
        adboutUs = this.findViewById(R.id.about_us);
        imageView = (ImageView) this.findViewById(R.id.user_pic);
        changeUserPic = this.findViewById(R.id.change_user_pic);
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
