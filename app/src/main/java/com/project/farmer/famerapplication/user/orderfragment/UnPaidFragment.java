package com.project.farmer.famerapplication.user.orderfragment;

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
import com.project.farmer.famerapplication.R;
import com.project.farmer.famerapplication.entity.OrderModel;
import com.project.farmer.famerapplication.entity.TransferObject;
import com.project.farmer.famerapplication.http.API;
import com.project.farmer.famerapplication.http.AppHttpResListener;
import com.project.farmer.famerapplication.http.AppRequest;
import com.project.farmer.famerapplication.loadmore.LoadMoreFooter;
import com.project.farmer.famerapplication.util.AppUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

//未付款
public class UnPaidFragment extends BaseFragment {
    private RecyclerView unPaidList;
    private UnPaidAdapter adapter;
    private List<OrderModel> orderModels;
    private LoadMoreFooter loadMoreFooter;
    private Integer pageNumber = 0;
    private SimpleDateFormat sdformat;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter;


    @Override
    protected void initViews() {
        findViews();
        initData();
    }

    private void findViews() {
        unPaidList = (RecyclerView) this.findViewById(R.id.unpaid_list);
    }

    private void initData() {
        sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制
        orderModels = new ArrayList<OrderModel>();
        adapter = new UnPaidAdapter();
        unPaidList.setLayoutManager(new LinearLayoutManager(context));
        loadMoreFooter = new LoadMoreFooter(context);
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        unPaidList.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        RecyclerViewUtils.setFooterView(unPaidList,loadMoreFooter.getFooter());
        unPaidList.addOnScrollListener(loadMoreListener);
        loadDataFromServer();
    }

    private void loadDataFromServer() {
        loadMoreFooter.setIsLoading(true);
        String url = API.URL + API.API_URL.PERSON_ORDER;
        TransferObject data = AppUtil.getHttpData(context);
        data.setType(AppUtil.ordNP);
        data.setUserAliasId("aaa");
        data.setPageNumber(pageNumber);
        AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                List<OrderModel> list = data.getOrderModels();
                if (list != null && list.size() > 0) {
                    if(pageNumber == 0) {
                        orderModels = list;
                    }else{
                        orderModels.addAll(list);
                    }
                    adapter.notifyDataSetChanged();
                }else{
                    if(pageNumber > 0)
                        pageNumber--;
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

    class UnPaidAdapter extends RecyclerView.Adapter<UnPaidViewHolder> {
        @Override
        public UnPaidViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = View.inflate(parent.getContext(), R.layout.item_order, null);
            UnPaidViewHolder holder = new UnPaidViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(UnPaidViewHolder holder, int position) {
            OrderModel orderModel = orderModels.get(position);
            holder.unPaidName.setText(orderModel.getFarmSetName());
            holder.unPaidDesc.setText(orderModel.getFarmSetDesc());
            holder.unPaidPrice.setText(orderModel.getOrdePrice() + context.getString(R.string.rmb));
            holder.payBtn.setText(R.string.go_pay);
            holder.payBtn.setEnabled(true);
            holder.unPaidTime.setTextColor(Color.rgb(157, 206, 133));
            holder.delBtn.setText(R.string.del);
            if (AppUtil.ordPD.equals(orderModel.getStatus())) {
                holder.payBtn.setText(R.string.failed);
                holder.payBtn.setEnabled(false);
                holder.unPaidTime.setTextColor(Color.rgb(146, 146, 146));
                holder.unPaidTime.setText(R.string.order_status_overdue);
            } else {
                if (orderModel.getRecordTime() != null)
                    holder.unPaidTime.setText(sdformat.format(orderModel.getRecordTime()) + context.getString(R.string.gen_order_text));
            }
        }

        @Override
        public int getItemCount() {
            return orderModels.size();
        }
    }

    class UnPaidViewHolder extends RecyclerView.ViewHolder {
        private TextView unPaidName;
        private TextView unPaidDesc;
        private TextView unPaidPrice;
        private TextView unPaidTime;
        private TextView payBtn;
        private TextView delBtn;

        public UnPaidViewHolder(View itemView) {
            super(itemView);
            unPaidName = (TextView) itemView.findViewById(R.id.order_item_name);
            unPaidDesc = (TextView) itemView.findViewById(R.id.order_item_desc);
            unPaidPrice = (TextView) itemView.findViewById(R.id.order_item_price);
            unPaidTime = (TextView) itemView.findViewById(R.id.order_item_time);
            payBtn = (TextView) itemView.findViewById(R.id.order_item_btn1);
            delBtn = (TextView) itemView.findViewById(R.id.order_item_btn2);
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.frag_user_order_unpaid;
    }
}
