package com.egceo.app.myfarm.user.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.maps.model.Text;
import com.baseandroid.BaseFragment;
import com.baseandroid.util.CommonUtil;
import com.cundong.recyclerview.EndlessRecyclerOnScrollListener;
import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.cundong.recyclerview.RecyclerViewUtils;
import com.egceo.app.myfarm.R;
import com.egceo.app.myfarm.entity.CollectModel;
import com.egceo.app.myfarm.entity.FarmModel;
import com.egceo.app.myfarm.entity.FarmTopicModel;
import com.egceo.app.myfarm.entity.OrderModel;
import com.egceo.app.myfarm.entity.TransferObject;
import com.egceo.app.myfarm.farm.activity.FarmDetailActivity;
import com.egceo.app.myfarm.http.API;
import com.egceo.app.myfarm.http.AppHttpResListener;
import com.egceo.app.myfarm.http.AppRequest;
import com.egceo.app.myfarm.loadmore.LoadMoreFooter;
import com.egceo.app.myfarm.topic.activity.TimingTopicDetailsActivity;
import com.egceo.app.myfarm.topic.activity.TopicDetailsActivity;
import com.egceo.app.myfarm.util.AppUtil;
import com.egceo.app.myfarm.view.CustomUIHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by gseoa on 2016/1/14.
 */
public class UserFavoFragment extends BaseFragment {
    private RecyclerView favoList;
    private FavoAdapter adapter;
    private List<CollectModel> collectModels = new ArrayList<>();
    private SimpleDateFormat dataDateFormat;
    private LoadMoreFooter loadMoreFooter;
    private Integer pageNumber = 0;
    private PtrFrameLayout frameLayout;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter;
    private TextView favouriteNum;
    @Override
    protected void initViews() {
        findViews();
        initData();
    }

    private void findViews() {
        favoList = (RecyclerView) this.findViewById(R.id.favo_list);
        frameLayout = (PtrFrameLayout) this.findViewById(R.id.ptr);
        favouriteNum = (TextView) this.findViewById(R.id.user_favourite_num);
    }

    private void initData() {
        adapter = new FavoAdapter();
        favoList.setLayoutManager(new LinearLayoutManager(context));
        dataDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        loadMoreFooter = new LoadMoreFooter(context);
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        favoList.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        RecyclerViewUtils.setFooterView(favoList,loadMoreFooter.getFooter());
        favoList.addOnScrollListener(loadMoreListener);
        CustomUIHandler header = new CustomUIHandler(context);
        frameLayout.addPtrUIHandler(header);
        frameLayout.setHeaderView(header);
        frameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pageNumber = 0;
                favoList.removeOnScrollListener(loadMoreListener);
                favoList.addOnScrollListener(loadMoreListener);
                loadMoreFooter.reset();
                loadDataFromServer();
            }
        });
        frameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                frameLayout.autoRefresh(true);
            }
        }, 100);
    }

    private void loadDataFromServer() {
        loadMoreFooter.setIsLoading(true);
        String url = API.URL + API.API_URL.MY_FAVOURITE;
        TransferObject data = AppUtil.getHttpData(context);
        data.setPageNumber(pageNumber);
        AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                favouriteNum.setText(data.getTotalNum());
                List<CollectModel> list = data.getCollectModels();
                if(pageNumber == 0){
                    if(list == null) {
                        showNothing();
                        list = new ArrayList<>();
                    }
                    collectModels = list;
                }else{
                    if(list.size() > 0){
                        collectModels.addAll(list);
                    }else{
                        pageNumber--;
                        loadMoreFooter.showNoMoreTips();
                        favoList.removeOnScrollListener(loadMoreListener);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onEnd() {
                frameLayout.refreshComplete();
                loadMoreFooter.setIsLoading(false);
                loadMoreFooter.hideLoadMore();
            }
        },data);
        request.execute();
    }

    private void showNothing() {
        showRetryView();
        retryButton.setText(R.string.go_watch);
        retryImg.setImageResource(R.mipmap.no_data);
        retryText.setText(R.string.no_favourite);
    }

    @Override
    protected void retry() {
        activity.finish();
    }

    //加载更多监听
    private EndlessRecyclerOnScrollListener loadMoreListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            if(loadMoreFooter.isLoading())return;
            pageNumber++;
            loadMoreFooter.showLoadingTips();
            loadDataFromServer();
        }
    };

    class FavoAdapter extends RecyclerView.Adapter<FavoViewHolder> {
        @Override
        public FavoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = View.inflate(parent.getContext(), R.layout.item_favourite, null);
            RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,RecyclerView.LayoutParams.WRAP_CONTENT);
            v.setLayoutParams(layoutParams);
            v.setOnClickListener(onFavClick);
            v.setOnLongClickListener(onFavLongClick);
            FavoViewHolder holder = new FavoViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(FavoViewHolder holder, int position) {
            CollectModel collectModel = collectModels.get(position);
            holder.itemView.setTag(collectModel);
            holder.favTag.setText(getFavTag(collectModel.getStatus()));
            holder.favTag.setBackgroundResource(getFavTagBg(collectModel.getStatus()));
            holder.favTime.setText(dataDateFormat.format(collectModel.getCollectDate()));
            holder.favName.setText(collectModel.getTitle());
            holder.favDesc.setText(collectModel.getDesc());
        }

        @Override
        public int getItemCount() {
            return collectModels.size();
        }
    }

    class FavoViewHolder extends RecyclerView.ViewHolder {
        private TextView favTag;
        private TextView favName;
        private TextView favDesc;
        private TextView favTime;
        public FavoViewHolder(View itemView) {
            super(itemView);
            favDesc = (TextView) itemView.findViewById(R.id.favourite_desc);
            favName = (TextView) itemView.findViewById(R.id.favourite_name);
            favTime = (TextView) itemView.findViewById(R.id.favourite_time);
            favTag = (TextView) itemView.findViewById(R.id.favourite_tag);
        }
    }

    private View.OnLongClickListener onFavLongClick = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            final CollectModel collectModel = (CollectModel) v.getTag();
            new AlertDialog.Builder(activity).setItems(new String[]{getString(R.string.del)}, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    TransferObject data = AppUtil.getHttpData(view.getContext());
                    String url = API.URL + API.API_URL.COLLECT_CANCEL;
                    if (collectModel.getStatus().equals("1")) {
                        data.setFarmAliasId(collectModel.getCollectAliasId());
                    } else{
                        if(collectModel.getStatus().equals("3")){
                            if(collectModel.getType().equals("0")){
                                data.setFarmAliasId(collectModel.getCollectAliasId());
                            }else{
                                data.setFarmTopicAliasId(collectModel.getCollectAliasId());
                            }
                        }else {
                            data.setFarmTopicAliasId(collectModel.getCollectAliasId());
                        }
                    }
                    AppRequest request = new AppRequest(view.getContext(), url, new AppHttpResListener() {
                        @Override
                        public void onSuccess(TransferObject data) {
                            collectModels.remove(collectModel);
                            favouriteNum.setText(collectModels.size()+"");
                            adapter.notifyDataSetChanged();
                        }
                    }, data);
                    request.execute();
                }
            }).show();
            return false;
        }
    };

    private View.OnClickListener onFavClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            CollectModel collectModel = (CollectModel) view.getTag();
            Intent intent = null;
            switch (collectModel.getStatus()){
                case "0":
                    intent = new Intent(context, TopicDetailsActivity.class);
                    FarmTopicModel farmTopicModel = new FarmTopicModel();
                    farmTopicModel.setFarmTopicAliasId(collectModel.getCollectAliasId());
                    farmTopicModel.setFarmTopicName(collectModel.getTitle());
                    intent.putExtra("farmTopic",farmTopicModel);
                    break;
                case "2":
                    intent = new Intent(context, TimingTopicDetailsActivity.class);
                    farmTopicModel = new FarmTopicModel();
                    farmTopicModel.setFarmTopicAliasId(collectModel.getCollectAliasId());
                    farmTopicModel.setFarmTopicName(collectModel.getTitle());
                    intent.putExtra("farmTopic",farmTopicModel);
                    break;
                case "1":
                    intent = new Intent(context, FarmDetailActivity.class);
                    FarmModel farmModel = new FarmModel();
                    farmModel.setFarmAliasId(collectModel.getCollectAliasId());
                    farmModel.setFarmName(collectModel.getTitle());
                    intent.putExtra("farmModel",farmModel);
                    break;
                case "3":
                    CommonUtil.showMessage(context,"已过期");
                    break;
            }
            if(intent != null)
                startActivity(intent);
        }
    };

    private String getFavTag(String type){
        switch (type){
            case "0":
                return "精选";
            case "1":
                return "农庄";
            case "2":
                return "抢购";
            case "3":
                return "过期";
        }
        return "";
    }

    private int getFavTagBg(String type){
        switch (type){
            case "0":
                return R.drawable.tag_n_bg;
            case "1":
                return R.drawable.tag_s_bg;
            case "2":
                return R.drawable.tag_n_bg;
            case "3":
                return R.drawable.tag_g_bg;
        }
        return 0;
    }

    @Override
    protected int getContentView() {
        return R.layout.frag_user_favo;
    }
}
