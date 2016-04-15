package com.egceo.app.myfarm.topic.fragment;

import com.egceo.app.myfarm.comment.BaseCommentFragment;
import com.egceo.app.myfarm.entity.CommentModel;
import com.egceo.app.myfarm.entity.FarmTopicModel;
import com.egceo.app.myfarm.entity.OrderModel;
import com.egceo.app.myfarm.entity.TransferObject;
import com.egceo.app.myfarm.http.API;
import com.egceo.app.myfarm.http.AppHttpResListener;
import com.egceo.app.myfarm.http.AppRequest;
import com.egceo.app.myfarm.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import github.chenupt.dragtoplayout.AttachUtil;

public class TopicCommentFragment extends BaseCommentFragment {

    protected FarmTopicModel farmTopicModel;
    @Override
    protected void initData() {
        farmTopicModel = (FarmTopicModel) this.getArguments().getSerializable("farmTopic");
        super.initData();
    }

    protected void loadDataFromServer() {
        String url = API.URL + API.API_URL.FARM_TOPIC_COMMENT_LIST;
        TransferObject data = AppUtil.getHttpData(context);
        data.setPageNumber(pageNumber);
        data.setFarmTopicAliasId(farmTopicModel.getFarmTopicAliasId());
        AppRequest appRequest = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                resData = data;
                List<CommentModel> list = data.getCommentModels();
                if(pageNumber == 0){
                    if(list == null || list.size() <= 0) {
                        list = new ArrayList<>();
                        showNothing();
                    }
                    comments = list;
                }else{
                    if(list.size() > 0){
                        comments.addAll(list);
                    }else{
                        pageNumber--;
                        loadMoreFooter.showNoMoreTips();
                        pingjiaList.removeOnScrollListener(loadMoreListener);
                    }
                }
                refreshComment();
            }

            @Override
            public void onEnd() {
                super.onEnd();
                loadMoreFooter.setIsLoading(false);
                loadMoreFooter.hideLoadMore();
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
