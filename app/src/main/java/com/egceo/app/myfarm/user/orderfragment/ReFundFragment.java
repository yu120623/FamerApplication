package com.egceo.app.myfarm.user.orderfragment;

import android.content.Intent;
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
import com.egceo.app.myfarm.entity.OrderModel;
import com.egceo.app.myfarm.entity.TransferObject;
import com.egceo.app.myfarm.http.API;
import com.egceo.app.myfarm.http.AppHttpResListener;
import com.egceo.app.myfarm.http.AppRequest;
import com.egceo.app.myfarm.loadmore.LoadMoreFooter;
import com.egceo.app.myfarm.order.actvity.RefundOrderActivity;
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
// 退单
public class ReFundFragment extends BaseFragment {
    private RecyclerView refundList;
    private PaidAdapter adapter;
    private List<OrderModel> orderModels;
    private SimpleDateFormat dateFormat;
    private LoadMoreFooter loadMoreFooter;
    private Integer pageNumber = 0;
    private PtrFrameLayout frameLayout;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter;
    @Override
    protected void initViews() {
        findViews();
        initData();
    }
    private void findViews() {
        refundList = (RecyclerView) this.findViewById(R.id.refund_list);
        frameLayout = (PtrFrameLayout) this.findViewById(R.id.ptr);
    }

    private void initData() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        orderModels = new ArrayList<OrderModel>();
        adapter = new PaidAdapter();
        refundList.setLayoutManager(new LinearLayoutManager(context));
        loadMoreFooter = new LoadMoreFooter(context);
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        refundList.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        RecyclerViewUtils.setFooterView(refundList,loadMoreFooter.getFooter());
        refundList.addOnScrollListener(loadMoreListener);
        CustomUIHandler header = new CustomUIHandler(context);
        frameLayout.addPtrUIHandler(header);
        frameLayout.setHeaderView(header);
        frameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pageNumber = 0;
                loadDataFromServer();
            }
        });
        frameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                frameLayout.autoRefresh(true);
            }
        },100);
    }

    private void loadDataFromServer() {
        String url = API.URL + API.API_URL.PERSON_ORDER;
        TransferObject data = AppUtil.getHttpData(context);
        data.setType(AppUtil.ordRB);
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
                frameLayout.refreshComplete();
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

    class PaidAdapter extends RecyclerView.Adapter<PaidViewHolder> {
        @Override
        public PaidViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = View.inflate(parent.getContext(), R.layout.item_order, null);
            v.setOnClickListener(onOrderClick);
            PaidViewHolder holder = new PaidViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(PaidViewHolder holder, int position) {
            OrderModel orderModel = orderModels.get(position);
            holder.itemView.setTag(orderModel);
            holder.paidName.setText(orderModel.getFarmSetModel().getFarmSetName());
            holder.paidDesc.setText(orderModel.getFarmSetModel().getFarmSetDesc());
            holder.paidPrice.setText(orderModel.getOrdePrice() + context.getString(R.string.rmb));
            holder.paidTime.setTextColor(context.getResources().getColor(R.color.green2));
            String orderText = "";
            if(orderModel.getStatus().equals(AppUtil.rqAGR)){//同意
                holder.btn1.setText(R.string.cancel);
                holder.btn1.setBackgroundResource(R.drawable.unpaid_d_btn_bg);
                holder.btn1.setTextColor(context.getResources().getColor(R.color.gray));
                orderText = context.getString(R.string.refunding);
            }else if(orderModel.getStatus().equals(AppUtil.rqAPY)){//申请
                holder.btn1.setText(R.string.cancel);
                holder.btn1.setBackgroundResource(R.drawable.unpaid_d_btn_bg);
                holder.btn1.setTextColor(context.getResources().getColor(R.color.gray));
                orderText = context.getString(R.string.already_aply);
            }else if(orderModel.getStatus().equals(AppUtil.reREF)){//拒绝
                holder.btn1.setText(R.string.del);
                holder.btn1.setBackgroundResource(R.drawable.unpaid_p_btn_bg);
                holder.btn1.setTextColor(context.getResources().getColor(R.color.white));
                orderText = context.getString(R.string.refund_failed);
                holder.paidTime.setTextColor(context.getResources().getColor(R.color.order_process_seleted_bg));
            }else if(orderModel.getStatus().equals(AppUtil.rqCOM)){//完成
                holder.btn1.setText(R.string.del);
                holder.btn1.setBackgroundResource(R.drawable.unpaid_p_btn_bg);
                holder.btn1.setTextColor(context.getResources().getColor(R.color.white));
                orderText = context.getString(R.string.already_refund);
            }
            holder.paidTime.setText(dateFormat.format(orderModel.getRecordTime())+orderText);
            holder.btn2.setVisibility(View.GONE);
        }

        @Override
        public int getItemCount() {
            return orderModels.size();
        }
    }

    class PaidViewHolder extends RecyclerView.ViewHolder {
        private TextView paidName;
        private TextView paidDesc;
        private TextView paidPrice;
        private TextView paidTime;
        private TextView btn1;
        private TextView btn2;

        public PaidViewHolder(View itemView) {
            super(itemView);
            paidName = (TextView) itemView.findViewById(R.id.order_item_name);
            paidDesc = (TextView) itemView.findViewById(R.id.order_item_desc);
            paidPrice = (TextView) itemView.findViewById(R.id.order_item_price);
            paidTime = (TextView) itemView.findViewById(R.id.order_item_time);
            btn1 = (TextView) itemView.findViewById(R.id.order_item_btn1);
            btn2 = (TextView) itemView.findViewById(R.id.order_item_btn2);

        }
    }

    private View.OnClickListener onOrderClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            OrderModel order = (OrderModel) view.getTag();
            Intent intent = new Intent(context, RefundOrderActivity.class);
            intent.putExtra("order",order);
            startActivity(intent);
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.frag_user_order_refund;
    }
}
