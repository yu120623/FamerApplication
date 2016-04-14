package com.egceo.app.myfarm.user.orderfragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
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
import com.egceo.app.myfarm.order.actvity.OrderChoosePayActivity;
import com.egceo.app.myfarm.order.actvity.OrderDetailActivity;
import com.egceo.app.myfarm.util.AppUtil;
import com.egceo.app.myfarm.view.CustomUIHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

//未付款
public class UnPaidFragment extends OrderBaseFragment {
    private RecyclerView unPaidList;
    private UnPaidAdapter adapter;
    private List<OrderModel> orderModels;
    private LoadMoreFooter loadMoreFooter;
    private Integer pageNumber = 0;
    private SimpleDateFormat sdformat;
    private PtrFrameLayout frameLayout;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter;
    @Override
    protected void initViews() {
        findViews();
        initData();
    }

    private void findViews() {
        unPaidList = (RecyclerView) this.findViewById(R.id.unpaid_list);
        frameLayout = (PtrFrameLayout) this.findViewById(R.id.ptr);
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
        CustomUIHandler header = new CustomUIHandler(context);
        frameLayout.addPtrUIHandler(header);
        frameLayout.setHeaderView(header);
        frameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pageNumber = 0;
                unPaidList.removeOnScrollListener(loadMoreListener);
                unPaidList.addOnScrollListener(loadMoreListener);
                loadMoreFooter.reset();
                loadDataFromServer();
            }
        });
    }

    private void loadDataFromServer() {
        loadMoreFooter.setIsLoading(true);
        String url = API.URL + API.API_URL.PERSON_ORDER;
        TransferObject data = AppUtil.getHttpData(context);
        data.setType(AppUtil.ordNP);
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
                        unPaidList.removeOnScrollListener(loadMoreListener);
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
            v.setOnClickListener(onOrderClick);
            UnPaidViewHolder holder = new UnPaidViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(UnPaidViewHolder holder, int position) {
            OrderModel orderModel = orderModels.get(position);
            holder.itemView.setTag(orderModel);
            holder.unPaidName.setText(orderModel.getFarmSetModel().getFarmSetName());
            holder.unPaidDesc.setText(orderModel.getFarmSetModel().getFarmSetDesc());
            holder.unPaidPrice.setText(orderModel.getOrdePrice() + context.getString(R.string.rmb));
            holder.unPaidTime.setTextColor(Color.rgb(157, 206, 133));
            holder.delBtn.setText(R.string.del);
            holder.delBtn.setTag(orderModel);
            holder.delBtn.setOnClickListener(onDelClick);
            if (AppUtil.ordPD.equals(orderModel.getStatus())) {
                holder.payBtn.setText(R.string.failed);
                holder.payBtn.setEnabled(false);
                holder.unPaidTime.setTextColor(Color.rgb(146, 146, 146));
                holder.unPaidTime.setText(R.string.order_status_overdue);
                holder.payBtn.setOnClickListener(null);
            } else {
                holder.payBtn.setText(R.string.go_pay);
                holder.payBtn.setEnabled(true);
                if (orderModel.getRecordTime() != null)
                    holder.unPaidTime.setText(sdformat.format(orderModel.getRecordTime()) + context.getString(R.string.gen_order_text));
                holder.payBtn.setOnClickListener(onGoToPayClick);
                holder.payBtn.setTag(orderModel);
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

    //删除订单
    private View.OnClickListener onDelClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final OrderModel order = (OrderModel) view.getTag();
            new AlertDialog.Builder(activity)
                    .setTitle(context.getString(R.string.hint))
                    .setMessage(context.getString(R.string.del_order_hint))
            .setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    delOrder(order);
                }
            }).show();
        }
    };

    private void delOrder(final OrderModel order) {
        CommonUtil.showSimpleProgressDialog(context.getString(R.string.deleting_order_hint),activity);
        String url = API.URL + API.API_URL.DEL_ORDER;
        TransferObject data = AppUtil.getHttpData(context);
        data.setOrderSn(order.getOrderSn());
        AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                CommonUtil.showMessage(context,context.getString(R.string.del_success));
                orderModels.remove(order);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onEnd() {
                super.onEnd();
                CommonUtil.dismissSimpleProgressDialog();
            }
        },data);
        request.execute();
    }

    //订单详细
    private View.OnClickListener onOrderClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            OrderModel order = (OrderModel) view.getTag();
            Intent intent = new Intent(context, OrderDetailActivity.class);
            intent.putExtra("order",order);
            startActivity(intent);
        }
    };

    //去支付
    private View.OnClickListener onGoToPayClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            OrderModel orderModel = (OrderModel) view.getTag();
            Intent intent = new Intent(context, OrderChoosePayActivity.class);
            intent.putExtra("order",orderModel);
            startActivity(intent);
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.frag_user_order_unpaid;
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
            }, 100);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
