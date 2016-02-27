package com.egceo.app.myfarm.listener;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;

import com.baseandroid.util.CommonUtil;
import com.egceo.app.myfarm.R;
import com.egceo.app.myfarm.entity.TransferObject;
import com.egceo.app.myfarm.home.activity.LoginActivity;
import com.egceo.app.myfarm.http.API;
import com.egceo.app.myfarm.http.AppHttpResListener;
import com.egceo.app.myfarm.http.AppRequest;
import com.egceo.app.myfarm.util.AppUtil;
import com.egceo.app.myfarm.view.FavouriteBtn;

/**
 * Created by FreeMason on 2016/2/18.
 */
public class OnFavouriteClick implements FavouriteBtn.OnCheckedChangeListener {
    public static final String FARM_TOPIC = "farmTopic";
    public static final String FARM = "farm";
    private Activity activity;
    public OnFavouriteClick(Activity activity){
        this.activity = activity;
    }

    @Override
    public void onCheckedChanged(View view) {
        FavouriteBtn favouriteBtn = (FavouriteBtn) view;
        if(!AppUtil.checkIsLogin(view.getContext())){
            Intent intent = new Intent(activity, LoginActivity.class);
            activity.startActivity(intent);
            return;
        }
        String id = (String) view.getTag(R.id.favourite_id);
        String type = (String) view.getTag(R.id.favourite_type);
        String url = "";
        if(favouriteBtn.isChecked()){
            CommonUtil.showMessage(view.getContext(), view.getContext().getString(R.string.cancel_favourite));
            url = API.URL + API.API_URL.COLLECT_CANCEL;
        }else {
            CommonUtil.showMessage(view.getContext(), view.getContext().getString(R.string.favourite_success));
            url = API.URL + API.API_URL.FAVOURITE;
        }
        TransferObject data = AppUtil.getHttpData(view.getContext());
        if (type.equals(FARM_TOPIC)) {
            data.setFarmTopicAliasId(id);
        } else if (type.equals(FARM)) {
            data.setFarmAliasId(id);
        }
        AppRequest request = new AppRequest(view.getContext(), url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {

            }
        }, data);
        request.execute();
    }
}
