package com.egceo.app.myfarm.search.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baseandroid.BaseActivity;
import com.egceo.app.myfarm.R;
import com.egceo.app.myfarm.entity.SearchModel;
import com.egceo.app.myfarm.entity.TransferObject;
import com.egceo.app.myfarm.http.API;
import com.egceo.app.myfarm.http.AppHttpResListener;
import com.egceo.app.myfarm.http.AppRequest;
import com.egceo.app.myfarm.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gseoa on 2016/1/12.
 */
public class SearchResultsActivity extends BaseActivity {
    private RecyclerView searchResultsList;
    private TextView notFound;
    private List<SearchModel> searchModels;
    private SearchResultsAdapter adapter;
    private String key;
    private String type;

    @Override
    protected void initViews() {
        findViews();
        initDate();
    }

    private void findViews() {
        searchResultsList = (RecyclerView) findViewById(R.id.search_results);
        notFound = (TextView) findViewById(R.id.search_null);
    }

    private void initDate() {
        Intent intent = getIntent();
        key = intent.getStringExtra("key");
        type=intent.getStringExtra("type");
        searchModels = new ArrayList<SearchModel>();
        searchResultsList.setLayoutManager(new LinearLayoutManager(context));
        adapter = new SearchResultsAdapter();
        searchResultsList.setAdapter(adapter);
        loadDataFromServer();
    }

    private void loadDataFromServer() {
        String url = API.URL + API.API_URL.SEARCH_RESULT_LIST;
        TransferObject data = AppUtil.getHttpData(context);
        data.setPageNumber(0);
        data.setType(type);
        data.setCityCode("0512");
        data.setKey(key);
        AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                searchModels = data.getSearchModels();
                if (searchModels != null && searchModels.size() > 0) {
                    adapter.notifyDataSetChanged();
                } else {
                    searchResultsList.setVisibility(View.GONE);
                    notFound.setVisibility(View.VISIBLE);
                }
            }
        }, data);
        request.execute();
    }


    class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsViewHolder> {

        @Override
        public SearchResultsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = View.inflate(parent.getContext(), R.layout.item_search_result, null);
            v.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT));
            SearchResultsViewHolder holder = new SearchResultsViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(SearchResultsViewHolder holder, int position) {
            SearchModel searchModel = searchModels.get(position);
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

    @Override
    protected int getContentView() {
        return R.layout.activity_search_results;
    }

    @Override
    protected String setActionBarTitle() {
        return "关键字显示";
    }
}
