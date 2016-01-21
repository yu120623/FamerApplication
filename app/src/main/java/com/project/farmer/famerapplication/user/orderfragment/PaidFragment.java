package com.project.farmer.famerapplication.user.orderfragment;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baseandroid.BaseFragment;
import com.project.farmer.famerapplication.R;
import com.project.farmer.famerapplication.entity.OrderModel;
import com.project.farmer.famerapplication.entity.TransferObject;
import com.project.farmer.famerapplication.http.API;
import com.project.farmer.famerapplication.http.AppHttpResListener;
import com.project.farmer.famerapplication.http.AppRequest;
import com.project.farmer.famerapplication.util.AppUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gseoa on 2016/1/14.
 */
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
        data.setType("ordNC");
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
            View v = View.inflate(parent.getContext(), R.layout.item_paid, null);
            PaidViewHolder holder = new PaidViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(PaidViewHolder holder, int position) {
            OrderModel orderModel = orderModels.get(position);
            holder.paidName.setText(orderModel.getFarmSetName());
            holder.paidDesc.setText(orderModel.getFarmSetDesc());
            holder.paidPrice.setText(orderModel.getOrdePrice() + "元");
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

        public PaidViewHolder(View itemView) {
            super(itemView);
            paidName = (TextView) itemView.findViewById(R.id.paid_farm_set_name);
            paidDesc = (TextView) itemView.findViewById(R.id.paid_farm_set_desc);
            paidPrice = (TextView) itemView.findViewById(R.id.paid_farm_set_price);

        }
    }

    @Override
    protected int getContentView() {
        return R.layout.frag_user_order_paid;
    }
}
