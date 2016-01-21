package com.project.farmer.famerapplication.user.orderfragment;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.project.farmer.famerapplication.loadmore.LoadMoreFooter;
import com.project.farmer.famerapplication.util.AppUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by gseoa on 2016/1/14.
 */
public class UnPaidFragment extends BaseFragment {
    private RecyclerView unPaidList;
    private UnPaidAdapter adapter;
    private List<OrderModel> orderModels;
    private LoadMoreFooter loadMoreFooter;
    private Integer pageNumber = 0;

    @Override
    protected void initViews() {
        findViews();
        initData();
    }

    private void findViews() {
        unPaidList = (RecyclerView) this.findViewById(R.id.unpaid_list);
    }

    private void initData() {
        orderModels = new ArrayList<OrderModel>();
        adapter = new UnPaidAdapter();
        unPaidList.setLayoutManager(new LinearLayoutManager(context));
        unPaidList.setAdapter(adapter);
        loadDataFromServer();
    }

    private void loadDataFromServer() {
        String url = API.URL + API.API_URL.PERSON_ORDER;
        TransferObject data = AppUtil.getHttpData(context);
        data.setType("ordNP");
        data.setUserAliasId("aaa");
        data.setPageNumber(0);
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


    class UnPaidAdapter extends RecyclerView.Adapter<UnPaidViewHolder> {
        @Override
        public UnPaidViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = View.inflate(parent.getContext(), R.layout.item_unpaid, null);
            UnPaidViewHolder holder = new UnPaidViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(UnPaidViewHolder holder, int position) {
            OrderModel orderModel = orderModels.get(position);
            holder.unPaidName.setText(orderModel.getFarmSetName());
            holder.unPaidDesc.setText(orderModel.getFarmSetDesc());
            holder.unPaidPrice.setText(orderModel.getOrdePrice() + "元");
            SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制
            holder.payBtn.setText("去支付");
            holder.payBtn.setEnabled(true);
            holder.unPaidTime.setTextColor(Color.rgb(157, 206, 133));
            if ("ordPD".equals(orderModel.getStatus())) {
                holder.payBtn.setText("已失败");
                holder.payBtn.setEnabled(false);
                holder.unPaidTime.setTextColor(Color.rgb(146, 146, 146));
                holder.unPaidTime.setText("订单已失效");
            } else {
                if (orderModel.getRecordTime() != null)
                    holder.unPaidTime.setText(sdformat.format(orderModel.getRecordTime()) + "生成订单");
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

        public UnPaidViewHolder(View itemView) {
            super(itemView);
            unPaidName = (TextView) itemView.findViewById(R.id.unpaid_farm_set_name);
            unPaidDesc = (TextView) itemView.findViewById(R.id.unpaid_farm_set_desc);
            unPaidPrice = (TextView) itemView.findViewById(R.id.unpaid_farm_set_price);
            unPaidTime = (TextView) itemView.findViewById(R.id.unpaid_farm_set_time);
            payBtn = (TextView) itemView.findViewById(R.id.pay_btn);

        }
    }

    @Override
    protected int getContentView() {
        return R.layout.frag_user_order_unpaid;
    }
}
