package com.egceo.app.myfarm.user.orderfragment;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.egceo.app.myfarm.comment.SendCommentActivity;
import com.egceo.app.myfarm.entity.OrderModel;
import com.egceo.app.myfarm.entity.TransferObject;
import com.egceo.app.myfarm.http.API;
import com.egceo.app.myfarm.http.AppHttpResListener;
import com.egceo.app.myfarm.http.AppRequest;
import com.egceo.app.myfarm.loadmore.LoadMoreFooter;
import com.egceo.app.myfarm.order.actvity.OrderDetailActivity;
import com.egceo.app.myfarm.util.AppUtil;
import com.egceo.app.myfarm.view.CustomUIHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by gseoa on 2016/1/21.
 */
//已消费
public class ConsumedFragment extends BaseFragment {
    private RecyclerView consumedList;
    private ConsumedAdapter adapter;
    private List<OrderModel> orderModels;
    private SimpleDateFormat sdformat;
    private PtrFrameLayout frameLayout;
    private LoadMoreFooter loadMoreFooter;
    private Integer pageNumber = 0;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter;
    @Override
    protected void initViews() {
        findViews();
        initData();
    }

    private void initData() {
        sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制
        orderModels = new ArrayList<OrderModel>();
        adapter = new ConsumedAdapter();
        consumedList.setLayoutManager(new LinearLayoutManager(context));
        loadMoreFooter = new LoadMoreFooter(context);
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        consumedList.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        RecyclerViewUtils.setFooterView(consumedList,loadMoreFooter.getFooter());
        consumedList.addOnScrollListener(loadMoreListener);
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
        data.setType(AppUtil.ordHC);
        data.setPageNumber(pageNumber);
        AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                List<OrderModel> list = data.getOrderModels();
                if(pageNumber == 0){
                    if(list == null)
                        list = new ArrayList<>();
                    orderModels = list;
                }else{
                    if(list.size() > 0){
                        orderModels.addAll(list);
                    }else{
                        pageNumber--;
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

    class ConsumedAdapter extends RecyclerView.Adapter<ConsumedViewHolder> {
        @Override
        public ConsumedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = View.inflate(parent.getContext(), R.layout.item_order, null);
            v.setOnClickListener(onOrderClick);
            ConsumedViewHolder holder = new ConsumedViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(ConsumedViewHolder holder, int position) {
            OrderModel orderModel = orderModels.get(position);
            holder.itemView.setTag(orderModel);
            holder.consumedName.setText(orderModel.getFarmSetModel().getFarmSetName());
            holder.consumedDesc.setText(orderModel.getFarmSetModel().getFarmSetDesc());
            holder.consumedPrice.setText(orderModel.getOrdePrice() + "元");
            holder.consumedTime.setText(sdformat.format(orderModel.getRecordTime()) + context.getString(R.string.use));
            holder.commentBtn.setText(R.string.comment);
            holder.commentBtn.setTag(orderModel);
            holder.delBtn.setText(R.string.del);
            holder.delBtn.setTag(orderModel);
            holder.delBtn.setOnClickListener(onDelClick);
            holder.commentBtn.setOnClickListener(onCommentClick);
        }

        @Override
        public int getItemCount() {
            return orderModels.size();
        }
    }

    private View.OnClickListener onCommentClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            OrderModel order = (OrderModel) view.getTag();
            Intent intent = new Intent(context, SendCommentActivity.class);
            intent.putExtra("order", order);
            startActivity(intent);
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
                if(data.getMessage().getStatus().equals("00000")){
                    CommonUtil.showMessage(context,context.getString(R.string.del_success));
                    orderModels.remove(order);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onEnd() {
                super.onEnd();
                CommonUtil.dismissSimpleProgressDialog();
            }
        },data);
        request.execute();
    }

    class ConsumedViewHolder extends RecyclerView.ViewHolder {
        private TextView consumedName;
        private TextView consumedDesc;
        private TextView consumedPrice;
        private TextView consumedTime;
        private TextView commentBtn;
        private TextView delBtn;
        public ConsumedViewHolder(View itemView) {
            super(itemView);
            consumedName = (TextView) itemView.findViewById(R.id.order_item_name);
            consumedDesc = (TextView) itemView.findViewById(R.id.order_item_desc);
            consumedPrice = (TextView) itemView.findViewById(R.id.order_item_price);
            consumedTime = (TextView) itemView.findViewById(R.id.order_item_time);
            commentBtn = (TextView) itemView.findViewById(R.id.order_item_btn1);
            delBtn = (TextView) itemView.findViewById(R.id.order_item_btn2);
        }
    }

    private void findViews() {
        consumedList = (RecyclerView) this.findViewById(R.id.consumed_list);
        frameLayout = (PtrFrameLayout) this.findViewById(R.id.ptr);
    }

    @Override
    protected int getContentView() {
        return R.layout.frag_user_order_consumed;
    }
}
