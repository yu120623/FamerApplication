package com.project.farmer.famerapplication.order.actvity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.model.Text;
import com.baseandroid.BaseActivity;
import com.baseandroid.util.ImageLoaderUtil;
import com.project.farmer.famerapplication.R;
import com.project.farmer.famerapplication.entity.FarmItemsModel;
import com.project.farmer.famerapplication.entity.OrderModel;
import com.project.farmer.famerapplication.entity.TransferObject;
import com.project.farmer.famerapplication.http.API;
import com.project.farmer.famerapplication.http.AppHttpResListener;
import com.project.farmer.famerapplication.http.AppRequest;
import com.project.farmer.famerapplication.util.AppUtil;

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
    @Override
    protected void initViews() {
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
    }

    private void loadDataFromServer() {
        String url = API.URL + API.API_URL.ORDER_INFO;
        TransferObject data = new TransferObject();
        data.setOrderSn(order.getOrderSn());
        data.setUserAliasId("aaa");
        AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                if(data.getOrderModel() != null){
                    showOrderInfo(data.getOrderModel());
                }
            }
        },data);
        request.execute();
    }

    private void showOrderInfo(OrderModel orderModel) {
        orderSN.setText(orderModel.getOrderSn());
        orderMoney.setText(orderModel.getPrice()+"×"+orderModel.getCopies());
        buyNum.setText(orderModel.getCopies()+"");
        farmFund.setText(orderModel.getFund()+"");
        contactName.setText(orderModel.getContact().getConnectName());
        for(FarmItemsModel farmItemsModel : orderModel.getFarmItemsModels()){
            getFarmSetSubItem(farmItemsModel);
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
        ImageLoaderUtil.getInstance().displayImg(farmSetImg, farmItemsModel.getResources().get(0).getResourceLocation());
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
