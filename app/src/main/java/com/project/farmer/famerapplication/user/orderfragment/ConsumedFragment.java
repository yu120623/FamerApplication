package com.project.farmer.famerapplication.user.orderfragment;

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
 * Created by gseoa on 2016/1/21.
 */
public class ConsumedFragment extends BaseFragment {
    private RecyclerView consumedList;
    private ConsumedAdapter adapter;
    private List<OrderModel> orderModels;

    @Override
    protected void initViews() {
        findViews();
        initData();
    }

    private void findViews() {
        consumedList = (RecyclerView) this.findViewById(R.id.consumed_list);
    }

    private void initData() {
        orderModels = new ArrayList<OrderModel>();
        adapter = new ConsumedAdapter();
        consumedList.setLayoutManager(new LinearLayoutManager(context));
        consumedList.setAdapter(adapter);
        loadDataFromServer();
    }

    private void loadDataFromServer() {
        String url = API.URL + API.API_URL.PERSON_ORDER;
        TransferObject data = AppUtil.getHttpData(context);
        data.setType("ordHC");
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

    class ConsumedAdapter extends RecyclerView.Adapter<ConsumedViewHolder> {
        @Override
        public ConsumedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = View.inflate(parent.getContext(), R.layout.item_consumed, null);
            ConsumedViewHolder holder = new ConsumedViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(ConsumedViewHolder holder, int position) {
            OrderModel orderModel = orderModels.get(position);
            holder.consumedName.setText(orderModel.getFarmSetName());
            holder.consumedDesc.setText(orderModel.getFarmSetDesc());
            holder.consumedPrice.setText(orderModel.getOrdePrice() + "元");
            SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制
            holder.consumedTime.setText(sdformat.format(orderModel.getRecordTime()) + "使用");
        }

        @Override
        public int getItemCount() {
            return orderModels.size();
        }
    }

    class ConsumedViewHolder extends RecyclerView.ViewHolder {
        private TextView consumedName;
        private TextView consumedDesc;
        private TextView consumedPrice;
        private TextView consumedTime;

        public ConsumedViewHolder(View itemView) {
            super(itemView);
            consumedName = (TextView) itemView.findViewById(R.id.consumed_farm_set_name);
            consumedDesc = (TextView) itemView.findViewById(R.id.consumed_farm_set_desc);
            consumedPrice = (TextView) itemView.findViewById(R.id.consumed_farm_set_price);
            consumedTime = (TextView) itemView.findViewById(R.id.consumed_farm_set_time);

        }
    }

    @Override
    protected int getContentView() {
        return R.layout.frag_user_order_consumed;
    }
}
