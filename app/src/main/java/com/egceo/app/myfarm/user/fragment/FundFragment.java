package com.egceo.app.myfarm.user.fragment;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baseandroid.BaseFragment;
import com.cundong.recyclerview.EndlessRecyclerOnScrollListener;
import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.cundong.recyclerview.RecyclerViewUtils;
import com.egceo.app.myfarm.R;
import com.egceo.app.myfarm.entity.*;
import com.egceo.app.myfarm.http.API;
import com.egceo.app.myfarm.http.AppHttpResListener;
import com.egceo.app.myfarm.http.AppRequest;
import com.egceo.app.myfarm.loadmore.LoadMoreFooter;
import com.egceo.app.myfarm.util.AppUtil;
import com.egceo.app.myfarm.view.CustomUIHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class FundFragment extends BaseFragment {
    private RecyclerView fundList;
    private TextView msgNum;
    private SimpleDateFormat dateFormat;
    private LoadMoreFooter loadMoreFooter;
    private Integer pageNumber = 0;
    private PtrFrameLayout frameLayout;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter;
    private FundAdapter adapter;
    private List<aa53458768RecordModel> recordModels = new ArrayList<>();

    @Override
    protected void initViews() {
        findViews();
        initData();
    }
    
    private void initData() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        adapter=new FundAdapter();
        fundList.setLayoutManager(new LinearLayoutManager(context));
        loadMoreFooter = new LoadMoreFooter(context);
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        fundList.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        RecyclerViewUtils.setFooterView(fundList, loadMoreFooter.getFooter());
        CustomUIHandler header = new CustomUIHandler(context);
        frameLayout.addPtrUIHandler(header);
        frameLayout.setHeaderView(header);
        frameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pageNumber = 0;
                fundList.removeOnScrollListener(loadMoreListener);
                fundList.addOnScrollListener(loadMoreListener);
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
        String url = API.URL + API.API_URL.FUND_LIST;
        TransferObject data = AppUtil.getHttpData(context);
        data.setPageNumber(pageNumber);
        data.setType("1");
        AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                msgNum.setText(data.getWalletModel().getLvValue()+"");
                List<aa53458768RecordModel> list = data.getAa53458768RecordModels();
                if(pageNumber == 0){
                    if(list == null) {
                        list = new ArrayList<>();
                        showNothing();
                    }
                    recordModels = list;
                }else{
                    if(list.size() > 0){
                        recordModels.addAll(list);
                    }else{
                        pageNumber--;
                        loadMoreFooter.showNoMoreTips();
                        fundList.removeOnScrollListener(loadMoreListener);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(com.egceo.app.myfarm.entity.Error error) {
                super.onFailed(error);
                //showRetryView();
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
        retryButton.setVisibility(View.GONE);
        retryImg.setImageResource(R.mipmap.no_data);
        retryText.setText(R.string.no_package);
    }

    class FundAdapter extends RecyclerView.Adapter<FundViewHolder> {
        @Override
        public FundViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = View.inflate(parent.getContext(), R.layout.item_fund, null);
            FundViewHolder holder = new FundViewHolder(v);
            RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,RecyclerView.LayoutParams.WRAP_CONTENT);
            v.setLayoutParams(layoutParams);
            return holder;
        }

        @Override
        public void onBindViewHolder(FundViewHolder holder, int position) {
            aa53458768RecordModel recordModel = recordModels.get(position);
            if(recordModel.getType().equals("0")){
                holder.fundName.setText(recordModel.getFarmSetModel().getFarmSetName());
                holder.fundName.setTextColor(Color.BLACK);
                holder.fundMoney.setTextColor(Color.BLACK);
                holder.fundMoney.setText("-"+recordModel.getValue());
            }else{
                holder.fundName.setText(R.string.gongxi_facai);
                holder.fundName.setTextColor(getResources().getColor(R.color.order_process_seleted_bg));
                holder.fundMoney.setTextColor(getResources().getColor(R.color.order_process_seleted_bg));
                holder.fundMoney.setText("+"+recordModel.getValue());
            }
            holder.fundTime.setText(dateFormat.format(recordModel.getCreatedTime()));
        }

        @Override
        public int getItemCount() {
            return recordModels.size();
        }
    }

    class FundViewHolder extends RecyclerView.ViewHolder {
        private TextView fundName;
        private TextView fundMoney;
        private TextView fundTime;
        public FundViewHolder(View itemView) {
            super(itemView);
            fundName = (TextView) itemView.findViewById(R.id.fund_name);
            fundMoney = (TextView) itemView.findViewById(R.id.fund_money);
            fundTime = (TextView) itemView.findViewById(R.id.fund_time);
        }
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


    private void findViews() {
        fundList = (RecyclerView) this.findViewById(R.id.fund_list);
        frameLayout = (PtrFrameLayout) this.findViewById(R.id.ptr);
        msgNum = (TextView) this.findViewById(R.id.user_fund);
    }

    @Override
    protected int getContentView() {
        return R.layout.frag_user_bank_card;
    }
}
