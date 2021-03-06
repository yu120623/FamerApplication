package com.egceo.app.myfarm.order.actvity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baseandroid.BaseActivity;
import com.baseandroid.util.ImageLoaderUtil;
import com.egceo.app.myfarm.R;
import com.egceo.app.myfarm.entity.FarmItemsModel;
import com.egceo.app.myfarm.entity.FarmSetModel;
import com.egceo.app.myfarm.entity.OrderModel;
import com.egceo.app.myfarm.entity.TransferObject;
import com.egceo.app.myfarm.http.API;
import com.egceo.app.myfarm.http.AppHttpResListener;
import com.egceo.app.myfarm.http.AppRequest;
import com.egceo.app.myfarm.util.AppUtil;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by FreeMason on 2016/1/28.
 */
public class OrderCodeActivity extends BaseActivity {
    private LinearLayout orderItemLayout;
    private TextView orderName;
    private TextView orderPrice;
    private TextView orderTime;
    private ImageView code;
    private SimpleDateFormat dateFormat;
    private OrderModel order;
    @Override
    protected void initViews() {
        findViews();
        initData();
        loadDataFromServer();
    }

    private void loadDataFromServer() {
        String url = API.URL + API.API_URL.ORDER_CODE;
        TransferObject data = AppUtil.getHttpData(context);
        data.setOrderSn(order.getOrderSn());
        AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                if(data.getQrCodeModel() != null){
                    showData(data);
                }
            }

        },data);
        request.execute();
    }

    private void showData(TransferObject data) {
        ImageLoaderUtil.getInstance().displayImg(code,data.getQrCodeModel().getResource().getResourceLocation());
        for(OrderModel orderModel : data.getQrCodeModel().getOrderModels()){
            List<FarmItemsModel> farmItemsModelList = orderModel.getFarmItemsModels();
            for(int i = 0; i < farmItemsModelList.size();i++){
                FarmItemsModel farmItemsModel = farmItemsModelList.get(i);
                View view = inflater.inflate(R.layout.item_farm_code,null,false);
                TextView tag = (TextView) view.findViewById(R.id.farm_item_tag);
                TextView name = (TextView) view.findViewById(R.id.farm_item_name);
                TextView desc = (TextView) view.findViewById(R.id.farm_item_desc);
                TextView status = (TextView) view.findViewById(R.id.farm_item_status);
                tag.setText(AppUtil.getFarmSetTag(farmItemsModel.getFarmItemType()));
                tag.setBackgroundResource(AppUtil.getFarmSetTagBg(farmItemsModel.getFarmItemType()));
                name.setText(farmItemsModel.getFarmItemDesc());
                desc.setText(farmItemsModel.getFarmName());
                status.setText(farmItemsModel.getStatus().equals("0")?getString(R.string.wait_consumption):getString(R.string.already_consumption));
                status.setTextColor(farmItemsModel.getStatus().equals("0")?getResources().getColor(R.color.gray):getResources().getColor(R.color.green2));
                orderItemLayout.addView(view);
            }
        }
    }

    private void initData() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        order = (OrderModel) this.getIntent().getSerializableExtra("order");
        orderName.setText(getString(R.string.product_info)+order.getFarmSetModel().getFarmSetName());
        orderPrice.setText(getString(R.string.order_money)+order.getOrdePrice()+getString(R.string.rmb));
        orderTime.setText(getString(R.string.order_time)+dateFormat.format(order.getJourneyTime()));
    }

    private void findViews() {
        orderItemLayout = (LinearLayout) this.findViewById(R.id.order_code_item_layout);
        code = (ImageView) this.findViewById(R.id.order_code_img);
        orderName = (TextView) this.findViewById(R.id.order_name);
        orderPrice = (TextView) this.findViewById(R.id.order_price);
        orderTime = (TextView) this.findViewById(R.id.order_time);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_order_code;
    }

    @Override
    protected String setActionBarTitle() {
        return getString(R.string.order_code_title);
    }
}
