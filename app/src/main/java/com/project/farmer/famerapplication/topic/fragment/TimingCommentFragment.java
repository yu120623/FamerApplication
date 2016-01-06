package com.project.farmer.famerapplication.topic.fragment;

import com.project.farmer.famerapplication.comment.fragment.BaseCommentFragment;
import com.project.farmer.famerapplication.entity.FarmTopicModel;
import com.project.farmer.famerapplication.entity.TransferObject;
import com.project.farmer.famerapplication.http.API;
import com.project.farmer.famerapplication.http.AppHttpResListener;
import com.project.farmer.famerapplication.http.AppRequest;
import com.project.farmer.famerapplication.util.AppUtil;


public class TimingCommentFragment extends BaseCommentFragment {
    protected FarmTopicModel farmTopicModel;

    @Override
    protected void initData() {
        farmTopicModel = (FarmTopicModel) this.getArguments().getSerializable("farmTopic");
        super.initData();
    }

    protected void loadDataFromServer() {
        String url = API.URL + API.API_URL.FARM_TOPIC_COMMENT_LIST;
        TransferObject data = AppUtil.getHttpData(context);
        data.setPageNumber(0);
        data.setFarmTopicAliasId(farmTopicModel.getFarmTopicAliasId());
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
