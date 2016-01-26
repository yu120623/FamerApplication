package com.egceo.app.myfarm.farm.fragment;

import com.egceo.app.myfarm.comment.BaseCommentFragment;
import com.egceo.app.myfarm.entity.FarmModel;
import com.egceo.app.myfarm.entity.TransferObject;
import com.egceo.app.myfarm.http.API;
import com.egceo.app.myfarm.http.AppHttpResListener;
import com.egceo.app.myfarm.http.AppRequest;
import com.egceo.app.myfarm.util.AppUtil;

import de.greenrobot.event.EventBus;
import github.chenupt.dragtoplayout.AttachUtil;


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

    public void onEvent(Integer index){
        if(index.intValue() == 1){
            EventBus.getDefault().post(AttachUtil.isRecyclerViewAttach(pingjiaList));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
}
