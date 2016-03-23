package com.egceo.app.myfarm.search.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baseandroid.BaseActivity;
import com.cundong.recyclerview.EndlessRecyclerOnScrollListener;
import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.cundong.recyclerview.RecyclerViewUtils;
import com.egceo.app.myfarm.R;
import com.egceo.app.myfarm.entity.FarmModel;
import com.egceo.app.myfarm.entity.FarmTopicModel;
import com.egceo.app.myfarm.entity.SearchModel;
import com.egceo.app.myfarm.entity.TransferObject;
import com.egceo.app.myfarm.farm.activity.FarmDetailActivity;
import com.egceo.app.myfarm.http.API;
import com.egceo.app.myfarm.http.AppHttpResListener;
import com.egceo.app.myfarm.http.AppRequest;
import com.egceo.app.myfarm.loadmore.LoadMoreFooter;
import com.egceo.app.myfarm.topic.activity.TimingTopicDetailsActivity;
import com.egceo.app.myfarm.topic.activity.TopicDetailsActivity;
import com.egceo.app.myfarm.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gseoa on 2016/1/12.
 */
public class SearchResultsActivity extends BaseActivity {
    private RecyclerView searchResultsList;
    private View notFound;
    private List<SearchModel> searchModels;
    private SearchResultsAdapter adapter;
    private String key;
    private String type;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter = null;
    private LoadMoreFooter loadMoreFooter;
    private Integer pageNumber = 0;
    @Override
    protected void initViews() {
        findViews();
        initDate();
    }

    private void findViews() {
        searchResultsList = (RecyclerView) findViewById(R.id.search_results);
        notFound = (View) findViewById(R.id.search_null);
    }

    private void initDate() {
        Intent intent = getIntent();
        key = intent.getStringExtra("key");
        type=intent.getStringExtra("type");
        searchModels = new ArrayList<SearchModel>();
        searchResultsList.setLayoutManager(new LinearLayoutManager(context));
        adapter = new SearchResultsAdapter();
        loadMoreFooter = new LoadMoreFooter(context);
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        searchResultsList.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        RecyclerViewUtils.setFooterView(searchResultsList, loadMoreFooter.getFooter());
        loadDataFromServer();
        loadMoreFooter.setIsLoading(true);
        searchResultsList.addOnScrollListener(loadMoreListener);
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

    private void loadDataFromServer() {
        if(loadMoreFooter.isLoading())
            return;
        String url = API.URL + API.API_URL.SEARCH_RESULT_LIST;
        TransferObject data = AppUtil.getHttpData(context);
        data.setPageNumber(pageNumber);
        data.setType(type);
        data.setKey(key);
        AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {

                List<SearchModel> list = data.getSearchModels();
                if (list != null && list.size() > 0) {
                    if(pageNumber == 0) {
                        searchModels = list;
                    }else{
                        searchModels.addAll(list);
                    }
                    adapter.notifyDataSetChanged();
                }else{
                    if(pageNumber > 0) {
                        pageNumber--;
                        loadMoreFooter.showNoMoreTips();
                        searchResultsList.removeOnScrollListener(loadMoreListener);
                    }else{
                        searchResultsList.setVisibility(View.GONE);
                        notFound.setVisibility(View.VISIBLE);
                    }
                }
            }
            @Override
            public void onEnd() {
                loadMoreFooter.setIsLoading(false);
                loadMoreFooter.hideLoadMore();
            }
        }, data);
        request.execute();
    }


    class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsViewHolder> {

        @Override
        public SearchResultsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = View.inflate(parent.getContext(), R.layout.item_search_result, null);
            v.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT));
            v.setOnClickListener(onSearchResultClick);
            SearchResultsViewHolder holder = new SearchResultsViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(SearchResultsViewHolder holder, int position) {
            SearchModel searchModel = searchModels.get(position);
            holder.itemView.setTag(searchModel);
            holder.resultName.setText(searchModel.getSearchTitle());
            holder.resultDesc.setText(searchModel.getSearchDesc());
            holder.resultTag.setText(AppUtil.getSearchTag(searchModel.getSearchType()));
            holder.resultTag.setBackgroundResource(AppUtil.getSearchTagBg(searchModel.getSearchType()));
        }

        @Override
        public int getItemCount() {
            return searchModels.size();
        }
    }

    class SearchResultsViewHolder extends RecyclerView.ViewHolder {
        private TextView resultName;
        private TextView resultDesc;
        private TextView resultTag;

        public SearchResultsViewHolder(View itemView) {
            super(itemView);
            resultTag = (TextView) itemView.findViewById(R.id.search_results_tag);
            resultName = (TextView) itemView.findViewById(R.id.search_results_name);
            resultDesc = (TextView) itemView.findViewById(R.id.search_results_desc);
        }
    }

    private View.OnClickListener onSearchResultClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SearchModel searchModel = (SearchModel) view.getTag();
            Intent intent = null;
            if(searchModel.getSearchType().equals("p")){
                intent = new Intent(context, TopicDetailsActivity.class);
                FarmTopicModel farmTopicModel = new FarmTopicModel();
                farmTopicModel.setFarmTopicAliasId(searchModel.getSearchId());
                farmTopicModel.setFarmTopicName(searchModel.getSearchTitle());
                intent.putExtra("farmTopic",farmTopicModel);
            }else if(searchModel.getSearchType().equals("n")){
                intent = new Intent(context, TimingTopicDetailsActivity.class);
                FarmTopicModel farmTopicModel = new FarmTopicModel();
                farmTopicModel.setFarmTopicName(searchModel.getSearchTitle());
                farmTopicModel.setFarmTopicAliasId(searchModel.getSearchId());
                intent.putExtra("farmTopic",farmTopicModel);
            }else if(searchModel.getSearchType().equals("s")){
                intent = new Intent(context, FarmDetailActivity.class);
                FarmModel farmModel = new FarmModel();
                farmModel.setFarmName(searchModel.getSearchTitle());
                farmModel.setFarmAliasId(searchModel.getSearchId());
                intent.putExtra("farmModel",farmModel);
            }
            startActivity(intent);
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.activity_search_results;
    }

    @Override
    protected String setActionBarTitle() {
        return "关键字显示";
    }
}
