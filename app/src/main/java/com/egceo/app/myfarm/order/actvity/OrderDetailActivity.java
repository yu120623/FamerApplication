package com.egceo.app.myfarm.order.actvity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baseandroid.BaseActivity;
import com.baseandroid.util.ImageLoaderUtil;
import com.egceo.app.myfarm.R;
import com.egceo.app.myfarm.entity.FarmItemsModel;
import com.egceo.app.myfarm.entity.OrderModel;
import com.egceo.app.myfarm.entity.TransferObject;
import com.egceo.app.myfarm.http.API;
import com.egceo.app.myfarm.http.AppHttpResListener;
import com.egceo.app.myfarm.http.AppRequest;
import com.egceo.app.myfarm.util.AppUtil;

import java.text.SimpleDateFormat;

public class OrderDetailActivity extends BaseActivity {
    private LinearLayout farmSetItemList;
    private TextView orderSN;
    private TextView buyNum;
    private TextView orderMoney;
    private OrderModel order;
    private View currentItemView;
    private SimpleDateFormat dataFormat;
    private TextView contactName;
    private TextView farmFund;
    private Button orderBtn;
    private View orderBtnLayout;
    @Override
    protected void initViews() {
        showProgress();
        findViews();
        initData();
        loadDataFromServer();
    }

    private void initData() {
        order = (OrderModel) this.getIntent().getSerializableExtra("order");
        dataFormat = new SimpleDateFormat("yyyy-MM-dd");

    }

    private void findViews() {
        farmSetItemList = (LinearLayout) this.findViewById(R.id.farm_set_item_content);
        orderSN = (TextView) this.findViewById(R.id.order_sn);
        buyNum = (TextView) this.findViewById(R.id.buy_num);
        orderMoney = (TextView) this.findViewById(R.id.order_total_money);
        farmFund = (TextView) this.findViewById(R.id.fund);
        contactName = (TextView) this.findViewById(R.id.contact_name);
        orderBtn = (Button) this.findViewById(R.id.order_btn);
        orderBtnLayout = this.findViewById(R.id.order_btn_layout);
    }

    private void loadDataFromServer() {
        String url = API.URL + API.API_URL.ORDER_INFO;
        TransferObject data = AppUtil.getHttpData(context);
        data.setOrderSn(order.getOrderSn());
        AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                if(data.getOrderModel() != null){
                    showOrderInfo(data.getOrderModel());
                }
            }

            @Override
            public void onEnd() {
                super.onEnd();
                hideProgress();
            }
        },data);
        request.execute();
    }

    private void showOrderInfo(OrderModel orderModel) {
        orderSN.setText(orderModel.getOrderSn());
        orderMoney.setText(orderModel.getOrdePrice()+"×"+orderModel.getCopies());
        buyNum.setText(orderModel.getCopies()+"");
        farmFund.setText(orderModel.getFund()+"");
        contactName.setText(orderModel.getContact().getConnectName());
        for(FarmItemsModel farmItemsModel : orderModel.getFarmItemsModels()){
            getFarmSetSubItem(farmItemsModel);
        }
        if(AppUtil.ordNP.equals(orderModel.getStatus())){
            orderBtnLayout.setVisibility(View.VISIBLE);
            orderBtn.setText(R.string.go_pay);
            orderBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, OrderChoosePayActivity.class);
                    intent.putExtra("order",order);
                    startActivity(intent);
                    finish();
                }
            });
        }else if(AppUtil.ordHP.equals(orderModel.getStatus())){
            orderBtnLayout.setVisibility(View.VISIBLE);
            orderBtn.setText(R.string.order_code);
            orderBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, OrderCodeActivity.class);
                    intent.putExtra("order", order);
                    startActivity(intent);
                }
            });
        }
    }

    public View getFarmSetSubItem(FarmItemsModel farmItemsModel) {
        View item = ((LinearLayout) inflater.inflate(R.layout.item_sub_farmset, farmSetItemList, true)).getChildAt(farmSetItemList.getChildCount() - 1);
        TextView farmSetName = (TextView) item.findViewById(R.id.farmset_item_name);
        TextView farmSetDesc = (TextView) item.findViewById(R.id.farmset_item_desc);
        TextView farmSetPrice = (TextView) item.findViewById(R.id.farmset_item_price);
        TextView farmSetTag = (TextView) item.findViewById(R.id.farmset_item_tag);
        TextView farmSetDescList = (TextView) item.findViewById(R.id.farmset_item_desclist);
        ImageView farmSetImg = (ImageView) item.findViewById(R.id.farmset_item_img);
        farmSetPrice.setTextColor(getResources().getColor(R.color.green2));
        farmSetName.setText(farmItemsModel.getFarmName());
        farmSetDesc.setText(farmItemsModel.getFarmItemName());
        farmSetPrice.setText(dataFormat.format(farmItemsModel.getConsumeTime()));
        farmSetTag.setText(AppUtil.getFarmSetTag(farmItemsModel.getFarmItemType()));
        farmSetTag.setBackgroundResource(AppUtil.getFarmSetTagBg(farmItemsModel.getFarmItemType()));
        farmSetDescList.setText(farmItemsModel.getFarmItemDesc());
        ImageLoaderUtil.getInstance().displayImg(farmSetImg, farmItemsModel.getResources().get(0).getResourceLocation()+AppUtil.FARM_SET_DETAIL_IMG_SIZE);
        View setHeader = item.findViewById(R.id.farm_set_item_header);
        View setContent = item.findViewById(R.id.farm_set_item_content);
        setHeader.setTag(setContent);
        setHeader.setOnClickListener(onFarmSetItemClick);
        return item;
    }

    public View.OnClickListener onFarmSetItemClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(currentItemView == v)return;
            View view = (View) v.getTag();
            if (view.getVisibility() == View.VISIBLE) {
                view.setVisibility(View.GONE);
            } else {
                if(currentItemView != null)
                    currentItemView.setVisibility(View.GONE);
                view.setVisibility(View.VISIBLE);
                currentItemView = view;
            }
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected String setActionBarTitle() {
        return getString(R.string.order_info);
    }
}
