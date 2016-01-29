package com.egceo.app.myfarm.user.orderfragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baseandroid.BaseFragment;
import com.egceo.app.myfarm.R;
import com.egceo.app.myfarm.entity.OrderModel;
import com.egceo.app.myfarm.entity.TransferObject;
import com.egceo.app.myfarm.http.API;
import com.egceo.app.myfarm.http.AppHttpResListener;
import com.egceo.app.myfarm.http.AppRequest;
import com.egceo.app.myfarm.order.actvity.OrderDetailActivity;
import com.egceo.app.myfarm.order.actvity.SubmitRefundActivity;
import com.egceo.app.myfarm.util.AppUtil;

import java.util.ArrayList;
import java.util.List;


//已付款
public class PaidFragment extends BaseFragment {
    private RecyclerView paidList;
    private PaidAdapter adapter;
    private List<OrderModel> orderModels;
    @Override
    protected void initViews() {
        findViews();
        initData();
    }
    private void findViews() {
        paidList = (RecyclerView) this.findViewById(R.id.paid_list);
    }

    private void initData() {
        orderModels = new ArrayList<OrderModel>();
        adapter = new PaidAdapter();
        paidList.setLayoutManager(new LinearLayoutManager(context));
        paidList.setAdapter(adapter);
        loadDataFromServer();
    }

    private void loadDataFromServer() {
        String url = API.URL + API.API_URL.PERSON_ORDER;
        TransferObject data = AppUtil.getHttpData(context);
        data.setType(AppUtil.ordNC);
        data.setUserAliasId("aaa");
        AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                orderModels = data.getOrderModels();
                if (orderModels != null && orderModels.size() > 0) {
                    adapter.notifyDataSetChanged();
                }
            }

        }, data);
        request.execute();
    }

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
            holder.paidTime.setText(R.string.gening_order_pls_wait);
            holder.okBtn.setText(R.string.oking);
            holder.backOrder.setText(R.string.back_order_btn);
            holder.backOrder.setTag(orderModel);
            holder.backOrder.setOnClickListener(onBackClick);
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
        private TextView okBtn;
        private TextView backOrder;

        public PaidViewHolder(View itemView) {
            super(itemView);
            paidName = (TextView) itemView.findViewById(R.id.order_item_name);
            paidDesc = (TextView) itemView.findViewById(R.id.order_item_desc);
            paidPrice = (TextView) itemView.findViewById(R.id.order_item_price);
            paidTime = (TextView) itemView.findViewById(R.id.order_item_time);
            okBtn = (TextView) itemView.findViewById(R.id.order_item_btn1);
            backOrder = (TextView) itemView.findViewById(R.id.order_item_btn2);

        }
    }


    private View.OnClickListener onBackClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            OrderModel order = (OrderModel) view.getTag();
            Intent intent = new Intent(context, SubmitRefundActivity.class);
            intent.putExtra("order",order);
            startActivity(intent);
        }
    };


    private View.OnClickListener onOrderClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            OrderModel order = (OrderModel) view.getTag();
            Intent intent = new Intent(context, OrderDetailActivity.class);
            intent.putExtra("order",order);
            startActivity(intent);
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.frag_user_order_paid;
    }
}
