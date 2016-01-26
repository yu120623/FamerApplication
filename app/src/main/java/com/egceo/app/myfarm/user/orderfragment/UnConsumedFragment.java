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
import com.egceo.app.myfarm.util.AppUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gseoa on 2016/1/21.
 */
//代销费
public class UnConsumedFragment extends BaseFragment{
    private RecyclerView paidList;
    private PaidAdapter adapter;
    private List<OrderModel> orderModels;
    private SimpleDateFormat simpleDateFormat;
    @Override
    protected void initViews() {
        findViews();
        initData();
    }
    private void findViews() {
        paidList = (RecyclerView) this.findViewById(R.id.consumed_list);
    }

    private void initData() {
        orderModels = new ArrayList<OrderModel>();
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd mm:HH");
        adapter = new PaidAdapter();
        paidList.setLayoutManager(new LinearLayoutManager(context));
        paidList.setAdapter(adapter);
        loadDataFromServer();
    }

    private void loadDataFromServer() {
        String url = API.URL + API.API_URL.PERSON_ORDER;
        TransferObject data = AppUtil.getHttpData(context);
        data.setType(AppUtil.ordHP);
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
            PaidViewHolder holder = new PaidViewHolder(v);
            v.setOnClickListener(onOrderClick);
            return holder;
        }

        @Override
        public void onBindViewHolder(PaidViewHolder holder, int position) {
            OrderModel orderModel = orderModels.get(position);
            holder.itemView.setTag(orderModel);
            holder.paidName.setText(orderModel.getFarmSetName());
            holder.paidDesc.setText(orderModel.getFarmSetDesc());
            holder.paidPrice.setText(orderModel.getOrdePrice() + context.getString(R.string.rmb));
            holder.codeBtn.setText(R.string.order_code);
            if("1".equals(orderModel.getStatus())){
                holder.backOrder.setText(R.string.using_order);
                holder.paidTime.setText(simpleDateFormat.format(orderModel.getRecordTime())+" "+context.getString(R.string.use));
            }else{
                holder.backOrder.setText(R.string.back_order_btn);
                holder.paidTime.setText(simpleDateFormat.format(orderModel.getRecordTime())+" "+context.getString(R.string.create));
            }
        }

        @Override
        public int getItemCount() {
            return orderModels.size();
        }
    }

    private View.OnClickListener onOrderClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            OrderModel order = (OrderModel) view.getTag();
            Intent intent = new Intent(context, OrderDetailActivity.class);
            intent.putExtra("order",order);
            startActivity(intent);
        }
    };

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
}
