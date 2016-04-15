package com.egceo.app.myfarm.user.orderfragment;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baseandroid.BaseFragment;
import com.baseandroid.util.CommonUtil;
import com.cundong.recyclerview.EndlessRecyclerOnScrollListener;
import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.cundong.recyclerview.RecyclerViewUtils;
import com.egceo.app.myfarm.R;
import com.egceo.app.myfarm.entity.OrderModel;
import com.egceo.app.myfarm.entity.TransferObject;
import com.egceo.app.myfarm.http.API;
import com.egceo.app.myfarm.http.AppHttpResListener;
import com.egceo.app.myfarm.http.AppRequest;
import com.egceo.app.myfarm.loadmore.LoadMoreFooter;
import com.egceo.app.myfarm.order.actvity.OrderCodeActivity;
import com.egceo.app.myfarm.order.actvity.RefundOrderActivity;
import com.egceo.app.myfarm.order.actvity.OrderDetailActivity;
import com.egceo.app.myfarm.order.actvity.SubmitRefundActivity;
import com.egceo.app.myfarm.util.AppUtil;
import com.egceo.app.myfarm.view.CustomUIHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by gseoa on 2016/1/21.
 */
//代销费
public class UnConsumedFragment extends OrderBaseFragment {
    private RecyclerView paidList;
    private PaidAdapter adapter;
    private List<OrderModel> orderModels;
    private SimpleDateFormat simpleDateFormat;
    private PtrFrameLayout frameLayout;
    private LoadMoreFooter loadMoreFooter;
    private Integer pageNumber = 0;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter;

    @Override
    protected void initViews() {
        findViews();
        initData();
    }

    private void findViews() {
        paidList = (RecyclerView) this.findViewById(R.id.consumed_list);
        frameLayout = (PtrFrameLayout) this.findViewById(R.id.ptr);
    }

    private void initData() {
        orderModels = new ArrayList<OrderModel>();
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        adapter = new PaidAdapter();
        paidList.setLayoutManager(new LinearLayoutManager(context));
        loadMoreFooter = new LoadMoreFooter(context);
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        paidList.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        RecyclerViewUtils.setFooterView(paidList, loadMoreFooter.getFooter());
        paidList.addOnScrollListener(loadMoreListener);
        CustomUIHandler header = new CustomUIHandler(context);
        frameLayout.addPtrUIHandler(header);
        frameLayout.setHeaderView(header);
        frameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pageNumber = 0;
                paidList.removeOnScrollListener(loadMoreListener);
                paidList.addOnScrollListener(loadMoreListener);
                loadMoreFooter.reset();
                loadDataFromServer();
            }
        });
    }

    private void loadDataFromServer() {
        loadMoreFooter.setIsLoading(true);
        String url = API.URL + API.API_URL.PERSON_ORDER;
        TransferObject data = AppUtil.getHttpData(context);
        data.setType(AppUtil.ordHP);
        data.setPageNumber(pageNumber);
        AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                List<OrderModel> list = data.getOrderModels();
                if(pageNumber == 0){
                    if(list == null || list.size() <= 0) {
                        list = new ArrayList<>();
                        showNothing();
                    }
                    orderModels = list;
                }else{
                    if(list.size() > 0){
                        orderModels.addAll(list);
                    }else{
                        pageNumber--;
                        loadMoreFooter.showNoMoreTips();
                        paidList.removeOnScrollListener(loadMoreListener);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(com.egceo.app.myfarm.entity.Error error) {
                super.onFailed(error);
                showFailed();
            }

            @Override
            public void onEnd() {
                frameLayout.refreshComplete();
                loadMoreFooter.setIsLoading(false);
                loadMoreFooter.hideLoadMore();
            }
        }, data);
        request.execute();
    }

    private void showFailed() {
        orderModels.clear();
        adapter.notifyDataSetChanged();
        showRetryView();
    }

    @Override
    protected void retry() {
        hideRetryView();
        frameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                frameLayout.autoRefresh(true);
            }
        }, 100);
    }

    //加载更多监听
    private EndlessRecyclerOnScrollListener loadMoreListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            if (loadMoreFooter.isLoading()) return;
            pageNumber++;
            loadMoreFooter.showLoadingTips();
            loadDataFromServer();
        }
    };

    class PaidAdapter extends RecyclerView.Adapter<PaidViewHolder> {
        @Override
        public PaidViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = View.inflate(parent.getContext(), R.layout.item_order, null);
            PaidViewHolder holder = new PaidViewHolder(v);
            v.setOnClickListener(onOrderClick);
            return holder;
        }

        @Override
        public void onBindViewHolder(PaidViewHolder holder, int position) {
            OrderModel orderModel = orderModels.get(position);
            holder.itemView.setTag(orderModel);
            holder.paidName.setText(orderModel.getFarmSetModel().getFarmSetName());
            holder.paidDesc.setText(orderModel.getFarmSetModel().getFarmSetDesc());
            holder.paidPrice.setText(orderModel.getOrdePrice() + context.getString(R.string.rmb));
            holder.codeBtn.setText(R.string.order_code);
            holder.codeBtn.setTag(orderModel);
            holder.backOrder.setTag(orderModel);
            if ("ord_Y".equals(orderModel.getStatus())) {//使用中
                holder.backOrder.setText(R.string.using_order);
                holder.backOrder.setOnClickListener(null);
                holder.paidTime.setText(simpleDateFormat.format(orderModel.getRecordTime()) + " " + context.getString(R.string.use));
            } else {//未使用
                holder.backOrder.setText(R.string.back_order_btn);
                holder.paidTime.setText(simpleDateFormat.format(orderModel.getRecordTime()) + " " + context.getString(R.string.create));
                holder.backOrder.setOnClickListener(onBackClick);
            }
            holder.codeBtn.setOnClickListener(onCodeBtnClick);
        }

        @Override
        public int getItemCount() {
            return orderModels.size();
        }
    }

    public View.OnClickListener onCodeBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            OrderModel order = (OrderModel) view.getTag();
            Intent intent = new Intent(context, OrderCodeActivity.class);
            intent.putExtra("order", order);
            startActivity(intent);
        }
    };

    private View.OnClickListener onBackClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            OrderModel order = (OrderModel) view.getTag();
            Intent intent = new Intent(context, SubmitRefundActivity.class);
            intent.putExtra("order", order);
            startActivityForResult(intent,1);
        }
    };

    private View.OnClickListener onOrderClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            OrderModel order = (OrderModel) view.getTag();
            Intent intent = new Intent(context, OrderDetailActivity.class);
            intent.putExtra("order", order);
            startActivity(intent);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == 1){
                OrderModel order = (OrderModel) data.getSerializableExtra("order");
                for(int i = 0; i < orderModels.size();i++){
                    if(order.getOrderSn().equals(orderModels.get(i).getOrderSn())){
                        orderModels.remove(i);
                        adapter.notifyDataSetChanged();
                        break;
                    }
                }
                EventBus.getDefault().post(new Integer(4));
            }
        }
    }

    class PaidViewHolder extends RecyclerView.ViewHolder {
        private TextView paidName;
        private TextView paidDesc;
        private TextView paidPrice;
        private TextView paidTime;
        private TextView codeBtn;
        private TextView backOrder;

        public PaidViewHolder(View itemView) {
            super(itemView);
            paidName = (TextView) itemView.findViewById(R.id.order_item_name);
            paidDesc = (TextView) itemView.findViewById(R.id.order_item_desc);
            paidPrice = (TextView) itemView.findViewById(R.id.order_item_price);
            paidTime = (TextView) itemView.findViewById(R.id.order_item_time);
            codeBtn = (TextView) itemView.findViewById(R.id.order_item_btn1);
            backOrder = (TextView) itemView.findViewById(R.id.order_item_btn2);
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.frag_user_order_consumed;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(CommonUtil.isNetWorkConnected(context)) {
            frameLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    frameLayout.autoRefresh(true);
                }
            }, 1000);
        }
    }
}
