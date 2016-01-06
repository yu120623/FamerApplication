package com.project.farmer.famerapplication.farm.fragment;

import android.util.Log;

import com.project.farmer.famerapplication.comment.fragment.BaseCommentFragment;
import com.project.farmer.famerapplication.entity.FarmModel;
import com.project.farmer.famerapplication.entity.TransferObject;
import com.project.farmer.famerapplication.http.API;
import com.project.farmer.famerapplication.http.AppHttpResListener;
import com.project.farmer.famerapplication.http.AppRequest;
import com.project.farmer.famerapplication.util.AppUtil;


public class FarmCommentFragment extends BaseCommentFragment {

    private FarmModel farmModel;

    @Override
    protected void initData() {
        farmModel = (FarmModel) this.getArguments().getSerializable("farmModel");
        super.initData();
    }

    @Override
    protected void loadDataFromServer() {
        String url = API.URL + API.API_URL.FARM_TOPIC_COMMENT_LIST;
        TransferObject data = AppUtil.getHttpData(context);
        data.setPageNumber(0);
        data.setFarmAliasId(farmModel.getFarmAliasId());
        AppRequest appRequest = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                resData = data;
                comments = data.getCommentModels();
                refreshComment();
            }
        },data);
        appRequest.execute();
    }
}
